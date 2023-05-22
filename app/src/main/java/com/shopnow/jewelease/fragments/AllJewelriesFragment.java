package com.shopnow.jewelease.fragments;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shopnow.jewelease.R;
import com.shopnow.jewelease.adapters.ProductAdapter;
import com.shopnow.jewelease.database.AppDatabase;
import com.shopnow.jewelease.database.dao.ProductDao;
import com.shopnow.jewelease.database.entity.Product;
import com.shopnow.jewelease.models.ProductModel;
import com.shopnow.jewelease.util.ImageHelper;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AllJewelriesFragment extends Fragment {

    private List<Product> productList;
    private ProductDao productDao;

    public AllJewelriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productDao = AppDatabase.getInstance(getContext()).productDao();
        initData();

        RecyclerView product_item_rv = view.findViewById(R.id.product_item_rv);
        ProductAdapter productAdapter = new ProductAdapter(getContext(), productList);
        product_item_rv.setAdapter(productAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_jewelries, container, false);
    }

    private void initData() {
        productList = new ArrayList<>();
        load();

        try {
            productList.addAll(productDao.getProducts());
        } catch (NullPointerException ignored) {
        }
    }

    private void load() {
        Bitmap bmpDiamond = BitmapFactory.decodeResource(getResources(), R.drawable.image_28);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmpDiamond.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] diamondRing = stream.toByteArray();
        productDao.insert(new Product("Diamond Ring", "Diamond", "Pure ring with Diamonds (0.1400Ct)", BigDecimal.valueOf(5500), diamondRing));

        bmpDiamond = BitmapFactory.decodeResource(getResources(), R.drawable.image_27);
        stream = new ByteArrayOutputStream();
        bmpDiamond.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] goldRing = stream.toByteArray();
        productDao.insert(new Product("Gold Ring", "Gold", "Pure ring with Golds (0.1400Ct)", BigDecimal.valueOf(5500), goldRing));

        bmpDiamond = BitmapFactory.decodeResource(getResources(), R.drawable.image_26);
        stream = new ByteArrayOutputStream();
        bmpDiamond.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] silverRing = stream.toByteArray();
        productDao.insert(new Product("Gold Ring", "Gold", "Pure ring with Golds (0.1400Ct)", BigDecimal.valueOf(5500), silverRing));

    }
}