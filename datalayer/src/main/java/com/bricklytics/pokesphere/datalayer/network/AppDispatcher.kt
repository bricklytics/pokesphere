package com.bricklytics.pokesphere.datalayer.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AppDispatcher(val dispatcher: AppDispatchers)

enum class AppDispatchers {
    IO,
    MAIN,
    DEFAULT;
}