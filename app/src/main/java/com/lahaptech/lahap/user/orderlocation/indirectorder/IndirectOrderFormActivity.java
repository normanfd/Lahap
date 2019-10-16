package com.lahaptech.lahap.user.orderlocation.indirectorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.Prevalent;
import com.lahaptech.lahap.user.home.UserActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndirectOrderFormActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tv_total_amount)
    TextView tv_total_amount;
    @BindView(R.id.tv_time_pick)
    TextView tv_time_pick;
    @BindView(R.id.next_btn)
    Button nextButton;

    String total_amount = "" , time_pick= "", locationID ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indirect_form);
        ButterKnife.bind(this);

        locationID = getIntent().getStringExtra("qrcode");


        time_pick = getIntent().getStringExtra("timeOrder");
        total_amount = getIntent().getStringExtra("totalAmount");

        tv_time_pick.setText(time_pick);
        tv_total_amount.setText(total_amount);

        nextButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.next_btn){
            String usernameIPB = Prevalent.CurrentOnlineUser.getUsername();
            String orderType = "indirect";
            saveToFirebase(usernameIPB, locationID, orderType, total_amount);

        }
    }

    private void saveToFirebase(String usernameIPB, String locationID, String orderType, String total) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> order = new HashMap<>();
        order.put("usernameIPB", usernameIPB);
        order.put("locationID", locationID);
        order.put("orderTime", time_pick);
        order.put("orderTable", null);
        order.put("orderType", orderType);
        order.put("orderStatus", "0");
        order.put("payMethod", "Transfer");
        order.put("transferProof", null);
        order.put("totalAmount", total);

        db.collection("order").document()
                .set(order)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(IndirectOrderFormActivity.this, "Success Added", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
}
