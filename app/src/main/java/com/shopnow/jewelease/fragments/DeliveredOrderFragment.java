package com.shopnow.jewelease.fragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shopnow.jewelease.R;
import com.shopnow.jewelease.adapters.OrderAdapter;
import com.shopnow.jewelease.database.AppDatabase;
import com.shopnow.jewelease.database.dao.OrderDao;
import com.shopnow.jewelease.database.entity.Cart;
import com.shopnow.jewelease.database.entity.Order;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeliveredOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeliveredOrderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private long userId;

    private OrderAdapter orderAdapter;
    private List<Order> orderList;

    public DeliveredOrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment DeliveredOrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeliveredOrderFragment newInstance(long param1) {
        DeliveredOrderFragment fragment = new DeliveredOrderFragment();
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
        return inflater.inflate(R.layout.fragment_delivered_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OrderDao orderDao = AppDatabase.getInstance(requireContext()).orderDao();
        orderList = orderDao.getDeliveredOrder(userId);

        RecyclerView recyclerView = view.findViewById(R.id.rv_order);
        orderAdapter = new OrderAdapter(requireContext(), orderList);
        recyclerView.setAdapter(orderAdapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Order order = orderAdapter.getOrderAt(position);
                confirmDelete(order, position);

            }
        }).attachToRecyclerView(recyclerView);
    }

    private void confirmDelete(Order order, int position) {
        OrderDao orderDao = AppDatabase.getInstance(requireContext()).orderDao();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext(), R.style.AlertDialogCustom);
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View dialogView = inflater.inflate(R.layout.order_review, null);
        EditText editText = dialogView.findViewById(R.id.et_feedback);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Retrieve the text from the EditText
                Toast.makeText(requireContext(), "Thank you for your feedback!", Toast.LENGTH_LONG).show();
                order.reviewed = true;
                orderDao.update(order);
                orderList.remove(position);
                orderAdapter.notifyItemRemoved(position);
            }
        });
        dialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                orderAdapter.setOrderList(orderDao.getDeliveredOrder(userId));
            }
        });
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

}