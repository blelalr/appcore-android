package com.android.app_aqi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import androidx.viewpager2.widget.ViewPager2
import com.android.app_aqi.model.AqiModel
import com.android.app_aqi.model.SiteModel
import com.android.app_aqi.room.AqiDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var siteList: List<SiteModel>
    private lateinit var siteViewPager: ViewPager2
    private lateinit var siteViewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
    }

    private fun initData() {
        val aqiDatabase = Room.databaseBuilder(applicationContext, AqiDatabase::class.java, AqiDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
        GlobalScope.launch {
            siteList = aqiDatabase.getAqiDao().getSiteList()
            initViewPager()
        }
        aqiDatabase.close()
    }

    private fun initViewPager() {
        siteViewPager = findViewById(R.id.vp_main)
        siteViewPagerAdapter = ViewPagerAdapter(this, siteList)
        siteViewPager.adapter = siteViewPagerAdapter
//        siteViewPager.setPageTransformer(mAnimator)
    }

//    private val mAnimator = ViewPager2.PageTransformer { page, position ->
//        val absPos = Math.abs(position)
//        page.apply {
//            translationY = absPos * 500f
//            translationX = 0f
//        }
//    }

}
