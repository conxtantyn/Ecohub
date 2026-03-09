package com.ecohub.ui.main

import cafe.adriel.voyager.navigator.Navigator
import com.ecohub.ui.dashboard.DashboardScreen
import com.ecohub.ui.splash.SplashEvent
import org.koin.core.scope.ScopeID

class MainInteractor(
    private val scope: ScopeID,
    private val navigator: Navigator
) : SplashEvent {
    override fun invoke(event: SplashEvent.Event) {
        if (event is SplashEvent.Event.Initialized) {
            navigator.replaceAll(DashboardScreen(scope, event.uuid))
        }
    }
}
