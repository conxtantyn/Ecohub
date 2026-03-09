package com.ecohub.common.exception

open class DomainException(
    val status: String,
    reason: String?,
) : Throwable(reason)
