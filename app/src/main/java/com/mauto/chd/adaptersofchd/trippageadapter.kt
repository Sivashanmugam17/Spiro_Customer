//package com.cabilyhandyforalldinedoo.chd.adaptersofchd
//
//
//import android.content.Context
//import androidx.recyclerview.widget.RecyclerView
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.LinearLayout
//import android.widget.TextView
//import cabily.handyforall.dinedoo.R
//import com.cabilyhandyforalldinedoo.chd.Modal.tripdetailsModel
//import com.cabilyhandyforalldinedoo.chd.clickableInterface.rideCustomOnClickListener
//
//
//class trippageadapter(val context : Context, var countrymodedata: ArrayList<tripdetailsModel>, internal var mCustomOnClickListener: rideCustomOnClickListener) : RecyclerView.Adapter<trippageadapter.ViewHolder>()
//{
//    override fun onBindViewHolder(p0: ViewHolder, p1: Int)
//    {
////        p0?.dateandtime?.text = countrymodedata[p1].pickupdate!!.replace("am", "AM").replace("pm","PM")
////        p0?.amount?.text = countrymodedata[p1].amount!!.substring(1,countrymodedata[p1].amount!!.length)
////        p0?.vehiclename?.text =countrymodedata[p1].vehicletype!!.trim()
////        p0?.paymenttype?.text = countrymodedata[p1].paymentmode
////        p0?.paymentwithmode?.text =countrymodedata[p1].status
////        p0?.rideid?.text =context.getString(R.string.rideid)+":"+countrymodedata[p1].ride_id
////
////        p0?.amountsymbol?.text = countrymodedata[p1].amount!!.substring(0,1)
////        p0?.currenysymboll?.text = countrymodedata[p1].amount!!.substring(0,1)
////
////        if(countrymodedata[p1].ride_status.equals("Cancelled"))
////        {
////            p0?.cashimagee?.visibility = View.GONE
////            p0?.cancelimagee?.visibility = View.VISIBLE
////        }
////        else
////        {
////            p0?.cancelimagee?.visibility = View.GONE
////            p0?.cashimagee?.visibility = View.VISIBLE
////        }
////
////
////        p0?.fulllayoutclick?.setOnClickListener { view -> mCustomOnClickListener.onItemClickListener(view, p0.adapterPosition ,countrymodedata[p1].ride_id!!) }
////
//
//    }
//
//    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) {
////        val v = LayoutInflater.from(p0?.context).inflate(R.layout.trippageadapter, p0, false)
////        return ViewHolder(v)
//    }
//    override fun getItemCount(): Int
//    {
//        return countrymodedata.size
//    }
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
//    {
////        val dateandtime = itemView.findViewById<TextView>(R.id.dateandtime)
////        val amount = itemView.findViewById<TextView>(R.id.amount)
////        val vehiclename = itemView.findViewById<TextView>(R.id.vehiclename)
////        val paymenttype = itemView.findViewById<TextView>(R.id.paymenttype)
////        val paymentwithmode = itemView.findViewById<TextView>(R.id.paymentwithmode)
////        val rideid = itemView.findViewById<TextView>(R.id.rideid)
////        val cashimagee = itemView.findViewById<LinearLayout>(R.id.cashimagee)
////        val cancelimagee = itemView.findViewById<LinearLayout>(R.id.cancelimagee)
////        val fulllayoutclick = itemView.findViewById<LinearLayout>(R.id.fulllayoutclick)
////        val amountsymbol = itemView.findViewById<TextView>(R.id.amountsymbol)
////        val currenysymboll = itemView.findViewById<TextView>(R.id.currenysymboll)
////
//
//
//    }
//}