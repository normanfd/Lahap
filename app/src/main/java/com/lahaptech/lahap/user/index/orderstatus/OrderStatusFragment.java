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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.Order;
import com.lahaptech.lahap.model.User;
import com.lahaptech.lahap.user.payment.OnlinePaymentActivity;

import java.util.HashMap;
import java.util.Map;
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
    private String total, orderID, usernameIPB, dateOrder, timeOrder, productList, locationID;
    private ProgressDialog loadingBar;

    private FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    private DocumentReference docRef;


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
            intent.putExtra(EXTRA_USER, user);
            startActivity(intent);
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

    }

    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                Objects.requireNonNull(getContext()));

        alertDialogBuilder.setTitle("Anda Yakin?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Klik Ya Jika Akan Ingin Membatalkan Order!")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", (dialog, id) -> {
                    docRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            saveHistoryOrder(usernameIPB, orderID, dateOrder, timeOrder, productList, total, locationID);
                        }
                    });


                })
                .setNegativeButton("Tidak", (dialog, id) -> {

                    dialog.cancel();
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
        docRef = rootRef.collection("order").document(usernameIPB);
        docRef.addSnapshotListener((documentSnapshot, e) -> {
            if (documentSnapshot != null && documentSnapshot.exists()){
                Order orderData = documentSnapshot.toObject(Order.class);
                assert orderData!= null;
                switch (orderData.getOrderStatus()) {
                    case "0":
                        status_order.setText(" belum dibayar");
                        status_order.setTextColor(Color.RED);
                        payment.setVisibility(View.VISIBLE);
                        btn_cancel.setVisibility(View.VISIBLE);
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
                dateOrder = orderData.getOrderDate();
                timeOrder = orderData.getOrderTime();
                productList = orderData.getProductList();
                locationID = orderData.getLocationID();

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

    private void saveHistoryOrder(String username, String orderID, String dateOrder, String timeOrder,
                                  String productList , String totalAmount, String locationID){

        Map<String, Object> history = new HashMap<>();

        history.put("usernameIPB", username);
        history.put("orderID", orderID);
        history.put("dateOrder", dateOrder);
        history.put("timeOrder", timeOrder);
        history.put("productList", productList);
        history.put("totalAmount", totalAmount);
        history.put("locationID", locationID);
        history.put("status", "cancelled");

        rootRef.collection("history").document().set(history);
    }

}
