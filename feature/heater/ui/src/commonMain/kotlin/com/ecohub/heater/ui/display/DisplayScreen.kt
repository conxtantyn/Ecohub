package com.ecohub.heater.ui.display

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.ecohub.heater.ui.extension.round
import com.ecohub.ui.extension.factory
import org.koin.core.scope.ScopeID

class DisplayScreen(val scope: ScopeID) : Screen {
    @Composable
    override fun Content() {
        val factory = factory(scope)
        val component = remember { factory
            .builder(Display.Builder::class).build() }
        val viewModel = rememberScreenModel { component.get<DisplayViewModel>() }
        val state by viewModel.state.collectAsState()
        val derivedState = remember { derivedStateOf {
            when(state) {
                is DisplayViewModel.State.Default -> "--°C" to "Waiting..."
                is DisplayViewModel.State.Success -> {
                    "${(state as DisplayViewModel.State.Success).metrics.temperature
                        .round(2)}°C" to "Current temperature"
                }
                is DisplayViewModel.State.Conflict -> {
                    "${(state as DisplayViewModel.State.Conflict).target.temperature
                        .round(2)}°C" to "Conflict Detected"
                }
            }
        } }
        DisplayPage(
            title = derivedState.value.first,
            status = derivedState.value.second,
            color = if (state is DisplayViewModel.State.Conflict) {
                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.outline
            }
        )
        LaunchedEffect(Unit) {
            viewModel()
        }
    }
}
