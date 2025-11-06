package com.example.kew_kredit_ilham1125100160_tii25;

import android.os.Bundle;

import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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
//                Toast.makeText(DataPengajuanKreditActivity.this, "Tugas Cetak PDF", Toast.LENGTH_SHORT).show();
//                return;
                try {
                    // Ambil data JSON untuk baris ini
                    JSONObject dataKredit = arrayQueryKredit.getJSONObject(i);

                    // Ganti "Admin Sales" dengan nama petugas yang login (sesuai instruksi UAS)
                    // Ini adalah placeholder, ganti dengan data login yang sebenarnya.
                    String namaPetugas = "Admin Sales";

                    // Panggil metode untuk membuat PDF
                    buatPdfKredit(dataKredit, namaPetugas);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Gagal mengambil data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
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

    /**
     * Membuat file PDF Surat Pengajuan Kredit
     *
     * @param data        JSONObject berisi data lengkap dari baris yang dipilih
     * @param namaPetugas Nama petugas yang sedang login (sesuai instruksi UAS)
     */
    private void buatPdfKredit(JSONObject data, String namaPetugas) {
        try {
            // 1. Ambil semua data dari JSONObject
            String invoice = data.optString("invoice");
            String tanggal = data.optString("tanggal");
            String idkreditor = data.optString("idkreditor");
            String nama = data.optString("nama");
            String alamat = data.optString("alamat");
            String kdmotor = data.optString("kdmotor");
            String nmotor = data.optString("nmotor");
            String hrgtunai = data.optString("hrgtunai");
            String dp = data.optString("dp");
            String hrgkredit = data.optString("hrgkredit");
            String bunga = data.optString("bunga");
            String lama = data.optString("lama");
            String totalkredit = data.optString("totalkredit");
            String angsuran = data.optString("angsuran");

            // 2. Dapatkan tanggal cetak saat ini (sesuai instruksi UAS)
            // Format "Semarang, 01-Feb-2019" sesuai contoh
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", new Locale("id", "ID"));
            String tglCetak = sdf.format(new Date());

            // 3. Tentukan lokasi dan nama file
            String mFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    + "/Kredit_" + invoice + ".pdf";
            File mFile = new File(mFilePath);

            // 4. Buat Dokumen PDF
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(mFile));
            document.open();

            // 5. Tambahkan Konten
            Font fontJudul = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
            Font fontNormal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
            Font fontTabelLabel = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

            // Judul
            Paragraph judul = new Paragraph("SURAT PENGAJUAN KREDIT\n\n", fontJudul);
            judul.setAlignment(Element.ALIGN_CENTER);
            document.add(judul);

            // Buat tabel untuk data agar rapi
            PdfPTable table = new PdfPTable(3); // 3 kolom (Label, :, Isi)
            table.setWidthPercentage(100);
            table.setWidths(new float[]{35, 5, 60}); // Persentase lebar kolom
            table.getDefaultCell().setBorder(Rectangle.NO_BORDER); // Tanpa border

            // Tambahkan data ke tabel
            tambahBarisTabel(table, "Invoice", ":", invoice, fontTabelLabel, fontNormal);
            tambahBarisTabel(table, "Tanggal", ":", tanggal, fontTabelLabel, fontNormal);
            tambahBarisTabel(table, "ID Kreditor", ":", idkreditor, fontTabelLabel, fontNormal);
            tambahBarisTabel(table, "Nama", ":", nama, fontTabelLabel, fontNormal);
            tambahBarisTabel(table, "Alamat", ":", alamat, fontTabelLabel, fontNormal);
            tambahBarisTabel(table, "Kode Motor", ":", kdmotor, fontTabelLabel, fontNormal);
            tambahBarisTabel(table, "Nama Motor", ":", nmotor, fontTabelLabel, fontNormal);
            tambahBarisTabel(table, "Harga Tunai", ":", "Rp." + hrgtunai + ",-", fontTabelLabel, fontNormal);
            tambahBarisTabel(table, "DP", ":", "Rp." + dp + ",-", fontTabelLabel, fontNormal);
            tambahBarisTabel(table, "Harga Kredit", ":", "Rp." + hrgkredit + ",-", fontTabelLabel, fontNormal);
            tambahBarisTabel(table, "Bunga", ":", bunga + "%/Tahun", fontTabelLabel, fontNormal);
            tambahBarisTabel(table, "Lama", ":", lama + " Bulan", fontTabelLabel, fontNormal);
            tambahBarisTabel(table, "Total Kredit", ":", "Rp." + totalkredit + ",-", fontTabelLabel, fontNormal);
            tambahBarisTabel(table, "Angsuran /bln", ":", "Rp." + angsuran + ",-", fontTabelLabel, fontNormal);

            document.add(table);

            // 6. Tambahkan Bagian Pernyataan dan Tanda Tangan
            Paragraph pernyataan = new Paragraph("\n\nMenyatakan bahwa data diatas benar.\n\n", fontNormal);
            pernyataan.setAlignment(Element.ALIGN_LEFT);
            document.add(pernyataan);

            // Bagian Tanda Tangan
            Paragraph ttd = new Paragraph();
            ttd.setAlignment(Element.ALIGN_RIGHT);
            ttd.add(new Phrase("Semarang, " + tglCetak + "\n\n\n\n", fontNormal)); // Tanggal Cetak (Instruksi UAS)
            ttd.add(new Phrase(namaPetugas, fontNormal)); // Nama Petugas (Instruksi UAS)
            document.add(ttd);

            // 7. Tutup Dokumen
            document.close();

            // 8. Beri notifikasi
            Toast.makeText(this, "PDF berhasil disimpan di " + mFilePath, Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Gagal membuat PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Helper method untuk menambahkan baris ke tabel PDF
     */
    private void tambahBarisTabel(PdfPTable table, String label, String separator, String isi, Font fontLabel, Font fontIsi) {
        // Sel 1: Label (Bold)
        PdfPCell cellLabel = new PdfPCell(new Phrase(label, fontLabel));
        cellLabel.setBorder(Rectangle.NO_BORDER);
        table.addCell(cellLabel);

        // Sel 2: Separator (:)
        PdfPCell cellSeparator = new PdfPCell(new Phrase(separator, fontIsi));
        cellSeparator.setBorder(Rectangle.NO_BORDER);
        cellSeparator.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellSeparator);

        // Sel 3: Isi (Normal)
        PdfPCell cellIsi = new PdfPCell(new Phrase(isi, fontIsi));
        cellIsi.setBorder(Rectangle.NO_BORDER);
        table.addCell(cellIsi);
    }
}