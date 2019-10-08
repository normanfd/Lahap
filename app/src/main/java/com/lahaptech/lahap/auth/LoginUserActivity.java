package com.lahaptech.lahap.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.User;
import com.lahaptech.lahap.owner.HomeOwnerActivity;
import com.lahaptech.lahap.user.home.HomeUserNavActivity;
import com.lahaptech.lahap.Prevalent;
import com.rey.material.widget.CheckBox;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;

public class LoginUserActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.login_username)
    EditText inpt_username;
    @BindView(R.id.login_password_input)
    EditText inpt_password;
    @BindView(R.id.login_btn)
    Button btn_login;
    @BindView(R.id.remember_me_chkb)
    CheckBox chkBoxRememberMe;

    ProgressDialog loadingBar;
    String ParentDbName = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Paper.init(this);
        ButterKnife.bind(this);

        loadingBar = new ProgressDialog(this);
        btn_login.setOnClickListener(this);

    }

    private void loginUser() {
        String username = inpt_username.getText().toString();
        String password = inpt_password.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(LoginUserActivity.this, R.string.write_your_name, Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(LoginUserActivity.this, R.string.write_your_password, Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Login account");
            loadingBar.setMessage("Please wait while we are checking your credentials..");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            LoginToFirestore(username, password);
//            AllowAccessAccount(username, password);
        }
    }

    private void LoginToFirestore(String username, String password) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection(ParentDbName).document(username);
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
                        if (ParentDbName.equals("seller")) {
                            Toast.makeText(LoginUserActivity.this, "Welcome Owner, you are logged in succesfully", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent = new Intent(LoginUserActivity.this, HomeOwnerActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginUserActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent = new Intent(LoginUserActivity.this, HomeUserNavActivity.class);
                            Prevalent.CurrentOnlineUser = userData;
                            startActivity(intent);
                        }
                    }
                    else {
                        loadingBar.dismiss();
                        Toast.makeText(LoginUserActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                }

            } else {
                Toast.makeText(LoginUserActivity.this, "Account with username " + username + " does not exist", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        });


    }

    private void AllowAccessAccount(final String username, final String password) {
        if(chkBoxRememberMe.isChecked()){
            Paper.book().write(Prevalent.UserName,username);
            Paper.book().write(Prevalent.UserPasswordKey,password);
        }
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(ParentDbName).child(username).exists()) {
                    User UserData = dataSnapshot.child(ParentDbName).child(username).getValue(User.class);
                    assert UserData != null;
                    if (UserData.getUsername().equals(username)) {
                        if (UserData.getPassword().equals(password)) {
                            if (ParentDbName.equals("Owner")) {
                                Toast.makeText(LoginUserActivity.this, "Welcome Owner, you are logged in succesfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(LoginUserActivity.this, HomeOwnerActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginUserActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(LoginUserActivity.this, HomeUserNavActivity.class);
                                Prevalent.CurrentOnlineUser = UserData;
                                startActivity(intent);
                            }
                        } else {
                            loadingBar.dismiss();
                            Toast.makeText(LoginUserActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(LoginUserActivity.this, "Account with username " + username + " does not exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login_btn) {
            loginUser();
        }
    }
}