package com.ecohub.heater.domain.model

sealed interface Response {
    data object Empty : Response
    data class Success(val metrics: Metrics): Response
    data class Conflict(
        val target: Metrics,
        val current: Metrics,
    ): Response
}
