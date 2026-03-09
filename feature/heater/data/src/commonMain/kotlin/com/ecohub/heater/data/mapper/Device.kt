package com.ecohub.heater.data.mapper

import com.ecohub.heater.data.model.DeviceModel
import com.ecohub.heater.domain.model.Device

fun DeviceModel.mapToDomain(): Device {
    return Device(
        uuid = uuid,
        name = name
    )
}
