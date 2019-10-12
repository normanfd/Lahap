package com.lahaptech.lahap.user.orderlocation.directorder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.Prevalent;

public class DirectOrderFormActivity extends AppCompatActivity {

    TextView noMeja, username;
    RadioButton transfer, cash;
    RadioGroup rdo_payment;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_order_form);
        String noMejaAcc = getIntent().getStringExtra("noMeja");

        rdo_payment = findViewById(R.id.order_radio_payment);
        noMeja = findViewById(R.id.order_table_number);
        username = findViewById(R.id.order_username);
        btn = findViewById(R.id.btnnn);
        noMeja.setText(noMejaAcc);
        username.setText(Prevalent.CurrentOnlineUser.getUsername());


        btn.setOnClickListener(view -> {
            int selectedId = rdo_payment.getCheckedRadioButtonId();
            Log.d("radio checked", "selected " + selectedId);
        });
    }
}
