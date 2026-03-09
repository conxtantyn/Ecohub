package com.ecohub.heater.data.persistence

import com.ecohub.heater.data.model.MetricsModel
import com.ecohub.heater.domain.model.Channel
import com.ecohub.heater.domain.model.Metrics
import com.ecohub.heater.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface HeaterPersistence {
    fun observe(): Flow<Response>

    fun current(): MetricsModel?

    fun target(): MetricsModel?

    fun update(target: MetricsModel)

    fun update(
        channel: Channel,
        temperature: Float,
        version: Int,
        hasConflict: Boolean = false
    ): Metrics
}
