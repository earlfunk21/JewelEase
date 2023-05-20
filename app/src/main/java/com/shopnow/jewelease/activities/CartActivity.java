package com.shopnow.jewelease.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.shopnow.jewelease.R;
import com.shopnow.jewelease.adapters.CartAdapter;
import com.shopnow.jewelease.adapters.ProductAdapter;
import com.shopnow.jewelease.database.AppDatabase;
import com.shopnow.jewelease.database.dao.CartDao;
import com.shopnow.jewelease.database.entity.Cart;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private List<Cart> cartList;
    private CartDao cartDao;
    private long userId;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartDao = AppDatabase.getInstance(this).cartDao();
        SharedPreferences userPrefs = getSharedPreferences("userPrefs", MODE_PRIVATE);
        String username = userPrefs.getString("user", "");
        userId = AppDatabase.getInstance(this).userDao().getUserByUsername(username).id;

        initData();
        RecyclerView cart_item_rv = findViewById(R.id.cart_item_rv);
        cartAdapter = new CartAdapter(this, cartList);
        cart_item_rv.setAdapter(cartAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Cart cart = cartAdapter.getCartAt(position);
                confirmDelete(cart);
            }
        }).attachToRecyclerView(cart_item_rv);
    }

    private void confirmDelete(Cart cart) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.popup_delete, null);
        TextView tvName = dialogView.findViewById(R.id.et_cart_delete);
        String name = "Cart ID: " + cart.id;
        tvName.setText(name);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Retrieve the text from the EditText
                cartDao.delete(cart);
                cartAdapter.setCartList(cartDao.getUserWithCarts(userId));
            }
        });
        dialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                cartAdapter.setCartList(cartDao.getUserWithCarts(userId));
            }
        });
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    public void backActivity(View view) {
        onBackPressed();
    }

    private void initData() {
        cartList = new ArrayList<>();
        try {
            System.out.println(cartDao.getUserWithCarts(userId).size());
            cartList.addAll(cartDao.getUserWithCarts(userId));
        } catch (NullPointerException ignored) {
        }
    }
}