package com.shopnow.jewelease.fragments;

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

import java.util.ArrayList;
import java.util.List;

public class GoldFragment extends Fragment {

    private List<Product> productList;
    private ProductDao productDao;

    public GoldFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gold, container, false);
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
    private void initData(){
        productList = new ArrayList<>();
        try {
            productList.addAll(productDao.getProductsByGold());
        } catch (NullPointerException ignored) {
        }
    }
}