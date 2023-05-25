package com.shopnow.jewelease.database.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;

@Entity
public class Order {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public long userId;
    public String deliveredTo;
    public BigDecimal totalAmount;
    public boolean delivered = false;
    public boolean reviewed = false;

    public Order(long userId, String deliveredTo, BigDecimal totalAmount) {
        this.userId = userId;
        this.deliveredTo = deliveredTo;
        this.totalAmount = totalAmount;
    }
}
