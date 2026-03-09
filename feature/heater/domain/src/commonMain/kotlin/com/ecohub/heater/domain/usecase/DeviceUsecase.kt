package com.ecohub.heater.domain.usecase

import com.ecohub.common.concurrent.Dispatcher
import com.ecohub.common.usecase.SuspendWithArgsUseCase
import com.ecohub.heater.domain.model.Device
import com.ecohub.heater.domain.repository.DeviceRepository
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory
class DeviceUsecase(
    private val dispatcher: Dispatcher,
    private val repository: DeviceRepository
) : SuspendWithArgsUseCase<String, Device> {
    override suspend fun invoke(
        args: String
    ): Device = withContext(dispatcher.io) {
        try {
            repository.get()
        } catch (_: Throwable) {
            repository.initialize(args)
        }
    }
}
