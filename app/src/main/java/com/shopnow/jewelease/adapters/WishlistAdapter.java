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
import com.shopnow.jewelease.database.AppDatabase;
import com.shopnow.jewelease.database.entity.Cart;
import com.shopnow.jewelease.database.entity.Product;
import com.shopnow.jewelease.database.entity.User;
import com.shopnow.jewelease.database.entity.Wishlist;
import com.shopnow.jewelease.util.ImageHelper;

import java.math.BigDecimal;
import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

    private Context context;
    private List<Wishlist> wishlists;

    public WishlistAdapter(Context context, List<Wishlist> wishlists) {
        this.context = context;
        this.wishlists = wishlists;
    }

    public void setWishlists(List<Wishlist> wishlists){
        this.wishlists = wishlists;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        Wishlist wishlist = wishlists.get(position);
        Product product = AppDatabase.getInstance(context).productDao().getProductById(wishlist.productId);
        // Product properties
        String name = product.name;
        String description = product.description;
        byte[] thumbnail = product.thumbnail;


        // User properties
        Bitmap bmpThumbnail = ImageHelper.convertByteArrayToBitmap(thumbnail);
        viewHolder.getTv_product_name().setText(name);
        viewHolder.getTv_product_description().setText(description);
        viewHolder.getIv_product_thumbnail().setImageBitmap(bmpThumbnail);
        viewHolder.cv_product_view.setOnClickListener(new View.OnClickListener() {
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
    public WishlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.wishlist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return wishlists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_product_thumbnail;
        private TextView tv_product_name, tv_product_description;
        private CardView cv_product_view;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            tv_product_name = (TextView) view.findViewById(R.id.tv_product_name2_wishlist);
            tv_product_description = (TextView) view.findViewById(R.id.tv_product_description2_wishlist);
            iv_product_thumbnail = (ImageView) view.findViewById(R.id.iv_product_thumbnail2_wishlist);
            cv_product_view = (CardView) view.findViewById(R.id.product_view2_wishlist);
        }

        public TextView getTv_product_name() {
            return tv_product_name;
        }

        public TextView getTv_product_description() {
            return tv_product_description;
        }

        public ImageView getIv_product_thumbnail() {
            return iv_product_thumbnail;
        }
    }

    public Wishlist getWishlistAt(int position){
        return wishlists.get(position);
    }
}
