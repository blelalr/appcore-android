package com.android.app_aqi.model

import com.google.gson.annotations.SerializedName

data class BaseResponse @JvmOverloads constructor(

    @field:SerializedName("MonitorDate")
    val monitorDate: String? = null,

    @field:SerializedName("SiteId")
    val siteId: String? = null,

    @field:SerializedName("SiteName")
    val siteName: String? = null,

    @field:SerializedName("ItemEngName")
    val itemEngName: String? = null,

    @field:SerializedName("ItemName")
    val itemName: String? = null,

    @field:SerializedName("Concentration")
    val concentration: String? = null,

    @field:SerializedName("County")
    val county: String? = null,

    @field:SerializedName("ItemId")
    var itemId: String? = null,

    @field:SerializedName("ItemUnit")
    val itemUnit: String? = null
)
