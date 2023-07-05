package com.mauto.chd.view_model_with_repositary_main

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.backgroundservices.dashboardaapii
import com.mauto.chd.backgroundservices.logoutcallapi
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.data.steptwodocumentdb.documenttwoRepository
import kotlinx.coroutines.Job
import com.mauto.chd.R


class MainPageViewModel(application: Application) : AndroidViewModel(application)
{

    var dashboarddata: dashboardpagerepostiatry
    private lateinit var job: Job
    private var mSessionManager: SessionManager? = null
    private val driverdetailsviewmodell = MutableLiveData<String>()
    private val currentzoommodel = MutableLiveData<ArrayList<CurrentZoomModel>>()
    private val driveronlinestatuschanged = MutableLiveData<String>()
    private val defaultdriveronlinestatuschanged = MutableLiveData<String>()

    private val getdriverprofile = MutableLiveData<String>()

    private val getdocstatus = MutableLiveData<String>()
    private val repository: documenttwoRepository = documenttwoRepository(application)


    private val getmobilenoo = MutableLiveData<String>()
    private val dutyrideid = MutableLiveData<String>()
    private val earningtoday = MutableLiveData<String>()
    private val completedtrip = MutableLiveData<String>()
    private val misstrip = MutableLiveData<String>()
    private val driverimages = MutableLiveData<String>()



    private val offstatus = MutableLiveData<Int>()
    private val onstatus = MutableLiveData<Int>()
    private val locationmode = MutableLiveData<Int>()
    private val logoutval = MutableLiveData<Int>()
    private val driverdetaill = MutableLiveData<ArrayList<DriverDetailDataModel>>()

    private val defaultdriverdetail = MutableLiveData<ArrayList<DriverDetailDataModel>>()

    private val nightmodeoptionsettings = MutableLiveData<Boolean>()

    private val opendrwaerlayout = MutableLiveData<Int>()



    fun driveretailsviewmodellObserver(): MutableLiveData<String>
    {
        return driverdetailsviewmodell
    }
    fun opendrwaerlayoutobserver(): MutableLiveData<Int>
    {
        return opendrwaerlayout
    }

    fun nighmodesettingsobeser(): MutableLiveData<Boolean>
    {
        return nightmodeoptionsettings
    }
    fun locationmodeobserver(): MutableLiveData<Int>
    {
        return locationmode
    }
    fun mobilenoobserver(): MutableLiveData<String>
    {
        return getmobilenoo
    }
    fun driverimagesobserver(): MutableLiveData<String>
    {
        return driverimages
    }

    fun Offerbanerimageobserver(): MutableLiveData<String>
    {
        return driverimages
    }

    fun zoomlevelobserver(): MutableLiveData<ArrayList<CurrentZoomModel>>
    {
        return currentzoommodel
    }



    fun driverfulldetailsobserver(): MutableLiveData<ArrayList<DriverDetailDataModel>>
    {
        return driverdetaill
    }

    fun defaultdriverdetailobserver(): MutableLiveData<ArrayList<DriverDetailDataModel>>
    {
        return defaultdriverdetail
    }





    fun onlinestatusobserver(): MutableLiveData<String>
    {
        return driveronlinestatuschanged
    }
    fun offstatusobserver(): MutableLiveData<Int>
    {
        return offstatus
    }
    fun dutyrideobserver(): MutableLiveData<String>
    {
        return dutyrideid
    }

