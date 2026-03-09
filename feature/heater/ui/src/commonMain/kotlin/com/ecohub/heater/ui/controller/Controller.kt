package com.ecohub.heater.ui.controller

import com.ecohub.heater.domain.usecase.LocalUpdateUsecase
import com.ecohub.heater.domain.usecase.HeatResolveUseCase
import com.ecohub.heater.domain.usecase.ModeUpdateUsecase
import com.ecohub.heater.domain.usecase.ModeUsecase
import com.ecohub.heater.domain.usecase.ObserveUseCase
import com.ecohub.ui.component.UiComponent
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module
import org.koin.ksp.generated.module

@Module
object Controller {
    @org.koin.core.annotation.Scope(Controller::class)
    fun provideViewModel(
        @Named("rate") rate: Float,
        mode: ModeUsecase,
        modeUpdate: ModeUpdateUsecase,
        decrement: LocalUpdateUsecase,
        heatResolve: HeatResolveUseCase,
        observe: ObserveUseCase,
    ): ControllerViewModel {
        return ControllerViewModel(
            modeObserver = mode,
            observe = observe,
            rate = rate,
            modeUpdate = modeUpdate,
            resolve = heatResolve,
            localUpdate = decrement,
        )
    }

    class Builder(scope: Scope): UiComponent.ComponentBuilderWithArgs<Float>(scope) {
        override fun build(args: Float): Scope {
            val scope = scope(named<Controller>())
            scope.getKoin().loadModules(listOf(
                module {
                    scope<Controller> {
                        factory(named("rate")) { args }
                    }
                },
                Controller.module
            ))
            return scope
        }
    }
}
