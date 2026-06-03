package com.tubes.model;
public class Anggaran {
    private String bulan;
    private double totalPemasukan;
    private double totalPengeluaran;

    public Anggaran(String bulan, double totalPemasukan, double totalPengeluaran) {
        this.bulan = bulan;
        this.totalPemasukan = totalPemasukan;
        this.totalPengeluaran = totalPengeluaran;
    }

    public String getBulan() { return bulan; }
    public double getTotalPemasukan() { return totalPemasukan; }
    public double getTotalPengeluaran() { return totalPengeluaran; }
    
    // Hitung sisa saldo
    public double getSaldo() {
        return totalPemasukan - totalPengeluaran;
    }
}
