package com.mauto.chd.backgroundservices


import android.app.Activity
import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.commonutils.AppUtils
import com.mauto.chd.retrofit.RetrofitInstance
import com.google.android.gms.maps.model.LatLng
import com.mauto.chd.R
import com.mindorks.kotnetworking.KotNetworking
import com.mindorks.kotnetworking.common.Priority
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject
import java.util.*


class updatelocationtoserverapi : IntentService("ApiHit")
{


    lateinit private var mSessionManager: SessionManager
    var api_name: String = ""
    lateinit var waypoints_array: ArrayList<LatLng>
    private var stops_location_array: ArrayList<String>? = null
    var header = HashMap<String, String>()
    var urltohit: String = ""





    override fun onBind(intent: Intent): IBinder?
    {
        throw UnsupportedOperationException("Not yet implemented")
    }
    override fun onHandleIntent(intent: Intent?)
    {


        waypoints_array = ArrayList()
        stops_location_array = ArrayList()
        mSessionManager = SessionManager(applicationContext!!)
        if (intent?.hasExtra(getString(R.string.intent_putextra_api_key))!!)
        {
            api_name = intent?.getStringExtra(getString(R.string.intent_putextra_api_key))!!
            if (api_name.equals(getString(R.string.onlinestatuysupdate)))
            {
                var onlinelatitude = intent?.getStringExtra("onlinelatitude")
                var onlinelongitude = intent?.getStringExtra("onlinelongitude")
                var onlinebearing = intent?.getStringExtra("onlinebearing")
                Log.d("dasresffwrw5rf",onlinelatitude+" "+onlinelatitude)

                header.put("driver_id",mSessionManager!!.getDriverId())
                header.put("driver_lat",onlinelatitude!!)
                header.put("driver_lon",onlinelongitude!!)
                header.put("bearing",onlinebearing!!)
                header.put("ride_id","")

                Log.d("mheader", header.toString())
                // url to hit
                urltohit = RetrofitInstance.updatedriverlat

                // common network call
                if (AppUtils.isNetworkConnecteddfromservice(applicationContext))
                    commonapihit(header,api_name)

            }
        }
    }

    // common api fetch service
    private fun commonapihit(header: HashMap<String, String>,api_name:String)
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
                  Log.e("sucess",
                          "update--"+response.toString())
              }
              else
              {
                  Log.e("failure",
                          "update--"+response.toString())
                  checkhasrideid(response.toString()!!)
              }
              }
    }


    // saving auth key to session
    fun checkhasrideid(response:String)
    {
        try
        {
            val response_json_object = JSONObject(response)
            val status = response_json_object.getString("status")
            val response_object = `response_json_object`.getJSONObject("response")
            if (status.equals("1"))
            {
                val verify_status = response_object.getString("verify_status")
                if(verify_status.equals("No") || verify_status.equals("no"))
                {
                    EventBus.getDefault().post(IntentServiceResult(Activity.RESULT_OK, verify_status, getString(R.string.docpendingstage)))
                }
            }
        }
        catch (e: Exception)
        {
        }
    }




}