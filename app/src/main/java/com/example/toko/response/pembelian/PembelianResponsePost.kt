package com.example.toko.response.pembelian


data class PembelianResponsePost(
    val `data`: DataPembelian,
    val message: String,
    val success: Boolean
)
data class DataPembelian(
    val `pembelian` : Pembelian
)