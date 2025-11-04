package com.example.kew_kredit_ilham1125100160_tii25;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PengajuanKreditActivity extends AppCompatActivity {

    // Class Models
    Kreditor kreditor = new Kreditor();
    Motor motor = new Motor();
    Kredit kredit = new Kredit();

    // UI Components
    Spinner SpinnerNamaKreditor, SpinnerNamaMotor;
    EditText editUangMuka, editBunga, editLamaAngsuran;
    TextView textNamaMotor, textAlamatKrditor, textNamaKreditor, textHargaMotor,
            textHargaKredit, textTotalKredit, textAngsuranPerbulan;
    Button buttonProsesPengajuanKredit, buttonSimpanPengajuanKredit, buttonResetKredit;

    // Data Containers
    ArrayList<String> arrayListNamaKreditor = new ArrayList<>();
    ArrayList<String> arrayListNamaMotor = new ArrayList<>();
    JSONArray arrayKreditor;
    JSONArray arrayMotor;

    // Calculation Variables
    private double HargaKredit = 0.0;
    private double TotalKredit = 0.0;
    private double Angsuran = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengajuan_kredit);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // --- Binding Views ---
        textNamaKreditor = (TextView) findViewById(R.id.TextNamaKreditor);
        textAlamatKrditor = (TextView) findViewById(R.id.TextAlamatKreditor);
        textNamaMotor = (TextView) findViewById(R.id.textNamaMotor);
        textHargaMotor = (TextView) findViewById(R.id.textHargaMotor);
        textHargaKredit = (TextView) findViewById(R.id.textHargaKredit);
        textTotalKredit = (TextView) findViewById(R.id.textTotalKredit);
        textAngsuranPerbulan = (TextView) findViewById(R.id.textAngsuranPerbulan);

        editBunga = (EditText) findViewById(R.id.editBunga);
        editUangMuka = (EditText) findViewById(R.id.editUangMuka);
        editLamaAngsuran = (EditText) findViewById(R.id.editLamaAngsuran);

        buttonProsesPengajuanKredit = (Button) findViewById(R.id.buttonProsesPengajuanKredit);
        buttonSimpanPengajuanKredit = (Button) findViewById(R.id.buttonSimpanPengajuanKredit);
        buttonResetKredit = (Button) findViewById(R.id.buttonResetKredit);

        SpinnerNamaKreditor = (Spinner) findViewById(R.id.SpinnerNamaKreditor);
        SpinnerNamaMotor = (Spinner) findViewById(R.id.SpinnerNamaMotor);

        // --- Spinner Listeners ---

        SpinnerNamaKreditor.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                getNamaKreditor();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        SpinnerNamaMotor.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                getKdmotorKredit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        // --- Load Initial Data ---
        SpinnerKreditor();
        SpinnerMotor();
    }

    // --- Data Loading Functions ---

    public void SpinnerKreditor() {
        try {
            // Asumsi: kreditor.tampilKreditorbyIdNama() hanya mengembalikan idkreditor dan nama
//            arrayKreditor = new JSONArray(kreditor.tampilKreditorbyIdNama());
            arrayKreditor = new JSONArray(kreditor.tampilKreditor());

            for (int i = 0; i < arrayKreditor.length(); i++) {
                JSONObject jsonChildNode = arrayKreditor.getJSONObject(i);
                // Mambil data dari nama tabel databse
                String idkreditor = jsonChildNode.optString("idkreditor");
                String nama = jsonChildNode.optString("nama");
                System.out.println("id:" + idkreditor);
                System.out.println("nama:" + nama);

                arrayListNamaKreditor.add(idkreditor); // Hanya menyimpan ID Kreditor di spinner
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // membuat adapter untuk menghubungkan spinner dengan data arraylist
        ArrayAdapter<String> adapterNamaKreditor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayListNamaKreditor);
        adapterNamaKreditor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // masukkan adapter kedalam spinner
        SpinnerNamaKreditor.setAdapter(adapterNamaKreditor);
        SpinnerNamaKreditor.setSelection(0);
    }

    public void SpinnerMotor() {
        try {
            // Asumsi: motor.tampilMotorbyIdNama() mengembalikan kdmotor, nama, harga
            arrayMotor = new JSONArray(motor.tampilMotorbyIdNama());

            for (int i = 0; i < arrayMotor.length(); i++) {
                JSONObject jsonChildNode = arrayMotor.getJSONObject(i);
                // ambil data dari nama tabel databse
                String kdmotor = jsonChildNode.optString("kdmotor");
                String nama = jsonChildNode.optString("nama");
                String harga = jsonChildNode.optString("harga");

                System.out.println("kdmotor" + kdmotor);
                System.out.println("nama:" + nama);
                System.out.println("harga :" + harga);

                arrayListNamaMotor.add(kdmotor); // Hanya menyimpan Kode Motor di spinner
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // membuat adapter untuk menghubungkan spinner dengan data arraylist
        ArrayAdapter<String> adapterNamaMotor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayListNamaMotor);
        adapterNamaMotor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // masukkan adapter kedalam spinner
        SpinnerNamaMotor.setAdapter(adapterNamaMotor);
        SpinnerNamaMotor.setSelection(0);
    }

    // --- Detail Fetching Functions ---

    public void getNamaKreditor() {
        String idkreditor = SpinnerNamaKreditor.getSelectedItem().toString(); // Ambil idkreditor dari spinner
        String namaEdit = null;
        String alamatEdit = null;

        try {
            // Asumsi: kreditor.selectByIdkreditor() ada dan mengembalikan data kreditor berdasarkan ID
//            JSONArray arrayKreditorDetail = new JSONArray(kreditor.select_by_Idkreditor(idkreditor));
            JSONArray arrayKreditorDetail = new JSONArray(kreditor.getKreditorByidkreditor(Integer.parseInt(idkreditor)));

            for (int i = 0; i < arrayKreditorDetail.length(); i++) {
                JSONObject jsonChildNode = arrayKreditorDetail.getJSONObject(i);
                namaEdit = jsonChildNode.optString("nama");
                alamatEdit = jsonChildNode.optString("alamat");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        textNamaKreditor.setText(namaEdit);
        textAlamatKrditor.setText(alamatEdit);
    }

    public void getKdmotorKredit() {
        String kdmotor = SpinnerNamaMotor.getSelectedItem().toString(); // Ambil kdmotor dari spinner
        String namaEdit = null;
        String hargaEdit = null;

        try {
            // Asumsi: motor.select_by_KdmotorKredit() ada dan mengembalikan data motor berdasarkan kode
            JSONArray arrayKodemotor = new JSONArray(motor.select_by_KdmotorKredit(kdmotor));

            for (int i = 0; i < arrayKodemotor.length(); i++) {
                JSONObject jsonChildNode = arrayKodemotor.getJSONObject(i);
                // idmotorEdit = jsonChildNode.optString("idmotor"); // Tidak digunakan
                // kdmotorEdit = jsonChildNode.optString("kdmotor"); // Tidak digunakan
                namaEdit = jsonChildNode.optString("nama");
                hargaEdit = jsonChildNode.optString("harga");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        textNamaMotor.setText(namaEdit);
        textHargaMotor.setText(hargaEdit);
    }

    // --- Calculation and Process Functions ---

    public void HitungKredit() {
        String sharga = textHargaMotor.getText().toString();
        String sdp = editUangMuka.getText().toString();
        String sbunga = editBunga.getText().toString();
        String slama = editLamaAngsuran.getText().toString();

        // Validasi input
        if (sharga.equalsIgnoreCase("") || sharga.equalsIgnoreCase("0") ||
                sdp.equalsIgnoreCase("0") || sdp.equalsIgnoreCase("") ||
                sbunga.equalsIgnoreCase("") || sbunga.equalsIgnoreCase("0") ||
                slama.equalsIgnoreCase("") || slama.equalsIgnoreCase("0")) {

            Toast.makeText(this, "silahkan Lengkapi Data", Toast.LENGTH_LONG).show();
            return;
        }

        // Konversi dan Hitung
        double harga = Double.parseDouble(sharga);
        double dp = Double.parseDouble(sdp);
        double bunga = Double.parseDouble(sbunga);
        double lama = Double.parseDouble(slama);

        HargaKredit = harga - dp;
        // Rumus Bunga: (HargaKredit * Bunga/100) * (Lama Angsuran / 12)
        // Rumus yang digunakan di source code: HargaKredit + (HargaKredit * (bunga/100) * 12)
        // Rumus yang digunakan di source code sepertinya mengasumsikan bunga per tahun dikali 12 bulan (salah secara logika kredit umum, tapi diikuti sesuai source)
        TotalKredit = HargaKredit + (HargaKredit * (bunga / 100) * 12);
        Angsuran = TotalKredit / lama;

        // Tampilkan Hasil (Menggunakan String.format untuk format angka)
        textHargaKredit.setText(String.format("%.2f", HargaKredit));
        textTotalKredit.setText(String.format("%.2f", TotalKredit));
        textAngsuranPerbulan.setText(String.format("%.2f", Angsuran));
    }

    public void simpanKredit() {
        // Ambil nilai dari UI components
        String idkreditor = SpinnerNamaKreditor.getSelectedItem().toString();
        String kdmotor = SpinnerNamaMotor.getSelectedItem().toString();
        String hrgtunai = textHargaMotor.getText().toString(); // Harga motor = Harga Tunai
        String dp = editUangMuka.getText().toString();
        String hrgkredit = textHargaKredit.getText().toString();
        String bunga = editBunga.getText().toString();
        String lama = editLamaAngsuran.getText().toString();
        String totalkredit = textTotalKredit.getText().toString();
        String angsuran = textAngsuranPerbulan.getText().toString();

        System.out.println("idkreditor : " + idkreditor + " kdmotor: " + kdmotor);

        // Panggil metode simpan
        String laporan = kredit.simpan_kredit(idkreditor, kdmotor, hrgtunai, dp, hrgkredit, bunga, lama, totalkredit, angsuran);

        Toast.makeText(PengajuanKreditActivity.this, laporan, Toast.LENGTH_SHORT).show();

        // Refresh activity setelah simpan
        finish();
        startActivity(getIntent());
    }

    // --- OnClick Handlers from XML ---

    public void KlikbuttonProsesPengajuanKredit(View v) {
        HitungKredit();
    }

    public void KlikbuttonResetKredit(View v) {
        editUangMuka.setText("");
        editBunga.setText("");
        editLamaAngsuran.setText("");
        textTotalKredit.setText("0"); // Reset display
        textAngsuranPerbulan.setText("0"); // Reset display
        textHargaKredit.setText("0"); // Reset display
    }

    public void KlikbuttonSimpanPengajuanKredit(View v) {
        simpanKredit();
    }
}