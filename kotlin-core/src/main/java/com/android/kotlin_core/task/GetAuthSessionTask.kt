package com.android.kotlin_core.task
import com.android.kotlin_core.model.BaseRequestData
import com.android.kotlin_core.model.GetAuthSessionResponseData
import com.android.kotlin_core.network.ApiConstant
import com.android.kotlin_core.network.TaskService
import com.android.kotlin_core.util.AppConst
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

open class GetAuthSessionTask : TaskService<BaseRequestData, GetAuthSessionResponseData>() {

    override fun getEndPoint(): String {
        return ApiConstant.GET_AUTH_GUEST_SESSION_NEW
    }

    override fun getMethod(): AppConst.HttpMethodType {
        return AppConst.HttpMethodType.GET
    }

    override fun genResponse(): GetAuthSessionResponseData {
        val resultType= object : TypeToken<GetAuthSessionResponseData>() {}.type
        return Gson().fromJson(Gson().toJson(responseBody), resultType)
    }

}