package com.android.kotlin_core.util

import com.google.gson.Gson
import java.lang.reflect.Type
open class Util {
    companion object {
        fun <T> jsonToObject(jsonText: String?, typeOfT: Type?): T {
            val gson = Gson()
            return gson.fromJson(jsonText, typeOfT)
        }
    }
}