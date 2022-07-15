package com.example.todolist;

public class ModelData {
    int id;
    private String nama_barang;
    private Integer harga_beli;
    private String satuan;
    private Integer harga_jual;

    ModelData(int id, String nama_barang, Integer harga_beli, String satuan, Integer harga_jual) {
        this.id = id;
        this.nama_barang = nama_barang;
        this.harga_beli = harga_beli;
        this.satuan = satuan;
        this.harga_jual = harga_jual;
    }

    int getId() {
        return id;
    }

    String getNamaBarang() {
        return nama_barang;
    }

    Integer getHargaBeli() {
        return harga_beli;
    }

    String getSatuan() {
        return satuan;
    }

    Integer getHargaJual() {
        return harga_jual;
    }
}
