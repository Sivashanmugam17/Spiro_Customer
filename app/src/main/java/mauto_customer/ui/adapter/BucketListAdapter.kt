package mauto_customer.ui.adapter


import android.content.Context
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import com.mauto.chd.Modal.TransactionHistoryModel
import com.mauto.chd.R
import com.mauto.chd.clickableInterface.rideCustomOnClickListener
import kotlinx.android.synthetic.main.mainpage_spoc.*
import mauto_customer.ui.BucketlistModel
import java.util.ArrayList


class BucketListAdapter(val context: Context, var countrymodedata: ArrayList<BucketlistModel>, internal var mCustomOnClickListener: rideCustomOnClickListener) : RecyclerView.Adapter<BucketListAdapter.ViewHolder>()
{


    override fun onBindViewHolder(p0: ViewHolder, p1: Int)
    {
//        p0?.dateandtime?.text = countrymodedata[p1].pickupdate!!.replace("am", "AM").replace("pm","PM")
//        p0?.amount?.text = countrymodedata[p1].amount!!.substring(1,countrymodedata[p1].amount!!.length)
        p0?.Recenttextviews?.text =countrymodedata[p1].amount
        p0?.amountsnew?.text =countrymodedata[p1].label
        p0?.SOMBLE?.text =countrymodedata[p1].currency_symbol

        if (p1==countrymodedata.size-1){
            p0?.view_1.visibility=View.GONE
        }


        p0?.Recenttextviews.setOnClickListener {


                mCustomOnClickListener.onItemClickListener(p0.Recenttextviews,p1,p0?.Recenttextviews.text.toString())


        }



    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder
    {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.amount_list, p0, false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int
    {
        return countrymodedata.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
//        val amount: Any
        val Recenttextviews = itemView.findViewById<TextView>(R.id.Recenttextviews)
        val view_1 = itemView.findViewById<View>(R.id.view_1)
        val amountsnew = itemView.findViewById<TextView>(R.id.amountsnew1)
        val SOMBLE = itemView.findViewById<TextView>(R.id.amountsnew)




    }
}