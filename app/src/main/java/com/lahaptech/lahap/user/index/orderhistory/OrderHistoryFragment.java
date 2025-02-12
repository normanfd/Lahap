package com.lahaptech.lahap.user.index.orderhistory;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.History;
import com.lahaptech.lahap.model.User;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lahaptech.lahap.user.index.UserActivity.EXTRA_USER;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderHistoryFragment extends Fragment {

    @BindView(R.id.rv_history)
    RecyclerView rv_history;
    @BindView(R.id.const_empty_order)
    ConstraintLayout emptyHistory;

    public OrderHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_order_history, container, false);
        ButterKnife.bind(this, root);


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //get user with parcelable
        User user = Objects.requireNonNull(getActivity()).getIntent().getParcelableExtra(EXTRA_USER);
        assert user != null;
        Log.d("LOG USERNAME", user.getUsername());
        setupRecyclerView(user);
        int rvCount = 0;
        Log.d("rvcount", Integer.toString(rvCount));

    }

    private void setupRecyclerView(User user) {
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        final Query query = rootRef.collection("history").whereEqualTo("usernameIPB", user.getUsername());
        query.addSnapshotListener((snapshots, e) -> {

            FirestoreRecyclerOptions<History> options = new FirestoreRecyclerOptions.Builder<History>()
                    .setQuery(query, History.class)
                    .build();

            FirestoreRecyclerAdapter<History, HistoryViewHolder> adapter =
                    new FirestoreRecyclerAdapter<History, HistoryViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull HistoryViewHolder holder, int position, @NonNull History model) {
                            holder.order_date.setText(model.getDateOrder());
                            holder.order_price.setText(model.getTotalAmount());
                            holder.order_product_list.setText(model.getProductList());
                            if (model.getStatus().equals("cancelled")){
                                holder.order_logo.setImageDrawable(getResources().getDrawable(R.drawable.ic_clear_red_24dp));
                            }
                            else holder.order_logo.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_black_24dp));
                        }

                        @NonNull
                        @Override
                        public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_layout, parent, false);
                            return new HistoryViewHolder(view);
                        }
                    };

            rv_history.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            rv_history.setHasFixedSize(true);
            rv_history.setAdapter(adapter);
            adapter.startListening();

            assert snapshots != null;
            Log.d("cekdata", String.valueOf(snapshots.size()));
            if (snapshots.size() > 0){
                emptyHistory.setVisibility(View.INVISIBLE);
            }
            else {
                emptyHistory.setVisibility(View.VISIBLE);
            }

        });


    }

}
