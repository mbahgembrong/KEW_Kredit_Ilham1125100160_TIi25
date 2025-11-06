package com.example.kew_kredit_ilham1125100160_tii25;

public class Kredit extends Koneksi {
    private long id;
    Server server = new Server();
    String SERVER = server.urlDatabase1();
    String URL = "http://" + SERVER + "/pemograman-mobile-2-web/tbkredit.php"; // Mengarah ke tbkredit.php
    String url = "";
    String response = "";

    public String tampil_query_kredit() {
        try {
            url = URL + "?operasi=query_kredit";
            System.out.println("URL tampil query kredit: " + url);
            response = call(url);
        } catch (Exception e) {
            return response;
        }
        return response;
    }

    // Metode ini seharusnya ada di kelas Motor/Kreditor, tetapi tetap disertakan sesuai source
    public String tampilKreditorbyIdNama() {
        try {
            url = URL + "?operasi=select_by_idnama";
            System.out.println("URL Tampil Kreditor: " + url);
            response = call(url);
        } catch (Exception e) {
            return response;
        }
        return response;
    }

    public String simpan_kredit(String idkreditor, String kdmotor, String hrgtunai, String dp, String hrgkredit, String bunga, String lama, String totalkredit, String angsuran) {

        // Baris replace nama dan alamat diabaikan karena tidak relevan/lengkap
        // nama.replace("", "20");
        // alamat.replace("", "20");

        try {
            url = URL + "?operasi=simpan_kredit&idkreditor=" + idkreditor +
                    "&kdmotor=" + kdmotor + "&hrgtunai=" + hrgtunai + "&dp=" + dp +
                    "&hrgkredit=" + hrgkredit + "&bunga=" + bunga + "&lama=" + lama +
                    "&totalkredit=" + totalkredit + "&angsuran=" + angsuran;
            url = url.replace(" ", "%20"); // Ganti spasi dengan %20

            System.out.println("URL simpan kredit : " + url);
            response = call(url);
        } catch (Exception e) {
            return response;
        }
        return response;
    }

    public String hapus_kredit(int invoice) {
        try {
            url = URL + "?operasi=hapus_kredit&invoice=" + invoice;
            System.out.println("URL Hapus kredit: " + url);
            response = call(url);
        } catch (Exception e) {
            return response;
        }
        return response;
    }

    public long getId() {
        // TODO Auto-generated method stub
        return id;
    }
}
