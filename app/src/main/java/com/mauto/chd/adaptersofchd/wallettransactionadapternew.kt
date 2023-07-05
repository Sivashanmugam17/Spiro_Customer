package com.mauto.chd.adaptersofchd


import android.content.Context
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mauto.chd.Modal.TransactionHistoryModel
import com.mauto.chd.Modal.TransactionHistoryModelCustomer
import com.mauto.chd.R
import com.mauto.chd.clickableInterface.rideCustomOnClickListener
import java.util.ArrayList


class wallettransactionadapternew(val context: Context, var countrymodedata: ArrayList<TransactionHistoryModelCustomer>, internal var mCustomOnClickListener: rideCustomOnClickListener) : RecyclerView.Adapter<wallettransactionadapternew.ViewHolder>()
{
    override fun onBindViewHolder(p0: ViewHolder, p1: Int)
    {
//        p0?.dateandtime?.text = countrymodedata[p1].pickupdate!!.replace("am", "AM").replace("pm","PM")
//        p0?.amount?.text = countrymodedata[p1].amount!!.substring(1,countrymodedata[p1].amount!!.length)
        p0?.txn_date?.text = countrymodedata[p1].txn_date

        p0?.getway?.text = ""+"txn id :"+countrymodedata[p1].txn_id
        p0?.getway_type?.text = countrymodedata[p1].gateway

        if (p1==countrymodedata.size-1){
            p0?.view_lay.visibility=View.GONE
        }


        p0?.txn_amount?.text ="+ "+countrymodedata[p1].currency_code+" "+countrymodedata[p1].txn_amount.toString()



    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder
    {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.wallet_transaction_list, p0, false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int
    {
        return countrymodedata.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val getway = itemView.findViewById<TextView>(R.id.getway)
        val txn_date = itemView.findViewById<TextView>(R.id.txn_date)
        val txn_amount = itemView.findViewById<TextView>(R.id.txn_amount)
        val getway_type = itemView.findViewById<TextView>(R.id.getway_type)


        val view_lay = itemView.findViewById<View>(R.id.view_lay)



    }
}