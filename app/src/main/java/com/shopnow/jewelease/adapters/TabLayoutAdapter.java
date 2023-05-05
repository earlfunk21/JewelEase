package com.shopnow.jewelease.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.shopnow.jewelease.fragments.AllJewelriesFragment;
import com.shopnow.jewelease.fragments.DiamondFragment;
import com.shopnow.jewelease.fragments.GoldFragment;
import com.shopnow.jewelease.fragments.SilverFragment;

public class TabLayoutAdapter extends FragmentStateAdapter {

    public TabLayoutAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new GoldFragment();
            case 2:
                return new DiamondFragment();
            case 3:
                return new SilverFragment();
        }
        return new AllJewelriesFragment();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
