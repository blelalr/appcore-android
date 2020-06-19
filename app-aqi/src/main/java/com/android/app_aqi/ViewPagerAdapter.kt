package com.android.app_aqi

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.app_aqi.main.SiteFragment
import com.android.app_aqi.model.AqiModel
import com.android.app_aqi.model.SiteModel

class ViewPagerAdapter(activity: AppCompatActivity, val siteList : List<SiteModel>) :FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return siteList.size
    }

    override fun createFragment(position: Int): Fragment {
        return SiteFragment.newInstance(siteList[position])
    }
}