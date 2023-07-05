package com.mauto.chd.adaptersofchd


import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.mauto.chd.R
import com.mauto.chd.view_model_tracking_page.passengerpaidModel


class passengerpaidadapter(val context: Context, val listModelArrayList: ArrayList<passengerpaidModel>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View?
        val vh: ViewHolder123
        if (convertView == null)
        {
            val layoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.pasengerpaidla, parent, false)
            vh = ViewHolder123(view)
            view!!.tag = vh
        }
        else
        {
            view = convertView
            vh = view.tag as ViewHolder123
        }
        if(listModelArrayList[position].positive.equals("2"))
        {
            vh.amount.text = "-"+listModelArrayList[position].value
            vh.amount.setTextColor(Color.parseColor("#ff0000"))
        }
        else
        {
            vh.amount.text = listModelArrayList[position].value
            vh.amount.setTextColor(Color.parseColor("#000000"));
        }
        vh.description.text = listModelArrayList[position].title
        return view
    }
    override fun getItem(position: Int): Any {
        return listModelArrayList[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getCount(): Int {
        return listModelArrayList.size
    }
}
private class ViewHolder123(view: View?)
{
    val description: TextView = view?.findViewById<TextView>(R.id.description) as TextView
    val amount: TextView= view?.findViewById<TextView>(R.id.amount) as TextView
}