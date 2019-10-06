package com.lahaptech.lahap.owner.update;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lahaptech.lahap.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateProductDetailActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.apply_chg_btn)
    Button applyChangesBtn;
    @BindView(R.id.delete_product_btn)
    Button delProductBtn;
    @BindView(R.id.product_name_maintain)
    EditText name;
    @BindView(R.id.product_price_maintain)
    EditText price;
    @BindView(R.id.product_desc_maintain)
    EditText desc;
    @BindView(R.id.product_image_maintain)
    ImageView imageView;



    private String productID = "";
    private DatabaseReference productRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product_detail);

        productID = getIntent().getStringExtra("pid");
        String category = getIntent().getStringExtra("category");

        assert category != null;
        productRef = FirebaseDatabase.getInstance().getReference().child("Products").child(category).child(productID);

        ButterKnife.bind(this);

        displaySpecificProductInfo();

        applyChangesBtn.setOnClickListener(this);

        delProductBtn.setOnClickListener(this);

    }

    private void deleteThisProduct() {
        productRef.removeValue().addOnCompleteListener(
                task -> Toast.makeText(
                        UpdateProductDetailActivity.this,"Product deleted successfully",
                        Toast.LENGTH_SHORT).show()
        );
    }

    private void applyChanges() {
        String pName = name.getText().toString();
        String pPrice = price.getText().toString();
        String pDesc = desc.getText().toString();

        if (pName.equals("")){
            Toast.makeText(this,"Write down product name", Toast.LENGTH_SHORT).show();
        }
        else if (pPrice.equals("")){
            Toast.makeText(this,"Write down product price", Toast.LENGTH_SHORT).show();

        }
        else if (pDesc.equals("")){
            Toast.makeText(this,"Write down product description", Toast.LENGTH_SHORT).show();

        }
        else {
            HashMap<String, Object> ProductMap = new HashMap<>();
            ProductMap.put("pid", productID);
            ProductMap.put("description", pDesc);
            ProductMap.put("price",pPrice);
            ProductMap.put("productname", pName);

            productRef.updateChildren(ProductMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Toast.makeText(UpdateProductDetailActivity.this, "Change succesfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(UpdateProductDetailActivity.this, UpdateProductActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    private void displaySpecificProductInfo() {
        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String pName = Objects.requireNonNull(dataSnapshot.child("productname").getValue()).toString();
                    String pPrice = Objects.requireNonNull(dataSnapshot.child("price").getValue()).toString();
                    String pDesc = Objects.requireNonNull(dataSnapshot.child("description").getValue()).toString();
                    String pImage = Objects.requireNonNull(dataSnapshot.child("image").getValue()).toString();

                    name.setText(pName);
                    price.setText(pPrice);
                    desc.setText(pDesc);
                    Picasso.get().load(pImage).into(imageView);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case (R.id.apply_chg_btn):
                applyChanges();
                break;
            case R.id.delete_product_btn:
                deleteThisProduct();
                break;
        }
    }
}
