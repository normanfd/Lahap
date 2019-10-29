package com.lahaptech.lahap.user.orderlocation.directorder;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.User;
import com.lahaptech.lahap.user.index.UserActivity;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static com.lahaptech.lahap.user.index.UserActivity.EXTRA_USER;

public class DirectOrderFormActivity extends AppCompatActivity {

    TextView tv_table_no, radiobtn, tv_total_amount, tv_product_list;
//    RadioGroup rdo_payment, rdo_btnCash;
    Button btn;
    User currentOnlineUser;
    String total="", locationIDWithNumber ="", orderTable = "", productList="", saveCurrentDate, saveCurrentTime, orderID, usernameIPB="";
    ProgressDialog loadingBar;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_order_form);

        loadingBar = new ProgressDialog(this);

        orderTable = getIntent().getStringExtra("orderTableNo");
        productList = getIntent().getStringExtra("productList");
        locationIDWithNumber = getIntent().getStringExtra("qrcode");
        total = getIntent().getStringExtra("TotalPrice");
        String total_price = NumberFormat.getNumberInstance(Locale.US).format(Integer.valueOf(total));

        currentOnlineUser = getIntent().getParcelableExtra(EXTRA_USER);

        String[] locationID = locationIDWithNumber.split(",", 2);

        tv_total_amount = findViewById(R.id.tv_total_amount);
        tv_table_no = findViewById(R.id.tv_table_no);
        tv_product_list = findViewById(R.id.tv_productlist);

        btn = findViewById(R.id.order_next_btn);
        tv_table_no.setText(orderTable);
        tv_total_amount.setText("Rp" + total_price +".-");
        tv_product_list.setText(productList);
        
        usernameIPB = currentOnlineUser.getUsername();



        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime = currentTime.format(calendar.getTime());
        orderID = usernameIPB + saveCurrentDate + saveCurrentTime;

        btn.setOnClickListener(view -> {
            String orderType = "direct";
//            int radioID = rdo_payment.getCheckedRadioButtonId();
//            radiobtn = findViewById(radioID);
            String payMethod = "Cash";
            loadingBar.setTitle("Direct Order");
            loadingBar.setMessage("Please wait while we are saving your order..");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            deleteCart(usernameIPB);
            saveToFirebase(usernameIPB, locationID[0], orderTable, saveCurrentTime, saveCurrentDate, orderType, payMethod, total, productList, orderID);

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
        order.put("orderStatus", "1");
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
                    intent.putExtra(EXTRA_USER, currentOnlineUser);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {

                });
    }

    private void deleteCart(String usernameIPB) {
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference itemsRef = rootRef.collection("cart");
        Query query = itemsRef.whereEqualTo("username", usernameIPB);
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    itemsRef.document(document.getId()).delete();
                }
            } else {
                Log.d("ERROR", "Error getting documents: ", task.getException());
            }
        });

    }

}
