package com.lahaptech.lahap.user.menuproduct;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lahaptech.lahap.ItemClickListener;
import com.lahaptech.lahap.R;

import butterknife.BindView;
import butterknife.ButterKnife;

class MenuViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.frag_food_name)
    TextView name;
    @BindView(R.id.frag_food_image)
    ImageView photo;
    @BindView(R.id.frag_food_price)
    TextView price;
    @BindView(R.id.frag_food_seller)
    TextView seller;

    MenuViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
