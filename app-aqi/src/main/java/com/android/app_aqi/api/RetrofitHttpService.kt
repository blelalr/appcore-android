package com.android.app_aqi.api

import com.android.app_aqi.model.BaseResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface RetrofitHttpService {

    @GET
    fun get(@HeaderMap headers: Map<String, String>, @Url url: String,
            @QueryMap params: Map<String, Object>): Deferred<Response<BaseResponse>>
}
