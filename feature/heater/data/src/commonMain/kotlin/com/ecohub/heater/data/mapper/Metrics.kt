package com.ecohub.heater.data.mapper

import com.ecohub.heater.data.model.MetricsModel
import com.ecohub.heater.domain.model.Channel
import com.ecohub.heater.domain.model.Metrics

fun MetricsModel.mapToDomain(): Metrics {
    return Metrics(
        device = device,
        channel = Channel.valueOf(channel),
        temperature = temperature,
        timestamp = timestamp,
        version = version
    )
}

fun Metrics.mapFromDomain(): MetricsModel {
    return MetricsModel(
        device = device,
        channel = channel.name,
        temperature = temperature,
        timestamp = timestamp,
        version = version
    )
}
