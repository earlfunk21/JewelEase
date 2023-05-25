package com.shopnow.jewelease.database.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.shopnow.jewelease.database.entity.Cart;

import java.util.List;

@Dao
public interface CartDao {
    @Insert
    void insert(Cart cart);

    @Delete
    void delete(Cart cart);

    @Query("DELETE FROM Cart")
    void deleteAll();

    @Query("SELECT * FROM Cart WHERE userId = :userId")
    List<Cart> getUserWithCarts(long userId);

    @Query("DELETE FROM Cart WHERE userId = :userId")
    void deleteAllWithUserId(long userId);
}
