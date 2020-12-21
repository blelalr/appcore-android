package com.android.kotlin_core.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiServiceBuilder {
    var apiService : ApiService

    init {
        val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()

        val retrofit = Builder()
                .baseUrl(ApiConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .client(okHttpClient)
                .build()

        apiService = retrofit.create(ApiService::class.java)
    }
}