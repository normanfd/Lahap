package com.lahaptech.lahap.seller.order;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.Seller;

import java.util.Objects;

import static com.lahaptech.lahap.seller.HomeSellerActivity.EXTRA_SELLER;

public class DirectOrderFragment extends Fragment {
    private Seller seller;

    public DirectOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_direct_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        seller = Objects.requireNonNull(getActivity()).getIntent().getParcelableExtra(EXTRA_SELLER);
        assert seller != null;
        Log.d("SELLER NAME Direct", seller.getSellerID());
    }
}
