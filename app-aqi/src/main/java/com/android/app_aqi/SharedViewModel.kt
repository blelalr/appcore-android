package com.android.app_aqi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.app_aqi.model.SiteModel

class SharedViewModel : ViewModel() {
    lateinit var siteList: List<SiteModel>
    var followedSiteList: MutableLiveData<List<SiteModel>> = MutableLiveData()
    var followedSet: MutableSet<String> = mutableSetOf()
    var currentPos = 0
}