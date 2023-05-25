package com.shopnow.jewelease.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.shopnow.jewelease.R;
import com.shopnow.jewelease.adapters.OrderLayoutAdapter;
import com.shopnow.jewelease.adapters.TabLayoutAdapter;
import com.shopnow.jewelease.database.AppDatabase;
import com.shopnow.jewelease.database.dao.UserDao;
import com.shopnow.jewelease.database.entity.User;

public class OrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        ViewPager2 viewPager2 = findViewById(R.id.viewPager2);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        viewPager2.setUserInputEnabled(false);

        UserDao userDao = AppDatabase.getInstance(this).userDao();
        SharedPreferences userPrefs = getSharedPreferences("userPrefs", MODE_PRIVATE);
        User user = userDao.getUserByUsername(userPrefs.getString("user", ""));
        FragmentManager fragmentManager = getSupportFragmentManager();
        OrderLayoutAdapter tabLayoutAdapter = new OrderLayoutAdapter(fragmentManager, getLifecycle(), user.id);
        viewPager2.setAdapter(tabLayoutAdapter);

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
    }

    public void goBack(View view){
        onBackPressed();
    }
}