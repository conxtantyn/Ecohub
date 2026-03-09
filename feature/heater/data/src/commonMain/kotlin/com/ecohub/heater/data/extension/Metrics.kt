package com.ecohub.heater.data.extension

import com.ecohub.common.interactor.PersistenceInteractor
import com.ecohub.heater.data.mapper.mapToDomain
import com.ecohub.heater.data.model.MetricsModel
import com.ecohub.heater.domain.model.Metrics
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.typeOf

fun PersistenceInteractor.getMetrics(key: String): MetricsModel? {
    return get(
        key = key,
        type = typeOf<MetricsModel>()
    )
}

fun PersistenceInteractor.getMetricsOrDefault(
    key: String, default: Metrics
): Metrics {
    return getMetrics(key)?.mapToDomain() ?: default
}

fun PersistenceInteractor.observeMetrics(
    key: String
): Flow<MetricsModel?> {
    return observe(
        key = key,
        type = typeOf<MetricsModel?>()
    )
}
