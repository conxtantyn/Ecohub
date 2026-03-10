package com.ecohub.heater.ui.controller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.ecohub.heater.domain.model.Channel
import com.ecohub.heater.domain.model.Mode
import com.ecohub.heater.ui.controller.ControllerEvent.Companion.LocalControllerEvent
import com.ecohub.ui.extension.factory
import kotlinx.coroutines.flow.collectLatest
import org.koin.core.scope.ScopeID

class ControllerScreen(
    val scope: ScopeID,
    val rate: Float
) : Screen {
    @Composable
    override fun Content() {
        val factory = factory(scope)
        val component = remember { factory
            .builder(Controller.Builder::class).build(rate) }
        val viewModel = rememberScreenModel { component.get<ControllerViewModel>() }
        val event = LocalControllerEvent.current
        val state by viewModel.state.collectAsState()
        val status by viewModel.status.collectAsState()
        val mode = viewModel.mode.collectAsState()
        val initialized = remember { mutableStateOf(false) }
        val hasConflict = remember { derivedStateOf { state is ControllerViewModel.State.Conflict } }
        val current = remember { derivedStateOf {
            (state as? ControllerViewModel.State.Success?)?.current
        } }
        ControllerPage(
            mode = mode,
            onDecrement = { viewModel.onUpdate(false) },
            onIncrement = { viewModel.onUpdate() }
        ) { viewModel.onToggleMode(it) }
        ControllerDialog(
            state = state,
            hasConflict = hasConflict
        ) { viewModel.onResolve(it) }
        LaunchedEffect(Unit) { viewModel() }
        LaunchedEffect(status) {
            if (status is ControllerViewModel.Status.Error) {
                event(ControllerEvent.Event.Toast(
                    (status as ControllerViewModel.Status.Error).error.message ?: "Unknown Error"
                ))
            }
        }
        LaunchedEffect(Unit) {
            snapshotFlow { current.value }.collectLatest {
                val notify = it != null
                        && it.channel == Channel.REMOTE
                        && mode.value == Mode.MANUAL
                if (notify && initialized.value) {
                    event(ControllerEvent.Event.Toast(
                        "Updated by technician to ${(state as ControllerViewModel.State.Success).current.temperature}°C."
                    ))
                }
                initialized.value = true
            }
        }
    }
}
