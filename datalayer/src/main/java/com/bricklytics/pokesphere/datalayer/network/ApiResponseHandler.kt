package com.bricklytics.pokesphere.datalayer.network

import com.bricklytics.pokesphere.domainlayer.base.error.wrapper.ResultWrapper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import retrofit2.Response

object ApiResponseHandler {
    inline fun <SUCCESS, reified ERROR> dispatch(
        response: Response<SUCCESS>,
    ): ResultWrapper<SUCCESS, ERROR> {

        return if (response.isSuccessful) {
            ResultWrapper(
                success = response.body(),
                headers = getHeaders(response.headers()),
                code = response.code()
            )
        } else {
            ResultWrapper(
                error = Gson().fromJson(
                    """
                        {
                            "code": "${response.code()}",
                            "body": "${response.errorBody()?.string()}",
                            "message": "${response.message()}"
                        }
                        """,
                    object : TypeToken<ERROR>() {}.type
                ),
                headers = getHeaders(response.headers()),
                code = response.code()
            )
        }
    }

    fun getHeaders(headers: Headers): MutableMap<String, String> {
        val keyValueMap = headers.names().associateWith { headerKey ->
            headers[headerKey].orEmpty()
        }.toMutableMap()

        return keyValueMap
    }

}