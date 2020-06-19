package com.android.app_aqi.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "AQI")
data class AqiModel @JvmOverloads constructor(

        @PrimaryKey(autoGenerate = false)
        var id: Long,

        @field:SerializedName("SiteName")
        val siteName: String? = null,

        @field:SerializedName("County")
        val county: String? = null,

        @field:SerializedName("AQI")
        val aQI: String? = null,

        @field:SerializedName("Pollutant")
        val pollutant: String? = null,

        @field:SerializedName("Status")
        val status: String? = null,

        @field:SerializedName("SO2")
        val sO2: String? = null,

        @field:SerializedName("CO")
        val cO: String? = null,

        @field:SerializedName("CO_8hr")
        val cO8hr: String? = null,

        @field:SerializedName("O3")
        val o3: String? = null,

        @field:SerializedName("O3_8hr")
        val o38hr: String? = null,

        @field:SerializedName("PM10")
        val pM10: String? = null,

        @field:SerializedName("PM2.5")
        val pM25: String? = null,

        @field:SerializedName("NO2")
        val nO2: String? = null,

        @field:SerializedName("NOx")
        val nOx: String? = null,

        @field:SerializedName("NO")
        val nO: String? = null,

        @field:SerializedName("WindSpeed")
        val windSpeed: String? = null,

        @field:SerializedName("WindDirec")
        val windDirec: String? = null,

        @field:SerializedName("PublishTime")
        val publishTime: String? = null,

        @field:SerializedName("PM2.5_AVG")
        val pM25AVG: String? = null,

        @field:SerializedName("PM10_AVG")
        val pM10AVG: String? = null,

        @field:SerializedName("SO2_AVG")
        val sO2AVG: String? = null,

        @field:SerializedName("Longitude")
        val longitude: String? = null,

        @field:SerializedName("Latitude")
        val latitude: String? = null,

        @field:SerializedName("SiteId")
        val siteId: String? = null
) : Serializable
