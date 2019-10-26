package com.lahaptech.lahap.user.index.orderstatus;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.esotericsoftware.kryo.serializers.FieldSerializer;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.Order;
import com.lahaptech.lahap.model.Product;
import com.lahaptech.lahap.model.User;
import com.lahaptech.lahap.user.payment.OnlinePaymentActivity;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lahaptech.lahap.user.index.UserActivity.EXTRA_USER;

public class OrderStatusFragment extends Fragment {
    @BindView(R.id.buttonCancel)
    Button btn_cancel;
    @BindView(R.id.status_order)
    TextView status_order;
    @BindView(R.id.order_list)
    TextView order_list;
    @BindView(R.id.order_total_price)
    TextView order_total_price;
    @BindView(R.id.buttonPayment)
    Button payment;
    @BindView(R.id.textnoorder)
    TextView no_order;
    @BindView(R.id.const_empty_order)
    ConstraintLayout const_empty;
    @BindView(R.id.const_exist_order)
    ConstraintLayout const_exist;
    private String total, orderID, usernameIPB;
    private ProgressDialog loadingBar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order_status, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadingBar = new ProgressDialog(getActivity());

        User user = Objects.requireNonNull(getActivity()).getIntent().getParcelableExtra(EXTRA_USER);
        assert user != null;
        usernameIPB = user.getUsername();
        showLoading(true);
        getOrderDetail(usernameIPB);

        payment.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), OnlinePaymentActivity.class);
            intent.putExtra("total_amount", total);
            intent.putExtra("orderID", orderID);
            intent.putExtra("userID", usernameIPB);
            startActivity(intent);
        });
//        setupRecyclerView();

    }

    private void showLoading(Boolean state) {
        if (state){
            loadingBar.setTitle("Loading..");
            loadingBar.setMessage(getResources().getString(R.string.checking_credentials));
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
        }
        else
            loadingBar.dismiss();
    }

    @SuppressLint("SetTextI18n")
    private void getOrderDetail(String usernameIPB) {
        FirebaseFirestore productRef = FirebaseFirestore.getInstance();
        DocumentReference docRef = productRef.collection("order").document(usernameIPB);
        docRef.addSnapshotListener((documentSnapshot, e) -> {
            if (documentSnapshot != null && documentSnapshot.exists()){
                Order orderData = documentSnapshot.toObject(Order.class);
                assert orderData!= null;
                switch (orderData.getOrderStatus()) {
                    case "0":
                        status_order.setText(" belum dibayar");
                        status_order.setTextColor(Color.RED);
                        payment.setVisibility(View.VISIBLE);
                        payment.setVisibility(View.VISIBLE);
                        break;
                    case "1":
                        status_order.setText(" Menunggu Antrian");
                        status_order.setTextColor(Color.MAGENTA);
                        break;
                    case "2":
                        status_order.setText(" pesanan sedang dibuat");
                        status_order.setTextColor(Color.BLUE);
                        break;
                    default:
                        status_order.setText(" SELESAI");
                        status_order.setTextColor(Color.GREEN);
                        break;
                }
                order_list.setText(orderData.getProductList());
                order_total_price.setText(orderData.getTotalAmount());
                total = orderData.getTotalAmount();
                orderID = orderData.getOrderID();

                const_exist.setVisibility(View.VISIBLE);
                const_empty.setVisibility(View.INVISIBLE);
            }
            else {
                const_exist.setVisibility(View.INVISIBLE);
                const_empty.setVisibility(View.VISIBLE);
            }
            showLoading(false);
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
