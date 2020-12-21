package com.android.kotlin_core.util

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type

internal class Data<T> {
    @SerializedName("data")
    private val details: List<T>? = null
    fun getDetail(typeOfT: Type?): List<T> {
        val jsonArray: JsonArray = Gson().toJsonTree(details).getAsJsonArray()
        return jsonToObject(jsonArray.toString(), typeOfT)
    }

    private fun jsonToObject(jsonText: String, typeOfT: Type?): List<T> {
        val gson = Gson()
        return gson.fromJson(jsonText, typeOfT)
    }
}