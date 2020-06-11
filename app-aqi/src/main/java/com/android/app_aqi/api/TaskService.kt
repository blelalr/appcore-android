package com.android.app_aqi.api

import com.android.app_aqi.model.RequestEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

abstract class TaskService {

    lateinit var siteId: String
    lateinit var params: Map<String, String>

    abstract fun onTaskSucceed(result: Any?)

    abstract fun onTaskFailed(error: Any?)

    open fun genParams(request: RequestEntity): Map<String, String>{
        return request.serializeToMap()
    }

    //convert a data class to a map
    private fun <T> T.serializeToMap(): Map<String, String> {
        return convert()
    }

    //convert a map to a data class
    inline fun <reified T> Map<String, String>.toDataClass(): T {
        return convert()
    }

    //convert an object of type I to type O
    inline fun <I, reified O> I.convert(): O {
        val gson = Gson()
        val json = gson.toJson(this)
        return gson.fromJson(json, object : TypeToken<O>() {}.type)
    }



}