package com.android.app_aqi.api

import com.android.app_aqi.model.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

@JvmSuppressWildcards
interface ApiService {

    @GET("{siteId}")
    suspend fun getAqi(@Path("siteId") siteId: String,
            @QueryMap params: Map<String, Any>
    ): Response<List<BaseResponse>>

}
