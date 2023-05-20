package com.shopnow.jewelease.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.shopnow.jewelease.database.entity.Product;
import com.shopnow.jewelease.util.ImageHelper;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> productList;
    private Context context;

    public ProductAdapter(Context context, List<Product> productModelList) {
        this.context = context;
        this.productList = productModelList;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Product product = productList.get(position);
        String name = product.name;
        String description = product.description;
        String price = "$" + product.price;
        byte[] thumbnail = product.thumbnail;
        Bitmap bmpThumbnail = ImageHelper.convertByteArrayToBitmap(thumbnail);
        viewHolder.tvName.setText(name);
        viewHolder.tvDescription.setText(description);
        viewHolder.tvPrice.setText(price);
        viewHolder.ivThumbnail.setImageBitmap(bmpThumbnail);
        viewHolder.cvView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("product_id", product.id);
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivThumbnail;
        public TextView tvName, tvDescription, tvPrice;
        public CardView cvView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            tvName = (TextView) view.findViewById(R.id.tv_product_name);
            tvDescription = (TextView) view.findViewById(R.id.tv_product_description);
            tvPrice = (TextView) view.findViewById(R.id.tv_product_price);
            ivThumbnail = (ImageView) view.findViewById(R.id.iv_product_thumbnail);
            cvView = (CardView) view.findViewById(R.id.product_view);
        }
    }
}
