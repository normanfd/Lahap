package com.lahaptech.lahap.owner.update.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lahaptech.lahap.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.frag_food_name)
    TextView productName;
    @BindView(R.id.frag_food_price)
    TextView productPrice;
    @BindView(R.id.frag_food_desc)
    TextView productDesc;
    @BindView(R.id.frag_food_image)
    ImageView productImage;

    ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
