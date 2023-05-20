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

    @Test
    public void writeUserAndReadInList() throws Exception {
        Product product = new Product("Diamond Ring", "Diamond", "Pure ring with Diamonds (0.1400Ct)", BigDecimal.valueOf(5500), R.drawable.image_26);
        productDao.insert(product);
        List<Product> products = productDao.getProducts();
        for (Product productItem:
             products) {
            System.out.println("Name: " + productItem.name + " ID: " + productItem.id + " Image: " + productItem.thumbnail);
        }
    }
}
