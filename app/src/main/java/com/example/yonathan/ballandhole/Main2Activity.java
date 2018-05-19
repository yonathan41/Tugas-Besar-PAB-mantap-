package com.example.yonathan.ballandhole;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentListener {
    FirstFragment firstFragment;
    SecondFragment secondFragment;
    FragmentManager fragmentmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        this.firstFragment = FirstFragment.newInstance();
        this.secondFragment = SecondFragment.newInstance();
        this.fragmentmanager = this.getSupportFragmentManager();
        this.changePage(2);
    }

    public void changePage(int page) {
        FragmentTransaction ft = this.fragmentmanager.beginTransaction();
        if (page == 1) {
            if (this.firstFragment.isAdded()) {
                ft.show(this.firstFragment);
                this.firstFragment.onResume();
            } else {
                ft.add(R.id.fragment_container, this.firstFragment);
            }
            if (this.secondFragment.isAdded()) {
                ft.hide(secondFragment);
            }
        } else if (page == 2) {
            if (this.secondFragment.isAdded()) {
                ft.show(this.secondFragment);
            } else {
                ft.add(R.id.fragment_container, this.secondFragment);
            }
            if (this.firstFragment.isAdded()) {
                ft.hide(firstFragment);
            }
        }
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_score) {
            // Handle the camera action
        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_exit) {
            this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
