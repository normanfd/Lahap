package com.lahaptech.lahap.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.lahaptech.lahap.user.index.UserActivity;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;

import static com.lahaptech.lahap.user.index.UserActivity.EXTRA_USER;


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

    //    check if input blank
    private void loginUser() {
        String username = inpt_username.getText().toString().trim().toLowerCase();
        String password = inpt_password.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(LoginUserActivity.this, R.string.write_your_name, Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(LoginUserActivity.this, R.string.write_your_password, Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle(R.string.login_account);
            loadingBar.setMessage(getString(R.string.check_credential));
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            LoginToFirestore(username, password);
        }
    }

    //    access db for login
    private void LoginToFirestore(String username, String password) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection(ParentDbName).document(username);
        docRef.addSnapshotListener((documentSnapshot, e) -> {

            String hash = MD5_Hash(password);

            if (documentSnapshot != null && documentSnapshot.exists()) {
//                Log.d("get", "Current data: " + documentSnapshot.getData());
                User userData = documentSnapshot.toObject(User.class);
                assert userData != null;
                if (userData.getUsername().equals(username)) {
                    if (userData.getPassword().equals(hash)) {

                        Paper.book().write(Prevalent.UserName, username);
                        Paper.book().write(Prevalent.UserPasswordKey, hash);

                        loadingBar.dismiss();

                        Intent intent = new Intent(LoginUserActivity.this, UserActivity.class);
                        intent.putExtra(EXTRA_USER, userData);
                        Prevalent.CurrentOnlineUser = userData;

                        startActivity(intent);
                        finish();
                    } else {
                        loadingBar.dismiss();
                        Toast.makeText(LoginUserActivity.this, getString(R.string.incorrect_password), Toast.LENGTH_SHORT).show();
                    }
                }
                else Toast.makeText(LoginUserActivity.this, getString(R.string.incorrect_username), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginUserActivity.this, getResources().getString(R.string.account_with_username) + " " + username + " "
                        + getResources().getString(R.string.does_not_exist), Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        });
    }

    public static String MD5_Hash(String s) {
        MessageDigest m = null;

        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        assert m != null;
        m.update(s.getBytes(), 0, s.length());
        return new BigInteger(1, m.digest()).toString(16);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login_btn) {
            loginUser();
        }
    }
}