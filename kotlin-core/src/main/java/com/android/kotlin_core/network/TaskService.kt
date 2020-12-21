package com.android.kotlin_core.network

import com.android.kotlin_core.util.AppConst.HttpMethodType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

abstract class TaskService<PostData, ResponseData> {
    abstract var postData: PostData
    
    abstract fun onTaskSucceed(result: ResponseData)

    abstract fun onTaskFailed(error: String)

    abstract fun getEndPoint(): String

    abstract fun getMethod(): HttpMethodType

    open fun genPostData(): Map<String, String>{
        return postData.serializeToMap()
    }

    //convert a data class to a map
    private fun <PostData> PostData.serializeToMap(): Map<String, String> {
        return convert()
    }

    //convert a map to a data class
    inline fun <reified ResponseData> Map<String, String>.toDataClass(): ResponseData {
        return convert()
    }

    //convert an object of type I to type O
    inline fun <I, reified O> I.convert(): O {
        val gson = Gson()
        val json = gson.toJson(this)
        return gson.fromJson(json, object : TypeToken<O>() {}.type)
    }
    
}