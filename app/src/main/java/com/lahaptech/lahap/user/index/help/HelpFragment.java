package com.lahaptech.lahap.user.index.help;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lahaptech.lahap.R;
import com.lahaptech.lahap.user.index.help.aboutme.AboutActivity;
import com.lahaptech.lahap.user.index.help.faq.FaqActivity;
import com.lahaptech.lahap.user.index.help.contact.ContactActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HelpFragment extends Fragment implements View.OnClickListener{

    @BindView(R.id.cardview_contact)
    TextView cardview_usage;
    @BindView(R.id.cardview_faq)
    TextView cardview_faq;
    @BindView(R.id.cardview_about_me)
    TextView cardview_about_me;
    @BindView(R.id.privacy_policy)
    TextView privacy_policy;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_help, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardview_usage.setOnClickListener(this);
        cardview_faq.setOnClickListener(this);
        cardview_about_me.setOnClickListener(this);
        privacy_policy.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cardview_contact){
            Intent intent = new Intent(getActivity(), ContactActivity.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.cardview_faq){
            Intent intent = new Intent(getActivity(), FaqActivity.class);
            startActivity(intent);
        }
        else if (view.getId() == R.id.privacy_policy){
            Intent intent = new Intent(getActivity(), HelpDetailActivity.class);
            intent.putExtra("helpUrl", "https://www.kantinlahap.com/kebijakan-privasi/");
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(getActivity(), AboutActivity.class);
            startActivity(intent);
        }
    }
}