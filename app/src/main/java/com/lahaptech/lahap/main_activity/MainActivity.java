package com.lahaptech.lahap.main_activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.auth.LoginUserActivity;
import com.lahaptech.lahap.auth.RegisterActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_join;
    Button btn_login;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_join = findViewById(R.id.main_join_now_btn);
        btn_login = findViewById(R.id.main_login_btn);

        loadingBar = new ProgressDialog(this);

        btn_join.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.main_login_btn) {
            Intent intent = new Intent(MainActivity.this, LoginUserActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
