package com.bricklytics.pokesphere.datalayer.network.error

import com.bricklytics.pokesphere.datalayer.base.error.ErrorDetailDTO
import com.bricklytics.pokesphere.datalayer.base.error.ErrorHandler

open class NetworkErrorHandler : ErrorHandler {
    private var failure: ErrorDetailDTO? = null

    override fun onFailure(errorDetails: ErrorDetailDTO) {
        this.failure = errorDetails
    }

    fun getFailure(): ErrorDetailDTO? {
        return failure
    }

    fun anyFailure(): Boolean = failure != null
}