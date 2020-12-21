package com.android.kotlin_core.model

import com.google.gson.annotations.SerializedName

data class GetAuthGuestSessionNewResponseData @JvmOverloads constructor(
        @field:SerializedName("success")
        var success: Boolean,
        @field:SerializedName("guest_session_id")
        var guest_session_id: String,
        @field:SerializedName("expires_at")
        var expires_at: String
)

