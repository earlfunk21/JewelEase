package com.shopnow.jewelease.fragments;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
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
 * Use the {@link OrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private long userId;
    private int estimatedTime = 5;

    public OrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment OrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrdersFragment newInstance(long param1) {
        OrdersFragment fragment = new OrdersFragment();
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
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OrderDao orderDao = AppDatabase.getInstance(requireContext()).orderDao();
        List<Order> orderList = orderDao.getOrders(userId);

        RecyclerView recyclerView = view.findViewById(R.id.rv_order);
        OrderAdapter orderAdapter = new OrderAdapter(requireContext(), orderList);
        recyclerView.setAdapter(orderAdapter);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                estimatedTime--;
                if (estimatedTime == 0) {
                    handler.removeCallbacks(this);
                    orderDao.updateOrderToDelivered(userId);
                    List<Order> orderList = orderDao.getOrders(userId);
                    orderAdapter.setOrderList(orderList);
                    createNotification();
                } else {
                    handler.postDelayed(this, 1000);
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    private void createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My Notification", "JewelEase Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = requireContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        // Create an intent to launch the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), "My Notification");
        builder.setContentTitle("JewelEase Notification!");
        builder.setContentText("Your Order has been delivered!");
        builder.setSmallIcon(R.drawable.diamond);
        builder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(requireContext());
        managerCompat.notify(1, builder.build());
    }
}