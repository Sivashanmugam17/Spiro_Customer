package com.mauto.chd.adaptersofchd


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.mauto.chd.R
import com.mauto.chd.view_model_tracking_page.CancellationReasonDataModel
import com.mauto.chd.clickableInterface.cancelreasonclick


class cancelreasonadapter(val context: Context, val listModelArrayList: ArrayList<CancellationReasonDataModel>, internal var mCustomOnClickListener: cancelreasonclick) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View?
        val vh: ViewHolderd1
        if (convertView == null)
        {
            val layoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.cancelreasonadapterlayout, parent, false)
            vh = ViewHolderd1(view)
            view.tag = vh
        }
        else
        {
            view = convertView
            vh = view.tag as ViewHolderd1
        }
        vh.tvTitle.text = listModelArrayList[position].reason
        vh.tvTitle.setOnClickListener { view -> listModelArrayList[position].id?.let { listModelArrayList[position].reason?.let { it1 -> mCustomOnClickListener.onItemClickListener(view, it, it1) } } }
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
private class ViewHolderd1(view: View?)
{
    val tvTitle: TextView = view?.findViewById<TextView>(R.id.title) as TextView


}