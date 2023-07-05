//package com.cabilyhandyforalldinedoo.chd.adaptersofchd
//
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import cabily.handyforall.dinedoo.R
//import com.cabilyhandyforalldinedoo.chd.Modal.VehicleListModel
//import com.cabilyhandyforalldinedoo.chd.clickableInterface.vehiclelistclick
//
//
//class VehicleListAdapter(private var messages: ArrayList<VehicleListModel>, private val listener: vehiclelistclick)  : RecyclerView.Adapter<VehicleListAdapter.MessageViewHolder>() {
//
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.vehiclelistadapetrla, parent, false)
//        return MessageViewHolder(view)
//    }
//
//    override fun getItemCount() = messages.size
//
//
//    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
//        holder.bind(messages[position])
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return position
//    }
//
//
//
//    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val vehicle_maker: TextView = itemView.findViewById(R.id.vehicle_maker)
//        private val vehicle_model: TextView = itemView.findViewById(R.id.vehicle_model)
//        private val vehicle_number: TextView = itemView.findViewById(R.id.vehicle_number)
//        private val verfiystatus: TextView = itemView.findViewById(R.id.verfiystatus)
//        private val rootview: LinearLayout = itemView.findViewById(R.id.rootview)
//        private val editvehicle: LinearLayout = itemView.findViewById(R.id.editvehicle)
//
//        fun bind(message: VehicleListModel) {
//            if (message is VehicleListModel) {
//                if(message.verify_status.equals("No"))
//                {
//                    verfiystatus.visibility = View.VISIBLE
//                }
//                else
//                    verfiystatus.visibility = View.GONE
//
//                vehicle_maker.text = message.vehicle_maker
//                vehicle_model.text = "("+message.vehicle_model+")"
//                vehicle_number.text = message.vehicle_number
//                rootview?.setOnClickListener { view -> listener.onRecyclerViewItemClick(view,message.vehicle_maker+"("+message.vehicle_model+")" , message.vehicle_id.toString(),"default") }
//                editvehicle?.setOnClickListener { view -> listener.onRecyclerViewItemClick(view,message.vehicle_maker+"("+message.vehicle_model+")" , message.vehicle_id.toString(),"edit") }
//            }
//        }
//    }
//}