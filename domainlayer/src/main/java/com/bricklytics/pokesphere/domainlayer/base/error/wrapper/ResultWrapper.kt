package com.bricklytics.pokesphere.domainlayer.base.error.wrapper

class ResultWrapper<SUCCESS, ERROR> (
    val success: SUCCESS? = null,
    val error: ERROR? = null,
    val headers: MutableMap<String, String> = mutableMapOf(),
    val code: Int = 0,
) {

    inline fun <TO_SUCCESS> transformSuccess(
        mapperFunction: (originalSuccess: SUCCESS) -> TO_SUCCESS
    ): ResultWrapper<TO_SUCCESS, ERROR> {
        return ResultWrapper(
            success = this.success?.let{ mapperFunction(it) },
            error = this.error,
            headers = this.headers,
            code = this.code
        )
    }

    inline fun <TO_ERROR> transformError(
        mapperFunction: (originalError: ERROR) -> TO_ERROR
    ): ResultWrapper<SUCCESS, TO_ERROR> {
        return ResultWrapper(
            error = this.error?.let { mapperFunction(it) },
            success = this.success,
            headers = this.headers,
            code = this.code
        )
    }

    fun isSuccess() = this.success != null

    companion object {

        fun <SUCCESS, ERROR> createSuccess(
            success: SUCCESS,
            code: Int = 0
        ) = ResultWrapper<SUCCESS, ERROR>(
            success = success,
            code = code
        )

        fun <SUCCESS, ERROR> createError(
            error: ERROR,
            code: Int = 0,
        ) = ResultWrapper<SUCCESS, ERROR>(
            error = error,
            code = code
        )
    }
}