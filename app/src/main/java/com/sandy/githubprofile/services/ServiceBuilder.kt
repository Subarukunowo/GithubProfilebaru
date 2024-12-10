package com.sandy.githubprofile.services

import com.airbnb.lottie.BuildConfig
import com.sandy.githubprofile.helpers.Config
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceBuilder {

    // Create HttpLoggingInterceptor based on the build type
    private val loggingInterceptor = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    } else {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }

    // Create OkHttpClient with timeout and logging interceptor
    private val okHttp: OkHttpClient.Builder = OkHttpClient.Builder()
        .callTimeout(5, TimeUnit.SECONDS)  // Adjust the timeout duration
        .addInterceptor(loggingInterceptor)

    // Create Retrofit Builder
    private val builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(Config.BASE_URL)  // Base URL from Config
        .addConverterFactory(GsonConverterFactory.create())  // Convert JSON to Kotlin objects
        .client(okHttp.build())  // Use OkHttp client with interceptor

    // Create Retrofit instance
    private val retrofit: Retrofit = builder.build()

    // Build and return the Retrofit service
    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }
}
