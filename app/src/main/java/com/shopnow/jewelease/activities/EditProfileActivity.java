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

public class EditProfileActivity extends AppCompatActivity {

    private UserDao userDao;
    private User user;
    private SharedPreferences userPrefs;
    private EditText etAddress1;
    private EditText etAddress2;
    private EditText etPhoneNumber;
    private EditText etFirstName;
    private EditText etLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        AppDatabase db = AppDatabase.getInstance(this);
        userDao = db.userDao();
        userPrefs = getSharedPreferences("userPrefs", MODE_PRIVATE);
        user = userDao.getUserByUsername(userPrefs.getString("user", ""));

        etAddress1 = findViewById(R.id.address_1_et);
        etAddress2 = findViewById(R.id.address_2_et);
        etPhoneNumber = findViewById(R.id.phone_number_et);
        etFirstName = findViewById(R.id.first_name_et);
        etLastName = findViewById(R.id.last_name_et);

        etAddress1.setText(user.address1);
        etAddress2.setText(user.address2);
        etPhoneNumber.setText(user.phoneNumber);
        etFirstName.setText(user.firstname);
        etLastName.setText(user.lastname);
    }

    public void editAccount(View view) {
        String firstname = Objects.requireNonNull(etFirstName.getText()).toString();
        String lastname = Objects.requireNonNull(etLastName.getText()).toString();
        String address1 = Objects.requireNonNull(etAddress1.getText()).toString();
        String address2 = Objects.requireNonNull(etAddress2.getText()).toString();
        String phoneNumber = Objects.requireNonNull(etPhoneNumber.getText()).toString();

        if (firstname.equals("")
                || lastname.equals("")
                || address1.equals("")
                || address2.equals("")
                || phoneNumber.equals("")) {
            Toast.makeText(this, "Fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }
        user.firstname = firstname;
        user.lastname = lastname;
        user.address1 = address1;
        user.address2 = address2;
        user.phoneNumber = phoneNumber;
        userDao.update(user);
        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        Toast.makeText(this, "Successfully Edited!", Toast.LENGTH_SHORT).show();
    }
}