package com.example.toko

import com.example.toko.response.cart.Beli
import com.example.toko.response.cart.Cart

interface CallbackInterface {
    fun passResultCallback(total: String,cart: ArrayList<Cart>)

}