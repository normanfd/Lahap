package com.lahaptech.lahap.user.orderlocation;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.lahaptech.lahap.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndirectOrderActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.bt_showtimepicker)
    Button btTimePicker;
    @BindView(R.id.tv_timeresult)
    TextView tvTimeResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indirect_order);

        ButterKnife.bind(this);

        btTimePicker.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bt_showtimepicker){
            showTimeDialog();
        }
    }

    @SuppressLint("SetTextI18n")
    private void showTimeDialog() {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) ->
                        tvTimeResult.setText("Waktu dipilih = " + hourOfDay + ":" + minute),
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(this));


        timePickerDialog.show();
    }
}
