package com.lahaptech.lahap.owner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.lahaptech.lahap.MainActivity;
import com.lahaptech.lahap.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeOwnerActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.imgFood)
    ImageView food;
    @BindView(R.id.imgDrink)
    ImageView drink;
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
//
//        maintainProductBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AdminCategoryActivity.this, HomeActivity.class);
//                intent.putExtra("Admin", "Admin");
//                startActivity(intent);
//            }
//        });
//
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeOwnerActivity.this, AddNewProductActivity.class);
                intent.putExtra("category", "food");
                startActivity(intent);
            }
        });
//
//        konveksi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent =new Intent(AdminCategoryActivity.this, AddKonveksiActivity.class);
//                intent.putExtra("category", "konveksi");
//                startActivity(intent);
//            }
//        });
//
//        konsumsi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent =new Intent(AdminCategoryActivity.this, AddKonsumsiActivity.class);
//                intent.putExtra("category", "konsumsi");
//                startActivity(intent);
//            }
//        });

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.admin_logout_btn){
            Intent intent = new Intent(HomeOwnerActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        else if (view.getId()==R.id.admin_check_order_btn){
            Intent intent = new Intent(HomeOwnerActivity.this, CheckOrderActivity.class);
            startActivity(intent);
        }
    }
}
