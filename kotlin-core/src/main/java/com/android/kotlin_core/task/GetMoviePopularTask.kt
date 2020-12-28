package com.android.kotlin_core.task

import com.android.kotlin_core.model.BaseRequestData
import com.android.kotlin_core.model.GetPopularMovieResponseData
import com.android.kotlin_core.network.ApiConstant
import com.android.kotlin_core.network.TaskService
import com.android.kotlin_core.util.AppConst
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GetMoviePopularTask : TaskService<BaseRequestData, GetPopularMovieResponseData>() {

    override fun getEndPoint(): String {
        return ApiConstant.GET_MOVIE_POPULAR
    }

    override fun getMethod(): AppConst.HttpMethodType {
        return AppConst.HttpMethodType.GET
    }

    override fun genResponse(): GetPopularMovieResponseData {
        val resultType= object : TypeToken<GetPopularMovieResponseData>() {}.type
        return Gson().fromJson(Gson().toJson(responseBody), resultType)
    }
}