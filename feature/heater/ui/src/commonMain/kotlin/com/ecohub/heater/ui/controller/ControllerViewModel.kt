package com.ecohub.heater.ui.controller

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.ecohub.heater.domain.model.Metrics
import com.ecohub.heater.domain.model.Mode
import com.ecohub.heater.domain.model.Response
import com.ecohub.heater.domain.usecase.LocalUpdateUsecase
import com.ecohub.heater.domain.usecase.HeatResolveUseCase
import com.ecohub.heater.domain.usecase.ModeUpdateUsecase
import com.ecohub.heater.domain.usecase.ModeUsecase
import com.ecohub.heater.domain.usecase.ObserveUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ControllerViewModel(
    private val rate: Float,
    private val observe: ObserveUseCase,
    modeObserver: ModeUsecase,
    private val modeUpdate: ModeUpdateUsecase,
    private val localUpdate: LocalUpdateUsecase,
    private val resolve: HeatResolveUseCase
) : ScreenModel {
    private val scope = screenModelScope

    private val _status = MutableStateFlow<Status>(Status.Default)

    private val _state = MutableStateFlow<State>(State.Default)

    val state: StateFlow<State> = _state.asStateFlow()

    val status: StateFlow<Status> = _status.asStateFlow()

    val mode = modeObserver().stateIn(
        scope = screenModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Mode.MANUAL
    )

    operator fun invoke() {
        scope.launch {
            observe().collectLatest { response ->
                when (response) {
                    is Response.Empty -> _state.emit(State.Default)
                    is Response.Success -> {
                        _state.emit(State.Success(response.metrics))
                    }
                    is Response.Conflict -> {
                        _state.emit(State.Conflict(
                            target = response.target,
                            current = response.current
                        ))
                    }
                }
            }
        }
    }

    fun onUpdate(increment: Boolean = true) {
        screenModelScope.launch {
            try {
                localUpdate(if (increment) {
                    rate
                } else {
                    -rate
                })
            } catch (error: Throwable) {
                _status.emit(Status.Error(error))
            }
        }
    }

    fun onToggleMode(enabled: Boolean) {
        screenModelScope.launch {
            modeUpdate(if (enabled) {
                Mode.AUTOMATIC
            } else {
                Mode.MANUAL
            })
        }
    }

    fun onResolve(metrics: Metrics) {
        screenModelScope.launch {
            resolve(metrics)
        }
    }

    sealed interface Status {
        data object Default: Status
        data class Error(val error: Throwable): Status
    }

    sealed interface State {
        data object Default : State
        data class Success(val current: Metrics) : State
        data class Conflict(
            val target: Metrics,
            val current: Metrics,
        ) : State
    }
}
