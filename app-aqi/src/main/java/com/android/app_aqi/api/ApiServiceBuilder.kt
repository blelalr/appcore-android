package com.android.app_aqi.api

import com.android.app_aqi.Constant
import com.android.app_aqi.model.AqiModel
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.QueryMap
import java.util.concurrent.TimeUnit


class ApiServiceBuilder {
    private val apiService : ApiService

    init {

        val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()

        val retrofit = Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .client(okHttpClient)
                .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    suspend fun get(@QueryMap params: Map<String, String>): Response<List<AqiModel>> {
        return apiService.get(params)
    }
}