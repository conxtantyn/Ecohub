package com.ecohub.common.usecase

interface BlockingWithArgsUseCase<A, T> : Usecase {
    operator fun invoke(args: A): T
}
