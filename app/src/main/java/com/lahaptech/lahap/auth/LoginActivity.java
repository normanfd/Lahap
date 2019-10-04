package com.lahaptech.lahap.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lahaptech.lahap.Main2Activity;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.login_username)
    EditText inpt_username;
    @BindView(R.id.login_password_input)
    EditText inpt_password;
    @BindView(R.id.login_btn)
    Button btn_login;

    ProgressDialog loadingBar;
    private String ParentDbName = "User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loadingBar = new ProgressDialog(this);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String username = inpt_username.getText().toString();
        String password = inpt_password.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(LoginActivity.this, R.string.write_your_name, Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, R.string.write_your_password, Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Login account");
            loadingBar.setMessage("Please wait while we are checking your credentials..");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            AllowAccessAccount(username, password);
        }
    }

    private void AllowAccessAccount(final String username, final String password) {
//        if(chkBoxRememberMe.isChecked()){
//            Paper.book().write(Prevalent.UserPhoneKey,phone);
//            Paper.book().write(Prevalent.UserPasswordKey,password);
//        }
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(ParentDbName).child(username).exists()) {
                    User UserData = dataSnapshot.child(ParentDbName).child(username).getValue(User.class);
                    assert UserData != null;
                    if (UserData.getName().equals(username)) {
                        if (UserData.getPassword().equals(password)) {
                            if (ParentDbName.equals("Admins")) {
                                Toast.makeText(LoginActivity.this, "Welcome Admin, you are logged in succesfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
//                                Intent intent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
//                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
//                                Prevalent.CurrentOnlineUser = UserData;
                                startActivity(intent);
                            }
                        } else {
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Account with phone number " + username + " does not exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}