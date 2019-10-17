package com.lahaptech.lahap.user.orderlocation.indirectorder;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.User;
import com.lahaptech.lahap.user.payment.OnlinePaymentActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lahaptech.lahap.user.home.UserActivity.EXTRA_USER;

public class IndirectOrderFormActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tv_total_amount)
    TextView tv_total_amount;
    @BindView(R.id.tv_time_pick)
    TextView tv_time_pick;
    @BindView(R.id.next_btn)
    Button nextButton;
    String total_amount = "" , time_pick= "", locationID ="", saveCurrentDate, saveCurrentTime, orderID;
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
        currentOnlineUser = getIntent().getParcelableExtra(EXTRA_USER);

        tv_time_pick.setText(time_pick);
        tv_total_amount.setText(total_amount);

        Calendar calendar = Calendar.getInstance();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        orderID = saveCurrentDate + saveCurrentTime;

        nextButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.next_btn){
            String usernameIPB = currentOnlineUser.getUsername();
            String orderType = "indirect";
            loadingBar.setTitle("Indirect Order");
            loadingBar.setMessage("Please wait while we are saving your order..");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            saveToFirebase(usernameIPB, locationID, orderType, total_amount);
        }
    }

    private void saveToFirebase(String usernameIPB, String locationID, String orderType, String total) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> order = new HashMap<>();
        order.put("orderID", orderID);
        order.put("usernameIPB", usernameIPB);
        order.put("locationID", locationID);
        order.put("orderTime", time_pick);
        order.put("orderTable", null);
        order.put("orderType", orderType);
        order.put("orderStatus", "0");
        order.put("payMethod", "transfer");
        order.put("transferProof", null);
        order.put("totalAmount", total);

        db.collection("order").document(orderID)
                .set(order)

                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(IndirectOrderFormActivity.this, "Success Added", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Intent intent = new Intent(IndirectOrderFormActivity.this, OnlinePaymentActivity.class);
                    intent.putExtra("total_amount", total);
                    intent.putExtra("orderID", orderID);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {

                });

    }
}
