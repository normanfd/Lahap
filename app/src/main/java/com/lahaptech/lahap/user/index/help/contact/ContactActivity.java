package com.lahaptech.lahap.user.index.help.contact;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lahaptech.lahap.R;

public class ContactActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        TextView address = findViewById(R.id.tv_address);
        address.setText("Jln. Alternatif Babakan Tengah, Kec. Dramaga, Bogor 16680, Indonesia");
    }
}
