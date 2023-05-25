package com.shopnow.jewelease.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.shopnow.jewelease.fragments.DeliveredOrderFragment;
import com.shopnow.jewelease.fragments.OrderHistoryFragment;
import com.shopnow.jewelease.fragments.OrdersFragment;

public class OrderLayoutAdapter extends FragmentStateAdapter {

    private long userId;

    public OrderLayoutAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, long userId) {
        super(fragmentManager, lifecycle);
        this.userId = userId;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return DeliveredOrderFragment.newInstance(userId);
            case 2:
                return OrderHistoryFragment.newInstance(userId);
        }
        return OrdersFragment.newInstance(userId);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
