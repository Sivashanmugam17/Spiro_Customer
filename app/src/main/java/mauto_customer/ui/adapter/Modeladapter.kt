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
import java.util.ArrayList


class Modeladapter(val context: Context, var countrymodedata:  ArrayList<String>, internal var mCustomOnClickListener: rideCustomOnClickListener) : RecyclerView.Adapter<Modeladapter.ViewHolder>()
{
    private var previousPosition = -1

    override fun onBindViewHolder(p0: ViewHolder, p1: Int)
    {
//        p0?.dateandtime?.text = countrymodedata[p1].pickupdate!!.replace("am", "AM").replace("pm","PM")
//        p0?.amount?.text = countrymodedata[p1].amount!!.substring(1,countrymodedata[p1].amount!!.length)
        p0?.radio_button?.text = countrymodedata[p1]


//        if (p1==countrymodedata.size-1){
//            p0?.view_lay.visibility=View.GONE
//        }


        p0?.radio_button.setOnClickListener {

                mCustomOnClickListener.onItemClickListener(p0.radio_button,p1,p0?.radio_button.text.toString())

            previousPosition = p0.adapterPosition
            notifyDataSetChanged()

        }

        p0.radio_button.isChecked = previousPosition==p1



    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder
    {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.model_list, p0, false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int
    {
        return countrymodedata.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val radio_button = itemView.findViewById<RadioButton>(R.id.radio_button)



    }
}