package com.bricklytics.pokesphere.domainlayer.base.error.model

data class ErrorDetailModel(
    val code: Int = 0,
    val body: String = "",
    val message: String = ""
)
