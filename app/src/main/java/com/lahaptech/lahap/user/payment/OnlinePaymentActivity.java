package com.lahaptech.lahap.user.payment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lahaptech.lahap.R;

import java.util.HashMap;

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
