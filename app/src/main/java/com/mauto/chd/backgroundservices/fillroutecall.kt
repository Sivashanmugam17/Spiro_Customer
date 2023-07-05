package com.mauto.chd.backgroundservices


import android.app.Activity
import android.app.IntentService
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.IBinder
import android.util.Log
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.data.onridelatandlong.onrideDbHelper
import com.mauto.chd.retrofit.RetrofitInstance
import com.google.android.gms.maps.model.LatLng
import com.mauto.chd.R
import com.mindorks.kotnetworking.KotNetworking
import com.mindorks.kotnetworking.common.Priority
import okhttp3.ResponseBody
import org.greenrobot.eventbus.EventBus
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class fillroutecall : IntentService("ApiHit")
{
    var header = HashMap<String, String>()
    var urltohit: String = ""
    lateinit private var mSessionManager: SessionManager
    lateinit var waypoints_array: ArrayList<LatLng>
    var mHelper: onrideDbHelper? = null
    var dataBase: SQLiteDatabase? = null
    private var stops_location_array: ArrayList<String>? = null

    override fun onBind(intent: Intent): IBinder?
    {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onHandleIntent(intent: Intent?)
    {
        mHelper= onrideDbHelper(applicationContext)
        mSessionManager = SessionManager(applicationContext!!)
        waypoints_array = ArrayList()
        stops_location_array = ArrayList()
        if (intent?.hasExtra("coloumnid")!!)
        {
            var coloumnid:Int = intent?.getStringExtra("coloumnid")!!.toInt()
            var rideid:String = intent?.getStringExtra("rideid")!!
            if(coloumnid > 1)
            {
               var firstpickpostion:Int=(coloumnid-1)
               var firstlat:LatLng=getrecordfromlocaldb(rideid,firstpickpostion)
               var secondlat:LatLng=getrecordfromlocaldb(rideid,coloumnid)
                if(firstlat.latitude != 0.0 && firstlat.longitude != 0.0 && secondlat.latitude != 0.0 && secondlat.longitude != 0.0)
                {
                    val routeUrl = getRouteApiUrl(firstlat, secondlat)
                    fetchRouteUrl(routeUrl,coloumnid)
                }
            }
        }
        else if (intent.hasExtra("iamnear"))
        {
            var rideid:String = intent.getStringExtra("rideid")!!
            header.put("driver_id",mSessionManager.getDriverId())
            header.put("ride_id",rideid)

            urltohit = RetrofitInstance.nearingcallhit
            nearcallhit(header,rideid)

        }
    }

    private fun getRouteApiUrl(origin: LatLng, dest: LatLng): String {

        // Origin of route
        val str_origin = "origin=" + origin.latitude + "," + origin.longitude
        // Destination of route
        val str_dest = "destination=" + dest.latitude + "," + dest.longitude
        // Sensor enabled
        val sensor = "sensor=false&mode=driving"
        //waypoints

        var waypoints: String = ""
        for (i in 0 until waypoints_array.size) {
            if (i == 0)
                waypoints = "waypoints="
            waypoints += "via:" + waypoints_array.get(i).latitude.toString() + "," + waypoints_array.get(i).longitude.toString() + "|"
        }
        // api_key
//        val api_key = "key=" + resources.getString(R.string.routeKey)
        val api_key = "key=" + mSessionManager.getgoogle_api_key()!!
        // Building the parameters to the web service
        val parameters = "$str_origin&$str_dest&$sensor&$waypoints&$api_key"
        // Output format
        val output = "json"
        // Building the url to the web service
        val url = "https://maps.googleapis.com/maps/api/directions/$output?$parameters"
        return url
    }


    private fun fetchRouteUrl(url: String,coloumnid:Int) {

        val responseCall = RetrofitInstance.getGoogleInstance().getRouteList(url)
        responseCall.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    if (response.body() != null)
                    {
                        try
                        {
                            val responseStr = response.body()!!.string()
                            Log.e("getAddressListRes", responseStr)
                              var  json:JSONObject =  JSONObject(responseStr)
                              var routeArray:JSONArray = json.getJSONArray("routes")
                              var routes:JSONObject = routeArray.getJSONObject(0)
                              var newTempARr:JSONArray = routes.getJSONArray("legs")
                              var newDisTimeOb:JSONObject = newTempARr.getJSONObject(0)
                              var distOb:JSONObject = newDisTimeOb.getJSONObject("distance")
                              if(distOb.getString("text").contains("km"))
                              {
                                  var getingkm:String=distOb.getString("text").replace("km","")
                                  getingkm=getingkm.replace(" ","")
                                  var inmeters:Float = (getingkm.toFloat() * 1000)
                                  updatestatusofrecord(inmeters,coloumnid)
                              }
                        }
                        catch (e: Exception)
                        {
                            e.printStackTrace()
                            updatestatusalone(coloumnid)
                        }

                    }
                }
                else
                {
                    updatestatusalone(coloumnid)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                updatestatusalone(coloumnid)
            }
        })
    }


    // common api fetch service
    private fun nearcallhit(header: HashMap<String, String>,ride_id:String)
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
                        EventBus.getDefault().post(IntentServiceResult(Activity.RESULT_OK, "0", getString(R.string.hitnearingapi)))
                    }
                    else
                    {
                        var response:String = response.toString()
                        Log.d("on ack", response)
                        try
                        {
                            mSessionManager = SessionManager(applicationContext!!)
                            mSessionManager!!.savehittedrideidvalue(ride_id)
                            EventBus.getDefault().post(IntentServiceResult(Activity.RESULT_OK, "1", getString(
                                R.string.hitnearingapi)))
                        }
                        catch (e: Exception)
                        {
                        }
                    }
                }
    }



 fun getrecordfromlocaldb(rideid:String,coloumnid:Int):LatLng
 {
     var lat:String = "0.0"
     var longitude:String = "0.0"
     dataBase = mHelper!!.getWritableDatabase();
     var mCursor: Cursor = dataBase!!.rawQuery("SELECT * FROM " + onrideDbHelper.TABLE_NAME+" WHERE rideid='"+rideid+"' AND id="+coloumnid, null);
     if (mCursor.moveToFirst()) {
         do {
              lat= mCursor.getString(mCursor.getColumnIndex(onrideDbHelper.latitude)).toString()
              longitude= mCursor.getString(mCursor.getColumnIndex(onrideDbHelper.longitude)).toString()
         } while (mCursor.moveToNext());
     }
     mCursor.close()

    return LatLng(lat.toDouble(), longitude.toDouble())
 }


    fun updatestatusofrecord(inmeters:Float,coloumnid:Int)
    {
        Log.e("distOb--->", inmeters.toString()+"---->"+coloumnid)
        dataBase = mHelper!!.getWritableDatabase();
        var values: ContentValues = ContentValues()
        values.put(onrideDbHelper.km,inmeters.toString())
        values.put(onrideDbHelper.stattus,"0")
        dataBase!!.update(onrideDbHelper.TABLE_NAME, values, onrideDbHelper.KEY_ID+"="+coloumnid, null);
    }


    fun updatestatusalone(coloumnid:Int)
    {
        Log.e("update status alone--->", "---->"+coloumnid)
        dataBase = mHelper!!.getWritableDatabase();
        var values: ContentValues = ContentValues()
        values.put(onrideDbHelper.stattus,"0")
        dataBase!!.update(onrideDbHelper.TABLE_NAME, values, onrideDbHelper.KEY_ID+"="+coloumnid, null);
    }

}