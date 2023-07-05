//package com.cabilyhandyforalldinedoo.chd.adaptersofchd
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import cabily.handyforall.dinedoo.R
//import com.cabilyhandyforalldinedoo.chd.Modal.VehicleselectionModel
//import com.cabilyhandyforalldinedoo.chd.ViewModelWIthRepositaryMain.EarningModel
//import com.cabilyhandyforalldinedoo.chd.clickableInterface.RecyclerViewClickListener
//
//
//class EarningsAdapter(val context : Context, private val earning: List<EarningModel>, private val listener: RecyclerViewClickListener) : RecyclerView.Adapter<EarningsAdapter.ViewHolder>()
//{
//    override fun onBindViewHolder(holder: ViewHolder, position: Int)
//    {
//        holder?.amount?.text = earning[position].amount
//        holder?.currencysymboll?.text = earning[position].symbol
//
//        holder?.lastat?.text = earning[position].lastat
//        holder?.categories?.text = earning[position].categories
//
//        if(position == 0)
//            holder?.triptype?.text =context.getString(R.string.today_trips)
//        else if(position == 1)
//            holder?.triptype?.text =context.getString(R.string.lastrip)
//        else if(position == 2)
//            holder?.triptype?.text=context.getString(R.string.weekrip)
//
//
//    }
//
//    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder
//    {
//        val v = LayoutInflater.from(p0?.context).inflate(R.layout.earningsadapter, p0, false)
//        return ViewHolder(v)
//    }
//    override fun getItemCount(): Int
//    {
//        return earning.size
//    }
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
//    {
//        val amount = itemView.findViewById<TextView>(R.id.amount)
//        val currencysymboll = itemView.findViewById<TextView>(R.id.currencysymboll)
//        val triptype = itemView.findViewById<TextView>(R.id.triptype)
//        val lastat = itemView.findViewById<TextView>(R.id.lastat)
//        val categories = itemView.findViewById<TextView>(R.id.categories)
//
//
//    }
//}