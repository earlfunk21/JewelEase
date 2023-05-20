package com.shopnow.jewelease.activities;

import androidx.appcompat.app.AppCompatActivity;

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
        String fullName = user.firstname + " " + user.lastname;

        tvFullName.setText(fullName);
        tvUsername.setText(user.username);

    }

    public void logoutUser(View view) {
        userPrefs.
    }
}