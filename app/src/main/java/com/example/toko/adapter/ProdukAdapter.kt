package com.example.toko.adapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.toko.LoginActivity
import com.example.toko.R
import com.example.toko.api.BaseRetrofit
import com.example.toko.response.produk.Produk
import com.example.toko.response.produk.ProdukResponsePost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProdukAdapter(private val listProduk: List<Produk>):RecyclerView.Adapter<ProdukAdapter.ViewHolder>() {

    private  val api by lazy { BaseRetrofit().endpoint }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_produk, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produk = listProduk[position]
        holder.txtNamaProduk.text = produk.nama
        holder.txtHarga.text = produk.harga
        holder.txtStok.text = "Sisa Stok : "+produk.stok


        LoginActivity.sessionManager.getString("TOKEN")
        val token= LoginActivity.sessionManager

        holder.btnDelete.setOnClickListener {
//            Toast.makeText(holder.itemView.context, produk.nama.toString(), Toast.LENGTH_LONG)
//                .show()

            api.deleteProduk(token.toString(), produk.id.toInt()).enqueue(object :
                Callback<ProdukResponsePost> {
                override fun onResponse(
                    call: Call<ProdukResponsePost>,
                    response: Response<ProdukResponsePost>
                ) {
                    Log.d("Sucess", response.toString())
                    Toast.makeText(
                        holder.itemView?.context,
                        "Data Berhasil diHapus",
                        Toast.LENGTH_SHORT
                    ).show()

                    holder.itemView.findNavController().navigate(R.id.produkFragment)
                }

                override fun onFailure(call: Call<ProdukResponsePost>, t: Throwable) {
                    Log.e(" Error", t.toString())
                }

            })

        }
        holder.btnEdit.setOnClickListener {
            Toast.makeText(holder.itemView.context, produk.nama.toString(), Toast.LENGTH_LONG)
                .show()
            val bundle= Bundle()
            bundle.putParcelable("produk",produk)
            bundle.putString("status","edit")


                    holder.itemView.findNavController().navigate(R.id.produkFormFragment,bundle)
                }


        }




    override fun getItemCount(): Int {
        return listProduk.size
    }

    class ViewHolder(itemViem: View) : RecyclerView.ViewHolder(itemViem) {
        val txtNamaProduk = itemViem.findViewById(R.id.txtNamaProduk) as TextView
        val txtHarga = itemViem.findViewById(R.id.txtHarga) as TextView
        val txtStok = itemViem.findViewById(R.id.txtStok) as TextView
        val btnDelete = itemViem.findViewById(R.id.btnDelete) as ImageButton
        val btnEdit = itemViem.findViewById(R.id.btnEdit) as ImageButton
    }
}