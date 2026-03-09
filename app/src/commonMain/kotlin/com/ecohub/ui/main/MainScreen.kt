package com.ecohub.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import com.ecohub.ui.design.DesignNavigation
import com.ecohub.ui.splash.SplashEvent.Companion.LocalSplashEvent
import com.ecohub.ui.splash.SplashScreen
import org.koin.compose.getKoin
import org.koin.core.scope.ScopeID

class MainScreen(val scope: ScopeID) : Screen {
    @Composable
    override fun Content() {
        val current = getKoin().getScope(scope)
        val component = remember { Main.Builder(current).build() }
        Navigator(SplashScreen(component.id)) { navigator ->
            val currentScreen = navigator.lastItemOrNull
            val interactor = remember { MainInteractor(component.id, navigator) }
            CompositionLocalProvider(
                LocalSplashEvent provides interactor
            ) { DesignNavigation(targetState = currentScreen) { it?.Content() } }
        }
    }
}
