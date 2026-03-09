package com.ecohub

import androidx.compose.ui.window.ComposeUIViewController
import com.ecohub.ui.extension.scopeOf
import com.ecohub.ui.main.MainScreen
import com.ecohub.ui.theme.DesignTheme
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.ObservableSettings
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

interface EcohubApplication

object IosApp {
    private val koin = startKoin {}.koin
    val scope = koin.scopeOf(named<EcohubApplication>())

    init {
        scope.getKoin().loadModules(listOf(
            module {
                single<ObservableSettings> {
                    NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults())
                }
            }
        ))
    }
}

fun MainViewController() = ComposeUIViewController {
    DesignTheme { MainScreen(IosApp.scope.id).Content() }
}
