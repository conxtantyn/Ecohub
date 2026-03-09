package com.ecohub.ui.splash

import androidx.compose.runtime.staticCompositionLocalOf
import org.koin.ext.getFullName

interface SplashEvent {
    operator fun invoke(event: Event)

    sealed interface Event {
        data class Initialized(val uuid: String) : Event
    }

    companion object {
        val LocalSplashEvent = staticCompositionLocalOf<SplashEvent> {
            error("${SplashEvent::class.getFullName()} not provided")
        }
    }
}
