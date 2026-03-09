package com.ecohub.heater.ui.display

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.ecohub.heater.domain.model.Metrics
import com.ecohub.heater.domain.model.Response
import com.ecohub.heater.domain.usecase.ObserveUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DisplayViewModel(
    private val observe: ObserveUseCase
) : ScreenModel {
    private val scope = screenModelScope

    private val _state = MutableStateFlow<State>(State.Default)

    val state: StateFlow<State> = _state.asStateFlow()

    operator fun invoke() {
        scope.launch {
            observe().collect { response ->
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

    sealed interface State {
        data object Default : State
        data class Success(val metrics: Metrics) : State
        data class Conflict(
            val target: Metrics,
            val current: Metrics,
        ) : State
    }
}
