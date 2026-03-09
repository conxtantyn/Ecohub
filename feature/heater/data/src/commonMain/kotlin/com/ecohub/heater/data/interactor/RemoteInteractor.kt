package com.ecohub.heater.data.interactor

import com.ecohub.heater.domain.model.Metrics

interface RemoteInteractor {
    suspend fun synchronize(
        temperature: Float,
        version: Int
    ): Metrics
}
