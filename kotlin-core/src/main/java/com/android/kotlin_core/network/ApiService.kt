package com.android.kotlin_core.network

import retrofit2.Response
import retrofit2.http.*

@JvmSuppressWildcards
interface ApiService {

    @Headers(
        "Content-Type:application/json",
        "Key:value"
    )

    @GET
    suspend fun get(@Url url: String, @QueryMap request: Map<String, String>): Response<Any>

    @POST
    suspend fun post(@Url url: String, @Body request: Any?): Response<Any>

}
