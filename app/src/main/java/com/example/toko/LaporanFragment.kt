package com.example.toko

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toko.adapter.LaporanAdapter
import com.example.toko.adapter.ProdukAdapter
import com.example.toko.adapter.TransaksiAdapter
import com.example.toko.api.BaseRetrofit
import com.example.toko.response.produk.ProdukResponse
import com.example.toko.response.transaksi.TransaksiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*


class LaporanFragment : Fragment() {
    private val api by lazy { BaseRetrofit().endpoint }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view= inflater.inflate(R.layout.fragment_laporan, container, false)
        getLaporan(view)
        return  view
    }

    //memanggil produk dari api
    fun getLaporan(view: View) {
        val token = LoginActivity.sessionManager.getString("TOKEN")
        api.getLaporan(token.toString()).enqueue(object : Callback<TransaksiResponse> {
            override fun onResponse(
                call: Call<TransaksiResponse>,
                response: Response<TransaksiResponse>
            ) {
                Log.d("TransaksiResponseSucces", response.body().toString())


                val rv = view.findViewById(R.id.rv_laporan) as RecyclerView

                val txtTotalPendapatan = view.findViewById(R.id.txtTotalPendapatan) as TextView
                val totalPendapatan = response.body()!!.data.total

                val localeId = Locale("in","ID")
                val numberFormat = NumberFormat.getCurrencyInstance(localeId)

                txtTotalPendapatan?.setText(numberFormat.format(totalPendapatan.toString().toDouble()).toString())

                rv.setHasFixedSize(true)
                rv.layoutManager = LinearLayoutManager(activity)
                val rvAdapter = LaporanAdapter(response.body()!!.data.transaksi)
                rv.adapter = rvAdapter
            }



            override fun onFailure(call: Call<TransaksiResponse>, t: Throwable) {
                Log.e("TransaksiResponseError", t.toString())
            }
        })
    }

}