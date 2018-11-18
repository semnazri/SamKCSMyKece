package kcs.com.logicart.Adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kcs.com.logicart.POJO.DaftarHarga
import kcs.com.logicart.R
import kcs.com.logicart.ViewHolder.DaftarHargaViewHolder

class DaftarHargaAdapter(val mValues: List<DaftarHarga>, val mContext: Context) : RecyclerView.Adapter<DaftarHargaViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaftarHargaViewHolder {


        return DaftarHargaViewHolder(LayoutInflater.from(mContext).inflate(R.layout.card_harga, parent, false))
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    override fun onBindViewHolder(holder: DaftarHargaViewHolder, position: Int) {

        if (mValues[position].fiturNama!!.toLowerCase().contains("smartfren")) {
            holder?.llparent.setBackgroundResource(R.drawable.smartfren_background)
        } else if (mValues[position].fiturNama!!.toLowerCase().contains("three")) {
            holder?.llparent.setBackgroundResource(R.drawable.three_background)

        } else if (mValues[position].fiturNama!!.toLowerCase().contains("axis")) {
            holder?.llparent.setBackgroundResource(R.drawable.axis_background)

        } else if (mValues[position].fiturNama!!.toLowerCase().contains("xl")) {
            holder?.llparent.setBackgroundResource(R.drawable.xl_background)
//            holder?.title.setTextColor(Color.parseColor("#000000"))

        } else if (mValues[position].fiturNama!!.toLowerCase().contains("telkomsel") || mValues[position].fiturNama!!.toLowerCase().contains("as") || mValues[position].fiturNama!!.toLowerCase().contains("simpati")) {
            holder?.llparent.setBackgroundResource(R.drawable.telkomsel_background)

        } else if (mValues[position].fiturNama!!.toLowerCase().contains("indosat") || mValues[position].fiturNama!!.toLowerCase().contains("im3")) {
            holder?.llparent.setBackgroundResource(R.drawable.indosat_background)

        } else {
            holder?.llparent.setBackgroundResource(R.drawable.kcs_background)

        }

        holder?.title?.text = mValues[position].fiturNama
        holder?.harga?.text = "Rp. " + mValues[position].harga.toString()
    }
}