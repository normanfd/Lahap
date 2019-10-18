package com.lahaptech.lahap.user.menuproduct.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lahaptech.lahap.R;

class MenuViewHolder extends RecyclerView.ViewHolder {
    TextView name;
    ImageView photo;
    TextView price;
    TextView desc;

    MenuViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.frag_food_name);
        photo = itemView.findViewById(R.id.frag_food_image);
        price = itemView.findViewById(R.id.frag_food_price);
        desc = itemView.findViewById(R.id.frag_food_desc);
    }
}
