package com.ecohub.ui.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.ecohub.ui.extension.factory
import com.ecohub.ui.splash.SplashEvent.Companion.LocalSplashEvent
import ecohub.app.generated.resources.Res
import ecohub.app.generated.resources.app_name
import org.jetbrains.compose.resources.stringResource
import org.koin.core.scope.ScopeID

class SplashScreen(val scope: ScopeID) : Screen {
    @Composable
    override fun Content() {
        val event = LocalSplashEvent.current
        val factory = factory(scope)
        val component = remember { factory.builder(Splash.Builder::class).build() }
        val viewModel = rememberScreenModel { component.get<SplashViewModel>() }
        val state = viewModel.state.collectAsStateWithLifecycle()
        val app = stringResource(Res.string.app_name)
        SplashPage()
        LaunchedEffect(Unit) { viewModel(app) }
        LaunchedEffect(Unit) {
            snapshotFlow { state.value }
                .collect { state ->
                    if (state is SplashViewModel.State.Success) {
                        event(SplashEvent.Event.Initialized(state.uuid))
                    }
                }
        }
    }
}
