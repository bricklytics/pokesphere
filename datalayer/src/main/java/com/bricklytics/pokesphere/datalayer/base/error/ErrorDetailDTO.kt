package com.bricklytics.pokesphere.datalayer.base.error

import com.bricklytics.pokesphere.domainlayer.base.error.model.ErrorDetailModel
import com.google.gson.annotations.SerializedName

data class ErrorDetailDTO(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("cause")
    val cause: String = "",
    @SerializedName("message")
    val message: String = "",
) {
    fun mapTo() = ErrorDetailModel(
        code = this.code,
        body = this.cause,
        message = this.message
    )
}
