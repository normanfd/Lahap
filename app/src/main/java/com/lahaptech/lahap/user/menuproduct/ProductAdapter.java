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

public class ProductAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.frag_food_name)
    TextView name;
    @BindView(R.id.frag_food_image)
    ImageView photo;
    @BindView(R.id.frag_food_price)
    TextView price;
    @BindView(R.id.frag_food_desc)
    TextView desc;
    private ItemClickListener listener;

    ProductAdapter(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(), false);
    }
}