package com.lahaptech.lahap.user.home.orderstatus;


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
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.Canteen;
import com.lahaptech.lahap.model.Order;
import com.lahaptech.lahap.model.Prevalent;
import com.lahaptech.lahap.user.menuproduct.SelectMenuActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lahaptech.lahap.user.menuproduct.SelectMenuActivity.CANTEEN_ID;
import static com.lahaptech.lahap.user.menuproduct.SelectMenuActivity.CANTEEN_QR_CODE;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderStatusFragment extends Fragment {


    @BindView(R.id.rv_order)
    RecyclerView rv_order;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order_status, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();

    }

    private void setupRecyclerView() {
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        final Query query = rootRef.collection("order")
                .whereEqualTo("usernameIPB", Prevalent.CurrentOnlineUser.getUsername() );

        query.addSnapshotListener((snapshots, e) -> {

            FirestoreRecyclerOptions<Order> options = new FirestoreRecyclerOptions.Builder<Order>()
                    .setQuery(query, Order.class)
                    .build();

            FirestoreRecyclerAdapter<Order, OrderViewHolder> adapter =
                    new FirestoreRecyclerAdapter<Order, OrderViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Order model) {
                            holder.id_order.setText(model.getUsernameIPB());
                            holder.orderStatus.setText(model.getOrderStatus());
                            holder.totalAmount.setText(model.getTotalAmount());
                            holder.orderType.setText(model.getOrderType());
//                            holder.itemView.setOnClickListener(view -> {
//                                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
//                                intent.putExtra("order", model.getOrderTime());
//                                intent.putExtra(CANTEEN_QR_CODE, model.getCanteenCode());
//                                Toast.makeText(getContext(), model.getCanteenCode(), Toast.LENGTH_SHORT).show();
//                                startActivity(intent);
//                            });
                        }

                        @NonNull
                        @Override
                        public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_order, parent, false);
                            return new OrderViewHolder(view);
                        }
                    };

            rv_order.setLayoutManager(new LinearLayoutManager(getContext()));
            rv_order.setHasFixedSize(true);
            rv_order.setAdapter(adapter);
            adapter.startListening();
        });
    }
}