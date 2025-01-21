package com.bricklytics.pokesphere.domainlayer.base.usecase

abstract class BaseUseCase<T> {

    abstract suspend fun fetch(args: Map<String, Any> = emptyMap()) : T

    open suspend fun <T> runAsync(
        run: suspend () -> T
    ): T { return run() }

    open fun runSync(
        run: () -> T
    ): T { return run() }
}