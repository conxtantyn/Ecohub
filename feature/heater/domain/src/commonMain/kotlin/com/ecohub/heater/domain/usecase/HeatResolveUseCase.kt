package com.ecohub.heater.domain.usecase

import com.ecohub.common.usecase.SuspendWithArgsUseCase
import com.ecohub.heater.domain.interactor.HeaterInteractor
import com.ecohub.heater.domain.model.Metrics
import com.ecohub.heater.domain.model.Response
import org.koin.core.annotation.Factory

@Factory
class HeatResolveUseCase(
    private val interactor: HeaterInteractor
) : SuspendWithArgsUseCase<Metrics, Response> {
    override suspend fun invoke(args: Metrics): Response {
        return interactor.resolve(args)
    }
}
