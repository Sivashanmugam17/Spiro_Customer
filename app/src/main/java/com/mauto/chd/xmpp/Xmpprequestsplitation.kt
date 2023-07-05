package com.mauto.chd.xmpp

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mauto.chd.R
import com.mauto.chd.backgroundservices.chathandlingintentservice
import com.mauto.chd.backgroundservices.duplicaterideidservice
import com.mauto.chd.backgroundservices.walletbalanceservice
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.SessionManagerPackage.SessionManager
//import com.cabilyhandyforalldinedoo.chd.ui.MainPage.walletbalancenotify
import org.greenrobot.eventbus.EventBus
//import org.jivesoftware.smack.packet.Message
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.text.SimpleDateFormat
import java.util.*


class Xmpprequestsplitation(private var context: Context) {

    init
    {
        context = context;
    }

//    fun onHandleChatMessage(message: Message)
//    {
//        val data = message.body
//        if (!data.startsWith("{")){
//            val  bgg =   decodeBase64(data)
//            println("--------------bgg------------"+bgg)
//            val response = JSONObject(bgg)
//            var action = response.getString("action")
//            if (action.equals("collect_cash")){
//                EventBus.getDefault().post(IntentServiceResult(Activity.RESULT_OK, "", "collect_cash"))
//
//            }
//            if (action.equals("fleet_immobilized")){
//                Log.d("checkin25225g","popup")
////                var mSessionManagers: SessionManager?
////                mSessionManagers = SessionManager(context)
////                mSessionManagers.setmfleet_immobilized("fleet_immobilized")
//                ridenotificationnew()
////                EventBus.getDefault().post(IntentServiceResult(Activity.RESULT_OK, "", "fleet_immobilized"))
//
//            }
//
//            if (action.equals("rate_user")){
//                Log.d("asdhhdfhf","adsfkkdfc")
//                EventBus.getDefault().post(IntentServiceResult(Activity.RESULT_OK, "", "rate_user"))
//
//            }
//
//            if(bgg!!.contains("dectar_chat"))
//            {
//                val response = JSONObject(bgg)
//                var action = response.getString("action")
//                if(action.equals("dectar_chat")) {
//                    chatnotification(response)
//                }
//            }
//            else  if(data.contains("xmppactivecheck"))
//            {
//                println("------>I am active")
//            }
//            else
//            {
//                val decodeValue: ByteArray = Base64.decode(data, Base64.DEFAULT)
//                val response = JSONObject(String(decodeValue))
//                println("-----------bgg---1"+response)
//                var mSessionManager: SessionManager?
//                mSessionManager = SessionManager(context)
//                if(response.has("action"))
//                {
//                    var action = response.getString("action")
//                    Log.d("mmaction",action)
//                    if (action.equals("ride_completed")){
//                        mSessionManager.setmride_completed("ride_completed")
//                        mSessionManager.setnormalride_completed("ride_completed")
//                    }
//                    if(action.equals("ride_cancelled"))
//                        cancelnotification(response)
//                    else if(action.equals("ride_request")) {
//                        Log.e("riderequest", "---riderequest");
//                        if(mSessionManager.hasSession() && !mSessionManager.getOnlineAvailability().equals("No") && !mSessionManager.ridehasSession())
//                            riderequestpart(response)
//                    }
//                    else if(action.equals("dectar_chat"))
//                        chatnotification(response)
//                }
//            }
//
//        }else if (data.startsWith("{")){
//            val chat_object = JSONObject(data)
//            val action = chat_object.getString("action")
//            if (action=="dectar_chat"){
//                chatnotification(chat_object)
//            }
//        }else{
//
//            println("------>I am active")
//
//        }
//
//
//
//    }
    private fun decodeBase64(coded: String): String? {
        var valueDecoded: ByteArray? = ByteArray(0)
        try {
            valueDecoded = Base64.decode(coded.toByteArray(charset("UTF-8")), Base64.DEFAULT)
        } catch (e: UnsupportedEncodingException) {
        }
        return String(valueDecoded!!)
    }
    private fun riderequestpart(response: JSONObject)
    {
        if(response.has("action"))
        {
            var action = response.getString("action")
            var ride_id:String = ""
            var time_stamp:String = ""
            var ackid:String = ""
            var timer:String = ""
            var ratting:String = ""
            var duration:String = ""
            var distance:String = ""
            var pickup_lat:String = ""
            var pickup_lon:String = ""
            var pickup:String = ""
            var category:String =""
            if(action.equals("ride_request"))
            {
                var servertimestamp=""
                if(response.has("timestamp"))
                {
                    servertimestamp = response.getString("timestamp")
                    var cal: Calendar = Calendar.getInstance(Locale.ENGLISH);
                    cal.setTimeInMillis(servertimestamp.toLong() * 1000);
                    var date:String  = DateFormat.format("dd-MM-yyyy HH:mm:ss", cal).toString()
                    Log.e("serverdatewithtime", date)
                    var dateFormat1 =  SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    var servermobieldateformat = dateFormat1.parse(date)

                    val tsLong = System.currentTimeMillis() / 1000
                    var cals: Calendar = Calendar.getInstance(Locale.ENGLISH);
                    cals.setTimeInMillis(tsLong * 1000);
                    var mobiledate:String  = DateFormat.format("dd-MM-yyyy HH:mm:ss", cals).toString()
                    Log.e("mobiledatewithtime", mobiledate)

                    var dateFormat =  SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    var mobiledatedateformat = dateFormat.parse(mobiledate)
                    var validrequest=printDifference(mobiledatedateformat, servermobieldateformat)
                    if(validrequest)
                    {
                        ride_id = response.getString("ride_id")
                        time_stamp = response.getString("timestamp")
                        ackid = response.getString("ack_id")
                        timer = response.getString("timer")

                        if(response.has("duration"))
                            duration = response.getString("duration")

                        if(response.has("category"))
                            category = response.getString("category")

                        distance = response.getString("distance")
                        pickup_lat = response.getString("pickup_lat")
                        pickup_lon = response.getString("pickup_lon")
                        if (response.has("user_ratting")){
                            ratting = response.getString("user_ratting")
                        }
                        pickup = response.getString("pickup")
                        ridenotification(ride_id, time_stamp, ackid, timer, ratting, duration, distance, pickup_lat, pickup_lon, pickup, category)
                    }
                }
            }
        }
    }
    private fun cancelnotification(response: JSONObject)
    {
        if(response.has("action"))
        {
            var rideid:String = response.getString("ride_id")
            cleartripbasedonrideid(rideid)
        }
    }
    private fun chatnotification(response: JSONObject)
    {
        if(response.has("action")) {
            var desc:String = response.getString("desc")
            var sender_ID:String = response.getString("sender_ID")
            var timestamp:String = response.getString("time_stamp")
            var ride_id:String = response.getString("ride_id")
            if(laststack() >= 1)
            {
                if(!ride_id.equals("") && ride_id.length > 2)
                {
                    if(!isMyServiceRunning(context, chathandlingintentservice::class.java))
                    {
                        val serviceClass = chathandlingintentservice::class.java
                        val intent1 = Intent(context, serviceClass)
                        intent1.putExtra("desc", desc)
                        intent1.putExtra("sender_ID", sender_ID)
                        intent1.putExtra("timestamp", timestamp)
                        intent1.putExtra("ride_id", ride_id)
                        context.startService(intent1)
                    }
                }
            }

        }
    }


