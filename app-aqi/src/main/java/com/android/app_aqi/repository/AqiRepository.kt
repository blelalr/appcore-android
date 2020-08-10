package com.android.app_aqi.repository

import androidx.lifecycle.LiveData
import com.android.app_aqi.model.SiteModel

interface AqiRepository {

    fun getAllSiteList(): LiveData<List<SiteModel>>

    fun getAllFollowedSite(): LiveData<List<SiteModel>>

    fun followSite(site: String)

    fun unFollowSite(siteId: String)

    fun insertAqiData()

    fun isFollowed(siteId: String): Boolean

    fun updateFollowSiteList()

}