package com.bricklytics.pokesphere.datalayer.network

import com.bricklytics.pokesphere.domainlayer.base.error.wrapper.ResultWrapper
import com.google.gson.Gson
import retrofit2.Response

object ApiResponseHandler {
    inline fun <SUCCESS : Any, reified ERROR : Any> dispatch(
        response: Response<SUCCESS>,
    ): ResultWrapper<SUCCESS, ERROR> {

        return if (response.isSuccessful) {
            ResultWrapper.Success(
                data = response.body() as SUCCESS
            )
        } else {
            ResultWrapper.Error(
                error = Gson().fromJson(
                    response.errorBody()!!.string(),
                    ERROR::class.java
                ),
            )
        }
    }
}