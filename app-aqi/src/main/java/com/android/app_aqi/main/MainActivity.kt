package com.android.app_aqi.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.app_aqi.R
import com.android.app_aqi.SharedViewModel
import com.android.app_aqi.home.HomeFragment
import com.android.app_aqi.list.ListFragment

class MainActivity : AppCompatActivity(), ListFragment.ListItemListener, HomeFragment.OnMenuItemClickListener {
    lateinit var sharedViewModel : SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        initData()
    }

    private fun initData() {
        sharedViewModel.getFollowSiteList().observe(this, Observer {
            sharedViewModel.followedSite = mutableListOf()
            sharedViewModel.followedSite = it
            replaceByHomeFragment(null)
        })
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
