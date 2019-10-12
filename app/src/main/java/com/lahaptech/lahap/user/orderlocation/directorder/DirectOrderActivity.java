package com.lahaptech.lahap.user.orderlocation.directorder;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.lahaptech.lahap.R;

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
    String totalPrice = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_order);

        totalPrice = getIntent().getStringExtra("TotalPrice");
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
        String[] arrOfStr = hasil.split(",", 2);
//        String[] dataPutExtra = canteenCode.split(",", 2);

        if (arrOfStr[0].equals(canteenCode)){
//            if (arrOfStr[0].equals(dataPutExtra[0])){
            Toast.makeText(this, "Pesanan selesai, lanjutkan pembayaran", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DirectOrderActivity.this, DirectOrderFormActivity.class);
            intent.putExtra("qrcode", hasil);
            intent.putExtra("orderTableNo", arrOfStr[1]);
            intent.putExtra("TotalPrice", totalPrice);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Anda tidak berada di kantin yang bersangkutan", Toast.LENGTH_SHORT).show();
            final Handler handler = new Handler();
            handler.postDelayed(this::beginQRCode, 3000);

        }
    }
}
