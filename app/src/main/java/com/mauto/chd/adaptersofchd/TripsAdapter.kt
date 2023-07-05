package com.mauto.chd.adaptersofchd

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mauto.chd.view_model_trip_detail.TripDataModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mauto.chd.R
import com.mauto.chd.clickableInterface.tripdetailclick


class TripsAdapter(val context : Context, private val earning: List<TripDataModel>, private val listener: tripdetailclick) : RecyclerView.Adapter<TripsAdapter.ViewHolder>()
{
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder?.rideid?.text = earning[position].ride_id
        holder?.ridedateandtime?.text = earning[position].ride_date+" "+context.getString(R.string.att)+" "+earning[position].ride_time
        holder?.tripfare?.text = earning[position].driver_revenue
        holder?.rideid?.text = context.getString(R.string.tripid)+" "+earning[position].ride_id
        if(earning[position].group.equals("all"))  holder?.ridestatus?.text = context.getString(R.string.tripcancelled)
        else if(earning[position].group.equals("completed"))  holder?.ridestatus?.text = context.getString(
            R.string.tripcompleted)
        Glide.with(context)
                .load(earning[position].map_image)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                       return false
                    }
                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                     return false
                    }
                })
                .into(holder?.loadmap)
        holder?.loadmap?.setOnClickListener { view -> listener.onRecyclerViewItemClick(view, earning.get(position).ride_id.toString(), earning.get(position).map_image.toString()) }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder
    {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.tripsadapter, p0, false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int
    {
        return earning.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val rideid = itemView.findViewById<TextView>(R.id.rideid)
        val loadmap = itemView.findViewById<ImageView>(R.id.loadmap)
        val ridedateandtime = itemView.findViewById<TextView>(R.id.ridedateandtime)
        val tripfare = itemView.findViewById<TextView>(R.id.tripfare)
        val ridestatus = itemView.findViewById<TextView>(R.id.ridestatus)
    }
}