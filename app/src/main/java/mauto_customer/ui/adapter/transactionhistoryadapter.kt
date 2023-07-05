package mauto_customer.ui.adapter


import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mauto.chd.Modal.TransactionHistoryModel
import com.mauto.chd.R
import com.mauto.chd.clickableInterface.rideCustomOnClickListener
import java.util.ArrayList


class transactionhistoryadapter(val context: Context, var countrymodedata: ArrayList<TransactionHistoryModel>, internal var mCustomOnClickListener: rideCustomOnClickListener) : RecyclerView.Adapter<transactionhistoryadapter.ViewHolder>()
{
    override fun onBindViewHolder(p0: ViewHolder, p1: Int)
    {
        Log.d("checkingsphone","listviewss")
//        p0?.dateandtime?.text = countrymodedata[p1].pickupdate!!.replace("am", "AM").replace("pm","PM")
//        p0?.amount?.text = countrymodedata[p1].amount!!.substring(1,countrymodedata[p1].amount!!.length)





    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder
    {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.transaction_history_adapter, p0, false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int
    {
        return countrymodedata.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {






    }
}