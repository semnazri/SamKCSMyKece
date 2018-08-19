package kcs.com.logicart.ViewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

import kcs.com.logicart.R

class DaftarHargaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val title: TextView = itemView.findViewById(R.id.fitur_nama)
    val harga: TextView = itemView.findViewById(R.id.denominal)
    val llparent: LinearLayout = itemView.findViewById(R.id.llparent)

}
