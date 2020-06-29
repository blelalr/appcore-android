package com.android.app_aqi.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.android.app_aqi.model.SiteModel

class SiteViewPagerAdapter(private val siteList : List<SiteModel>, fm : FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return siteList.size
    }

    override fun getItem(position: Int): Fragment {
        return SiteFragment.newInstance(siteList[position])
    }
}