package com.example.toko.response.transaksi

data class TransaksiResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
)