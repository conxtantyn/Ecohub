package com.ecohub.heater.ui.display

import com.ecohub.heater.domain.usecase.HeatResolveUseCase
import com.ecohub.heater.domain.usecase.ModeUsecase
import com.ecohub.heater.domain.usecase.ObserveUseCase
import com.ecohub.ui.component.UiComponent
import org.koin.core.annotation.Module
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.ksp.generated.module

@Module
object Display {
    @org.koin.core.annotation.Scope(Display::class)
    fun provideViewModel(usecase: ObserveUseCase): DisplayViewModel {
        return DisplayViewModel(usecase)
    }

    class Builder(scope: Scope): UiComponent.ComponentBuilder(scope) {
        override fun build(): Scope {
            val scope = scope(named<Display>())
            scope.getKoin().loadModules(listOf(Display.module))
            return scope
        }
    }
}
