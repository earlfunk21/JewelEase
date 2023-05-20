package com.shopnow.jewelease.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;


@Entity
public class Product {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;
    public String category;
    public String description;
    public BigDecimal price;
    public byte[] thumbnail;

    public Product(String name, String category, String description, BigDecimal price, byte[] thumbnail) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.thumbnail = thumbnail;
    }
}
