package com.shopnow.jewelease.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.shopnow.jewelease.database.entity.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM User WHERE username = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM User WHERE username = :username AND password = :password")
    User checkUser(String username, String password);

    @Query("SELECT * FROM User")
    List<User> getUsers();
}