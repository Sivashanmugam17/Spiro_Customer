package com.mauto.chd.adaptersofchd

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.mauto.chd.R
import com.mauto.chd.clickableInterface.driverOnClickListener
import com.mauto.chd.ui.sidemenus.questionAnswerModel


class Faqchildadapter(val context : Context, var countrymodedata: ArrayList<questionAnswerModel>, internal var mCustomOnClickListener: driverOnClickListener) : RecyclerView.Adapter<Faqchildadapter.ViewHolder>()
{
    private var listViewHolder : ArrayList<ViewHolder> = ArrayList()

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        listViewHolder!!.add(p0)
        p0?.question?.text =countrymodedata[p1].question

        p0?.plus.setOnClickListener {
            if( listViewHolder[p1].answer?.visibility== View.VISIBLE){
                listViewHolder[p1].answer?.visibility= View.GONE
                listViewHolder[p1].plus.setBackgroundResource(R.drawable.icon_plus)
            } else {
                for (i in 0 until listViewHolder!!.size){
                    if (i == p1){
                        listViewHolder[i].answer?.visibility= View.VISIBLE
                        listViewHolder[i].plus.setBackgroundResource(R.drawable.minus)
                    } else {
                        listViewHolder[i].answer?.visibility= View.GONE
                        listViewHolder[i].plus.setBackgroundResource(R.drawable.icon_plus)
                    }
                }

            }
        }
        p0?.answer?.visibility= View.GONE
        p0?.answer?.text =countrymodedata[p1].answer
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder
    {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.faq_adapter_child, p0, false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int
    {
        return countrymodedata.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val  question = itemView.findViewById<TextView>(R.id.layout_inflate_child_list_item_TXT_title)
        val  answer = itemView.findViewById<TextView>(R.id.layout_inflate_child_list_item_TXT_answer)
        val  plus = itemView.findViewById<ImageView>(R.id.layout_inflate_child_list_item_IMG_plus)
    }
}