package com.lahaptech.lahap.user.home.SelectCanteen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.Canteen;
import com.lahaptech.lahap.model.User;
import com.lahaptech.lahap.user.menuproduct.SelectMenuActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lahaptech.lahap.user.home.UserActivity.EXTRA_USER;
import static com.lahaptech.lahap.user.menuproduct.SelectMenuActivity.CANTEEN_ID;
import static com.lahaptech.lahap.user.menuproduct.SelectMenuActivity.CANTEEN_QR_CODE;


public class SelectCanteenFragment extends Fragment {

    @BindView(R.id.rv_canteen)
    RecyclerView rv_canteen;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_selectcanteen, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //get user with parcelable
        User user = Objects.requireNonNull(getActivity()).getIntent().getParcelableExtra(EXTRA_USER);
        assert user != null;

        setupRecyclerView(user);


//        Log.d("username", user.getUsername());
    }

    private void setupRecyclerView(User user) {
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        final Query query = rootRef.collection("canteen")
                .orderBy("canteenID", Query.Direction.ASCENDING);

        query.addSnapshotListener((snapshots, e) -> {

            FirestoreRecyclerOptions<Canteen> options = new FirestoreRecyclerOptions.Builder<Canteen>()
                    .setQuery(query, Canteen.class)
                    .build();

            FirestoreRecyclerAdapter<Canteen, CanteenViewHolder> adapter =
                    new FirestoreRecyclerAdapter<Canteen, CanteenViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull CanteenViewHolder holder, int position, @NonNull Canteen model) {
                            holder.item_canteen.setText(model.getCanteenName());
                            holder.itemView.setOnClickListener(view -> {
                                Intent intent = new Intent(getActivity(), SelectMenuActivity.class);
                                intent.putExtra(CANTEEN_ID, model.getCanteenID());
                                intent.putExtra(CANTEEN_QR_CODE, model.getCanteenCode());
                                intent.putExtra(EXTRA_USER, user);
                                Toast.makeText(getContext(), model.getCanteenCode(), Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            });
                        }

                        @NonNull
                        @Override
                        public CanteenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_canteen, parent, false);
                            return new CanteenViewHolder(view);
                        }
                    };

            rv_canteen.setLayoutManager(new LinearLayoutManager(getContext()));
            rv_canteen.setHasFixedSize(true);
            rv_canteen.setAdapter(adapter);
            adapter.startListening();
        });
    }
}