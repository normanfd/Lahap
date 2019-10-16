package com.lahaptech.lahap.user.orderlocation.directorder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.Prevalent;
import com.lahaptech.lahap.model.User;
import com.lahaptech.lahap.user.home.UserActivity;

import java.util.HashMap;
import java.util.Map;

import static com.lahaptech.lahap.user.home.UserActivity.EXTRA_USER;

public class DirectOrderFormActivity extends AppCompatActivity {

    TextView orderTableNo, username, tPrice, radiobtn;
    RadioGroup rdo_payment;
    Button btn;
    User currentOnlineUser;
    String total="", locationID ="", orderTable = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_order_form);

        orderTable = getIntent().getStringExtra("orderTableNo");
        locationID = getIntent().getStringExtra("qrcode");
        total = getIntent().getStringExtra("TotalPrice");
        currentOnlineUser = getIntent().getParcelableExtra(EXTRA_USER);

        tPrice = findViewById(R.id.order_total_price);
        rdo_payment = findViewById(R.id.order_radio_payment);
        orderTableNo = findViewById(R.id.order_table_number);
        username = findViewById(R.id.order_username);
        btn = findViewById(R.id.order_next_btn);

        orderTableNo.setText(orderTable);
        username.setText(currentOnlineUser.getUsername());
        tPrice.setText(total);

        btn.setOnClickListener(view -> {
            String usernameIPB = currentOnlineUser.getUsername();
            String orderType = "direct";
            int radioID = rdo_payment.getCheckedRadioButtonId();
            radiobtn = findViewById(radioID);
            String payMethod = radiobtn.getText().toString();
            savetofirebase(usernameIPB, locationID, orderTable, orderType, payMethod, total);
        });
    }

    private void savetofirebase(
            String usernameIPB, String locationID, String orderTable,
            String orderType, String payMethod, String totalAmount) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> order = new HashMap<>();
        order.put("usernameIPB", usernameIPB);
        order.put("locationID", locationID);
        order.put("orderTime", null);
        order.put("orderTable", orderTable);
        order.put("orderType", orderType);
        order.put("orderStatus", "0");
        order.put("payMethod", payMethod);
        order.put("transferProof", null);
        order.put("totalAmount", totalAmount);

        db.collection("order").document()
                .set(order)

                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(DirectOrderFormActivity.this, "Success Added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DirectOrderFormActivity.this, UserActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {

                });
    }
}
