package com.mauto.chd.view_model_trip_detail

import android.content.Context
import com.mauto.chd.commonutils.CurrencySymbolConventer
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.ArrayList


class Trippagerepostiatry(private val listener: TripListener)
{
    //for getting driver details
    fun gettripdetails(mcontext:Context,response:String)
    {
        try
        {
            var tripdatamodelarraylist = ArrayList<TripDataModel>()
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
                                var ride_date    = j_ride_object.getString("ride_date")
                                var grand_fare    = j_ride_object.getString("grand_fare")
                                var driver_revenue    = j_ride_object.getString("driver_revenue")
                                if(driver_revenue.equals(""))
                                    driver_revenue = "0.00"
                                var map_image    = j_ride_object.getString("map_image")
                                var currency_code    = j_ride_object.getString("currency_code")
                                var currency_symbol: String = ""
                                var currencySymbolConventer: CurrencySymbolConventer
                                currencySymbolConventer = CurrencySymbolConventer()
                                currency_symbol = currencySymbolConventer.getCurrencySymbol(currency_code)



                                var pickup    = j_ride_object.getString("pickup")
                                var group    = j_ride_object.getString("group")
                                var datetime    = j_ride_object.getString("datetime")
                                tripdatamodelarraylist.add(TripDataModel(ride_id,ride_time,ride_date,grand_fare,currency_symbol+driver_revenue,map_image,currency_symbol,pickup,group,datetime))
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



    fun getridetypes(mcontext:Context,ridesttatus:String,fullarray:ArrayList<TripDataModel>,fromdate:String,todate:String)
    {




        var arraylist=ArrayList<String>()
        if(!fromdate.equals(""))
        {
            var date =fromdate
            var spf = SimpleDateFormat("dd/MM/yyyy")
            val newDate = spf.parse(date)
            spf = SimpleDateFormat("dd-MM-yyyy")
            date = spf.format(newDate)
            arraylist.add(date)
        }

        if(!todate.equals(""))
        {
            var date = todate
            var spf = SimpleDateFormat("dd/MM/yyyy")
            val newDate = spf.parse(date)
            spf = SimpleDateFormat("dd-MM-yyyy")
            date = spf.format(newDate)
            arraylist.add(date)
        }


        var tripdatamodelarraylist = ArrayList<TripDataModel>()
        for (i in 0 until fullarray.size)
        {
            var group    =fullarray.get(i).group.toString()
            if(ridesttatus.equals("showall"))
            {
                var ride_id: String       = fullarray.get(i).ride_id.toString()
                var ride_time     = fullarray.get(i).ride_time.toString()
                var ride_date    = fullarray.get(i).ride_date.toString()
                var grand_fare    = fullarray.get(i).grand_fare.toString()
                var driver_revenue    = fullarray.get(i).driver_revenue.toString()
                if(driver_revenue.equals(""))
                    driver_revenue = "0.00"
                var map_image    =fullarray.get(i).map_image.toString()
                var currency_code    = fullarray.get(i).currency_code.toString()
                var pickup    = fullarray.get(i).pickup.toString()
                var datetime    = fullarray.get(i).datetime.toString()
                if(arraylist.size > 0)
                {
                        if(arraylist.contains(datetime))
                        tripdatamodelarraylist.add(TripDataModel(ride_id,ride_time,ride_date,grand_fare,driver_revenue,map_image,currency_code,pickup,group,datetime))
                }
                else
                    tripdatamodelarraylist.add(TripDataModel(ride_id,ride_time,ride_date,grand_fare,driver_revenue,map_image,currency_code,pickup,group,datetime))
             }
            else
            {
                if(group.equals(ridesttatus))
                {
                    var ride_id: String       = fullarray.get(i).ride_id.toString()
                    var ride_time     = fullarray.get(i).ride_time.toString()
                    var ride_date    = fullarray.get(i).ride_date.toString()
                    var grand_fare    = fullarray.get(i).grand_fare.toString()
                    var driver_revenue    = fullarray.get(i).driver_revenue.toString()
                    if(driver_revenue.equals(""))
                        driver_revenue = "0.00"
                    var map_image    =fullarray.get(i).map_image.toString()
                    var currency_code    = fullarray.get(i).currency_code.toString()
                    var pickup    = fullarray.get(i).pickup.toString()
                    var datetime    = fullarray.get(i).datetime.toString()
                    if(arraylist.size > 0)
                    {
                        if(arraylist.contains(datetime))
                            tripdatamodelarraylist.add(TripDataModel(ride_id,ride_time,ride_date,grand_fare,driver_revenue,map_image,currency_code,pickup,group,datetime))
                    }
                    else
                        tripdatamodelarraylist.add(TripDataModel(ride_id,ride_time,ride_date,grand_fare,driver_revenue,map_image,currency_code,pickup,group,datetime))

                }
            }

        }
        if (listener != null)   listener!!.filterDataReceived(tripdatamodelarraylist)
    }



}