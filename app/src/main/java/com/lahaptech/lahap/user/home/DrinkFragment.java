package com.lahaptech.lahap.user.home;


import android.annotation.SuppressLint;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.Product;
import com.lahaptech.lahap.user.detailproduct.DetailActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrinkFragment extends Fragment {
    @BindView(R.id.rv_drink)
    RecyclerView recyclerView;

    public DrinkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_drink, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        String category = "drink";
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products").child(category);
        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(productRef, Product.class)
                        .build();
        FirebaseRecyclerAdapter<Product, ProductAdapter> adapter =
                new FirebaseRecyclerAdapter<Product, ProductAdapter>(options) {

                    @SuppressLint("SetTextI18n")
                    @Override
                    protected void onBindViewHolder(@NonNull ProductAdapter holder, int position, @NonNull final Product model) {
                        holder.name.setText(model.getProductname());
                        holder.desc.setText(model.getDescription());
                        holder.price.setText(getResources().getString(R.string.template_price) + model.getPrice());
                        Picasso.get().load(model.getImage()).into(holder.photo);

                        holder.itemView.setOnClickListener(v -> {
                            Intent intent = new Intent(getActivity(), DetailActivity.class);
                            intent.putExtra("pid", model.getPid());
                            intent.putExtra("category", "drink");
                            startActivity(intent);
                        });
                    }

                    @NonNull
                    @Override
                    public ProductAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_food, parent, false);
                        return new ProductAdapter(view);
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
