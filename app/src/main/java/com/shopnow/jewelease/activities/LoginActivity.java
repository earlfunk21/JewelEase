package com.shopnow.jewelease.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.shopnow.jewelease.R;
import com.shopnow.jewelease.database.AppDatabase;
import com.shopnow.jewelease.database.dao.UserDao;
import com.shopnow.jewelease.database.entity.User;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private UserDao userDao;
    private SharedPreferences userPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userPrefs = getSharedPreferences("userPrefs", MODE_PRIVATE);
        userDao = AppDatabase.getInstance(this).userDao();
    }

    public void loginUser(View view) {
        EditText etEmail = findViewById(R.id.email_address_et);
        EditText etPassword = findViewById(R.id.password_et);
        String strEmail = Objects.requireNonNull(etEmail.getText()).toString();
        String strPassword = Objects.requireNonNull(etPassword.getText()).toString();

        User user = userDao.checkUser(strEmail, strPassword);
        if (user == null) {
            Toast.makeText(this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        SharedPreferences.Editor editor = userPrefs.edit();
        editor.putString("user", user.username);
        editor.apply();
    }
}