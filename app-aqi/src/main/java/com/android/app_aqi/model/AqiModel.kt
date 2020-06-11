package com.android.app_aqi.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "AQI")
data class AqiModel @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true)
    var id: Long,

    @field:SerializedName("MonitorDate")
    var monitorDate: String? = null,

    @field:SerializedName("SiteId")
    var siteId: String? = null,

    @field:SerializedName("SiteName")
    var siteName: String? = null,

    @field:SerializedName("ItemEngName")
    var itemEngName: String? = null,

    @field:SerializedName("ItemName")
    var itemName: String? = null,

    @field:SerializedName("Concentration")
    var concentration: String? = null,

    @field:SerializedName("County")
    var county: String? = null,

    @field:SerializedName("ItemId")
    var itemId: String? = null,

    @field:SerializedName("ItemUnit")
    var itemUnit: String? = null
)
