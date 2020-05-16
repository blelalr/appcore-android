package com.android.app_aqi

import com.google.gson.annotations.SerializedName

data class RequestEntity @JvmOverloads constructor(
    @field:SerializedName("top")
    var top : Int? = -1,
    @field:SerializedName("skip")
    var skip : Int? = -1,
    @SerializedName("format")
    var format: String
)


