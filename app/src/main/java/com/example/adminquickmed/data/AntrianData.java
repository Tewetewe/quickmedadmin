package com.example.adminquickmed.data;

import java.util.ArrayList;

public class AntrianData {
    private String pendaftaran_id;
    private String user_id;
    private String nama;
    private String created_at;
    private String keluhan;
    private String photo;
    private String nama_faskes;
    private String status;

    public AntrianData() {
    }

    public AntrianData(String pendaftaran_id, String user_id, String nama, String created_at, String keluhan, String photo, String nama_faskes, String status) {
        this.pendaftaran_id = pendaftaran_id;
        this.user_id = user_id;
        this.nama = nama;
        this.created_at = created_at;
        this.photo = photo;
        this.keluhan = keluhan;
        this.nama_faskes = nama_faskes;
        this.status = status;
    }

    public String getPendaftaran_id() {
        return pendaftaran_id;
    }

    public void setPendaftaran_id(String pendaftaran_id) {
        this.pendaftaran_id = pendaftaran_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }
    public String getFaskes() {
        return nama_faskes;
    }

    public void setFaskes(String nama_faskes) {
        this.nama_faskes = nama_faskes;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

