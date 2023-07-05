package com.mauto.chd.viewmodelfortriplist

import android.content.Context
import android.util.Log
import com.mauto.chd.Modal.tripdetailsModel
import com.mauto.chd.commonutils.CurrencySymbolConventer
import io.realm.Realm
import io.realm.Sort
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class tripdetailsrepostiary(private val listener: tripdetailsListener)
{
    val realm by lazy { Realm.getDefaultInstance() }
    private lateinit var currencySymbolConventer: CurrencySymbolConventer

    fun splitresponse(mContext: Context,respnse:String)
    {
        try
        {
            var responseObj = JSONObject(respnse)
            Log.d("df344sdfgsd", responseObj.toString())

            if (responseObj.getString("status").equals("1"))
            {
                if (responseObj.has("response"))
                {
                    var getingres: String = responseObj.getString("response")
                    var resultsresponse = JSONObject(getingres)
                    if(resultsresponse.getString("rides").equals("{}"))
                    {
                        if (listener != null)
                            listener!!.failedobserver("1")
                    }
                    else
                    {
                        var rides= resultsresponse.getJSONArray("rides")
                        for (j in 0 until rides.length())
                        {
                            val ride_id = rides.getJSONObject(j).getString("ride_ref")
                            val ride_status = rides.getJSONObject(j).getString("ride_status")
                            val display_status = rides.getJSONObject(j).getString("ride_status")
                            val units = rides.getJSONObject(j).getString("currency")
                            val category = rides.getJSONObject(j).getString("service_type")
                            val ride_distance=rides.getJSONObject(j).getString("ride_distance")
                            var  pickuplocation = ""
                            var  pickup_short = ""
                            var  pickup_date_time = ""
                            var  pickup_time = ""

                            var  droplocation = ""
                            var  drop_short = ""
                            var  timestamp = ""
                            var  drop_date_time = ""
                            var  drop_time = ""

                            val booking_information = rides.getJSONObject(j).getJSONArray("locations")
                            for (k in 0 until booking_information.length())
                            {
                                if(booking_information.getJSONObject(k).getString("ref").equals("pickup"))
                                {
                                     pickuplocation = booking_information.getJSONObject(k).getString("location")
                                     pickup_short = pickuplocation
                                     timestamp = ""
                                     pickup_date_time = timestamp
                                     pickup_time = booking_information.getJSONObject(k).getString("time")
                                }
                                else if(booking_information.getJSONObject(k).getString("ref").equals("drop"))
                                {
                                     droplocation = booking_information.getJSONObject(k).getString("location")
                                     drop_short = droplocation
                                     timestamp = ""
                                     drop_date_time = timestamp
                                     drop_time = booking_information.getJSONObject(k).getString("time")
                                }

                            }

                            val booking_date_time = rides.getJSONObject(j).getString("ride_date")+" "+ rides.getJSONObject(j).getString("ride_time")
                            val booking_time = rides.getJSONObject(j).getString("timestamp")
                            val booking_time_stamp = rides.getJSONObject(j).getString("timestamp")












                            var reason:String=""
                            if(ride_status.equals("Cancelled"))
                            {
                                reason="Sorry ride cancelled"
                            }




                            val summary = rides.getJSONObject(j).getString("fare_details")
                            val fare_summary = rides.getJSONObject(j).getString("fare_details")

                            val driver_name = rides.getJSONObject(j).getString("user_name")
                            val driver_image = rides.getJSONObject(j).getString("user_image")
                            val vehile_maker_model = rides.getJSONObject(j).getString("service_type")
                            val vehicle_number = ""
                            val avg_review = rides.getJSONObject(j).getString("user_review")

                            val invoice_src = rides.getJSONObject(j).getString("invoice_src")
                            val payment_method = rides.getJSONObject(j).getString("driver_revenue")
                            val grand_fare = rides.getJSONObject(j).getString("user_pay_amount")

                            val payment_typeuser = rides.getJSONObject(j).getString("payment_method")

                            val results = realm.where(tripdetailsrealmm::class.java).equalTo("ride_id", ride_id).findAll()
                            var ridesize:Int=results.size
                            if(ridesize == 0)
                            {
                                realm.beginTransaction()
                                val tripdetailsrealmm = realm.createObject(tripdetailsrealmm::class.java)
                                tripdetailsrealmm.timestampid = booking_time_stamp
                                tripdetailsrealmm.ride_id = ride_id
                                tripdetailsrealmm.ride_status = ride_status
                                tripdetailsrealmm.display_status = display_status
                                tripdetailsrealmm.units = units
                                tripdetailsrealmm.category = category
                                tripdetailsrealmm.pickuplocation = pickuplocation
                                tripdetailsrealmm.pickup_short = pickup_short
                                tripdetailsrealmm.droplocation = droplocation
                                tripdetailsrealmm.drop_short = drop_short
                                tripdetailsrealmm.booking_date_time = booking_date_time
                                tripdetailsrealmm.booking_time = booking_time
                                tripdetailsrealmm.pickup_date_time = pickup_date_time
                                tripdetailsrealmm.pickup_time = pickup_time
                                tripdetailsrealmm.drop_date_time = drop_date_time
                                tripdetailsrealmm.drop_time = drop_time
                                tripdetailsrealmm.cancelled_date_time = ""
                                tripdetailsrealmm.cancelled_time = ""
                                tripdetailsrealmm.summary = summary
                                tripdetailsrealmm.fare_summary = fare_summary

                                tripdetailsrealmm.payment_typeuser = payment_typeuser

                                tripdetailsrealmm.driver_name = driver_name
                                tripdetailsrealmm.driver_image = driver_image
                                tripdetailsrealmm.vehile_maker_model = vehile_maker_model
                                tripdetailsrealmm.vehicle_number = vehicle_number
                                tripdetailsrealmm.avg_review = avg_review

                                tripdetailsrealmm.invoice_src = invoice_src
                                tripdetailsrealmm.payment_method = payment_method
                                tripdetailsrealmm.grand_fare = grand_fare
                                tripdetailsrealmm.cancelreason = reason
                                tripdetailsrealmm.ride_distance =ride_distance

                                realm.commitTransaction()
                            }
                        }
                        if (listener != null)
                            listener!!.insertedobserver("1")
                    }
                }

            }
        }
        catch (e: JSONException)
        {
            if (listener != null)
                listener!!.failedobserver("1")
        }
    }
    fun splitlastresponse(mContext: Context,respnse:String)
    {
        try
        {
            var countryarraylist = ArrayList<tripdetailsModel>()

            var responseObj = JSONObject(respnse)
            Log.d("fgdt564667", responseObj.toString())

            if (responseObj.getString("status").equals("1"))
            {
                if (responseObj.has("response"))
                {
                    var getingres: String = responseObj.getString("response")
                    var resultsresponse = JSONObject(getingres)
                    var ridescheck: String = resultsresponse.getString("rides")
                    if(!ridescheck.equals("{}"))
                    {
                        var rides= resultsresponse.getJSONArray("rides")
                        if(rides.length() > 0)
                        {
                            for (j in 0 until rides.length())
                            {
                                val ride_id = rides.getJSONObject(j).getString("ride_ref")
                                val ride_status = rides.getJSONObject(j).getString("ride_status")
                                val display_status = rides.getJSONObject(j).getString("ride_status")
                                val units = rides.getJSONObject(j).getString("currency")
                                val category = rides.getJSONObject(j).getString("service_type")
                                val ride_distance=rides.getJSONObject(j).getString("ride_distance")

                                var  pickuplocation = ""
                                var  pickup_short = ""
                                var  pickup_date_time = ""
                                var  pickup_time = ""

                                var  droplocation = ""
                                var  drop_short = ""
                                var  timestamp = ""
                                var  drop_date_time = ""
                                var  drop_time = ""

                                val booking_information = rides.getJSONObject(j).getJSONArray("locations")
                                for (k in 0 until booking_information.length())
                                {
                                    if(booking_information.getJSONObject(k).getString("ref").equals("pickup"))
                                    {
                                        pickuplocation = booking_information.getJSONObject(k).getString("location")
                                        pickup_short = pickuplocation
                                        timestamp = ""
                                        pickup_date_time = timestamp
                                        pickup_time = booking_information.getJSONObject(k).getString("time")
                                    }
                                    else if(booking_information.getJSONObject(k).getString("ref").equals("drop"))
                                    {
                                        droplocation = booking_information.getJSONObject(k).getString("location")
                                        drop_short = droplocation
                                        timestamp = ""
                                        drop_date_time = timestamp
                                        drop_time = booking_information.getJSONObject(k).getString("time")
                                    }

                                }

                                val booking_date_time = rides.getJSONObject(j).getString("ride_date")+" "+ rides.getJSONObject(j).getString("ride_time")

                                val booking_time = rides.getJSONObject(j).getString("timestamp")
                                val booking_time_stamp = rides.getJSONObject(j).getString("timestamp")











                                var reason:String=""
                                if(ride_status.equals("Cancelled"))
                                {
                                    reason="Sorry ride cancelled"
                                }




                                val summary = rides.getJSONObject(j).getString("fare_details")
                                val fare_summary = rides.getJSONObject(j).getString("fare_details")


                                val driver_name = rides.getJSONObject(j).getString("user_name")
                                val driver_image = rides.getJSONObject(j).getString("user_image")
                                val vehile_maker_model = rides.getJSONObject(j).getString("service_type")
                                val vehicle_number = ""
                                val avg_review = rides.getJSONObject(j).getString("user_review")

                                val invoice_src = rides.getJSONObject(j).getString("invoice_src")
                                val payment_method = rides.getJSONObject(j).getString("driver_revenue")
                                val grand_fare = rides.getJSONObject(j).getString("user_pay_amount")
                                val payment_typeuser = rides.getJSONObject(j).getString("payment_method")

                                val results = realm.where(tripdetailsrealmm::class.java).equalTo("ride_id", ride_id).findAll()
                                var ridesize:Int=results.size
                                if(ridesize == 0)
                                {
                                    realm.beginTransaction()
                                    val tripdetailsrealmm = realm.createObject(tripdetailsrealmm::class.java)
                                    tripdetailsrealmm.timestampid = booking_time_stamp
                                    tripdetailsrealmm.ride_id = ride_id
                                    tripdetailsrealmm.ride_status = ride_status
                                    tripdetailsrealmm.display_status = display_status
                                    tripdetailsrealmm.units = units
                                    tripdetailsrealmm.category = category
                                    tripdetailsrealmm.pickuplocation = pickuplocation
                                    tripdetailsrealmm.pickup_short = pickup_short
                                    tripdetailsrealmm.droplocation = droplocation
                                    tripdetailsrealmm.drop_short = drop_short
                                    tripdetailsrealmm.booking_date_time = booking_date_time
                                    tripdetailsrealmm.booking_time = booking_time
                                    tripdetailsrealmm.pickup_date_time = pickup_date_time
                                    tripdetailsrealmm.pickup_time = pickup_time
                                    tripdetailsrealmm.drop_date_time = drop_date_time
                                    tripdetailsrealmm.drop_time = drop_time
                                    tripdetailsrealmm.cancelled_date_time = ""
                                    tripdetailsrealmm.cancelled_time = ""
                                    tripdetailsrealmm.summary = summary
                                    tripdetailsrealmm.fare_summary = fare_summary

                                    tripdetailsrealmm.payment_typeuser = payment_typeuser

                                    tripdetailsrealmm.driver_name = driver_name
                                    tripdetailsrealmm.driver_image = driver_image
                                    tripdetailsrealmm.vehile_maker_model = vehile_maker_model
                                    tripdetailsrealmm.vehicle_number = vehicle_number
                                    tripdetailsrealmm.avg_review = avg_review

                                    tripdetailsrealmm.invoice_src = invoice_src
                                    tripdetailsrealmm.payment_method = payment_method
                                    tripdetailsrealmm.grand_fare = grand_fare
                                    tripdetailsrealmm.cancelreason = reason
                                    tripdetailsrealmm.ride_distance =ride_distance

                                    realm.commitTransaction()
                                }
                                currencySymbolConventer = CurrencySymbolConventer()
                                var currency_symbol = currencySymbolConventer.getCurrencySymbol(units)
                                countryarraylist.add(tripdetailsModel(booking_date_time, booking_time,currency_symbol+grand_fare,category,payment_method,display_status,invoice_src,ride_status,ride_id,booking_date_time,booking_time_stamp))
                            }
                        }
                        else
                        {
                            listener!!.stoploadinglast("1")
                        }

                        if (listener != null)
                        {
                            listener!!.secondsplit("1")
                            listener!!.responseLiveDataSecond(countryarraylist)
                        }
                    }
                    else
                    {
                        listener!!.stoploadinglast("1")
                    }
                }
            }
            else
            {
                listener!!.stoploadinglast("1")
            }
        }
        catch (e: JSONException)
        {
        }
    }
    fun splitfirstresponse(mContext: Context,respnse:String)
    {
        try
        {
            var responseObj = JSONObject(respnse)
            Log.d("dgdfyet746", responseObj.toString())

            if (responseObj.getString("status").equals("1"))
            {
                if (responseObj.has("response"))
                {
                    var getingres: String = responseObj.getString("response")
                    var resultsresponse = JSONObject(getingres)
                    var ridescheck: String = resultsresponse.getString("rides")
                    if(!ridescheck.equals("{}"))
                    {
                        var rides= resultsresponse.getJSONArray("rides")
                        for (j in 0 until rides.length())
                        {
                            val ride_id = rides.getJSONObject(j).getString("ride_ref")
                            val ride_status = rides.getJSONObject(j).getString("ride_status")
                            val display_status = rides.getJSONObject(j).getString("ride_status")
                            val units = rides.getJSONObject(j).getString("currency")
                            val category = rides.getJSONObject(j).getString("service_type")
                            val ride_distance=rides.getJSONObject(j).getString("ride_distance")
                            var  pickuplocation = ""
                            var  pickup_short = ""
                            var  pickup_date_time = ""
                            var  pickup_time = ""

                            var  droplocation = ""
                            var  drop_short = ""
                            var  timestamppickup = ""
                            var  timestampdrop = ""

                            var  drop_date_time = ""
                            var  drop_time = ""
                            var pick_lat =""
                            var pick_long =""
                            var drop_lat =""
                            var drop_long =""

                            val booking_information = rides.getJSONObject(j).getJSONArray("locations")
                            for (k in 0 until booking_information.length())
                            {
                                if(booking_information.getJSONObject(k).getString("ref").equals("pickup"))
                                {
                                    pickuplocation = booking_information.getJSONObject(k).getString("location")
                                    pickup_short = pickuplocation
                                    timestamppickup =booking_information.getJSONObject(k).getString("timestamp")
                                    pick_lat=booking_information.getJSONObject(k).getString("lat")
                                    pick_long=booking_information.getJSONObject(k).getString("lon")

                                    Log.d("fw4dd5345",timestamppickup)

                                    pickup_time =booking_information.getJSONObject(k).getString("time")
//                                    pickup_date_time = timestamppickup
//                                    pickup_time = timestamppickup
                                }
                                else if(booking_information.getJSONObject(k).getString("ref").equals("drop"))
                                {
                                    droplocation = booking_information.getJSONObject(k).getString("location")
                                    timestampdrop =booking_information.getJSONObject(k).getString("timestamp")
                                    drop_lat=booking_information.getJSONObject(k).getString("lat")
                                    drop_long=booking_information.getJSONObject(k).getString("lon")
                                    Log.d("fw46tg5345",timestampdrop)
                                    drop_short = droplocation
                                    drop_time = booking_information.getJSONObject(k).getString("time")
//                                    drop_date_time = timestampdrop
//                                    drop_time = timestampdrop
                                }

                            }

                            val booking_date_time = rides.getJSONObject(j).getString("ride_date")+" "+ rides.getJSONObject(j).getString("ride_time")

                            val booking_time = rides.getJSONObject(j).getString("timestamp")
                            val booking_time_stamp = rides.getJSONObject(j).getString("timestamp")






                            var reason:String=""
                            if(ride_status.equals("Cancelled"))
                            {
                                reason="Sorry ride cancelled"
                            }









                            val summary = rides.getJSONObject(j).getString("fare_details")
                            val fare_summary = rides.getJSONObject(j).getString("fare_details")
                            val driver_name = rides.getJSONObject(j).getString("user_name")
                            val driver_image = rides.getJSONObject(j).getString("user_image")
                            val vehile_maker_model = rides.getJSONObject(j).getString("service_type")
                            val vehicle_number = ""
                            val avg_review = rides.getJSONObject(j).getString("user_review")

                            val invoice_src = rides.getJSONObject(j).getString("invoice_src")
                            val payment_method = rides.getJSONObject(j).getString("driver_revenue")
                            val grand_fare = rides.getJSONObject(j).getString("user_pay_amount")

                            val payment_typeuser = rides.getJSONObject(j).getString("payment_method")

                            val results = realm.where(tripdetailsrealmm::class.java).equalTo("ride_id", ride_id).findAll()
                            var ridesize:Int=results.size
                            if(ridesize == 0)
                            {
                                realm.beginTransaction()
                                val tripdetailsrealmm = realm.createObject(tripdetailsrealmm::class.java)
                                tripdetailsrealmm.timestampid = booking_time_stamp
                                tripdetailsrealmm.ride_id = ride_id
                                tripdetailsrealmm.ride_status = ride_status
                                tripdetailsrealmm.display_status = display_status
                                tripdetailsrealmm.units = units
                                tripdetailsrealmm.category = category
                                tripdetailsrealmm.pickuplocation = pickuplocation
                                tripdetailsrealmm.pickup_short = pickup_short
                                tripdetailsrealmm.droplocation = droplocation
                                tripdetailsrealmm.drop_short = drop_short
                                tripdetailsrealmm.booking_date_time = booking_date_time
                                tripdetailsrealmm.booking_time = booking_time
                                tripdetailsrealmm.pickup_date_time = pickup_date_time
                                tripdetailsrealmm.pickup_time = pickup_time
                                tripdetailsrealmm.drop_date_time = drop_date_time
                                tripdetailsrealmm.drop_time = drop_time
                                tripdetailsrealmm.cancelled_date_time = ""
                                tripdetailsrealmm.cancelled_time = ""
                                tripdetailsrealmm.summary = summary
                                tripdetailsrealmm.fare_summary = fare_summary
                                tripdetailsrealmm.payment_typeuser = payment_typeuser
                                tripdetailsrealmm.driver_name = driver_name
                                tripdetailsrealmm.driver_image = driver_image
                                tripdetailsrealmm.vehile_maker_model = vehile_maker_model
                                tripdetailsrealmm.vehicle_number = vehicle_number
                                tripdetailsrealmm.avg_review = avg_review

                                tripdetailsrealmm.invoice_src = invoice_src
                                tripdetailsrealmm.payment_method = payment_method
                                tripdetailsrealmm.grand_fare = grand_fare
                                tripdetailsrealmm.timestamppickup =timestamppickup
                                tripdetailsrealmm.timestampdrop =timestampdrop
                                tripdetailsrealmm.cancelreason =reason
                                tripdetailsrealmm.pick_lat =pick_lat
                                tripdetailsrealmm.pick_long =pick_long
                                tripdetailsrealmm.drop_lat =drop_lat
                                tripdetailsrealmm.drop_long =drop_long
                                tripdetailsrealmm.ride_distance =ride_distance


                                realm.commitTransaction()
                            }
                        }
                        if (listener != null)
                            listener!!.firstsplit("1")
                    }

                }
            }
        }
        catch (e: JSONException)
        {
        }
    }
    fun getdatafromrealmrecord(mContext: Context)
    {
        var countryarraylist = ArrayList<tripdetailsModel>()
        val results = realm.where(tripdetailsrealmm::class.java).sort("timestampid", Sort.DESCENDING).findAll()
        for (recentfields in results)
        {
            var booking_date_time:String=recentfields.booking_date_time.toString()
            var booking_time:String=recentfields.booking_time.toString()
            var grand_fare:String=recentfields.grand_fare.toString()
            var category:String=recentfields.category.toString()
            var payment_method:String=recentfields.payment_method.toString()
            var display_status:String=recentfields.display_status.toString()
            var ride_status:String=recentfields.ride_status.toString()
            var timestampid:String=recentfields.timestampid.toString()
            var units:String=recentfields.units.toString()
            val currency=units
            currencySymbolConventer = CurrencySymbolConventer()
            var currency_symbol = currencySymbolConventer.getCurrencySymbol(currency)
            var date:String=""
            date = booking_date_time
            var invoice_src:String=recentfields.invoice_src.toString()
            var ride_id:String=recentfields.ride_id.toString()
            var vehile_maker_model:String=""
            vehile_maker_model = recentfields.category.toString()


            countryarraylist.add(tripdetailsModel(date, booking_time,currency_symbol+grand_fare,category,payment_method,display_status,invoice_src,ride_status,ride_id,booking_date_time,timestampid))
        }
        if (listener != null)
            listener!!.responseLiveData(countryarraylist)
    }
    fun getdatafromrealmrecordfirst(mContext: Context)
    {
        var countryarraylist = ArrayList<tripdetailsModel>()
        val results = realm.where(tripdetailsrealmm::class.java).sort("timestampid", Sort.DESCENDING).findAll()
        for (recentfields in results)
        {
            var booking_date_time:String=recentfields.booking_date_time.toString()
            var booking_time:String=recentfields.booking_time.toString()
            var grand_fare:String=recentfields.grand_fare.toString()
            var category:String=recentfields.category.toString()
            var payment_method:String=recentfields.payment_method.toString()
            var display_status:String=recentfields.display_status.toString()
            var ride_status:String=recentfields.ride_status.toString()
            var units:String=recentfields.units.toString()
            var timestampid:String=recentfields.timestampid.toString()
            val currency = units
            currencySymbolConventer = CurrencySymbolConventer()
            var currency_symbol = currencySymbolConventer.getCurrencySymbol(currency)
            var date:String=""
            date = booking_date_time
            var invoice_src:String=recentfields.invoice_src.toString()
            var ride_id:String=recentfields.ride_id.toString()
            var vehile_maker_model:String=""
            vehile_maker_model = recentfields.category.toString()
            countryarraylist.add(tripdetailsModel(date, booking_time,currency_symbol+grand_fare,category+vehile_maker_model,payment_method,display_status,invoice_src,ride_status,ride_id,booking_date_time,timestampid))
        }
        if (listener != null)
            listener!!.responseLiveDataFirst(countryarraylist)
    }

}