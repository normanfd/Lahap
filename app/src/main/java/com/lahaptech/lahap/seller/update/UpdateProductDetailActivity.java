package com.lahaptech.lahap.seller.update;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.Product;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

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
    @BindView(R.id.product_description_maintain)
    EditText desc;
    @BindView(R.id.product_image_maintain)
    ImageView imageView;
    @BindView(R.id.product_nutrition_detail_maintain)
    EditText nutrition;
    @BindView(R.id.product_menu_detail_maintain)
    EditText menu;


    private String productID = "";
    DocumentReference docRef;
    String pName, pPrice, pDesc, pImage, pMenu, pNutrition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product_detail);

        Calendar calendar = Calendar.getInstance();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        String saveCurrentDate = currentDate.format(calendar.getTime());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        String saveCurrentTime = currentTime.format(calendar.getTime());

        String ProductRandomKey = saveCurrentDate + saveCurrentTime;
        productID = ProductRandomKey.replaceAll("\\s+", "");

        String pid  = getIntent().getStringExtra("pid");
        String category = getIntent().getStringExtra("category");

        assert category != null;
        FirebaseFirestore productRef = FirebaseFirestore.getInstance();
        assert pid != null;
        docRef = productRef.collection("product").document(pid);


        ButterKnife.bind(this);

        displaySpecificProductInfo();

        applyChangesBtn.setOnClickListener(this);

        delProductBtn.setOnClickListener(this);

    }

    private void deleteThisProduct() {
        docRef.delete().addOnCompleteListener(task -> {
            Toast.makeText(UpdateProductDetailActivity.this,"Product deleted successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdateProductDetailActivity.this, UpdateProductActivity.class);
            startActivity(intent);
            finish();

        });


    }

    private void applyChanges() {
        pName = name.getText().toString();
        pPrice = price.getText().toString();
        pDesc = desc.getText().toString();
        pMenu = menu.getText().toString();
        pNutrition = nutrition.getText().toString();


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
            ProductMap.put("productID", productID);
            ProductMap.put("description", pDesc);
            ProductMap.put("price",pPrice);
            ProductMap.put("productName", pName);
            ProductMap.put("menuDetail", pMenu);
            ProductMap.put("nutritionDetail",pNutrition);

            docRef.update(ProductMap).addOnSuccessListener(aVoid -> {
                Toast.makeText(UpdateProductDetailActivity.this, "Change succesfully", Toast.LENGTH_SHORT).show();

                finish();

            }).addOnFailureListener(e ->
                    Toast.makeText(UpdateProductDetailActivity.this, "Change succesfully", Toast.LENGTH_SHORT).show());


        }
    }

    private void displaySpecificProductInfo() {
        docRef.addSnapshotListener((documentSnapshot, e) -> {
            if (documentSnapshot != null && documentSnapshot.exists()){
                Product productData = documentSnapshot.toObject(Product.class);
                assert productData != null;
                Log.d("cek product name", productData.getProductName());
                pName = productData.getProductName();
                pPrice = productData.getPrice();
                pDesc = productData.getDescription();
                pImage = productData.getImage();
                pMenu = productData.getMenuDetail();
                pNutrition = productData.getNutritionDetail();

                name.setText(pName);
                price.setText(pPrice);
                desc.setText(pDesc);
                nutrition.setText(pNutrition);
                menu.setText(pMenu);
                Picasso.get().load(pImage).into(imageView);
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
