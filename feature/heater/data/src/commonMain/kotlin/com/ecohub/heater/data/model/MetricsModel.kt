package com.ecohub.heater.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MetricsModel(
    val device: String,
    val channel: String,
    val temperature: Float,
    val timestamp: Long,
    val version: Int
)
