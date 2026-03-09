package com.ecohub.heater.ui.controller

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
        val component = remember(factory, rate) { factory
            .builder(Controller.Builder::class).build(rate) }
        val viewModel = rememberScreenModel { component.get<ControllerViewModel>() }
        val event = LocalControllerEvent.current
        val state by viewModel.state.collectAsState()
        val status by viewModel.status.collectAsState()
        val mode = viewModel.mode.collectAsState()
        val isConflict = remember { derivedStateOf { state is ControllerViewModel.State.Conflict } }
        val initialized = remember { mutableStateOf(false) }
        val current = remember { derivedStateOf {
            (state as? ControllerViewModel.State.Success?)?.current
        } }
        ControllerPage(
            mode = mode,
            onDecrement = { viewModel.onUpdate(false) },
            onIncrement = { viewModel.onUpdate() }
        ) { viewModel.onToggleMode(it) }
        if (isConflict.value) {
            val conflict = state as ControllerViewModel.State.Conflict
            AlertDialog(
                onDismissRequest = { },
                title = { Text("Conflict Detected") },
                text = {
                    Text("The technician has updated the temperature to ${conflict.target.temperature}°C. You tried to set it to ${conflict.current.temperature}°C. What would you like to do?")
                },
                confirmButton = {
                    Button(onClick = { viewModel.onResolve(conflict.current) }) {
                        Text("Overwrite")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        viewModel.onResolve(conflict.target)
                    }) {
                        Text("Keep Theirs")
                    }
                }
            )
        }
        LaunchedEffect(Unit) { viewModel() }
        LaunchedEffect(status) {
            if (status is ControllerViewModel.Status.Error) {
                event(ControllerEvent.Event.Toast(
                    (status as ControllerViewModel.Status.Error).error.message ?: "Unknown Error"
                ))
            }
        }
        LaunchedEffect(Unit) {
            snapshotFlow { current.value != null
                    && current.value!!.channel == Channel.REMOTE
                    && mode.value == Mode.AUTOMATIC
            }.collectLatest {
                if (it && initialized.value) {
                    event(ControllerEvent.Event.Toast(
                        "Updated by technician to ${(state as ControllerViewModel.State.Success).current.temperature}°C."
                    ))
                }
                initialized.value = true
            }
        }
    }
}
