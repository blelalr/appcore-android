package com.android.nav_components

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController
    private lateinit var appBarConfig: AppBarConfiguration
    private lateinit var toolBar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        setUpNavigation()
    }

    private fun initView() {
        toolBar = findViewById(R.id.toolbar)
        bottomNavigationView = findViewById(R.id.bottom_nav)
    }

    private fun setUpNavigation() {
        navController = Navigation.findNavController(this, R.id.home_nav_host_fragment)
        appBarConfig = AppBarConfiguration.Builder(R.id.homeFragment, R.id.exploreFragment, R.id.memberFragment).build()
        NavigationUI.setupWithNavController(toolBar, navController, appBarConfig)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return (NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item))
    }
}
