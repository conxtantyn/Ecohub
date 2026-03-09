package com.ecohub.ui.splash

import com.ecohub.heater.domain.usecase.DeviceUsecase
import com.ecohub.ui.component.UiComponent
import org.koin.core.annotation.Module
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.ksp.generated.module

@Module
object Splash {
    @org.koin.core.annotation.Scope(Splash::class)
    fun provideViewModel(
        usecase: DeviceUsecase
    ): SplashViewModel {
        return SplashViewModel(usecase)
    }

    class Builder(scope: Scope): UiComponent.ComponentBuilder(scope) {
        override fun build(): Scope {
            val scope = scope(named<Splash>())
            scope.getKoin().loadModules(listOf(Splash.module))
            return scope
        }
    }
}
