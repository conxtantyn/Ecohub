package com.ecohub.heater.ui.fake

import com.ecohub.common.concurrent.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher

class FakeDispatcher(dispatcher: CoroutineDispatcher) : Dispatcher {
    override val io: CoroutineDispatcher = dispatcher
    override val main: CoroutineDispatcher = dispatcher
    override val default: CoroutineDispatcher = dispatcher
}
