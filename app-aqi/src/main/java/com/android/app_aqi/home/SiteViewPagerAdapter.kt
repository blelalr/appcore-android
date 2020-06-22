package com.android.app_aqi.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.app_aqi.model.SiteModel

class SiteViewPagerAdapter(activity: FragmentActivity, val siteList : List<SiteModel>) :FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return SiteFragment.newInstance(siteList[position])
    }
}