package com.ecohub.module

import com.ecohub.preference.PreferenceModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(
    includes = [
        CoreModule::class,
        PreferenceModule::class,
        HeaterModule::class,
    ]
)
@ComponentScan("com.ecohub.service")
object AppModule
