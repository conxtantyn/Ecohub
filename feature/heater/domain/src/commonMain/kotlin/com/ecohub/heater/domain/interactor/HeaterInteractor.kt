package com.ecohub.heater.domain.interactor

import com.ecohub.heater.domain.model.Metrics
import com.ecohub.heater.domain.model.Mode
import com.ecohub.heater.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface HeaterInteractor {
    fun get(): Metrics

    fun mode(): Flow<Mode>

    fun state(): Flow<Response>

    suspend fun resolve(metrics: Metrics): Response

    fun update(mode: Mode)

    suspend fun update(
        temperature: Float,
        version: Int
    ): Metrics
}
