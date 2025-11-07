package com.example.kew_kredit_ilham1125100160_tii25;

public class Kreditor extends Koneksi {
    private long id;
    Server server = new Server();
    String SERVER = server.urlDatabase1();
    // Ganti tbmotor.php menjadi tbkreditor.php
    String URL = "http://" + SERVER + "/tbkreditor.php";
    String url = "";
    String response = "";

    public String tampilKreditor() {
        try {
            url = URL + "?operasi=view";
            System.out.println("URL Tampil Kreditor: " + url);
            response = call(url);
        } catch (Exception e) {
            return response;
        }
        return response;
    }

    public String insertKreditor(String nama, String pekerjaan, String telp, String alamat) {
        // String nama = nama.replace(" ", "%20"); // Contoh encoding spasi
        try {
            url = URL + "?operasi=insert&nama=" + nama + "&pekerjaan=" + pekerjaan + "&telp=" + telp + "&alamat=" + alamat;
            System.out.println("URL Insert Kreditor: " + url);
            response = call(url);
        } catch (Exception e) {
            return response;
        }
        return response;
    }

    public String getKreditorByidkreditor(int idkreditor) {
        try {
            url = URL + "?operasi=get_kreditor_by_idkreditor&idkreditor=" + idkreditor;
            System.out.println("URL Get Kreditor: " + url);
            response = call(url);
        } catch (Exception e) {
            return response;
        }
        return response;
    }

    public String updateKreditor(String idkreditor, String nama, String pekerjaan, String telp, String alamat) {
        // String nama = nama.replace(" ", "%20"); // Contoh encoding spasi
        try {
            url = URL + "?operasi=update&idkreditor=" + idkreditor + "&nama=" + nama + "&pekerjaan=" + pekerjaan + "&telp=" + telp + "&alamat=" + alamat;
            System.out.println("URL Update Kreditor: " + url);
            response = call(url);
        } catch (Exception e) {
            return response;
        }
        return response;
    }

    public String deleteKreditor(int idkreditor) {
        try {
            url = URL + "?operasi=delete&idkreditor=" + idkreditor;
            System.out.println("URL Hapus Kreditor: " + url);
            response = call(url);
        } catch (Exception e) {
            return response;
        }
        return response;
    }

    // Asumsi metode lain (seperti select_by_idnama) tidak diperlukan untuk Data Kreditor

    public long getId() {
        return id;
    }
}
