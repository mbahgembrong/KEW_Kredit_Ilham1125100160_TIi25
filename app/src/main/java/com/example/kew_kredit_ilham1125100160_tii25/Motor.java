package com.example.kew_kredit_ilham1125100160_tii25;

public class Motor extends Koneksi {
    private long id; // Variabel ID motor

    // Inisialisasi Server dan URL API
    Server server = new Server();
    String SERVER = server.urlDatabase1();
    String URL = "http://" + SERVER + "/tbmotor.php"; // URL lengkap ke script PHP

    String url = "";
    String response = "";

    /**
     * Mengambil semua data motor dari server (operasi=view).
     */
    public String tampilMotor() {
        try {
            url = URL + "?operasi=view";
            System.out.println("URL, Tampil Motor: " + url);
            response = call(url);
        } catch (Exception e) {
            return response;
        }
        return response;
    }

    /**
     * Mengambil data motor untuk keperluan dropdown/list dengan kolom id dan nama.
     * (operasi=select_by_idnama).
     */
    public String tampilMotorbyIdNama() {
        try {
            url = URL + "?operasi=select_by_idnama";
            System.out.println("URL Tampil Kreditor: " + url);
            response = call(url);
        } catch (Exception e) {
            return response;
        }
        return response;
    }

    /**
     * Menyimpan data motor baru ke database (operasi=insert).
     */
    public String insertMotor(String kdmotor, String nama, String harga) {
        // Baris untuk replace karakter dihilangkan karena tidak lengkap/jelas di sumber
        // nama nama.replace("20")
        try {
            url = URL + "?operasi=insert&kdmotor=" + kdmotor + "&nama=" + nama + "&harga=" + harga;
            System.out.println("URL Insert Motor: " + url);
            response = call(url);
        } catch (Exception e) {
            // H
            return response;
        }
        return response;
    }

    /**
     * Mengambil detail satu motor berdasarkan idmotor (operasi=get_motor_by_kdmotor).
     */
    public String getMotorByKdmotor(int idmotor) {
        try {
            url = URL + "?operasi=get_motor_by_kdmotor&idmotor=" + idmotor;
            System.out.println("URL Get Motor: " + url);
            response = call(url);
        } catch (Exception e) {
            return response;
        }
        return response;
    }

    /**
     * Mengambil data motor berdasarkan kdmotor untuk keperluan kredit.
     * (operasi=select_by_kdmotorkredit).
     */
    public String select_by_KdmotorKredit(String kdmotor) {
        try {
            url = URL + "?operasi=select_by_kdmotorkredit&kdmotor=" + kdmotor;
            System.out.println("URL Get Motor: " + url);
            response = call(url);
        } catch (Exception e) {
            return response;
        }
        return response;
    }

    /**
     * Memperbarui data motor (nama dan harga) berdasarkan idmotor (operasi=update).
     */
    public String updateMotor(String idmotor, String kdmotor, String nama, String harga) {
        // Baris untuk replace karakter dihilangkan karena tidak lengkap/jelas di sumber
        // nama.replace("","120");
        // kdmotor.replace("","20");
        try {
            url = URL + "?operasi=update&idmotor=" + idmotor + "&kdmotor=" + kdmotor + "&nama=" + nama + "&harga=" + harga;
            System.out.println("URL Update Motor: " + url);
            response = call(url);
        } catch (Exception e) {
            return response;
        }
        return response;
    }

    /**
     * Menghapus data motor berdasarkan idmotor (operasi=delete).
     */
    public String deleteMotor(int idmotor) {
        try {
            url = URL + "?operasi=delete&idmotor=" + idmotor;
            System.out.println("URL Hapus Motor: " + url);
            response = call(url);
        } catch (Exception e) {
            return response;
        }
        return response;
    }

    public long getId() {
        // TODO Auto-generated method stub
        return id;
        // return null;
    }
}
