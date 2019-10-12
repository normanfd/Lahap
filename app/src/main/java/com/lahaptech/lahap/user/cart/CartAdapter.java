package com.lahaptech.lahap.user.cart;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lahaptech.lahap.ItemClickListener;
import com.lahaptech.lahap.R;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ItemClickListener itemClickListener;

    TextView txtProductName;
    TextView txtProductPrice;
    TextView txtProductQuantity;

    CartAdapter(@NonNull View itemView) {
        super(itemView);
        ImageView imageView = itemView.findViewById(R.id.cart_food_image);
        txtProductName = itemView.findViewById(R.id.cart_product_name);
        txtProductPrice = itemView.findViewById(R.id.cart_product_price);
        txtProductQuantity = itemView.findViewById(R.id.cart_product_quantity);

    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}

