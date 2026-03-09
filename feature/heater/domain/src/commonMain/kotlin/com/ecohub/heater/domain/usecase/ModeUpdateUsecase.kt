package com.ecohub.heater.domain.usecase

import com.ecohub.common.usecase.SuspendWithArgsUseCase
import com.ecohub.heater.domain.interactor.HeaterInteractor
import com.ecohub.heater.domain.model.Mode
import org.koin.core.annotation.Factory

@Factory
class ModeUpdateUsecase(
    private val interactor: HeaterInteractor
) : SuspendWithArgsUseCase<Mode, Unit> {
    override suspend fun invoke(args: Mode) {
        return interactor.update(args)
    }
}
