package com.lahaptech.lahap.owner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lahaptech.lahap.R;

public class HomeOwnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_owner);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
