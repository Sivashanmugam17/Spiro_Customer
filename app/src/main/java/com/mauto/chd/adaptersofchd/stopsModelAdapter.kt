package com.mauto.chd.adaptersofchd


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.mauto.chd.R
import com.mauto.chd.view_model_tracking_page.stopsModel


class stopsModelAdapter(val context: Context, val listModelArrayList: ArrayList<stopsModel>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View?
        val vh: ViewHolder1
        if (convertView == null)
        {
            val layoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.farelistadapterkotlin, parent, false)
            vh = ViewHolder1(view)
            view!!.tag = vh
        }
        else
        {
            view = convertView
            vh = view.tag as ViewHolder1
        }
        vh.tvTitle.text = listModelArrayList[position].title
        if (position == 0) {
            vh.colorchange.setImageResource(R.drawable.passenger_x_last_mile_map_components_high_availability_city_level_station_marker)
        } else if (position == (listModelArrayList.size - 1)) {
            vh.colorchange.setImageResource(R.drawable.passenger_x_last_mile_map_components_low_availability_city_level_station_marker)
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
private class ViewHolder1(view: View?)
{
    val tvTitle: TextView = view?.findViewById<TextView>(R.id.title) as TextView
    val colorchange: ImageView= view?.findViewById<ImageView>(R.id.colorchange) as ImageView


}