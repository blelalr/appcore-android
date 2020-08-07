package com.android.app_aqi.repository

import androidx.lifecycle.LiveData
import com.android.app_aqi.model.SiteModel

interface AqiRepository {

    fun getAllSiteList(): List<SiteModel>

    fun getAllFollowedSite(): LiveData<List<SiteModel>>

    fun followSite(site: SiteModel)

    fun unFollowSite(siteId: Int)

    fun insertAqiData()

    fun isFollow(siteId: String): Boolean

}