package com.ecohub.heater.data.interactor

import com.ecohub.common.interactor.PersistenceInteractor
import com.ecohub.heater.data.mapper.mapFromDomain
import com.ecohub.heater.data.mapper.mapToDomain
import com.ecohub.heater.data.persistence.HeaterPersistence
import com.ecohub.heater.data.persistence.HeaterPersistenceDelegate
import com.ecohub.heater.domain.exception.InitException
import com.ecohub.heater.domain.interactor.HeaterInteractor
import com.ecohub.heater.domain.model.Channel
import com.ecohub.heater.domain.model.Metrics
import com.ecohub.heater.domain.model.Mode
import com.ecohub.heater.domain.model.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import org.koin.ext.getFullName
import kotlin.reflect.typeOf

class HeaterInteractorDelegate(
    val uuid: String,
    private val persistence: PersistenceInteractor
) : HeaterInteractor,
    RemoteInteractor,
    HeaterPersistence by HeaterPersistenceDelegate(uuid, persistence) {
    private val key: String = HeaterInteractorDelegate::class.getFullName()

    override fun get(): Metrics = current()?.mapToDomain()  ?: throw InitException()

    override fun mode(): Flow<Mode> {
        return persistence.observe<String>(
            key = key,
            type = typeOf<String>()
        ).map { it?.let { Mode.valueOf(it) } ?: Mode.MANUAL }
    }

    override fun state(): Flow<Response> = observe()

    override suspend fun resolve(metrics: Metrics): Response {
        val target = target()?.version ?: 0
        val current = current()?.version ?: 0
        val model = metrics.copy(version = maxOf(target, current))
        update(model.mapFromDomain())
        return Response.Success(metrics = model)
    }

    override fun update(mode: Mode) {
        return persistence.set(key, mode.name)
    }

    override suspend fun update(
        temperature: Float,
        version: Int
    ): Metrics {
        return update(
            channel = Channel.LOCAL,
            temperature = temperature,
            version = version
        )
    }

    override suspend fun synchronize(temperature: Float, version: Int): Metrics {
        val mode = (mode().firstOrNull() ?: Mode.MANUAL)
        return update(
            channel = Channel.REMOTE,
            temperature = temperature,
            version = version,
            hasConflict = current()?.let {
                it.version >= version && mode == Mode.MANUAL
            } == true
        )
    }
}
