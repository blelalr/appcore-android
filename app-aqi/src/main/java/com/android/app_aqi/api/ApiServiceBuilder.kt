package com.android.app_aqi.api

import com.android.app_aqi.Constant
import com.google.gson.GsonBuilder
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceBuilder {

    companion object {
        val retrofit : ApiService = Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build().create(ApiService::class.java)

    }

}