package com.lahaptech.lahap.user.cart;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.Cart;
import com.lahaptech.lahap.model.Prevalent;
import com.lahaptech.lahap.model.User;
import com.lahaptech.lahap.user.orderlocation.OrderLocationActivity;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;

import static com.lahaptech.lahap.user.index.UserActivity.EXTRA_USER;
import static com.lahaptech.lahap.user.menuproduct.SelectMenuActivity.CANTEEN_ID;
import static com.lahaptech.lahap.user.menuproduct.SelectMenuActivity.CANTEEN_QR_CODE;

public class CartActivity extends AppCompatActivity {
    @BindView(R.id.next_process_btn)
    public
    Button nextProcessBtn;
    @BindView(R.id.rv_cart)
    RecyclerView recyclerView;

    @BindView(R.id.linear_kosong)
    LinearLayout cart_empty;

    String canteenCode = "";
    String canteenID = "";
    User currentOnlineUser;
    ProgressDialog loadingBar;
    StringBuilder productList = new StringBuilder(100);
    private String saveLocation="";
    public static String locationNow="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        loadingBar = new ProgressDialog(this);
        Paper.init(this);

//        Objects.requireNonNull(getSupportActionBar()).setTitle("Cart");
        canteenID = getIntent().getStringExtra(CANTEEN_ID);
        canteenCode = getIntent().getStringExtra(CANTEEN_QR_CODE);
        currentOnlineUser = getIntent().getParcelableExtra(EXTRA_USER);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listTest();
        listCart();
    }

    private void listTest() {
        FirebaseFirestore cartListRef = FirebaseFirestore.getInstance();
        final Query query = cartListRef.collection("cart")
                .whereEqualTo("username", currentOnlineUser.getUsername());
        query.addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                // Handle error
                //...
                return;
            }

            // Convert query snapshot to a list of chats
            assert snapshot != null;
            List<Cart> listcart = snapshot.toObjects(Cart.class);
            int total = 0;
            for (Cart cart : listcart) {
                total = total + (Integer.valueOf(cart.getPrice()) * (Integer.valueOf(cart.getQuantity())));
                Log.d("Test cart", String.valueOf(total));
            }

            Objects.requireNonNull(getSupportActionBar()).setTitle("Total : " + total);
            String totalPrice = String.valueOf(total);

            if (total == 0) {
                cart_empty.setVisibility(View.VISIBLE);
                nextProcessBtn.setVisibility(View.INVISIBLE);
            }

            nextProcessBtn.setOnClickListener(v -> {
                Intent intent = new Intent(CartActivity.this, OrderLocationActivity.class);
                intent.putExtra("TotalPrice", totalPrice);
                intent.putExtra("productList", productList.toString());
                intent.putExtra(CANTEEN_ID, canteenID);
                intent.putExtra(CANTEEN_QR_CODE, canteenCode);
                intent.putExtra(EXTRA_USER, currentOnlineUser);
                startActivity(intent);
                finish();
            });
        });
    }

    private void listCart() {
        FirebaseFirestore cartListRef = FirebaseFirestore.getInstance();
        final Query query = cartListRef.collection("cart")
                .whereEqualTo("username", currentOnlineUser.getUsername());

        query.addSnapshotListener((queryDocumentSnapshots, e) -> {
            FirestoreRecyclerOptions<Cart> options = new FirestoreRecyclerOptions.Builder<Cart>()
                    .setQuery(query, Cart.class)
                    .build();

            FirestoreRecyclerAdapter<Cart, CartAdapter> adapter =
                    new FirestoreRecyclerAdapter<Cart, CartAdapter>(options) {
                        @SuppressLint("SetTextI18n")
                        @Override
                        protected void onBindViewHolder(@NonNull CartAdapter holder, int position, @NonNull Cart model) {

                            String cartID = model.getUsername() + model.getProductID();
                            saveLocation = model.getLocationID();

                            DocumentReference documentReference = cartListRef.collection("cart").document(cartID);

                            holder.txtProductName.setText(model.getProductName());
                            holder.txtProductQuantity.setText(  "Quantity       = " + model.getQuantity() + " item");
                            holder.txtProductPrice.setText(     "Price          = Rp" + model.getPrice() + ",00");
                            productList = productList.append(model.getProductName()).append(" - ").append(model.getSellerID()).append(" - ").append(model.getQuantity()).append(" item").append(System.getProperty("line.separator"));

                            Log.d("Product list", productList.toString());
                            holder.itemView.setOnClickListener(view -> {
                                CharSequence[] options = new CharSequence[]{
                                        "Remove"
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                                builder.setTitle("Cart Options");

                                builder.setItems(options, (dialog, i) -> {
                                    if (i == 0) {
                                        documentReference.delete().addOnCompleteListener(task -> {
                                            Toast.makeText(CartActivity.this, "Item removed successfully", Toast.LENGTH_SHORT).show();
                                        });
                                    }

                                });
                                builder.show();
                            });
                        }

                        @NonNull
                        @Override
                        public CartAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_layout, parent, false);
                            return new CartAdapter(view1);
                        }
                    };

            recyclerView.setAdapter(adapter);
            adapter.startListening();

            assert queryDocumentSnapshots != null;
            Log.d("snapshot", String.valueOf(queryDocumentSnapshots.size()));

            if (queryDocumentSnapshots.size() == 0){
                Paper.book().delete(Prevalent.SaveLocation);
            }
        });
    }

}
