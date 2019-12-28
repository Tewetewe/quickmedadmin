package com.example.adminquickmed.data;


public class DataProfile {
    private String user_id, nama, alamat, email, goldar, username, password;

    public DataProfile() {
    }

    public DataProfile(String user_id, String nama, String alamat, String email, String goldar, String username, String password) {
        this.user_id = user_id;
        this.nama = nama;
        this.alamat = alamat;
        this.email = email;
        this.goldar = goldar;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return user_id;
    }

    public void setId(String user_id) {
        this.user_id = user_id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGoldar() {
        return goldar;
    }

    public void setGoldar(String goldar) {
        this.goldar = goldar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
