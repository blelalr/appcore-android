package com.android.app_aqi.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "FollowedSite")
data class SiteModel @JvmOverloads constructor(

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

        @PrimaryKey(autoGenerate = false)
        @field:SerializedName("SiteId")
        var siteId: String? = null,

        @field:SerializedName("PublishTime")
        var publishTime: String? = null
) : Serializable