package com.lahaptech.lahap.user.home.selectcanteen;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lahaptech.lahap.R;

class CanteenViewHolder extends RecyclerView.ViewHolder {

    TextView item_canteen;
    CanteenViewHolder(@NonNull View itemView) {
        super(itemView);
        item_canteen = itemView.findViewById(R.id.item_canteen);
    }
}
