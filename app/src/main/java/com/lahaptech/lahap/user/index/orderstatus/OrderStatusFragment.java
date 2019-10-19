package com.lahaptech.lahap.user.index.orderstatus;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.Order;
import com.lahaptech.lahap.model.Product;
import com.lahaptech.lahap.model.User;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lahaptech.lahap.user.index.UserActivity.EXTRA_USER;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderStatusFragment extends Fragment {

    @BindView(R.id.order_id)
    TextView order_id;
    @BindView(R.id.status_order)
    TextView status_order;
    @BindView(R.id.order_list)
    TextView order_list;
    @BindView(R.id.order_total_price)
    TextView order_total_price;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order_status, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        User user = Objects.requireNonNull(getActivity()).getIntent().getParcelableExtra(EXTRA_USER);
        assert user != null;
        getOrderDetail(user.getUsername());
//        setupRecyclerView();

    }

    private void getOrderDetail(String usernameIPB) {
        FirebaseFirestore productRef = FirebaseFirestore.getInstance();
        DocumentReference docRef = productRef.collection("order").document(usernameIPB);
        docRef.addSnapshotListener((documentSnapshot, e) -> {
            if (documentSnapshot != null && documentSnapshot.exists()){
                Order orderData = documentSnapshot.toObject(Order.class);
                assert orderData!= null;
                order_id.setText(orderData.getOrderID());
                status_order.setText(orderData.getOrderStatus());
                order_list.setText(orderData.getProductList());
                order_total_price.setText(orderData.getTotalAmount());
            }
        });

    }

//    private void setupRecyclerView() {
//        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
//        final Query query = rootRef.collection("order")
//                .whereEqualTo("usernameIPB", user.getUsername() );
//
//        query.addSnapshotListener((snapshots, e) -> {
//
//            FirestoreRecyclerOptions<Order> options = new FirestoreRecyclerOptions.Builder<Order>()
//                    .setQuery(query, Order.class)
//                    .build();
//
//            FirestoreRecyclerAdapter<Order, OrderViewHolder> adapter =
//                    new FirestoreRecyclerAdapter<Order, OrderViewHolder>(options) {
//                        @Override
//                        protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Order model) {
//                            holder.id_order.setText(model.getUsernameIPB());
//                            holder.orderStatus.setText(model.getOrderStatus());
//                            holder.totalAmount.setText(model.getTotalAmount());
//                            holder.orderType.setText(model.getOrderType());
////                            holder.itemView.setOnClickListener(view -> {
////                                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
////                                intent.putExtra("order", model.getOrderTime());
////                                intent.putExtra(CANTEEN_QR_CODE, model.getCanteenCode());
////                                Toast.makeText(getContext(), model.getCanteenCode(), Toast.LENGTH_SHORT).show();
////                                startActivity(intent);
////                            });
//                        }
//
//                        @NonNull
//                        @Override
//                        public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_order, parent, false);
//                            return new OrderViewHolder(view);
//                        }
//                    };
//
//            rv_order.setLayoutManager(new LinearLayoutManager(getContext()));
//            rv_order.setHasFixedSize(true);
//            rv_order.setAdapter(adapter);
//            adapter.startListening();
//        });
//    }
}
