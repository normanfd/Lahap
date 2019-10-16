package com.lahaptech.lahap.user.orderlocation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lahaptech.lahap.R;
import com.lahaptech.lahap.user.orderlocation.directorder.DirectOrderActivity;
import com.lahaptech.lahap.user.orderlocation.indirectorder.IndirectOrderActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lahaptech.lahap.user.menuproduct.SelectMenuActivity.CANTEEN_ID;
import static com.lahaptech.lahap.user.menuproduct.SelectMenuActivity.CANTEEN_QR_CODE;

public class OrderLocationActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.btn_direct_order)
    Button btn_direct;
    @BindView(R.id.btn_not_direct_order)
    Button btn_indirect_order;
    String canteenID="";
    String canteenCode = "";
    String totalPrice = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_location);
        ButterKnife.bind(this);

        totalPrice = getIntent().getStringExtra("TotalPrice");
        canteenID = getIntent().getStringExtra(CANTEEN_ID);
        canteenCode = Objects.requireNonNull(getIntent().getStringExtra(CANTEEN_QR_CODE));

        btn_direct.setOnClickListener(this);
        btn_indirect_order.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_direct_order){
            Toast.makeText(this, "Direct Order", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(OrderLocationActivity.this, DirectOrderActivity.class);
            intent.putExtra(CANTEEN_ID, canteenID);
            intent.putExtra(CANTEEN_QR_CODE, canteenCode);
            intent.putExtra("TotalPrice", totalPrice);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(this, "Indirect Order", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(OrderLocationActivity.this, IndirectOrderActivity.class);
            intent.putExtra(CANTEEN_ID, canteenID);
            intent.putExtra(CANTEEN_QR_CODE, canteenCode);
            intent.putExtra("TotalPrice", totalPrice);
            startActivity(intent);
            finish();
        }
    }
}
