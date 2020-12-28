package com.android.kotlin_core.model

import com.google.gson.annotations.SerializedName

data class BaseRequestData @JvmOverloads constructor(
        @field:SerializedName("api_key")
        var api_key: String? = null
)