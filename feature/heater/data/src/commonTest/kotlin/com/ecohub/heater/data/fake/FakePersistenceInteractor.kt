package com.ecohub.heater.data.fake

import com.ecohub.common.interactor.PersistenceInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.serialization.Serializable
import kotlin.reflect.KType

class FakePersistenceInteractor : PersistenceInteractor {
    private val data = mutableMapOf<String, Any>()
    private val flows = mutableMapOf<String, MutableSharedFlow<Any?>>()

    @Suppress("UNCHECKED_CAST")
    override fun <T : @Serializable Any> observe(key: String, type: KType): Flow<T?> {
        return flows.getOrPut(key) {
            MutableSharedFlow<Any?>(replay = 1).apply {
                tryEmit(data[key])
            }
        }.asSharedFlow() as Flow<T?>
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : @Serializable Any> get(key: String, type: KType): T? {
        return data[key] as T?
    }

    override fun <T : @Serializable Any> set(key: String, value: T) {
        data[key] = value
        flows[key]?.tryEmit(value)
    }

    override fun remove(key: String) {
        data.remove(key)
        flows[key]?.tryEmit(null)
    }
}
