package mauto_customer.ui.adapter


import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mauto.chd.Modal.DriverProofModel
import com.mauto.chd.R
import com.mauto.chd.clickableInterface.rideCustomOnClickListener
import java.util.ArrayList


class Driverproofadapter(val context: Context, var countrymodedata: ArrayList<DriverProofModel>, internal var mCustomOnClickListener: rideCustomOnClickListener) : RecyclerView.Adapter<Driverproofadapter.ViewHolder>()
{
    override fun onBindViewHolder(p0: ViewHolder, p1: Int)
    {
//        p0?.dateandtime?.text = countrymodedata[p1].pickupdate!!.replace("am", "AM").replace("pm","PM")
//        p0?.amount?.text = countrymodedata[p1].amount!!.substring(1,countrymodedata[p1].amount!!.length)
//        p0?.txn_date?.text = countrymodedata[p1].txn_date
        Glide.with(context).load(countrymodedata[p1].front_url).into(p0?.id_proof_imageview)
//        Glide.with(context).load(countrymodedata[p1].back_url).placeholder(R.drawable.loading_placeholder).into(p0?.id_proof_imageview_back)

//        p0?.National_Card?.text = countrymodedata[p1].name
        Log.d("mmmcghefjc", countrymodedata[p1].name.toString())


//        if(countrymodedata[p1].type.equals("debit")) {
//            p0?.txn_amount?.text ="- "+countrymodedata[p1].currency_code+" "+countrymodedata[p1].txn_amount.toString()
//            p0?.txn_amount?.setTextColor(Color.parseColor("#ff0000"))
//
//
//        }
//        else
//        {
//            p0?.txn_amount?.text ="+ "+countrymodedata[p1].currency_code+" "+countrymodedata[p1].txn_amount.toString()
//            p0?.txn_amount?.setTextColor(Color.parseColor("#65aa01"))
//
////            p0?.getway?.text ="tmoney"
//
//        }


    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder
    {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.driver_proof_adapter, p0, false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int
    {
        return countrymodedata.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
//        val National_Card = itemView.findViewById<TextView>(R.id.National_Card)
        val id_proof_imageview = itemView.findViewById<ImageView>(R.id.id_proof_imageview)
        val id_proof_imageview_back = itemView.findViewById<ImageView>(R.id.id_proof_imageview_back)

//        val getway_type = itemView.findViewById<TextView>(R.id.getway_type)





    }
}