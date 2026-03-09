package com.ecohub.heater.domain.usecase

import com.ecohub.common.usecase.ObservableUseCase
import com.ecohub.heater.domain.interactor.HeaterInteractor
import com.ecohub.heater.domain.model.Mode
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class ModeUsecase(
    private val interactor: HeaterInteractor
) : ObservableUseCase<Mode> {
    override fun invoke(): Flow<Mode> {
        return interactor.mode()
    }
}
