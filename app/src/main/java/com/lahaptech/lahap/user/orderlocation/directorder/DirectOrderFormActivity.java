package com.lahaptech.lahap.user.orderlocation.directorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.Prevalent;
import com.lahaptech.lahap.user.home.UserActivity;

import java.util.HashMap;
import java.util.Map;

public class DirectOrderFormActivity extends AppCompatActivity {

    TextView orderTableNo, username, tPrice, radiobtn;
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
            int radioID = rdo_payment.getCheckedRadioButtonId();
            radiobtn = findViewById(radioID);
            String payMethod = radiobtn.getText().toString();
            savetofirebase(usernameIPB, locationID, orderTable, orderType, payMethod, total);
//            Log.d("radio checked", payMethod);
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

        db.collection("order").document(usernameIPB)
                .set(order)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DirectOrderFormActivity.this, "Success Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DirectOrderFormActivity.this, UserActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}
