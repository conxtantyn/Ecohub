package com.ecohub.heater.domain.repository

import com.ecohub.heater.domain.model.Device

interface DeviceRepository {
    suspend fun get(): Device

    suspend fun initialize(name: String): Device
}
