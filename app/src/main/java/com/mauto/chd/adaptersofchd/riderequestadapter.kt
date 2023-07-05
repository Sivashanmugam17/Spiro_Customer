//package com.cabilyhandyforalldinedoo.chd.adaptersofchd
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import cabily.handyforall.dinedoo.R
//import com.cabilyhandyforalldinedoo.chd.ViewModelRideRequest.RideRequestDataModel
//import com.cabilyhandyforalldinedoo.chd.clickableInterface.CustomOnClickListener
//
//class riderequestadapter(val context : Context, var countrymodedata: ArrayList<RideRequestDataModel>, internal var mCustomOnClickListener: CustomOnClickListener) : RecyclerView.Adapter<riderequestadapter.ViewHolder>()
//{
//    override fun onBindViewHolder(p0: ViewHolder, p1: Int)
//    {
//        p0?.category?.text = countrymodedata[p1].category
//        p0?.rating?.text = countrymodedata[p1].rating
//        p0?.min?.text = countrymodedata[p1].min
//        p0?.miles?.text = countrymodedata[p1].miles
//    }
//
//    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder
//    {
//        val v = LayoutInflater.from(p0?.context).inflate(R.layout.driverideaccpted, p0, false)
//        return ViewHolder(v)
//    }
//    override fun getItemCount(): Int
//    {
//        return countrymodedata.size
//    }
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
//    {
//        val category = itemView.findViewById<TextView>(R.id.category)
//        val rating = itemView.findViewById<TextView>(R.id.rating)
//        val min = itemView.findViewById<TextView>(R.id.min)
//        val miles = itemView.findViewById<TextView>(R.id.miles)
//    }
//
//    fun filterList(filteredCourseList: ArrayList<RideRequestDataModel>)
//    {
//        this.countrymodedata = filteredCourseList;
//        notifyDataSetChanged();
//    }
//}