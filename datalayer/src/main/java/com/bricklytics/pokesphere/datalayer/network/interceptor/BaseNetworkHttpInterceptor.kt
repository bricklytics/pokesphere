package com.bricklytics.pokesphere.datalayer.network.interceptor


import com.bricklytics.pokesphere.datalayer.base.error.ErrorDetailDTO
import com.bricklytics.pokesphere.datalayer.base.error.ErrorHandler
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.HttpException
import timber.log.Timber

internal class BaseNetworkHttpInterceptor(
    private val errorHandler: ErrorHandler
) : Interceptor {
    override fun intercept(
        chain: Interceptor.Chain
    ): Response = synchronized<Response>(this) {
        val request = chain.request()

        val response = request.runCatching {
            chain.proceed(this)
        }.getOrElse { failure ->
            when(failure.cause) {
                is HttpException -> {
                    val errorCode = (failure.cause as HttpException).code()
                    errorHandler.onFailure(
                        ErrorDetailDTO(
                            code = errorCode,
                            cause = failure.cause.toString(),
                            message = failure.message.orEmpty()
                        )
                    )
                    Timber.e(failure.cause, "Error: $errorCode - ${failure.message.orEmpty()}")
                }
                else -> {
                    errorHandler.onFailure(
                        ErrorDetailDTO(
                            code = 0,
                            cause = failure.cause.toString(),
                            message = failure.message.orEmpty()
                        )
                    )
                    Timber.e(failure.cause, failure.message)
                }
            }
            Response.Builder().code(0).build()
        }

        response.run {
           if(code == 0) errorHandler.onFailure(ErrorDetailDTO())
        }

        Timber.d(response.toString())

        return response
    }
}

