package com.shopnow.jewelease.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shopnow.jewelease.R;
import com.shopnow.jewelease.models.ProductModel;

public class ProductDetailActivity extends AppCompatActivity {

    private TextView tv_product_quantity;
    private TextView tv_product_price;
    private ProductModel productModel;
    private int product_quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        TextView tv_product_name = findViewById(R.id.tv_product_name1);
        TextView tv_product_description = findViewById(R.id.tv_product_description1);
        tv_product_price = findViewById(R.id.tv_product_price1);
        ImageView iv_product_thumbnail = findViewById(R.id.iv_product_thumbnail1);
        tv_product_quantity = findViewById(R.id.tv_product_quantity);

        Intent intent = getIntent();
        productModel = (ProductModel) intent.getSerializableExtra("model");

        String price = "$" + productModel.getPrice();

        tv_product_name.setText(productModel.getName());
        tv_product_price.setText(price);
        tv_product_description.setText(productModel.getDescription());
        iv_product_thumbnail.setImageResource(productModel.getThumbnail());
    }

    public void backActivity(View view) {
        onBackPressed();
    }

    public void decrementQuantity(View view) {
        if(product_quantity > 1){
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

    public void modifyPriceByQuantity(){
        String price = "$" + (productModel.getPrice() * product_quantity);
        tv_product_price.setText(price);
    }

    public void goToMyCart(View view) {
        Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}