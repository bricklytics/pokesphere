package com.bricklytics.pokesphere.datalayer.network.interceptor


import com.bricklytics.pokesphere.datalayer.base.error.ErrorDetailDTO
import com.bricklytics.pokesphere.datalayer.network.error.NetworkErrorHandler
import com.google.gson.Gson
import io.ktor.http.HttpStatusCode
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import okio.BufferedSource
import okio.buffer
import okio.source
import timber.log.Timber

internal class BaseHttpInterceptor(
    private val errorHandler: NetworkErrorHandler
) : Interceptor {
    override fun intercept(
        chain: Interceptor.Chain
    ): Response = synchronized<Response>(this) {
        val request = chain.request()
        lateinit var response: Response

        request.runCatching {
            chain.proceed(this)
        }.fold(
            onFailure = { failure ->
                errorHandler.onFailure(
                    ErrorDetailDTO(
                        code = 0,
                        cause = failure.cause.toString(),
                        message = failure.message.orEmpty()
                    )
                )
                Timber.e(
                    "Network Error - ${failure.cause}: ${failure.message}"
                )
            },
            onSuccess = { success ->
                if (success.code in 400..599) {
                    errorHandler.onFailure(
                        ErrorDetailDTO(
                            code = success.code,
                            cause = success.message,
                            message = success.headers.joinToString() +
                                    success.body?.string().orEmpty()
                        )
                    )
                    Timber.e(
                        "Http Error ${success.code} - " +
                                HttpStatusCode.fromValue(success.code).description
                    )
                }
                response = success
            }
        )

        if (errorHandler.anyFailure()) {
            val errorJson = Gson().toJson(
                errorHandler.getFailure(),
                ErrorDetailDTO::class.java
            )
            return response.newBuilder()
                .body(
                    CustomResponseBody(
                        body = errorJson,
                        originalBody = response.body!!
                    )
                ).build()
        }

        return response
    }
}

private class CustomResponseBody(
    private val body: String,
    private val originalBody: ResponseBody
) : ResponseBody() {
    override fun contentLength() = body.length.toLong()
    override fun contentType(): MediaType? = originalBody.contentType()
    override fun source(): BufferedSource = body.byteInputStream().source().buffer()
}

