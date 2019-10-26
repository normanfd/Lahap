package com.lahaptech.lahap.user.index.logout;

import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.lahaptech.lahap.main_activity.MainActivity;

import java.util.Objects;

import io.paperdb.Paper;


public class LogoutFragment extends Fragment {

    @Override
    public void onStart() {
        Paper.book().destroy();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).finish();
        super.onStart();
    }
}