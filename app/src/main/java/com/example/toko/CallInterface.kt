package com.example.toko

import com.example.toko.response.cart.Beli

interface CallInterface {
    fun passResCallback(idprod:String ,nama:String,qty: String,beli: ArrayList<Beli>)

}