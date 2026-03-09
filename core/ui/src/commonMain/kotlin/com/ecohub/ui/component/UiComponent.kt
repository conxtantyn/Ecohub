package com.ecohub.ui.component

import com.ecohub.ui.extension.scopeOf
import org.koin.core.qualifier.TypeQualifier
import org.koin.core.scope.Scope

interface UiComponent {
    interface Builder

    abstract class ComponentBuilder(private val scope: Scope) : Builder {
        fun scope(qualifier: TypeQualifier): Scope {
            return scope.getKoin().scopeOf(qualifier).also { it.linkTo(scope) }
        }

        abstract fun build(): Scope
    }

    abstract class ComponentBuilderWithArgs<T>(protected val scope: Scope) : Builder {
        fun scope(qualifier: TypeQualifier): Scope {
            return scope.getKoin().scopeOf(qualifier).also { it.linkTo(scope) }
        }

        abstract fun build(args: T): Scope
    }
}
