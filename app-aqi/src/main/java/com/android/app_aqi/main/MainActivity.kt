package com.android.app_aqi.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.android.app_aqi.Constant
import com.android.app_aqi.R
import com.android.app_aqi.SharedViewModel
import com.android.app_aqi.home.HomeFragment
import com.android.app_aqi.list.ListFragment
import com.android.app_aqi.model.SiteModel
import com.android.app_aqi.room.AqiDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
class MainActivity : AppCompatActivity(), ListFragment.OnListItemClickListener, HomeFragment.OnMenuItemClickListener {
    lateinit var sharedViewModel : SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        initData()
    }

    private fun initData() {
        //find all follow site
        val sharedPreferences = getSharedPreferences(Constant.SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val setFromSharedPreferences = sharedPreferences.getStringSet(Constant.FOLLOWED_SITE_LIST, mutableSetOf())
        if(setFromSharedPreferences.isNullOrEmpty()) {
            val copyOfSet = setFromSharedPreferences!!.toMutableSet()
            copyOfSet.add("12")

            val editor = sharedPreferences.edit()
            editor.putStringSet(Constant.FOLLOWED_SITE_LIST, copyOfSet)
            editor.apply() // or commit() if really needed
        }

        sharedViewModel.followedSet = sharedPreferences.getStringSet(Constant.FOLLOWED_SITE_LIST, mutableSetOf())!!

        val aqiDatabase = Room.databaseBuilder(applicationContext!!, AqiDatabase::class.java, AqiDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
        GlobalScope.launch {
            var followList: MutableList<SiteModel>  = mutableListOf()
            for( followSiteId in sharedViewModel.followedSet) {
                followList.add(aqiDatabase.getAqiDao().getFollowSiteById(followSiteId))
            }
            sharedViewModel.followedSiteList.postValue(followList)
            sharedViewModel.siteList = aqiDatabase.getAqiDao().getSiteList()
        }
        aqiDatabase.close()

        replaceByHomeFragment(null)
    }

    private fun replaceByListFragment(itemView: View) {
        if(supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, ListFragment.newInstance())
                    .commit()
        }

    }

    private fun replaceByHomeFragment(itemView: View?) {
        if(itemView == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.main_container, HomeFragment.newInstance())
                    .commit()
        } else {
            if(supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            } else {
                supportFragmentManager
                        .beginTransaction()
                        .addSharedElement(itemView, itemView.transitionName)
                        .replace(R.id.main_container, HomeFragment.newInstance())
                        .addToBackStack(HomeFragment::class.simpleName)
                        .commit()
            }
        }
    }

    override fun onListItemClick(itemView: View, position: Int) {
        sharedViewModel.currentPos = position
        replaceByHomeFragment(itemView)
    }

    override fun onMenuItemClick(itemView: View, position: Int) {
        sharedViewModel.currentPos = position
        replaceByListFragment(itemView)
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
