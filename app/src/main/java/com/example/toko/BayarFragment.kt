package com.example.toko

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.toko.api.BaseRetrofit
import com.example.toko.response.cart.Cart
import com.example.toko.response.itemtransaksi.ItemTransaksiResponesPost
import com.example.toko.response.produk.ProdukResponsePost
import com.example.toko.response.transaksi.TransaksiResponsePost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*


class BayarFragment : Fragment() {
    private val api by lazy { BaseRetrofit().endpoint }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view= inflater.inflate(R.layout.fragment_bayar, container, false)

        val total = arguments?.getString("TOTAL")
        val my_cart = arguments?.getParcelableArrayList<Cart>("MY_CART")

        val localeId = Locale("in","ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeId)

        val txtKembalian = view.findViewById<TextView>(R.id.txtKembalian)
        val txtTotalTransaksiBayar = view.findViewById<TextView>(R.id.txtTotalTransaksiBayar)
        txtTotalTransaksiBayar?.setText(numberFormat.format(total.toString().toDouble()).toString())
        Log.d("test", my_cart.toString())


        val txtPenerimaan = view.findViewById<EditText>(R.id.txtPenerimaan)
        txtPenerimaan.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                var kembalian : Int = 0

                if(txtPenerimaan.text.toString() != ""){
                    val penerimaan:Int = txtPenerimaan.text.toString().toInt()
                    kembalian = penerimaan - total.toString().toInt()

                    if(kembalian>0){
                        txtKembalian.setText(numberFormat.format(kembalian.toDouble()).toString())

                    }else{

                        txtKembalian.setText(numberFormat.format(0).toString())

                    }
                }
            }

        })
        val token=LoginActivity.sessionManager.getString("TOKEN")
        val adminId= LoginActivity.sessionManager.getString("ADMIN_ID")

        val btnSimpanBayar = view.findViewById<Button>(R.id.btnSimpanBayar)
        btnSimpanBayar.setOnClickListener {

            api.postTransaksi(token.toString(),adminId.toString().toInt(),total.toString().toInt()).enqueue(object :
                Callback<TransaksiResponsePost> {
                override fun onResponse(
                    call: Call<TransaksiResponsePost>,
                    response: Response<TransaksiResponsePost>
                ) {
                    val id_transaksi = response.body()!!.data.transaksi.id
                    for(item in my_cart!!){
                        api.postItemTransaksi(token.toString(),id_transaksi.toString().toInt(),item.id,item.qty,item.harga).enqueue(
                            object: Callback<ItemTransaksiResponesPost>{
                                override fun onResponse(
                                    call: Call<ItemTransaksiResponesPost>,
                                    response: Response<ItemTransaksiResponesPost>
                                ) {

                                }

                                override fun onFailure(
                                    call: Call<ItemTransaksiResponesPost>,
                                    t: Throwable
                                ) {
                                    Log.e(" Error",t.toString())
                                }

                            })

                        Toast.makeText(view.context, "Transaksi di Simpan", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.transaksiFragment)

                    }

                }

                override fun onFailure(call: Call<TransaksiResponsePost>, t: Throwable) {
                    Log.e(" Error",t.toString())
                }

            })

        }

        return view
    }


}