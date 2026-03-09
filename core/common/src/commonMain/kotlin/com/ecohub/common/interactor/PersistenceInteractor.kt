package com.ecohub.common.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable
import kotlin.reflect.KType

interface PersistenceInteractor {
    fun<T : @Serializable Any> observe(key: String, type: KType): Flow<T?>

    fun<T : @Serializable Any> get(key: String, type: KType): T?

    fun<T : @Serializable Any> set(key: String, value: T)

    fun remove(key: String)
}
