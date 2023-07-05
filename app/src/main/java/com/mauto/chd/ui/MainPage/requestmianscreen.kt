//package com.cabilyhandyforalldinedoo.chd.ui.MainPage
//
//import android.Manifest
//import android.app.Activity
//import android.app.ActivityManager
//import android.content.*
//import android.content.pm.PackageManager
//import android.content.res.Resources
//import android.database.sqlite.SQLiteDatabase
//import android.graphics.Bitmap
//import android.graphics.drawable.BitmapDrawable
//import android.graphics.drawable.Drawable
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.os.IBinder
//import android.provider.Settings
//import android.util.Log
//import android.view.Gravity
//import android.view.View
//import android.view.animation.Animation
//import android.view.animation.AnimationUtils
//import android.widget.*
//import androidx.appcompat.app.AlertDialog
//import androidx.core.content.ContextCompat
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import cabily.handyforall.dinedoo.R
//import com.bumptech.glide.Glide
//import com.bumptech.glide.request.RequestOptions
//import com.bumptech.glide.request.target.CustomTarget
//import com.bumptech.glide.request.transition.Transition
//import com.cabilyhandyforalldinedoo.chd.Backgroundservices.AService
//import com.cabilyhandyforalldinedoo.chd.Backgroundservices.FService
//import com.cabilyhandyforalldinedoo.chd.Backgroundservices.UpdateForgroundService
//import com.cabilyhandyforalldinedoo.chd.Backgroundservices.commonapifetchservice
//import com.cabilyhandyforalldinedoo.chd.EventBusConnection.ChatIntentServiceResult
//import com.cabilyhandyforalldinedoo.chd.EventBusConnection.IntentServiceReroutingRouteResult
//import com.cabilyhandyforalldinedoo.chd.EventBusConnection.IntentServiceResult
//import com.cabilyhandyforalldinedoo.chd.EventBusConnection.IntentServiceRouteResult
//import com.cabilyhandyforalldinedoo.chd.Modal.TextMessage
//import com.cabilyhandyforalldinedoo.chd.SessionManagerPackage.SessionManager
//import com.cabilyhandyforalldinedoo.chd.ViewModelTrackingPage.CancellationReasonDataModel
//import com.cabilyhandyforalldinedoo.chd.ViewModelTrackingPage.TrackingPageViewModel
//import com.cabilyhandyforalldinedoo.chd.ViewModelTrackingPage.Trackingpagerepostiatry
//import com.cabilyhandyforalldinedoo.chd.ViewModelTrackingPage.stopsModel
//import com.cabilyhandyforalldinedoo.chd.adaptersofchd.cancelreasonadapter
//import com.cabilyhandyforalldinedoo.chd.adaptersofchd.stopsModelAdapter
//import com.cabilyhandyforalldinedoo.chd.clickableInterface.cancelreasonclick
//import com.cabilyhandyforalldinedoo.chd.commonutils.AppUtils
//import com.cabilyhandyforalldinedoo.chd.data.LanguageDb
//import com.cabilyhandyforalldinedoo.chd.data.onridelatandlong.onrideDbHelper
//import com.cabilyhandyforalldinedoo.chd.mylocation.FindRoute
//import com.cabilyhandyforalldinedoo.chd.mylocation.RouteListeners
//import com.cabilyhandyforalldinedoo.chd.mylocation.TrackingPageGooglemap
//import com.cabilyhandyforalldinedoo.chd.ui.registeration.LocaleAwareCompatActivity
//import com.google.android.gms.maps.*
//import com.google.android.gms.maps.model.*
//import com.google.android.material.bottomsheet.BottomSheetDialog
//import com.google.android.material.snackbar.Snackbar
//import com.google.firebase.database.*
//import com.ncorti.slidetoact.SlideToActView
//import fr.castorflex.android.smoothprogressbar.SmoothProgressBar
//import kotlinx.android.synthetic.main.otppagethree.*
//import kotlinx.android.synthetic.main.otpride.*
//import kotlinx.android.synthetic.main.requestpagedfa.*
//import kotlinx.android.synthetic.main.requestpagedfa.ctmap2
//import kotlinx.android.synthetic.main.requestpagedfa.otp1
//import kotlinx.android.synthetic.main.requestpagedfa.otp2
//import kotlinx.android.synthetic.main.requestpagedfa.otp3
//import kotlinx.android.synthetic.main.requestpagedfa.otp4
//import kotlinx.android.synthetic.main.requestpagedfa.username
//import kotlinx.coroutines.*
//import org.andcoe.floatingwidget.FloatingWidgetService
//import org.greenrobot.eventbus.EventBus
//import org.greenrobot.eventbus.Subscribe
//import org.greenrobot.eventbus.ThreadMode
//import org.json.JSONObject
//import java.text.SimpleDateFormat
//import java.util.*
//import kotlin.collections.ArrayList
//
//class requestmianscreen : LocaleAwareCompatActivity (), OnMapReadyCallback, RouteListeners {
//    companion object {
//        private const val DRAW_OVERLAYS_PERMISSION_REQUEST_CODE = 666
//    }
//    var pickupLatLng: LatLng? = null
//    var drop_latlng: LatLng? = null
//    var myService: FService? = null
//    var aService: AService? = null
//    var isBound = false
//    var routeapihitted:Int = 0
//    var hidebuttons:Int = 0
//    var voicenearingg:Int = 0
//    var mapchoosen:String =""
//    var sucesofdropmarker:Int = 0
//    var boundfixed:Int = 0
//    var reroutingsuccessorfailed:Int = 0
//    var waithitted:Int = 0
//    var currentridestatus:String = ""
//    private lateinit var mMapFragment: SupportMapFragment
//    private lateinit var mContext: Activity
//    private lateinit var viewModel: TrackingPageViewModel
//    private var googlemap: GoogleMap? = null
//    var driver_marker_resized: Bitmap? = null
//    var dropmarkerresized: Bitmap? = null
//    var marker:Marker? = null
//    var usernmaegetting:String=""
//    var userimagesetting:String=""
//    var turnoffrerouting:Int=0
//    var mapmode:Boolean = false
//    var ridecancelleddialog:BottomSheetDialog ?= null
//    var userridecancelleddialog:BottomSheetDialog ?= null
//    private lateinit var repeatFun : Job
//
//    lateinit var wayPointsBuilder: LatLngBounds.Builder
//    lateinit var routeLatLngArray: ArrayList<LatLng>
//    var  otpstatusvalue:String=""
//    var dotsize:Float =7f
//    private var isRouteAvail: Boolean = false
//    var foregroundPolyline: Polyline ?= null
//    lateinit var mSessionManager: SessionManager
//    var canceldialog : BottomSheetDialog ? =null
//    var ratingdialog : BottomSheetDialog ? =null
//    var loadrrating:SmoothProgressBar ?= null
//    var collectcash : BottomSheetDialog ? =null
//    var padding: Int = 0
//    private var dest_drop_lat: Double = 0.0
//    private var dest_drop_lng: Double = 0.0
//    var drawply : FindRoute?=null
//    var rideid:String = ""
//    var useridtosend:String = ""
//    var globalridestatus:String=""
//    var canelreasonhasbeenstored:String = ""
//    var customerphno:String = ""
//    var  fab_open: Animation?= null
//    var fab_close:Animation ?= null
//    var rotate_forward:Animation?= null
//    var rotate_backward:Animation?= null
//    var closed:Int = 0
//    private var myLocationGoogleMap: TrackingPageGooglemap? = null
//    var list: ArrayList<String>? = null
//    var messagestatus:String=""
//    lateinit var myRef : DatabaseReference
//    var mride_completeds=""
//    var mHelper: onrideDbHelper? = null
//    var dataBase: SQLiteDatabase? = null
//    lateinit var mHelperslan: LanguageDb
//    var paytypess:String = "kkiapay"
//    private lateinit var repeatFunpayment : Job
//
//
//    private val myConnection = object : ServiceConnection
//    {
//        override fun onServiceConnected(className: ComponentName,
//                                        service: IBinder)
//        {
//            val binder = service as FService.MyLocalBinder
//            myService = binder.getService()
//            isBound = true
//        }
//        override fun onServiceDisconnected(name: ComponentName)
//        {
//            isBound = false
//        }
//    }
//    private val arrivalConnection = object : ServiceConnection
//    {
//        override fun onServiceConnected(className: ComponentName,
//                                        service: IBinder)
//        {
//            val binder = service as AService.MyLocalBinder
//            aService = binder.getService()
//            isBound = true
//        }
//        override fun onServiceDisconnected(name: ComponentName)
//        {
//            isBound = false
//        }
//    }
//    override fun onCreate(savedInstanceState: Bundle?)
//    {
//        super.onCreate(savedInstanceState)
////        setContentView(R.layout.requestpagedfa)
//        mContext = this@requestmianscreen
//        iv_line.visibility = View.VISIBLE
//        intilizecommonvalue()
//        initviewmodel()
//        initializeMap()
//        defaultcar()
//        defaultdropmarker()
//        myRef = FirebaseDatabase.getInstance().getReference("RideChat/"+mSessionManager.gerrideid())
//        slidebutton.onSlideUserFailedListener= object : SlideToActView.OnSlideUserFailedListener {
//            override fun onSlideFailed(view: SlideToActView, isOutside: Boolean) {
//                if(loadercancel.visibility == View.VISIBLE)
//                    commsnackbaralert(getString(R.string.slideerror))
//                else
//                    tripupdate()
//            }
//        }
//
//        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                val value = dataSnapshot.getValue()
//                Log.i("checkingstauta", "Value is: $value")
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Failed to read value
//            }
//        })
//
//
////        bottom_sheet_layout.setOnProgressListener {  }
//        bottum_nested1.setOnClickListener {
//
//        }
//
//        bottum_nested.setOnClickListener { }
//        chatcustomer1.setOnClickListener { _ -> chatpage() }
//        cancel1.setOnClickListener { _ -> cleartrip() }
////        imageViewArrow.setOnClickListener { _ -> bottom_sheet_layout.toggle() }
////        onlineofflinetext.setOnClickListener { _ -> bottom_sheet_layout.toggle() }
////        profileeclick.setOnClickListener {  _ -> bottom_sheet_layout.toggle() }
////        driverphoto.setOnClickListener {  _ -> bottom_sheet_layout.toggle() }
//        callcustomer.setOnClickListener { _ -> callcustomer() }
//        navigate1.setOnClickListener { _ -> navigatetoggolemap() }
//        ctmap.setOnClickListener { _ -> viewModel.zoommap(mContext) }
//        ctmap2.setOnClickListener { _ -> viewModel.zoommap(mContext) }
//        settings2.setOnClickListener { _ -> movetosettingpage()
//
//
//        }
//    }
//    //Intlizing Value
//    fun intilizecommonvalue()
//    {
//        mSessionManager = SessionManager(mContext!!)
//        routeLatLngArray = ArrayList()
//        mHelper= onrideDbHelper(applicationContext)
//        mHelperslan=LanguageDb(applicationContext)
//        dataBase=mHelper!!.getWritableDatabase()
//        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
//        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
//        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
//        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
//        myLocationGoogleMap = TrackingPageGooglemap(mContext)
//
////        bottom_sheet_layout.setOnProgressListener { progress -> rotateArrow(progress)}
//    }
//    //Sheet listener
//    private fun rotateArrow(progress: Float)
//    {
//        imageViewArrow.rotation = -180 * progress
//        if(progress > 0.01f)
//        {
//            if(closed == 0)
//            {
//                navigate.visibility = View.VISIBLE
//                ctmap.visibility = View.VISIBLE
//                settings.visibility = View.GONE
//                profileeclick.visibility = View.GONE
//                closed = 1
//            }
//        }
//        else
//        {
//            if(progress == 0.00f)
//            {
//                if(closed == 1)
//                    closed = 0
//                ctmap.visibility = View.VISIBLE
//                settings.visibility = View.GONE
//                navigate.visibility = View.VISIBLE
//                profileeclick.visibility = View.GONE
//            }
//        }
//        if(progress > 0.5f)
//            slidebutton.visibility = View.VISIBLE
//        else
//            slidebutton.visibility = View.VISIBLE
//    }
//    //Cancel Button Operation
//    fun cleartrip()
//    {
//        if(hidebuttons == 0)
//        {
//            if (loadercancel.getVisibility() == View.VISIBLE)
//                commsnackbaralert(getString(R.string.loading))
//            else
//            {
//                if(!canelreasonhasbeenstored.equals(""))
//                    viewModel.cancelreasonresponsesplit(mContext,canelreasonhasbeenstored)
//                else
//                {
//                    loadercancel.visibility = View.VISIBLE
//                    viewModel.getcanclreason(mContext,rideid)
//                }
//            }
//        }
//    }
//    //Map Intilize Part
//    private fun initializeMap()
//    {
//        try
//        {
//            mMapFragment = supportFragmentManager
//                    .findFragmentById(R.id.firstpagemap) as SupportMapFragment
//            mMapFragment.getMapAsync(this)
//        }
//        catch (e: Exception)
//        {
//            e.printStackTrace()
//        }
//    }
//
//    //Map is ready
//    @InternalCoroutinesApi
//    override fun onMapReady(mGoogleMap: GoogleMap?)
//    {
//        googlemap = mGoogleMap
//        myLocationGoogleMap!!.addTo(googlemap,driver_marker_resized,1)
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//        {
//            if (ContextCompat.checkSelfPermission(this,
//                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                mGoogleMap!!.isMyLocationEnabled = false
//            }
//        }
//        else
//            mGoogleMap!!.isMyLocationEnabled = false
//        mGoogleMap!!.getUiSettings().setRotateGesturesEnabled(false)
//        setMapStyle(mGoogleMap!!)
//        viewModel.getnightmodeoption(mContext)
//        mGoogleMap!!.getUiSettings().setMapToolbarEnabled(false)
//        viewModel.getsplitrepsonse(mContext)
//        handleData()
//
//    }
//    @InternalCoroutinesApi
//    fun repeatFunpayment(): Job {
//        val scope = CoroutineScope(Dispatchers.Default)
//        return scope.launch {
//            while(isActive) {
//                startServicePaymenttmoneycheckride()
//                delay(5000)
//            }
//        }
//    }
//    //Map Style Day Mode
//    private fun setMapStyle(googleMap: GoogleMap)
//    {
//        try
//        {
//            var success = googleMap.setMapStyle(
//                    MapStyleOptions.loadRawResourceStyle(
//                            this, R.raw.ubermapstyle))
//            if(routeLatLngArray.size != 0)
//            {
//                fitmapzoom()
//                if(foregroundPolyline != null)
//                {
//                    foregroundPolyline!!.remove()
//                }
//                val optionsForeground = PolylineOptions().addAll(routeLatLngArray).color(ContextCompat.getColor(applicationContext,R.color.mapbluee)).width(dotsize)
//                foregroundPolyline = googlemap!!.addPolyline(optionsForeground)
//            }
//        } catch (e: Resources.NotFoundException) {
//        } catch (e1: Exception) {
//        }
//    }
//    //Map Style Night Mode
//    private fun nightmode(googleMap: GoogleMap)
//    {
//        try
//        {
//            var success = googleMap.setMapStyle(
//                    MapStyleOptions.loadRawResourceStyle(
//                            this, R.raw.night_map_style))
//            viewModel.nightmodeoption(mContext,true)
//            if(routeLatLngArray.size != 0)
//            {
//                if(foregroundPolyline != null)
//                {
//                    foregroundPolyline!!.remove()
//                }
//                val optionsForeground = PolylineOptions().addAll(routeLatLngArray).color(ContextCompat.getColor(applicationContext,R.color.innergreen)).width(dotsize)
//                foregroundPolyline = googlemap!!.addPolyline(optionsForeground)
//                fitmapzoom()
//            }
//        } catch (e: Resources.NotFoundException) {
//        } catch (e1: Exception) {
//        }
//    }
//    //Viewmodel Observer Part
//    fun initviewmodel()
//    {
//        viewModel = ViewModelProviders.of(this).get(TrackingPageViewModel::class.java)
//        viewModel.getsplitrepsonse(mContext)
//        viewModel.getdataforride(mContext)
//        viewModel.getnavigationoption(mContext)
//        viewModel.zoomlevelobserver().observe(this, Observer {
//            if(googlemap != null)
//                googlemap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.get(0).zoomlatitude!!.toDouble(),it.get(0).zoomlongitude!!.toDouble()), 15.5f))
//        })
//        viewModel.unreadchatobserver().observe(this, Observer {
//            if(it==0)
//                messagecount.visibility = View.INVISIBLE
//            else
//            {
//                messagecount.visibility = View.VISIBLE
//            }
//        })
//        viewModel.ratingaddedsuccessfullyobserver().observe(this, Observer {
//            if (it == 1)
//            {
//                if(ratingdialog != null)
//                {
//                    if(ratingdialog!!.isShowing)
//                        ratingdialog!!.dismiss()
//                }
//                stopforegroundservice()
//                viewModel.clearridedtaa(mContext)
//                movetomainpageafterrating()
//            }
//            else
//                commontoast(getString(R.string.failedproblem))
//        })
//        viewModel.navigatevalueobserver().observe(this, Observer {
//            mapchoosen=it
////            if (it.equals("1"))
//////                navigate1.setImageResource(R.drawable.navigationicon)
////            else
//////                navigate1.setImageResource(R.drawable.deprecated_profiles_ic_waze)
//        })
//        viewModel.esttimeobserver().observe(this, Observer {
//            runOnUiThread {
//                esttime.text = it
//            }
//        })
//        viewModel.reroutingoptionsettingsobserver().observe(this, Observer {
//            if(it)
//                turnoffrerouting = 1
//            else
//                turnoffrerouting = 0
//        })
//        viewModel.nighmodesettingsobeser().observe(this, Observer {
//            mapmode=it
//            if(it)
//            {
//                if(googlemap!=null)
//                    nightmode(googlemap!!)
//            }
//            else
//            {
//                if(googlemap!=null)
//                    setMapStyle(googlemap!!)
//            }
//        })
//        viewModel.cancelviewmodellObserver().observe(this, Observer {
//            runOnUiThread {
//                cancelreaon(it!!)
//            }
//        })
//        viewModel.cancelfailedobserver().observe(this, Observer {
//            if(it == 1)
//                movetomainpage()
//            else
//                AppUtils.commonerrorsheet(mContext,getString(R.string.failed),getString(R.string.cancellatinfailed))
//        })
//        viewModel.esthoursobserver().observe(this, Observer {
//            runOnUiThread {
//                time.text = it
//            }
//        })
//        viewModel.reroutingcallobserver().observe(this, Observer {
//            if(it == 1)
//            {
//                if(pickupLatLng != null)
//                {
//                    isRouteAvail = false
//                    reroutingsuccessorfailed = 1
//                    routeapihitted = 1
//                    reroutingprocess(pickupLatLng!!)
//                }
//            }
//        })
//        viewModel.oncashrecivedobsercver().observe(this, Observer {
//            if(it == 1)
//            {
//                if(collectcash != null)
//                {
//                    if(collectcash!!.isShowing)
//                        collectcash!!.dismiss()
//                }
//                viewModel.getsplitrepsonse(mContext)
//            }
//            else
//                commontoast(getString(R.string.failedproblem))
//        })
//        viewModel.tripcancelledtripobserver().observe(this, Observer {
//            if(it == 0)
//                viewModel.clertripbasedonurl(mContext)
//            else
//                viewModel.getsplitrepsonse(mContext)
//        })
//        viewModel.stoplistobserver().observe(this, Observer {
//            stopslistdisplay(it!!)
//        })
//        viewModel.estDistanceeobserver().observe(this, Observer {
//            runOnUiThread {
//                kms.text = it
//            }
//            if(globalridestatus.equals("2") && !it.contains("."))
//            {
//                var meterless:Int=it.split(" ")[0].toInt()
//                if(meterless < 500)
//                {
//                    if(!rideid.equals("") && waithitted == 0 && !mSessionManager.gethittedrideid().equals(rideid))
//                    {
//                        waithitted = 1
//                        if(AppUtils.isNetworkConnected(mContext))
//                            viewModel.hitnearingapicall(mContext,rideid)
//                    }
//                }
//            }
//        })
//    }
//    private fun startingforegroundservice(username:String,useridtosend:String,ride_status:String)
//    {
//        val serviceClass = FService::class.java
//        val serviceIntent = Intent(applicationContext, serviceClass)
//        if (!isServiceRunning(serviceClass))
//        {
//            serviceIntent.putExtra("From", getString(R.string.riding_with)+" "+username);
//            serviceIntent.putExtra("rideid", rideid);
//            serviceIntent.putExtra("useridtosend", useridtosend);
//            serviceIntent.putExtra("ride_status", ride_status);
//            startService(serviceIntent)
//            bindService(serviceIntent, myConnection, Context.BIND_AUTO_CREATE)
//        }
//        else
//            bindService(serviceIntent, myConnection, Context.BIND_AUTO_CREATE)
//
//                startUpdateForegroundService()
//    }
//    private fun stopforegroundserviceontripstatsus()
//    {
//        stopforegroundservice()
//    }
//    // start and stop arrival service
//    private fun startarrivalforegroundservice(username:String,pickuplocation:String,useridtosend:String,ride_status:String)
//    {
//        val serviceClass = AService::class.java
//        val serviceIntent = Intent(applicationContext, serviceClass)
//        if (!isServiceRunning(serviceClass))
//        {
//            serviceIntent.putExtra("From", getString(R.string.beforeridebegin)+" "+username);
//            serviceIntent.putExtra("rideid", rideid)
//            serviceIntent.putExtra("userid", useridtosend)
//            serviceIntent.putExtra("pickuplocation", pickuplocation)
//            serviceIntent.putExtra("ride_status", ride_status);
//            startService(serviceIntent)
//            bindService(serviceIntent, arrivalConnection, Context.BIND_AUTO_CREATE)
//        }
//        else
//            bindService(serviceIntent, arrivalConnection, Context.BIND_AUTO_CREATE)
//    }
//    private fun stoparrivalforegroundservice()
//    {
//        val serviceClass = AService::class.java
//        val serviceIntent = Intent(applicationContext, serviceClass)
//        try {
//            unbindService(arrivalConnection)
//        } catch (e: IllegalArgumentException) {
//        }
//        if (isServiceRunning(AService::class.java)) {
//            stopService(serviceIntent)
//        }
//    }
//    //Split response of all api
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun doThis(intentServiceResult: ChatIntentServiceResult)
//    {
//        viewModel.getchatunreadcount(mContext,rideid)
//    }
//
//    //Trip Finished
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun doThis1(tripStatus: String)
//    {
//        if (tripStatus=="Trip Finished"){
//            onResume()
//        }
//    }
//
//    //Split response of all api
//    @InternalCoroutinesApi
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun doThis(intentServiceResult: IntentServiceResult)
//    {
//        var apiName: String = intentServiceResult.apiName
//        var driverLatLng: LatLng? = null
//        //Getting Location of Driver
//        if (apiName.equals(getString(R.string.gettingridecurrentlocation))) {
//            var bothlatandlong: String = intentServiceResult.resultValue
//            val latlongvalue = bothlatandlong.split(",").toTypedArray()
//            var ctlat:Double = latlongvalue[0].toDouble()
//            var ctlong:Double = latlongvalue[1].toDouble()
//            driverLatLng = LatLng(ctlat, ctlong)
//            if(routeLatLngArray.size != 0 && foregroundPolyline != null && turnoffrerouting == 1)
//            {
//                var  latLng =  LatLng(ctlat, ctlong)
//                try
//                {
//                    viewModel.checkdriverisonpathornot(mContext,foregroundPolyline!!,reroutingsuccessorfailed,latLng)
//                }
//                catch (io:java.lang.Exception)
//                {
//                }
//            }
//            if(sucesofdropmarker == 0)
//            {
//                if(pickupLatLng != null  && googlemap != null)
//                    dropmarker(pickupLatLng!!)
//
//
//            }
//
//            if(pickupLatLng != null && boundfixed == 0)
//            {
//                boundfixed = 1
//                fixboundsbasedonpickupanddrop(pickupLatLng!!,driverLatLng!!)
//            }
//            if(pickupLatLng != null && routeapihitted == 0 && googlemap != null)
//            {
//                routeapihitted = 1
//                startedtrackingyoucandrawroutehere(pickupLatLng!!)
//            }
//            if(pickupLatLng != null)
//                viewModel?.etacalculation(mContext,driverLatLng!!, pickupLatLng!!)
//        }
//        //Get Ride Details
//        else  if (apiName.equals(getString(R.string.ridedetails)))
//        {
//            iv_line.visibility = View.GONE
//            var rasponse: String = intentServiceResult.resultValue
//            if(rasponse.equals("0"))
//                viewModel.cleartripdetails2(mContext)
//            else
//                viewModel.getsplitrepsonse(mContext)
//        }
//        //Get hitagain
//        else  if (apiName.equals(getString(R.string.hitnearingapi)))
//        {
//            var rasponse: String = intentServiceResult.resultValue
//            if(rasponse.equals("0"))
//            {
//                waithitted = 0
//            }
//            else
//            {
//                if(voicenearingg == 0)
//                {
//                    voicenearingg++
//                    viewModel.voiceofnearing(mContext)
//                }
//            }
//        }
//        //Route Failed Listener
//        else  if (apiName.equals(getString(R.string.mapfailedtointernet)))
//        {
//            var rasponse: String = intentServiceResult.resultValue
//            if(rasponse.equals("0"))
//                routeapihitted = 0
//        }
//        //Cancel Reason Split Api
//        else  if (apiName.equals(getString(R.string.cancelreason)))
//        {
//            loadercancel.visibility = View.GONE
//            var rasponse: String = intentServiceResult.resultValue
//            if(rasponse.equals("failed"))
//                commsnackbaralert(getString(R.string.failedproblem))
//            else
//            {
//                canelreasonhasbeenstored = rasponse
//                viewModel.cancelreasonresponsesplit(mContext,rasponse)
//            }
//        }
//        //Cash Recived Splitation
//        else  if (apiName.equals(getString(R.string.cashreceived)))
//        {
//            var rasponse: String = intentServiceResult.resultValue
//            if(rasponse.equals("failed"))
//                commsnackbaralert(getString(R.string.failedproblem))
//            else
//                viewModel.splitbasedoncashresponse(mContext,rasponse)
//        }
//        // Rating response Splitation
//        else  if (apiName.equals(getString(R.string.skiprating)))
//        {
//            if(loadrrating != null)
//                loadrrating!!.visibility = View.GONE
//            var rasponse: String = intentServiceResult.resultValue
//            if(rasponse.equals("failed"))
//            {
//                viewModel.skipnow(mContext,rasponse)
//            }
//            else
//                viewModel.skipnow(mContext,rasponse)
//        }
//        else  if (apiName.equals(getString(R.string.fcmcancel)))
//        {
//            var rideiidd: String = intentServiceResult.resultValue
//            if(rideid.equals(rideiidd)) userridecancelledpagedesign()
//        }
//        else  if (apiName.equals(getString(R.string.cancelthisride)))
//        {
//            //Clear trip details
//            loadercancel.visibility = View.GONE
//            var rasponse: String = intentServiceResult.resultValue
//            if(rasponse.equals("failed"))
//                commsnackbaralert(getString(R.string.cancellatinfailed))
//            else
//                viewModel.cleartripdetails(mContext,rasponse)
//        }
//        else  if (apiName.equals(getString(R.string.tripupdate)))
//        {
//            //Slider Update
//            loadercancel.visibility = View.GONE
//            var rasponse: String = intentServiceResult.resultValue
//            if(rasponse.equals("failed"))
//                commsnackbaralert(getString(R.string.failedproblem))
//            else
//            {
//                routeapihitted = 0
//                viewModel.splittripupdate(mContext,rasponse)
//            }
//        }
//        else  if (apiName.equals("canceltri")) {
//            //Slider Update
//            loadercancel.visibility = View.GONE
//            var rasponse: String = intentServiceResult.resultValue
//            if(rasponse.equals("failed"))
//                commsnackbaralert(getString(R.string.failedproblem))
//            else
//            {
//                movetomainpage()
//            }
//        }else  if (apiName.equals("rate_user")) {
//            Log.d("kkipay1220","checkimhhss")
//            ratingpage.visibility = View.GONE
//            ratingpagedialog("",mSessionManager.getUserDetails()[mSessionManager.driver_name]!!,"","")
//
//        }else  if (apiName.equals(getString(R.string.tmoneycheckride))) {
//            var finalresponse: String = intentServiceResult.resultValue
//            if (finalresponse == "failed") {
//
//            } else {
//                Log.d("mchekingtmonesd",finalresponse)
//                PaymentsuccestmoneyApiParser(finalresponse)
//            }
//        }else  if (apiName.equals("collect_cash")) {
//            viewModel.getdataforride(mContext)
//
//            ratingpage.visibility = View.GONE
//
//
//        }
//    }
//
//    @InternalCoroutinesApi
//    private fun handleData(){
//        viewModel.driveretailsviewmodellObserver().observe(this, Observer {
//            var userphoto:String = it.get(0).user_image.toString()
//            var rating:String = it.get(0).user_review.toString()
//            var usernames:String = it.get(0).user_name.toString()
//            usernmaegetting=usernames
//            userimagesetting=userphoto
//            var paymethodd:String = it.get(0).payment_method.toString()
//            var ride_status:String = it.get(0).ride_status.toString()
//            globalridestatus=ride_status
//            otpstatusvalue=mSessionManager.getbegin_otp()
//            Log.d("dkjsdfde",paymethodd)
//            Log.d("dfhsbtgsere4",ride_status)
//            Log.d("mchwowbber","android cecj")
//
//
//
//            if(!paymethodd.equals(""))
//                paymenttype.text = paymethodd
//            rideid = it.get(0).ride_id.toString()
//            useridtosend= it.get(0).user_id.toString()
//            if(!rideid.equals(""))
//                viewModel.getchatunreadcount(mContext,rideid)
//            if(!currentridestatus.equals(ride_status))
//            {
//                currentridestatus = ride_status
//                customerphno= it.get(0).phone_number.toString()
//                mSessionManager.setcustomerphno(customerphno)
//                bookedtime.text =  getString(R.string.booked_at)+it.get(0).pickup_time.toString()
//                if(!it.get(0).pickup_lat.equals("") && !it.get(0).pickup_lat.equals("0") && !it.get(0).pickup_lat.equals("0.0"))
//                {
//                    var pickup_lat:Double = it.get(0).pickup_lat!!.toDouble()
//                    var pickup_lon:Double = it.get(0).pickup_lon!!.toDouble()
////                    var pickup_lat:Double = 13.0429725
////                    var pickup_lon:Double = 80.2735487
//                    if (ride_status.equals("Confirmed")){
//                        ride_status="1"
//                        kmebike.setText(mSessionManager.getmileage()+"km")
//                        percentebike.setText(mSessionManager.getpercentage()+"%")
//                        otp_lay.visibility=View.GONE
//                        esttime_layout.visibility=View.VISIBLE
//                    }else if (ride_status.equals("Arrived")){
//                        ride_status="2"
//                        otp_lay.visibility=View.VISIBLE
//                        esttime_layout.visibility=View.GONE
//
//                        kmebike.setText(mSessionManager.getmileage()+"km")
//                        percentebike.setText(mSessionManager.getpercentage()+"%")
//                    }else if (ride_status.equals("Onride")){
//                        ride_status="3"
//                        kmebike.setText(mSessionManager.getmileage()+"km")
//                        percentebike.setText(mSessionManager.getpercentage()+"%")
//                        otp_lay.visibility=View.GONE
//                        esttime_layout.visibility=View.VISIBLE
//
//                    }else if (ride_status.equals("Finished")){
//                        ride_status="4"
//                        kmebike.setText(mSessionManager.getmileage()+"km")
//                        percentebike.setText(mSessionManager.getpercentage()+"%")
//                        otp_lay.visibility=View.GONE
//
//                    }else if (ride_status.equals("Completed")){
//                        kmebike.setText(mSessionManager.getmileage()+"km")
//                        percentebike.setText(mSessionManager.getpercentage()+"%")
//                        ride_status="5"
//                    }
//                    Log.d("dedc2fdfedaf",ride_status)
//                    if(ride_status.equals("1"))
//                    {
//                        startarrivalforegroundservice(usernmaegetting,it.get(0).pickup_location.toString(),useridtosend,ride_status)
//                        stopforegroundserviceontripstatsus()
//                        slidebutton.text = getkey("slide").toString()
//                        pickup_address.visibility=View.VISIBLE
//                        dropup_address.visibility=View.GONE
//                        pickupLatLng = LatLng(pickup_lat, pickup_lon)
//                        lateinit var mSessionManager: SessionManager
//                        mSessionManager = SessionManager(mContext!!)
//                        var driverLatLng = LatLng(mSessionManager!!.getOnlineLatitiude().toDouble(), mSessionManager!!.getOnlineLongitude().toDouble())
//
////                        var driverLatLng = LatLng(mSessionManager!!.getOnlineLatitiude().toDouble(), mSessionManager!!.getOnlineLongitude().toDouble())
//                        if(pickupLatLng != null && routeapihitted == 0)
//                            viewModel?.etacalculation(mContext,driverLatLng!!, pickupLatLng!!)
//                        var pickup_lat:Double = it.get(0).pickup_lat!!.toDouble()
//                        var pickup_lon:Double = it.get(0).pickup_lon!!.toDouble()
//                        var lat: String = mSessionManager.getOnlineLatitiude()
//                        var long: String = mSessionManager.getOnlineLongitude()
//                        dest_drop_lat=lat!!.toDouble()
//                        dest_drop_lng=long!!.toDouble()
//                        var latLngList:ArrayList<LatLng> = ArrayList()
//                        pickupLatLng = LatLng(pickup_lat, pickup_lon)
//                        drop_latlng = LatLng(dest_drop_lat,dest_drop_lng)
//                        latLngList.add(pickupLatLng!!)
//                        latLngList.add(drop_latlng!!)
//                        drawply= FindRoute(this)
//                        drawply?.setUpPolyLine(googlemap!!,this,pickupLatLng,drop_latlng,latLngList,true)
//                        googlemap?.addMarker(MarkerOptions().position(pickupLatLng!!).icon(BitmapDescriptorFactory.fromResource(R.drawable.dropicon)))!!
//                        googlemap?.addMarker(MarkerOptions().position(drop_latlng!!).icon(BitmapDescriptorFactory.fromResource(R.drawable.pickup_map_marker)))!!
//
////                        dest_drop_lat=pickup_lat
////                        dest_drop_lng=pickup_lon
//
//
////                        dropmarker(pickupLatLng!!)
//
//
//                    }
//                    else if(ride_status.equals("2"))
//                    {
//                        startarrivalforegroundservice(usernmaegetting,it.get(0).pickup_location.toString(),useridtosend,ride_status)
//                        stopforegroundserviceontripstatsus()
//                        isRouteAvail = false
//                        if(routeLatLngArray.size != 0)
//                        {
//                            if(foregroundPolyline != null)
//                                foregroundPolyline!!.remove()
//                        }
//                        otp_lay.visibility= View.VISIBLE
//
//                        slidebutton.text = getkey("slide_to_begin").toString()
//
//                        repeatFun = repeatFun()
//                        mSessionManager.setnormalride_completed("")
//
//                        if(otpstatusvalue != null)
//                        {
////                            var firststring:String = otpstatusvalue.substring(0,1)
////                            otp1?.setText(firststring)
////                            var second:String = otpstatusvalue.substring(1,2)
////                            otp2?.setText(second)
////                            var third:String = otpstatusvalue.substring(2,3)
////                            otp3?.setText(third)
////                            var four:String = otpstatusvalue.substring(3,4)
////                            otp4?.setText(four)
////                            otp4.setSelection(otp4.getText().length)
//
//                            otp1.setText(otpstatusvalue.get(0).toString())
//                            otp2.setText(otpstatusvalue.get(1).toString())
//                            otp3.setText(otpstatusvalue.get(2).toString())
//                            otp4.setText(otpstatusvalue.get(3).toString())
//
//                        }
//                        else
//                        {
//                            otp1.requestFocus()
//                        }
//                        pickup_address.visibility=View.VISIBLE
//                        dropup_address.visibility=View.GONE
//
//                        var pickup_lat:Double = it.get(0).pickup_lat!!.toDouble()
//                        var pickup_lon:Double = it.get(0).pickup_lon!!.toDouble()
////                        var pickup_lat:Double = 13.0429725
////                        var pickup_lon:Double = 80.2735487
//                        pickupLatLng = LatLng(pickup_lat, pickup_lon)
//                        lateinit var mSessionManager: SessionManager
//                        mSessionManager = SessionManager(mContext!!)
//                        var driverLatLng = LatLng(mSessionManager!!.getOnlineLatitiude().toDouble(), mSessionManager!!.getOnlineLongitude().toDouble())
//                        if(pickupLatLng != null && routeapihitted == 0 && googlemap != null)
//                        {
//                            viewModel?.etacalculation(mContext,driverLatLng!!, pickupLatLng!!)
//                            routeapihitted = 1
//                            startedtrackingyoucandrawroutehere(pickupLatLng!!)
//                        }
////
//                        dest_drop_lat=it.get(0).drop_lat!!.toDouble()
//                        dest_drop_lng=it.get(0).drop_lon!!.toDouble()
//
//                        var latLngList:ArrayList<LatLng> = ArrayList()
//                        drop_latlng = LatLng(dest_drop_lat,dest_drop_lng)
//                        latLngList.add(pickupLatLng!!)
//                        latLngList.add(drop_latlng!!)
//                        drawply= FindRoute(this)
//                        drawply?.clearpolyline()
//
//                        drawply?.setUpPolyLine(googlemap!!,this,pickupLatLng,drop_latlng,latLngList,true)
//                        googlemap?.addMarker(MarkerOptions().position(drop_latlng!!).icon(BitmapDescriptorFactory.fromResource(R.drawable.dropicon)))!!
////                        dropmarker(pickupLatLng!!)
//
//                    }
//                    else if(ride_status.equals("3"))
//                    {
//                        repeatFun = repeatFun()
//
//                        stoparrivalforegroundservice()
//                        startingforegroundservice(usernmaegetting,useridtosend,ride_status)
//                        hideonbegin()
//                        otp_lay.visibility= View.GONE
//                        slidebutton.text = getkey("slide_to_end").toString()
//                        kmebike.setText(mSessionManager.getmileage()+"km")
//                        percentebike.setText(mSessionManager.getpercentage()+"%")
//                        pickup_address.visibility=View.GONE
//                        dropup_address.visibility=View.VISIBLE
//                        var pickup_lat:Double = it.get(0).drop_lat!!.toDouble()
//                        var pickup_lon:Double = it.get(0).drop_lon!!.toDouble()
//                        pickupLatLng = LatLng(pickup_lat, pickup_lon)
//                        lateinit var mSessionManager: SessionManager
//                        mSessionManager = SessionManager(mContext!!)
//                        var driverLatLng = LatLng(mSessionManager!!.getOnlineLatitiude().toDouble(), mSessionManager!!.getOnlineLongitude().toDouble())
//                        if(pickupLatLng != null && routeapihitted == 0)
//                            viewModel?.etacalculation(mContext,driverLatLng!!, pickupLatLng!!)
//
////                        dest_drop_lat=pickup_lat
////                        dest_drop_lng=pickup_lon
////                        dropmarker(pickupLatLng!!)
//
//                        dest_drop_lat=it.get(0).drop_lat!!.toDouble()
//                        dest_drop_lng=it.get(0).drop_lon!!.toDouble()
//
//                        var latLngList:ArrayList<LatLng> = ArrayList()
//                        drop_latlng = LatLng(dest_drop_lat,dest_drop_lng)
//                        latLngList.add(pickupLatLng!!)
//                        latLngList.add(drop_latlng!!)
//                        drawply= FindRoute(this)
//                        drawply?.setUpPolyLine(googlemap!!,this,pickupLatLng,drop_latlng,latLngList,true)
//                    }
//                    else if(ride_status.equals("4")) {
//                        if(paymethodd.equals("Cash")){
////                            ratingpage.visibility=View.VISIBLE
//                            viewModel.getdataforride(mContext)
//                            Log.d("checkincash_colledrg","cash_colledr")
//
//
////                            collectbelowamountfromcustomer(it.get(0).tripamount.toString(),it.get(0).currency_code.toString(),usernames,it.get(0).pickup_location.toString(),it.get(0).drop_location.toString())
//
//                        }
//
//                        if(it.get(0).receive_cash.equals("1")) {
//                            ratingpage.visibility=View.GONE
//
//                            repeatFun = repeatFun()
//
//                            repeatFun.cancel()
//                            Log.d("mpickup2525",it.get(0).pickup_location.toString()+"------"+it.get(0).drop_location.toString())
//                            collectbelowamountfromcustomer(it.get(0).tripamount.toString(),it.get(0).currency_code.toString(),usernames,it.get(0).pickup_location.toString(),it.get(0).drop_location.toString())
//
//                        }else if(paymethodd.equals("kkiapay")){
//                            ratingpage.visibility=View.VISIBLE
//                            repeatFunpayment = repeatFunpayment()
//
//
////                            collectbelowamountfromcustomer(it.get(0).tripamount.toString(),it.get(0).currency_code.toString(),usernames,it.get(0).pickup_location.toString(),it.get(0).drop_location.toString())
//
//                        }else if(paymethodd.equals("tmoney")){
//                            ratingpage.visibility=View.VISIBLE
//                            repeatFunpayment = repeatFunpayment()
//
//
////                            collectbelowamountfromcustomer(it.get(0).tripamount.toString(),it.get(0).currency_code.toString(),usernames,it.get(0).pickup_location.toString(),it.get(0).drop_location.toString())
//
//                        }else if(paymethodd.equals("wallet")){
//                            ratingpage.visibility=View.VISIBLE
////                            repeatFunpayment = repeatFunpayment()
//
//
////                            collectbelowamountfromcustomer(it.get(0).tripamount.toString(),it.get(0).currency_code.toString(),usernames,it.get(0).pickup_location.toString(),it.get(0).drop_location.toString())
//
//                        }
//                    }
//                    else if(ride_status.equals("5"))
//                        ratingpage.visibility=View.GONE
//
//                    //calncel
//                    /* else if(ride_status.equals("6") || ride_status.equals("8") )
//                         ratingpagedialog(userphoto,usernames, it.get(0).tripamount.toString(),it.get(0).currency_code.toString())
//                   */  else if(ride_status.equals("5") || ride_status.equals("6") || ride_status.equals("8"))
//                        ratingpagedialog(userphoto,usernames, it.get(0).tripamount.toString(),it.get(0).currency_code.toString())
//
//                }
//                username.text = usernames
//                userrating.setRating(rating.toFloat())
//                if(userphoto.startsWith("http"))
//                {
//                    Glide.with(mContext)
//                            .asBitmap()
//                            .apply(RequestOptions().override(60, 60))
//                            .load(userphoto)
//                            .into(object : CustomTarget<Bitmap>() {
//                                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                                    runOnUiThread {
//                                        userphotos.setImageBitmap(resource)
//                                        driverphoto.setImageBitmap(resource)
//                                    }
//                                }
//                                override fun onLoadCleared(placeholder: Drawable?)
//                                {
//                                }
//                            })
//                }
//            }
//        })
//
//    }
//
//
//    @InternalCoroutinesApi
//    fun repeatFun(): Job {
//        val scope = CoroutineScope(Dispatchers.Default)
//        return scope.launch {
//            while(isActive) {
//                Log.d("checkings","awsomeno225")
//                rideapi()
//                travelhistory()
//                delay(10000)
//            }
//        }
//    }
//    fun travelhistory(){
//        var current_lat=mSessionManager!!.getOnlineLatitiude()
//        var current_long=mSessionManager!!.getOnlineLongitude()
//        Log.d("current_lats",current_lat+"   "+current_long)
//        val aCalendar = Calendar.getInstance()
//        val aDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//        val aCurrentDate = aDateFormat.format(aCalendar.getTime())
//        var values: ContentValues = ContentValues()
//        values.put(onrideDbHelper.latitude, current_lat)
//        values.put(onrideDbHelper.longitude, current_long)
//        values.put(onrideDbHelper.time, aCurrentDate)
//        dataBase!!.insert(onrideDbHelper.TABLE_NAME_FOR_NORMAL_RIDE, null, values)
//
//    }
//    private fun getkey(key: String): String? {
//        return mHelperslan.getvalueforkey(key)
//    }
//
//    fun startServicePaymenttmoneycheckride() {
//        val serviceClass = commonapifetchservice::class.java
//        val intent = Intent(applicationContext, serviceClass)
//        intent.putExtra("api_name", getString(R.string.tmoneycheckride))
//        intent.putExtra("rideid", rideid)
//        startService(intent)
//    }
//
//
//    fun rideapi(){
//        mride_completeds=mSessionManager.getnormalride_completed()
//        if (mride_completeds.equals("ride_completed")){
//            viewModel.getdataforride(mContext)
//        }
//
//    }
//    //Fixing Bound Based on  pickup and drop
//    private fun fixboundsbasedonpickupanddrop(pickupLatLng: LatLng,driverLatLng:LatLng)
//    {
//        var builder:LatLngBounds.Builder =  LatLngBounds.Builder()
//        builder.include(pickupLatLng)
//        builder.include(driverLatLng)
//
//        var bounds:LatLngBounds = builder.build()
//        var padding:Int = 0
//        var  cu:CameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
//        if(googlemap != null)
//        {
//            googlemap!!.setPadding(50, 50, 50, 50)
//            googlemap!!.moveCamera(cu)
//        }
//    }
//    //ReRoute Result
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun rerouteResult(intentServiceRouteResult: IntentServiceReroutingRouteResult)
//    {
//        var result_code = intentServiceRouteResult.result
//        wayPointsBuilder = intentServiceRouteResult.wayPoints
//        val routeLatLngPoints = intentServiceRouteResult.routeLatLngArray
//        routeLatLngArray.clear()
//        var pickup_lat = mSessionManager.getOnlineLatitiude().toDouble()
//        var pickup_lng = mSessionManager.getOnlineLongitude().toDouble()
//        routeLatLngArray.add(LatLng(pickup_lat, pickup_lng))
//        for (i in 0 until routeLatLngPoints.size)
//        {
//            routeLatLngArray.add(routeLatLngPoints.get(i))
//        }
//        routeLatLngArray.add(LatLng(dest_drop_lat, dest_drop_lng))
//        if (result_code == Activity.RESULT_OK)
//        {
//            if(routeLatLngArray.size != 0 && foregroundPolyline != null)
//            {
//                if(foregroundPolyline != null)
//                {
//                    foregroundPolyline!!.remove()
//                }
//                reroutingsuccessorfailed = 0
//                routeapihitted = 1
//
//                if(googlemap != null)
//                {
//                    if(isRouteAvail == false)
//                    {
//                        isRouteAvail = true
//                        lateinit var optionsForeground: PolylineOptions
//                        if(mapmode)
//                            optionsForeground = PolylineOptions().addAll(routeLatLngArray).color(ContextCompat.getColor(applicationContext,R.color.innergreen)).width(dotsize)
//                        else
//                            optionsForeground = PolylineOptions().addAll(routeLatLngArray).color(ContextCompat.getColor(applicationContext,R.color.mapbluee)).width(dotsize)
//
//                        foregroundPolyline = googlemap!!.addPolyline(optionsForeground)
//                    }
//                }
//            }
//        }
//        else if (result_code == Activity.RESULT_CANCELED)
//        {
//            reroutingsuccessorfailed = 0
//            routeapihitted = 0
//        }
//
//    }
//    //Route Result
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun routeResult(intentServiceRouteResult: IntentServiceRouteResult)
//    {
//        var result_code = intentServiceRouteResult.result
//        wayPointsBuilder = intentServiceRouteResult.wayPoints
//        val routeLatLngPoints = intentServiceRouteResult.routeLatLngArray
//        routeLatLngArray.clear()
//        var pickup_lat = mSessionManager.getOnlineLatitiude().toDouble()
//        var pickup_lng = mSessionManager.getOnlineLongitude().toDouble()
//        routeLatLngArray.add(LatLng(pickup_lat, pickup_lng))
//        for (i in 0 until routeLatLngPoints.size)
//        {
//            if(routeLatLngPoints.get(i).latitude != 0.0 && routeLatLngPoints.get(i).longitude != 0.0)
//                routeLatLngArray.add(routeLatLngPoints.get(i))
//        }
//        routeLatLngArray.add(LatLng(dest_drop_lat, dest_drop_lng))
//        if (result_code == Activity.RESULT_OK)
//        {
//            reroutingsuccessorfailed = 0
//            routeapihitted = 1
//            var builder:LatLngBounds.Builder =  LatLngBounds.Builder()
//            for (i in 0 until routeLatLngArray.size)
//            {
//                var valuse:LatLng = LatLng(routeLatLngArray.get(i).latitude,routeLatLngArray.get(i).longitude)
//                builder.include(valuse)
//            }
//            var bounds:LatLngBounds = builder.build()
//            var padding:Int = 0
//            var  cu:CameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
//            if(googlemap != null)
//            {
//                if(isRouteAvail == false)
//                {
//                    googlemap!!.setPadding(50, 50, 50, 50)
//                    googlemap!!.moveCamera(cu)
//                    isRouteAvail = true
//                    lateinit var optionsForeground: PolylineOptions
//                    if(mapmode)
//                        optionsForeground = PolylineOptions().addAll(routeLatLngArray).color(ContextCompat.getColor(applicationContext,R.color.innergreen)).width(dotsize)
////                    else
////                        optionsForeground = PolylineOptions().addAll(routeLatLngArray).color(ContextCompat.getColor(applicationContext,R.color.mapbluee)).width(dotsize)
//
////                    foregroundPolyline = googlemap!!.addPolyline(optionsForeground)
//                }
//            }
//        }
//        else if (result_code == Activity.RESULT_CANCELED)
//        {
//            reroutingsuccessorfailed = 0
//            routeapihitted = 0
//        }
//
//    }
//    @InternalCoroutinesApi
//    private fun PaymentsuccestmoneyApiParser(finalresponse: String) {
//        println("-----------finalresponsesss-----" + finalresponse)
//        val response_json_object = JSONObject(finalresponse)
//        try {
//            val status = response_json_object.getString("status")
//            if (status.equals("1")) {
//                val response =response_json_object.getJSONObject("response")
//                val is_paid=response.getBoolean("is_paid")
//                val collect_cash=response.getBoolean("collect_cash")
//
//                if (is_paid==true){
//                    ratingpage.visibility = View.GONE
//                    repeatFunpayment.cancel()
//                    ratingpagedialog("",mSessionManager.getUserDetails()[mSessionManager.driver_name]!!,"","")
//
//
//                }else if(collect_cash==true){
//                    ratingpage.visibility = View.GONE
//                    viewModel.getdataforride(mContext)
//
//                }
//                currentridestatus=""
//                Log.d("dfd33hdhf", is_paid.toString())
//
//
//            }
//        } catch (e: java.lang.Exception) {
//
//            e.printStackTrace()
//        }
//    }
//    //Activity Life Cycle for maintaining event bus
//    override fun onResume()
//    {
//        super.onResume()
//
//        if (mSessionManager.getis_electric().equals("true")) {
//            linearLayout2.visibility=View.VISIBLE
//        }else{
//            linearLayout2.visibility=View.GONE
//
//        }
//
//        if(!EventBus.getDefault().isRegistered(this))
//            EventBus.getDefault().register(this)
//        stopservicerunning()
//        viewModel.getnavigationoption(mContext)
//        viewModel.getreroutingoption(mContext)
////        viewModel.getnightmodeoption(mContext)
//        if(!rideid.equals(""))
//            viewModel.getchatunreadcount(mContext,rideid)
//    }
//    override fun onDestroy()
//    {
//        super.onDestroy()
//        if(EventBus.getDefault().isRegistered(this))
//            EventBus.getDefault().unregister(this)
//        myLocationGoogleMap!!.removeFrom(googlemap)
////        stopforegroundservice()
//        stopservicerunning()
//        stoparrivalforegroundservice()
//    }
//    //default icon
//    fun defaultcar()
//    {
//        val height = resources.getDimension(R.dimen._25sdp)
//        val width = resources.getDimension(R.dimen._25sdp)
//        val bitmapdraw = ContextCompat.getDrawable(mContext,R.drawable.bikeliveicon) as BitmapDrawable
//        val b = bitmapdraw.bitmap
//        driver_marker_resized = Bitmap.createScaledBitmap(b, width.toInt(), height.toInt(), false)
//    }
//
//    //default icon
//    fun defaultdropmarker()
//    {
//        val height = resources.getDimension(R.dimen._10sdp)
//        val width = resources.getDimension(R.dimen._10sdp)
//        val bitmapdraw = ContextCompat.getDrawable(mContext,R.drawable.startpoint) as BitmapDrawable
//        val b = bitmapdraw.bitmap
//        dropmarkerresized = Bitmap.createScaledBitmap(b, width.toInt(), height.toInt(), false)
//    }
//
//    //address list array
//    fun stopslistdisplay(it:ArrayList<stopsModel>)
//    {
//        if(it.size == 0)
//            ridelist.visibility = View.GONE
//        else
//        {
//            var listViewAdapter = stopsModelAdapter(applicationContext, it)
//            list = ArrayList()
//            for (i in 0..it.size-1){
//                list!!.add(it[i].title!!)
//            }
//            pickup_address.setText(it[0].title)
//            dropup_address.setText(it[1].title)
//            ridelist.setAdapter(listViewAdapter)
//            ridelist.isExpanded = true
//            ridelist.visibility = View.VISIBLE
//        }
//    }
//
//
//    //cancellation array
//    fun cancelreaon(it:ArrayList<CancellationReasonDataModel>)
//    {
//        if(it.size == 0)
//            commontoast(getString(R.string.no_cancellation))
//        else
//            cancelreasonbottomsheet(it)
//    }
//    // moving to dash board page
//    fun movetomainpage()
//    {
//        viewModel.clearchatrecord(mContext)
//        viewModel.cleartripdetailsalone(mContext)
//        if(ridecancelleddialog !=null)
//        {
//            if(!ridecancelleddialog!!.isShowing)
//                commoncanceldialog()
//        }
//        else
//            commoncanceldialog()
//    }
//
//    // moving to dash board page
//    fun movetomainpageusercancel()
//    {
//        viewModel.clearchatrecord(mContext)
//        viewModel.cleartripdetailsalone(mContext)
//        if(userridecancelleddialog !=null)
//        {
//            if(!userridecancelleddialog!!.isShowing)
//                usercanceldialog()
//        }
//        else
//            usercanceldialog()
//    }
//
//
//    // moving to dash board page
//    fun movetomainpageafterrating()
//    {
//        stopforegroundserviceontripstatsus()
//        viewModel.clearchatrecord(mContext)
//        viewModel.cleartripdetailsalone(mContext)
//        val intent_otppage = Intent(mContext, mianscreenpage::class.java)
//        intent_otppage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        intent_otppage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        startActivity(intent_otppage)
//    }
//    // moving to dash board page
//    fun chatpage()
//    {
//        if(hidebuttons == 0)
//        {
//            if(!rideid.equals(""))
//            {
////                val intent_otppage = Intent(mContext, MessagesActivity::class.java)
////                intent_otppage.putExtra("rideid", rideid)
////                intent_otppage.putExtra("name", usernmaegetting)
////                intent_otppage.putExtra("useridtosend", useridtosend)
////                intent_otppage.putExtra("userimage", userimagesetting)
////                startActivity(intent_otppage)
//            }
//        }
//    }
//    //calling customer
//    fun callcustomer()
//    {
//        if(hidebuttons == 0)
//        {
//            if(customerphno.equals(""))
//                commsnackbaralert(getString(R.string.nonotavaialbe))
//            else
//            {
//                val intent =  Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", customerphno, null));
//                startActivity(intent);
//            }
//        }
//    }
//    // plot destination marker
//    fun dropmarker(drop:LatLng)
//
//    {
////        if(googlemap != null)
////        {
////            val markerOptions = MarkerOptions()
////            markerOptions.position(drop)
////            if(marker == null)
////            {
////                marker  = googlemap?.addMarker(MarkerOptions().position(drop!!).icon(BitmapDescriptorFactory.fromBitmap(dropmarkerresized)))!!
////            }
////            else
////            {
////                marker!!.remove()
////                marker  = googlemap?.addMarker(MarkerOptions().position(drop!!).icon(BitmapDescriptorFactory.fromBitmap(dropmarkerresized)))!!
////
////            }
////            marker!!.showInfoWindow()
////            sucesofdropmarker = 1
////        }
//    }
//    //on back pressed
//    override fun onBackPressed()
//    {
//        if(ctmap.visibility == View.VISIBLE)
//        {
//            finish()
//            super.onBackPressed()
//        }
////        else
////            bottom_sheet_layout.toggle()
//    }
//    //fetch route
//    fun startedtrackingyoucandrawroutehere(pickupLatLng:LatLng)
//    {
//        if(googlemap != null && pickupLatLng != null)
//            viewModel.startroutecall(mContext, pickupLatLng.latitude, pickupLatLng.longitude)
//    }
//    //fetch route
//    fun reroutingprocess(pickupLatLng:LatLng)
//    {
//        if(googlemap != null && pickupLatLng != null)
//            viewModel.reroutingcall(mContext, pickupLatLng.latitude, pickupLatLng.longitude)
//    }
//    //updating trip status
//    private fun tripupdate()
//    {
//        if(pickupLatLng != null)
//        {
//            loadercancel.visibility = View.VISIBLE
//            viewModel.tripupdatestatus(mContext,pickupLatLng!!.latitude.toString(),pickupLatLng!!.longitude.toString(),useridtosend)
//        }
//        else
//            commsnackbaralert(getString(R.string.problemwitlocation))
//    }
//    // hide call,chat,cancel button
//    fun hideonbegin()
//    {
//        hidebuttons = 1
//        hidealloption.visibility = View.GONE
//        viewModel.clearchatrecord(mContext)
//    }
//    //common error notification page
//    fun commsnackbaralert(message:String)
//    {
//        val snack = Snackbar.make(coordinate_bottom_sheet_ride_book_now,message, Snackbar.LENGTH_LONG)
//        var view:View = snack.getView()
//        var params: FrameLayout.LayoutParams =FrameLayout.LayoutParams(view.getLayoutParams())
//        params.gravity = Gravity.TOP;
//        view.setLayoutParams(params)
//        snack.show()
//    }
//    fun commontoast(message:String)
//    {
//        Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
//    }
//    //fit map zoom
//    fun fitmapzoom()
//    {
//        var builder:LatLngBounds.Builder =  LatLngBounds.Builder()
//        for (i in 0 until routeLatLngArray.size)
//        {
//            var valuse:LatLng = LatLng(routeLatLngArray.get(i).latitude,routeLatLngArray.get(i).longitude)
//            builder.include(valuse)
//        }
//        var bounds:LatLngBounds = builder.build()
//        var padding:Int = 0
//        var  cu:CameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
//        if(googlemap != null)
//        {
//            googlemap!!.setPadding(50, 50, 50, 50)
//            googlemap!!.moveCamera(cu)
//        }
//    }
//    //Cancellation Bottom Sheet
//    fun cancelreasonbottomsheet(it:ArrayList<CancellationReasonDataModel>)
//    {
//        canceldialog =  BottomSheetDialog(this)
//        val view = getLayoutInflater().inflate(R.layout.cancellationdialog, null);
//        canceldialog!!.setContentView(view)
//        canceldialog!!.setCancelable(true)
//        val cancelreason = view.findViewById(R.id.cancelreason) as TextView
//        val closetext = view.findViewById(R.id.closetext) as TextView
//        val cancellist = view.findViewById(R.id.cancellist) as com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView
//        cancelreason.visibility  = View.VISIBLE
//        if(it[0].cancel_charge.equals("Yes"))
//            cancelreason.text = getString(R.string.noteforca)+" "+it[0].currency_code+it[0].cancellation_amount+" "+getString(R.string.pallic)
//
//        val listViewAdapter = cancelreasonadapter(applicationContext,it, object : cancelreasonclick {
//            override fun onItemClickListener(view: View, id: String,reason :String)
//            {
//                materialalert(id)
//            }
//        })
//        closetext.setOnClickListener {
//            canceldialog!!.dismiss() }
//        cancellist.setAdapter(listViewAdapter)
//        cancellist.isExpanded = true
//        cancellist.visibility = View.VISIBLE
//        canceldialog!!.show()
//    }
//    //collect amount for customer Bottom Sheet
//    @InternalCoroutinesApi
//    fun collectbelowamountfromcustomer(payableamount:String, symbosl:String, usernames:String,pickup_location:String,drop_location:String)
//    {
//        collectcash =  BottomSheetDialog(this,R.style.CustomBottomSheetDialogTheme)
//        val view = getLayoutInflater().inflate(R.layout.collectcashlayout, null);
//        collectcash!!.setContentView(view)
//        collectcash!!.setCancelable(false)
//        val amount1 = view.findViewById(R.id.amount1) as TextView
//        val name = view.findViewById(R.id.usernames) as TextView
//        val amount2 = view.findViewById(R.id.amount2) as TextView
//        val symbol = view.findViewById(R.id.symbol) as TextView
//        val first_address_ = view.findViewById(R.id.first_address_) as TextView
//        val TripFare = view.findViewById(R.id.TripFare) as TextView
//        val tripamount = view.findViewById(R.id.tripamount) as TextView
//        val CouponApplied = view.findViewById(R.id.CouponApplied) as TextView
//        val CouponAppliedamout = view.findViewById(R.id.CouponAppliedamout) as TextView
//        val CommisionAmountitle = view.findViewById(R.id.CommisionAmountitle) as TextView
//        val CommisionAmount = view.findViewById(R.id.CommisionAmount) as TextView
//        val TotalEarningstitle = view.findViewById(R.id.TotalEarningstitle) as TextView
//        val TotalEarningsamount = view.findViewById(R.id.TotalEarningsamount) as TextView
//        val end_address_ = view.findViewById(R.id.end_address_) as TextView
//        val loaderreceivecash = view.findViewById(R.id.loaderreceivecash) as fr.castorflex.android.smoothprogressbar.SmoothProgressBar
//        val closetext = view.findViewById(R.id.closetext) as ImageView
//        val received = view.findViewById(R.id.cashcollected) as TextView
//        symbol.setText(symbosl)
//        amount1.setText(payableamount)
//        if (Trackingpagerepostiatry.arrayList!=null&&Trackingpagerepostiatry.arrayList.size>0) {
//            TripFare.setText(Trackingpagerepostiatry.arrayList[0])
//            tripamount.setText(symbosl +Trackingpagerepostiatry.arrayListvalue[0])
//            CouponApplied.setText(Trackingpagerepostiatry.arrayList[1])
//            CouponAppliedamout.setText(symbosl +Trackingpagerepostiatry.arrayListvalue[1])
//            CommisionAmountitle.setText(Trackingpagerepostiatry.arrayList[2])
//            CommisionAmount.setText(symbosl +Trackingpagerepostiatry.arrayListvalue[2])
//            TotalEarningstitle.setText(Trackingpagerepostiatry.arrayList[3])
//            TotalEarningsamount.setText(symbosl +Trackingpagerepostiatry.arrayListvalue[3])
//        }
//
//
//        name.setText(usernames)
//        first_address_.setText(pickup_location)
//        end_address_.setText(drop_location)
//        if(payableamount.contains("."))
//        {
//            amount1.setText(payableamount.split(".")[0])
//            amount2.setText("."+payableamount.split(".")[1])
//        }
//        closetext.setOnClickListener {
//            if(loaderreceivecash.visibility == View.VISIBLE)
//                commsnackbaralert(getString(R.string.slideerror))
//            else
//            {
//                collectcash!!.dismiss()
//                finish()
//            }
//        }
//        received.setOnClickListener {
//            if(loaderreceivecash.visibility == View.VISIBLE)
//                commontoast(getString(R.string.slideerror))
//            else
//            {
//                repeatFun = repeatFun()
//
//                repeatFun.cancel()
//                dataBase!!.execSQL("delete from " + onrideDbHelper.TABLE_NAME_FOR_NORMAL_RIDE);
//
//                mSessionManager.setnormalride_completed("")
//                loaderreceivecash.visibility = View.VISIBLE
//                viewModel.receivedcash(mContext,rideid,payableamount)
//            }
//
//        }
//        collectcash!!.show()
//    }
//    //Ride cancelled Bottom Sheet
//    fun ridecancelledpagedesign()
//    {
//        movetomainpage()
//    }
//    //Ride cancelled Bottom Sheet
//    fun userridecancelledpagedesign()
//    {
//        movetomainpageusercancel()
//    }
//    //rating page Bottom Sheet
//    fun ratingpagedialog(userphoto:String,usernames:String,tripamount:String,currencycode:String)
//    {
//        ratingdialog =  BottomSheetDialog(this,R.style.CustomBottomSheetDialogTheme)
//        val view = getLayoutInflater().inflate(R.layout.ratingbottomsheet, null);
//        ratingdialog!!.setContentView(view)
//        ratingdialog!!.setCancelable(false)
//        val userphotos = view.findViewById(R.id.userphotos) as ImageView
//        val username = view.findViewById(R.id.username) as TextView
//        val commnetfor = view.findViewById(R.id.commnetfor) as TextView
//        val earningss = view.findViewById(R.id.earningss) as TextView
//        loadrrating = view.findViewById(R.id.loadrrating) as SmoothProgressBar
//        val closetext = view.findViewById(R.id.closetext) as ImageView
//        val submitrating = view.findViewById(R.id.submitrating) as TextView
//        val ratingBar = view.findViewById(R.id.ratingBar) as RatingBar
//        username.setText(usernames)
//        earningss.setText(currencycode+tripamount)
//        commnetfor.setText(getString(R.string.your_commentfor)+" "+usernames)
//        if(userphoto.startsWith("http"))
//        {
//            Glide.with(mContext)
//                    .asBitmap()
//                    .apply(RequestOptions().override(100, 100))
//                    .load(userphoto)
//                    .into(object : CustomTarget<Bitmap>() {
//                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                            runOnUiThread {
//                                userphotos.setImageBitmap(resource)
//                            }
//                        }
//                        override fun onLoadCleared(placeholder: Drawable?)
//                        {
//                        }
//                    })
//        }
//        closetext.setOnClickListener {
//            if(loadrrating!!.visibility == View.VISIBLE)
//            {
//                commontoast(getString(R.string.slideerror))
//            }
//            else
//            {
//                loadrrating!!.visibility = View.VISIBLE
//                viewModel.skipratingclear(mContext,rideid,"0","")
//            }
//        }
//        submitrating.setOnClickListener {
//            if(loadrrating!!.visibility == View.VISIBLE)
//            {
//                commontoast(getString(R.string.slideerror))
//            }
//            else
//            {
//                loadrrating!!.visibility = View.VISIBLE
//                viewModel.skipratingclear(mContext,rideid,"1",ratingBar.rating.toString())
//            }
//        }
//        ratingdialog!!.show()
//    }
//    //Alert for cancelltion
//
//
//
//    fun materialalert(id:String)
//    {
//        val builder = AlertDialog.Builder(mContext)
//        builder.setTitle(getString(R.string.confirm_cancel))
//        builder.setMessage(getString(R.string.wanttocancel))
//        builder.setPositiveButton(getString(R.string.yesia)) { dialog, which ->
//            if(canceldialog != null)
//            {
//                canceldialog!!.dismiss()
//                loadercancel.visibility = View.VISIBLE
//                viewModel.passcancelid(mContext,id,rideid)
//            }
//        }
//        builder.setNegativeButton(getString(R.string.closepage)) { dialog, which ->
//        }
//        builder.show()
//    }
//    //Floating Widget Part
//    private fun navigatetoggolemap()
//    {
//        if(AppUtils.isNetworkConnected(mContext))
//        {
//
//            if(mapchoosen.equals("1"))
//            {
//                if (isDrawOverlaysAllowed())
//                {
//                    if(!viewModel.isMyServiceRunning(mContext,FloatingWidgetService::class.java))
//                    {
//                        if(pickupLatLng != null)
//                        {
//                            var driverLatLng = LatLng(mSessionManager!!.getOnlineLatitiude().toDouble(), mSessionManager!!.getOnlineLongitude().toDouble())
//                            startService(Intent(this@requestmianscreen, FloatingWidgetService::class.java))
//                            var gmmIntentUri1 = Uri.parse("https://www.google.com/maps/dir/?api=1&origin=" + driverLatLng!!.latitude.toString() + "," +  driverLatLng!!.longitude.toString() +
//                                    "&destination=" + pickupLatLng!!.latitude.toString() + "," + pickupLatLng!!.longitude.toString() +
//                                    "&travelmode=driving");
//                            var intent = Intent(Intent.ACTION_VIEW, gmmIntentUri1)
//                            intent.setPackage("com.google.android.apps.maps");
//                            try
//                            {
//                                startActivity(intent);
//                            }
//                            catch (ex: ActivityNotFoundException)
//                            {
//                                try
//                                {
//                                    var unrestrictedIntent =  Intent(Intent.ACTION_VIEW, gmmIntentUri1);
//                                    startActivity(unrestrictedIntent);
//                                }
//                                catch (innerEx:ActivityNotFoundException)
//                                {
//                                    commsnackbaralert(getString(R.string.gmap_proced))
//                                }
//                            }
//                        }
//                    }
//                    return
//                }
//                requestForDrawingOverAppsPermission()
//            }
//            else
//            {
//                if (isDrawOverlaysAllowed())
//                {
//                    if(!viewModel.isMyServiceRunning(mContext,FloatingWidgetService::class.java))
//                    {
//                        if(pickupLatLng != null)
//                        {
//                            openWaze(pickupLatLng!!.latitude, pickupLatLng!!.longitude)
//                        }
//                    }
//                    return
//                }
//                requestForDrawingOverAppsPermission()
//            }
//        }
//        else
//            commsnackbaralert(getString(R.string.failedproblem))
//
//    }
//
//
//    fun usercanceldialog()
//    {
//        if(userridecancelleddialog != null && !userridecancelleddialog!!.isShowing)
//        {
//            userridecancelleddialog!!.show()
//        }
//        else  if(userridecancelleddialog != null && userridecancelleddialog!!.isShowing)
//        {
//            userridecancelleddialog!!.dismiss()
//        }
//        else  if(userridecancelleddialog == null)
//        {
//            userridecancelleddialog =  BottomSheetDialog(this,android.R.style.Theme_Black_NoTitleBar_Fullscreen)
//            val view = getLayoutInflater().inflate(R.layout.usercancelpopup, null);
//            userridecancelleddialog!!.setContentView(view)
//            userridecancelleddialog!!.setCancelable(false)
//            val rideids = view.findViewById(R.id.rideid) as TextView
//            rideids.text = getString(R.string.ride_id)+" "+rideid
//            val closetext = view.findViewById(R.id.closetext) as ImageView
//            val cloepage = view.findViewById(R.id.cloepage) as LinearLayout
//            closetext.setOnClickListener {
//                userridecancelleddialog!!.dismiss()
//                val intent_otppage = Intent(mContext, mianscreenpage::class.java)
//                intent_otppage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                intent_otppage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                startActivity(intent_otppage)
//            }
//            cloepage.setOnClickListener {
//                userridecancelleddialog!!.dismiss()
//                val intent_otppage = Intent(mContext, mianscreenpage::class.java)
//                intent_otppage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                intent_otppage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                startActivity(intent_otppage)
//            }
//            userridecancelleddialog!!.show()
//        }
//    }
//
//    fun commoncanceldialog()
//    {
//        if(ridecancelleddialog != null && !ridecancelleddialog!!.isShowing)
//        {
//            ridecancelleddialog!!.show()
//        }
//        else  if(ridecancelleddialog != null && ridecancelleddialog!!.isShowing)
//        {
//            ridecancelleddialog!!.dismiss()
//        }
//        else  if(ridecancelleddialog == null)
//        {
//            ridecancelleddialog =  BottomSheetDialog(this,android.R.style.Theme_Black_NoTitleBar_Fullscreen)
//            val view = getLayoutInflater().inflate(R.layout.cancelpopup, null);
//            ridecancelleddialog!!.setContentView(view)
//            ridecancelleddialog!!.setCancelable(false)
//            val rideids = view.findViewById(R.id.rideid) as TextView
//            rideids.text = getString(R.string.ride_id)+" "+rideid
//            val closetext = view.findViewById(R.id.closetext) as ImageView
//            val cloepage = view.findViewById(R.id.cloepage) as LinearLayout
//            closetext.setOnClickListener {
//                ridecancelleddialog!!.dismiss()
//                val intent_otppage = Intent(mContext, mianscreenpage::class.java)
//                intent_otppage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                intent_otppage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                startActivity(intent_otppage)
//            }
//            cloepage.setOnClickListener {
//                ridecancelleddialog!!.dismiss()
//                val intent_otppage = Intent(mContext, mianscreenpage::class.java)
//                intent_otppage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                intent_otppage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                startActivity(intent_otppage)
//            }
//            ridecancelleddialog!!.show()
//        }
//    }
//    fun stopservicerunning()
//    {
//        if(viewModel.isMyServiceRunning(mContext,FloatingWidgetService::class.java))
//            stopService(Intent(this@requestmianscreen, FloatingWidgetService::class.java))
//    }
//    private fun isServiceRunning(serviceClass: Class<*>): Boolean {
//        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//        // Loop through the running services
//        for (service in activityManager.getRunningServices(Integer.MAX_VALUE)) {
//            if (serviceClass.name == service.service.className) {
//                // If the service is running then return true
//                return true
//            }
//        }
//        return false
//    }
//    fun stopforegroundservice()
//    {
//        val serviceClass = FService::class.java
//        val serviceIntent = Intent(applicationContext, serviceClass)
//        try {
//            unbindService(myConnection)
//        } catch (e: IllegalArgumentException) {
//        }
//        if (isServiceRunning(FService::class.java)) {
//            stopService(serviceIntent)
//        }
//    }
//    fun movetosettingpage()
//    {
//        val intent_otppage = Intent(mContext, settingspage::class.java)
//        intent_otppage.putExtra("hidelanguage", "1")
//        startActivity(intent_otppage)
//    }
//    fun openWaze(latitude: Double, longitude: Double)
//    {
//        packageManager?.let {
//            val url = "waze://?ll=$latitude,$longitude&navigate=yes"
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//            intent.resolveActivity(it)?.let {
//                startActivity(intent)
//                startService(Intent(this@requestmianscreen, FloatingWidgetService::class.java))
//            } ?: run {
//                AppUtils.wazeormap(mContext,getString(R.string.appnotfound),getString(R.string.wazeinstallplaystore))
//            }
//        }
//    }
//    private fun requestForDrawingOverAppsPermission()
//    {
//        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
//        startActivityForResult(intent, DRAW_OVERLAYS_PERMISSION_REQUEST_CODE)
//    }
//    private fun isDrawOverlaysAllowed(): Boolean =
//            Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(this)
//
//    override fun routeDrawnPickToDrop(time: Double?, dist: Double?) {
//    }
//
//    fun startUpdateForegroundService(){
//        val serviceClass = UpdateForgroundService::class.java
//        val intent = Intent(this, serviceClass)
//        startService(intent)
//    }
//    fun stopUpdateForegroundService(){
//        val serviceClass = UpdateForgroundService::class.java
//        val intent = Intent(this, serviceClass)
//        stopService(intent)
//    }
//
//}
//
//private fun TextView.setText() {
//    TODO("Not yet implemented")
//}
//
