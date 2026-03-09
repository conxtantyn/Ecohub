package com.ecohub.heater.ui.controller

import androidx.compose.runtime.staticCompositionLocalOf
import org.koin.ext.getFullName

interface ControllerEvent {
    operator fun invoke(event: Event)

    sealed interface Event {
        data class Toast(val message: String) : Event
    }

    companion object Companion {
        val LocalControllerEvent = staticCompositionLocalOf<ControllerEvent> {
            error("${ControllerEvent::class.getFullName()} not provided")
        }
    }
}