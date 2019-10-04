package com.lahaptech.lahap.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.lahaptech.lahap.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeUserActivity extends AppCompatActivity {

    @BindView(R.id.tab_layout_homeuser)
    TabLayout tabLayout;
    @BindView(R.id.view_page_fav)
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);
        ButterKnife.bind(this);

        HomeUserViewPager adapter = new HomeUserViewPager(getSupportFragmentManager());

        adapter.addFragment(new FoodFragment(), getResources().getString(R.string.food));
        adapter.addFragment(new DrinkFragment(), getResources().getString(R.string.drink));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_account_circle_black_24dp));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.profile) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
