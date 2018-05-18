package com.example.yonathan.ballandhole;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FragmentListener {
    FirstFragment firstFragment;
    SecondFragment secondFragment;
    FragmentManager fragmentmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.firstFragment= FirstFragment.newInstance();
        this.secondFragment= SecondFragment.newInstance();
        this.fragmentmanager= this.getSupportFragmentManager();
        this.changePage(2);


    }
    public void changePage(int page){
        FragmentTransaction ft= this.fragmentmanager.beginTransaction();
        if(page==1) {
            if(this.firstFragment.isAdded()){
                ft.show(this.firstFragment);
                this.firstFragment.onResume();
            }
            else {
                ft.add(R.id.fragment_container,this.firstFragment);
            }
            if(this.secondFragment.isAdded()){
                ft.hide(secondFragment);
            }
        }
        else if(page ==2) {
            if(this.secondFragment.isAdded()){
                ft.show(this.secondFragment);
            }
            else {
                ft.add(R.id.fragment_container,this.secondFragment);
            }
            if(this.firstFragment.isAdded()){
                ft.hide(firstFragment);
            }
        }
        ft.commit();
    }
}
