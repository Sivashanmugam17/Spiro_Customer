package com.mauto.chd.adaptersofchd

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.mauto.chd.Modal.ridelistsummaryModel
import com.mauto.chd.R


class ridelistsummaryModelAdapter(val context: Context, val listModelArrayList: ArrayList<ridelistsummaryModel>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View?
        val vh: ViewHolder1s
        if (convertView == null)
        {
            val layoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.farelistadapter, parent, false)
            vh = ViewHolder1s(view)
            view!!.tag = vh
        }
        else
        {
            view = convertView
            vh = view.tag as ViewHolder1s
        }
        vh.tvTitle.text = listModelArrayList[position].title
        vh.tvTitle.setTextColor(context.getResources().getColor(R.color.colordarkhint));
        var value:String=""+listModelArrayList[position].content;
        if(value.startsWith("-"))
        {
            vh.tvContent.text = listModelArrayList[position].content
            vh.tvContent.setTextColor(context.getResources().getColor(R.color.colorred));
        }
        else
        {
            vh.tvContent.text = listModelArrayList[position].content
            vh.tvContent.setTextColor(context.getResources().getColor(R.color.colordarkhint));
        }
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
private class ViewHolder1s(view: View?)
{
    val tvTitle: TextView = view?.findViewById<TextView>(R.id.faretile) as TextView
    val tvContent: TextView = view?.findViewById<TextView>(R.id.farevalue) as TextView
}