    private fun ridenotificationnew() {
        val serviceClass = walletbalanceservice::class.java
        val intent1 = Intent(context, serviceClass)
        context.startService(intent1)
    }

    private fun ridenotification(ride_id: String, time_stamp: String, category_name: String, timer: String, ratting: String, duration: String, distance: String, pickup_lat: String, pickup_lon: String, pickup: String, category: String)
    {
        if(laststack() >= 1)
        {
            if(!ride_id.equals("") && ride_id.length > 2)
            {
                if(!isMyServiceRunning(context, duplicaterideidservice::class.java))
                {
                    val serviceClass = duplicaterideidservice::class.java
                    val intent1 = Intent(context, serviceClass)
                    intent1.putExtra("ride_id", ride_id)
                    intent1.putExtra("time_stamp", time_stamp)
                    intent1.putExtra("category_name", category_name)
                    intent1.putExtra("timer", timer)
                    intent1.putExtra("ratting", ratting)
                    intent1.putExtra("duration", duration)
                    intent1.putExtra("distance", distance)
                    intent1.putExtra("pickup_lat", pickup_lat)
                    intent1.putExtra("pickup_lon", pickup_lon)
                    intent1.putExtra("pickup", pickup)
                    intent1.putExtra("category", category)
                    context.startService(intent1)
                }

            }
        }
    }
    private fun laststack(): Int
    {
        var returuncount = 0
        val mngr = context.getSystemService(AppCompatActivity.ACTIVITY_SERVICE) as ActivityManager
        val taskList = mngr.getRunningTasks(10)
        returuncount = taskList[0].numActivities
        return returuncount
    }
    fun cleartripbasedonrideid(rideid: String)
    {
        try
        {
            var mSessionManager: SessionManager?
            mSessionManager = SessionManager(context)
            var responseObj = JSONObject(mSessionManager!!.gettripdetails())
            if (responseObj.getString("status").equals("1"))
            {
                if (responseObj.has("response"))
                {
                    var getingres: JSONObject? = responseObj.getJSONObject("response")
                    Log.d("stefe55rt", getingres.toString())

                    val dataobj = `getingres`!!.getJSONObject("data")
                    var ride_id = dataobj.getString("ride_id")
                    if(ride_id.equals(rideid))
                    {
                        Log.d("stert","sgtdstgtgsr")
                        mSessionManager!!.clearridedetails()
                        EventBus.getDefault().post(IntentServiceResult(Activity.RESULT_OK, ride_id, context.getString(
                            R.string.fcmcancel)))
                    }
                }

            }
        }
        catch (e: JSONException)
        {
        }
    }
    fun printDifference(startDate: Date, endDate: Date):Boolean
    {
        var validornt:Boolean = false
        var different:Long = endDate.getTime() - startDate.getTime()
        var secondsInMilli:Long = 1000
        var minutesInMilli = secondsInMilli * 60
        var hoursInMilli = minutesInMilli * 60
        var daysInMilli = hoursInMilli * 24
        var elapsedDays = different / daysInMilli;
        different = different % daysInMilli;
        var elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;
        var elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;
        var elapsedSeconds = different / secondsInMilli;
        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
        if(elapsedDays.toInt() == 0 && elapsedHours.toInt() == 0)
        {
            if(elapsedMinutes.toInt() in 10 downTo -10)
                validornt= true;
        }
        else
            validornt= false

        return validornt
    }




    fun isMyServiceRunning(context: Context, serviceClass: Class<*>): Boolean
    {
        var result = false
        val manager = context?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                result = true
                break
            } else {
                result = false
            }
        }
        return result
    }
}