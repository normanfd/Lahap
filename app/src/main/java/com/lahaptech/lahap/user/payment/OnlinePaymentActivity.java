package com.lahaptech.lahap.user.payment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.user.index.UserActivity;

import java.util.HashMap;
import java.util.Map;

public class OnlinePaymentActivity extends AppCompatActivity {


    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText txt_nama;
    String namaRekening;
    ProgressDialog loadingBar;

    @SuppressLint({"SetTextI18n", "ShowToast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_payment);
        String total_amount = getIntent().getStringExtra("total_amount");
        String orderID = getIntent().getStringExtra("orderID");
        String userID = getIntent().getStringExtra("userID");
        TextView totalAmount = findViewById(R.id.tv_total_amount);
        totalAmount.setText("Rp" + total_amount + ",00");
        Button button = findViewById(R.id.btn_payment);
        button.setOnClickListener(view -> {
            DialogForm(orderID, userID);
        });
    }

    @SuppressLint("InflateParams")
    private void DialogForm(String orderID, String userID) {
        dialog = new AlertDialog.Builder(OnlinePaymentActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_transfer, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Informasi Rekening");

        txt_nama    = dialogView.findViewById(R.id.nama_rekening);
        txt_nama.setText(null);

        dialog.setPositiveButton("SUBMIT", (dialog, which) -> {
            namaRekening    = txt_nama.getText().toString();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String, Object> transfer = new HashMap<>();
            transfer.put("namaRekening", namaRekening);
            transfer.put("userID", userID);
            db.collection("transfer").document(orderID).set(transfer);

            updateStatusOrder(userID);

            dialog.dismiss();
            Intent intent = new Intent(OnlinePaymentActivity.this, UserActivity.class);
            startActivity(intent);
            finish();
        });

        dialog.setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());

        dialog.show();
    }

    private void updateStatusOrder(String userID){

        FirebaseFirestore productRef = FirebaseFirestore.getInstance();
        assert userID != null;
        DocumentReference docRef = productRef.collection("order").document(userID);
        docRef.update("orderStatus", "1");
    }
}
