package com.ecohub.ui.splash

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.ecohub.heater.domain.usecase.DeviceUsecase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val usecase: DeviceUsecase
) : ScreenModel {
    private val scope = screenModelScope

    private val _state = MutableStateFlow<State>(State.Default)

    val state: StateFlow<State> = _state.asStateFlow()

    operator fun invoke(name: String) {
        scope.launch {
            try {
                _state.emit(State.Loading)
                _state.emit(State.Success(usecase(name).uuid))
            } catch (error: Throwable) {
                _state.emit(State.Error(error))
            }
        }
    }

    sealed interface State {
        data object Default : State
        data object Loading : State
        data class Success(val uuid: String) : State
        data class Error(val error: Throwable) : State
    }
}
