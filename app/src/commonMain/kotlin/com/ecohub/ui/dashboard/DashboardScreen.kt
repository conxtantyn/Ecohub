package com.ecohub.ui.dashboard

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import cafe.adriel.voyager.core.screen.Screen
import com.ecohub.heater.ui.controller.ControllerScreen
import com.ecohub.heater.ui.controller.ControllerEvent
import com.ecohub.heater.ui.controller.ControllerEvent.Companion.LocalControllerEvent
import com.ecohub.heater.ui.display.DisplayScreen
import com.ecohub.service.HeatService
import com.ecohub.ui.extension.factory
import kotlinx.coroutines.launch
import org.koin.core.scope.ScopeID

class DashboardScreen(
    val scope: ScopeID,
    val uuid: String,
) : Screen {
    @Composable
    override fun Content() {
        val context = rememberCoroutineScope()
        val snackbar = remember { SnackbarHostState() }
        val factory = factory(scope)
        val component = remember(factory, uuid) { factory
            .builder(Dashboard.Builder::class).build(uuid) }
        Scaffold(snackbarHost = { SnackbarHost(snackbar) }) {
            val event = remember { object : ControllerEvent {
                override fun invoke(event: ControllerEvent.Event) {
                    when (event) {
                        is ControllerEvent.Event.Toast -> {
                            context.launch {
                                snackbar.showSnackbar(
                                    message = event.message
                                )
                            }
                        }
                    }
                }
            } }
            CompositionLocalProvider(LocalControllerEvent provides event) {
                DashboardPage(
                    top = { DisplayScreen(component.id).Content() }
                ) {
                    ControllerScreen(
                        scope = component.id,
                        rate = 0.5f
                    ).Content()
                }
            }
        }
        LaunchedEffect(Unit) {
            component.get<HeatService>().start()
        }
    }
}
