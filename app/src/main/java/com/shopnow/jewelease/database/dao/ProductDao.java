package com.shopnow.jewelease.database.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.shopnow.jewelease.database.entity.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert
    void insert(Product product);

    @Delete
    void delete(Product product);

    @Query("DELETE FROM Product")
    void deleteAll();

    @Query("SELECT * FROM Product LIMIT 4")
    List<Product> getProducts();

    @Query("SELECT * FROM Product WHERE id = :id")
    Product getProductById(long id);

    @Query("SELECT * FROM Product WHERE category = 'Silver'")
    List<Product> getProductsBySilver();

    @Query("SELECT * FROM Product WHERE category = 'Gold'")
    List<Product> getProductsByGold();

    @Query("SELECT * FROM Product WHERE category = 'Diamond'")
    List<Product> getProductsByDiamond();
}
