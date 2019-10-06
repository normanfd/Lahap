package com.lahaptech.lahap.owner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.lahaptech.lahap.MainActivity;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.owner.order.CheckOrderActivity;
import com.lahaptech.lahap.owner.update.UpdateProductActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeOwnerActivity extends AppCompatActivity implements View.OnClickListener {

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
                startActivity(intent);
                finish();
                break;

            case R.id.admin_check_order_btn:
                intent = new Intent(HomeOwnerActivity.this, CheckOrderActivity.class);
                startActivity(intent);
                break;

            case R.id.ll_food:
                intent = new Intent(HomeOwnerActivity.this, AddNewProductActivity.class);
                intent.putExtra("category", "food");
                startActivity(intent);
                break;

            case R.id.ll_drink:
                intent = new Intent(HomeOwnerActivity.this, AddNewProductActivity.class);
                intent.putExtra("category", "drink");
                startActivity(intent);
                break;

            case R.id.ll_snack:
                intent = new Intent(HomeOwnerActivity.this, AddNewProductActivity.class);
                intent.putExtra("category", "snack");
                startActivity(intent);
                break;

            case R.id.maintain_btn:
                intent = new Intent(HomeOwnerActivity.this, UpdateProductActivity.class);
                startActivity(intent);
                break;

        }

    }
}
