package com.ecohub.heater.domain.usecase

import com.ecohub.common.concurrent.Dispatcher
import com.ecohub.common.usecase.SuspendWithArgsUseCase
import com.ecohub.heater.domain.interactor.HeaterInteractor
import com.ecohub.heater.domain.model.Metrics
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory
class HeatUpdateUsecase(
    private val dispatcher: Dispatcher,
    private val interactor: HeaterInteractor
) : SuspendWithArgsUseCase<HeatUpdateUsecase.Argument, Metrics> {
    override suspend fun invoke(
        args: Argument
    ): Metrics = withContext(dispatcher.io) {
        interactor.update(
            args.temperature,
            args.version
        )
    }

    data class Argument(
        val temperature: Float,
        val version: Int
    )
}
