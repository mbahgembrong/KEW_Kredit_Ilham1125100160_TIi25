package com.example.kew_kredit_ilham1125100160_tii25;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources(); // Resource object untuk mendapatkan mendapatkan gambar
        TabHost tabHost = getTabHost(); // activity TabHost
        TabHost.TabSpec spec; // menggunakan TabSpec untuk tab lain
        Intent intent; // Menggunakan Intent untuk tab yg lain

        // Membuat intent untuk menampilkan activity ke dalam Tab yg digunakan
        intent = new Intent().setClass(this, HomeActivity.class);

        // inisialisasi TabSpec untuk tab yg lain dan menambahkan ke TabHost
        spec = tabHost.newTabSpec("Home").setIndicator("Home",
                        res.getDrawable(R.drawable.ic_tab_home))
                .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, TransaksiActivity.class);
        spec = tabHost.newTabSpec("Transaksi").setIndicator("Transaksi",
                        res.getDrawable(R.drawable.ic_tab_tansaksi))
                .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, AboutActivity.class);
        spec = tabHost.newTabSpec("About").setIndicator("About",
                        res.getDrawable(R.drawable.ic_tab_about))
                .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(3);
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
}