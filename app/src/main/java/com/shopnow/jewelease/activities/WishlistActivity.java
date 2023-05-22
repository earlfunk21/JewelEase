package com.shopnow.jewelease.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.shopnow.jewelease.R;
import com.shopnow.jewelease.adapters.CartAdapter;
import com.shopnow.jewelease.adapters.WishlistAdapter;
import com.shopnow.jewelease.database.AppDatabase;
import com.shopnow.jewelease.database.dao.CartDao;
import com.shopnow.jewelease.database.dao.WishlistDao;
import com.shopnow.jewelease.database.entity.Cart;
import com.shopnow.jewelease.database.entity.Wishlist;

import java.util.ArrayList;
import java.util.List;

public class WishlistActivity extends AppCompatActivity {

    private List<Wishlist> wishlists;
    private long userId;
    private WishlistDao wishlistDao;
    private WishlistAdapter wishlistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        SharedPreferences userPrefs = getSharedPreferences("userPrefs", MODE_PRIVATE);
        String username = userPrefs.getString("user", "");
        userId = AppDatabase.getInstance(this).userDao().getUserByUsername(username).id;
        wishlistDao = AppDatabase.getInstance(this).wishlistDao();

        initData();
        RecyclerView cart_item_rv = findViewById(R.id.wishlist_item_rv);
        wishlistAdapter = new WishlistAdapter(this, wishlists);
        cart_item_rv.setAdapter(wishlistAdapter);

    }

    public void backActivity(View view) {
        onBackPressed();
    }

    private void initData() {
        wishlists = new ArrayList<>();
        List<Wishlist> wishlists1 = wishlistDao.getWishlistByUserId(userId);
        if (wishlists1 != null)
            wishlists.addAll(wishlists1);
    }
}