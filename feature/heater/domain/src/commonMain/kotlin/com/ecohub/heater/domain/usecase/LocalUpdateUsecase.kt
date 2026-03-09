package com.ecohub.heater.domain.usecase

import com.ecohub.common.concurrent.Dispatcher
import com.ecohub.common.usecase.SuspendWithArgsUseCase
import com.ecohub.heater.domain.interactor.HeaterInteractor
import com.ecohub.heater.domain.model.Metrics
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory
class LocalUpdateUsecase(
    private val dispatcher: Dispatcher,
    private val heatUpdate: HeatUpdateUsecase,
    private val interactor: HeaterInteractor,
) : SuspendWithArgsUseCase<Float, Metrics> {
    override suspend fun invoke(
        args: Float
    ): Metrics = withContext(dispatcher.io) {
        val metrics = interactor.get()
        heatUpdate(
            HeatUpdateUsecase.Argument(
                temperature = metrics.temperature + args,
                version = metrics.version + 1
            )
        )
    }
}
