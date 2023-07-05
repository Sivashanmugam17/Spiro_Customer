package com.mauto.chd.backgroundservices


import android.app.Activity
import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.mauto.chd.R
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.clickableInterface.TripIntentServiceResult
import com.mauto.chd.retrofit.RetrofitInstance
//import com.cabilyhandyforalldinedoo.chd.ui.MainPage.requestmianscreen
import com.mindorks.kotnetworking.KotNetworking
import com.mindorks.kotnetworking.common.Priority
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject
import java.util.*


class acceptcallapi : IntentService("ApiHit")
{


    var responsetosend: String = "failed"
    var header = HashMap<String, String>()
    var urltohit: String = ""
    lateinit private var mSessionManager: SessionManager
    var ride_id:String =""
    override fun onBind(intent: Intent): IBinder?
    {
        throw UnsupportedOperationException("Not yet implemented")
    }
    override fun onHandleIntent(intent: Intent?)
    {
        mSessionManager = SessionManager(applicationContext!!)
        if (intent?.hasExtra("ride_id")!!)
        {
            ride_id = intent?.getStringExtra("ride_id")!!
            var ack_id = intent?.getStringExtra("ack_id")
            header.put("driver_id",mSessionManager!!.getDriverId())
            header.put("action","1")
            header.put("ack_id", ack_id!!)
            header.put("ride_id",ride_id)
            header.put("driver_lat",mSessionManager!!.getOnlineLatitiude())
            header.put("driver_lon",mSessionManager!!.getOnlineLongitude())
            // url to hit
            urltohit = RetrofitInstance.acknowledgecallhit
            // common network call
            acknwoldegementcallhit(header)
        }
    }

    // common api fetch service
    private fun acknwoldegementcallhit(header: HashMap<String, String>)
    {
        Log.e("Params......",header.toString())
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
                            R.string.acceptcall)))
                    }
                    else
                    {
                        var response:String = response.toString()
                        responsetosend = response
                        var responseObj = JSONObject(responsetosend)
                        if (responseObj.getString("status").equals("1"))
                        {
                            if (responseObj.has("response"))
                            {
                                mSessionManager.setDutyrideid(ride_id)
                                EventBus.getDefault().post(IntentServiceResult(Activity.RESULT_OK, responsetosend!!, getString(R.string.acceptcall)))
                                mSessionManager.onridedetails(responsetosend,"1","0")
                                var getingres: JSONObject? = responseObj.getJSONObject("response")
                                val data = getingres!!.getJSONObject("data")
                                val list = data!!.getJSONArray("list")
                                if (list.length() > 0)
                                {
                                    for (i in 0 until list.length())
                                    {
                                        var j_ride_object = list.getJSONObject(i)
                                        if(j_ride_object.has("begin_otp")){
                                            var begin_otp:String  = j_ride_object!!.getString("begin_otp")
                                            mSessionManager.setbegin_otp(begin_otp)
                                        }
                                        if(j_ride_object.has("user_token"))
                                            fcmsending(j_ride_object!!.getString("user_token"),ride_id)
                                        if(j_ride_object.has("user_id"))
                                            emittingthroughxmpp(j_ride_object!!.getString("user_id"))
                                    }
                                }
                                navigatetoridepage()
                            }
                        }
                    }
                }
    }
    fun fcmsending(fcmtoken:String,ride_id:String)
    {
        val registerpagetwopage = Intent(applicationContext, fcmsendingprocess::class.java)
        registerpagetwopage.putExtra("rideid", ride_id)
        registerpagetwopage.putExtra("ridestatus", "")
        registerpagetwopage.putExtra("actions", "ride_confirmed")
        registerpagetwopage.putExtra("messages", "")
        registerpagetwopage.putExtra("fcmtoken", fcmtoken)
        registerpagetwopage.putExtra("useridtosend", "")
        startService(registerpagetwopage)
    }
    fun navigatetoridepage()
    {
//        val intent_otppage = Intent(applicationContext, requestmianscreen::class.java)
//        intent_otppage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        intent_otppage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        startActivity(intent_otppage)

    }
    fun emittingthroughxmpp(user_id:String)
    {
        var actions = "ride_confirmed"
        val job = JSONObject()
        job.put("action", actions)
        job.put("rideid", ride_id)
        EventBus.getDefault().post(TripIntentServiceResult(job.toString(),user_id+"@"+mSessionManager!!.getxmpp_host_name()))
   }
}