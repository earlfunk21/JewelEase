package com.shopnow.jewelease.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.shopnow.jewelease.R;

public class WelcomeActivity extends AppCompatActivity {

    private SharedPreferences userPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        userPrefs = getSharedPreferences("userPrefs", MODE_PRIVATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!userPrefs.getString("user", "").equals("")) {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    public void openLoginActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void openRegisterActivity(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}