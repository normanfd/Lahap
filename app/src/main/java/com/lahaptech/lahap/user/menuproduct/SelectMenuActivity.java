package com.lahaptech.lahap.user.menuproduct;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.User;
import com.lahaptech.lahap.user.cart.CartActivity;
import com.lahaptech.lahap.user.menuproduct.fragment.DrinkFragment;
import com.lahaptech.lahap.user.menuproduct.fragment.FoodFragment;
import com.lahaptech.lahap.user.menuproduct.fragment.SnackFragment;
import com.lahaptech.lahap.user.menuproduct.viewpager.SelectMenuViewPager;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;

import static com.lahaptech.lahap.user.home.UserActivity.EXTRA_USER;

public class SelectMenuActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String CANTEEN_ID = "canteen id";
    public static final String CANTEEN_QR_CODE = "canteen code";
    public static final String CANTEEN_NAME = "canteen name";
    @BindView(R.id.tab_layout_homeuser)
    TabLayout tabLayout;
    @BindView(R.id.view_page_fav)
    ViewPager viewPager;
    @BindView(R.id.fab)
    Button fab;
//    @BindView(R.id.fab)
//    FloatingActionButton fab;
    String canteenID = "", canteenCode = "", canteenName="";
    User currentOnlineUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_menu);
        ButterKnife.bind(this);
        Paper.init(this);

        currentOnlineUser = Objects.requireNonNull(getIntent().getParcelableExtra(EXTRA_USER));

        SelectMenuViewPager adapter = new SelectMenuViewPager(getSupportFragmentManager());

        canteenID = getIntent().getStringExtra(CANTEEN_ID);
        canteenCode = getIntent().getStringExtra(CANTEEN_QR_CODE);
        canteenName = getIntent().getStringExtra(CANTEEN_NAME);

        Objects.requireNonNull(getSupportActionBar()).setTitle(canteenName);

        adapter.addFragment(new FoodFragment(), getResources().getString(R.string.food));
        adapter.addFragment(new DrinkFragment(), getResources().getString(R.string.drink));
        adapter.addFragment(new SnackFragment(), getResources().getString(R.string.snack));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.fab){
            Intent intent = new Intent(SelectMenuActivity.this, CartActivity.class);
            intent.putExtra(CANTEEN_ID, canteenID);
            intent.putExtra(CANTEEN_QR_CODE, canteenCode);
            intent.putExtra(EXTRA_USER, currentOnlineUser);
            startActivity(intent);
        }
    }
}


