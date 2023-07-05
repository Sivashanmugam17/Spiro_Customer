package com.mauto.chd.ui.MainPage

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProviders
import com.mauto.chd.R
import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.backgroundservices.dashboardaapii
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.view_model_with_repositary_main.MainViewModel
import com.mauto.chd.data.LanguageDb
import com.mauto.chd.mylocation.AppConstants
import com.mauto.chd.mylocation.GpsUtils
import com.mauto.chd.ui.registeration.LocaleAwareCompatActivity
//import com.cabilyhandyforalldinedoo.chd.ui.registeration.documentpageone
//import com.cabilyhandyforalldinedoo.chd.ui.registeration.documentpagetwo
import kotlinx.android.synthetic.main.locationonoffmissing.*
import mauto_customer.ui.MainscreenCustomers
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONException
import org.json.JSONObject
import pub.devrel.easypermissions.EasyPermissions

class turnlocationonandintenet : LocaleAwareCompatActivity()


{

    private lateinit var mContext: Activity
    private lateinit var viewModel: MainViewModel
    lateinit var mSessionManager: SessionManager
    private var isGPS:Boolean = false;
    private val LOCATION_AND_CONTACTS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
    lateinit var mHelpers: LanguageDb

    private var RC_LOCATION_CONTACTS_PERM:Int = 124
    var selectedbikestype:String = ""
    val REQUEST_CODE_PERMISSIONS = 101
    override fun onPause() {
        super.onPause()
        dashbaordapiservice(mContext)
//        getData()
        EventBus.getDefault().unregister(this);
    }

    override fun onResume() {
        super.onResume()
        dashbaordapiservice(mContext)
        getapplanuage()
        /*locationAndContactsTask()*/
//        requestLocationPermission()

//        getData()

        EventBus.getDefault().register(this);
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this);
    }
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.locationandinteneton)
        mContext = this@turnlocationonandintenet
        mSessionManager = SessionManager(mContext!!)
        mHelpers = LanguageDb(applicationContext)
        initviewmodel()
        dashbaordapiservice(mContext)
        getapplanuage()

