package com.mauto.chd.view_model_tracking_page

import android.content.Context
import android.util.Log
import com.mauto.chd.R
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.commonutils.CurrencySymbolConventer
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

import kotlin.collections.ArrayList


class MyearningsRepostiatry(private val listener: OtpRidePageListener)
{

    companion object{
        var arrayList : ArrayList<String> = ArrayList()
        var arrayListvalue : ArrayList<String> = ArrayList()
        lateinit var farearray : JSONArray
    }
    // Method for getting the minimum value
    fun getdrivertails(response:String,context:Context)
    {
        try
        {
            var vehicledetailsarraylist = ArrayList<TrackingPageDataModel>()
            val stoplist = ArrayList<stopsModel>()
            var responseObj = JSONObject(response)
            if (responseObj.getString("status").equals("1"))
            {
                if (responseObj.has("response"))
                {

                    var user_id:String = ""
                    var user_name:String = ""
                    var user_email:String = ""
                    var phone_number:String = ""
                    var user_image:String = ""
                    var user_review:String = ""
                    var ride_id:String = ""
                    var pickup_lat:String = ""
                    var pickup_lon:String = ""
                    var pickup_time:String = ""
                    var drop_location:String = ""
                    var drop_lat:String = ""
                    var drop_lon:String = ""
                    var pickup_location:String = ""
                    var drop_loc:String = ""
                    var est_amount:String=""
                    var currency_code:String=""
                    var currency:String = "0"
                    var payment_method:String=""
                    var total_payable_amount:String = "0"
                    var receive_cash="0"

                    var getingres: JSONObject? = responseObj.getJSONObject("response")
                    Log.d("dfjdhfjhjs", getingres.toString())
                    val data = getingres!!.getJSONObject("data")

                    if (data.has("list")) {
                        val list = data.getJSONArray("list")
                        if (list.length() > 0) {
                            for (i in 0 until list.length()) {
                                var j_ride_objectss = list.getJSONObject(i)

                                user_id = j_ride_objectss!!.getString("user_id")

                                Log.d("muser_ids",user_id)
                                user_name = j_ride_objectss!!.getString("user_name")
                                user_email = ""
                                phone_number = j_ride_objectss!!.getString("user_phone")
                                user_image = j_ride_objectss!!.getString("user_image")
                                user_review = j_ride_objectss!!.getString("user_review")
                                ride_id = j_ride_objectss!!.getString("ride_id")
                                total_payable_amount = j_ride_objectss.getString("user_pay_amount")



                                if (j_ride_objectss.has("user_token")) {
                                    var fcmtoken: String = j_ride_objectss!!.getString("user_token")
                                    var mSessionManager: SessionManager? = null
                                    mSessionManager = SessionManager(context!!)
                                    mSessionManager.setUsertripfcmtoken(fcmtoken)
                                }


                                if (j_ride_objectss.has("currency")) {
                                    currency = j_ride_objectss.getString("currency")
                                    var currencySymbolConventer: CurrencySymbolConventer
                                    currencySymbolConventer = CurrencySymbolConventer()
                                    currency_code = currencySymbolConventer.getCurrencySymbol(currency)
                                }
                                if (j_ride_objectss.has("receive_cash")) {
                                    if (j_ride_objectss.getString("receive_cash").equals("1"))
                                        payment_method = context.getString(R.string.cashpay)

                                    receive_cash = j_ride_objectss.getString("receive_cash")
                                }
                                arrayListvalue.clear()
                                arrayList.clear()
                                farearray = j_ride_objectss!!.getJSONArray("fare_details")
                                if (farearray!=null){
                                    if (farearray.length() > 0){
                                        for (k in 0 until farearray.length()){
                                            var j_fare_object = farearray.getJSONObject(k)
                                            arrayList.add(j_fare_object.getString("title"))
                                            arrayListvalue.add(j_fare_object.getString("value"))
                                        }

                                    }
                                }




                                val jarray = j_ride_objectss!!.getJSONArray("locations")
                                if (jarray.length() > 0) {
                                    for (i in 0 until jarray.length()) {
                                        var j_ride_object = jarray.getJSONObject(i)
                                        if (j_ride_object.getString("ref").equals("pickup")) {
                                            pickup_location = j_ride_object.getString("location")
                                            pickup_lat = j_ride_object.getString("lat")
                                            pickup_lon = j_ride_object.getString("lon")
                                            pickup_time = j_ride_object.getString("timestamp")
                                        } else if (j_ride_object.getString("ref").equals("drop")) {
                                            drop_location = j_ride_object.getString("location")
                                            drop_loc = j_ride_object.getString("location")
                                            drop_lat = j_ride_object.getString("lat")
                                            drop_lon = j_ride_object.getString("lon")
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
                if (listener != null)
                {
                    listener!!.onDataReceived(vehicledetailsarraylist)
//                    listener!!.onStopsAdapter(stoplist)
                }
            }
        }
        catch (e: JSONException)
        {
        }
    }


    fun getcashresponse(response:String,context:Context)
    {
        try
        {
            var responseObj = JSONObject(response)
            Log.d("cehhffss",response)
            if (responseObj.getString("status").equals("1"))
            {
                if (listener != null)
                {
                    var mSessionManager: SessionManager? = null
                    mSessionManager = SessionManager(context!!)
                    try
                    {
                        mSessionManager!!.settridestatusforcash()
                        mSessionManager!!.settridestatusforcashtwo()

                        listener!!.oncashrecived(1)
                    }
                    catch (e: Exception)
                    {
                    }
                }
            }
            else
            {
                if (listener != null)
                {
                    listener!!.oncashrecived(0)
                }
            }
        }
        catch (e: JSONException)
        {
        }
    }


    fun savepickupdroplatitude(pickup_lat:String,pickup_lon:String,drop_lat:String,drop_lon:String,ride_id:String,context:Context)
    {
        var mSessionManager: SessionManager? = null
        mSessionManager = SessionManager(context!!)
        mSessionManager.setRidepickuplatitude(pickup_lat)
        mSessionManager.setRidepickuplongitude(pickup_lon)
        mSessionManager.setRidedroplatitiude(drop_lat)
        mSessionManager.setRidedroplongitude(drop_lon)
        mSessionManager.setRideid(ride_id)
    }
}
