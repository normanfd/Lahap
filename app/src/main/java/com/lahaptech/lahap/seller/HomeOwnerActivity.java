package com.lahaptech.lahap.seller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.lahaptech.lahap.main_activity.MainActivity;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.Seller;
import com.lahaptech.lahap.seller.order.CheckOrderActivity;
import com.lahaptech.lahap.seller.update.UpdateProductActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;

public class HomeOwnerActivity extends AppCompatActivity implements View.OnClickListener {
    public static String EXTRA_SELLER = "extra seller";
    Seller seller;
    @BindView(R.id.ll_food)
    LinearLayout food;
    @BindView(R.id.ll_drink)
    LinearLayout drink;
    @BindView(R.id.ll_snack)
    LinearLayout snack;
    @BindView(R.id.admin_logout_btn)
    Button adminLogoutBtn;
    @BindView(R.id.admin_check_order_btn)
    Button checkOrderBtn;
    @BindView(R.id.maintain_btn)
    Button maintainProductBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_owner);
        ButterKnife.bind(this);
        Paper.init(this);

        seller = getIntent().getParcelableExtra(EXTRA_SELLER);
        assert seller != null;
        Log.d("SELLER NAME", seller.getSellerID());
        adminLogoutBtn.setOnClickListener(this);

        checkOrderBtn.setOnClickListener(this);
        food.setOnClickListener(this);
        drink.setOnClickListener(this);
        snack.setOnClickListener(this);
        maintainProductBtn.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId()){
            case R.id.admin_logout_btn:
                intent = new Intent(HomeOwnerActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Paper.book().destroy();
                startActivity(intent);
                finish();
                break;

            case R.id.admin_check_order_btn:
                intent = new Intent(HomeOwnerActivity.this, CheckOrderActivity.class);
                intent.putExtra(EXTRA_SELLER, seller);
                startActivity(intent);
                break;

            case R.id.ll_food:
                intent = new Intent(HomeOwnerActivity.this, AddNewProductActivity.class);
                intent.putExtra(EXTRA_SELLER, seller);
                intent.putExtra("category", "food");
                startActivity(intent);
                break;

            case R.id.ll_drink:
                intent = new Intent(HomeOwnerActivity.this, AddNewProductActivity.class);
                intent.putExtra(EXTRA_SELLER, seller);
                intent.putExtra("category", "drink");
                startActivity(intent);
                break;

            case R.id.ll_snack:
                intent = new Intent(HomeOwnerActivity.this, AddNewProductActivity.class);
                intent.putExtra(EXTRA_SELLER, seller);
                intent.putExtra("category", "snack");
                startActivity(intent);
                break;

            case R.id.maintain_btn:
                intent = new Intent(HomeOwnerActivity.this, UpdateProductActivity.class);
                intent.putExtra(EXTRA_SELLER, seller);
                startActivity(intent);
                break;
        }

    }
}
