package com.android.app_aqi

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.app_aqi.model.SiteModel
import com.android.app_aqi.repository.AqiRepository
import com.android.app_aqi.repository.AqiRepositoryImpl

class SharedViewModel(private val repositoryImpl: AqiRepository = AqiRepositoryImpl()) : ViewModel() {
    var currentPos = 0
    lateinit var allFollowedSite :LiveData<List<SiteModel>>
    lateinit var allSite :LiveData<List<SiteModel>>
    fun getAllFollowSiteList() : LiveData<List<SiteModel>> {
        allFollowedSite = repositoryImpl.getAllFollowedSite()
        return allFollowedSite
    }

    fun getAllSiteList() : LiveData<List<SiteModel>> {
        allSite = repositoryImpl.getAllSiteList()
        return allSite
    }

    fun followSite(siteId: String) {
        repositoryImpl.followSite(siteId)
    }

    fun unFollowSite(siteId: String) {
        repositoryImpl.unFollowSite(siteId)
    }

}