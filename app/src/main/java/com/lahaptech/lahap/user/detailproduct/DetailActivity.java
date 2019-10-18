package com.lahaptech.lahap.user.detailproduct;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.Prevalent;
import com.lahaptech.lahap.model.Product;
import com.lahaptech.lahap.model.User;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lahaptech.lahap.user.index.UserActivity.EXTRA_USER;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.product_image_detail)
    ImageView photo;
    @BindView(R.id.product_name_detail)
    TextView name;
    @BindView(R.id.product_description_detail)
    TextView desc;
    @BindView(R.id.product_price_detail)
    TextView price;
    @BindView(R.id.menu_detail)
    TextView menu;
    @BindView(R.id.nutrition_detail)
    TextView nutrition;
    @BindView(R.id.number_btn)
    ElegantNumberButton numberButton;
    @BindView(R.id.pd_add_to_cart_btn)
    Button btn_add_cart;
    ProgressDialog loadingBar;

    String foodID, category, sellerID, locationID, productPrice="";
    String state="normal";
    User currentOnlineUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_food);

        ButterKnife.bind(this);
        loadingBar = new ProgressDialog(this);

        currentOnlineUser = getIntent().getParcelableExtra(EXTRA_USER);
        assert currentOnlineUser != null;
        Log.d("Coba kita lihat siapa", currentOnlineUser.getUsername());
        foodID = getIntent().getStringExtra("pid");
        category = getIntent().getStringExtra("category");
        sellerID = getIntent().getStringExtra("sellerID");
        locationID = getIntent().getStringExtra("locationID");

        assert category != null;
        Log.i("CATEGORY", category);

        getProductDetail(foodID);

        btn_add_cart.setOnClickListener(v -> {
            if(state.equals("Order placed") || state.equals("Order shipped")){
                Toast.makeText(DetailActivity.this, "you can add purchase products, once your order is shipped or confirmed", Toast.LENGTH_LONG ).show();
            }
            else {
                loadingBar.setTitle("Adding to cart");
                loadingBar.setMessage(getResources().getString(R.string.checking_credentials));
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
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

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> cart = new HashMap<>();
        cart.put("productName", name.getText().toString());
        cart.put("productID", foodID);
        cart.put("price", productPrice);
        cart.put("quantity", numberButton.getNumber());
        cart.put("date",saveCurrentDate);
        cart.put("time",saveCurrentTime);
        cart.put("category", category);
        cart.put("overview",null);
        cart.put("username", currentOnlineUser.getUsername());
        cart.put("sellerID", sellerID);
        cart.put("locationID", locationID);

        db.collection("cart").document(foodID)
                .set(cart).addOnCompleteListener(task -> {
                    loadingBar.dismiss();
                    Toast.makeText(DetailActivity.this, "Added to cart list", Toast.LENGTH_SHORT).show();
                    finish();
                }).addOnFailureListener(e -> { });
    }
    @SuppressLint("SetTextI18n")
    private void getProductDetail(String foodID) {
        FirebaseFirestore productRef = FirebaseFirestore.getInstance();
        DocumentReference docRef = productRef.collection("product").document(foodID);
        docRef.addSnapshotListener((documentSnapshot, e) -> {
            if (documentSnapshot != null && documentSnapshot.exists()){
                Product productData = documentSnapshot.toObject(Product.class);
                assert productData != null;
                name.setText(productData.getProductName());
                productPrice = productData.getPrice();
                String str = NumberFormat.getNumberInstance(Locale.US).format(Integer.valueOf(productPrice));
                price.setText("Rp" + str + ".00");
                desc.setText(productData.getDescription());
                menu.setText(productData.getMenuDetail());
                nutrition.setText(productData.getNutritionDetail());
                Picasso.get().load(productData.getImage()).resize(200,160).into(photo);
            }
        });

    }
}
