package com.lahaptech.lahap.user.menuproduct;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.Product;
import com.lahaptech.lahap.owner.update.UpdateProductDetailActivity;
import com.lahaptech.lahap.user.detailproduct.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lahaptech.lahap.user.menuproduct.SelectMenuActivity.CANTEEN_ID;

public class FoodFragment extends Fragment {

    @BindView(R.id.rv_food)
    RecyclerView recyclerView;

    public FoodFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String canteenID = Objects.requireNonNull(getActivity()).getIntent().getStringExtra(CANTEEN_ID);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        final Query query = rootRef.collection("product")
                .whereEqualTo("locationID", canteenID)
                .whereEqualTo("category", "food");

        query.addSnapshotListener((queryDocumentSnapshots, e) -> {
            FirestoreRecyclerOptions<Product> options = new FirestoreRecyclerOptions.Builder<Product>()
                    .setQuery(query, Product.class)
                    .build();

            FirestoreRecyclerAdapter<Product, MenuViewHolder> adapter =
                    new FirestoreRecyclerAdapter<Product, MenuViewHolder>(options) {
                        @SuppressLint("SetTextI18n")
                        @Override
                        protected void onBindViewHolder(@NonNull MenuViewHolder holder, int position, @NonNull Product model) {
                            holder.name.setText(model.getProductName());
                            holder.seller.setText(getResources().getString(R.string.seller) + model.getSellerID());
                            holder.price.setText(getResources().getString(R.string.price) + model.getPrice());
                            Picasso.get()
                                    .load(model.getImage())
                                    .resize(100,100)
                                    .into(holder.photo);

                            holder.itemView.setOnClickListener(v -> {
                                Intent intent = new Intent(getActivity(), DetailActivity.class);
                                intent.putExtra("pid", model.getProductID());
                                intent.putExtra("category", "food");
                                intent.putExtra("sellerID", model.getSellerID());
                                intent.putExtra("locationID", model.getLocationID());
                                startActivity(intent);

                            });

                        }

                        @NonNull
                        @Override
                        public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_food, parent, false);
                            return new MenuViewHolder(view1);
                        }
                    };

            recyclerView.setAdapter(adapter);
            adapter.startListening();
        });
    }
}
