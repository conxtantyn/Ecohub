package com.ecohub.module

import com.ecohub.heater.data.HeaterDataModule
import com.ecohub.heater.domain.HeaterDomainModule
import org.koin.core.annotation.Module

@Module(
    includes = [
        HeaterDomainModule::class,
        HeaterDataModule::class,
    ]
)
class HeaterModule
