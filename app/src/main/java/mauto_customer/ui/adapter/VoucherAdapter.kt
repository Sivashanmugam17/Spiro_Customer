package mauto_customer.ui.adapter


import android.content.Context
import android.content.Intent
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
import kotlinx.android.synthetic.main.offers_view_adapter.view.*
import mauto_customer.ui.OfferProoModel
import mauto_customer.ui.VoucherModul
import mauto_customer.ui.mainpage.Offers
import mauto_customer.ui.mainpage.ShareReferlCode
import java.util.ArrayList


class VoucherAdapter(val context: Context, var countrymodedata: ArrayList<VoucherModul>, internal var mCustomOnClickListener: rideCustomOnClickListener) : RecyclerView.Adapter<VoucherAdapter.ViewHolder>()
{
    override fun onBindViewHolder(p0: ViewHolder, p1: Int)
    {
//        p0?.dateandtime?.text = countrymodedata[p1].pickupdate!!.replace("am", "AM").replace("pm","PM")
//        p0?.amount?.text = countrymodedata[p1].amount!!.substring(1,countrymodedata[p1].amount!!.length)
        p0.Voucher_Amount?.text = countrymodedata[p1].voucherdiscount_amount

//        Glide.with(context).load(countrymodedata[p1].banner_image).into(p0?.id_proof_imageview1)
//        Glide.with(context).load(countrymodedata[p1].offer_image).into(p0?.id_proof_imageview_back)
        p0.Voucher_date?.text = countrymodedata[p1].voucher_offer_valid_date
        p0.contract?.text = countrymodedata[p1].voucher_name
//
        p0.Voucher_code?.text = countrymodedata[p1].vouchercode
//        Log.d("mmmcghefjc", countrymodedata[p1].status.toString())

        p0.itemView.setOnClickListener{
            countrymodedata[p1].vouchercode?.let { id ->
                mCustomOnClickListener.onItemClickListener(p0.itemView, p1, id)
            }
        }




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
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.vouchers_view_adapter, p0, false)


        return ViewHolder(v)



        p0.id_proof_imageview1.setOnClickListener{

            val intent2 = Intent(context, ShareReferlCode()::class.java)
            context.startActivity(intent2)
        }

    }
    override fun getItemCount(): Int
    {
        return countrymodedata.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val status = itemView.findViewById<TextView>(R.id.status)
        val Voucher_Amount = itemView.findViewById<TextView>(R.id.Voucher_Amount)
        val Voucher_code = itemView.findViewById<TextView>(R.id.vocher_code)
        val Voucher_date = itemView.findViewById<TextView>(R.id.Voucher_date)
        val contract= itemView.findViewById<TextView>(R.id.contract)

//        val id_proof_imageview1 = itemView.findViewById<ImageView>(R.id.id_proof_imageview1)
//        val id_proof_imageview_back = itemView.findViewById<ImageView>(R.id.id_proof_imageview_back)

//        val getway_type = itemView.findViewById<TextView>(R.id.getway_type)





    }
}