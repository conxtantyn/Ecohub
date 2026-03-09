package com.ecohub.heater.domain.model

data class Metrics(
    val device: String,
    val channel: Channel,
    val temperature: Float,
    val timestamp: Long,
    val version: Int
)
