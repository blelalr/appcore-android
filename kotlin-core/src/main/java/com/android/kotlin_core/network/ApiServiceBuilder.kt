package com.android.kotlin_core.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiServiceBuilder {
    var apiService : ApiService

    init {

        val authInterceptor = Interceptor{
            val newUrl = it.request().url.newBuilder().addQueryParameter("api_key", ApiConstant.API_KEY_V3).build()
            val newRequest = it.request() .newBuilder() .url(newUrl) .build()
            it.proceed(newRequest)
        }

        val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(authInterceptor) .build()

        val retrofit = Builder()
                .baseUrl(ApiConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        apiService = retrofit.create(ApiService::class.java)
    }
}