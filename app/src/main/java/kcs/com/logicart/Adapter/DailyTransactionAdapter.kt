package kcs.com.logicart.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import kcs.com.logicart.POJO.DaftarTransaksi
import kcs.com.logicart.R
import kcs.com.logicart.ViewHolder.DailyTransactionViewHolder

class DailyTransactionAdapter(val mValues: List<DaftarTransaksi>, val mContext: Context) : RecyclerView.Adapter<DailyTransactionViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyTransactionViewHolder {

        return DailyTransactionViewHolder(LayoutInflater.from(mContext).inflate(R.layout.card_transaksi_harian, parent, false))
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    override fun onBindViewHolder(holder: DailyTransactionViewHolder, position: Int) {
        holder?.denom?.text = "Denom : " + mValues.get(position).denomial.toString()
        holder?.hargadenom?.text = "Harga : Rp. " + mValues.get(position).harga.toString()
        holder?.nohape?.text = "No. Tujuan : " + mValues.get(position).noHp!!.replace("00", "0")
        holder?.trans_date?.text = "Trans Date : " + mValues.get(position).transDate
        holder?.fitur_n?.text = "Operator : " + mValues.get(position).fiturNama


//        holder?.status?.text = mValues.get(position).errorKode

        if (mValues.get(position).errorKode!!.equals("00")) {
            holder?.status?.text = "SUKSES"
        } else {
            holder?.status?.text = Html.fromHtml("GAGAL,<br>Error code :" +mValues.get(position).errorKode)
        }

    }

}
