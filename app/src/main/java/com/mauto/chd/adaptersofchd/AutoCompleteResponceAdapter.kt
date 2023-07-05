package com.mauto.chd.adaptersofchd

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mauto.chd.ui.MainPage.AutoCompleteSearchActivity
import com.dineshm.AutoCompleteWithDB.Pojo.PredictionsItem
import com.mauto.chd.R

class AutoCompleteResponceAdapter(val context: Activity, private var list: java.util.ArrayList<PredictionsItem>) : RecyclerView.Adapter<AutoCompleteResponceAdapter.ExampleViewHolder>() {
    var companyListAll = ArrayList<PredictionsItem>()

    init {
        companyListAll = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.autocompleteitem, parent, false)
        return ExampleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val listPosition = list[position]
        holder.autocompleteitem_tv.text = listPosition.description
        holder.autocompleteitem_cv.setOnClickListener {
            val locationName = holder.autocompleteitem_tv.text
//            AutoCompleteActivity.placeName = locationName.toString()
            Log.e("Location Name...", locationName.toString())
             val intetnt = context.intent.putExtra("location_name",locationName.toString())
             context.setResult(Activity.RESULT_FIRST_USER,intetnt)
            AutoCompleteSearchActivity.instance.finish()
        }
    }

    override fun getItemCount() = list.size

    inner class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val autocompleteitem_tv: TextView = itemView.findViewById(R.id.autocompleteitem_tv)
        val autocompleteitem_cv: CardView = itemView.findViewById(R.id.autocompleteitem_cv)
    }

}