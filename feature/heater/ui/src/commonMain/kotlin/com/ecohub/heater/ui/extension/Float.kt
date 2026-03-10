package com.ecohub.heater.ui.extension

import kotlin.math.pow
import kotlin.math.round

fun Float.round(decimals: Int): Float {
    val factor = 10f.pow(decimals)
    return round(this * factor) / factor
}
