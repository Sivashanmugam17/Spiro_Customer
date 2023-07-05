package com.mauto.chd.backgroundservices


import android.app.Activity
import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.mauto.chd.R
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.retrofit.RetrofitInstance
import com.mindorks.kotnetworking.KotNetworking
import com.mindorks.kotnetworking.common.Priority
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject
import java.util.*


class ridedetailsapicall : IntentService("ApiHit")
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

        header.put("driver_id", mSessionManager!!.getDriverId())
        header.put("ride_id", mSessionManager!!.getdutyrideid())

        // url to hit
        urltohit = RetrofitInstance.ridedetailss

        // common network call
        acknwoldegementcallhit(header)
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
                        Log.d("cnxcncnx",responsetosend)
                        EventBus.getDefault().post(IntentServiceResult(Activity.RESULT_OK, responsetosend!!, getString(R.string.ridedetails)))
                    }
                    else
                    {
                        var response:String = response.toString()
                        Log.d("zdvfhbhxcxc",response)

                        var responseObj = JSONObject(response)
                        val status = responseObj.getString("status")
                        if (status.equals("1")) {
                            if (responseObj.has("response")) {
                                var getingres: JSONObject? = responseObj.getJSONObject("response")
                                var mSessionManager: SessionManager? = null
                                mSessionManager = SessionManager(applicationContext!!)
                                val data = getingres!!.getJSONObject("data")
                                if (data != null) {
                                    val ride_status = data.getString("ride_status")
                                    val ride_id=data.getString("ride_id")
                                    val user_id=data.getString("user_id")
                                    Log.d("msusermsss",user_id)

                                    mSessionManager.setuser_id(user_id)
                                    mSessionManager.onridedetails(response, ride_status, "1")
                                    mSessionManager.setRideid(ride_id)

                                    EventBus.getDefault().post(IntentServiceResult(Activity.RESULT_OK, response, getString(
                                        R.string.ridedetails)))
                                }
                            } else {
                                EventBus.getDefault().post(IntentServiceResult(Activity.RESULT_OK, "0", getString(R.string.ridedetails)))
                            }
                        }else{
                            EventBus.getDefault().post(IntentServiceResult(Activity.RESULT_OK, "0", getString(R.string.ridedetails)))
                        }
                    }
                }
    }






}