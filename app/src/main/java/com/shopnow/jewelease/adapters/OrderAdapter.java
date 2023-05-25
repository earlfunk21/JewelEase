package com.shopnow.jewelease.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shopnow.jewelease.R;
import com.shopnow.jewelease.database.AppDatabase;
import com.shopnow.jewelease.database.dao.OrderDao;
import com.shopnow.jewelease.database.entity.Order;

import java.math.BigDecimal;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Context context;
    private List<Order> orderList;


    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Order order = orderList.get(position);
        String totalAmount = order.totalAmount.toString();
        String orderId = String.valueOf(order.id);
        viewHolder.tvTotalAmount.setText(totalAmount);
        viewHolder.tvOrderId.setText(orderId);
        viewHolder.tvDeliveredTo.setText(order.deliveredTo);
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvOrderId;
        public TextView tvDeliveredTo;
        public TextView tvTotalAmount;

        public ViewHolder(View view) {
            super(view);
            tvDeliveredTo = view.findViewById(R.id.tv_delivered_to);
            tvOrderId = view.findViewById(R.id.tv_order_id);
            tvTotalAmount = view.findViewById(R.id.tv_total_amount);
        }
    }

    public Order getOrderAt(int position) {
        return orderList.get(position);
    }
}
