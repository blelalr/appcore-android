package com.android.app_aqi

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.app_aqi.model.SiteModel
import com.android.app_aqi.repository.AqiRepository
import com.android.app_aqi.repository.AqiRepositoryImpl

class SharedViewModel(private val repositoryImpl: AqiRepository = AqiRepositoryImpl()) : ViewModel() {
    var currentPos = 0
    fun getAllFollowSiteList() : LiveData<List<SiteModel>> {
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

    fun updateFollowSiteList() {
        getAllFollowSiteList()
    }

}