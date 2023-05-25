package com.shopnow.jewelease.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shopnow.jewelease.R;
import com.shopnow.jewelease.database.AppDatabase;
import com.shopnow.jewelease.database.dao.CartDao;
import com.shopnow.jewelease.database.dao.ProductDao;
import com.shopnow.jewelease.database.entity.Cart;
import com.shopnow.jewelease.database.entity.Product;
import com.shopnow.jewelease.database.entity.User;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class CheckOutActivity extends AppCompatActivity {

    private CartDao cartDao;
    private long userId;
    private TextView tvAddress1, tvItemTotal, tvTotal;

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
        String totalFee = total.add(BigDecimal.valueOf(50)).toString();
        tvTotal.setText(totalFee);

        tvAddress1.setText(user.address1);
    }

    public void backActivity(View view) {
        onBackPressed();
    }
}