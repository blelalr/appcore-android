package com.android.kotlin_core.network

import com.android.kotlin_core.util.AppConst.HttpMethodType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

abstract class TaskService<Request, Response> {

    var requestData: Request? = null

    var responseBody: Any? = null

    abstract fun getEndPoint(): String

    abstract fun getMethod(): HttpMethodType

    abstract fun genResponse() : Response

    open fun genQueryMap(): Map<String, String> {
        return requestData.serializeToMap()
    }

    //convert a data class to a map
    private fun <D> D.serializeToMap(): Map<String, String> {
        return convert()
    }

    //convert a map to a data class
    private inline fun <reified D> Map<String, String>.toDataClass(): D {
        return convert()
    }

    //convert an object of type I to type O
    inline fun <I, reified O> I.convert(): O {
        val gson = Gson()
        val json = gson.toJson(this)
        return gson.fromJson(json, object : TypeToken<O>() {}.type)
    }

}