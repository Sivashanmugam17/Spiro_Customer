package com.mauto.chd.adaptersofchd

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mauto.chd.R


class TransactionHistoryAdapter(var contax:Context /*private var transactionHistoryModel:ArrayList<TransactionHistoryModel>*/ ) : RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder>() {
    private lateinit var doctorsListOnItemClickListener: DoctorsListOnItemClickListener

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       // val dataModel = transactionHistoryModel[position]



    /*    if (position == doctorsList.size - 1)
            holder.binding.view.visibility = View.GONE
        else
            holder.binding.view.visibility = View.VISIBLE*/

/*        if (transactionHistoryModel[position].onlineStatus == "1")
            holder.itemView.doctorStatus.visibility = View.VISIBLE

        else
            holder.itemView.doctorStatus.visibility = View.GONE



        if (transactionHistoryModel[position].imageUrl != null && transactionHistoryModel[position].imageUrl.isNotEmpty())
          //  Glide.with(contax).load(dataModel.imageUrl).into((holder.binding.profileImage))

        Glide.with(contax).load(transactionHistoryModel[position].imageUrl).centerCrop().into(holder.itemView.profileImage);




        holder.itemView.doctorName.text=dataModel.name
        holder.itemView.doctorDesignation.text=dataModel.address
        holder.itemView.video.setOnClickListener {

            doctorsListOnItemClickListener.onItemClick(position, ""+dataModel.nurse_id, ""+dataModel.address, "Nurse", ""+dataModel.name, ""+dataModel.imageUrl,""+holder.itemView.video.resources.getString(R.string.video_txt))
        }
        holder.itemView.call.setOnClickListener {
            doctorsListOnItemClickListener.onItemClick(position, ""+dataModel.nurse_id, ""+dataModel.address, "Nurse", ""+dataModel.name, ""+dataModel.imageUrl,""+holder.itemView.video.resources.getString(R.string.call_txt))
        }
        holder.itemView.chat.setOnClickListener {

            EventBus.getDefault().post("chat")

            doctorsListOnItemClickListener.onItemClick(position, ""+dataModel.nurse_id, ""+dataModel.address, "Nurse", ""+dataModel.name, ""+dataModel.imageUrl,""+holder.itemView.video.resources.getString(R.string.chat_taxt))
        }


        holder.itemView.parentLayout.setOnClickListener {
            doctorsListOnItemClickListener.onItemClick(position, ""+dataModel.nurse_id, ""+dataModel.address, "Nurse", ""+dataModel.name, ""+dataModel.imageUrl,""+holder.itemView.video.resources.getString(R.string.chat_taxt))

    }*/



    }





    fun setOnItemClickListener(navigationOnClickListener: DoctorsListOnItemClickListener) {
        this.doctorsListOnItemClickListener = navigationOnClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, p: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.wallet_transaction_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        var size = 0
        try {
//            size = transactionHistoryModel.size
            size=8
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return size
    }



    interface DoctorsListOnItemClickListener {
        fun onItemClick(position: Int, receiverId: String,description: String, receiverType: String, receiverName: String, imageUrl: String, eventType: String)
    }

   /* fun updateList(doctorsList: ArrayList<TransactionHistoryModel>) {
        this.transactionHistoryModel = doctorsList
        notifyDataSetChanged()
    }*/

    class ViewHolder(var view:View) : RecyclerView.ViewHolder(view)
}

