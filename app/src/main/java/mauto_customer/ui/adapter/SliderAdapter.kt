package mauto_customer.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mauto.chd.R
import com.mauto.chd.ui.sidemenus.WalletPage
import mauto_customer.ui.mainpage.Offers
import mauto_customer.ui.mainpage.SliderModel

class SliderAdapter(var list: ArrayList<SliderModel>,var context: Context):
    RecyclerView.Adapter<SliderAdapter.ViewHolder>() {
class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

    val sliderImage:ImageView = itemView.findViewById(R.id.slider_image)




}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.slider_layout,parent,false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var currentItem = list [position]

        Glide.with(context.applicationContext).
            load(currentItem.image).
            into(holder.sliderImage)

        holder.itemView.setOnClickListener{


                val intent2 = Intent(context, Offers()::class.java)
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                context.startActivity(intent2)

        }


    }

    override fun getItemCount(): Int {


        return list.size
    }


}