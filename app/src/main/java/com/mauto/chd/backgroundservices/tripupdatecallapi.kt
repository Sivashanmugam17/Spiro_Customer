package com.mauto.chd.backgroundservices


import android.app.Activity
import android.app.IntentService
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.IBinder
import android.util.Log
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.clickableInterface.TripIntentServiceResult
import com.mauto.chd.data.onridelatandlong.onrideDbHelper
import com.mauto.chd.retrofit.RetrofitInstance
import com.google.gson.JsonArray
import com.mauto.chd.R
import com.mindorks.kotnetworking.KotNetworking
import com.mindorks.kotnetworking.common.Priority
import org.greenrobot.eventbus.EventBus
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


class tripupdatecallapi : IntentService("ApiHit")
{

    var ridesttatus =""
    var responsetosend: String = "failed"
    var header = HashMap<String, String>()
    var urltohit: String = ""
    lateinit private var mSessionManager: SessionManager
    var useridtosend:String = ""
    var ride_id:String = ""

    override fun onBind(intent: Intent): IBinder?
    {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onHandleIntent(intent: Intent?)
    {

        mSessionManager = SessionManager(applicationContext!!)
         ride_id = mSessionManager.gerrideid()

         ridesttatus = mSessionManager.getdriverstatus()
        Log.d("dgfvgfrtr4",ridesttatus)


        header.put("driver_id",mSessionManager!!.getDriverId())
        header.put("ride_id",ride_id)
        header.put("driver_lat", mSessionManager!!.getOnlineLatitiude())
        header.put("driver_lon", mSessionManager!!.getOnlineLongitude())

        var droplat = intent?.getStringExtra("droplat")
        var droplong = intent?.getStringExtra("droplong")

        useridtosend= intent!!.getStringExtra("useridtosend")!!
        if (ridesttatus.equals("Confirmed")){
            ridesttatus="1"
        }else if (ridesttatus.equals("Arrived")){
            ridesttatus="2"
        }else if (ridesttatus.equals("Onride")){
            ridesttatus="3"
        }else if (ridesttatus.equals("Finished")){
            ridesttatus="4"
        }
        if(ridesttatus.equals("1"))
        {
            // url to hit
            urltohit = RetrofitInstance.tripupdatearrived
            acknwoldegementcallhit(header)

        }
        else if(ridesttatus.equals("2"))
        {
            // url to hit
            urltohit = RetrofitInstance.begintripurl
            acknwoldegementcallhit(header)

        }
        else if(ridesttatus.equals("3"))
        {
//            val travel_history: ArrayList<String>
            val travel_history: ArrayList<ArrayList<String>>

            travel_history=getallrecordforparticularrideid(ride_id)
            Log.d("xsfxzvg34", travel_history.toString())

//            val builder = StringBuilder()
//            for (string in travel_history) {
//                builder.append(",$string")
//            }

            header.put("travel_history", travel_history.toString())
            Log.d("msjhshhsgcc", header.toString())
            // url to hit
            urltohit = RetrofitInstance.endtripurl
            endtripcallhit(header)
        }else if (ridesttatus.equals("4")){
            EventBus.getDefault().post("Trip Finished")
        }

        // common network call
    }

    // common api fetch service
    private fun acknwoldegementcallhit(header: HashMap<String, String>)
    {
        KotNetworking.post(urltohit)
                .addBodyParameter(header)
                .addHeaders(mSessionManager!!.getApiHeader())
                .setTag(this)
                .setPriority(Priority.HIGH)
                .build()
                .getAsString { response, error ->
                    if (error != null)
                    {
                        EventBus.getDefault().post(IntentServiceResult(Activity.RESULT_OK, responsetosend!!, getString(
                            R.string.tripupdate)))
                        Log.d("responsetosendsf",responsetosend)


                    }
                    else
                    {
                        var response:String = response.toString()
                        responsetosend = response
                        Log.d("fgkfjgnjt5ty6",responsetosend)
                        try
                        {
                            var responseObj = JSONObject(response)
                            if (responseObj.getString("status").equals("1"))
                            {
                                Log.d("vfxgdfh", responseObj.toString())

                                if (responseObj.has("response"))
                                {
                                    var getingres: JSONObject? = responseObj.getJSONObject("response")
                                    var mSessionManager: SessionManager? = null
                                    mSessionManager = SessionManager(applicationContext!!)


                                    val data = getingres!!.getJSONObject("data")

                                    if (data!=null)
                                    {
                                        val list=data.getJSONArray("list")
                                        for (i in 0 until list.length())
                                        {
                                            var j_ride_object = list.getJSONObject(i)
                                            var ridestatuss:String  = j_ride_object!!.getString("ride_status")
                                            Log.d("dfstw4gdsf",ridestatuss)
                                            mSessionManager.setridestatustwo(ridestatuss)
                                            mSessionManager .onridedetails(response,ridestatuss,"1")



                                            if(j_ride_object.has("user_token"))
                                            {
                                                var fcmtoken:String  = j_ride_object!!.getString("user_token")
                                                fcmsending(fcmtoken)
                                            }
                                            emittingthroughxmpp()
                                        }
                                    }
                                       }
                            }
                            else if (responseObj.getString("status").equals("0"))
                            {
                                EventBus.getDefault().post(IntentServiceResult(Activity.RESULT_OK, responsetosend!!, "canceltri"))

                            }
                        }
                        catch (e: JSONException)
                        {
                        }

                    }
                }
    }



    private fun endtripcallhit(header: HashMap<String, String>)
    {
        KotNetworking.post(urltohit)
                .addBodyParameter(header)
                .addHeaders(mSessionManager!!.getApiHeader())
                .setTag(this)
                .setPriority(Priority.HIGH)
                .build()
                .getAsString { response, error ->
                    if (error != null)
                    {
                        EventBus.getDefault().post(IntentServiceResult(Activity.RESULT_OK, responsetosend!!, getString(
                            R.string.tripupdate)))
                        Log.d("responsetosendsf",responsetosend)


                    }
                    else
                    {
                        var response:String = response.toString()
                        responsetosend = response
                        Log.d("fgkfjgnjt5ty6",responsetosend)
                        try
                        {
                            var responseObj = JSONObject(response)
                            if (responseObj.getString("status").equals("1"))
                            {
                                Log.d("vfxgdfh", responseObj.toString())

                                if (responseObj.has("response"))
                                {
                                    var getingres: JSONObject? = responseObj.getJSONObject("response")
                                    var mSessionManager: SessionManager? = null
                                    mSessionManager = SessionManager(applicationContext!!)
                                    val waiting  = getingres!!.getInt("waiting")
                                    if (waiting==1){
                                        Log.d("checkkinh","cheoose")
                                        EventBus.getDefault().post(IntentServiceResult(Activity.RESULT_OK, "", "rate_user"))

                                    }

                                    val data = getingres!!.getJSONObject("data")

                                    if (data!=null)
                                    {
                                        val list=data.getJSONArray("list")
                                        for (i in 0 until list.length())
                                        {
                                            var j_ride_object = list.getJSONObject(i)
                                            var ridestatuss:String  = j_ride_object!!.getString("ride_status")
                                            Log.d("dfstw4gdsf",ridestatuss)
                                            mSessionManager.setridestatustwo(ridestatuss)
                                            mSessionManager .onridedetails(response,ridestatuss,"1")



                                            if(j_ride_object.has("user_token"))
                                            {
                                                var fcmtoken:String  = j_ride_object!!.getString("user_token")
                                                fcmsending(fcmtoken)
                                            }
                                            emittingthroughxmpp()
                                        }
                                    }
                                }
                            }
                            else if (responseObj.getString("status").equals("0"))
                            {
                                EventBus.getDefault().post(IntentServiceResult(Activity.RESULT_OK, responsetosend!!, "canceltri"))

                            }
                        }
                        catch (e: JSONException)
                        {
                        }

                    }
                }
    }



    fun fcmsending(fcmtoken:String)
    {
        val registerpagetwopage = Intent(applicationContext, fcmsendingprocess::class.java)
        registerpagetwopage.putExtra("rideid", ride_id)
        registerpagetwopage.putExtra("ridestatus", "")
        registerpagetwopage.putExtra("useridtosend", useridtosend)
        if(ridesttatus.equals("1"))
        {
            registerpagetwopage.putExtra("actions", "cab_arrived")
        }
        else if(ridesttatus.equals("2"))
        {
            registerpagetwopage.putExtra("actions", "trip_begin")
        }
        else if(ridesttatus.equals("3"))
        {
            registerpagetwopage.putExtra("actions", "make_payment")
        }
        registerpagetwopage.putExtra("messages", "")
        registerpagetwopage.putExtra("fcmtoken", fcmtoken)
        startService(registerpagetwopage)
    }


    fun getallrecordforparticularrideid(ride_id:String):ArrayList<ArrayList<String>>
    {
        val endDataTrips = ArrayList<ArrayList<String>>()
        val endDataTrips1 = JsonArray()
        val endDataTripspick = ArrayList<String>()
        val endDataTripsdrop = ArrayList<String>()


        var mHelper: onrideDbHelper? = null
        var dataBase: SQLiteDatabase? = null
        mHelper= onrideDbHelper(applicationContext)
        dataBase = mHelper!!.getWritableDatabase()
        println("data from db"+dataBase)
        val curser = mHelper.getLatLongFornormalRide()
        if (curser.moveToFirst()){
            do {
                println("Data from latlng end trip"+curser)
                var ride_id= curser.getString(curser.getColumnIndex(onrideDbHelper.KEY_ID))
                var latitude= curser.getString(curser.getColumnIndex(onrideDbHelper.latitude))
                var longitude= curser.getString(curser.getColumnIndex(onrideDbHelper.longitude))
                var time= curser.getString(curser.getColumnIndex(onrideDbHelper.time))
                val jsonArray = ArrayList<String>()
                var mlatitude=""+"\""+latitude+"\""
                var mlongitude=""+"\""+longitude+"\""
                var mtime=""+"\""+time+"\""

                println("Uniq ID "+ride_id)

                jsonArray.add(mlatitude + "," + mlongitude + "," + mtime)
                endDataTrips.add(jsonArray)
            }while (curser.moveToNext())
        }
        println("array data"+endDataTrips)

//        var mCursor: Cursor = dataBase!!.rawQuery("SELECT * FROM " + onrideDbHelper.TABLE_NAME+" WHERE rideid='"+ride_id+"' ORDER BY id ASC", null);
//        if (mCursor.moveToFirst()) {
//            do
//            {
//                var latitude= mCursor.getString(mCursor.getColumnIndex(onrideDbHelper.latitude))
//                var longitude= mCursor.getString(mCursor.getColumnIndex(onrideDbHelper.longitude))
//
//                var time= mCursor.getString(mCursor.getColumnIndex(onrideDbHelper.time))
////                endDataTrips.add(latitude + ";" + longitude + ";" + time)
//
//
//            } while (mCursor.moveToNext());
//        }
//        mCursor.close()
//        val aCalendars = Calendar.getInstance()
//        val aDateFormats = SimpleDateFormat("yyyy-MM-dd")
//        val aCurrentDates = aDateFormats.format(aCalendars.getTime())
//        val aCurrentDatem = mSessionManager!!.getpickpcurrenttimeotp()
//        endDataTripspick.add(mSessionManager!!.getonlinelatitudeotp())
//        endDataTripspick.add(mSessionManager!!.getonlinelongitudeotp())
//        endDataTripspick.add(aCurrentDatem)
//        val aCalendar = Calendar.getInstance()
//        val aDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//        val aCurrentDate = aDateFormat.format(aCalendar.getTime())
//        endDataTripsdrop.add(mSessionManager!!.getTrackingLat())
//        endDataTripsdrop.add(mSessionManager!!.getTrackingLong())
//        endDataTripsdrop.add(aCurrentDate)
//        endDataTrips.add(endDataTripspick.toString())
//        endDataTrips.add(endDataTripsdrop.toString())


//        endDataTrips.add(mSessionManager!!.getTrackingLat() + ";" + mSessionManager!!.getTrackingLong() + ";" + aCurrentDate)
        return endDataTrips
    }


//    fun getallrecordforparticularrideid(ride_id:String): ArrayList<String>
//    {
//        val endDataTrips = ArrayList<String>()
//        val endDataTripspick = ArrayList<String>()
//        val endDataTripsdrop = ArrayList<String>()
//
//        var mHelper: onrideDbHelper? = null
//        var dataBase: SQLiteDatabase? = null
//        mHelper= onrideDbHelper(applicationContext)
//        dataBase = mHelper!!.getWritableDatabase();
//        var mCursor: Cursor = dataBase!!.rawQuery("SELECT * FROM " + onrideDbHelper.TABLE_NAME+" WHERE rideid='"+ride_id+"' ORDER BY id ASC", null);
//        if (mCursor.moveToFirst()) {
//            do
//            {
//                var latitude= mCursor.getString(mCursor.getColumnIndex(onrideDbHelper.latitude))
//                var longitude= mCursor.getString(mCursor.getColumnIndex(onrideDbHelper.longitude))
//
//                var time= mCursor.getString(mCursor.getColumnIndex(onrideDbHelper.time))
////                endDataTrips.add(latitude + ";" + longitude + ";" + time)
//                endDataTripspick.add(latitude)
//                endDataTripspick.add(longitude)
//                endDataTripspick.add(time)
//
//            } while (mCursor.moveToNext());
//        }
//        mCursor.close()
//        val aCalendar = Calendar.getInstance()
//        val aDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//        val aCurrentDate = aDateFormat.format(aCalendar.getTime())
//        endDataTripsdrop.add(mSessionManager!!.getTrackingLat())
//        endDataTripsdrop.add(mSessionManager!!.getTrackingLong())
//        endDataTripsdrop.add(aCurrentDate)
//        endDataTrips.add(endDataTripspick.toString())
//        endDataTrips.add(endDataTripsdrop.toString())
//
//
////        endDataTrips.add(mSessionManager!!.getTrackingLat() + ";" + mSessionManager!!.getTrackingLong() + ";" + aCurrentDate)
//        return endDataTrips
//    }


    fun emittingthroughxmpp()
    {
        var actions = ""
        if(ridesttatus.equals("1"))
            actions = "cab_arrived"
        else if(ridesttatus.equals("2"))
            actions ="trip_begin"
        else if(ridesttatus.equals("3"))
            actions ="make_payment"

        val job = JSONObject()
        job.put("action", actions)
        job.put("rideid", ride_id)
        EventBus.getDefault().post(TripIntentServiceResult(job.toString(),useridtosend+"@"+mSessionManager!!.getxmpp_host_name()))
        EventBus.getDefault().post(IntentServiceResult(Activity.RESULT_OK, responsetosend!!, getString(R.string.tripupdate)))
    }
}