package com.lahaptech.lahap.user.payment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lahaptech.lahap.R;

public class OnlinePaymentActivity extends AppCompatActivity {

    @SuppressLint({"SetTextI18n", "ShowToast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_payment);
        String total_amount = getIntent().getStringExtra("total_amount");
        String orderID = getIntent().getStringExtra("orderID");
        TextView totalAmount = findViewById(R.id.tv_total_amount);
        totalAmount.setText("Rp" + total_amount + ",00");
        Button button = findViewById(R.id.btn_payment);
        button.setOnClickListener(view -> {
            FirebaseFirestore productRef = FirebaseFirestore.getInstance();
            assert orderID != null;
            DocumentReference docRef = productRef.collection("order").document(orderID);
            docRef.update("transferProof", "1").addOnCompleteListener(task -> {
                finish();
                Toast.makeText(OnlinePaymentActivity.this,"Payment Successfully", Toast.LENGTH_SHORT);
            });

        });
    }
}
