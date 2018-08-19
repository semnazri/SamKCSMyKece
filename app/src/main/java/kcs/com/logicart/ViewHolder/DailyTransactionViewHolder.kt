package kcs.com.logicart.ViewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import kcs.com.logicart.R

class DailyTransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val denom: TextView = itemView.findViewById(R.id.denom)
    val hargadenom: TextView = itemView.findViewById(R.id.harga_denom)
    val nohape: TextView = itemView.findViewById(R.id.no_hape)
    val trans_date: TextView = itemView.findViewById(R.id.trans_date)
    val status: TextView = itemView.findViewById(R.id.status)
    val fitur_n:TextView = itemView.findViewById(R.id.fitur_nama)

}