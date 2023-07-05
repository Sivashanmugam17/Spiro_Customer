package com.mauto.chd.earningsviewmodel

import android.content.Context
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class Earningpagepagerepostiatry(private val listener: EarningListener)
{
    //for getting driver details
    fun gettripdetails(mcontext:Context,response:String)
    {
        try
        {
            var tripdatamodelarraylist = ArrayList<WeekModel>()
            var responseObj = JSONObject(response)
            if (responseObj.getString("status").equals("1"))
            {
                if (responseObj.has("response"))
                {
                    var getingres: JSONObject? = responseObj.getJSONObject("response")

                    val total_rides = `getingres`!!.getString("total_rides")
                    if(!total_rides.equals("0"))
                    {
                        val jarray = `getingres`!!.getJSONArray("rides")
                        if (jarray.length() > 0)
                        {
                            for (i in 0 until jarray.length())
                            {
                                var j_ride_object = jarray.getJSONObject(i)
                                var ride_id: String       = j_ride_object.getString("ride_id")
                                var ride_time     = j_ride_object.getString("ride_time")


                                var pickup    = j_ride_object.getString("pickup")
                                var group    = j_ride_object.getString("group")
                                var datetime    = j_ride_object.getString("datetime")
                                tripdatamodelarraylist.add(WeekModel(ride_id,ride_time,""))
                            }
                            if (listener != null)   listener!!.onDataReceived(tripdatamodelarraylist)
                        }
                    }
                    else listener!!.onSuccessorFailed(3)

                }
            }
            else if (listener != null)   listener!!.onSuccessorFailed(0)
        }
        catch (e: JSONException)
        {
        }
    }







}