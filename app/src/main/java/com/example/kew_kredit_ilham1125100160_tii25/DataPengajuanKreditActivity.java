package com.example.kew_kredit_ilham1125100160_tii25;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class DataPengajuanKreditActivity extends AppCompatActivity implements OnClickListener {

    Kredit kredit = new Kredit();
    CetakPdf cetakpdf = new CetakPdf(); // Asumsi kelas ini ada
    Server server = new Server(); // Asumsi kelas ini ada

    TableLayout tbQueryKredit;
    Button btRefreshKredit;

    // Perhatikan: Nama variabel di source code adalah buttonPdf dan buttonDelete
    ArrayList<Button> buttonPdf = new ArrayList<>();
    ArrayList<Button> buttonDelete = new ArrayList<>();

    JSONArray arrayQueryKredit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pengajuan_kredit);

        if (android.os.Build.VERSION.SDK_INT >= 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // Pemberian Nama komponen
        tbQueryKredit = (TableLayout) findViewById(R.id.tbQueryKredit);
        btRefreshKredit = (Button) findViewById(R.id.btRefreshKredit);

        tampilQueryKredit();
    }

    // Tampil data Pengajuan Kredit
    public void tampilQueryKredit() {
        TableRow barisTabel = new TableRow(this);
        barisTabel.setBackgroundColor(Color.BLACK);

        // --- Membangun Header Tabel ---
        String[] headers = {
                "Invoice", "Tgl", "IdKreditor", "Nama", "Alamat", "KdMotor", "NmMotor",
                "HrgTunai", "DP", "HrgKredit", "Bunga", "Lama", "TotKredit", "Angsuran", "Action"
        };

        for (String header : headers) {
            TextView viewHeader = new TextView(this);
            viewHeader.setText(header);
            viewHeader.setTextColor(Color.WHITE);
            viewHeader.setPadding(5, 1, 5, 1);
            barisTabel.addView(viewHeader);
        }

        tbQueryKredit.addView(barisTabel, new TableLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        // --- Mengisi Data dari Server ---
        try {
            arrayQueryKredit = new JSONArray(kredit.tampil_query_kredit());

            for (int i = 0; i < arrayQueryKredit.length(); i++) {
                JSONObject jsonChildNode = arrayQueryKredit.getJSONObject(i);

                // Ambil data dari JSON
                String invoice = jsonChildNode.optString("invoice");
                String tanggal = jsonChildNode.optString("tanggal");
                String idkreditor = jsonChildNode.optString("idkreditor");
                String nama = jsonChildNode.optString("nama");
                String alamat = jsonChildNode.optString("alamat");
                String kdmotor = jsonChildNode.optString("kdmotor");
                String nmotor = jsonChildNode.optString("nmotor");
                String hrgtunai = jsonChildNode.optString("hrgtunai");
                String dp = jsonChildNode.optString("dp");
                String hrgkredit = jsonChildNode.optString("hrgkredit");
                String bunga = jsonChildNode.optString("bunga");
                String lama = jsonChildNode.optString("lama");
                String totalkredit = jsonChildNode.optString("totalkredit");
                String angsuran = jsonChildNode.optString("angsuran");

                System.out.println("invoice:" + invoice);

                barisTabel = new TableRow(this);
                if (i % 2 == 0) {
                    barisTabel.setBackgroundColor(Color.LTGRAY);
                }

                // Data Kolom (urutan harus sesuai header)
                String[] data = {
                        invoice, tanggal, idkreditor, nama, alamat, kdmotor, nmotor,
                        hrgtunai, dp, hrgkredit, bunga, lama, totalkredit, angsuran
                };

                for (String item : data) {
                    TextView viewData = new TextView(this);
                    viewData.setText(item);
                    viewData.setPadding(5, 1, 5, 1);
                    viewData.setGravity(Gravity.CENTER_VERTICAL);
                    barisTabel.addView(viewData);
                }

                // Membuat Button PDF
                Button btnPdf = new Button(this);
                btnPdf.setId(Integer.parseInt(invoice));
                btnPdf.setTag("PDF");
                btnPdf.setText("PDF");
                btnPdf.setOnClickListener(this);
                barisTabel.addView(btnPdf);
                buttonPdf.add(btnPdf);

                // Membuat Button Delete
                Button btnDelete = new Button(this);
                btnDelete.setId(Integer.parseInt(invoice));
                btnDelete.setTag("Delete");
                btnDelete.setText("Delete");
                btnDelete.setOnClickListener(this);
                barisTabel.addView(btnDelete);
                buttonDelete.add(btnDelete);

                tbQueryKredit.addView(barisTabel, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Hapus Kredit berdasarkan Invoice
    public void deleteKredit(int invoice) {
        kredit.hapus_kredit(invoice);
        // restart activity
        finish();
        startActivity(getIntent());
    }

    // Dipanggil dari XML saat tombol REFRESH diklik
    public void KlikbtRefreshKredit(View v) {
        // restart acrtivity
        finish();
        startActivity(getIntent());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        // Cek tombol PDF
        for (int i = 0; i < buttonPdf.size(); i++) {
            if (id == buttonPdf.get(i).getId() && view.getTag().toString().trim().equals("PDF")) {
                Toast.makeText(DataPengajuanKreditActivity.this, "Tugas Cetak PDF", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Cek tombol DELETE
        for (int i = 0; i < buttonDelete.size(); i++) {
            if (id == buttonDelete.get(i).getId() && view.getTag().toString().trim().equals("Delete")) {
                int invoice = buttonDelete.get(i).getId();
                deleteKredit(invoice);
                return;
            }
        }
    }
}