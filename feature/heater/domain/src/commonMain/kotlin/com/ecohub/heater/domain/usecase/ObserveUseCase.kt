package com.ecohub.heater.domain.usecase

import com.ecohub.common.usecase.ObservableUseCase
import com.ecohub.heater.domain.interactor.HeaterInteractor
import com.ecohub.heater.domain.model.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class ObserveUseCase(
    private val interactor: HeaterInteractor
) : ObservableUseCase<Response> {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun invoke(): Flow<Response> {
        return interactor.state()
    }
}
