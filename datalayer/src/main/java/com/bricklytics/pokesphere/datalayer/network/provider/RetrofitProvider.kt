package com.bricklytics.pokesphere.datalayer.network.provider

import com.bricklytics.pokesphere.datalayer.BuildConfig.BASE_URL
import com.bricklytics.pokesphere.datalayer.base.BaseApiServicesFacade
import com.bricklytics.pokesphere.datalayer.network.error.NetworkErrorHandler
import com.bricklytics.pokesphere.datalayer.network.interceptor.BaseHttpInterceptor
import com.bricklytics.pokesphere.datalayer.network.interceptor.HttpLoggingInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitProvider : BaseApiServicesFacade<Retrofit> {

    private val errorHandler = NetworkErrorHandler()

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(BaseHttpInterceptor(errorHandler))
            .addInterceptor(HttpLoggingInterceptor())
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                        .create()
                )
            ).baseUrl(BASE_URL)
            .build()
    }

    override fun provideApiService(): Retrofit {
        val okHttpClient = provideOkHttpClient()
        val retrofit = provideRetrofit(okHttpClient)

        return retrofit
    }
}