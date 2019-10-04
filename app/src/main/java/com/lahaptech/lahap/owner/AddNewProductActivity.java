package com.lahaptech.lahap.owner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.Product;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

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


    private String CategoryName, ProductDescription, ProductPrice, ProductName,
            ProductRandomKey, DownloadImageUrl, saveCurrentDate, saveCurrentTime;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private StorageReference ProductImageRef;
    private DatabaseReference ProductRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);
        CategoryName = getIntent().getExtras().get("category").toString();
        Log.i("test",CategoryName);
        ProductImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductRef = FirebaseDatabase.getInstance().getReference().child("Products");
        loadingBar = new ProgressDialog(this);

        ButterKnife.bind(this);

        inputProductImage.setOnClickListener(this);
        addNewProductButton.setOnClickListener(this);
    }

    private void ValidateProductData() {
        ProductName = inputProductName.getText().toString();
        ProductDescription = inputProductDescription.getText().toString();
        ProductPrice = inputProductPrice.getText().toString();

        if(ImageUri == null){
            Toast.makeText(this, "Product Image is mandatory...", Toast.LENGTH_SHORT).show();
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

        ProductRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = ProductImageRef.child(ImageUri.getLastPathSegment() + ProductRandomKey + ".jpg");
        final UploadTask UploadTask = filePath.putFile(ImageUri);
        UploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AddNewProductActivity.this, "Error" + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<com.google.firebase.storage.UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AddNewProductActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                //get Link Image
                UploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<com.google.firebase.storage.UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw Objects.requireNonNull(task.getException());
                        } else {
                            DownloadImageUrl = filePath.getDownloadUrl().toString();
                            return filePath.getDownloadUrl();
                        }
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            DownloadImageUrl = Objects.requireNonNull(task.getResult()).toString();
                            Toast.makeText(AddNewProductActivity.this, "got the product Image url succcesfully...", Toast.LENGTH_SHORT).show();
                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }

    private void SaveProductInfoToDatabase() {
        Product product = new Product(CategoryName, saveCurrentDate,
                ProductDescription, DownloadImageUrl, ProductRandomKey,
                ProductPrice, ProductName, saveCurrentTime);

        ProductRef.child(CategoryName).child(ProductRandomKey).setValue(product)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            loadingBar.dismiss();
                            Toast.makeText(AddNewProductActivity.this, "Product is added succesfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddNewProductActivity.this,HomeOwnerActivity.class);
                            startActivity(intent);
                        }else{
                            loadingBar.dismiss();
                            String message = Objects.requireNonNull(task.getException()).toString();
                            Toast.makeText(AddNewProductActivity.this, "Error " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
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
