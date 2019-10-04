package com.lahaptech.lahap.user.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.lahaptech.lahap.ItemClickListener;
import com.lahaptech.lahap.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.frag_food_name)
    public
    TextView name;
    @BindView(R.id.frag_food_image)
    public
    ImageView photo;
    @BindView(R.id.frag_food_price)
    public
    TextView price;
    @BindView(R.id.frag_food_desc)
    public
    TextView desc;
    private ItemClickListener listener;

    public FoodAdapter(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(), false);
    }
}
