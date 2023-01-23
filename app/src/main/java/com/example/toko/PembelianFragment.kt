package com.example.toko

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toko.adapter.PembelianAdapter
import com.example.toko.adapter.ProdukAdapter
import com.example.toko.adapter.TransaksiAdapter
import com.example.toko.api.BaseRetrofit
import com.example.toko.response.cart.Beli
import com.example.toko.response.cart.Cart
import com.example.toko.response.itempembelian.ItemPembelian
import com.example.toko.response.itempembelian.ItemPembelianResponse
import com.example.toko.response.pembelian.PembelianResponse
import com.example.toko.response.produk.ProdukResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class PembelianFragment : Fragment() {
    private val api by lazy { BaseRetrofit().endpoint }
    private lateinit var buy : ArrayList<Beli>
    private lateinit  var name: String
    private lateinit  var quantity: String

    private  lateinit var idproduk: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_pembelian, container, false)
        val btnBeli = view.findViewById<Button>(R.id.btnBeli)
        btnBeli.setOnClickListener {
            val bundle=Bundle()
            bundle.putParcelableArrayList("BELI",buy)
            bundle.putString("NAMAPRODUK",name)
            bundle.putString("idprod",idproduk)
            bundle.putString("QUANTITY",quantity)

            findNavController().navigate(R.id.beliFormFragment,bundle)
        }
        getProduk(view)
        return view;
    }
    fun getProduk(view: View) {
        val token = LoginActivity.sessionManager.getString("TOKEN")
        api.getProduk(token.toString()).enqueue(object : Callback<ProdukResponse> {
            override fun onResponse(
                call: Call<ProdukResponse>,
                response: Response<ProdukResponse>
            ) {
                Log.d("ProdukData", response.body().toString())

                val rv = view.findViewById(R.id.rv_itempembelian) as RecyclerView


                rv.setHasFixedSize(true)
                rv.layoutManager = LinearLayoutManager(activity)
                val rvAdapter = PembelianAdapter(response.body()!!.data.produk)
                rv.adapter = rvAdapter

                rvAdapter.callbackInterface = object : CallInterface{
                    override fun passResCallback(idprod:String,nama:String,qty: String,beli: ArrayList<Beli>) {

                        val jumlah = activity?.findViewById<TextView>(R.id.txtJum)
                        jumlah?.setText(qty.toString())
                        name= nama
                        quantity= qty
                        buy = beli
                        idproduk=idprod

//                        Log.d("Pembelian", qty.toString())

                        Log.d("Pembelian", beli.toString())
                    }



                }


            }

            override fun onFailure(call: Call<ProdukResponse>, t: Throwable) {
                Log.e("ProdukError", t.toString())
            }
        })
    }

}


