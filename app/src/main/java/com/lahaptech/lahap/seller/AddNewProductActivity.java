package com.lahaptech.lahap.seller;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.Prevalent;
import com.lahaptech.lahap.model.Seller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lahaptech.lahap.seller.HomeOwnerActivity.EXTRA_SELLER;

public class AddNewProductActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.add_new_product)
    Button addNewProductButton;
    @BindView(R.id.select_product_image)
    ImageView inputProductImage;
    @BindView(R.id.product_name)
    EditText inputProductName;
    @BindView(R.id.product_description)
    EditText inputProductDescription;
    @BindView(R.id.product_price)
    EditText inputProductPrice;
    @BindView(R.id.product_menu_detail)
    EditText inputMenuDetail;
    @BindView(R.id.product_nutrition_detail)
    EditText inputNutrition;


    private String ProductRandomKey;
    private String SellerID;
    private String ProductName;
    private String CategoryName;
    private String ProductDescription;
    private String ProductPrice;
    private String DownloadImageUrl;
    private String saveCurrentDate;
    private String saveCurrentTime;
    private String LocationID;
    private String MenuDetail;
    private String NutritionDetail;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private StorageReference ProductImageRef;
    private ProgressDialog loadingBar;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);
        CategoryName = Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("category")).toString();
        Log.i("test",CategoryName);
        ProductImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        loadingBar = new ProgressDialog(this);

        ButterKnife.bind(this);

        inputProductImage.setOnClickListener(this);
        addNewProductButton.setOnClickListener(this);
    }

    private void ValidateProductData() {
        ProductName = inputProductName.getText().toString();
        ProductDescription = inputProductDescription.getText().toString();
        ProductPrice = inputProductPrice.getText().toString();
        MenuDetail = inputMenuDetail.getText().toString();
        NutritionDetail = inputNutrition.getText().toString();

        if(ImageUri == null){
            Toast.makeText(this, "Please Upload Product Image", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(ProductName)){
            Toast.makeText(this, "Please write product name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(ProductDescription)){
            Toast.makeText(this, "Please write product description", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(ProductPrice)){
            Toast.makeText(this, "Please write product price", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(MenuDetail)){
            Toast.makeText(this, "Please write Menu Detail", Toast.LENGTH_SHORT).show();
        }
        else{
            StoreProductInformation();
        }

    }

    private void StoreProductInformation() {
        loadingBar.setTitle("Add new product");
        loadingBar.setMessage("please wait, while we are adding the new product..");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        ProductRandomKey = saveCurrentDate + saveCurrentTime + ProductName;
        ProductRandomKey = ProductRandomKey.replaceAll("\\s+", "");

        Seller seller = getIntent().getParcelableExtra(EXTRA_SELLER);
        assert seller != null;
        SellerID = seller.getSellerID();
        LocationID = seller.getLocationID();

        final StorageReference filePath = ProductImageRef.child(ProductRandomKey + ".jpg");

        // Compress Image
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
        byte[] img = byteArrayOutputStream.toByteArray();

        final UploadTask UploadTask = filePath.putBytes(img);
        UploadTask.addOnFailureListener(e -> {
            String message = e.toString();
            Toast.makeText(AddNewProductActivity.this, "Error" + message, Toast.LENGTH_SHORT).show();
            loadingBar.dismiss();
        }).addOnSuccessListener(taskSnapshot -> {
            Toast.makeText(AddNewProductActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
            //get Link Image
            UploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                } else {
                    DownloadImageUrl = filePath.getDownloadUrl().toString();
                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DownloadImageUrl = Objects.requireNonNull(task.getResult()).toString();
                    Toast.makeText(AddNewProductActivity.this, "got the product Image url succcesfully...", Toast.LENGTH_SHORT).show();
                    SaveProductToFirestore();
                }
            });
        });
    }

    private void SaveProductToFirestore(){
        String isAvailable = "true";

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> product = new HashMap<>();

        product.put("productID", ProductRandomKey);
        product.put("sellerID", SellerID);
        product.put("productName", ProductName);
        product.put("category", CategoryName);
        product.put("date", saveCurrentDate);
        product.put("description", ProductDescription);
        product.put("image", DownloadImageUrl);
        product.put("price", ProductPrice);
        product.put("time", saveCurrentTime);
        product.put("isAvailable", isAvailable);
        product.put("locationID", LocationID);
        product.put("menuDetail", MenuDetail);
        product.put("nutritionDetail", NutritionDetail);

        db.collection("product").document(ProductRandomKey).set(product).addOnSuccessListener(aVoid -> {
            loadingBar.dismiss();
            Toast.makeText(AddNewProductActivity.this, "Product is added successfully", Toast.LENGTH_SHORT).show();
            finish();
        }).addOnFailureListener(e -> {
            loadingBar.dismiss();
            String message = e.toString();
            Toast.makeText(AddNewProductActivity.this, "Error " + message, Toast.LENGTH_SHORT).show();
        });
    }


    //PICK IMAGE FROM GALLERY
    private void OpenGallery() {
        Intent GalleryIntent = new Intent();
        GalleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        GalleryIntent.setType("image/*");
        startActivityForResult(GalleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GalleryPick && resultCode == RESULT_OK && data!= null){
            ImageUri = data.getData();
            inputProductImage.setImageURI(ImageUri);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), ImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.select_product_image:
                OpenGallery();
                break;

            case R.id.add_new_product:
                ValidateProductData();
                break;
        }
    }
}
