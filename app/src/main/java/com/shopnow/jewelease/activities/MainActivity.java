package com.shopnow.jewelease.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;
import com.shopnow.jewelease.R;
import com.shopnow.jewelease.adapters.TabLayoutAdapter;
import com.shopnow.jewelease.database.AppDatabase;
import com.shopnow.jewelease.database.entity.Product;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private TabLayoutAdapter tabLayoutAdapter;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.viewPager2);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2.setUserInputEnabled(false);

        FragmentManager fragmentManager = getSupportFragmentManager();
        tabLayoutAdapter = new TabLayoutAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(tabLayoutAdapter);
        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.addTab(tabLayout.newTab().setText("Gold"));
        tabLayout.addTab(tabLayout.newTab().setText("Diamond"));
        tabLayout.addTab(tabLayout.newTab().setText("Silver"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        drawerLayout = findViewById(R.id.drawerLayout);
    }

    public void openCartActivity(View view) {
        Intent intent = new Intent(MainActivity.this, CartActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void openSidebar(View view) {
        drawerLayout.openDrawer(GravityCompat.START);
    }
}