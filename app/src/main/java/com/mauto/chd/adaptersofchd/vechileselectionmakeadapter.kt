package com.mauto.chd.adaptersofchd

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mauto.chd.Modal.VehicleselectionModels
import com.mauto.chd.R
import com.mauto.chd.clickableInterface.CustomOnClickListener

class vechileselectionmakeadapter(val context : Context, var countrymodedata: ArrayList<VehicleselectionModels>, internal var mCustomOnClickListener: CustomOnClickListener) : RecyclerView.Adapter<vechileselectionmakeadapter.ViewHolder>()
{
    override fun onBindViewHolder(p0: ViewHolder, p1: Int)
    {
        p0?.name?.text = countrymodedata[p1].makename
        p0?.name?.setOnClickListener { view ->
            mCustomOnClickListener.onItemClickListener(view, p0.adapterPosition , countrymodedata[p1].makeid, countrymodedata[p1].makename)
        }
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder
    {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.vehicleselectionadapter, p0, false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int
    {
        return countrymodedata.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val name = itemView.findViewById<TextView>(R.id.name)

    }
    fun filterList(filteredCourseList: ArrayList<VehicleselectionModels>)
    {
        this.countrymodedata = filteredCourseList;
        notifyDataSetChanged();
    }
}