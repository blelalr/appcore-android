package com.android.kotlin_core.network

import com.android.kotlin_core.model.BaseErrorData
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


class ApiServiceBuilder {
    var retrofit: Retrofit
    var apiService: ApiService

    init {
        val authInterceptor = Interceptor {
            val newUrl = it.request().url.newBuilder()
                    .addQueryParameter("api_key", ApiConstant.API_KEY_V3)
                    .addQueryParameter("language", ApiConstant.LANGUAGE)
                    .build()
            val newRequest = it.request().newBuilder().url(newUrl).build()
            it.proceed(newRequest)
        }

        val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(authInterceptor).build()

        retrofit = Builder()
                .baseUrl(ApiConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    fun parseError(response: Response<*>): BaseErrorData {
        val converter: Converter<ResponseBody, BaseErrorData> = retrofit
                .responseBodyConverter(BaseErrorData::class.java, arrayOfNulls<Annotation>(0))
        val errorData: BaseErrorData
        errorData = try {
            converter.convert(response.errorBody())!!
        } catch (e: IOException) {
            return BaseErrorData()
        }
        return errorData
    }
}