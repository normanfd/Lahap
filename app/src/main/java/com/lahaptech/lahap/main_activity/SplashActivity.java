package com.lahaptech.lahap.main_activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.Prevalent;
import com.lahaptech.lahap.model.User;
import com.lahaptech.lahap.user.index.UserActivity;

import io.paperdb.Paper;

import static com.lahaptech.lahap.user.index.UserActivity.EXTRA_USER;

public class SplashActivity extends AppCompatActivity {

    private String UserName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Paper.init(this);
        int SPLASH_TIME_OUT = 1000;

        //check storage object data
        UserName = Paper.book().read(Prevalent.UserName);

        new Handler().postDelayed(() -> {
            // Kondisi belum login
            if(TextUtils.isEmpty(UserName)){
                Intent intent = new Intent(SplashActivity.this, SlideActivity.class);
                startActivity(intent);
            }
            // Kondisi sudah login (User)
            else if (!TextUtils.isEmpty(UserName)){
                Intent intent = new Intent(SplashActivity.this, UserActivity.class);
                User user = new User();
                user.setUsername(UserName);
                intent.putExtra(EXTRA_USER, user);
                startActivity(intent);
            }
            finish();
        }, SPLASH_TIME_OUT);

    }
}
