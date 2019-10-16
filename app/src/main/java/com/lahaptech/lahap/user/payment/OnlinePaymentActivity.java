package com.lahaptech.lahap.user.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.lahaptech.lahap.R;

public class OnlinePaymentActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_payment);
        String total_amount = getIntent().getStringExtra("total_amount");
        TextView totalAmount = findViewById(R.id.tv_total_amount);
        totalAmount.setText("Rp" + total_amount + ",00");
    }
}
