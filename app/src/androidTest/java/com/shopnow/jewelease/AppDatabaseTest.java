package com.shopnow.jewelease;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.shopnow.jewelease.database.AppDatabase;
import com.shopnow.jewelease.database.dao.ProductDao;
import com.shopnow.jewelease.database.entity.Product;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@RunWith(AndroidJUnit4.class)
public class AppDatabaseTest {
    private ProductDao productDao;
    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        productDao = db.productDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

}
