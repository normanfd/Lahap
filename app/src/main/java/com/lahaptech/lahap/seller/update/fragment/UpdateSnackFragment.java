package com.lahaptech.lahap.seller.update.fragment;


import android.annotation.SuppressLint;
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
import com.lahaptech.lahap.model.Seller;
import com.lahaptech.lahap.seller.update.UpdateProductDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lahaptech.lahap.seller.HomeOwnerActivity.EXTRA_SELLER;


public class UpdateSnackFragment extends Fragment {

    @BindView(R.id.rv_update_product)
    RecyclerView recyclerView;

    public UpdateSnackFragment() {
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

        Seller seller = Objects.requireNonNull(getActivity()).getIntent().getParcelableExtra(EXTRA_SELLER);
        assert seller != null;
        String SellerID = seller.getSellerID();

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        final Query query = rootRef.collection("product")
                .whereEqualTo("category", "snack")
                .whereEqualTo("sellerID", SellerID);

        query.addSnapshotListener((queryDocumentSnapshots, e) -> {
            FirestoreRecyclerOptions<Product> options = new FirestoreRecyclerOptions.Builder<Product>()
                    .setQuery(query, Product.class)
                    .build();

            FirestoreRecyclerAdapter<Product, ProductViewHolder> adapter =
                    new FirestoreRecyclerAdapter<Product, ProductViewHolder>(options) {
                        @SuppressLint("SetTextI18n")
                        @Override
                        protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Product model) {
                            holder.productName.setText(model.getProductName());
                            holder.productDesc.setText(model.getDescription());
                            holder.productPrice.setText("Harga:" + model.getPrice());
                            Picasso.get().load(model.getImage()).resize(100,80).into(holder.productImage);

                            holder.itemView.setOnClickListener(v -> {
                                Intent intent = new Intent(getActivity(), UpdateProductDetailActivity.class);
                                intent.putExtra("pid", model.getProductID());
                                intent.putExtra("category", "snack");
                                startActivity(intent);
                            });

                        }

                        @NonNull
                        @Override
                        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_food, parent, false);
                            return new ProductViewHolder(view1);
                        }
                    };

            recyclerView.setAdapter(adapter);
            adapter.startListening();
        });
    }
}
