package com.shopnow.jewelease.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.shopnow.jewelease.database.entity.Order;

import java.util.List;

@Dao
public interface OrderDao {

    @Insert
    void insert(Order order);

    @Delete
    void delete(Order order);

    @Query("SELECT * FROM `Order` WHERE userId = :userId AND delivered = 0")
    List<Order> getOrders(long userId);

    @Query("SELECT * FROM `Order` WHERE userId = :userId")
    List<Order> getOrderHistory(long userId);

    @Query("SELECT * FROM `Order` WHERE userId = :userId AND delivered = 1 AND reviewed = 0")
    List<Order> getDeliveredOrder(long userId);

    @Update
    void update(Order order);

    @Query("UPDATE `Order` SET delivered = 1 WHERE userId = :userId")
    void updateOrderToDelivered(long userId);


}
