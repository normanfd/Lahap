package com.lahaptech.lahap.user.home.orderstatus;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lahaptech.lahap.R;

public class OrderViewHolder extends RecyclerView.ViewHolder {

    TextView id_order, totalAmount, orderType, orderStatus;
    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        id_order = itemView.findViewById(R.id.id_order);
        totalAmount = itemView.findViewById(R.id.tv_total_amount);
        orderType = itemView.findViewById(R.id.tv_order_type);
        orderStatus = itemView.findViewById(R.id.tv_order_status);

    }
}
