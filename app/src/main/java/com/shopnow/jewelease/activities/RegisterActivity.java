package com.shopnow.jewelease.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.shopnow.jewelease.R;
import com.shopnow.jewelease.database.AppDatabase;
import com.shopnow.jewelease.database.dao.UserDao;
import com.shopnow.jewelease.database.entity.User;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userDao = AppDatabase.getInstance(this).userDao();
    }

    public void openLoginActivity(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void createAccount(View view) {
        EditText etEmail = findViewById(R.id.email_address_et1);
        EditText etPassword = findViewById(R.id.password_et1);
        EditText etPassword1 = findViewById(R.id.re_type_password_et);
        EditText etAddress1 = findViewById(R.id.address_1_et);
        EditText etAddress2 = findViewById(R.id.address_2_et);
        EditText etPhoneNumber = findViewById(R.id.phone_number_et);
        EditText etFirstName = findViewById(R.id.first_name_et);
        EditText etLastName = findViewById(R.id.last_name_et);


        String password = Objects.requireNonNull(etPassword.getText()).toString();
        String confirm_password = Objects.requireNonNull(etPassword1.getText()).toString();
        String username = Objects.requireNonNull(etEmail.getText()).toString();
        String firstname = Objects.requireNonNull(etFirstName.getText()).toString();
        String lastname = Objects.requireNonNull(etLastName.getText()).toString();
        String address1 = Objects.requireNonNull(etAddress1.getText()).toString();
        String address2 = Objects.requireNonNull(etAddress2.getText()).toString();
        String phoneNumber = Objects.requireNonNull(etPhoneNumber.getText()).toString();


        if(password.equals("")
                || confirm_password.equals("")
                || username.equals("")
                || firstname.equals("")
                || lastname.equals("")
                || address1.equals("")
                || address2.equals("")
                || phoneNumber.equals("")){
            Toast.makeText(this, "Fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!password.equals(confirm_password)){
            Toast.makeText(this, "Password must match!", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = userDao.getUserByUsername(username);
        if(user != null){
            Toast.makeText(this, "Username is already exists!", Toast.LENGTH_SHORT).show();
            return;
        }
        user = new User(
                username, password, firstname, lastname, address1, address2, phoneNumber
        );
        userDao.insert(user);
        Toast.makeText(this, "Successfully Created!", Toast.LENGTH_SHORT).show();
        openLoginActivity(view);
    }
}