package com.belajar.loginapps.apihelper;

import com.belajar.loginapps.model.Record;

public class ApiResponse {
    int id, userid, harga;
    String judul, penerbit, penulis, tahun, thumb, token;
    boolean success;
    Record record;

    public ApiResponse() {
    }

    public ApiResponse(int id, int userid, int harga, String judul, String penerbit, String penulis, String tahun, String thumb, String token, boolean success, Record record) {
        this.id = id;
        this.userid = userid;
        this.harga = harga;
        this.judul = judul;
        this.penerbit = penerbit;
        this.penulis = penulis;
        this.tahun = tahun;
        this.thumb = thumb;
        this.token = token;
        this.success = success;
        this.record = record;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }
}
