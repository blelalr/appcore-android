package com.android.app_aqi.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "AQI")
data class SiteModel @JvmOverloads constructor(

        @field:SerializedName("SiteName")
        val siteName: String? = null,

        @field:SerializedName("County")
        val county: String? = null,

        @field:SerializedName("AQI")
        val aQI: String? = null,

        @field:SerializedName("Longitude")
        val longitude: String? = null,

        @field:SerializedName("Latitude")
        val latitude: String? = null,

        @field:SerializedName("SiteId")
        val siteId: String? = null
) : Serializable