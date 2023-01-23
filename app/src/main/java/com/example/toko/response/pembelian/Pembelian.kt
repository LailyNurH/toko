package com.example.toko.response.pembelian

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pembelian(
    val id: String,
    val nama_admin: String,
    val nama_produk: String,
    val namasupplier: String,
    val tanggal: String,
    val stok:String

):Parcelable