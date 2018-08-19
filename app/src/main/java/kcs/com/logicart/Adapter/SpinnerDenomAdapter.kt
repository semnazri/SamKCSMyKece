package kcs.com.logicart.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SpinnerAdapter
import android.widget.TextView

import kcs.com.logicart.JSON.Denom
import kcs.com.logicart.R

class SpinnerDenomAdapter(private val mContext: Context, private val mValues: List<Denom>) : BaseAdapter(), SpinnerAdapter {

    override fun getCount(): Int {
        return mValues.size
    }

    override fun getItem(position: Int): Any {
        return mValues[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val inflater = mContext.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.item_spinner_denom, parent, false)
        }

        val denom = convertView!!.findViewById<TextView>(R.id.item_name)
        val denominal = convertView.findViewById<TextView>(R.id.id_item)

        denom.text = mValues[position].fixed
        denominal.text = mValues[position].denominal


        return convertView
    }
}
