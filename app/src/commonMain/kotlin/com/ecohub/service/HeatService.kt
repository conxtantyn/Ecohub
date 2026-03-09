package com.ecohub.service

import com.ecohub.common.concurrent.Dispatcher
import com.ecohub.heater.domain.interactor.HeaterInteractor
import com.ecohub.heater.data.usecase.RemoteUpdateUsecase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

class HeatService(
    dispatcher: Dispatcher,
    private val interactor: HeaterInteractor,
    private val remoteUpdateUsecase: RemoteUpdateUsecase
) {
    private val scope = CoroutineScope(SupervisorJob() + dispatcher.io)
    private var version = 0
    private var started = false

    fun start() {
        if (started) return
        started = true
        scope.launch {
            try {
                version = interactor.get().version
            } catch (error: Throwable) {
                error.printStackTrace()
            }
            while (isActive) {
                try {
                    val temperature = Random.nextFloat() * 10 + 15
                    val result = remoteUpdateUsecase(
                        RemoteUpdateUsecase.Argument(
                            temperature = temperature,
                            version = ++version
                        )
                    )
                    version = result.version
                } catch (error: Exception) {
                    error.printStackTrace()
                }
                delay(15000)
            }
        }
    }
}
