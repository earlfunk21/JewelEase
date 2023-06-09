package com.shopnow.jewelease.database;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.shopnow.jewelease.database.converter.BigDecimalConverter;
import com.shopnow.jewelease.database.dao.CartDao;
import com.shopnow.jewelease.database.dao.OrderDao;
import com.shopnow.jewelease.database.dao.ProductDao;
import com.shopnow.jewelease.database.dao.UserDao;
import com.shopnow.jewelease.database.dao.WishlistDao;
import com.shopnow.jewelease.database.entity.Cart;
import com.shopnow.jewelease.database.entity.Order;
import com.shopnow.jewelease.database.entity.Product;
import com.shopnow.jewelease.database.entity.User;
import com.shopnow.jewelease.database.entity.Wishlist;

import java.math.BigDecimal;


@Database(entities = {User.class, Product.class, Cart.class, Wishlist.class, Order.class}, version = 1)
@TypeConverters({BigDecimalConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract ProductDao productDao();

    public abstract CartDao cartDao();

    public abstract WishlistDao wishlistDao();
    public abstract OrderDao orderDao();

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                    "test_db5").allowMainThreadQueries().build();
        }
        return instance;
    }


}