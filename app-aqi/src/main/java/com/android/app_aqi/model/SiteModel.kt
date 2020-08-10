package com.android.app_aqi.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "ALL_SITE")
data class SiteModel @JvmOverloads constructor(
        
        @PrimaryKey(autoGenerate = false)
        @field:SerializedName("SiteId")
        var siteId: String,
        
        @field:SerializedName("SiteName")
        var siteName: String? = null,

        @field:SerializedName("County")
        var county: String? = null,

        @field:SerializedName("AQI")
        var aQI: String? = null,

        @field:SerializedName("Longitude")
        var longitude: String? = null,

        @field:SerializedName("Latitude")
        var latitude: String? = null,
        
        @field:SerializedName("PublishTime")
        var publishTime: String? = null,

        @field:SerializedName("isFollow")
        var isFollow: Boolean? = null
) : Serializable