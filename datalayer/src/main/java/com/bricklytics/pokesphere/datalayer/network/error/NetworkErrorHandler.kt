package com.bricklytics.pokesphere.datalayer.network.error

import com.bricklytics.pokesphere.datalayer.base.error.ErrorDetailDTO
import com.bricklytics.pokesphere.datalayer.base.error.ErrorHandler

open class NetworkErrorHandler : ErrorHandler {
    private var failure = ErrorDetailDTO()

    override fun onFailure(errorDetails: ErrorDetailDTO) {
        this.failure = errorDetails
    }

    fun getFailure(): ErrorDetailDTO {
        return failure
    }

    //criar um mecanismo de disparo de evento, e.g. LiveData, Flow, EventBus
}