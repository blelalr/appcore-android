package com.android.app_aqi

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.app_aqi.model.AqiModel
import com.android.app_aqi.model.SiteModel
import com.android.app_aqi.repository.AqiRepository
import com.android.app_aqi.repository.AqiRepositoryImpl

class SharedViewModel(private val repositoryImpl: AqiRepository = AqiRepositoryImpl()) : ViewModel() {
    var currentPos = 0
    lateinit var allSite :List<SiteModel>
    lateinit var followedSite :List<SiteModel>
    fun getFollowSiteList() : LiveData<List<SiteModel>> {
        return repositoryImpl.getAllFollowedSite()
    }

    fun getAllSiteList() : LiveData<List<SiteModel>> {
        return repositoryImpl.getAllSiteList()
    }

    fun followSite(siteId: String) {
        repositoryImpl.followSite(siteId)
    }

    fun unFollowSite(siteId: String) {
        repositoryImpl.unFollowSite(siteId)
    }

    fun getLast12HourAqiDataBySiteId(siteId: String) : LiveData<List<AqiModel>>{
        return repositoryImpl.getLast12HourAqiDataBySiteId(siteId)
    }

}