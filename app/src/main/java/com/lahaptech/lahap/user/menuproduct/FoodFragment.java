package com.lahaptech.lahap.user.menuproduct;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.Product;
import com.lahaptech.lahap.owner.update.UpdateProductDetailActivity;
import com.lahaptech.lahap.user.detailproduct.DetailActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        final Query query = rootRef.collection("product")
                .whereEqualTo("category", "food").whereEqualTo("isAvailable","1")
                .orderBy("productName", Query.Direction.ASCENDING);

        query.addSnapshotListener((queryDocumentSnapshots, e) -> {
            FirestoreRecyclerOptions<Product> options = new FirestoreRecyclerOptions.Builder<Product>()
                    .setQuery(query, Product.class)
                    .build();

            FirestoreRecyclerAdapter<Product, ProductAdapter> adapter =
                    new FirestoreRecyclerAdapter<Product, ProductAdapter>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull ProductAdapter holder, int position, @NonNull Product model) {
                            holder.name.setText(model.getProductName());
                            holder.desc.setText(model.getDescription());
                            holder.price.setText(model.getPrice());
                            Picasso.get().load(model.getImage()).into(holder.photo);

                            holder.itemView.setOnClickListener(v -> {
                                Intent intent = new Intent(getActivity(), UpdateProductDetailActivity.class);
                                intent.putExtra("pid", model.getProductID());
                                intent.putExtra("category", "food");
                                startActivity(intent);
                            });

                        }

                        @NonNull
                        @Override
                        public ProductAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_food, parent, false);
                            return new ProductAdapter(view1);
                        }
                    };

            recyclerView.setAdapter(adapter);
            adapter.startListening();
        });
    }
}
