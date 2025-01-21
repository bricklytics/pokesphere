package com.bricklytics.pokesphere.datalayer.base.error

interface ErrorHandler {
    fun onFailure(errorDetails: ErrorDetailDTO)
}