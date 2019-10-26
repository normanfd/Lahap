package com.lahaptech.lahap.user.index.help;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.esotericsoftware.kryo.serializers.FieldSerializer;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.user.index.help.aboutme.AboutActivity;
import com.lahaptech.lahap.user.index.help.faq.FaqActivity;
import com.lahaptech.lahap.user.index.help.usage.HowtoUseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HelpFragment extends Fragment implements View.OnClickListener{

    @BindView(R.id.cardview_usage)
    CardView cardview_usage;
    @BindView(R.id.cardview_faq)
    CardView cardview_faq;
    @BindView(R.id.cardview_about_me)
    CardView cardview_about_me;

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
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cardview_usage){
            Intent intent = new Intent(getActivity(), HowtoUseActivity.class);
            startActivity(intent);
        }else if(view.getId() == R.id.cardview_faq){
            Intent intent = new Intent(getActivity(), FaqActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(getActivity(), AboutActivity.class);
            startActivity(intent);
        }
    }
}