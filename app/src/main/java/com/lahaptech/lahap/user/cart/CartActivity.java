package com.lahaptech.lahap.user.cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lahaptech.lahap.Prevalent;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.Cart;
import com.lahaptech.lahap.user.home.SelectMenuActivity;
import com.lahaptech.lahap.user.orderlocation.OrderLocationActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        nextProcessBtn.setOnClickListener(v -> {
//            txtTotalAmount.setText("Total Price = " + String.valueOf(overTotalPrice));
            Intent intent = new Intent(CartActivity.this, OrderLocationActivity.class);
            intent.putExtra("Total Price", String.valueOf(overTotalPrice));
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkOrderState();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("cart list");
        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartListRef.child("User View")
                                .child(Prevalent.CurrentOnlineUser.getName())
                                .child("Products"), Cart.class)
                        .build();

        FirebaseRecyclerAdapter<Cart, CartAdapter> adapter
                = new FirebaseRecyclerAdapter<Cart, CartAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartAdapter holder, int position, @NonNull final Cart model) {
                holder.txtProductName.setText(model.getProductName());
                holder.txtProductQuantity.setText(" quantity = " + model.getQuantity());
                holder.txtProductPrice.setText("price " + model.getPrice());
//                Picasso.get().load(model.get()).into(holder.photo);

                int oneTypeProductPrice = Integer.valueOf(model.getPrice()) * Integer.valueOf(model.getQuantity());
                overTotalPrice = overTotalPrice + oneTypeProductPrice;

                holder.itemView.setOnClickListener(v -> {
                    CharSequence[] options1 = new CharSequence[]
                            {
//                                        "Edit",
                                    "Remove"
                            };
                    AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                    builder.setTitle("Cart Options");

                    builder.setItems(options1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            //Jika indeks == 0 atau edit maka kita lempar ke Product detail activity dengan pid tertentu
//                                if(i == 0){
//                                    Intent intent =new Intent(CartActivity.this, ProductDetailActivity.class);
//                                    intent.putExtra("pid", model.getPid());
//                                    startActivity(intent);
//                                }
                            // Jika indeks == 1 atau Remove maka hapus dengan pid tertentu
                            if (i == 0) {
                                cartListRef.child("User View")
                                        .child(Prevalent.CurrentOnlineUser.getPhone())
                                        .child("Products")
                                        .child(model.getPid())
                                        .removeValue()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(CartActivity.this, "Item removed successfully", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(CartActivity.this, SelectMenuActivity.class);
                                                    startActivity(intent);
                                                }
                                            }
                                        });
                            }

                        }
                    });
                    builder.show();
                });
            }

            @NonNull
            @Override
            public CartAdapter onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_layout, parent, false);
                return new CartAdapter(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
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
