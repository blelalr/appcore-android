package com.android.kotlin_core.model

import com.google.gson.annotations.SerializedName

data class BaseErrorData(
        @field:SerializedName("status_code")
        var statusCode: Int = -1,
        @field:SerializedName("status_message")
        var statusMessage: String = "",
        @field:SerializedName("success")
        var success: Boolean = false
)


