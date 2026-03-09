package com.ecohub.heater.ui.fake

import com.ecohub.heater.domain.interactor.HeaterInteractor
import com.ecohub.heater.domain.model.Channel
import com.ecohub.heater.domain.model.Metrics
import com.ecohub.heater.domain.model.Mode
import com.ecohub.heater.domain.model.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeHeaterInteractor : HeaterInteractor {
    private val _mode = MutableStateFlow(Mode.MANUAL)
    override fun mode(): Flow<Mode> = _mode

    private val _state = MutableStateFlow<Response>(Response.Empty)
    override fun state(): Flow<Response> = _state

    var metrics = Metrics("dev", Channel.LOCAL, 20f, 1000L, 1)
    override fun get(): Metrics = metrics

    override suspend fun resolve(metrics: Metrics): Response {
        this.metrics = metrics
        _state.value = Response.Success(metrics)
        return Response.Success(metrics)
    }

    override fun update(mode: Mode) {
        _mode.value = mode
    }

    override suspend fun update(temperature: Float, version: Int): Metrics {
        metrics = metrics.copy(temperature = temperature, version = version)
        _state.value = Response.Success(metrics)
        return metrics
    }
}

