package com.lahaptech.lahap.user.index.help.faq;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.lahaptech.lahap.R;

public class FaqActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        TextView tv_title = findViewById(R.id.tv_faq_title);
        TextView tv_desc = findViewById(R.id.tv_faq_desc);

        tv_title.setText("Dimana saya bisa menemukan Kantin Lahap ?");
        tv_desc.setText("Lokasi perusahaan Kantin Lahap terletak di Jalan Alternatif Babakan Tengah " +
                "RT 02/ RW 08 Desa Babakan, Kecamatan Dramaga, Kab. Bogor. \n\nUntuk saat ini kamu bisa " +
                "menemukan Kantin Lahap dikantin Green Corner IPB yang berlokasi di Jalan Raya Meranti, " +
                "Dramaga, Bogor dan kantin-kantin lainnya yang tersebar di beberapa fakultas di IPB. " +
                "\n\nUntuk  pengembangannya,  pada  tahun  kedua -5  tahun  yang  akan  datang  kami  " +
                "akan  melakukan ekspansi  bisnis  di  beberapa  spot  lokasi  sekolah-sekolah  dan  " +
                "universitas  di  Kabupaten  Bogor. Sedangkan pada rentang waktu 5-10 tahun mendatang " +
                "adalah ekspansi bisnis ke sekolah-sekolah dan universitas beberapa titik potensial Provinsi Jawa Barat");

    }
}
