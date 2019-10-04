package com.lahaptech.lahap.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.common.util.Base64Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.Product;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFoodActivity extends AppCompatActivity {

    @BindView(R.id.product_image_detail)
    ImageView photo;
    @BindView(R.id.product_name_detail)
    TextView name;
    @BindView(R.id.product_description_detail)
    TextView desc;
    @BindView(R.id.product_price_detail)
    TextView price;
    @BindView(R.id.number_btn)
    ElegantNumberButton numberButton;
    @BindView(R.id.pd_add_to_cart_btn)
    Button btn_add_cart;
    String foodID;
    String category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_food);

        ButterKnife.bind(this);
        foodID = getIntent().getStringExtra("pid");
        assert foodID != null;
        Log.i("PID", foodID);
        category = getIntent().getStringExtra("category");
        assert category != null;
        Log.i("CATEGORY", category);

        getProductDetail(foodID,category);
    }

    private void getProductDetail(String foodID, String category) {
        DatabaseReference productsref = FirebaseDatabase.getInstance().getReference().child("Products").child(category);
        productsref.child(foodID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Product product = dataSnapshot.getValue(Product.class);
                    assert product != null;
                    name.setText(product.getProductname());
                    price.setText(product.getPrice());
                    desc.setText(product.getDescription());
                    Picasso.get().load(product.getImage()).into(photo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
