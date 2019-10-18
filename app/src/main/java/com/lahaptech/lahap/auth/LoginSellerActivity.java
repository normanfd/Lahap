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
import com.lahaptech.lahap.model.Seller;
import com.lahaptech.lahap.seller.HomeOwnerActivity;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;

import static com.lahaptech.lahap.seller.HomeOwnerActivity.EXTRA_SELLER;

public class LoginSellerActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.login_username)
    EditText inpt_username;
    @BindView(R.id.login_password_input)
    EditText inpt_password;
    @BindView(R.id.login_btn)
    Button btn_login;

    ProgressDialog loadingBar;
    String ParentDbName = "seller";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Paper.init(this);
        ButterKnife.bind(this);
        inpt_username.setHint(R.string.hint_login_seller);
        
        loadingBar = new ProgressDialog(this);
        btn_login.setOnClickListener(this);

    }

    private void loginUser() {
        String sellerID = inpt_username.getText().toString();
        String password = inpt_password.getText().toString();

        if (TextUtils.isEmpty(sellerID)) {
            Toast.makeText(LoginSellerActivity.this, R.string.write_your_name, Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(LoginSellerActivity.this, R.string.write_your_password, Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Login account");
            loadingBar.setMessage("Please wait while we are checking your credentials..");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            LoginToFirestore(sellerID, password);
        }
    }

    private void LoginToFirestore(String sellerID, String password) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection(ParentDbName).document(sellerID);
        docRef.addSnapshotListener((documentSnapshot, e) -> {
            if (e != null) {
                Log.w("fail", "Listen failed.", e);
                return;
            }
            String hash = MD5_Hash(password);

            if (documentSnapshot != null && documentSnapshot.exists()) {
                Log.d("get", "Current data: " + documentSnapshot.getData());
                Seller sellerData = documentSnapshot.toObject(Seller.class);
                assert sellerData != null;
                if (sellerData.getSellerID().equals(sellerID)) {
                    if (sellerData.getPassword().equals(hash)) {
                        Paper.book().write(Prevalent.SellerID,sellerID);
                        Paper.book().write(Prevalent.SellerPassword,hash);
                        Toast.makeText(LoginSellerActivity.this, "Welcome Owner, you are logged in successfully", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();

                        Seller seller = new Seller();
                        seller.setSellerID(sellerID);
                        seller.setLocationID(sellerData.getLocationID());

                        Intent intent = new Intent(LoginSellerActivity.this, HomeOwnerActivity.class);
                        intent.putExtra(EXTRA_SELLER, seller);

                        Prevalent.CurrentOnlineSeller = sellerData;

                        startActivity(intent);
                        finish();
                    }
                    else {
                        loadingBar.dismiss();
                        Toast.makeText(LoginSellerActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                }

            } else {
                Toast.makeText(LoginSellerActivity.this, "Account with sellerID " + sellerID + " does not exist", Toast.LENGTH_SHORT).show();
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

    public static String MD5_Hash(String s) {
        MessageDigest m = null;

        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        assert m != null;
        m.update(s.getBytes(),0,s.length());
        return new BigInteger(1, m.digest()).toString(16);
    }
}