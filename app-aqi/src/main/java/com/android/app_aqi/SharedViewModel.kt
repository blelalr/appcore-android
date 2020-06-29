package com.android.app_aqi

import androidx.lifecycle.ViewModel
import com.android.app_aqi.model.SiteModel

class SharedViewModel : ViewModel() {
    lateinit var siteList: List<SiteModel>
    var currentPos = 0
}