package com.ecohub.ui.extension

fun String.format(vararg args: Any?): String {
    var formatted = this
    args.forEachIndexed { index, arg ->
        formatted = formatted.replace("{$index}", arg.toString())
    }
    return formatted
}
