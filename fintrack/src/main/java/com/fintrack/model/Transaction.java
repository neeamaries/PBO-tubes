package com.fintrack.model;

public class Transaction {
    private String nama;
    private double nominal;
    private String tipe; // "Pemasukan" atau "Pengeluaran"

    // Constructor
    public Transaction(String nama, double nominal, String tipe) {
        this.nama = nama;
        this.nominal = nominal;
        this.tipe = tipe;
    }

    // Getter dan Setter (Wajib ada agar bisa dibaca oleh JSP)
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public double getNominal() { return nominal; }
    public void setNominal(double nominal) { this.nominal = nominal; }

    public String getTipe() { return tipe; }
    public void setTipe(String tipe) { this.tipe = tipe; }
}