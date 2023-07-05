package com.mauto.chd.fcm
/*
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import cabily.handyforall.dinedoo.R
import com.cabilyhandyforalldinedoo.chd.Backgroundservices.chathandlingintentservice
import com.cabilyhandyforalldinedoo.chd.Backgroundservices.duplicaterideidservice
import com.cabilyhandyforalldinedoo.chd.EventBusConnection.IntentServiceResult
import com.cabilyhandyforalldinedoo.chd.SessionManagerPackage.SessionManager
//import com.cabilyhandyforalldinedoo.chd.ui.MainPage.adjusclockdateandtime
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.greenrobot.eventbus.EventBus
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class MyFirebaseMessagingService: FirebaseMessagingService()
{
    override fun onNewToken(token: String?)
    {
        super.onNewToken(token)
        saveFcmId(token)
    }
    private fun saveFcmId(token: String?)
    {
        var mSessionManager: SessionManager?
        mSessionManager = SessionManager(applicationContext)
        mSessionManager.setfcmkey(token!!)
    }
    override fun onMessageReceived(remoteMessage: RemoteMessage?)
    {
        Log.d("checking","messagefmpc")
        super.onMessageReceived(remoteMessage)
        val response = JSONObject(remoteMessage?.data!!.toMap())
        var mSessionManager: SessionManager?
        mSessionManager = SessionManager(applicationContext)
        if(response.has("action"))
        {
            var action = response.getString("action")
            if(action.equals("ride_cancelled"))
                cancelnotification(response)
            else if(action.equals("ride_request"))
            {
                Log.e("riderequest","---riderequest");
                if(mSessionManager.hasSession() == true && !mSessionManager.getOnlineAvailability().equals("No") && !mSessionManager.ridehasSession() == true)
                    riderequestpart(response)
            }
            else if(action.equals("dectar_chat"))
                   chatnotification(response)
        }
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
            if(action.equals("ride_request"))
            {
                var servertimestamp=""
                if(response.has("key10"))
                {
                     servertimestamp = response.getString("key10")
                     var cal:Calendar = Calendar.getInstance(Locale.ENGLISH);
                     cal.setTimeInMillis(servertimestamp.toLong() * 1000);
                     var date:String  = DateFormat.format("dd-MM-yyyy HH:mm:ss", cal).toString()
                     Log.e("serverdatewithtime",date)
                    var dateFormat1 =  SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    var servermobieldateformat = dateFormat1.parse(date)

                    val tsLong = System.currentTimeMillis() / 1000
                    var cals:Calendar = Calendar.getInstance(Locale.ENGLISH);
                    cals.setTimeInMillis(tsLong * 1000);
                    var mobiledate:String  = DateFormat.format("dd-MM-yyyy HH:mm:ss", cals).toString()
                    Log.e("mobiledatewithtime",mobiledate)

                    var dateFormat =  SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    var mobiledatedateformat = dateFormat.parse(mobiledate)
                    var validrequest=printDifference(mobiledatedateformat,servermobieldateformat)
                    if(validrequest)
                    {
                        ride_id = response.getString("key1")
                        time_stamp = response.getString("key10")
                        ackid = response.getString("key11")
                        timer = response.getString("key2")

                        if(response.has("key7"))
                            duration = response.getString("key7")

                        distance = response.getString("key6")
                        pickup_lat = "13.0418"
                        pickup_lon = "80.2341"
                        ratting = "0.0"
                        pickup = response.getString("key3")
                        ridenotification(ride_id,time_stamp,ackid,timer,ratting,duration,distance,pickup_lat,pickup_lon,pickup)
                    }
                    else
                    {
                        if(laststack() >= 1)
                        {
                           */
/* val intent1 = Intent(applicationContext, adjusclockdateandtime::class.java)
                            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent1)*//*

                        }
                    }
                }
            }
        }
    }
    private fun cancelnotification(response: JSONObject)
    {
        if(response.has("action"))
        {
            var rideid:String = response.getString("key1")
            cleartripbasedonrideid(rideid)
        }
    }
    private fun chatnotification(response: JSONObject)
    {
        if(response.has("action"))
        {
            var desc:String = response.getString("desc")
            var sender_ID:String = response.getString("sender_ID")
            var timestamp:String = response.getString("timestamp")
            var ride_id:String = response.getString("ride_id")
            if(laststack() >= 1)
            {
                if(!ride_id.equals("") && ride_id.length > 2)
                {
                    if(!isMyServiceRunning(applicationContext,chathandlingintentservice::class.java))
                    {
                        val serviceClass = chathandlingintentservice::class.java
                        val intent1 = Intent(applicationContext, serviceClass)
                        intent1.putExtra("desc", desc)
                        intent1.putExtra("sender_ID", sender_ID)
                        intent1.putExtra("timestamp", timestamp)
                        intent1.putExtra("ride_id", ride_id)
                        applicationContext.startService(intent1)
                    }
                }
            }

        }
    }
    private fun ridenotification(ride_id: String,time_stamp: String,category_name: String,timer: String,ratting: String,duration: String,distance: String,pickup_lat: String,pickup_lon: String,pickup:String)
    {
        if(laststack() >= 1)
        {
            if(!ride_id.equals("") && ride_id.length > 2)
            {
                if(!isMyServiceRunning(applicationContext,duplicaterideidservice::class.java))
                {
                    val serviceClass = duplicaterideidservice::class.java
                    val intent1 = Intent(applicationContext, serviceClass)
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
                    applicationContext.startService(intent1)
                }
            }
        }
    }
    private fun laststack(): Int
    {
        var returuncount = 0
        val mngr = this@MyFirebaseMessagingService.getSystemService(AppCompatActivity.ACTIVITY_SERVICE) as ActivityManager
        val taskList = mngr.getRunningTasks(10)
        returuncount = taskList[0].numActivities
        return returuncount
    }
    fun cleartripbasedonrideid(rideid:String)
    {
        try
        {
            var mSessionManager: SessionManager?
            mSessionManager = SessionManager(applicationContext)
            var responseObj = JSONObject(mSessionManager!!.gettripdetails())
            if (responseObj.getString("status").equals("1"))
            {
                if (responseObj.has("response"))
                {
                        var getingres: JSONObject? = responseObj.getJSONObject("response")
                        val jarray = `getingres`!!.getJSONArray("rides")
                        if (jarray.length() > 0)
                        {
                            for (i in 0 until jarray.length())
                            {
                                var j_ride_object = jarray.getJSONObject(i)
                                var ride_id       = j_ride_object.getString("ride_id")
                                if(ride_id.equals(rideid))
                                {
                                    mSessionManager!!.clearridedetails()
                                    EventBus.getDefault().post(IntentServiceResult(Activity.RESULT_OK, ride_id, getString(R.string.fcmcancel)))
                                }
                            }
                        }
                }

            }
        }
        catch (e: JSONException)
        {
        }
    }
    fun printDifference(startDate:Date,endDate:Date):Boolean
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
*/
