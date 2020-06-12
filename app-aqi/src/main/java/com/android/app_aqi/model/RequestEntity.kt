package com.android.app_aqi.model

import com.google.gson.annotations.SerializedName

data class RequestEntity @JvmOverloads constructor(

        @field:SerializedName("\$top")
        var top: Int? = null,

        @field:SerializedName("\$skip")
        var skip: Int? = null,

        @SerializedName("\$format")
        var format: String,

        @SerializedName("\$orderby")
        var orderby: String
)


