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
import com.shopnow.jewelease.adapters.OrderAdapter;
import com.shopnow.jewelease.database.AppDatabase;
import com.shopnow.jewelease.database.dao.OrderDao;
import com.shopnow.jewelease.database.entity.Order;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderHistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private long userId;

    public OrderHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment OrderHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderHistoryFragment newInstance(long param1) {
        OrderHistoryFragment fragment = new OrderHistoryFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getLong(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OrderDao orderDao = AppDatabase.getInstance(requireContext()).orderDao();
        List<Order> orderList = orderDao.getOrderHistory(userId);

        RecyclerView recyclerView = view.findViewById(R.id.rv_order);
        OrderAdapter orderAdapter = new OrderAdapter(requireContext(), orderList);
        recyclerView.setAdapter(orderAdapter);
    }
}