package com.example.toko.response.transaksi

data class TransaksiResponsePost(
    val `data`: TransaksiData,
    val message: String,
    val success: Boolean
)
data class TransaksiData(
    val `transaksi`: Transaksi,
)