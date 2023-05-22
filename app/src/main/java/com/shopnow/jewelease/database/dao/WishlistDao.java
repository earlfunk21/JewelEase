package com.shopnow.jewelease.database.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.shopnow.jewelease.database.entity.Wishlist;

import java.util.List;

@Dao
public interface WishlistDao {
    @Insert
    void insert(Wishlist wishlist);

    @Delete
    void delete(Wishlist wishlist);

    @Query("SELECT * FROM Wishlist WHERE userId = :userId")
    List<Wishlist> getWishlistByUserId(long userId);

    @Query("SELECT * FROM Wishlist WHERE userId = :userId AND productId = :productId")
    Wishlist getWishlistByUserIdAndProductId(long userId, long productId);
}
