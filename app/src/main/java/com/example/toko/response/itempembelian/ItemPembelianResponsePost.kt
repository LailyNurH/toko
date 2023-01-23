package com.example.toko.response.itempembelian

data class ItemPembelianResponsePost(
    val `data`: Data,
    val message: String,
    val success: Boolean
)

data class DataPembelian(
    val `itemtransaksi` : ItemPembelian
)
