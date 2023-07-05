package com.mauto.chd.backgroundservices


import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.retrofit.RetrofitInstance
//import com.cabilyhandyforalldinedoo.chd.ui.MainPage.riderequestpage
import com.mindorks.kotnetworking.KotNetworking
import com.mindorks.kotnetworking.common.Priority
import java.util.*


class acknowledgeapi : IntentService("ApiHit")
{
    var header = HashMap<String, String>()
    var urltohit: String = ""
    lateinit private var mSessionManager: SessionManager

    override fun onBind(intent: Intent): IBinder?
    {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onHandleIntent(intent: Intent?)
    {
        mSessionManager = SessionManager(applicationContext!!)
        if (intent?.hasExtra("ride_id")!!)
        {
            var ack = intent?.getStringExtra("ack")
            var ride_id = intent?.getStringExtra("ride_id")
            var ack_id = intent?.getStringExtra("ack_id")

            header.put("driver_id",mSessionManager!!.getDriverId())
            header.put("ride_id",ride_id!!)
            header.put("ack_id",ack_id!!)
            header.put("action",ack!!)

            header.put("driver_lat",mSessionManager!!.getOnlineLatitiude())
            header.put("driver_lon",mSessionManager!!.getOnlineLongitude())


            if(ack.equals("2"))
            {
                // url to  Denied ack hit
                urltohit = RetrofitInstance.acknowledgecallhit
            }
            else  if(ack.equals("0"))
            {
                // url to received ack hit
                urltohit = RetrofitInstance.acknowledgecallhit
            }


            // common network call
            acknwoldegementcallhit(header)
        }
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
                        var response:String = response.toString()
                        Log.d("on ack", response)
                    }
                    else
                    {
                        var response:String = response.toString()
                        Log.d("on ack", response)
                    }
                }
    }


}