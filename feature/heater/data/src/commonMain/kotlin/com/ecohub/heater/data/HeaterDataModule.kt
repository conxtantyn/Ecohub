package com.ecohub.heater.data

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan(value = [
    "com.ecohub.heater.data.repository",
])
class HeaterDataModule
