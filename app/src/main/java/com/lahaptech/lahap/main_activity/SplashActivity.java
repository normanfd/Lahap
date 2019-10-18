package com.lahaptech.lahap.main_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.Prevalent;
import com.lahaptech.lahap.model.Seller;
import com.lahaptech.lahap.model.User;
import com.lahaptech.lahap.seller.HomeOwnerActivity;
import com.lahaptech.lahap.user.index.UserActivity;

import io.paperdb.Paper;

import static com.lahaptech.lahap.seller.HomeOwnerActivity.EXTRA_SELLER;
import static com.lahaptech.lahap.user.index.UserActivity.EXTRA_USER;

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

        /*
         * Showing splash screen with a timer. This will be useful when you
         * want to show case your app logo / company
         */
        new Handler().postDelayed(() -> {
            // This method will be executed once the timer is over
            // Start your app main activity

            // Belum Login
            if(TextUtils.isEmpty(UserName) && TextUtils.isEmpty(SellerID)){
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
            // Sudah login user
            else if (!TextUtils.isEmpty(UserName) && TextUtils.isEmpty(SellerID)){
                Intent intent = new Intent(SplashActivity.this, UserActivity.class);
                User user = new User();
                user.setUsername(UserName);
                intent.putExtra(EXTRA_USER, user);
                startActivity(intent);
            }
            // Sudah login seller
            else {
                Intent intent = new Intent(SplashActivity.this, HomeOwnerActivity.class);
                Seller seller = new Seller();
                seller.setSellerID(SellerID);
                intent.putExtra(EXTRA_SELLER, seller);
                startActivity(intent);
            }

            // close this activity
            finish();
        }, SPLASH_TIME_OUT);

    }
}
