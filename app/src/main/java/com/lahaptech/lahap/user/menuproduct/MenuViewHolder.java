package com.lahaptech.lahap.user.menuproduct;

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
    TextView seller;

    MenuViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.frag_food_name);
        photo = itemView.findViewById(R.id.frag_food_image);
        price = itemView.findViewById(R.id.frag_food_price);
        seller = itemView.findViewById(R.id.frag_food_seller);
    }
}