    fun earningtodayobserver(): MutableLiveData<String>
    {
        return earningtoday
    }
    fun misstripobserver(): MutableLiveData<String>
    {
        return misstrip
    }
    fun completedtripobserver(): MutableLiveData<String>
    {
        return completedtrip
    }
    fun onstatusobserver(): MutableLiveData<Int>
    {
        return onstatus
    }
    fun docstatusobserver(): MutableLiveData<String>
    {
        return getdocstatus
    }
    fun driverprofileobserver(): MutableLiveData<String>
    {
        return getdriverprofile
    }
    fun defaultonlinestatusobserver(): MutableLiveData<String>
    {
        return defaultdriveronlinestatuschanged
    }
    fun logoutobserver(): MutableLiveData<Int>
    {
        return logoutval
    }
    init
    {
         dashboarddata = dashboardpagerepostiatry( object : Listener
         {
             override fun onMobileNo(mobileno: String) {
                 getmobilenoo.value=mobileno
             }

             override fun driverimage(driverimage: String) {
                 driverimages.value=driverimage
             }

             override fun onDocstatus(mutableLiveData: String) {
                 getdocstatus.value = mutableLiveData
             }

             override fun onDutyride(mutableLiveData: String) {
                 dutyrideid.value = mutableLiveData
             }

             override fun onEarningsAdded(mutableLiveData: String) {
                 misstrip.value = mutableLiveData

             }
             override fun onEarning(mutableLiveData: String) {
                 earningtoday.value = mutableLiveData

             }
             override fun onAmount(mutableLiveData: String) {
                 completedtrip.value = mutableLiveData


             }
             override fun onOnlinestatusChange(onlinesttau: String) {
                 driveronlinestatuschanged.value = onlinesttau
             }
             override fun onDataReceived(mutableLiveDataforvehicledetails: ArrayList<MianPageDataModel>)
             {
             }
             override fun onError(error: Int)
             {
             }
         })
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
    fun getdriverimage(mcontext:Context)
    {
        var mSessionManager: SessionManager
        mSessionManager = SessionManager(mcontext)
        val driver_image =mSessionManager!!.getUserDetails()!![mSessionManager!!.driver_image]!!
        driverdetailsviewmodell.value = driver_image
    }
    fun getsplitrepsonse(mcontext:Context,response:String)
    {
        dashboarddata.getdrivertails(mcontext,response)
    }
    fun offstatus()
    {
        offstatus.value = 1
    }

    fun opendrawerlayout()
    {
        opendrwaerlayout.value = 1
    }
    fun logout(mcontext:Context)
    {
        val serviceClass = logoutcallapi::class.java
        val intent = Intent(mcontext, serviceClass)
        intent.putExtra(mcontext.getString(R.string.intent_putextra_api_key), mcontext.getString(R.string.logoutcallapi))
        mcontext.startService(intent)
        repository.deletallrecord()
    }
    fun onstatus()
    {
        onstatus.value = 1
    }
    fun locationmode(mcontext:Context,mode:Int)
    {
        var mSessionManager: SessionManager
        mSessionManager = SessionManager(mcontext)
        mSessionManager.setlocationmode(mode.toString())
        locationmode.value = mode
    }
    fun driverstatus(mContext:Context,availablestatus:String)
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.driveravailable))
        intent.putExtra("availablestatus", availablestatus)
        mContext.startService(intent)
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
    fun dispalydriverdetails(mcontext:Context)
    {
        var mSessionManager: SessionManager
        mSessionManager = SessionManager(mcontext)
        var driverdetails = java.util.ArrayList<DriverDetailDataModel>()
        val driver_id = mSessionManager.getUserDetails()!![mSessionManager!!.id]!!
        val driver_name =mSessionManager!!.getUserDetails()!![mSessionManager!!.driver_name]!!
        val driver_image =mSessionManager!!.getUserDetails()!![mSessionManager!!.driver_image]!!
        val email =mSessionManager!!.getUserDetails()!![mSessionManager!!.email]!!
        val driver_review =mSessionManager!!.getReview()
        val vehicle_number =mSessionManager!!.getVehicleno()
        val vehicle_model =mSessionManager!!.getModelName()
        val CategoryName =mSessionManager!!.geCategoryName()
        val currencysymboll =mSessionManager!!.getCurrencySymbol()
        val locationmaode =mSessionManager!!.getLocationMode()
        val mobileno =mSessionManager!!.getDriverMobileNo()
        driverdetails.add(DriverDetailDataModel(driver_id,driver_name,driver_image,vehicle_number,vehicle_model,driver_review,email,CategoryName,currencysymboll,locationmaode,mobileno))
        driverdetaill.value =driverdetails
    }
    fun defaultvalueloader(mcontext:Context)
    {
        var mSessionManager: SessionManager
        mSessionManager = SessionManager(mcontext)
        var driverdetails = java.util.ArrayList<DriverDetailDataModel>()
        val driver_id = mSessionManager.getUserDetails()!![mSessionManager!!.id]!!
        val driver_name =mSessionManager!!.getUserDetails()!![mSessionManager!!.driver_name]!!
        val driver_image =mSessionManager!!.getUserDetails()!![mSessionManager!!.driver_image]!!
        val email =mSessionManager!!.getUserDetails()!![mSessionManager!!.email]!!
        val driver_review =mSessionManager!!.getReview()
        val vehicle_number =mSessionManager!!.getVehicleno()
        val vehicle_model =mSessionManager!!.getModelName()
        val CategoryName =mSessionManager!!.geCategoryName()
        val currencysymboll =mSessionManager!!.getCurrencySymbol()
        val locationmaode =mSessionManager!!.getLocationMode()
        val mobileno =mSessionManager!!.getDriverMobileNo()
        driverdetails.add(DriverDetailDataModel(driver_id,driver_name,driver_image,vehicle_number,vehicle_model,driver_review,email,CategoryName,currencysymboll,locationmaode,mobileno))
        defaultdriverdetail.value =driverdetails
    }
    fun getDefaultStatus(mcontext:Context)
    {
        var mSessionManager: SessionManager? = null
        mSessionManager = SessionManager(mcontext!!)
        defaultdriveronlinestatuschanged.value=mSessionManager.getOnlineAvailability().toString()
    }
    fun getdriverdetails(mcontext:Context)
    {
        var mSessionManager: SessionManager? = null
        mSessionManager = SessionManager(mcontext!!)
        getdriverprofile.value = mSessionManager!!.getUserDetails()!![mSessionManager!!.driver_image]!!
    }
    fun getdocumentstatus(mcontext:Context)
    {
        var mSessionManager: SessionManager? = null
        mSessionManager = SessionManager(mcontext!!)
        getdocstatus.value = mSessionManager!!.getDocPending()
    }
    fun getonlinestatus(response:String,avialbilty:String,mcontext:Context)
    {
        dashboarddata.getdriveravailablitystatus(response,avialbilty,mcontext)
    }
    fun dashbaordapiservice(mContext: Context)
    {
        val serviceClass = dashboardaapii::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.dashboarapicall))
        mContext.startService(intent)
    }
}