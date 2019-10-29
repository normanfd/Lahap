package com.lahaptech.lahap.user.index.orderhistory;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lahaptech.lahap.R;

class HistoryViewHolder extends RecyclerView.ViewHolder {

    TextView order_date, order_price, order_product_list;
    ImageView order_logo;

    HistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        order_date = itemView.findViewById(R.id.order_date);
        order_price = itemView.findViewById(R.id.order_amount_price);
        order_product_list = itemView.findViewById(R.id.order_product_list);
        order_logo = itemView.findViewById(R.id.order_logo);
    }
}