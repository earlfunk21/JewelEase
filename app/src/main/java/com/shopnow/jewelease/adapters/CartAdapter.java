package com.shopnow.jewelease.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.shopnow.jewelease.R;
import com.shopnow.jewelease.activities.ProductDetailActivity;
import com.shopnow.jewelease.models.ProductModel;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<ProductModel> productModelList;
    private Context context;

    public CartAdapter(Context context, List<ProductModel> productModelList) {
        this.context = context;
        this.productModelList = productModelList;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        ProductModel productModel = productModelList.get(position);

        String name = productModel.getName();
        String description = productModel.getDescription();
        String price = "$" + productModel.getPrice();
        int thumbnail = productModel.getThumbnail();

        viewHolder.getTv_product_name().setText(name);
        viewHolder.getTv_product_description().setText(description);
        viewHolder.getTv_product_price().setText(price);
        viewHolder.getIv_product_thumbnail().setImageResource(thumbnail);
        viewHolder.cv_product_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("model", productModel);
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_product_thumbnail;
        private TextView tv_product_name, tv_product_description, tv_product_price, tv_product_quantity;
        private CardView cv_product_view;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            tv_product_name = (TextView) view.findViewById(R.id.tv_product_name);
            tv_product_description = (TextView) view.findViewById(R.id.tv_product_description);
            tv_product_price = (TextView) view.findViewById(R.id.tv_product_price);
            tv_product_quantity = (TextView) view.findViewById(R.id.tv_product_quantity2);
            iv_product_thumbnail = (ImageView) view.findViewById(R.id.iv_product_thumbnail);
            cv_product_view = (CardView) view.findViewById(R.id.product_view);
        }

        public TextView getTv_product_name() {
            return tv_product_name;
        }
        public TextView getTv_product_description() {
            return tv_product_description;
        }
        public TextView getTv_product_price() {
            return tv_product_price;
        }
        public ImageView getIv_product_thumbnail(){
            return iv_product_thumbnail;
        }
    }
}
