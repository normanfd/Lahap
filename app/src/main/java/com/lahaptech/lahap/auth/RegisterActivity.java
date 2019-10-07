package com.lahaptech.lahap.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lahaptech.lahap.MainActivity;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.model.User;

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
        String username = inpt_username.getText().toString();
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
        else if(TextUtils.isEmpty(name)){
            Toast.makeText(RegisterActivity.this, "Please write your name", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle(getResources().getString(R.string.create_account));
            loadingBar.setMessage(getResources().getString(R.string.checking_credentials));
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            validatePhoneNumber(name, username, email, phone, password);
        }
    }

    private void validatePhoneNumber(final String name, final String username, final String email, final String phone, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(username).exists())){
                    //Menyimpan User baru kedalam database
                    User user = null;
                    try {
                        user = new User(name, username, phone, email, password,null,null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    RootRef.child("User").child(username).setValue(user)
                            .addOnCompleteListener(task -> {
                                if(task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this, R.string.account_created, Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    loadingBar.dismiss();
                                    Toast.makeText(RegisterActivity.this, R.string.try_again_later, Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Phone number "+ phone + " already exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please use different phone number", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
