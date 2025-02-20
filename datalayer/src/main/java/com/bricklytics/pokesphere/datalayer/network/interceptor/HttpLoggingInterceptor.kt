package com.bricklytics.pokesphere.datalayer.network.interceptor

import kotlinx.io.IOException
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class HttpLoggingInterceptor: Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        Timber.i("---> ${request.method} - ${request.url}")
        Timber.i("headers: ${request.headers}")

        val response = chain.proceed(request)

        Timber.i("<--- (${response.code}) ${response.request.url}")
        Timber.i("headers: ${response.headers}")

        val responseBody = response.peekBody(Long.MAX_VALUE).string()
        Timber.i("body: $responseBody")

        return response
    }
}