package com.ecohub.heater.data.usecase

import com.ecohub.common.concurrent.Dispatcher
import com.ecohub.common.usecase.SuspendWithArgsUseCase
import com.ecohub.heater.data.interactor.RemoteInteractor
import com.ecohub.heater.domain.model.Metrics
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory
class RemoteUpdateUsecase(
    private val dispatcher: Dispatcher,
    private val interactor: RemoteInteractor
) : SuspendWithArgsUseCase<RemoteUpdateUsecase.Argument, Metrics> {
    override suspend fun invoke(
        args: Argument
    ): Metrics = withContext(dispatcher.io) {
        interactor.synchronize(
            temperature = args.temperature,
            version = args.version
        )
    }

    data class Argument(
        val temperature: Float,
        val version: Int
    )
}
