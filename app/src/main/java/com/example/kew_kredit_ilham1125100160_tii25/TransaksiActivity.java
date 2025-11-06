package com.example.kew_kredit_ilham1125100160_tii25;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TransaksiActivity extends AppCompatActivity {

    private Button btPengajuanKredit, btPembayaranAngsuran, btDataPengajuanKredit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transaksi);
        // Inisialisasi komponen Button dari layout
        btPengajuanKredit = (Button) findViewById(R.id.btPengajuanKredit);
        btPembayaranAngsuran = (Button) findViewById(R.id.btPembayaranAngsuran);
        btDataPengajuanKredit = (Button) findViewById(R.id.btDataPengajuanKredit);
    }

    /**
     * Metode yang dipanggil ketika tombol 'Pengajuan Kredit' diklik.
     *
     * @param v View (Button) yang memicu event
     */
    public void KlikbtPengajuanKredit(View v) {
        // Membuat Intent baru untuk memulai PengajuanKreditActivity
        Intent i = new Intent(getApplicationContext(), PengajuanKreditActivity.class);
        startActivity(i);
    }

    /**
     * Metode yang dipanggil ketika tombol 'Pembayaran Angsuran' diklik.
     *
     * @param v View (Button) yang memicu event
     */
    public void KlikbtPembayaranAngsuran(View v) {
        // Membuat Intent baru untuk memulai PembayaranAngsuranActivity
        Intent i = new Intent(getApplicationContext(), PembayaranAngsuranActivity.class);
        startActivity(i);
    }

    /**
     * Metode yang dipanggil ketika tombol 'Data Pengajuan Kredit' diklik.
     *
     * @param v View (Button) yang memicu event
     */
    public void KlikbtDataPengajuanKredit(View v) {
        // Membuat Intent baru untuk memulai DataPengajuanKreditActivity
        Intent i = new Intent(getApplicationContext(), DataPengajuanKreditActivity.class);
        startActivity(i);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.transaksi, menu);
//        return true;
//    }
}