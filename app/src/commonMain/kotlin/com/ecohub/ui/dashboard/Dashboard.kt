package com.ecohub.ui.dashboard

import com.ecohub.heater.data.interactor.HeaterInteractorDelegate
import com.ecohub.heater.data.interactor.RemoteInteractor
import com.ecohub.heater.data.usecase.RemoteUpdateUsecase
import com.ecohub.heater.domain.interactor.HeaterInteractor
import com.ecohub.heater.ui.controller.Controller
import com.ecohub.heater.ui.display.Display
import com.ecohub.service.HeatService
import com.ecohub.ui.component.UiComponent
import com.ecohub.ui.component.UiComponentProvider
import com.ecohub.ui.factory.UiBuilderFactory
import org.koin.core.annotation.Module
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module
import org.koin.ksp.generated.module

@Module
object Dashboard {
    class Builder(scope: Scope): UiComponent.ComponentBuilderWithArgs<String>(scope) {
        override fun build(args: String): Scope {
            val scope = scope(named<Dashboard>())
            scope.getKoin().loadModules(listOf(
                module {
                    scope<Dashboard> {
                        scoped<HeaterInteractorDelegate> {
                            HeaterInteractorDelegate(args, get())
                        }
                        scoped<HeaterInteractor> { get<HeaterInteractorDelegate>() }
                        scoped<RemoteInteractor> { get<HeaterInteractorDelegate>() }
                        scoped { RemoteUpdateUsecase(get(), get()) }
                        scoped { HeatService(get(), get(), get()) }
                        scoped<UiComponentProvider.Factory> {
                            UiBuilderFactory(
                                listOf(
                                    this@Builder,
                                    Controller.Builder(scope),
                                    Display.Builder(scope),
                                )
                            )
                        }
                    }
                },
                Dashboard.module
            ))
            return scope
        }
    }
}
