package com.ecohub.heater.data.repository

import com.ecohub.common.interactor.PersistenceInteractor
import com.ecohub.heater.data.mapper.mapToDomain
import com.ecohub.heater.data.model.DeviceModel
import com.ecohub.heater.domain.exception.InitException
import com.ecohub.heater.domain.exception.NotFoundException
import com.ecohub.heater.domain.model.Device
import com.ecohub.heater.domain.repository.DeviceRepository
import org.koin.core.annotation.Factory
import org.koin.ext.getFullName
import kotlin.reflect.typeOf
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Factory
class DeviceRepositoryDelegate(
    private val persistence: PersistenceInteractor
) : DeviceRepository {
    private val key: String = DeviceRepositoryDelegate::class.getFullName()

    override suspend fun get(): Device {
        return persistence.get<DeviceModel>(
            key = key,
            type = typeOf<DeviceModel?>()
        )?.mapToDomain() ?: throw NotFoundException()
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun initialize(name: String): Device {
        val device = DeviceModel(
            uuid = Uuid.random().toString(),
            name = name
        )
        persistence.set(key, device)
        return persistence.get<DeviceModel>(
            key = key,
            type = typeOf<DeviceModel?>()
        )?.mapToDomain() ?: throw InitException()
    }
}
