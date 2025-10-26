package com.example.kew_kredit_ilham1125100160_tii25;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class DataKreditorActivity extends AppCompatActivity implements OnClickListener {

    Kreditor kreditor = new Kreditor();
    TableLayout tbKreditor;
    Button btTambahKreditor, btRefreshDataKreditor;
    ArrayList<Button> buttonEdit = new ArrayList<>();
    ArrayList<Button> buttonDelete = new ArrayList<>();
    JSONArray arrayKreditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_kreditor);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // Inisialisasi komponen
        tbKreditor = (TableLayout) findViewById(R.id.tbKreditor);
        btTambahKreditor = (Button) findViewById(R.id.btTambahKreditor); // Asumsi ID ini ada di XML
        btRefreshDataKreditor = (Button) findViewById(R.id.btRefreshDataKreditor); // Asumsi ID ini ada di XML

        tampilDataKreditor();
    }

    // Dipanggil dari XML saat tombol TAMBAH diklik
    public void KlikbtTambahKreditor(View v) {
        tambahKreditor();
    }

    // Dipanggil dari XML saat tombol REFRESH diklik
    public void KlikbtRefreshDataKreditor(View v) {
        finish();
        startActivity(getIntent()); // Restart activity
    }

    public void tampilDataKreditor() {
        // --- Membangun Header Tabel ---
        TableRow barisTabelHeader = new TableRow(this);
        barisTabelHeader.setBackgroundColor(Color.BLACK);

        String[] headers = {"Nama", "Pekerjaan", "Telp", "Alamat", "Action"};
        for (String header : headers) {
            TextView viewHeader = new TextView(this);
            viewHeader.setText(header);
            viewHeader.setTextColor(Color.WHITE);
            viewHeader.setPadding(5, 1, 5, 1);
            barisTabelHeader.addView(viewHeader);
        }
        tbKreditor.addView(barisTabelHeader, new TableLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        // --- Mengisi Data dari Server ---
        try {
            arrayKreditor = new JSONArray(kreditor.tampilKreditor());

            for (int i = 0; i < arrayKreditor.length(); i++) {
                JSONObject jsonChildNode = arrayKreditor.getJSONObject(i);

                // Ambil data dari JSON
                String idkreditor = jsonChildNode.optString("idkreditor");
                String nama = jsonChildNode.optString("nama");
                String pekerjaan = jsonChildNode.optString("pekerjaan");
                String telp = jsonChildNode.optString("telp");
                String alamat = jsonChildNode.optString("alamat");

                // Membuat baris data
                TableRow barisTabel = new TableRow(this);
                if (i % 2 == 0) {
                    barisTabel.setBackgroundColor(Color.LTGRAY);
                }

                String[] data = {nama, pekerjaan, telp, alamat};
                for (String item : data) {
                    TextView viewData = new TextView(this);
                    viewData.setText(item);
                    viewData.setPadding(5, 1, 5, 1);
                    barisTabel.addView(viewData);
                }

                // Menambahkan Button Edit
                Button btnEdit = new Button(this);
                btnEdit.setId(Integer.parseInt(idkreditor));
                btnEdit.setTag("Edit");
                btnEdit.setText("Edit");
                btnEdit.setOnClickListener(this);
                barisTabel.addView(btnEdit);
                buttonEdit.add(btnEdit);

                // Menambahkan Button Delete
                Button btnDelete = new Button(this);
                btnDelete.setId(Integer.parseInt(idkreditor));
                btnDelete.setTag("Delete");
                btnDelete.setText("Delete");
                btnDelete.setOnClickListener(this);
                barisTabel.addView(btnDelete);
                buttonDelete.add(btnDelete);

                tbKreditor.addView(barisTabel, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Metode untuk menampilkan dialog Insert
    public void tambahKreditor() {
        LinearLayout layoutInput = new LinearLayout(this);
        layoutInput.setOrientation(LinearLayout.VERTICAL);

        // Input fields
        final EditText editNama = new EditText(this);
        editNama.setHint("NamaKreditor");
        layoutInput.addView(editNama);

        final EditText editPekerjaan = new EditText(this);
        editPekerjaan.setHint("Pekerjaan");
        layoutInput.addView(editPekerjaan);

        final EditText editTelp = new EditText(this);
        editTelp.setHint("Telp");
        editTelp.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        layoutInput.addView(editTelp);

        final EditText editAlamat = new EditText(this);
        editAlamat.setHint("Alamat");
        layoutInput.addView(editAlamat);

        AlertDialog.Builder builderInsert = new AlertDialog.Builder(this);
        builderInsert.setTitle("Insert Kreditor");
        builderInsert.setView(layoutInput);

        builderInsert.setPositiveButton("Insert", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nama = editNama.getText().toString();
                String pekerjaan = editPekerjaan.getText().toString();
                String telp = editTelp.getText().toString();
                String alamat = editAlamat.getText().toString();

                String laporan = kreditor.insertKreditor(nama, pekerjaan, telp, alamat);
                Toast.makeText(DataKreditorActivity.this, laporan, Toast.LENGTH_SHORT).show();

                finish();
                startActivity(getIntent());
            }
        });

        builderInsert.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builderInsert.show();
    }

    // Metode untuk mengambil data tunggal dan menampilkan dialog Update
    public void getKreditorByidkreditor(int idkreditor) {
        String idkreditorEdit = null;
        String namaEdit = null;
        String pekerjaanEdit = null;
        String telpEdit = null;
        String alamatEdit = null;

        try {
            JSONArray arrayPersonal = new JSONArray(kreditor.getKreditorByidkreditor(idkreditor));
            if (arrayPersonal.length() > 0) {
                JSONObject jsonChildNode = arrayPersonal.getJSONObject(0);
                idkreditorEdit = jsonChildNode.optString("idkreditor");
                namaEdit = jsonChildNode.optString("nama");
                pekerjaanEdit = jsonChildNode.optString("pekerjaan");
                telpEdit = jsonChildNode.optString("telp");
                alamatEdit = jsonChildNode.optString("alamat");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayout layoutInput = new LinearLayout(this);
        layoutInput.setOrientation(LinearLayout.VERTICAL);

        // Input ID (Hidden/Read-only, asumsi tidak ditampilkan di UI update)
        final TextView editIdKreditor = new EditText(this);
        editIdKreditor.setText(idkreditorEdit);
        // layoutInput.addView(editIdKreditor); // Bisa di-comment agar ID tersembunyi

        // Input Nama
        final EditText editNama = new EditText(this);
        editNama.setText(namaEdit);
        layoutInput.addView(editNama);

        // Input Pekerjaan
        final EditText editPekerjaan = new EditText(this);
        editPekerjaan.setText(pekerjaanEdit);
        layoutInput.addView(editPekerjaan);

        // Input Telp
        final EditText editTelp = new EditText(this);
        editTelp.setText(telpEdit);
        editTelp.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        layoutInput.addView(editTelp);

        // Input Alamat
        final EditText editAlamat = new EditText(this);
        editAlamat.setText(alamatEdit);
        layoutInput.addView(editAlamat);

        AlertDialog.Builder builderEdit = new AlertDialog.Builder(this);
        builderEdit.setTitle("Update Kreditor");
        builderEdit.setView(layoutInput);

        builderEdit.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String idkreditor = editIdKreditor.getText().toString();
                String nama = editNama.getText().toString();
                String pekerjaan = editPekerjaan.getText().toString();
                String telp = editTelp.getText().toString();
                String alamat = editAlamat.getText().toString();

                // Memanggil update dengan ID yang diambil saat inisialisasi
                String laporan = kreditor.updateKreditor(idkreditor, nama, pekerjaan, telp, alamat);

                Toast.makeText(DataKreditorActivity.this, laporan, Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
            }
        });

        builderEdit.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builderEdit.show();
    }

    // Metode untuk menghapus data
    public void deleteKreditor(int idkreditor) {
        kreditor.deleteKreditor(idkreditor);
        Toast.makeText(this, "Data dengan ID " + idkreditor + " dihapus.", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(getIntent());
    }

    @Override
    public void onClick(View view) {
        for (int i = 0; i < buttonEdit.size(); i++) {
            int id = view.getId();

            if (id == buttonEdit.get(i).getId() && view.getTag().toString().trim().equals("Edit")) {
                getKreditorByidkreditor(id);
                return;
            } else if (id == buttonDelete.get(i).getId() && view.getTag().toString().trim().equals("Delete")) {
                deleteKreditor(id);
                return;
            }
        }
    }
}