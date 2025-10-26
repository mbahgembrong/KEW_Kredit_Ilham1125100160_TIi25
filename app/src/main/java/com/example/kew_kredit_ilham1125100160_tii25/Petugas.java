package com.example.kew_kredit_ilham1125100160_tii25;

public class Petugas extends Koneksi {
    private long id;
    Server server = new Server();

    String SERVER = server.urlDatabase1();
    String URL = "http://" + SERVER + "/pemograman-mobile-2-web/tbpetugas.php";
    String url = "";
    String response = "";

    public String tampilPetugas() {
        try {
            url = URL + "?operasi=view";
            System.out.println("URL Tampil Petugas: " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String insertPetugas(String kdpetugas, String nama, String jabatan) {
        nama = nama.replace(" ", "%20");

        try {
            url = URL + "?operasi=insert&kdpetugas=" + kdpetugas + "&nama=" + nama + "&jabatan=" + jabatan;
            System.out.println("URL Insert Petugas : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String getPetugasByKdpetugas(int idpetugas) {
        try {
            url = URL + "?operasi=get_petugas_by_kdpetugas&idpetugas=" + idpetugas;
            System.out.println("URL Get Petugas: " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String updatePetugas(String idpetugas, String kdpetugas, String nama, String jabatan) {
        nama = nama.replace(" ", "%20");
        try {
            url = URL + "?operasi=update&idpetugas=" + idpetugas + "&kdpetugas=" + kdpetugas + "&nama=" + nama + "&jabatan=" + jabatan;
            System.out.println("URL Update Petugas : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String deletePetugas(int idpetugas) {
        try {
            url = URL + "?operasi=delete&idpetugas=" + idpetugas;
            System.out.println("URL Hapus Petugas: " + url);
            response = call(url);
        } catch (Exception e) {
            // H
        }
        return response;
    }

    public long getId() {
        // TODO Auto-generated method stub
        return id;
        //return null;
    }

}
