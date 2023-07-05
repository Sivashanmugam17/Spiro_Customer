//package com.cabilyhandyforalldinedoo.chd.ui.MainPage
//
//import android.Manifest
//import android.annotation.SuppressLint
//import android.app.Activity
//import android.content.*
//import android.content.pm.PackageManager
//import android.graphics.Bitmap
//import android.graphics.Color
//import android.graphics.drawable.AnimatedVectorDrawable
//import android.graphics.drawable.BitmapDrawable
//import android.graphics.drawable.Drawable
//import android.location.Location
//import android.net.Uri
//import android.os.*
//import android.provider.Settings
//import android.util.TypedValue
//import android.view.Gravity
//import android.view.MenuItem
//import android.view.View
//import android.widget.*
//import androidx.appcompat.app.ActionBarDrawerToggle
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import androidx.core.view.GravityCompat
//import androidx.databinding.DataBindingUtil
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import cabily.handyforall.dinedoo.R
//import cabily.handyforall.dinedoo.databinding.MaipagedesignBinding
//import com.bumptech.glide.Glide
//import com.bumptech.glide.request.target.CustomTarget
//import com.bumptech.glide.request.transition.Transition
//import com.cabilyhandyforalldinedoo.chd.Backgroundservices.OnlineJobService
//import com.cabilyhandyforalldinedoo.chd.EventBusConnection.IntentServiceResult
//import com.cabilyhandyforalldinedoo.chd.SessionManagerPackage.SessionManager
//import com.cabilyhandyforalldinedoo.chd.ViewModelWIthRepositaryMain.DriverDetailDataModel
//import com.cabilyhandyforalldinedoo.chd.ViewModelWIthRepositaryMain.MainViewModel
//import com.cabilyhandyforalldinedoo.chd.commonutils.AppUtils
//import com.cabilyhandyforalldinedoo.chd.mylocation.MyLocationGoogleMap
//import com.cabilyhandyforalldinedoo.chd.mylocation.TileSystem
//import com.cabilyhandyforalldinedoo.chd.ui.registeration.LocaleAwareCompatActivity
//import com.cabilyhandyforalldinedoo.chd.ui.registeration.splashpage
////import com.cabilyhandyforalldinedoo.chd.ui.sidemenus.addoreditvehicle
//import com.cabilyhandyforalldinedoo.chd.ui.sidemenus.editdriverdetails
//import com.cabilyhandyforalldinedoo.chd.ui.sidemenus.tripdetailspage
//import com.google.android.gms.common.ConnectionResult
//import com.google.android.gms.common.api.GoogleApiClient
//import com.google.android.gms.location.*
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.*
//import com.google.android.material.bottomsheet.BottomSheetBehavior
//import com.google.android.material.bottomsheet.BottomSheetDialog
//import com.google.android.material.navigation.NavigationView
//import com.google.android.material.snackbar.Snackbar
//import io.realm.Realm
//import kotlinx.android.synthetic.main.maipagedesign.*
//import org.greenrobot.eventbus.EventBus
//import org.greenrobot.eventbus.Subscribe
//import org.greenrobot.eventbus.ThreadMode
//import org.json.JSONObject
//
//class mianscreen : LocaleAwareCompatActivity(), OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
//    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
//        drawer_layout.closeDrawer(GravityCompat.START)
//        return true
//    }
//    override fun onConnected(p0: Bundle?) {
//        val r = mContext.getResources()
//        val density = r.getDisplayMetrics().density
//        val size = (256 * density).toInt()
//        TileSystem.setTileSize(size)
//        accuracyStrokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, r.getDisplayMetrics())
//        if (!hasPermission(Manifest.permission.ACCESS_FINE_LOCATION) && Build.VERSION.SDK_INT >= 23) {
//            val permissions = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
//            ActivityCompat.requestPermissions(mContext!!, permissions, 1000)
//        } else {
//            location = LocationServices.getFusedLocationProviderClient(mContext!!)
//            locationCallback = object : LocationCallback() {
//                override fun onLocationResult(locationResult: LocationResult?) {
//                    locationResult ?: return
//                    for (location in locationResult.locations) {
//                        googlemap?.let { it1 -> driver_marker_resized?.let { it2 -> addTo(it1, it2, location) } }
//                    }
//                }
//            }
//            location.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
//        }
//    }
//    override fun onConnectionSuspended(p0: Int) {}
//    override fun onConnectionFailed(p0: ConnectionResult) {}
//    override fun onLocationChanged(p0: Location?) {}
//    val ACCURACY_COLOR = "#1800ce5b"
//    val ACCURACY_COLOR_BORDERS = "#8000ce5b"
//    private val handler = Handler(Looper.getMainLooper())
//    private var isMyLocationCentered = false
//    private var accuracyStrokeWidth: Float = 0.0f
//    private var accuracyCircle: Circle? = null
//    private var locationMarker: Marker? = null
//    private var bearingMarker: Marker? = null
//    internal lateinit var mSessionManager: SessionManager
//    var aService: OnlineJobService? = null
//    private var googleApiClient: GoogleApiClient? = null
//    private var locationRequest: LocationRequest? = null
//    var zoomcamera: Int = 0
//    var opendriverdetails: Int = 0
//    lateinit var binding: MaipagedesignBinding
//    private lateinit var mMapFragment: SupportMapFragment
//    private lateinit var mContext: Activity
//    private lateinit var viewModel: MainViewModel
//    var zoomlevef: Float = 16f
//    var locationCallback: LocationCallback? = null
//    private var googlemap: GoogleMap? = null
//    var ctlat: Double = 0.0
//    var open: Int = 0
//    var ctlong: Double = 0.0
//    var dummymarker: BitmapDescriptor? = null
//    private var driver_marker_resized: Bitmap? = null
//    var driverMarker: Marker? = null
//    var driverLatLng: LatLng? = null
//    private var myLocationGoogleMap: MyLocationGoogleMap? = null
//    lateinit var location: FusedLocationProviderClient
//    var availabilystatus: String = "1"
//    var bottomSheetDialog: BottomSheetDialog? = null
//    var animR: AnimatedVectorDrawable? = null
//    var navdrivername: TextView? = null
//    var isBound = false
//    var navmobilevalue: TextView? = null
//    var navdriverphoto: ImageView? = null
//    var navratingBar: RatingBar? = null
//    private val arrivalConnection = object : ServiceConnection {
//        override fun onServiceConnected(className: ComponentName, service: IBinder) {
//            val binder = service as OnlineJobService.MyLocalBinder
//            aService = binder.getService()
//            isBound = true
//        }
//
//        override fun onServiceDisconnected(name: ComponentName) {
//            isBound = false
//        }
//    }
//    override fun onCreate(savedInstanceState: Bundle?)
//    {
//        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this, R.layout.maipagedesign)
//        mContext = this@mianscreen
//        mSessionManager = SessionManager(mContext)
//        animR = iv_line.getBackground() as AnimatedVectorDrawable?
//        intilizecommonvalue()
//        initviewmodel()
//        binding.setViewModel(viewModel)
//        initializeMap()
//        open = 0
//        opendriverdetails = 0
//        val toggle = ActionBarDrawerToggle(this, drawer_layout, null, R.string.settings, R.string.settings)
//        drawer_layout.addDrawerListener(toggle)
//        toggle.syncState()
////        nav_view.setNavigationItemSelectedListener(this)
//        profileeclick.setOnClickListener {
//            opendriverdetails = 1
//            viewModel.dispalydriverdetails(mContext)
//        }
//        driverphoto.setOnClickListener {
//            opendriverdetails = 1
//            viewModel.dispalydriverdetails(mContext)
//        }
//    }
//    fun intilizecommonvalue()
//    {
//        myLocationGoogleMap = MyLocationGoogleMap(mContext)
////        layout_ripplepulse3.startRippleAnimation()
//        var navigationView: NavigationView = findViewById(R.id.nav_view);
//        val view = navigationView.getHeaderView(0)
//        navdrivername = view.findViewById(R.id.navdrivername) as TextView
//        navmobilevalue = view.findViewById(R.id.navmobilevalue) as TextView
//        navdriverphoto = view.findViewById(R.id.navdriverphoto) as ImageView
//        navratingBar = view.findViewById(R.id.navratingBar) as RatingBar
//        var settingss = view.findViewById(R.id.settingss) as com.google.android.material.floatingactionbutton.FloatingActionButton
//        var tripsum = view.findViewById(R.id.tripsum) as LinearLayout
//        var profilerdit = view.findViewById(R.id.profilerdit) as LinearLayout
//        var settingspages = view.findViewById(R.id.settingspage) as LinearLayout
//        var tripsummary = view.findViewById(R.id.tripsummary) as LinearLayout
//
//        tripsummary.setOnClickListener {
//            val intent_otppage = Intent(mContext, tripdetailspage::class.java)
//            startActivity(intent_otppage)
//           // commonclose()
//        }
//        profilerdit.setOnClickListener {
//           // commonclose()
////            val intent_otppage = Intent(mContext, editdriverdetails::class.java)
////            startActivity(intent_otppage)
//        }
//        tripsum.setOnClickListener {
//           // commonclose()
////            val intent_otppage = Intent(mContext, addoreditvehicle::class.java)
////            startActivity(intent_otppage)
//        }
//        settingspages.setOnClickListener {
////            val intent_otppage = Intent(mContext, settingspage::class.java)
////            startActivity(intent_otppage)
//        }
//    }
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
//    fun commonclose()
//    {
//        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
//            drawer_layout.closeDrawer(GravityCompat.START)
//        } else {
//            drawer_layout.openDrawer(GravityCompat.START)
//            viewModel.defaultvalueloader(mContext)
//        }
//    }
//    override fun onMapReady(mGoogleMap: GoogleMap?)
//    {
//        googlemap = mGoogleMap
//        defaultcar()
//        val locationmaode = mSessionManager!!.getLocationMode().toInt()
//        connectToGoogleApiClient()
//        if (locationmaode == 1)
//        {
//            nightthemecolour()
//            googlemap!!.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.night_map_style))
//            googleApiClient?.connect()
//        }
//        else
//        {
//            googlemap!!.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
//            myLocationGoogleMap!!.addTo(googlemap, driver_marker_resized, dummymarker, locationmaode)
//        }
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//        {
//            if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                mGoogleMap!!.isMyLocationEnabled = false
//            }
//        }
//        else
//        {
//            mGoogleMap!!.isMyLocationEnabled = false
//        }
//        mGoogleMap!!.getUiSettings().setRotateGesturesEnabled(false)
//        mGoogleMap!!.getUiSettings().setMapToolbarEnabled(false)
//    }
//    fun initviewmodel()
//    {
//        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
//        viewModel.getDefaultStatus(mContext)
//        viewModel.getdriverdetails(mContext)
//        viewModel.getdocumentstatus(mContext)
//        viewModel.dashbaordapiservice(mContext)
//        viewModel.getdriverimage(mContext)
//        viewModel.defaultvalueloader(mContext)
//        viewModel.defaultonlinestatusobserver().observe(this, Observer {
//            runOnUiThread {
//                commonhidego(it)
//            }
//        })
//        viewModel.defaultdriverdetailobserver().observe(this, Observer {
//            runOnUiThread {
//                navigationheadervalue(it)
//            }
//        })
//        viewModel.opendrwaerlayoutobserver().observe(this, Observer {
//            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
//                drawer_layout.closeDrawer(GravityCompat.START)
//            } else {
//                drawer_layout.openDrawer(GravityCompat.START)
//                viewModel.defaultvalueloader(mContext)
//            }
//        })
//        viewModel.onstatusobserver().observe(this, Observer {
//            availbiltyon()
//        })
//        viewModel.zoomlevelobserver().observe(this, Observer {
//            if (googlemap != null)
//                googlemap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.get(0).zoomlatitude!!.toDouble(), it.get(0).zoomlongitude!!.toDouble()), 15.5f), 1000, null);
//        })
//        viewModel.locationmodeobserver().observe(this, Observer {
//            if (it == 1)
//            {
//                nightthemecolour()
//                googlemap!!.clear()
//                myLocationGoogleMap?.removeFrom(googlemap)
//                removeFrom()
//                if (googleApiClient == null)
//                    connectToGoogleApiClient()
//                if (googleApiClient != null) {
//                    googleApiClient?.isConnected?.let {
//                        if (it)
//                            googleApiClient?.disconnect()
//                    }
//                    googleApiClient?.connect()
//                }
//                googlemap!!.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.night_map_style))
//            } else {
//                morningthemecolour()
//                googlemap!!.clear()
//                if (googleApiClient != null) {
//                    googleApiClient?.isConnected?.let {
//                        if (it)
//                            googleApiClient?.disconnect()
//                    }
//                }
//                removelocationupdate()
//                myLocationGoogleMap?.removeFrom(googlemap)
//                myLocationGoogleMap!!.addTo(googlemap, driver_marker_resized, dummymarker, it)
//                googlemap!!.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
//            }
//        })
//        viewModel.offstatusobserver().observe(this, Observer {
//            if (it == 1)
//                availabilityoff()
//        })
//        viewModel.dutyrideobserver().observe(this, Observer {
//            if (!it.equals("")) {
//                mSessionManager.setDutyrideid(it)
//                movetotrippage()
//            }
//
//        })
//        viewModel.mobilenoobserver().observe(this, Observer {
//            navmobilevalue!!.setText(it)
//        })
//        viewModel.driverfulldetailsobserver().observe(this, Observer {
//            if (opendriverdetails == 1) {
//                if (it != null)
//                    driverprofillee(it)
//            }
//        })
//        viewModel.docstatusobserver().observe(this, Observer {
//            runOnUiThread {
//                if (it.equals("0")) {
//                    if (bottomSheetDialog != null) {
//                        if (!bottomSheetDialog!!.isShowing)
//                            successdialog(getString(R.string.verfication_pending), getString(R.string.thanks_for_registering_with_us_our_admin_will_be_verfying_your_document))
//                    } else
//                        successdialog(getString(R.string.verfication_pending), getString(R.string.thanks_for_registering_with_us_our_admin_will_be_verfying_your_document))
//                } else {
//                    if (bottomSheetDialog != null) {
//                        if (bottomSheetDialog!!.isShowing)
//                            bottomSheetDialog!!.dismiss()
//                    }
//                }
//            }
//        })
//        viewModel.onlinestatusobserver().observe(this, Observer {
//            runOnUiThread {
//                commonhidego(it)
//            }
//        })
//        viewModel.driveretailsviewmodellObserver().observe(this, Observer {
//            if (it != null) {
//                viewModel.getdocumentstatus(mContext)
//                var driverimagepath: String = it!!
//                if (!it!!.startsWith("http")) {
//                    driverimagepath = getString(R.string.amaozonurlprofileimage) + it!!
//                }
//
//                Glide.with(mContext)
//                        .asBitmap()
//                        .load(driverimagepath!!)
//                        .into(object : CustomTarget<Bitmap>() {
//                            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                                runOnUiThread {
//                                    driverphoto.setImageBitmap(resource)
//                                }
//                            }
//
//                            override fun onLoadCleared(placeholder: Drawable?) {
//                            }
//                        })
//            }
//        })
//    }
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun doThis(intentServiceResult: IntentServiceResult)
//    {
//        loader.visibility = View.GONE
//        var apiName: String = intentServiceResult.apiName
//        if (apiName.equals(getString(R.string.getlocation)))
//        {
//            var bothlatandlong: String = intentServiceResult.resultValue
//            val latlongvalue = bothlatandlong.split(",").toTypedArray()
//            ctlat = latlongvalue[0].toDouble()
//            ctlong = latlongvalue[1].toDouble()
//            driverLatLng = LatLng(ctlat, ctlong)
//            if (driverLatLng != null)
//            {
//                if (driverMarker == null)
//                driverMarker = googlemap?.addMarker(MarkerOptions().position(driverLatLng!!).icon(BitmapDescriptorFactory.fromBitmap(driver_marker_resized)))!!
//            }
//            if (zoomcamera == 0)
//            {
//                val cameraPosition = CameraPosition.Builder()
//                        .target(LatLng(latlongvalue[0].toDouble(), latlongvalue[1].toDouble())).zoom(zoomlevef)
//                        .build()
//                runOnUiThread {
//                    googlemap?.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
//                }
//                zoomcamera = 1
//            }
//        }
//        else if (apiName.equals(getString(R.string.acceptcall)))
//        {
//            var response: String = intentServiceResult.resultValue
//            if (response != "failed")
//            {
//                var responseObj = JSONObject(response)
//                if (responseObj.getString("status").equals("0"))
//                    commsnackbaralert(getString(R.string.failedproblem))
//            }
//            else
//                commsnackbaralert(getString(R.string.failedproblem))
//        }
//        else if (apiName.equals(getString(R.string.dashboarapicall)))
//        {
//            var response: String = intentServiceResult.resultValue
//            if (response != "failed")
//                viewModel.getsplitrepsonse(applicationContext, response)
//        }
//        else if (apiName.equals(getString(R.string.docpendingstage)))
//        {
//            if (bottomSheetDialog != null)
//            {
//                if (!bottomSheetDialog!!.isShowing)
//                    successdialog(getString(R.string.verfication_pending), getString(R.string.thanks_for_registering_with_us_our_admin_will_be_verfying_your_document))
//            }
//            else
//                successdialog(getString(R.string.verfication_pending), getString(R.string.thanks_for_registering_with_us_our_admin_will_be_verfying_your_document))
//        }
//        else if (apiName.equals(getString(R.string.logoutcallapi)))
//        {
//            backtologin(mContext)
//        }
//        else if (apiName.equals(getString(R.string.loadme)))
//        {
//            iv_line.visibility = View.VISIBLE
//            animationstart()
//        }
//        else if (apiName.equals(getString(R.string.driveravailable)))
//        {
//            var response: String = intentServiceResult.resultValue
//            if (response != "failed")
//                viewModel.getonlinestatus(response, availabilystatus, mContext)
//            else
//                AppUtils.commonerrorsheet(mContext, getString(R.string.failed), getString(R.string.failedproblem))
//        }
//    }
//    override fun onResume()
//    {
//        super.onResume()
//        if (!EventBus.getDefault().isRegistered(this))
//            EventBus.getDefault().register(this)
//        viewModel.defaultvalueloader(mContext)
//        viewModel.getdriverimage(mContext)
//    }
//    override fun onDestroy()
//    {
//        super.onDestroy()
//        clearllallthing()
//        stoponlineservice()
//    }
//    fun defaultcar()
//    {
//        val height = resources.getDimension(R.dimen._50sdp)
//        val width = resources.getDimension(R.dimen._50sdp)
//        val bitmapdraw = ContextCompat.getDrawable(mContext, R.drawable.greenloc) as BitmapDrawable
//        val b = bitmapdraw.bitmap
//        driver_marker_resized = Bitmap.createScaledBitmap(b, width.toInt(), height.toInt(), false)
//    }
//    fun commonhidego(it: String)
//    {
//        runOnUiThread {
//            loader.visibility = View.GONE
//        }
//        if (it.equals("1"))
//        {
//            runOnUiThread {
////                layout_ripplepulse3.visibility = View.GONE
////                layout_ripplepulse3.stopRippleAnimation()
////                offlineripple.visibility = View.VISIBLE
////                layout_ripplepulse3.visibility = View.GONE
//                if (!viewModel.isMyServiceRunning(mContext, OnlineJobService::class.java)) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    }
//                    startonlineservice()
//                    startoptimizepermission()
//                }
//            }
//        } else {
//            runOnUiThread {
////                layout_ripplepulse3.visibility = View.VISIBLE
//////                layout_ripplepulse3.startRippleAnimation()
////                offlineripple.visibility = View.GONE
//                if (viewModel.isMyServiceRunning(mContext, OnlineJobService::class.java)) {
//                    stoponlineservice()
//                    animationstop()
//                }
//            }
//        }
//    }
//    fun successdialog(heading: String, content: String)
//    {
//        availabilityoff()
//        bottomSheetDialog = BottomSheetDialog(this)
//        val view = getLayoutInflater().inflate(R.layout.successdialog, null);
//        bottomSheetDialog!!.setContentView(view)
//        bottomSheetDialog!!.setCancelable(false)
//        val title = view.findViewById(R.id.title) as TextView
//        val subcontent = view.findViewById(R.id.subcontent) as TextView
//        val retry = view.findViewById(R.id.retry) as TextView
//        val closetext = view.findViewById(R.id.closetext) as TextView
//        val backtologin = view.findViewById(R.id.backtologin) as Button
//        val backtologinim = view.findViewById(R.id.backtologinim) as ImageView
//        val backtologinlayout = view.findViewById(R.id.backtologinlayout) as LinearLayout
//        title.setText(heading)
//        subcontent.setText(content)
//        retry.setText(getString(R.string.ok).toUpperCase())
//        closetext.setText(getString(R.string.close).toUpperCase())
//        val done = view.findViewById(R.id.done) as LinearLayout
//        val close = view.findViewById(R.id.close) as LinearLayout
//        backtologin.setOnClickListener {
//            mSessionManager = SessionManager(applicationContext!!)
//            mSessionManager.clearalldata()
//            backtologin(applicationContext)
//            viewModel.logout(mContext)
//        }
//        backtologinim.setOnClickListener {
//            mSessionManager = SessionManager(applicationContext!!)
//            mSessionManager.clearalldata()
//            backtologin(applicationContext)
//            viewModel.logout(mContext)
//        }
//        backtologinlayout.setOnClickListener {
//            mSessionManager = SessionManager(applicationContext!!)
//            mSessionManager.clearalldata()
//            backtologin(applicationContext)
//            viewModel.logout(mContext)
//        }
//        done.setOnClickListener {
//            val intent2 = Intent(mContext, com.cabilyhandyforalldinedoo.chd.ui.registeration.documentpageone::class.java)
//            mContext.startActivity(intent2)
//        }
//        close.setOnClickListener {
//            bottomSheetDialog!!.dismiss()
//            finish()
//        }
//        bottomSheetDialog!!.show()
//    }
//    private fun navigationheadervalue(drivearray: ArrayList<DriverDetailDataModel>)
//    {
//        mSessionManager = SessionManager(applicationContext!!)
//        navdrivername!!.setText(drivearray.get(0).drivername)
//        navmobilevalue!!.setText(mSessionManager!!.getUserDetails()!![mSessionManager!!.mobilecode]!! + " " + mSessionManager!!.getUserDetails()!![mSessionManager!!.mobilenumber]!!)
//        var ratee: Float = drivearray.get(0).driverrating!!.toFloat()
//        navratingBar!!.rating = ratee
//        var driverimagepath= drivearray[0].driverimage!!
//        if (!driverimagepath.startsWith("http")) {
//            driverimagepath = getString(R.string.amaozonurlprofileimage) + drivearray[0].driverimage!!
//        }
//        if (driverimagepath.startsWith("http")) {
//            Glide.with(mContext)
//                    .asBitmap()
//                    .load(driverimagepath)
//                    .into(object : CustomTarget<Bitmap>() {
//                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                            runOnUiThread {
//                                navdriverphoto!!.setImageBitmap(resource)
//                            }
//                        }
//                        override fun onLoadCleared(placeholder: Drawable?) {
//                        }
//                    })
//        }
//
//    }
//    override fun onBackPressed()
//    {
//        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
//            drawer_layout.closeDrawer(GravityCompat.START)
//        } else {
//            super.onBackPressed()
//        }
//    }
//    fun expnd(dialogInterface: DialogInterface)
//    {
//        var d: BottomSheetDialog = dialogInterface as BottomSheetDialog
//        val bottomsheet = d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
//        val bottomsheetbeh = BottomSheetBehavior.from(bottomsheet!!)
//        bottomsheetbeh.state = BottomSheetBehavior.STATE_EXPANDED
//        bottomsheetbeh.peekHeight = bottomsheet!!.height
//    }
//    fun driverprofillee(drivearray: ArrayList<DriverDetailDataModel>)
//    {
//        var mSessionManager: SessionManager? = null
//        mSessionManager = SessionManager(mContext)
//        val bottomSheetDialog = BottomSheetDialog(this)
//        val view = getLayoutInflater().inflate(R.layout.driverdetailslist, null);
//        bottomSheetDialog!!.setContentView(view)
//        bottomSheetDialog!!.setOnShowListener(DialogInterface.OnShowListener { dialogInterface: DialogInterface? ->
//            if (dialogInterface != null) {
//                expnd(dialogInterface)
//            }
//        })
//        val drivername = view.findViewById(R.id.drivername) as TextView
//        val vehiclenumber = view.findViewById(R.id.vehiclenumber) as TextView
//        val vehiclemodel = view.findViewById(R.id.vehiclemodel) as TextView
//        val emailidvalue = view.findViewById(R.id.emailidvalue) as TextView
//        val categoryvalue = view.findViewById(R.id.categoryvalue) as TextView
//        val driverphoto = view.findViewById(R.id.driverphoto) as ImageView
//        val ratingBar = view.findViewById(R.id.ratingBar) as RatingBar
//        val simpleSwitch = view.findViewById(R.id.simpleSwitch) as Switch
//        drivername.setText(drivearray.get(0).drivername)
//        vehiclenumber.setText(mSessionManager!!.getUserDetails()!![mSessionManager!!.mobilecode]!!)
//        vehiclemodel.setText(mSessionManager!!.getUserDetails()!![mSessionManager!!.mobilenumber]!!)
//        emailidvalue.setText(mSessionManager!!.getUserDetails()!![mSessionManager!!.email]!!)
//        var ratee: Float = drivearray.get(0).driverrating!!.toFloat()
//        ratingBar.rating = ratee
//        var driverimagepath: String = drivearray[0].driverimage!!
//        if (!driverimagepath.startsWith("http")) {
//            driverimagepath = getString(R.string.amaozonurlprofileimage) + drivearray[0].driverimage!!
//        }
//        if (driverimagepath.startsWith("http")) {
//            Glide.with(mContext)
//                    .asBitmap()
//                    .load(driverimagepath)
//                    .into(object : CustomTarget<Bitmap>() {
//                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                            runOnUiThread {
//                                driverphoto.setImageBitmap(resource)
//                            }
//                        }
//                        override fun onLoadCleared(placeholder: Drawable?) {
//                        }
//                    })
//        }
//        var loction: Int = drivearray.get(0).locationmode!!.toInt()
//        simpleSwitch.isChecked = loction == 1
//        simpleSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked)
//                viewModel.locationmode(mContext, 1)
//            else
//                viewModel.locationmode(mContext, 0)
//        }
//        categoryvalue.setOnClickListener {
//
//        }
//
//
//
//        bottomSheetDialog!!.show()
//    }
//    fun availabilityoff()
//    {
//        if (AppUtils.isNetworkConnected(mContext)) {
//            if (loader!!.visibility == View.VISIBLE) {
//                commsnackbaralert(getString(R.string.loading))
//            } else {
//                iv_line.visibility = View.GONE
//                loader.visibility = View.VISIBLE
//                availabilystatus = "0"
//                viewModel.driverstatus(mContext, "0")
//            }
//        } else
//            commsnackbaralert(getString(R.string.failedproblem))
//
//    }
//    fun availbiltyon()
//    {
//        if (AppUtils.isNetworkConnected(mContext)) {
//            if (loader!!.visibility == View.VISIBLE) {
//                commsnackbaralert(getString(R.string.loading))
//            } else {
//                loader.visibility = View.VISIBLE
//                availabilystatus = "1"
//                viewModel.driverstatus(mContext, "1")
//            }
//        } else
//            commsnackbaralert(getString(R.string.failedproblem))
//
//    }
//    fun clearllallthing()
//    {
//        if(googleApiClient != null)
//        {
//            googleApiClient?.isConnected?.let {
//                if (it)
//                    googleApiClient?.disconnect()
//            }
//        }
//        removelocationupdate()
//        removeFrom()
//        if(myLocationGoogleMap != null && googlemap != null)
//            myLocationGoogleMap!!.removeFrom(googlemap)
//        if (EventBus.getDefault().isRegistered(this))
//            EventBus.getDefault().unregister(this)
////        layout_ripplepulse3.stopRippleAnimation()
//        if (viewModel.isMyServiceRunning(mContext, OnlineJobService::class.java))
//            stoponlineservice()
//    }
//    fun animationstart()
//    {
//        if (animR != null) {
//            if (!animR!!.isRunning) {
//                animR!!.start()
//            }
//        }
//    }
//    fun animationstop()
//    {
//        if (animR != null) {
//            iv_line.visibility = View.GONE
//            if (animR!!.isRunning)
//                animR!!.stop()
//        }
//    }
//    private fun connectToGoogleApiClient()
//    {
//        googleApiClient = mContext?.let {
//            GoogleApiClient.Builder(it)
//                    .addConnectionCallbacks(this)
//                    .addOnConnectionFailedListener(this)
//                    .addApi(LocationServices.API)
//                    .build()
//        }
//        locationRequest = LocationRequest.create()
//                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
//                .setInterval(1000)
//                .setFastestInterval(1000)
//    }
//    private fun hasPermission(permission: String): Boolean
//    {
//        return ContextCompat.checkSelfPermission(mContext!!, permission) == PackageManager.PERMISSION_GRANTED
//    }
//    @SuppressLint("MissingPermission")
//    fun moveToMyLocation(googleMap: GoogleMap?, location: Location?)
//    {
//        if (googleMap != null && location != null) {
//            val latLng = LatLng(location.latitude, location.longitude)
//            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17f)
//            googleMap.animateCamera(cameraUpdate, 1000, null)
//        }
//    }
//    fun addTo(googleMap: GoogleMap, driver_marker_resized: Bitmap, location: Location)
//    {
//        mSessionManager = SessionManager(mContext)
//        handler.post(Runnable {
//            mSessionManager.setOnlineStatsu(location.latitude, location.longitude, location.bearing, "")
//            val center = LatLng(location.latitude, location.longitude)
//            val radius = location.accuracy / TileSystem.GroundResolution(location.latitude,
//                    googleMap.cameraPosition.zoom.toDouble()).toFloat()
//            if (accuracyCircle == null) {
//                accuracyCircle = googleMap.addCircle(CircleOptions()
//                        .center(center)
//                        .radius(radius.toDouble())
//                        .fillColor(Color.parseColor(ACCURACY_COLOR))
//                        .strokeColor(Color.parseColor(ACCURACY_COLOR_BORDERS))
//                        .strokeWidth(accuracyStrokeWidth))
//            } else {
//                accuracyCircle!!.setCenter(center)
//                accuracyCircle!!.setRadius(radius.toDouble())
//            }
//            if (locationMarker == null) {
//                locationMarker = googleMap.addMarker(MarkerOptions()
//                        .position(center)
//                        .anchor(0.5f, 0.5f)
//                        .icon(BitmapDescriptorFactory.fromBitmap(driver_marker_resized)))
//            } else {
//                locationMarker!!.setPosition(center)
//            }
//            if (location.bearing.toDouble() == 0.0) {
//                if (bearingMarker != null) {
//                    bearingMarker!!.remove()
//                    bearingMarker = null
//                }
//            } else {
//                if (bearingMarker == null) {
//                    if (locationMarker != null) {
//                        locationMarker!!.remove()
//                        locationMarker = null
//                    }
//                    bearingMarker = googleMap.addMarker(MarkerOptions()
//                            .position(center)
//                            .flat(true)
//                            .anchor(0.5f, 0.5f)
//                            .rotation(location.bearing)
//                            .icon(BitmapDescriptorFactory.fromBitmap(driver_marker_resized)))
//                } else {
//                    if (locationMarker != null) {
//                        locationMarker!!.remove()
//                        locationMarker = null
//                    }
//                    bearingMarker!!.setPosition(center)
//                    bearingMarker!!.setRotation(location.bearing)
//                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(center))
//                }
//            }
//            if (!isMyLocationCentered) {
//                isMyLocationCentered = true
//                moveToMyLocation(googleMap, location)
//            }
//        })
//    }
//    fun removeFrom()
//    {
//        if (accuracyCircle != null) {
//            accuracyCircle!!.remove()
//            accuracyCircle = null
//        }
//        if (locationMarker != null) {
//            locationMarker!!.remove()
//            locationMarker = null
//        }
//        if (bearingMarker != null) {
//            bearingMarker!!.remove()
//            bearingMarker = null
//        }
//        isMyLocationCentered = false
//    }
//    fun removelocationupdate()
//    {
//        if (googleApiClient != null) {
//            if (locationCallback != null)
//                location.removeLocationUpdates(locationCallback)
//        }
//    }
//    fun nightthemecolour()
//    {
////        val sdk = android.os.Build.VERSION.SDK_INT;
////        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
////            changestatus.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.greenclickable))
////            profileeclick.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.greenclickablefull))
////            menubackground.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.greenclickablefull))
////            ctmap.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.greenclickablefull))
////            offbut.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.greenclickable))
////        } else {
////            changestatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.greenclickable))
////            profileeclick.setBackground(ContextCompat.getDrawable(mContext, R.drawable.greenclickablefull))
////            menubackground.setBackground(ContextCompat.getDrawable(mContext, R.drawable.greenclickablefull))
////            ctmap.setBackground(ContextCompat.getDrawable(mContext, R.drawable.greenclickablefull))
////            offbut.setBackground(ContextCompat.getDrawable(mContext, R.drawable.greenclickable))
////        }
////        gotext.setTextColor(Color.parseColor("#ffffff"))
////        offlineclick3.setTextColor(Color.parseColor("#ffffff"))
//    }
//    fun morningthemecolour()
//    {
////        val sdk = android.os.Build.VERSION.SDK_INT;
////        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
////            changestatus.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.clickablebutton))
////            profileeclick.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.clickablebutton))
////            menubackground.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.clickablebutton))
////            ctmap.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.clickablebutton))
////            offbut.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.clickablebutton))
////        } else {
////            changestatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.clickablebutton))
////            profileeclick.setBackground(ContextCompat.getDrawable(mContext, R.drawable.clickablebutton))
////            menubackground.setBackground(ContextCompat.getDrawable(mContext, R.drawable.clickablebutton))
////            ctmap.setBackground(ContextCompat.getDrawable(mContext, R.drawable.clickablebutton))
////            offbut.setBackground(ContextCompat.getDrawable(mContext, R.drawable.clickablebutton))
////        }
////        gotext.setTextColor(Color.parseColor("#ffffff"))
////        offlineclick3.setTextColor(Color.parseColor("#ffffff"))
//    }
//    fun backtologin(mContext: Context)
//    {
//        val realm: Realm = Realm.getDefaultInstance()
//        realm.beginTransaction()
//        realm.deleteAll()
//        realm.commitTransaction()
//        val intent2 = Intent(mContext, splashpage::class.java)
//        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        mContext.startActivity(intent2)
//    }
//    fun movetotrippage()
//    {
////        val intent2 = Intent(mContext, requestmianscreen::class.java)
////        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
////        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
////        mContext.startActivity(intent2)
//    }
//    fun commsnackbaralert(message: String)
//    {
//        val snack = Snackbar.make(coordinate_bottom_sheet_ride_book_now, message, Snackbar.LENGTH_LONG)
//        var view: View = snack.getView()
//        var params: FrameLayout.LayoutParams = FrameLayout.LayoutParams(view.getLayoutParams())
//        params.gravity = Gravity.TOP;
//        view.setLayoutParams(params)
//        snack.show()
//    }
//    fun startoptimizepermission()
//    {
//        val intent = Intent()
//        val packageName = getPackageName();
//        val pm = applicationContext.getSystemService(Context.POWER_SERVICE) as PowerManager
//        if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    !pm.isIgnoringBatteryOptimizations(packageName)
//                } else {
//                    TODO("VERSION.SDK_INT < M")
//                }) {
//            intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
//            intent.setData(Uri.parse("package:" + packageName));
//            startActivity(intent);
//        }
//    }
//    private fun startonlineservice()
//    {
//        val serviceClass = OnlineJobService::class.java
//        val serviceIntent = Intent(applicationContext, serviceClass)
//        if (!viewModel.isMyServiceRunning(mContext, OnlineJobService::class.java)) {
//            startService(serviceIntent)
//            bindService(serviceIntent, arrivalConnection, Context.BIND_AUTO_CREATE)
//        } else
//            bindService(serviceIntent, arrivalConnection, Context.BIND_AUTO_CREATE)
//    }
//    private fun stoponlineservice()
//    {
//        val serviceClass = OnlineJobService::class.java
//        val serviceIntent = Intent(applicationContext, serviceClass)
//        try {
//            unbindService(arrivalConnection)
//        } catch (e: IllegalArgumentException) {
//        }
//        if (viewModel.isMyServiceRunning(mContext, OnlineJobService::class.java)) {
//            stopService(serviceIntent)
//        }
//    }
//}
//
//
