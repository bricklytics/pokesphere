package com.bricklytics.pokesphere.datalayer.network.provider

import com.bricklytics.pokesphere.datalayer.BuildConfig.BASE_URL
import com.bricklytics.pokesphere.datalayer.base.BaseApiServicesFacade
import com.bricklytics.pokesphere.datalayer.network.error.NetworkErrorHandler
import com.bricklytics.pokesphere.datalayer.network.interceptor.BaseNetworkHttpInterceptor
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.util.concurrent.TimeUnit

object KtorProvider : BaseApiServicesFacade<HttpClient> {

    private val errorHandler = NetworkErrorHandler()
    private val interceptor = BaseNetworkHttpInterceptor(errorHandler)

    override fun provideApiService(): HttpClient = HttpClient(OkHttp) {
        expectSuccess = true

        defaultRequest {
            url(BASE_URL)
        }

        engine {
            config {
                followRedirects(true)
                addInterceptor(interceptor)
                connectTimeout(60, TimeUnit.SECONDS)
                readTimeout(60, TimeUnit.SECONDS)
                writeTimeout(60, TimeUnit.SECONDS)
                build()
            }
        }

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
            })
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Timber.v("Ktor =>", message)
                }

            }
            level = LogLevel.ALL
        }

        install(ResponseObserver) {
            onResponse { response ->
                Timber.d("HTTP status:", "${response.status.value}")
            }
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }
}