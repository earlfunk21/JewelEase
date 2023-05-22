package com.shopnow.jewelease.database.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Wishlist {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long userId;

    public long productId;

    public Wishlist(long userId, long productId) {
        this.userId = userId;
        this.productId = productId;
    }
}
