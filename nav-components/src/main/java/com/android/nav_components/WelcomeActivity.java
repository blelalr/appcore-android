package com.android.nav_components;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WelcomeActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private AppBarConfiguration appBarConfig;
    private Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
        setUpNavigation();
    }

    private void initView() {
        toolBar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottom_nav);
    }

    public void setUpNavigation(){

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        appBarConfig = new AppBarConfiguration.Builder(R.id.nav_home_fragment, R.id.nav_explore_fragment, R.id.nav_member_fragment).build();
        NavigationUI.setupWithNavController(toolBar, navController, appBarConfig);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }

}
