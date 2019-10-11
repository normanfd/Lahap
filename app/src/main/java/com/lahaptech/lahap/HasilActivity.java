package com.lahaptech.lahap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class HasilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);

        String intent = getIntent().getStringExtra("qrcode");
        assert intent != null;
        Log.d("INI HASIL INTENT", intent);
    }
}
