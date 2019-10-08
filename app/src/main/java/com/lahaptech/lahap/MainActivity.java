package com.lahaptech.lahap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lahaptech.lahap.auth.LoginSellerActivity;
import com.lahaptech.lahap.auth.LoginUserActivity;
import com.lahaptech.lahap.auth.RegisterActivity;
import com.lahaptech.lahap.model.User;
import com.lahaptech.lahap.user.home.HomeUserNavActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.main_join_now_btn)
    Button btn_join;
    @BindView(R.id.main_login_btn)
    Button btn_login;
    @BindView(R.id.main_login_btn_seller)
    Button btn_login_seller;

    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Paper.init(this);

        loadingBar = new ProgressDialog(this);


        btn_join.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_login_seller.setOnClickListener(this);


        String UserName = Paper.book().read(Prevalent.UserName);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

        if(!TextUtils.isEmpty(UserName) && !TextUtils.isEmpty(UserPasswordKey)){
            AllowAccess(UserName,UserPasswordKey);
            loadingBar.setTitle("Already Logged in");
            loadingBar.setMessage("please wait...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.main_login_btn) {
            Intent intent = new Intent(MainActivity.this, LoginUserActivity.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.main_join_now_btn) {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
        else  {
            Intent intent = new Intent(MainActivity.this, LoginSellerActivity.class);
            startActivity(intent);
        }
    }

    private void AllowAccess(final String username, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("User").child(username).exists())
                {
                    // akses model class User
                    User UserData = dataSnapshot.child("User").child(username).getValue(User.class);
                    assert UserData != null;
                    if(UserData.getUsername().equals(username)){
                        if(UserData.getPassword().equals(password)){
                            Toast.makeText(MainActivity.this, "Please wait, you are already logged in..", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent = new Intent(MainActivity.this, HomeUserNavActivity.class);
                            Prevalent.CurrentOnlineUser = UserData;
                            startActivity(intent);
                        }
                        else {
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Password is  incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Account with this " + username + " number do not exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
