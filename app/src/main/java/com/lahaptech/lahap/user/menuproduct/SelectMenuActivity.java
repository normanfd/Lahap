package com.lahaptech.lahap.user.menuproduct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.lahaptech.lahap.MainActivity;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.user.cart.CartActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;

public class SelectMenuActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String CANTEEN_ID = "canteen id";
    @BindView(R.id.tab_layout_homeuser)
    TabLayout tabLayout;
    @BindView(R.id.view_page_fav)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);
        ButterKnife.bind(this);
        Paper.init(this);
//        String test = getIntent().getStringExtra(CANTEEN_ID);

//        Log.d("TESTING", test);

        SelectMenuViewPager adapter = new SelectMenuViewPager(getSupportFragmentManager());

        adapter.addFragment(new FoodFragment(), getResources().getString(R.string.food));
        adapter.addFragment(new DrinkFragment(), getResources().getString(R.string.drink));
        adapter.addFragment(new SnackFragment(), getResources().getString(R.string.snack));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_account_circle_black_24dp));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.setting) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }else {
            Paper.book().destroy();
            Intent intent = new Intent(SelectMenuActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.fab){
            Intent intent = new Intent(SelectMenuActivity.this, CartActivity.class);
            startActivity(intent);
        }
    }
}


