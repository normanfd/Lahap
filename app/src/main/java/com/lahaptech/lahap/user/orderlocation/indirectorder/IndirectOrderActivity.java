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
import com.lahaptech.lahap.model.User;

import static com.lahaptech.lahap.user.home.UserActivity.EXTRA_USER;

public class IndirectOrderActivity extends AppCompatActivity{


    TimePicker picker;
    Button btnGet;
    TextView tvw;
    String totalAmount="", locationID="";
    User currentOnlineUser;

    @SuppressLint({"ShowToast", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indirect_order);

        locationID = getIntent().getStringExtra("qrcode");
        totalAmount = getIntent().getStringExtra("TotalPrice");
        currentOnlineUser = getIntent().getParcelableExtra(EXTRA_USER);

        tvw= findViewById(R.id.textView1);
        picker= findViewById(R.id.timePicker1);
        picker.setIs24HourView(true);
        btnGet= findViewById(R.id.button1);

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
            intent.putExtra(EXTRA_USER, currentOnlineUser);

            startActivity(intent);
        });
    }
}
