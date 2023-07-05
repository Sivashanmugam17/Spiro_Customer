package com.mauto.chd.adaptersofchd

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mauto.chd.Modal.CountryselectionModel
import com.mauto.chd.R
import com.mauto.chd.clickableInterface.CountryCustomOnClickListener

class countrysectionedadapter(val context : Context, var countrymodedata: ArrayList<CountryselectionModel>, internal var mCustomOnClickListener: CountryCustomOnClickListener) : RecyclerView.Adapter<countrysectionedadapter.ViewHolder>()
{
    override fun onBindViewHolder(p0: ViewHolder, p1: Int)
    {
        p0?.countrycode?.text = "(+"+countrymodedata[p1].countrycode+")"
        p0?.countryname?.text = countrymodedata[p1].countryname
        p0?.flagimage?.setImageBitmap(countrymodedata[p1].flag)


        // view clickable
        p0?.countrycode?.setOnClickListener { view -> mCustomOnClickListener.onItemClickListener(view, p0.adapterPosition , countrymodedata[p1].countrycode.toString(), countrymodedata[p1].countryshortname.toString(), countrymodedata[p1].countryname.toString()) }
        p0?.countryname?.setOnClickListener { view -> mCustomOnClickListener.onItemClickListener(view, p0.adapterPosition , countrymodedata[p1].countrycode.toString(), countrymodedata[p1].countryshortname.toString(), countrymodedata[p1].countryname.toString()) }
        p0?.flagimage?.setOnClickListener { view -> mCustomOnClickListener.onItemClickListener(view, p0.adapterPosition , countrymodedata[p1].countrycode.toString(), countrymodedata[p1].countryshortname.toString(), countrymodedata[p1].countryname.toString()) }
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder
    {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.countryselectionadapter, p0, false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int
    {
        return countrymodedata.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val countryname = itemView.findViewById<TextView>(R.id.countryname)
        val countrycode = itemView.findViewById<TextView>(R.id.countrycode)
        val flagimage = itemView.findViewById<ImageView>(R.id.flagimage)
    }
    fun filterList(filteredCourseList: ArrayList<CountryselectionModel>)
    {
        this.countrymodedata = filteredCourseList;
        notifyDataSetChanged();
    }
}