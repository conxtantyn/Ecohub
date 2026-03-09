package com.ecohub

import android.app.Application
import com.ecohub.ui.extension.scopeOf
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class EcohubApplication : Application() {
    lateinit var scope: Scope

    override fun onCreate() {
        super.onCreate()
        val koin = startKoin {}.koin
        scope = koin.scopeOf(named<EcohubApplication>())
    }
}
