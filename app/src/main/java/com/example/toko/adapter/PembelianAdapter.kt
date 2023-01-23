package com.example.toko.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.toko.CallInterface
import com.example.toko.CallbackInterface
import com.example.toko.LoginActivity
import com.example.toko.R
import com.example.toko.response.cart.Beli
import com.example.toko.response.cart.Cart
import com.example.toko.response.itempembelian.ItemPembelian
import com.example.toko.response.pembelian.Pembelian
import com.example.toko.response.produk.Produk


class PembelianAdapter( val listPembelian: List<Produk>): RecyclerView.Adapter<PembelianAdapter.ViewHolder>() {
    var callbackInterface : CallInterface? = null
    lateinit var NAMOSE:String
    lateinit var idproduk:String
    var qty:Int = 0
    var beli : ArrayList<Beli> = arrayListOf<Beli>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_beli, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val prod = listPembelian[position]

        holder.txtBarang?.text = prod.nama
        NAMOSE= prod.nama
        idproduk= prod.id

        holder.btnPlus?.setOnClickListener {
            val old_value = holder.txtQty?.text.toString().toInt()
            val new_value = old_value + 1

            holder.txtQty?.setText(new_value.toString())

            qty = qty + 1
            val index = beli.indexOfFirst { it.id==prod.id.toInt() }.toInt()

            if(index !=-1){
                beli.removeAt(index)
            }

            val itemBeli = Beli(prod.id.toInt(), prod.nama, new_value,idproduk.toInt())
            beli.add(itemBeli)

            callbackInterface?.passResCallback(prod.id,prod.nama,qty.toString(),beli)


        }
        holder.btnMinus?.setOnClickListener {
            val old_value = holder.txtQty?.text.toString().toInt()
            val new_value = old_value - 1
            val index = beli.indexOfFirst { it.id == prod.id.toInt() }.toInt()
            if (index != -1) {
                beli.removeAt(index)
            }
            if (new_value >= 0) {
                holder.txtQty?.setText(new_value.toString())

                qty = qty - 1


            }
            if (new_value >= 1) {
                val itemBeli = Beli(prod.id.toInt(),prod.nama, new_value,idproduk.toInt())
                beli.add(itemBeli)
            }

            callbackInterface?.passResCallback(prod.id,prod.nama,qty.toString() ,beli)

        }

    }



    override fun getItemCount(): Int {
        return listPembelian.size
    }

    class ViewHolder(itemViem: View) : RecyclerView.ViewHolder(itemViem) {
        val txtBarang = itemViem.findViewById(R.id.txtBeliProduk) as? TextView

        val txtQty = itemViem.findViewById(R.id.beliQty) as? TextView
        val btnPlus = itemViem.findViewById(R.id.btnPls) as? ImageButton
        val btnMinus = itemViem.findViewById(R.id.btnMin) as? ImageButton
    }
}