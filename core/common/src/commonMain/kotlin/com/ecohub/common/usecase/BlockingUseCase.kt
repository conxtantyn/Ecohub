package com.ecohub.common.usecase

interface BlockingUseCase<T> : Usecase {
    operator fun invoke(): T
}
