package com.mauto.chd.view_model_tracking_page

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mauto.chd.backgroundservices.*
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.view_model_with_repositary_main.CurrentZoomModel
import com.mauto.chd.data.chatdb.DbHelper
import com.mauto.chd.data.onridelatandlong.onrideDbHelper
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline
import com.mauto.chd.R
import org.json.JSONException
import org.json.JSONObject


class TrackingPageViewModel(application: Application) : AndroidViewModel(application)
{

    var dashboarddata: Trackingpagerepostiatry


    private var mSessionManager: SessionManager? = null
    private val driverdetailsviewmodell = MutableLiveData<ArrayList<TrackingPageDataModel>>()
    private val stopslistt = MutableLiveData<ArrayList<stopsModel>>()
    private val esttime = MutableLiveData<String>()
    private val cancellationfailedtryagain = MutableLiveData<Int>()
    private val unreadchatcount = MutableLiveData<Int>()
    private val ratingaddedsuccessfully = MutableLiveData<Int>()
    private val ontripcancelledlisten = MutableLiveData<Int>()
    private val oncashrecived = MutableLiveData<Int>()
    private val estDistance = MutableLiveData<String>()
    private val hours = MutableLiveData<String>()
    private val navigateoption = MutableLiveData<String>()
    private val currentzoommodel = MutableLiveData<ArrayList<CurrentZoomModel>>()
    private val cancelreasonviewmodel = MutableLiveData<ArrayList<CancellationReasonDataModel>>()
    private val hitreroutingcall = MutableLiveData<Int>()
    private val reroutingoptionsettings = MutableLiveData<Boolean>()
    private val nightmodeoptionsettings = MutableLiveData<Boolean>()


    fun driveretailsviewmodellObserver(): MutableLiveData<ArrayList<TrackingPageDataModel>>
    {
        return driverdetailsviewmodell
    }
    fun navigatevalueobserver(): MutableLiveData<String>
    {
        return navigateoption
    }
    fun reroutingoptionsettingsobserver(): MutableLiveData<Boolean>
    {
        return reroutingoptionsettings
    }
    fun reroutingcallobserver(): MutableLiveData<Int>
    {
        return hitreroutingcall
    }
    fun unreadchatobserver(): MutableLiveData<Int>
    {
        return unreadchatcount
    }

    fun tripcancelledtripobserver(): MutableLiveData<Int>
    {
        return ontripcancelledlisten
    }
    fun ratingaddedsuccessfullyobserver(): MutableLiveData<Int>
    {
        return ratingaddedsuccessfully
    }
    fun oncashrecivedobsercver(): MutableLiveData<Int>
    {
        return oncashrecived
    }

    fun cancelviewmodellObserver(): MutableLiveData<ArrayList<CancellationReasonDataModel>>
    {
        return cancelreasonviewmodel
    }

    fun stoplistobserver(): MutableLiveData<ArrayList<stopsModel>>
    {
        return stopslistt
    }
    fun esttimeobserver(): MutableLiveData<String>
    {
        return esttime
    }
    fun cancelfailedobserver(): MutableLiveData<Int>
    {
        return cancellationfailedtryagain
    }

    fun zoomlevelobserver(): MutableLiveData<ArrayList<CurrentZoomModel>>
    {
        return currentzoommodel
    }

    fun estDistanceeobserver(): MutableLiveData<String>
    {
        return estDistance
    }
    fun esthoursobserver(): MutableLiveData<String>
    {
        return hours
    }


    init
    {

         dashboarddata = Trackingpagerepostiatry( object : TrackingPageListener
         {
             override fun onReRoutingenabled(reroutingenabled: Int) {
                 hitreroutingcall.value = 1
             }

             override fun oncashrecived(cashrecived: Int) {
                 oncashrecived.value = cashrecived
             }

             override fun onTripCancelled(tripcancelled: Int) {
                 ontripcancelledlisten.value = tripcancelled
             }

             override fun onCancelDataReceived(mutableLiveData: java.util.ArrayList<CancellationReasonDataModel>) {
                 cancelreasonviewmodel.value = mutableLiveData
             }

             override fun setonhourd(esthours: String) {
                 hours.value = esthours
             }

             override fun estDistance(estDistancea: String) {
                 estDistance.value=estDistancea
             }

             override fun onStopsAdapter(mutableLiveDataforstops: ArrayList<stopsModel>) {
                 stopslistt.value = mutableLiveDataforstops
             }

             override fun estTime(time: String) {
                 esttime.value = time
             }

             override fun onDataReceived(mutableLiveDataforvehicledetails: ArrayList<TrackingPageDataModel>)
            {
                driverdetailsviewmodell.value = mutableLiveDataforvehicledetails
                Log.d("ridestatus", driverdetailsviewmodell.toString())
            }
            override fun onError(error: Int)
            {
            }
         })
    }

