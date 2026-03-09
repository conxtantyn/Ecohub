package com.ecohub.ui.fake

import com.ecohub.heater.domain.model.Device
import com.ecohub.heater.domain.repository.DeviceRepository

class FakeDeviceRepository : DeviceRepository {
    var device: Device? = null
    var shouldFail = false

    override suspend fun get(): Device {
        if (shouldFail) throw Throwable("Remote Error")
        return device ?: throw Throwable("Not found")
    }

    override suspend fun initialize(name: String): Device {
        if (shouldFail) throw Throwable("Init Error")
        val newDevice = Device(uuid = "uuid-$name", name = name)
        device = newDevice
        return newDevice
    }
}