//        getData()

        //click opertaions
        backbutton.setOnClickListener {
            finish()
        }
        btn_done.setOnClickListener {
            locationAndContactsTask()
        }
        btn_donelayout.setOnClickListener {
            locationAndContactsTask()
        }


    }

    fun initviewmodel()
    {
      viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var foreground = false
        var background = false

        if (requestCode == 124)
        {
            for (grantResult in grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED)
                {
                    locationAndContactsTask()
                }
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == AppConstants.GPS_REQUEST)
            {
                isGPS = true; // flag maintain before get location
                gpsenabledfunction()
                Log.d("dfdfdfdf", "fdggfgh")
            }
            else
            {
               Toast.makeText(applicationContext, "failed", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun getapplanuage() {
        val registerpagetwopage = Intent(applicationContext, commonapifetchservice::class.java)
        registerpagetwopage.putExtra(getString(R.string.intent_putextra_api_key), getString(R.string.getapplanuage))
        startService(registerpagetwopage)
    }

    fun dashbaordapiservice(mContext: Context) {
        val serviceClass = dashboardaapii::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.dashboarapicall))
        mContext.startService(intent)
    }
    private fun getData() {
        val aIntent = Intent(this, commonapifetchservice::class.java)
        aIntent.putExtra(getString(R.string.intent_putextra_api_key), getString(R.string.dashboarapicall))
        startService(aIntent)
    }
    fun gpsenabledfunction() {
        dashbaordapiservice(mContext)

        if(mSessionManager.ridehasSession() == true) {
            if (mSessionManager.getis_otp_ride()){
//                val mainpage = Intent(mContext, otpride::class.java)
//                mainpage.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                startActivity(mainpage)
//                finish()
            }
            else {
//                val mainpage = Intent(mContext, requestmianscreen::class.java)
//                mainpage.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                startActivity(mainpage)
//                finish()
            }
        }
        else
        {

            var mcurrentstage =mSessionManager.getdocumentstage2()
            Log.d("sfdwhtgdf", mcurrentstage)
//            var mdoctumentpending =mSessionManager.getDocPending()
//
//
//            if (mdoctumentpending.equals("0")){
//                selectedbikestype=mSessionManager.getbiketype()
//                var currentstage =""
//                if (selectedbikestype.equals("2")){
//                     currentstage="driver"
//                    mSessionManager?.setdocumentstage(currentstage)
//                    val intent2 = Intent(mContext, com.cabilyhandyforalldinedoo.chd.ui.registeration.documentpagetwo::class.java)
//                    mContext.startActivity(intent2)
//                }else{
//                    val intent2 = Intent(mContext, com.cabilyhandyforalldinedoo.chd.ui.registeration.documentpageone::class.java)
//                    mContext.startActivity(intent2)
//                }
//            }

            if (mcurrentstage.equals("")){
                val mainpage = Intent(mContext, MainscreenCustomers::class.java)
                mainpage.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(mainpage)
                finish()
            }

            if (mcurrentstage.equals("NOT_VERIFIED")){
                val mainpage = Intent(mContext, MainscreenCustomers::class.java)
               mainpage.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
              startActivity(mainpage)
               finish()
            }
            if (mcurrentstage.equals("READYTODRIVE")){
                val mainpage = Intent(mContext, MainscreenCustomers::class.java)
                mainpage.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(mainpage)
                finish()
            }
            if (mcurrentstage.equals("UPLOAD_DOC")){
//                val mainpage = Intent(mContext, documentpagetwo::class.java)
//                mainpage.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                startActivity(mainpage)
//                finish()
            }




            if (mcurrentstage.equals("ATTACH_VEHICLE")){
//                val intent2 = Intent(mContext, documentpageone::class.java)
//                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                startActivity(intent2)
            }
            if (mcurrentstage.equals("UPLOAD_DRIVER_DOC")){
                var currentstage ="driver"
                mSessionManager?.setdocumentstage(currentstage)
                Log.d("defr345her", currentstage)
//                val intent2 = Intent(mContext, documentpagetwo::class.java)
//                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                startActivity(intent2)
            }
//            if (mcurrentstage.equals("ATTACH_VEHICLE")){
//
//            }
//            val mainpage = Intent(mContext, mianscreenpage::class.java)
//            mainpage.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(mainpage)
//            finish()
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun doThis(intentServiceResult: IntentServiceResult) {
        var apiName: String = intentServiceResult.apiName
        if (apiName.equals(getString(R.string.dashboarapicall))) {
            var response: String = intentServiceResult.resultValue
            if (response != "failed")
                Log.d("dfhjhfh", "wowo")
                viewModel.getsplitrepsonse(applicationContext, response)

        }
        if(apiName.equals(getString(R.string.getapplanuage))){

            var response: String = intentServiceResult.resultValue
            Log.d("vkjhjchjd",response)

            savelankey(response)

        }

    }

    fun savelankey(response: String)
    {
        try
        {
            val response_json_object = JSONObject(response)
            Log.d("langkey", response_json_object.toString())

            val status = response_json_object.getString("status")
            val info = `response_json_object`.getJSONObject("response")
            if (status.equals("1"))
            {
                deleteDatabase(LanguageDb.DATABASE_NAME)
                val language_key = info.getJSONObject("content")
                val iter = language_key.keys()
                while (iter.hasNext()) {
                    val key = iter.next().toString()
                    try {
                        val value = language_key[key].toString()
                        println("Keysssssxd--$key--$value")
                        mHelpers.saveData(key, value.toString())
                        Log.d("dfdyyggcghghhh", value.toString())
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                mHelpers.closedatabase();

            }
            else
            {

            }
        }
        catch (e: Exception) {
            Log.d("dfdyyggcghghhh", e.toString())

        }
    }



    private fun requestLocationPermission() {
        val foreground = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) === PackageManager.PERMISSION_GRANTED
                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION), REQUEST_CODE_PERMISSIONS)

//        if (foreground) {
//            val background = ActivityCompat.checkSelfPermission(this,
//                    Manifest.permission.ACCESS_BACKGROUND_LOCATION) === PackageManager.PERMISSION_GRANTED
//            if (background) {
////                handleLocationUpdates()
//            } else {
//                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION), REQUEST_CODE_PERMISSIONS)
//            }
//        } else {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
//                    Manifest.permission.ACCESS_BACKGROUND_LOCATION), REQUEST_CODE_PERMISSIONS)
//        }
    }




    fun locationAndContactsTask()
    {
        if (hasLocationAndContactsPermissions())
        {
            // Permission given
            checklocationonedindevice()
        } else {
            // Ask for both permissions
            EasyPermissions.requestPermissions(this, "", RC_LOCATION_CONTACTS_PERM, *LOCATION_AND_CONTACTS)

        }
    }

    private fun hasLocationAndContactsPermissions(): Boolean {
        return EasyPermissions.hasPermissions(this, *LOCATION_AND_CONTACTS)
    }

    fun checklocationonedindevice()
    {
        try
        {
            val lm = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager;
            var gps_enabled:Boolean = false;
            var network_enabled:Boolean = false;
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if(!gps_enabled && !network_enabled)
            {
                GpsUtils(this).turnGPSOn(GpsUtils.onGpsListener() {
                    @Override
                    fun gpsStatus(isGPSEnable: Boolean) {
                        isGPS = isGPSEnable;
                    }
                })
            }
            else
            {
                Log.d("dshfhdfsfdfsg", "sdfhhshf")
                gpsenabledfunction()
            }
        }
        catch (e: Exception)
        {
        }
    }



}