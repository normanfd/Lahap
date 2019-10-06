package com.lahaptech.lahap.owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.owner.fragment.UpdateDrinkFragment;
import com.lahaptech.lahap.owner.fragment.UpdateFoodFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateProductActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.tab_layout_update_product)
    TabLayout tabLayout;
    @BindView(R.id.view_pager_owner)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        ButterKnife.bind(this);

        UpdateProductViewPager adapter = new UpdateProductViewPager(getSupportFragmentManager());

        adapter.addFragment(new UpdateFoodFragment(), getResources().getString(R.string.food));
        adapter.addFragment(new UpdateDrinkFragment(), getResources().getString(R.string.drink));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    public void onClick(View view) {

    }
}