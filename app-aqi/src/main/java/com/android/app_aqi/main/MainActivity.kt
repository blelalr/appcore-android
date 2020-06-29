package com.android.app_aqi.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.android.app_aqi.R
import com.android.app_aqi.SharedViewModel
import com.android.app_aqi.home.HomeFragment
import com.android.app_aqi.list.ListFragment
import com.android.app_aqi.room.AqiDatabase
import com.google.android.material.bottomappbar.BottomAppBar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
class MainActivity : AppCompatActivity(), ListFragment.OnListItemClickListener{
    private lateinit var bottomAppBar: BottomAppBar
    lateinit var sharedViewModel : SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
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
                    if(supportFragmentManager.findFragmentByTag(ListFragment::class.simpleName) == null)
                        replaceByListFragment()
                    else
                        onBackPressed()
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
            sharedViewModel.siteList = aqiDatabase.getAqiDao().getSiteList()
            replaceByHomeFragment(null)
        }
        aqiDatabase.close()
    }

    private fun replaceByListFragment() {
        supportFragmentManager.beginTransaction()
                .add(R.id.main_container, ListFragment.newInstance(), ListFragment::class.simpleName)
                .addToBackStack(ListFragment::class.simpleName)
                .commit()

    }

    private fun replaceByHomeFragment(itemView: View?) {
        if(itemView == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.main_container, HomeFragment.newInstance())
                    .commit()

        } else {
            supportFragmentManager
                    .beginTransaction()
                    .addSharedElement(itemView, itemView.transitionName)
                    .replace(R.id.main_container, HomeFragment.newInstance())
                    .addToBackStack(HomeFragment::class.simpleName)
                    .commit()
        }
    }

    override fun onListItemClick(itemView: View, position: Int) {
        sharedViewModel.currentPos = position
        replaceByHomeFragment(itemView)
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount != 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }

    }
}
