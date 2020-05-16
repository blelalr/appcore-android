package com.android.app_aqi.api

import com.android.app_aqi.RequestEntity
import com.android.app_aqi.model.BaseResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody

abstract class TaskService {
    lateinit var siteId: String
    lateinit var params: Map<String, Any>

    abstract fun onTaskSucceed(entity: BaseResponse?)

    abstract fun onTaskFailed(errorBody: ResponseBody?)

    open fun genParams(request: RequestEntity): Map<String, Any>{
        return request.serializeToMap()
    }

    //convert a data class to a map
    fun <T> T.serializeToMap(): Map<String, Any> {
        return convert()
    }

    //convert a map to a data class
    inline fun <reified T> Map<String, Any>.toDataClass(): T {
        return convert()
    }

    //convert an object of type I to type O
    inline fun <I, reified O> I.convert(): O {
        val gson = Gson()
        val json = gson.toJson(this)
        return gson.fromJson(json, object : TypeToken<O>() {}.type)
    }



}