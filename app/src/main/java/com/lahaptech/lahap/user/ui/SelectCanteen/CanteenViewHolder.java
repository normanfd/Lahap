package com.lahaptech.lahap.user.ui.SelectCanteen;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lahaptech.lahap.BuildConfig;
import com.lahaptech.lahap.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CanteenViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_canteen)
    TextView item_canteen;
    public CanteenViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
