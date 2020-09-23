package com.android.app_aqi.api

import com.android.app_aqi.model.AqiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

@JvmSuppressWildcards
interface ApiService {

    @GET("webapi/Data/REWIQA")
    suspend fun get(@QueryMap params: Map<String, String>): Response<List<AqiModel>>

}
