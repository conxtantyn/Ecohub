package com.ecohub.heater.domain

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan(value = [
    "com.ecohub.heater.domain.usecase",
])
class HeaterDomainModule
