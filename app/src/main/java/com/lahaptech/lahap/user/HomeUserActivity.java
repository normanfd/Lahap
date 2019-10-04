package com.lahaptech.lahap.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

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
}
