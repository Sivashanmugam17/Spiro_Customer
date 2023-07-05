package com.mauto.chd.adaptersofchd

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.mauto.chd.Modal.tripdetailsModels
import com.mauto.chd.R
import com.mauto.chd.clickableInterface.driverOnClickListener


class Faqadapter(val context : Context, var countrymodedata: ArrayList<tripdetailsModels>, internal var mCustomOnClickListener: driverOnClickListener) : RecyclerView.Adapter<Faqadapter.ViewHolder>() {
    private lateinit var faqadapter: Faqchildadapter
    var faqfulldatachild: java.util.ArrayList<tripdetailsModels> = java.util.ArrayList()

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0?.title?.text =countrymodedata[p1].topic
        p0?. recycler_question.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        faqadapter = Faqchildadapter(context,countrymodedata[p1]!!.questionAnswer!!, object : driverOnClickListener {
            override fun onItemClickListener(view: View, position: Int, rideid:String) {
//                fulldetailpage(rideid)
            }
        })
        p0?.recycler_question.adapter = faqadapter
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder
    {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.faq_adapter, p0, false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int
    {
        return countrymodedata.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val title = itemView.findViewById<TextView>(R.id.title)
        val recycler_question = itemView.findViewById<RecyclerView>(R.id.recycler_question)


    }
}