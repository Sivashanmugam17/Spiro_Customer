package com.cas.exapmle;

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mauto.chd.R
import com.mauto.chd.clickableInterface.CustomOnClickListenertype
import com.mauto.chd.earningsviewmodel.PaymentMethodsModel


class  PaymentAdapterRecyclerAdapter (private val mContext: Activity, private val list: ArrayList<PaymentMethodsModel>, internal var mCustomOnClickListener: CustomOnClickListenertype, var SnearestDriverArrivingTime:String) :
    RecyclerView.Adapter<PaymentAdapterRecyclerAdapter.CustomViewHolder>() {
    lateinit var holder: CustomViewHolder

    var selectedPosition = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val rowView =
            LayoutInflater.from(parent.context).inflate(R.layout.payment_list, parent, false)
        return CustomViewHolder(rowView)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        this.holder=holder
      /*  if (selectedPosition==position){
            holder.select_img.visibility = View.VISIBLE
        }else{
            holder.select_img.visibility = View.GONE
        }*/

        if (list[position].paymentMethodSelected.equals("1")){
            holder.select_img.setBackgroundResource(R.drawable.ic_select)
        }else{
            holder.select_img.setBackgroundResource(R.drawable.ic_unselect)
//            holder.select_payment_lay.setBackgroundResource(R.drawable.backround_grey)
//            holder.select_img.setBackgroundResource(R.drawable.ic_select_asha)
//

        }

        if (list[position].paymentMethodName.equals("card")){
            holder.payment_type.text="**** **** **** "+list[position].card_number
            holder.card_detalais.visibility=View.VISIBLE
            holder.card_delete.visibility=View.VISIBLE
        }else{
            holder.payment_type.text=list[position].paymentMethodName

            holder.card_detalais.visibility=View.GONE
            holder.card_delete.visibility=View.GONE
        }
        if (list[position].paymentMethodCode.equals("wallet")){
            holder.payment_type_wallet.visibility=View.VISIBLE
        }else{
            holder.payment_type_wallet.visibility=View.GONE
        }

        holder.card_exp_data.text=list[position].card_type+" -"+list[position].exp_month+"/"+list[position].exp_year
//        holder.categ_name.text=list[position].mCategoryName
//        val requestOptions = RequestOptions()
//        requestOptions.placeholder(R.drawable.ic_bike)
//        requestOptions.error(R.drawable.ic_bike)
//        Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(list[position].mCategoryImage).into(holder.img_item_right)


        holder.select_payment_lay.setOnClickListener {

                mCustomOnClickListener.onItemClickListener(holder.select_img,position)
                selectedPosition = position
                notifyDataSetChanged()


        }

    }



    fun EstimateTimeDriver(nearestDriverArrivingTime: String, position: Int) {
        SnearestDriverArrivingTime=nearestDriverArrivingTime
      notifyItemChanged(position)

    }
    class CustomViewHolder(val rowView: View, var item: String? = null) :
        RecyclerView.ViewHolder(rowView) {
        val select_img = rowView.findViewById(R.id.select_img) as ImageView
        val payment_type=rowView.findViewById(R.id.payment_type)as TextView
        val card_delete=rowView.findViewById(R.id.card_delete)as TextView
        val card_detalais=rowView.findViewById(R.id.card_detalais)as LinearLayout
        val select_payment_lay=rowView.findViewById(R.id.select_payment_lay)as LinearLayout
        val payment_type_wallet=rowView.findViewById(R.id.payment_type_wallet)as TextView
        val card_exp_data=rowView.findViewById(R.id.card_exp_data)as TextView


    }
}