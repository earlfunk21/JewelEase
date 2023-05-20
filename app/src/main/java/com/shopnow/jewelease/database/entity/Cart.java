package com.shopnow.jewelease.database.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Cart {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public long userId;
    public long productId;
    public int quantity;

    public Cart(long userId, long productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
