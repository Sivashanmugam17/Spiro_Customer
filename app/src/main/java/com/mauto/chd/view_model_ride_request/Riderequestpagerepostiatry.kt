package com.mauto.chd.view_model_ride_request

import org.json.JSONException
import java.util.ArrayList


class Riderequestpagerepostiatry(private val listener: RideRequestListener)
{
    //for getting ride request details
    fun getdrivertails(response:String)
    {
        try
        {
            val riderequestarraylist: ArrayList<ItemInfo> = ArrayList()
            for (i in 0 until 2)
            {
                var rideid = ""+i
                var timestamp = "112"
                var category = "UberX"
                var timer="30"
                var rating = "4.80"
                var min = "4 Min"
                var miles = "1.2 Mi"
                var droppoint = "Koyambedu"
                var pickuplat = "13.8023"
                var pickuplong = "30.254"

                val curTime = System.currentTimeMillis()
                var timertoin:Int = timer.toInt()
                var timertolong:Long = (timertoin * 1000).toLong()


                riderequestarraylist.add(ItemInfo(rideid, timestamp, category, timer.toLong(), rating, min,miles,droppoint, pickuplat, pickuplong, timertolong, (curTime + timertolong),""))
            }
            if (listener != null)
            {
                    listener!!.onDataReceived(riderequestarraylist)
            }
        }
        catch (e: JSONException)
        {
        }
    }
}