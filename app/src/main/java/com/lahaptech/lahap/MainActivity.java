package com.lahaptech.lahap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lahaptech.lahap.auth.LoginSellerActivity;
import com.lahaptech.lahap.auth.LoginUserActivity;
import com.lahaptech.lahap.auth.RegisterActivity;
import com.lahaptech.lahap.model.Seller;
import com.lahaptech.lahap.model.User;
import com.lahaptech.lahap.owner.HomeOwnerActivity;
import com.lahaptech.lahap.user.UserActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_join;
    Button btn_login;
    Button btn_login_seller;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_join = findViewById(R.id.main_join_now_btn);
        btn_login = findViewById(R.id.main_login_btn);
        btn_login_seller = findViewById(R.id.main_login_btn_seller);

        loadingBar = new ProgressDialog(this);

        btn_join.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_login_seller.setOnClickListener(this);

        Paper.init(this);
        String UserName = Paper.book().read(Prevalent.UserName);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

        if (!TextUtils.isEmpty(UserName) && !TextUtils.isEmpty(UserPasswordKey)) {
            loadingBar.setTitle("Already Logged in");
            loadingBar.setMessage("please wait...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            LoginToFirestore(UserName, UserPasswordKey);
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.main_login_btn) {
            Intent intent = new Intent(MainActivity.this, LoginUserActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.main_join_now_btn) {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, LoginSellerActivity.class);
            startActivity(intent);
        }
    }

    private void LoginToFirestore(String username, String password) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection("user").document(username);
        docRef.addSnapshotListener((documentSnapshot, e) -> {
            if (e != null) {
                Log.w("fail", "Listen failed.", e);
                return;
            }

            if (documentSnapshot != null && documentSnapshot.exists()) {
                Log.d("get", "Current data: " + documentSnapshot.getData());
                User userData = documentSnapshot.toObject(User.class);
                assert userData != null;
                if (userData.getUsername().equals(username)) {
                    if (userData.getPassword().equals(password)) {
                        Toast.makeText(MainActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        Intent intent = new Intent(MainActivity.this, UserActivity.class);
                        Prevalent.CurrentOnlineUser = userData;
                        startActivity(intent);
                    } else {
                        loadingBar.dismiss();
                        Toast.makeText(MainActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                }

            } else {
                Toast.makeText(MainActivity.this, "Account with username " + username + " does not exist", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
