package com.lahaptech.lahap.user.detailproduct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lahaptech.lahap.Prevalent;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.Cart;
import com.lahaptech.lahap.model.Product;
import com.lahaptech.lahap.user.home.HomeUserActivity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

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
    String state="normal";


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

        btn_add_cart.setOnClickListener(v -> {
            if(state.equals("Order placed") || state.equals("Order shipped")){
                Toast.makeText(DetailActivity.this, "you can add purchase products, once your order is shipped or confirmed", Toast.LENGTH_LONG ).show();
            }
            else {
                addingToCartList();
            }
        });
    }

    private void addingToCartList() {
        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("cart list");
        final Cart cartMap = new Cart(foodID, name.getText().toString(),
                price.getText().toString(), numberButton.getNumber(),
                saveCurrentDate, saveCurrentTime,category, null);

        cartListRef.child("User View").child(Prevalent.CurrentOnlineUser.getName())
                .child("Products").child(foodID)
                .setValue(cartMap)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        cartListRef.child("Admin View").child(Prevalent.CurrentOnlineUser.getName())
                                .child("Products").child(foodID)
                                .setValue(cartMap)
                                .addOnCompleteListener(task1 -> {
                                    if(task1.isSuccessful()){
                                        Toast.makeText(DetailActivity.this, "Added to cart list", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(DetailActivity.this, HomeUserActivity.class);
                                        startActivity(intent);
                                    }
                                });
                    }
                });
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
