package com.lahaptech.lahap.user.orderlocation;

import androidx.appcompat.app.AppCompatActivity;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.lahaptech.lahap.HasilActivity;
import com.lahaptech.lahap.R;
import com.lahaptech.lahap.ScanActivity;
import com.lahaptech.lahap.user.menuproduct.SelectMenuActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static com.lahaptech.lahap.user.menuproduct.SelectMenuActivity.CANTEEN_ID;
import static com.lahaptech.lahap.user.menuproduct.SelectMenuActivity.CANTEEN_QR_CODE;

public class DirectOrderActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    @BindView(R.id.scan_qr_code)
    ZXingScannerView scannerView;
    String canteenID="";
    String canteenCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_order);

        canteenID = getIntent().getStringExtra(CANTEEN_ID);
        canteenCode = Objects.requireNonNull(getIntent().getStringExtra(CANTEEN_QR_CODE));

        ButterKnife.bind(this);

        beginQRCode();
    }

    private void beginQRCode() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        scannerView.setResultHandler(DirectOrderActivity.this);
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(DirectOrderActivity.this, "Accept permission camera first..!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        String hasil = rawResult.getText();
        Toast.makeText(this, hasil, Toast.LENGTH_SHORT).show();
        if (hasil.equals(canteenCode)){
            Toast.makeText(this, "Pesanan selesai, lanjutkan pembayaran", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DirectOrderActivity.this, SelectMenuActivity.class);
            intent.putExtra("qrcode", hasil);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Anda tidak berada di kantin yang bersangkutan", Toast.LENGTH_SHORT).show();
            beginQRCode();
        }
    }
}
