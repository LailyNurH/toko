package com.example.toko

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.example.toko.api.BaseRetrofit
import com.example.toko.response.cart.Beli
import com.example.toko.response.itempembelian.ItemPembelianResponsePost
import com.example.toko.response.pembelian.PembelianResponsePost
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BeliFormFragment : Fragment() {


    private val api by lazy { BaseRetrofit().endpoint }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_beli_form, container, false)


        val idprod= arguments?.getString("idprod")
        val name = arguments?.getString("NAMAPRODUK")
        val quantity = arguments?.getString("QUANTITY")
        val beli = arguments?.getParcelableArrayList<Beli>("BELI")


        val txtNama = view.findViewById<TextView>(R.id.nama)
        txtNama.setText(name.toString())
        val txtQuan = view.findViewById<TextView>(R.id.banyak)
        Log.d("api",beli.toString())
        txtQuan.setText(quantity.toString())
        Log.d("tested",name.toString())
        val total = quantity
        Log.d("tested",total.toString())



        val token=LoginActivity.sessionManager.getString("TOKEN")
        val adminId= LoginActivity.sessionManager.getString("ADMIN_ID")
        Log.d("a",adminId.toString())

        val btnBeli = view.findViewById<Button>(R.id.btnBeli)
        btnBeli.setOnClickListener {
            val txtSup = view.findViewById<TextInputEditText>(R.id.txtInSupplier)
            val idadmin= adminId
            api.postPembelian(token.toString(),idprod.toString().toInt(),total.toString().toInt(),txtSup.text.toString(),).enqueue(object :
                Callback<PembelianResponsePost> {
                override fun onResponse(
                    call: Call<PembelianResponsePost>,
                    response: Response<PembelianResponsePost>
                ) {
                    val id_pembelian = response.body()!!.data.pembelian.id
                    Log.e("id_pembelian",id_pembelian)
                    for(item in beli!!){
                        api.postItemPembelian(token.toString(),id_pembelian.toString().toInt(),item.id,item.qty).enqueue(
                            object: Callback<ItemPembelianResponsePost>{
                                override fun onResponse(
                                    call: Call<ItemPembelianResponsePost>,
                                    response: Response<ItemPembelianResponsePost>
                                ) {

                                }

                                override fun onFailure(
                                    call: Call<ItemPembelianResponsePost>,
                                    t: Throwable
                                ) {
                                    Log.e(" Error",t.toString())
                                }

                            })

                        Toast.makeText(view.context, "Berhasil  di Beli", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.pembelianFragment)

                    }

                }

                override fun onFailure(call: Call<PembelianResponsePost>, t: Throwable) {
                    Log.e(" Error",t.toString())
                }

            })

        }

        return view
    }




}