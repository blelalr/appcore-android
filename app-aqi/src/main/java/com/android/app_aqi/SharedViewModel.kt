package com.android.app_aqi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.app_aqi.model.SiteModel
import com.android.app_aqi.repository.AqiRepository
import com.android.app_aqi.repository.AqiRepositoryImpl

class SharedViewModel(private val repositoryImpl: AqiRepository = AqiRepositoryImpl()) : ViewModel() {
//    var siteList: List<SiteModel> = repositoryImpl.getAllFollowedSite()
//    var followedSiteList: MutableLiveData<List<SiteModel>> = MutableLiveData()
//    var followedSet: MutableSet<String> = mutableSetOf()
    var currentPos = 0
    fun getAllFollowSiteList() : LiveData<List<SiteModel>> {
        return repositoryImpl.getAllFollowedSite()
    }

    fun getAllSiteList(): List<SiteModel> {
        return repositoryImpl.getAllSiteList()
    }

    fun isFollow(siteId: String): Boolean {
        return repositoryImpl.isFollow(siteId)
    }
}