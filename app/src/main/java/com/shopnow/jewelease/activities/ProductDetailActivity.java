package com.shopnow.jewelease.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.shopnow.jewelease.R;
import com.shopnow.jewelease.database.AppDatabase;
import com.shopnow.jewelease.database.dao.CartDao;
import com.shopnow.jewelease.database.dao.ProductDao;
import com.shopnow.jewelease.database.dao.UserDao;
import com.shopnow.jewelease.database.dao.WishlistDao;
import com.shopnow.jewelease.database.entity.Cart;
import com.shopnow.jewelease.database.entity.Product;
import com.shopnow.jewelease.database.entity.Wishlist;
import com.shopnow.jewelease.util.ImageHelper;

import java.math.BigDecimal;

public class ProductDetailActivity extends AppCompatActivity {

    private TextView tv_product_quantity;
    private TextView tv_product_price;
    private Product product;
    private int product_quantity = 1;
    private ProductDao productDao;
    private CartDao cartDao;

    private SharedPreferences userPrefs;

    private long userId;
    private long product_id;
    private UserDao userDao;
    private WishlistDao wishlistDao;
    private ImageButton ibWishlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        productDao = AppDatabase.getInstance(this).productDao();
        cartDao = AppDatabase.getInstance(this).cartDao();
        wishlistDao = AppDatabase.getInstance(this).wishlistDao();

        userPrefs = getSharedPreferences("userPrefs", MODE_PRIVATE);
        String username = userPrefs.getString("user", "");
        userDao = AppDatabase.getInstance(this).userDao();
        userId = userDao.getUserByUsername(username).id;

        TextView tv_product_name = findViewById(R.id.tv_product_name1);
        TextView tv_product_description = findViewById(R.id.tv_product_description1);
        tv_product_price = findViewById(R.id.tv_product_price1);
        ImageView iv_product_thumbnail = findViewById(R.id.iv_product_thumbnail1);
        tv_product_quantity = findViewById(R.id.tv_product_quantity);

        Intent intent = getIntent();
        product_id = intent.getExtras().getLong("product_id");
        product = productDao.getProductById(product_id);
        String price = "$" + product.price;

        tv_product_name.setText(product.name);
        tv_product_price.setText(price);
        tv_product_description.setText(product.description);
        Bitmap bmpThumbnail = ImageHelper.convertByteArrayToBitmap(product.thumbnail);
        iv_product_thumbnail.setImageBitmap(bmpThumbnail);

        ibWishlist = findViewById(R.id.ib_wishlist);
        if (wishlistDao.getWishlistByUserIdAndProductId(userId, product_id) != null) {
            ibWishlist.setImageResource(R.drawable.heart_filled);
        }
    }

    public void backActivity(View view) {
        onBackPressed();
    }

    public void decrementQuantity(View view) {
        if (product_quantity > 1) {
            product_quantity--;
        }
        String realPrice = "" + product_quantity;
        tv_product_quantity.setText(realPrice);
        modifyPriceByQuantity();
    }

    public void incrementQuantity(View view) {
        product_quantity++;
        String realPrice = "" + product_quantity;
        tv_product_quantity.setText(realPrice);
        modifyPriceByQuantity();
    }

    public void modifyPriceByQuantity() {
        String price = "$" + (product.price.multiply(BigDecimal.valueOf(product_quantity)));
        tv_product_price.setText(price);
    }

    public void addToCart(View view) {
        Cart cart = new Cart(userId, product_id, product_quantity);
        cartDao.insert(cart);
        Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void addToWishlist(View view) {
        Wishlist wishlist = wishlistDao.getWishlistByUserIdAndProductId(userId, product_id);
        if (wishlist != null) {
            ibWishlist.setImageResource(R.drawable.heart);
            ibWishlist.setEnabled(true);

            wishlistDao.delete(wishlist);
        } else {
            wishlistDao.insert(new Wishlist(userId, product_id));
            ibWishlist.setImageResource(R.drawable.heart_filled);
        }
    }
}