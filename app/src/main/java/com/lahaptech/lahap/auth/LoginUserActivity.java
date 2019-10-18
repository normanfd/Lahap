package com.lahaptech.lahap.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.Prevalent;
import com.lahaptech.lahap.model.User;
import com.lahaptech.lahap.seller.HomeOwnerActivity;
import com.lahaptech.lahap.user.home.UserActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;

import static com.lahaptech.lahap.user.home.UserActivity.EXTRA_USER;

//import static com.lahaptech.lahap.user.home.UserActivity.EXTRA_USER;

public class LoginUserActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.login_username)
    EditText inpt_username;
    @BindView(R.id.login_password_input)
    EditText inpt_password;
    @BindView(R.id.login_btn)
    Button btn_login;

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
        String username = inpt_username.getText().toString().trim();
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
                            Paper.book().write(Prevalent.UserName,username);
                            Paper.book().write(Prevalent.UserPasswordKey,password);
                            Toast.makeText(LoginUserActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent = new Intent(LoginUserActivity.this, UserActivity.class);
                            intent.putExtra(EXTRA_USER, userData);
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


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login_btn) {
            loginUser();
        }
    }
}