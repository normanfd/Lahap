package com.lahaptech.lahap.user.orderlocation.directorder;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.User;
import com.lahaptech.lahap.user.index.UserActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.lahaptech.lahap.user.index.UserActivity.EXTRA_USER;

public class DirectOrderFormActivity extends AppCompatActivity {

    TextView orderTableNo, username, tPrice, radiobtn;
    RadioGroup rdo_payment;
    RadioButton rdo_btnCash;
    Button btn;
    User currentOnlineUser;
    String total="", locationID ="", orderTable = "", productList="", saveCurrentDate, saveCurrentTime, orderID;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_order_form);

        loadingBar = new ProgressDialog(this);

        orderTable = getIntent().getStringExtra("orderTableNo");
        productList = getIntent().getStringExtra("productList");
        locationID = getIntent().getStringExtra("qrcode");
        total = getIntent().getStringExtra("TotalPrice");
        currentOnlineUser = getIntent().getParcelableExtra(EXTRA_USER);

        tPrice = findViewById(R.id.order_total_price);
        rdo_payment = findViewById(R.id.order_radio_payment);
        orderTableNo = findViewById(R.id.order_table_number);
        username = findViewById(R.id.order_username);
        btn = findViewById(R.id.order_next_btn);
        rdo_btnCash = findViewById(R.id.rdo_btn_cash);
        rdo_btnCash.setChecked(true);

        orderTableNo.setText(orderTable);
        username.setText(currentOnlineUser.getUsername());
        tPrice.setText(total);

        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());
        orderID = saveCurrentDate + " " + saveCurrentTime;

        btn.setOnClickListener(view -> {
            String usernameIPB = currentOnlineUser.getUsername();
            String orderType = "direct";
            int radioID = rdo_payment.getCheckedRadioButtonId();
            radiobtn = findViewById(radioID);
            String payMethod = radiobtn.getText().toString();
            loadingBar.setTitle("Direct Order");
            loadingBar.setMessage("Please wait while we are saving your order..");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            saveToFirebase(usernameIPB, locationID, orderTable, saveCurrentTime, saveCurrentDate, orderType, payMethod, total, productList, orderID);
        });
    }

    private void saveToFirebase(
            String usernameIPB, String locationID, String orderTable, String saveCurrentTime, String saveCurrentDate,
            String orderType, String payMethod, String totalAmount, String productList, String orderID) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> order = new HashMap<>();
        order.put("usernameIPB", usernameIPB);
        order.put("locationID", locationID);
        order.put("orderTime", saveCurrentTime);
        order.put("orderDate", saveCurrentDate);
        order.put("orderTable", orderTable);
        order.put("orderType", orderType);
        order.put("orderStatus", "0");
        order.put("payMethod", payMethod);
        order.put("transferProof", null);
        order.put("totalAmount", totalAmount);
        order.put("productList", productList);
        order.put("orderID", orderID);

        db.collection("order").document(usernameIPB)
                .set(order)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(DirectOrderFormActivity.this, "Success Added", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Intent intent = new Intent(DirectOrderFormActivity.this, UserActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {

                });
    }

}
