package com.lahaptech.lahap.seller.update.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lahaptech.lahap.R;

class ProductViewHolder extends RecyclerView.ViewHolder {
    TextView productName;
    TextView productPrice;
    TextView productDesc;
    ImageView productImage;

    ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        productName = itemView.findViewById(R.id.frag_food_name);
        productPrice = itemView.findViewById(R.id.frag_food_price);
        productDesc = itemView.findViewById(R.id.frag_food_desc);
        productImage = itemView.findViewById(R.id.frag_food_image);
    }
}
