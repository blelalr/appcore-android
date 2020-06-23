package com.android.app_aqi.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.room.Room
import com.android.app_aqi.R
import com.android.app_aqi.home.HomeFragment
import com.android.app_aqi.list.ListFragment
import com.android.app_aqi.model.SiteModel
import com.android.app_aqi.room.AqiDatabase
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
class MainActivity : AppCompatActivity(), ListFragment.OnListFragmentInteractionListener{
    private lateinit var bottomAppBar: BottomAppBar
    lateinit var siteList: List<SiteModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomAppBar = findViewById(R.id.bottom_app_bar)
    }

    override fun onResume() {
        super.onResume()
        initData()
        initAppbar()
    }

    private fun initAppbar() {
        bottomAppBar.replaceMenu(R.menu.bottom_menu)
        bottomAppBar.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.show_list -> {
                    replaceByListFragment()
                    true
                }
                else -> false
            }
        }
    }

    private fun initData() {
        val aqiDatabase = Room.databaseBuilder(applicationContext!!, AqiDatabase::class.java, AqiDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
        GlobalScope.launch {
            siteList = aqiDatabase.getAqiDao().getSiteList()
        }
        replaceByHomeFragment()
        aqiDatabase.close()
    }

    private fun replaceByHomeFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, HomeFragment.newInstance(0), HomeFragment::class.simpleName)
                .commit()
    }

    private fun replaceByListFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, ListFragment.newInstance() , ListFragment::class.simpleName)
                .commit()

    }

    private fun replaceByHomeFragment(itemView: View, position: Int) {

        // 步驟1：點下圖片後，設定該圖片的TransitionName。
        itemView.transitionName = "shared_element_container"
//        // 步驟3：設定動畫
//        val materialContainerTransform = MaterialContainerTransform()
//        fragment.sharedElementEnterTransition = materialContainerTransform
        // 步驟4：開啟Fragment，設定ShareElement

        supportFragmentManager
                .beginTransaction()
                .addSharedElement(itemView, itemView.transitionName)
                .replace(R.id.main_container, HomeFragment.newInstance(position))
                .commit()

    }

    override fun onFragmentInteraction(itemView: View, position: Int) {
        replaceByHomeFragment(itemView, position)
    }


}
