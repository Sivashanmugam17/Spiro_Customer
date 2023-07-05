package com.mauto.chd.ui.registeration

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.mauto.chd.R
import java.util.*

class vehicletypeCustomAdapter(var context: Context, var languages: ArrayList<String>) : BaseAdapter() {
     var inflater: LayoutInflater

     override fun getCount(): Int {
         return languages.size
     }

     override fun getItem(position: Int): Any? {
         return null

     }

     override fun getItemId(position: Int): Long {
         return  0

         TODO("Not yet implemented")
     }

     override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
         var convertView = convertView
         convertView = inflater.inflate(R.layout.spinner_gender, null)
         val selectingcity = convertView.findViewById<View>(R.id.text_lang) as TextView
         val view_line = convertView.findViewById<View>(R.id.view_line_lang) as View
         selectingcity.text = languages[position]
         view_line.visibility=View.GONE
         return convertView!!
     }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        convertView = inflater.inflate(R.layout.spinner_gender, null)
        val selectingcity = convertView.findViewById<View>(R.id.text_lang) as TextView
        val view_line = convertView.findViewById<View>(R.id.view_line_lang) as View
        if (position==0){
            selectingcity.visibility=View.GONE
            view_line.visibility=View.GONE
        }else{
            selectingcity.text = languages[position]
        }
        return convertView
    }
    init {
        inflater = LayoutInflater.from(context)
    }

 }
