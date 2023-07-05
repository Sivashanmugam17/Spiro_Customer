package com.mauto.chd.adaptersofchd

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mauto.chd.Modal.ServiceLocationModel
import com.mauto.chd.R
import com.mauto.chd.clickableInterface.CountryCustomOnClickListener

class SLsectionedadapter(val context : Context, var countrymodedata: ArrayList<ServiceLocationModel>, internal var mCustomOnClickListener: CountryCustomOnClickListener) : RecyclerView.Adapter<SLsectionedadapter.ViewHolder>()
{
    override fun onBindViewHolder(p0: ViewHolder, p1: Int)
    {

        p0?.countryname?.text = countrymodedata[p1].slname

        // view clickable
        p0?.countryname?.setOnClickListener { view -> mCustomOnClickListener.onItemClickListener(view, p0.adapterPosition , countrymodedata[p1].slcode.toString(), countrymodedata[p1].slcode.toString(), countrymodedata[p1].slname.toString()) }

    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder
    {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.sladapter, p0, false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int
    {
        return countrymodedata.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val countryname = itemView.findViewById<TextView>(R.id.countryname)

    }
    fun filterList(filteredCourseList: ArrayList<ServiceLocationModel>)
    {
        this.countrymodedata = filteredCourseList;
        notifyDataSetChanged();
    }
}