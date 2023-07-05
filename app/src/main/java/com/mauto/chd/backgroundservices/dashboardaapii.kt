package com.mauto.chd.backgroundservices


import android.app.Activity
import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.retrofit.RetrofitInstance
import com.google.android.gms.maps.model.LatLng
import com.mauto.chd.R
import com.mindorks.kotnetworking.KotNetworking
import com.mindorks.kotnetworking.common.Priority
import okhttp3.ResponseBody
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import java.util.*


class dashboardaapii : IntentService("ApiHit")
{
    lateinit private var mSessionManager: SessionManager
    private var apiCall: Call<ResponseBody>? = null
    var responsetosend: String = "failed"
    var api_name: String = ""
    lateinit var waypoints_array: ArrayList<LatLng>
    private var stops_location_array: ArrayList<String>? = null

    var header = HashMap<String, String>()
    var urltohit: String = ""

    // mobily verify call
    var mobmobileno: String = ""
    var mobcode: String = ""

    // mobily verify call
    var otpmobileno: String = ""
    var otpcode: String = ""

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

            if (api_name.equals(getString(R.string.dashboarapicall)))
            {

                header.put("driver_id",mSessionManager!!.getDriverId())
                header.put("driver_lat","13.0826802")
                header.put("driver_lon","81.0128651")

                // url to hit
                urltohit = RetrofitInstance.dashboardcallapi
                Log.d("sfhygghghfds", header.toString())

                // common network call
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
                  EventBus.getDefault().post(IntentServiceResult(Activity.RESULT_OK, responsetosend!!, api_name))
                  Log.d("cjeyyhf",response.toString())
              }
              else
              {
                  EventBus.getDefault().post(IntentServiceResult(Activity.RESULT_OK, response.toString()!!, api_name))
                  Log.d("dhsvgdvcc",response.toString())

              }
              }
    }



}