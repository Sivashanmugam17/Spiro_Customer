package com.mauto.chd.view_model_tracking_page

import android.content.Context
import android.location.Location
import android.util.Log
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.commonutils.AppUtils
import com.mauto.chd.commonutils.CurrencySymbolConventer
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline
import com.google.maps.android.PolyUtil
import com.mauto.chd.R
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class Trackingpagerepostiatry(private val listener: TrackingPageListener) {

    companion object{
        var arrayList : ArrayList<String> = ArrayList()
        var arrayListvalue : ArrayList<String> = ArrayList()
       lateinit var farearray : JSONArray
    }
    //for getting driver details
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
                    val data = getingres!!.getJSONObject("data")
                    Log.d("fhdfggdf", data.toString())
                    if (data.has("list")) {
                        val list = data.getJSONArray("list")
                        if (list.length() > 0) {
                            for (i in 0 until list.length()) {
                                var j_ride_objectss = list.getJSONObject(i)

                                user_id = j_ride_objectss!!.getString("user_id")
                                user_name = j_ride_objectss!!.getString("user_name")
                                user_email = ""
                                phone_number = j_ride_objectss!!.getString("user_phone")
                                user_image = j_ride_objectss!!.getString("user_image")
                                user_review = j_ride_objectss!!.getString("user_review")
                                ride_id = j_ride_objectss!!.getString("ride_id")
                                total_payable_amount = j_ride_objectss.getString("user_pay_amount")
                                payment_method=j_ride_objectss.getString("payment_method")


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
                                var mSessionManager: SessionManager? = null
                                mSessionManager = SessionManager(context!!)
                                var ridestatus = mSessionManager!!.getdriverstatus()
                                Log.d("ridestatus2344",ridestatus);

                                savepickupdroplatitude(pickup_lat, pickup_lon, drop_lat, drop_lon, ride_id, context)
                                stoplist.add(stopsModel(1, pickup_location, ""))
                                stoplist.add(stopsModel(2, drop_loc, ""))
                                vehicledetailsarraylist.add(TrackingPageDataModel(user_id, user_name, user_email, phone_number, user_image, user_review, ride_id, pickup_location, pickup_lat, pickup_lon, pickup_time, drop_location, drop_loc, drop_lat, drop_lon, est_amount, currency_code, payment_method, ridestatus, total_payable_amount, receive_cash))

                            }
                        }
                    }else {
                        if (data != null) {
                            user_id = data!!.getString("user_id")
                            user_name = data!!.getString("user_name")
                            user_email = ""
                            phone_number = data!!.getString("user_phone")
                            user_image = data!!.getString("user_image")
                            user_review = data!!.getString("user_review")
                            ride_id = data!!.getString("ride_id")
                            total_payable_amount = data.getString("user_pay_amount")



                            if (data.has("user_token")) {
                                var fcmtoken: String = data!!.getString("user_token")
                                var mSessionManager: SessionManager? = null
                                mSessionManager = SessionManager(context!!)
                                mSessionManager.setUsertripfcmtoken(fcmtoken)
                            }


                            if (data.has("currency")) {
                                currency = data.getString("currency")
                                var currencySymbolConventer: CurrencySymbolConventer
                                currencySymbolConventer = CurrencySymbolConventer()
                                currency_code = currencySymbolConventer.getCurrencySymbol(currency)
                            }
                            if (data.has("receive_cash")) {
                                if (data.getString("receive_cash").equals("1"))
                                    payment_method = context.getString(R.string.cashpay)

                                receive_cash = data.getString("receive_cash")
                            }


                            val jarray = `data`!!.getJSONArray("locations")
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
                            var mSessionManager: SessionManager? = null
                            mSessionManager = SessionManager(context!!)
                            var ridestatus = mSessionManager!!.getdriverstatus()
                            Log.d("fy45ggrgfd",ridestatus);
                            savepickupdroplatitude(pickup_lat, pickup_lon, drop_lat, drop_lon, ride_id, context)
                            stoplist.add(stopsModel(1, pickup_location, ""))
                            stoplist.add(stopsModel(2, drop_loc, ""))
                            vehicledetailsarraylist.add(TrackingPageDataModel(user_id, user_name, user_email, phone_number, user_image, user_review, ride_id, pickup_location, pickup_lat, pickup_lon, pickup_time, drop_location, drop_loc, drop_lat, drop_lon, est_amount, currency_code, payment_method, ridestatus, total_payable_amount, receive_cash))

                        }
                    }
                }
                if (listener != null)
                {
                    listener!!.onDataReceived(vehicledetailsarraylist)
                    listener!!.onStopsAdapter(stoplist)
                }
            }
        }
        catch (e: JSONException)
        {
        }
    }









    fun getdrivertailarrived(response:String,context:Context)
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
                    val data = getingres!!.getJSONObject("data")
                    val list=data.getJSONArray("list")
                    if (list.length() > 0) {
                        for (i in 0 until list.length()) {
                            var j_ride_objectss = list.getJSONObject(i)

                            user_id = j_ride_objectss!!.getString("user_id")
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
                            var mSessionManager: SessionManager? = null
                            mSessionManager = SessionManager(context!!)
                            var ridestatus = mSessionManager!!.getdriverstatus()
                            savepickupdroplatitude(pickup_lat, pickup_lon, drop_lat, drop_lon, ride_id, context)
                            stoplist.add(stopsModel(1, pickup_location, ""))
                            stoplist.add(stopsModel(2, drop_loc, ""))
                            vehicledetailsarraylist.add(TrackingPageDataModel(user_id, user_name, user_email, phone_number, user_image, user_review, ride_id, pickup_location, pickup_lat, pickup_lon, pickup_time, drop_location, drop_loc, drop_lat, drop_lon, est_amount, currency_code, payment_method, ridestatus, total_payable_amount, receive_cash))

                        }
                    }
                }
                if (listener != null)
                {
                    listener!!.onDataReceived(vehicledetailsarraylist)
                    listener!!.onStopsAdapter(stoplist)
                }
            }
        }
        catch (e: JSONException)
        {
        }
    }



    //for getting cancellation reason
    fun getcancellationreason(response:String,context:Context)
    {
        try
        {
            var CancellationReason = ArrayList<CancellationReasonDataModel>()

            var responseObj = JSONObject(response)
            if (responseObj.getString("status").equals("1"))
            {
                if (responseObj.has("response"))
                {
                    var getingres: JSONObject? = responseObj.getJSONObject("response")
                    val jarray = `getingres`!!.getJSONArray("reason")


                    var cancel_charge:String =getingres.getString("cancel_charge")
                    var currency_code:String = getingres.getString("currency_code")
                    var cancellation_amount:String = getingres.getString("cancellation_amount")

                    if(cancel_charge.equals("Yes"))
                    {
                        var currencySymbolConventer: CurrencySymbolConventer
                        currencySymbolConventer = CurrencySymbolConventer()
                        currency_code = currencySymbolConventer.getCurrencySymbol(currency_code)
                    }
                    if (jarray.length() > 0)
                    {
                        for (i in 0 until jarray.length())
                        {
                            var j_ride_object = jarray.getJSONObject(i)
                            var id: String       = j_ride_object.getString("id")
                            var reason     = j_ride_object.getString("reason")

                            CancellationReason.add(CancellationReasonDataModel(id,reason,cancel_charge,currency_code,cancellation_amount))

                        }
                    }

                }
                if (listener != null)
                {
                    listener!!.onCancelDataReceived(CancellationReason)
                }
            }
            else
            {
                listener!!.onTripCancelled(0)
            }
        }
        catch (e: JSONException)
        {
        }
    }


    //for getting cash response
    fun getcashresponse(response:String,context:Context)
    {
        try
        {
            var responseObj = JSONObject(response)
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


    //for getting cancellation reason
    fun getsplitresponse(response:String,context:Context)
    {
        try
        {
            var responseObj = JSONObject(response)
            if (responseObj.getString("status").equals("1"))
            {
                if (responseObj.has("response"))
                {
                    listener!!.onTripCancelled(1)
                }
            }
            else
                listener!!.onTripCancelled(0)
             }
        catch (e: JSONException)
        {
        }
    }


    fun etacalculation(context:Context, start_latlng: LatLng, end_latlng: LatLng)
    {
        var list: ArrayList<String>? = null
        list = ArrayList()

        val startLocation = Location("start_location")
        val endLocation = Location("end_location")

        startLocation.latitude = start_latlng.latitude
        startLocation.longitude = start_latlng.longitude
        endLocation.latitude = end_latlng.latitude
        endLocation.longitude = end_latlng.longitude

        var  distanceInKm = startLocation.distanceTo(endLocation) / 1000
        val speedInKms = 20.0 / 60.0

        val est_mins = (distanceInKm / speedInKms).toInt()
        val estimatedDriveTimeInSec = (distanceInKm / speedInKms) * 60.0

        val estimated_time = estimatedTimeToReach(estimatedDriveTimeInSec)



        if (listener != null)
        {
            if(distanceInKm < 1)
            {
                distanceInKm =  distanceInKm * 1000
                val roundmetters = Math.round(distanceInKm).toInt()
                listener!!.estDistance(roundmetters.toString()+" "+context.getString(R.string.meters))
            }
            else
            {
                var dest_diste= Math.round(distanceInKm * 100).toFloat() / 100
                Log.d("efdsrtret", dest_diste.toString())
//                distanceInKm = String.format("%.2f", distanceInKm).toFloat()
                listener!!.estDistance(dest_diste.toString()+" "+context.getString(R.string.kms))
            }
            if(est_mins.toString().equals("0"))
            listener!!.estTime(context.getString(R.string.nearing))
            else
            listener!!.estTime(est_mins.toString()+" "+context.getString(R.string.minn))

            listener!!.setonhourd(estimated_time)


        }
        // list.add(estimated_time)
        //list.add(est_mins.toString())


    }



    private fun estimatedTimeToReach(secs: Double): String
    {
        val sdf = SimpleDateFormat("hh:mm a")
        val currentDateandTime = sdf.format(Date())
        val date = sdf.parse(currentDateandTime)
        val calendar = Calendar.getInstance()
        calendar.setTime(date)
        calendar.add(Calendar.SECOND, secs.toInt())
        return sdf.format(calendar.time)
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

    fun checkdriverisonpathornot(mContext:Context, foregroundPolyline: Polyline, reroutingsuccessorfailed:Int, latLng:LatLng)
    {
        if(AppUtils.isNetworkConnecteddfromservice(mContext))
        {
            try
            {
                var tolerance: Double = 150.0 // meters
                var isLocationOnPath = PolyUtil.isLocationOnPath(latLng, foregroundPolyline.getPoints(), true, tolerance);
                if (!isLocationOnPath && reroutingsuccessorfailed == 0)
                    listener!!.onReRoutingenabled(1)
            }
            catch (io:java.lang.Exception)
            {
            }
        }
    }


}