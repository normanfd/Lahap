package com.lahaptech.lahap.user.orderlocation.indirectorder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.protobuf.StringValue;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.User;

import java.util.Calendar;

import static com.lahaptech.lahap.user.index.UserActivity.EXTRA_USER;
import static com.lahaptech.lahap.user.menuproduct.SelectMenuActivity.CANTEEN_QR_CODE;

public class IndirectOrderActivity extends AppCompatActivity{


    TimePicker picker;
    Button btnGet;
    TextView tvw;
    String totalAmount="", locationID="", productList="";
    User currentOnlineUser;
    Calendar calendar = Calendar.getInstance();

    @SuppressLint({"ShowToast", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indirect_order);

        locationID = getIntent().getStringExtra(CANTEEN_QR_CODE);
        totalAmount = getIntent().getStringExtra("TotalPrice");
        productList = getIntent().getStringExtra("productList");

        currentOnlineUser = getIntent().getParcelableExtra(EXTRA_USER);

        picker= findViewById(R.id.timePicker1);
        picker.setIs24HourView(true);
        btnGet= findViewById(R.id.button1);

        Calendar calendar = Calendar.getInstance();
        int hourNow = calendar.get(Calendar.HOUR_OF_DAY);
        int minuteNow = calendar.get(Calendar.MINUTE);

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

            if (hour <= hourNow && minute <= minuteNow){
                Toast.makeText(this, "Maaf, silakan pilih waktu yang valid", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(IndirectOrderActivity.this, IndirectOrderFormActivity.class);
                intent.putExtra("timeOrder", hour + ":" + minute);
                intent.putExtra("totalAmount", totalAmount);
                intent.putExtra("qrcode", locationID);
                intent.putExtra("productList", productList);
                intent.putExtra(EXTRA_USER, currentOnlineUser);

                startActivity(intent);
                finish();
            }


            // Validasi tanggal

//            Log.d("JAM", String.valueOf(hour));
//            Log.d("MINUTE", String.valueOf(minute));
//            Log.d("JAM", String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
//            Log.d("MINUTE", String.valueOf(calendar.get(Calendar.MINUTE)));
//
//            if (hour <= calendar.get(Calendar.HOUR_OF_DAY) && minute <= (calendar.get(Calendar.MINUTE))){
//                Log.d("JAM", "nice");
//            }


        });
    }
}
