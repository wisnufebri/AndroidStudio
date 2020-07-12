package com.belajar.loginapps.model;

public class Book {
    private int id;
    private String judul;
    private String penerbit;
    private String penulis;

    public Book() {
    }

    public Book(String judul, String penulis, String penerbit) {
        this.judul = judul;
        this.penerbit = penerbit;
        this.penulis = penulis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
