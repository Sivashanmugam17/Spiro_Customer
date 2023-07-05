package mauto_customer.ui.adapter


import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.mauto.chd.R
import com.mauto.chd.clickableInterface.rideCustomOnClickListener
import java.util.ArrayList


class servicelistadapter(val context: Context, var countrymodedata: ArrayList<String>, internal var mCustomOnClickListener: rideCustomOnClickListener) : RecyclerView.Adapter<servicelistadapter.ViewHolder>()
{
    var app_lay_check:String = "0"
    var vehicledetailslay:String = "0"

    override fun onBindViewHolder(p0: ViewHolder, p1: Int)
    {
        Log.d("checkingsphone","listviewss")
//        p0?.getway?.text = countrymodedata[p1].!!.replace("am", "AM").replace("pm","PM")
        p0?.getway?.text = countrymodedata[p1]
        p0?.arrow_payment.setOnClickListener {
            if (vehicledetailslay.equals("0")){
                p0?.payments_lay.visibility=View.VISIBLE
                vehicledetailslay="1"
            }else{
                p0?.payments_lay.visibility=View.GONE
                vehicledetailslay="0"
            }
        }

        p0?.report_send_payment.setOnClickListener {
            if (p0.type_your_comment_payment.text.isNotEmpty()){
                mCustomOnClickListener.onItemClickListener(p0.type_your_comment_payment,p1,p0?.getway.text.toString())

            }else{
                Toast.makeText(context,"Type Your comments",Toast.LENGTH_SHORT).show()

            }
        }





    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder
    {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.service_list_adapter, p0, false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int
    {
        return countrymodedata.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        val getway = itemView.findViewById<TextView>(R.id.report_title_tvs)

        val arrow_payment = itemView.findViewById<ImageView>(R.id.arrow_payment)

        val payments_lay = itemView.findViewById<ConstraintLayout>(R.id.payments_lay)

        val report_send_payment = itemView.findViewById<TextView>(R.id.report_send_payment)
        val type_your_comment_payment = itemView.findViewById<TextView>(R.id.type_your_comment_payment)

    }
}