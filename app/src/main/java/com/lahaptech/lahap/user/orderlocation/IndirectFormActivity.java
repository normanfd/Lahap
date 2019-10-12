package com.lahaptech.lahap.user.orderlocation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lahaptech.lahap.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndirectFormActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tv_total_amount)
    TextView tv_total_amount;
    @BindView(R.id.tv_time_pick)
    TextView tv_time_pick;

    String total_amount, time_pick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indirect_form);
        ButterKnife.bind(this);

        time_pick = getIntent().getStringExtra("timeOrder");
        total_amount = getIntent().getStringExtra("totalAmount");

        tv_time_pick.setText(time_pick);
        tv_total_amount.setText(total_amount);

    }

    @Override
    public void onClick(View view) {

    }
}
