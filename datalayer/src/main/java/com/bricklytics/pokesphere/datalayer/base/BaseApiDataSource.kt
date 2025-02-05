package com.bricklytics.pokesphere.datalayer.base

import com.bricklytics.pokesphere.datalayer.network.ApiResponseHandler
import com.bricklytics.pokesphere.datalayer.network.AppDispatcher
import com.bricklytics.pokesphere.datalayer.network.AppDispatchers
import com.bricklytics.pokesphere.domainlayer.base.error.wrapper.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

open class BaseApiDataSource @Inject constructor(
    @AppDispatcher(AppDispatchers.IO)
    val ioDispatcher: CoroutineDispatcher
) {
    protected suspend inline fun <SUCCESS: Any, reified ERROR: Any> safeApiCall(
        apiCall: () -> Response<SUCCESS>
    ): ResultWrapper<SUCCESS, ERROR> {

        val response = apiCall()

        return withContext(ioDispatcher) {
            ApiResponseHandler.dispatch(response)
        }
    }
}