package com.lahaptech.lahap.seller.update;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.seller.update.fragment.UpdateDrinkFragment;
import com.lahaptech.lahap.seller.update.fragment.UpdateFoodFragment;
import com.lahaptech.lahap.seller.update.fragment.UpdateSnackFragment;

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
        adapter.addFragment(new UpdateSnackFragment(), getResources().getString(R.string.snack));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}