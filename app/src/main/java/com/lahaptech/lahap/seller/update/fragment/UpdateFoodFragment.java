package com.lahaptech.lahap.seller.update.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.Prevalent;
import com.lahaptech.lahap.model.Product;
import com.lahaptech.lahap.seller.update.UpdateProductDetailActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateFoodFragment extends Fragment {

    @BindView(R.id.rv_update_product)
    RecyclerView recyclerView;

    public UpdateFoodFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_product, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        final Query query = rootRef.collection("product")
                .whereEqualTo("category", "food").whereEqualTo("sellerID", Prevalent.CurrentOnlineSeller.getSellerID())
                .orderBy("productName", Query.Direction.ASCENDING);

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                FirestoreRecyclerOptions<Product> options = new FirestoreRecyclerOptions.Builder<Product>()
                        .setQuery(query, Product.class)
                        .build();

                FirestoreRecyclerAdapter<Product, ProductViewHolder> adapter =
                        new FirestoreRecyclerAdapter<Product, ProductViewHolder>(options) {
                            @Override
                            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Product model) {
                                holder.productName.setText(model.getProductName());
                                holder.productDesc.setText(model.getDescription());
                                holder.productPrice.setText(model.getPrice());
                                Picasso.get().load(model.getImage()).into(holder.productImage);

                                holder.itemView.setOnClickListener(v -> {
                                    Intent intent = new Intent(getActivity(), UpdateProductDetailActivity.class);
                                    intent.putExtra("pid", model.getProductID());
                                    intent.putExtra("category", "food");
                                    startActivity(intent);
                                });

                            }

                            @NonNull
                            @Override
                            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_food, parent, false);
                                return new ProductViewHolder(view);
                            }
                        };

                recyclerView.setAdapter(adapter);
                adapter.startListening();
            }
        });
    }
}
