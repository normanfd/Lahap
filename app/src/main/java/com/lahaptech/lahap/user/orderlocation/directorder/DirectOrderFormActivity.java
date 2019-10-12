package com.lahaptech.lahap.user.orderlocation.directorder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.Prevalent;

public class DirectOrderFormActivity extends AppCompatActivity {

    TextView orderTableNo, username, tPrice;
    RadioGroup rdo_payment;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_order_form);
        String orderTable = getIntent().getStringExtra("orderTableNo");
        String locationID = getIntent().getStringExtra("qrcode");
        String total = getIntent().getStringExtra("TotalPrice");

        tPrice = findViewById(R.id.order_total_price);
        rdo_payment = findViewById(R.id.order_radio_payment);
        orderTableNo = findViewById(R.id.order_table_number);
        username = findViewById(R.id.order_username);
        btn = findViewById(R.id.order_next_btn);

        orderTableNo.setText(orderTable);
        username.setText(Prevalent.CurrentOnlineUser.getUsername());
        tPrice.setText(total);

        btn.setOnClickListener(view -> {
            String usernameIPB = Prevalent.CurrentOnlineUser.getUsername();
            String orderType = "direct";
            String orderStatus = "0";
            int payMethod = rdo_payment.getCheckedRadioButtonId();
//            savetofirebase(usernameIPB, locationID, orderTable, orderType, orderStatus, payMethod, totalAmount);
            Log.d("radio checked", "selected " + payMethod);
        });
    }

    private void savetofirebase(String usernameIPB, String locationID, String orderTable) {

    }
}
