package com.ecohub.ui.factory

import com.ecohub.ui.component.UiComponent
import com.ecohub.ui.component.UiComponentProvider
import com.ecohub.ui.exception.UiBuilderException
import kotlin.reflect.KClass

class UiBuilderFactory(private val builders: List<UiComponent.Builder>) : UiComponentProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : UiComponent.Builder> builder(clazz: KClass<T>): T {
        return builders.find { clazz.isInstance(it) } as? T
            ?: throw UiBuilderException("No builder found for ${clazz.qualifiedName}")
    }
}
