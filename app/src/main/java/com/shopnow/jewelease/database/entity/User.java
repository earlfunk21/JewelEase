package com.shopnow.jewelease.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String username;
    public String password;

    public String firstname;
    public String lastname;
    public String address1;
    public String address2;
    public String phoneNumber;

    public User(String username, String password, String firstname, String lastname, String address1, String address2, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address1 = address1;
        this.address2 = address2;
        this.phoneNumber = phoneNumber;
    }
}