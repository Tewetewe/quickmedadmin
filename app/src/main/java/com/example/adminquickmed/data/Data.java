package com.example.adminquickmed.data;

public class Data {
    private String id, nama, tgl_lahir, alamat, email, username, password, goldar;

    public Data() {
    }

    public Data(String id, String nama, String alamat, String tgl_lahir, String email, String username, String password, String goldar) {
        this.id = id;
        this.nama = nama;
        this.tgl_lahir = tgl_lahir;
        this.alamat = alamat;
        this.email = email;
        this.username = username;
        this.password = password;
        this.goldar = goldar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getUsername(){
        return username;
    }
    public void  setUsername(String username){
        this.username = username;
    }
    public  String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getGoldar(){
        return goldar;
    }
    public void setGoldar(String goldar){
        this.goldar = goldar;
    }
}

