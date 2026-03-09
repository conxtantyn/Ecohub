package com.ecohub.ui.main

import com.ecohub.module.AppModule
import com.ecohub.ui.component.UiComponent
import com.ecohub.ui.component.UiComponentProvider
import com.ecohub.ui.dashboard.Dashboard
import com.ecohub.ui.factory.UiBuilderFactory
import com.ecohub.ui.splash.Splash
import org.koin.core.annotation.Module
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module
import org.koin.ksp.generated.module

@Module
object Main {
    class Builder(scope: Scope): UiComponent.ComponentBuilder(scope) {
        override fun build(): Scope {
            val scope = scope(named<Main>())
            scope.getKoin().loadModules(listOf(
                module {
                    scope<Main> {
                        factory<UiComponentProvider.Factory> {
                            UiBuilderFactory(
                                listOf(
                                    this@Builder,
                                    Splash.Builder(scope),
                                    Dashboard.Builder(scope),
                                )
                            )
                        }
                    }
                },
                AppModule.module,
                Main.module
            ))
            return scope
        }
    }
}
