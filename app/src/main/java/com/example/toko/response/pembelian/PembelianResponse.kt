package com.example.toko.response.pembelian

data class PembelianResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
)