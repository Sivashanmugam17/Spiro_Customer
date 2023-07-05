package com.mauto.chd.view_model_trip_detail

import android.content.Context
import com.mauto.chd.view_model_tracking_page.earningModel
import com.mauto.chd.view_model_tracking_page.passengerpaidModel
import com.mauto.chd.commonutils.CurrencySymbolConventer
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList


class TripDetailfullrepostiatry(private val listener: FullTripDetailListener)
{
    //for getting driver details
    fun splitrideinfo(response:String,context:Context)
    {
        try
        {
            var adduserdetails:fullridedetailsDataModel ?= null

            var responseObj = JSONObject(response)
            if (responseObj.getString("status").equals("1"))
            {
                if (responseObj.has("response"))
                {

                    val earnnglist = ArrayList<earningModel>()
                    val passengerlistt = ArrayList<passengerpaidModel>()

                    var getingres: JSONObject? = responseObj.getJSONObject("response")
                    var detailsres: JSONObject? = getingres!!.getJSONObject("details")
                    var user_profile: JSONObject? = getingres!!.getJSONObject("user_profile")

                    var user_name: String       = user_profile!!.getString("user_name")
                    var user_id: String       = user_profile!!.getString("user_id")
                    var user_email: String       = user_profile!!.getString("user_email")
                    var phone_number: String       = user_profile!!.getString("phone_number")
                    var user_image: String       = user_profile!!.getString("user_image")
                    var user_review: String       = user_profile!!.getString("user_review")
                    var pickup_location: String       = user_profile!!.getString("pickup_location")
                    var pickup_time: String       = user_profile!!.getString("pickup_time")
                    var drop_loc: String       = user_profile!!.getString("drop_loc")
                    var drop_time: String       = user_profile!!.getString("drop_time")
                    var firstamount:String = ""
                    var secondamount:String = ""
                    var currency: String       = detailsres!!.getString("currency")
                    var currencySymbolConventer: CurrencySymbolConventer
                    currencySymbolConventer = CurrencySymbolConventer()
                    var currency_code = currencySymbolConventer.getCurrencySymbol(currency)

                    var ride_date: String       = detailsres!!.getString("ride_date")
                    var distance_unit: String       = detailsres!!.getString("distance_unit")

                    val jarray = `detailsres`!!.getJSONArray("driver_earning")
                    if (jarray.length() > 0)
                    {
                        for (i in 0 until jarray.length())
                        {
                            var j_ride_object = jarray.getJSONObject(i)
                            var title       = j_ride_object.getString("title")
                            var value       = currency_code+j_ride_object.getString("value")
                            var positive       = j_ride_object.getString("positive")
                            earnnglist.add(earningModel(title, value, positive))
                        }
                    }


                    val jarpassenger_fareray = `detailsres`!!.getJSONArray("passenger_fare")
                    if (jarpassenger_fareray.length() > 0)
                    {
                        for (i in 0 until jarpassenger_fareray.length())
                        {
                            var j_ride_object = jarpassenger_fareray.getJSONObject(i)
                            var title       = j_ride_object.getString("title")
                            var value       = currency_code+j_ride_object.getString("value")
                            var positive       = j_ride_object.getString("positive")
                            passengerlistt.add(passengerpaidModel(title, value, positive))
                        }
                    }

                    var payment_method: String       = detailsres!!.getString("payment_method")
                    var cab_type: String       = detailsres!!.getString("cab_type")
                    var grand_fare: String       = currency_code+detailsres!!.getString("grand_fare")
                    var ride_distafnce:String="0"

                    if (`detailsres`!!.has("summary"))
                    {
                        if(!detailsres!!.getString("summary").equals("[]"))
                        {
                            val summaryjsonobj = `detailsres`!!.getJSONObject("summary")
                            ride_distafnce  = summaryjsonobj!!.getString("ride_distance")
                        }
                    }

                    if (`detailsres`!!.has("driver_revenue"))
                    {
                        val driver_revenue = `detailsres`!!.getString("driver_revenue")
                        if (driver_revenue.contains(".")) {
                            val first = driver_revenue.split("\\.".toRegex())[0]
                            val second = driver_revenue.split("\\.".toRegex())[1]
                            firstamount = first
                            secondamount = "." + second
                        } else {
                            firstamount = driver_revenue
                            secondamount = ".00"
                        }
                    }
                    adduserdetails = fullridedetailsDataModel(user_name,user_id,user_email,phone_number,user_image,user_review,pickup_location,pickup_time,drop_loc,drop_time,firstamount,secondamount,currency_code,payment_method,cab_type,grand_fare,ride_date,distance_unit,ride_distafnce)
                    if (listener != null)
                    {
                        listener!!.onDataReceived(adduserdetails!!)
                        listener!!.onEarningList(earnnglist)
                        listener!!.onPassengerPaid(passengerlistt)
                    }
               }

            }
        }
        catch (e: JSONException)
        {
        }
    }




}