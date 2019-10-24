package com.lahaptech.lahap.user.index;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.lahaptech.lahap.main_activity.MainActivity;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.Prevalent;
import com.lahaptech.lahap.model.Seller;
import com.lahaptech.lahap.seller.HomeSellerActivity;

import io.paperdb.Paper;

import static com.lahaptech.lahap.seller.HomeSellerActivity.EXTRA_SELLER;

public class UserActivity extends AppCompatActivity {

    public static final String EXTRA_USER = "extra_person";
    private AppBarConfiguration mAppBarConfiguration;
    boolean doubleBackToExitPressedOnce = false;
    Seller sellerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_select_canteen, R.id.nav_order_status, R.id.nav_order_history,
                R.id.nav_help, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //parcelable modek User
//        User user = getIntent().getParcelableExtra(EXTRA_USER);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.user_logout) {
            Paper.book().destroy();
            Intent intent = new Intent(UserActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce=false, 2000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Paper.init(this);
        String UserName = Paper.book().read(Prevalent.UserName);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);
        String SellerID = Paper.book().read(Prevalent.SellerID);
        String SellerPassword = Paper.book().read(Prevalent.SellerPassword);

        if (TextUtils.isEmpty(UserName) && TextUtils.isEmpty(UserPasswordKey)) {
            if (!TextUtils.isEmpty(SellerID) && !TextUtils.isEmpty(SellerPassword)){
                Intent intent = new Intent(UserActivity.this, HomeSellerActivity.class);
                sellerData.setSellerID(SellerID);
                intent.putExtra(EXTRA_SELLER, sellerData);
                startActivity(intent);
                finish();
            }
//            else {
//                Intent intent = new Intent(UserActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
        }

    }
}
