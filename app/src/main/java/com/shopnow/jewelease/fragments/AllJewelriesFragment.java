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
import com.shopnow.jewelease.models.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class AllJewelriesFragment extends Fragment {

    private List<ProductModel> productModels;

    public AllJewelriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();
        RecyclerView product_item_rv = (RecyclerView) view.findViewById(R.id.product_item_rv);
        ProductAdapter productAdapter = new ProductAdapter(getContext(), productModels);
        product_item_rv.setAdapter(productAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_jewelries, container, false);
    }

    private void initData(){
        productModels = new ArrayList<>();
        productModels.add(new ProductModel("Diamond Ring", "Pure ring with Diamonds (0.1400Ct)", 5500, R.drawable.image_26));
        productModels.add(new ProductModel("Silver Ring", "Pure ring with Silver (0.1400Ct)", 800, R.drawable.image_27));
        productModels.add(new ProductModel("Gold Ring", "Pure ring with Gold (0.1400Ct)", 1200, R.drawable.image_28));
    }
}