package com.lahaptech.lahap.user.cart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.lahaptech.lahap.Prevalent;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.Cart;
import com.lahaptech.lahap.model.Product;
import com.lahaptech.lahap.user.detailproduct.DetailActivity;
import com.lahaptech.lahap.user.menuproduct.SelectMenuActivity;
import com.lahaptech.lahap.user.orderlocation.OrderLocationActivity;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lahaptech.lahap.user.menuproduct.SelectMenuActivity.CANTEEN_ID;
import static com.lahaptech.lahap.user.menuproduct.SelectMenuActivity.CANTEEN_QR_CODE;

public class CartActivity extends AppCompatActivity {
    private int overTotalPrice = 0;

    @BindView(R.id.next_process_btn)
    public
    Button nextProcessBtn;
    @BindView(R.id.message1)
    public
    TextView txtmessage1;
//    @BindView(R.id.total_price)
//    public
//    TextView txtTotalAmount;
    @BindView(R.id.rv_cart)
    RecyclerView recyclerView;
    String canteenCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);

        String canteenID = getIntent().getStringExtra(CANTEEN_ID);
        canteenCode = Objects.requireNonNull(getIntent().getStringExtra(CANTEEN_QR_CODE));

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        nextProcessBtn.setOnClickListener(v -> {
//            txtTotalAmount.setText("Total Price = " + String.valueOf(overTotalPrice));
            Intent intent = new Intent(CartActivity.this, OrderLocationActivity.class);
            intent.putExtra("Total Price", String.valueOf(overTotalPrice));
            intent.putExtra(CANTEEN_ID, canteenID);
            intent.putExtra(CANTEEN_QR_CODE, canteenCode);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
//        checkOrderState();

        FirebaseFirestore cartListRef = FirebaseFirestore.getInstance();
        final Query query = cartListRef.collection("cart")
                .whereEqualTo("username", Prevalent.CurrentOnlineUser.getUsername());


        query.addSnapshotListener((queryDocumentSnapshots, e) -> {
            FirestoreRecyclerOptions<Cart> options = new FirestoreRecyclerOptions.Builder<Cart>()
                    .setQuery(query, Cart.class)
                    .build();

            FirestoreRecyclerAdapter<Cart, CartAdapter> adapter =
                    new FirestoreRecyclerAdapter<Cart, CartAdapter>(options) {
                        @SuppressLint("SetTextI18n")
                        @Override
                        protected void onBindViewHolder(@NonNull CartAdapter holder, int position, @NonNull Cart model) {

                            DocumentReference documentReference = cartListRef.collection("cart").document(model.getProductID());

                            holder.txtProductName.setText(model.getProductName());
                            holder.txtProductName.setText(model.getProductName());
                            holder.txtProductQuantity.setText(" quantity = " + model.getQuantity());
                            holder.txtProductPrice.setText("price " + model.getPrice());

                            int oneTypeProductPrice = Integer.valueOf(model.getPrice()) * Integer.valueOf(model.getQuantity());
                            overTotalPrice = overTotalPrice + oneTypeProductPrice;

                            holder.itemView.setOnClickListener(view -> {
                                CharSequence[] options = new CharSequence[]{
//                                        "Edit",
                                        "Remove"
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                                builder.setTitle("Cart Options");

                                builder.setItems(options, (dialog, i) -> {
//                                    if (i == 0) {
//                                        Intent intent = new Intent(CartActivity.this, DetailActivity.class);
//                                        intent.putExtra("pid", model.getProductID());
//                                        startActivity(intent);
//                                        finish();
//                                    }
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
        });
    }



    private void checkOrderState() {

        DatabaseReference ordersRef;
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.CurrentOnlineUser.getPhone());
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String shippingState = Objects.requireNonNull(dataSnapshot.child("state").getValue()).toString();
                    String userName = Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString();

                    if (shippingState.equals("shipped")) {
//                        txtTotalAmount.setText("Dear " + userName + "\n order is shipped successfully");
                        recyclerView.setVisibility(View.GONE);
                        txtmessage1.setVisibility(View.VISIBLE);
                        txtmessage1.setText(" Congratulations, your final order has been shipped successfully, soon you will receive your order at your door step");
                        Toast.makeText(CartActivity.this, "You can purchase more products, once you received your first final order", Toast.LENGTH_SHORT).show();
                    } else if (shippingState.equals("not shipped")) {
//                        txtTotalAmount.setText("Shipping State = Not Shipped");
                        recyclerView.setVisibility(View.GONE);
                        txtmessage1.setVisibility(View.VISIBLE);
                        Toast.makeText(CartActivity.this, "You can purchase more products, once you received your first final order", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
