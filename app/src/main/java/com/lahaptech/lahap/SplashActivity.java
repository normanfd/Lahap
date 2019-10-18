package com.lahaptech.lahap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.lahaptech.lahap.model.Prevalent;
import com.lahaptech.lahap.model.User;
import com.lahaptech.lahap.user.home.UserActivity;

import io.paperdb.Paper;

import static com.lahaptech.lahap.user.home.UserActivity.EXTRA_USER;

public class SplashActivity extends AppCompatActivity {

    private String UserName="", SellerID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Paper.init(this);

        UserName = Paper.book().read(Prevalent.UserName);
        SellerID = Paper.book().read(Prevalent.SellerID);

        int SPLASH_TIME_OUT = 1000;
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent intent = new Intent(SplashActivity.this, UserActivity.class);
                User user = new User();
                user.setUsername(UserName);
                intent.putExtra(EXTRA_USER, user);
                startActivity(intent);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}
