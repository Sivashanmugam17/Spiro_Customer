package com.mauto.chd.backgroundservices


import android.app.Activity
import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import com.mauto.chd.R
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.retrofit.RetrofitInstance
import com.mindorks.kotnetworking.KotNetworking
import com.mindorks.kotnetworking.common.Priority
import org.greenrobot.eventbus.EventBus
import java.util.*


class skiprating : IntentService("ApiHit")
{
    var responsetosend: String = "failed"
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
            var ride_id = intent?.getStringExtra("ride_id")
            var skipstatus = intent?.getStringExtra("skipstatus")
            var rating = intent?.getStringExtra("rating")

            header.put("driver_id",mSessionManager!!.getDriverId())
            header.put("ride_id",ride_id!!)
            if(skipstatus.equals("0"))
            {
                /*header.put("skip_by","driver")
                // url to hit
                urltohit = RetrofitInstance.skiprideid*/
                header.put("rating","5")
                header.put("review", "")
                // url to hit
                urltohit = RetrofitInstance.submitrating
            }
            else
            {
                header.put("rating",rating!!)
                header.put("review", "")
                // url to hit
                urltohit = RetrofitInstance.submitrating
            }
            // common network call
            applytaing(header)
        }
    }

    // common api fetch service
    private fun applytaing(header: HashMap<String, String>)
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
                        EventBus.getDefault().post(IntentServiceResult(Activity.RESULT_OK, responsetosend!!, getString(R.string.skiprating)))
                    }
                    else
                    {
                        var response:String = response.toString()
                        responsetosend = response
                        EventBus.getDefault().post(IntentServiceResult(Activity.RESULT_OK, responsetosend!!, getString(R.string.skiprating)))
                    }
                }
    }
}