package com.lahaptech.lahap.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lahaptech.lahap.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.register_btn)
    Button btn_register;
    @BindView(R.id.register_name_input)
    EditText inpt_name;
    @BindView(R.id.register_username_input)
    EditText inpt_username;
    @BindView(R.id.register_email_input)
    EditText inpt_email;
    @BindView(R.id.register_phone_number_input)
    EditText inpt_phone_number;
    @BindView(R.id.register_password_input)
    EditText inpt_password;

    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        loadingBar = new ProgressDialog(this);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.register_btn){
            CreateAccount();
        }
    }

    private void CreateAccount() {
        String name = inpt_name.getText().toString();
        String username = inpt_username.getText().toString().trim();
        String phone = inpt_phone_number.getText().toString();
        String password = inpt_password.getText().toString();
        String email = inpt_email.getText().toString();

        if(TextUtils.isEmpty(username)){
            Toast.makeText(RegisterActivity.this, R.string.write_your_name, Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(email)){
            Toast.makeText(RegisterActivity.this, R.string.write_email_address, Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phone)){
            Toast.makeText(RegisterActivity.this, R.string.write_phone_number, Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(RegisterActivity.this, R.string.write_your_password, Toast.LENGTH_SHORT).show();
        }
        else if(!PhoneNumberUtils.isGlobalPhoneNumber(phone)) {
            Toast.makeText(RegisterActivity.this, R.string.write_valid_phone_number, Toast.LENGTH_SHORT).show();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(RegisterActivity.this, R.string.valid_email_address, Toast.LENGTH_SHORT).show();
        }
        else if (!email.contains("@apps.ipb.ac.id")){
            Toast.makeText(RegisterActivity.this, R.string.valid_email_ipb_address, Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(name)){
            Toast.makeText(RegisterActivity.this, "Please write your name", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle(getResources().getString(R.string.create_account));
            loadingBar.setMessage(getResources().getString(R.string.checking_credentials));
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            registerToFirestore(name, username, email, phone, password);
        }
    }

    private void registerToFirestore(String name, String username, String email, String phone, String password) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> user = new HashMap<>();

        user.put("name", name);
        user.put("username", username);
        user.put("email", email);
        user.put("phone", phone);
        user.put("password",password);

        db.collection("user").document(username)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(RegisterActivity.this, R.string.account_created, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        Log.d("Cek", "DocumentSnapshot added with ID: " + username);
                        Intent intent = new Intent(RegisterActivity.this, LoginUserActivity.class);
                        RegisterActivity.this.startActivity(intent);
                    }
                })
                .addOnFailureListener(e -> {
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, R.string.try_again_later, Toast.LENGTH_SHORT).show();
                });

    }


}