    fun clearchatrecord(mContext:Context)
    {
        mContext.deleteDatabase(DbHelper.DATABASE_NAME)


    }
    fun clearridedtaa(mContext:Context)
    {
        mContext.deleteDatabase(onrideDbHelper.DATABASE_NAME)
    }

    fun checkdriverisonpathornot(mContext:Context,foregroundPolyline:Polyline,reroutingsuccessorfailed:Int,latLng:LatLng)
    {
        dashboarddata.checkdriverisonpathornot(mContext,foregroundPolyline,reroutingsuccessorfailed,latLng)
    }

    fun nighmodesettingsobeser(): MutableLiveData<Boolean>
    {
        return nightmodeoptionsettings
    }

    fun getchatunreadcount(mContext:Context,rideid:String)
    {
        var unreadcount:Int=0
        var mHelper: DbHelper? = null
        var dataBase: SQLiteDatabase? = null
        mHelper= DbHelper(mContext);
        var values: ContentValues = ContentValues()
        values.put(DbHelper.status,"1")
        dataBase = mHelper!!.getWritableDatabase();
        var mCursor: Cursor = dataBase!!.rawQuery("SELECT * FROM " + DbHelper.TABLE_NAME+" WHERE rideid='"+rideid+"' AND status='0'", null);
        if (mCursor.moveToFirst()) {
            do {
                unreadcount++
            } while (mCursor.moveToNext());
        }
        mCursor.close()
        unreadchatcount.value=unreadcount
    }



    fun zoommap(mContext:Context)
    {
        var mSessionManager: SessionManager
        mSessionManager = SessionManager(mContext)

        var lat:String=mSessionManager.getOnlineLatitiude()
        var long:String=mSessionManager.getOnlineLongitude()

        if(!lat.equals("0"))
        {
            var zoomdetails = java.util.ArrayList<CurrentZoomModel>()
            zoomdetails.add(CurrentZoomModel(lat,long))
            currentzoommodel.value = zoomdetails
        }
    }


