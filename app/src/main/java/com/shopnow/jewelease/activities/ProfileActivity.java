package com.shopnow.jewelease.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shopnow.jewelease.R;
import com.shopnow.jewelease.database.AppDatabase;
import com.shopnow.jewelease.database.dao.UserDao;
import com.shopnow.jewelease.database.entity.User;

public class ProfileActivity extends AppCompatActivity {

    private UserDao userDao;
    private User user;

    private SharedPreferences userPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        AppDatabase db = AppDatabase.getInstance(this);
        userDao = db.userDao();
        userPrefs = getSharedPreferences("userPrefs", MODE_PRIVATE);
        user = userDao.getUserByUsername(userPrefs.getString("user", ""));
        TextView tvFullName = findViewById(R.id.tv_full_name);
        TextView tvUsername = findViewById(R.id.tv_username);
        TextView tvAddress1 = findViewById(R.id.tv_address1);
        TextView tvAddress2 = findViewById(R.id.tv_address2);
        TextView tvPhoneNumber = findViewById(R.id.tv_phone_number);
        String fullName = user.firstname + " " + user.lastname;
        tvFullName.setText(fullName);
        tvUsername.setText(user.username);
        tvAddress1.setText(user.address1);
        tvAddress2.setText(user.address2);
        tvPhoneNumber.setText(user.phoneNumber);
    }

    public void logoutUser(View view) {
        SharedPreferences.Editor userEditor = userPrefs.edit();
        userEditor.clear();
        userEditor.apply();
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void backActivity(View view) {
        onBackPressed();
    }

    public void openEditProfile(View view) {
        Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
        startActivity(intent);
    }
}