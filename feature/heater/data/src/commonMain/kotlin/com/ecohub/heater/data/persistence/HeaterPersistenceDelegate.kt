package com.ecohub.heater.data.persistence

import com.ecohub.common.interactor.PersistenceInteractor
import com.ecohub.heater.data.extension.getMetrics
import com.ecohub.heater.data.extension.getMetricsOrDefault
import com.ecohub.heater.data.extension.observeMetrics
import com.ecohub.heater.data.mapper.mapToDomain
import com.ecohub.heater.data.model.MetricsModel
import com.ecohub.heater.domain.model.Channel
import com.ecohub.heater.domain.model.Metrics
import com.ecohub.heater.domain.model.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import org.koin.ext.getFullName
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class HeaterPersistenceDelegate(
    private val uuid: String,
    private val persistence: PersistenceInteractor
): HeaterPersistence {
    private val key: String = "$METRICS/$uuid"

    private val targetKey: String = "$TARGET/$uuid"

    override fun observe(): Flow<Response> {
        val target = persistence.observeMetrics(targetKey).map { it?.mapToDomain() }
        val metrics = persistence.observeMetrics(key).map { it?.mapToDomain() }
        return combine(metrics, target) { metrics, target ->
            if (target == null) {
                metrics?.let { Response.Success(it) }
            } else {
                metrics?.let {
                    Response.Conflict(
                        current = it,
                        target = target,
                    )
                }
            } ?: Response.Empty
        }
    }

    override fun current(): MetricsModel? {
        return persistence.getMetrics(key)
    }

    override fun target(): MetricsModel? {
        return persistence.getMetrics(targetKey)
    }

    override fun update(target: MetricsModel) {
        persistence.remove(targetKey)
        persistence.set(key, target)
    }

    @OptIn(ExperimentalTime::class)
    override fun update(
        channel: Channel,
        temperature: Float,
        version: Int,
        hasConflict: Boolean
    ): Metrics {
        val metrics = MetricsModel(
            device = uuid,
            channel = channel.name,
            temperature = temperature,
            timestamp = Clock.System.now().toEpochMilliseconds(),
            version = version
        )
        if (hasConflict) {
            persistence.set(targetKey, metrics)
        } else {
            persistence.set(key, metrics)
        }
        return persistence.getMetricsOrDefault(key, metrics.mapToDomain())
    }

    private companion object {
        val TARGET = "${HeaterPersistenceDelegate::class.getFullName()}::TARGET"
        val METRICS = "${HeaterPersistenceDelegate::class.getFullName()}::METRICS"
    }
}
