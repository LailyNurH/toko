package com.example.toko.response.cart

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Beli(
    var id: Int,
    var nama:String,
    var qty:Int,
    var idprod:Int
):Parcelable

