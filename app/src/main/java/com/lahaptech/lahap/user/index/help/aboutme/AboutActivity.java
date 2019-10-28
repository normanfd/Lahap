package com.lahaptech.lahap.user.index.help.aboutme;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lahaptech.lahap.R;

public class AboutActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView textView = findViewById(R.id.tv_about_desc);
        textView.setText("Mayoritas Kantin sekolah di Indonesia belum menyediakan jajanan sehat bagi " +
                "siswanya, padahal asupan nutrisi sangat peting bagi perkembangan siswa dalam belajar. " +
                "Hingga saat ini, mayoritas jajanan sekolah didominasi oleh jajanan tradisional dan " +
                "konvensional hingga junk food yang kerap kali tidak memenuhi standar gizi sehat. \n\n" +
                "Makanan yang bergizi seimbang berperan sangat penting bagi kesehatan tubuh siswa. " +
                "Atas dasar inilah, Lahap memiliki cita-cita mulia untuk menyiapkan generasi muda yang " +
                "sehat dan gemilang saat  ini  dan  masa  depan.  Mulai  dari  membudayakan  Trend  " +
                "sarapan  pagi  dan  makan  siang  yang menyehatkan yang disediakan di Kantin sekolah " +
                "tentu cita-cita-cita baik tersebut dapat terwujud. \n\nOleh karena itu,  Lahap  " +
                "melihat adanya kesempatan untuk mengembangkan konsep kantin sehat tersebut  di  sekolah  " +
                "&  universitas  seluruh  Indonesia  namun  juga  modern  karena  berbasis  IoT. " +
                "Gagasan  Lahap dimulai dari komitmen menciptakan revolusi kantin sekolah  yang sehat " +
                "melalui pendekatan teknologi. \n\nSelain itu, Lahap melihat potensi lokal dalam pengadaan " +
                "makanan sehat dan bergizi untuk sekolahdan universitas sangat bisa untuk dioptimalkan. " +
                "Selain bisa memberikan nilai tambah pada produk lokal, hal tersebut juga penting untuk " +
                "menciptakan peluang pasar baru bagi para pelaku usaha lokal yang  ada di daerah baik itu " +
                "petani, peternak, nelayan, maupun UMKM. Sehingga lahap melihat ada  jalan  untuk  " +
                "pemberdayaan  sumber  daya  lokal  dan  pengadaan  makanan  sehat  bagi  siswa Indonesia. " +
                "Lahap mengembangkan kesempatan bermitra dengan sekolah dan supplier lokal serta " +
                "banyak rekan bisnis untuk saling berkolaborasi.\n\nLahap percaya kemajuan teknologi " +
                "sepatutnya beriringan dengan tumbuhnya semangat kebaikan. Semangat kami melalui " +
                " pengembangan kantin berbasis teknologi sebagai  bisnis potensial untuk membantu memperbaiki " +
                "perekonomian masyarakatdan kualitas hidup siswa Indonesia karena di kantin terjadi " +
                "aktivitas sosial, ekonomi dan cerita teman seperjuangan. Sehingga dengan semangat yang " +
                "sama siapapun bisa #MakandenganLahap dan berkembang bersamakantinLahap.");

    }
}
