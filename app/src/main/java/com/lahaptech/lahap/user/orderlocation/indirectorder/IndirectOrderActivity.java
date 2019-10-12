package com.lahaptech.lahap.user.orderlocation.indirectorder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.lahaptech.lahap.R;

public class IndirectOrderActivity extends AppCompatActivity{


    TimePicker picker;
    Button btnGet;
    TextView tvw;
    String totalAmount;

    @SuppressLint({"ShowToast", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_indirect_order);

        String locationID = getIntent().getStringExtra("qrcode");
        tvw= findViewById(R.id.textView1);
        picker= findViewById(R.id.timePicker1);
        picker.setIs24HourView(true);
        btnGet= findViewById(R.id.button1);
        totalAmount = getIntent().getStringExtra("TotalPrice");
        btnGet.setOnClickListener(v -> {
            int hour, minute;
            if (Build.VERSION.SDK_INT >= 23 ){
                hour = picker.getHour();
                minute = picker.getMinute();
            }
            else{
                hour = picker.getCurrentHour();
                minute = picker.getCurrentMinute();
            }

            Intent intent = new Intent(IndirectOrderActivity.this, IndirectOrderFormActivity.class);
            intent.putExtra("timeOrder", hour + ":" + minute);
            intent.putExtra("totalAmount", totalAmount);
            intent.putExtra("qrcode", locationID);


            startActivity(intent);
        });
    }
}
