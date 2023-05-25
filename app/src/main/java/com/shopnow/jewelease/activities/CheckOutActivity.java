package com.shopnow.jewelease.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.shopnow.jewelease.R;
import com.shopnow.jewelease.database.AppDatabase;
import com.shopnow.jewelease.database.dao.CartDao;
import com.shopnow.jewelease.database.dao.OrderDao;
import com.shopnow.jewelease.database.dao.ProductDao;
import com.shopnow.jewelease.database.entity.Cart;
import com.shopnow.jewelease.database.entity.Order;
import com.shopnow.jewelease.database.entity.Product;
import com.shopnow.jewelease.database.entity.User;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class CheckOutActivity extends AppCompatActivity {

    private CartDao cartDao;
    private long userId;
    private TextView tvAddress1, tvItemTotal, tvTotal;

    private BigDecimal totalAmount;
    private String address1;

    private CancellationSignal cancellationSignal = null;
    private BiometricPrompt.AuthenticationCallback authenticationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        cartDao = AppDatabase.getInstance(this).cartDao();
        SharedPreferences userPrefs = getSharedPreferences("userPrefs", MODE_PRIVATE);
        String username = userPrefs.getString("user", "");
        User user = AppDatabase.getInstance(this).userDao().getUserByUsername(username);
        userId = user.id;

        tvAddress1 = findViewById(R.id.tv_address1);
        tvItemTotal = findViewById(R.id.tv_item_total);
        tvTotal = findViewById(R.id.tv_total);
        ProductDao productDao = AppDatabase.getInstance(this).productDao();

        List<Cart> cartList = cartDao.getUserWithCarts(userId);
        BigDecimal total = BigDecimal.valueOf(0);
        try{
            for (Cart cart:
                 cartList) {
                Product product = productDao.getProductById(cart.productId);
                total = total.add(product.price.multiply(BigDecimal.valueOf(cart.quantity)));
            }
        } catch (NullPointerException ignored){}
        DecimalFormat decimalFormat = new DecimalFormat("₱#.##");

        tvItemTotal.setText(decimalFormat.format(total));
        totalAmount = total.add(BigDecimal.valueOf(50));
        String totalFee = totalAmount.toString();
        tvTotal.setText(totalFee);

        address1 = user.address1;
        tvAddress1.setText(user.address1);
    }

    public void backActivity(View view) {
        onBackPressed();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void placeOrder(View view) {
        authenticationCallback = new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(
                    int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                notifyUser("Authentication Error : " + errString);
            }
            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                Order order = new Order(userId, address1, totalAmount);
                OrderDao orderDao = AppDatabase.getInstance(CheckOutActivity.this).orderDao();
                orderDao.insert(order);
                cartDao.deleteAllWithUserId(userId);
                Intent intent = new Intent(CheckOutActivity.this, OrdersActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                notifyUser("Successfully added to your orders");
            }
        };

        BiometricPrompt biometricPrompt = new BiometricPrompt
                .Builder(getApplicationContext())
                .setTitle("JewelEase Authentication")
                .setSubtitle("Confirmation to place order")
                .setDescription("Uses FP")
                .setNegativeButton("Cancel", getMainExecutor(), new DialogInterface.OnClickListener() {
                    @Override
                    public void
                    onClick(DialogInterface dialogInterface, int i) {
                        notifyUser("Authentication Cancelled");
                    }
                }).build();

        // start the authenticationCallback in
        // mainExecutor
        biometricPrompt.authenticate(
                getCancellationSignal(),
                getMainExecutor(),
                authenticationCallback);

        checkBiometricSupport();
    }

    private CancellationSignal getCancellationSignal() {
        cancellationSignal = new CancellationSignal();
        cancellationSignal.setOnCancelListener(
                new CancellationSignal.OnCancelListener() {
                    @Override
                    public void onCancel() {
                        notifyUser("Authentication was Cancelled by the user");
                    }
                });
        return cancellationSignal;
    }

    private void checkBiometricSupport() {
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        if (!keyguardManager.isDeviceSecure()) {
            notifyUser("Fingerprint authentication has not been enabled in settings");
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {
            notifyUser("Fingerprint Authentication Permission is not enabled");
            return;
        }
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
        }
    }
    private void notifyUser(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}