    fun startroutecall(mContext: Context,droplat:Double,droplong:Double)
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.routeapicall))
        intent.putExtra("droplat", droplat.toString())
        intent.putExtra("droplong", droplong.toString())
         mContext.startService(intent)
    }


    fun reroutingcall(mContext: Context,droplat:Double,droplong:Double)
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.reroutingapicall))
        intent.putExtra("droplat", droplat.toString())
        intent.putExtra("droplong", droplong.toString())
        mContext.startService(intent)
    }


    fun reroutingoption(mContext: Context,isChecked:Boolean)
    {
        mSessionManager = SessionManager(mContext!!)
        try
        {
            mSessionManager!!.setreroutingoption(isChecked)
            reroutingoptionsettings.value = isChecked
        }
        catch (e: Exception)
        {
        }
    }




    fun getreroutingoption(mContext: Context)
    {
        mSessionManager = SessionManager(mContext!!)
        try
        {
            reroutingoptionsettings.value = mSessionManager!!.getreoutesettingboolean()
        }
        catch (e: Exception)
        {
        }
    }

    fun nightmodeoption(mContext: Context,isChecked:Boolean)
    {
        mSessionManager = SessionManager(mContext!!)
        try
        {
            mSessionManager!!.setnightmodeoption(isChecked)
        }
        catch (e: Exception)
        {
        }
    }

    fun getnightmodeoption(mContext: Context)
    {
        mSessionManager = SessionManager(mContext!!)
        try
        {
            nightmodeoptionsettings.value = mSessionManager!!.getnightmodeboolean()
        }
        catch (e: Exception)
        {
        }
    }


    fun hitnearingapicall(mContext: Context,rideid:String)
    {
        val serviceClass = fillroutecall::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.hitnearingapi))
        intent.putExtra("rideid", rideid)
        intent.putExtra("iamnear", "iamnear")
        mContext.startService(intent)
    }

    fun voiceofnearing(mContext: Context)
    {
        val serviceClass = nearningcustomerlocation::class.java
        val intent = Intent(mContext, serviceClass)
        mContext.startService(intent)
    }

    fun tripupdatestatus(mContext: Context,droplatitude:String,droplongitude:String,useridtosend:String)
    {
        val serviceClass = tripupdatecallapi::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.tripupdate))
        intent.putExtra("droplat",droplatitude)
        intent.putExtra("droplong", droplongitude)
        intent.putExtra("useridtosend", useridtosend)
        mContext.startService(intent)
    }



    fun getsplitrepsonse(context:Context)
    {
        mSessionManager = SessionManager(context!!)
        if(!mSessionManager!!.gettripdetails().equals(""))
        dashboarddata.getdrivertails(mSessionManager!!.gettripdetails(),context)
//        dashboarddata.getdrivertailarrived(mSessionManager!!.gettripdetails(),context)
    }

    fun cancelreasonresponsesplit(context:Context,rasponse:String)
    {
        dashboarddata.getcancellationreason(rasponse,context)
    }

    fun splitbasedoncashresponse(context:Context,rasponse:String)
    {
        dashboarddata.getcashresponse(rasponse,context)
    }

    fun splittripupdate(context:Context,rasponse:String)
    {
        dashboarddata.getdrivertails(rasponse,context)
//        dashboarddata.getdrivertailarrived(rasponse,context)
    }


    fun cleartripdetails(context:Context,response:String)
    {
        mSessionManager = SessionManager(context!!)

        try
        {
            val response_json_object = JSONObject(response)
            val status = response_json_object.getString("status")
            if (status.equals("1"))
            {
                mSessionManager!!.clearridedetails()
                cancellationfailedtryagain.value = 1

                if(!mSessionManager!!.getUserfcmtoken().equals(""))
                {
                    val serviceClass = fcmsendingprocess::class.java
                    val registerpagetwopage = Intent(context, serviceClass)
                    registerpagetwopage.putExtra("rideid", "")
                    registerpagetwopage.putExtra("ridestatus", "")
                    registerpagetwopage.putExtra("actions", "ride_cancelled")
                    registerpagetwopage.putExtra("messages", "")
                    registerpagetwopage.putExtra("fcmtoken", mSessionManager!!.getUserfcmtoken())
                    context.startService(registerpagetwopage)
                }

            }
            else
            {
                cancellationfailedtryagain.value = 0
            }
        }
        catch (e: Exception)
        {
        }
    }


    fun clertripbasedonurl(mContext:Context)
    {
        mSessionManager = SessionManager(mContext!!)
        try
        {
            mSessionManager!!.clearridedetails()
            cancellationfailedtryagain.value = 1
        }
        catch (e: Exception)
        {
        }
    }


    fun cleartripdetailsalone(mContext:Context)
    {
        mSessionManager = SessionManager(mContext!!)
        try
        {
            mSessionManager!!.clearridedetails()
        }
        catch (e: Exception)
        {
        }
    }




    fun skipnow(context:Context,response:String)
    {
        try
        {
            var responseObj = JSONObject(response)
            if (responseObj.getString("status").equals("1"))
            {
                mSessionManager = SessionManager(context!!)
                mSessionManager!!.clearridedetails()
                ratingaddedsuccessfully.value = 1
            }
            else
            {
                mSessionManager = SessionManager(context!!)
                mSessionManager!!.clearridedetails()
                ratingaddedsuccessfully.value = 1
            }
        }
        catch (e: JSONException)
        {
        }
    }


    fun skipratingclear(mContext:Context,rideid:String,skipstatus:String,rating:String)
    {
        mSessionManager = SessionManager(mContext!!)
        try
        {
            val serviceClass = skiprating::class.java
            val intent = Intent(mContext, serviceClass)
            intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.skiprating))
            intent.putExtra("ride_id", rideid)
            intent.putExtra("skipstatus", skipstatus)
            intent.putExtra("rating", rating)
            mContext.startService(intent)
        }
        catch (e: Exception)
        {
        }
    }

    fun receivedcash(mContext:Context,rideid:String,amount:String)
    {
        mSessionManager = SessionManager(mContext!!)
        try
        {
            val serviceClass = commonapifetchservice::class.java
            val intent = Intent(mContext, serviceClass)
            intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.cashreceived))
            intent.putExtra("ride_id", rideid)
            intent.putExtra("amount", amount)
            mContext.startService(intent)
        }
        catch (e: Exception)
        {
        }
    }

    fun cleartripdetails2(context:Context)
    {
        mSessionManager = SessionManager(context!!)
        mSessionManager!!.clearridedetails()
        cancellationfailedtryagain.value = 1
    }

    fun getcanclreason(mContext:Context,rideid:String)
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.cancelreason))
        intent.putExtra("rideid", rideid)
        mContext.startService(intent)
    }


    fun passcancelid(mContext:Context,id:String,rideid:String)
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.cancelthisride))
        intent.putExtra("rideid", rideid)
        intent.putExtra("cancelid", id)
        mContext.startService(intent)
    }



    fun etacalculation(context:Context,start_latlng: LatLng, end_latlng: LatLng)
    {
        mSessionManager = SessionManager(context!!)
        dashboarddata.etacalculation(context,start_latlng,end_latlng)
    }


    fun getdataforride(mContext:Context)
    {
        val serviceClass = ridedetailsapicall::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.ridedetails))
        mContext.startService(intent)
    }

    fun getnavigationoption(mContext: Context)
    {
        mSessionManager = SessionManager(mContext!!)
        try
        {
            navigateoption.value = mSessionManager!!.getnavigateopition()
        }
        catch (e: Exception)
        {
        }
    }


    fun isMyServiceRunning(context: Activity?, serviceClass: Class<*>): Boolean
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