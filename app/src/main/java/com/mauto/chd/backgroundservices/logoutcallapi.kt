package com.mauto.chd.backgroundservices


import android.app.Activity
import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import com.mauto.chd.R
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.retrofit.RetrofitInstance
//import com.cabilyhandyforalldinedoo.chd.ui.MainPage.riderequestpage
import com.mindorks.kotnetworking.KotNetworking
import com.mindorks.kotnetworking.common.Priority
import org.greenrobot.eventbus.EventBus
import java.util.*


class logoutcallapi : IntentService("ApiHit")
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
        header.put("driver_id",mSessionManager!!.getDriverId())
        header.put("device","ANDROID")
        // url to hit
        urltohit = RetrofitInstance.logoutcall
        // common network call
        logoutcallhit(header,mSessionManager!!.getApiHeader())
        mSessionManager = SessionManager(applicationContext!!)
        mSessionManager!!.clearalldata()
        EventBus.getDefault().post(IntentServiceResult(Activity.RESULT_OK, "1", applicationContext.getString(
            R.string.logoutcallapi)))
    }



    // common api fetch service
    private fun logoutcallhit(header: HashMap<String, String>,headertag:HashMap<String, String>)
    {
        KotNetworking.post(urltohit)
                .addBodyParameter(header)
                .addHeaders(headertag)
                .setTag(this)
                .setPriority(Priority.HIGH)
                .build()
                .getAsString { response, error ->
                    if (error != null)
                    {
                        var response:String = response.toString()
                    }
                    else
                    {
                        var response:String = response.toString()
                    }
                }
    }


}