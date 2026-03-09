package com.ecohub.heater.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DeviceModel(
    val uuid: String,
    val name: String,
)
