package com.lahaptech.lahap.user.orderlocation.indirectorder;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.lahaptech.lahap.user.payment.OnlinePaymentActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lahaptech.lahap.user.index.UserActivity.EXTRA_USER;

public class IndirectOrderFormActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tv_total_amount)
    TextView tv_total_amount;
    @BindView(R.id.tv_time_pick)
    TextView tv_time_pick;
    @BindView(R.id.next_btn)
    Button nextButton;
    String total_amount = "" , time_pick= "", locationID ="", product_list="", usernameIPB="", saveCurrentDate, saveCurrentTime, orderID;
    User currentOnlineUser;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indirect_form);
        ButterKnife.bind(this);

        loadingBar = new ProgressDialog(this);

        locationID = getIntent().getStringExtra("qrcode");
        time_pick = getIntent().getStringExtra("timeOrder");
        total_amount = getIntent().getStringExtra("totalAmount");
        product_list = getIntent().getStringExtra("productList");
        currentOnlineUser = getIntent().getParcelableExtra(EXTRA_USER);

        tv_time_pick.setText(time_pick);
        tv_total_amount.setText(total_amount);

        Calendar calendar = Calendar.getInstance();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime = currentTime.format(calendar.getTime());
        usernameIPB = currentOnlineUser.getUsername();

        orderID = usernameIPB + saveCurrentDate + saveCurrentTime;

        nextButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.next_btn){
            String orderType = "indirect";
            loadingBar.setTitle("Indirect Order");
            loadingBar.setMessage("Please wait while we are saving your order..");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            saveToFirestore(usernameIPB, locationID, orderType, total_amount, product_list, saveCurrentDate);
        }
    }

    private void saveToFirestore(String usernameIPB, String locationID, String orderType, String total, String productList, String saveCurrentDate) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> order = new HashMap<>();
        order.put("orderID", orderID);
        order.put("usernameIPB", usernameIPB);
        order.put("locationID", locationID);
        order.put("orderTime", time_pick);
        order.put("orderDate", saveCurrentDate);
        order.put("orderTable", null);
        order.put("orderType", orderType);
        order.put("orderStatus", "0");
        order.put("payMethod", "transfer");
        order.put("transferProof", null);
        order.put("totalAmount", total);
        order.put("productList", productList);

        db.collection("order").document(usernameIPB)
                .set(order)
                .addOnSuccessListener(aVoid -> {
                    deleteCart(usernameIPB);
                    Toast.makeText(IndirectOrderFormActivity.this, "Success Added", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Intent intent = new Intent(IndirectOrderFormActivity.this, OnlinePaymentActivity.class);
                    intent.putExtra("total_amount", total);
                    intent.putExtra("orderID", orderID);
                    intent.putExtra("userID", usernameIPB);
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
