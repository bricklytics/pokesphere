package com.bricklytics.pokesphere.datalayer.base

interface BaseApiServicesFacade <T> {
    fun provideApiService() : T
}