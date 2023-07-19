package mauto_customer.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.*
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.*
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.mauto.chd.BuildConfig
import com.mauto.chd.Locales
import com.mauto.chd.R
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.backgroundservices.FService
import com.mauto.chd.backgroundservices.OnlineJobService
import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.commonutils.AppUtils
import com.mauto.chd.data.LanguageDb
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.mylocation.FindRoute
import com.mauto.chd.mylocation.GpsUtils
import com.mauto.chd.mylocation.MyLocationGoogleMap
import com.mauto.chd.mylocation.TileSystem
import com.mauto.chd.ui.menu.menu.DrawerAdapter
import com.mauto.chd.ui.registeration.LocaleAwareCompatActivity
import com.mauto.chd.ui.registeration.splashpage
import com.mauto.chd.view_model_with_repositary_main.DriverDetailDataModel
import com.mauto.chd.view_model_with_repositary_main.MainPageViewModel
import io.realm.Realm
import kotlinx.android.synthetic.main.mainpage_spoc.*
import kotlinx.android.synthetic.main.nointernetconnection.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import mauto_customer.ui.adapter.SliderAdapter
import mauto_customer.ui.mainpage.*
import mauto_customer.ui.sidemenus.Referrals
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import pub.devrel.easypermissions.EasyPermissions
import kotlin.math.abs
import com.google.firebase.analytics.FirebaseAnalytics
import com.mauto.chd.backgroundservices.dashboardaapii
import com.mauto.chd.ui.sidemenus.*
import kotlinx.android.synthetic.main.activity_support.*
import kotlinx.android.synthetic.main.activity_wallet.*
import kotlinx.android.synthetic.main.driverprofile.*
import kotlinx.android.synthetic.main.mainpage_spoc.appversion
import kotlinx.android.synthetic.main.vehicle_details.*
//import com.mauto.chd.ui.menu.menu.DrawerAdapter.OnItemSelectedListener.tvShow
import kotlinx.android.synthetic.main.vehicleselectionadapter.*
import mauto_customer.ui.sidemenus.Stations
import mauto_customer.ui.sidemenus.View_WalletPage
import org.jetbrains.anko.toast


typealias ItemClickListener<T> = (position: Int, data: T) -> Unit

class MainscreenCustomers : LocaleAwareCompatActivity(), OnMapReadyCallback,
    DrawerAdapter.OnItemSelectedListener, GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, LocationListener {


    @SuppressLint("MissingPermission")
    override fun onConnected(p0: Bundle?) {
        val r = mContext.getResources()
        val density = r.getDisplayMetrics().density
        val size = (256 * density).toInt()
        TileSystem.setTileSize(size)
        accuracyStrokeWidth =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, r.getDisplayMetrics())
        if (!hasPermission(Manifest.permission.ACCESS_FINE_LOCATION) && Build.VERSION.SDK_INT >= 23) {
            val permissions = arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            ActivityCompat.requestPermissions(mContext!!, permissions, 1000)
        } else {
            location = LocationServices.getFusedLocationProviderClient(mContext!!)
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    locationResult ?: return
                    for (location in locationResult.locations) {
                        googlemap?.let { it1 ->
                            driver_marker_resized?.let { it2 ->
                                addTo(
                                    it1,
                                    it2,
                                    location
                                )
                            }
                        }
                    }
                }
            }
            location.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
        }
    }

    override fun onConnectionSuspended(p0: Int) {}
    override fun onConnectionFailed(p0: ConnectionResult) {}
    override fun onLocationChanged(p0: Location?) {}
    val ACCURACY_COLOR = "#1800ce5b"
    val ACCURACY_COLOR_BORDERS = "#8000ce5b"
    private val handler = Handler(Looper.getMainLooper())
    private var isMyLocationCentered = false
    private var accuracyStrokeWidth: Float = 0.0f
    private val RESULT_PAYMENT_METHOD: Int = 1001
    private var accuracyCircle: Circle? = null
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    private var locationMarker: Marker? = null
    private var bearingMarker: Marker? = null
    internal lateinit var mSessionManager: SessionManager
    //    internal lateinit var mSessionManager: setoffer_banner_image
    var aService: OnlineJobService? = null
    private var googleApiClient: GoogleApiClient? = null
    private var locationRequest: LocationRequest? = null
    var zoomcamera: Int = 0
    var modelImage = ArrayList<String>()
    var opendriverdetails: Int = 0
    private lateinit var mMapFragment: SupportMapFragment
    private lateinit var mContext: Activity
    private lateinit var viewModel: MainPageViewModel
    var zoomlevef: Float = 16f
    var locationCallback: LocationCallback? = null
    private var googlemap: GoogleMap? = null
    var ctlat: Double = 0.0
    var open: Int = 0
    var ctlong: Double = 0.0
    var selectedlanage: String? = "fr"
    var currency_symbol:String = ""
    var addamountnew:String = ""

    var drawply: FindRoute? = null
    var dummymarker: BitmapDescriptor? = null
    private var driver_marker_resized: Bitmap? = null
    var driverMarker: Marker? = null
    var driverLatLng: LatLng? = null
    private var myLocationGoogleMap: MyLocationGoogleMap? = null
    lateinit var location: FusedLocationProviderClient
    var availabilystatus: String = "1"
    var bottomSheetDialog: BottomSheetDialog? = null
    var bottomSheetDialogs: BottomSheetDialog? = null

    var animR: AnimatedVectorDrawable? = null
    var navdrivername: TextView? = null
    var isBound = false
    var navmobilevalue: TextView? = null
    var navdriverphoto: ImageView? = null
    var navratingBar: RatingBar? = null
    var app_curent_version:String = "customer"

    //    private var slidingRootNav: SlidingRootNav? = null
    private val POS_DASHBOARD = 0
    private val POS_ACCOUNT = 1
    private val POS_MESSAGES = 2
    private val POS_CART = 3
    private val POS_BIKE = 4
    private val POS_FAQ = 5
    private val POS_SWAPPING = 6
    private val POS_LOGOUT = 7

    private lateinit var screenTitles: Array<String>
    private lateinit var screenIcons: Array<Drawable?>
    var myService: FService? = null
    private var opens: Boolean = true
    var selectedbikestype: String = ""
    lateinit var wakeLock: PowerManager.WakeLock
    val REQUEST_CODE_PERMISSIONS = 101
    var check = "0"
    var customerphno:String = ""
    var customertogophno:String = "8884"
    var customebenninrphno:String = "7131"
    var customerrawndaphno:String = "737378052"
    var checkone = "0"
    var supportemail ="info@spironet.com"
    lateinit var mHelpers: LanguageDb
    var driverpath = ""
    var defaultbanner =""
    private lateinit var repeatFun: Job

    val pts: MutableList<LatLng> = ArrayList()
    private var isGPS: Boolean = false;
    private var RC_LOCATION_CONTACTS_PERM: Int = 124
    var app_version: String = ""

    //image slider
    lateinit var isViewImage: ViewPager2
    lateinit var isList: ArrayList<SliderModel>
    lateinit var adapter: SliderAdapter
    val sliderHandler = Handler()

    //
    private lateinit var swipeRefresh: SwipeRefreshLayout


    private val LOCATION_AND_CONTACTS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
    private val arrivalConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as OnlineJobService.MyLocalBinder
            aService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            isBound = false
        }
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
        sliderHandler.removeCallbacks(sliderRunnable)
    }


    @InternalCoroutinesApi
    @SuppressLint("InvalidWakeLockTag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainpage_spoc)
//        binding = DataBindingUtil.setContentView(this, R.layout.mainpage_spoc)
        mContext = this@MainscreenCustomers
        mSessionManager = SessionManager(mContext)
        mHelpers = LanguageDb(applicationContext)
        getappversion()

        offermodule()
        getFaqruser()
        getwalletbalance()



        customer_id_text1.setText(mSessionManager.getdriver_names())

        defaultbanner = mSessionManager.getDefaultbanner()
        Glide.with(mContext as MainscreenCustomers).load(defaultbanner).into(default_banner)

        Log.d("hfejgdjgfkjdx","$defaultbanner")



//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        isViewImage = findViewById(R.id.is_view_image)
        isList = ArrayList()


        if (mSessionManager.getModelImage() != null)
            modelImage = mSessionManager.getModelImage()!!

        if (modelImage.size > 0) {
            for (g in 0 until modelImage.size) {
                var banner_image = modelImage[g]
                isList.add(SliderModel(g, banner_image))
                isViewImage.visibility = View.VISIBLE
                defaultbanner3.visibility = View.GONE
            }
        }else{
            isViewImage.visibility = View.GONE
            defaultbanner3.visibility = View.VISIBLE
        }


//        isList.add(SliderModel(0,"https://s3.amazonaws.com/mautoafrica-s3kd8/offerPicture/4e1cbd6e84cca026db291fc0f0b379efio9dz8kev8upx2y6lygske1qppeai16g.png"))
//        isList.add(SliderModel(1,"https://s3.amazonaws.com/mautoafrica-s3kd8/offerPicture/4e1cbd6e84cca026db291fc0f0b379efio9dz8kev8upx2y6lygske1qppeai16g.png"))
//        isList.add(SliderModel(2,"https://s3.amazonaws.com/mautoafrica-s3kd8/offerPicture/4e1cbd6e84cca026db291fc0f0b379efio9dz8kev8upx2y6lygske1qppeai16g.png"))
//        isList.add(SliderModel(3,"https://s3.amazonaws.com/mautoafrica-s3kd8/offerPicture/4e1cbd6e84cca026db291fc0f0b379efio9dz8kev8upx2y6lygske1qppeai16g.png"))
//        Log.d("hidd",isList)
        adapter = SliderAdapter(isList, this)

        isViewImage.adapter = adapter
        isViewImage.clipChildren = false
        isViewImage.clipToPadding = false
        isViewImage.offscreenPageLimit = 4
        isViewImage.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        Handler().postDelayed({

            val offerBanareImage = mSessionManager.getoffer_banner_image()

//            val offerBanaresetImage = mSessionManager.setoffer_banner_image("YES")
            if (offerBanareImage == "NO") {
                mSessionManager.setoffer_banner_image("YES")
                isViewImage.visibility = View.VISIBLE
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
                overridePendingTransition(0, 0);
//

            }

        }, 1000)

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
//        fun transfromPage(page:View,position:Float){
//            val r = 1 - aps(position)
//        }

        compositePageTransformer.addTransformer(object : ViewPager2.PageTransformer {


            override fun transformPage(page: View, position: Float) {

                val r = 1 - abs(position)
//                page.scaleY = 0.85f + r + 0.15f

            }
        })
        isViewImage.setPageTransformer(compositePageTransformer)
        isViewImage.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, 5000)
                if (position == isList.size - 2) {
                    isViewImage.post(runnable)
                }


            }
        })

        val toolbar = findViewById<ImageView>(R.id.toolbar)
//        slidingRootNav = SlidingRootNavBuilder(this)
//                .withMenuOpened(false)
//                .withContentClickableWhenMenuOpened(false)
//                .withSavedState(savedInstanceState)
//                .withMenuLayout(R.layout.menu_left_drawer)
//                .inject()

        screenTitles = loadScreenTitles()
        myLocationGoogleMap = MyLocationGoogleMap(mContext)
        app_version = BuildConfig.VERSION_NAME
        appversion.setText("V " + app_version)

        val powerManager: PowerManager = mContext.getSystemService(POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Lock")
        Log.d("mswap_battery", mSessionManager.getswap_battery())


        goprofile.setOnClickListener {
            val intent2 = Intent(mContext, driverprofile()::class.java)
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mContext.startActivity(intent2)
        }

        default_banner.setOnClickListener {
            SwapstDialogstaction(defaultbanner.toString())
        }


//        prafile32.setOnClickListener {
//            val intent2 = Intent(mContext, driverprofile()::class.java)
//            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//            mContext.startActivity(intent2)
//        }
//        profile_lay.setOnClickListener {
//            val intent2 = Intent(mContext, driverprofile()::class.java)
//            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//            mContext.startActivity(intent2)
//        }
        Service_lay.setOnClickListener {

            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Offerpage_onclick")
            mFirebaseAnalytics!!.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)

            val intent2 = Intent(mContext, Offers()::class.java)
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mContext.startActivity(intent2)
        }
        swapstaction.setOnClickListener {
            locationAndContactsTask()
        }
        servicestaction.setOnClickListener {
            locationservicestaction()
        }
        logoout.setOnClickListener {
            exitappalert()
        }
        Vehicle.setOnClickListener {
            val intent2 = Intent(mContext, VehicleDetails()::class.java)
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mContext.startActivity(intent2)
        }
        callcustomer.setOnClickListener {
            callcustomer()
        }
        email_click3.setOnClickListener {
            sendEmail(this,supportemail)
        }
        Vehicle_lay.setOnClickListener {

            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "my_item_id")
//            bundle.putString("show_name", VehicleDetails().toString())
            mFirebaseAnalytics!!.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
//            mFirebaseAnalytics?.logEvent("show_selected", bundle)

            val intent2 = Intent(mContext, VehicleDetails()::class.java)
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mContext.startActivity(intent2)
        }
        Payment_lay.setOnClickListener {
            val intent2 = Intent(mContext, WalletPage()::class.java)
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mContext.startActivity(intent2)
        }
//        prebookings_textview.setOnClickListener {
//            val intent2 = Intent(mContext, WalletPageNew()::class.java)
//            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//            mContext.startActivity(intent2)
//        }
        amountcheck.setOnClickListener {
            val intent2 = Intent(mContext, WalletPage()::class.java)
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mContext.startActivity(intent2)
        }

        amountcheck2.setOnClickListener {
            val intent2 = Intent(mContext, WalletPage()::class.java)
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mContext.startActivity(intent2)
        }
        view.setOnClickListener {
            val intent_otppage = Intent(mContext, wallettransactionsdetailspage::class.java)
            mContext.startActivity(intent_otppage)
//            val intent2 = Intent(mContext, View_WalletPage()::class.java)
//            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//            mContext.startActivity(intent2)
        }
        view2.setOnClickListener {
            val intent_otppage = Intent(mContext, wallettransactionsdetailspage::class.java)
            mContext.startActivity(intent_otppage)
//            val intent2 = Intent(mContext, View_WalletPage()::class.java)
//            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//            mContext.startActivity(intent2)
        }


        pay_now.setOnClickListener {

            addamountnew=h2.text.toString()
            if ( addamountnew.equals("0") ){


                toast("No Rental Due Amount")
            }else{
                getpaymentpage()

            }

        }
        COMMANDO_model.setOnClickListener {
            prebookings_textview.visibility = View.VISIBLE
            CHAP_radiobutton.isChecked = false
        }
        Payment.setOnClickListener {
            val intent2 = Intent(mContext, Vouchers()::class.java)
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mContext.startActivity(intent2)
        }

        CHAP_radiobutton.setOnClickListener {
            prebookings_textview.visibility = View.VISIBLE
            COMMANDO_model.isChecked = false

        }
        language?.setOnClickListener {
            chooselanguage()
        }

//        swipeRefresh = findViewById(R.id.swipeRefresh)
//        swipeRefresh.setOnRefreshListener {
//            getAllData()
//        }







        referals_lay.setOnClickListener {
            val intent2 = Intent(mContext, Referrals()::class.java)
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mContext.startActivity(intent2)
        }

//        contact_lay
//        support
        shsre2.setOnClickListener {
            val intent2 = Intent(mContext, ShareReferlCode()::class.java)
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mContext.startActivity(intent2)


        }
//        ShareReferlCode
//        suport_emailh.setOnClickListener {
//
//            val intent2 = Intent(mContext, support()::class.java)
//            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//            mContext.startActivity(intent2)
//        }

        contact_lay.setOnClickListener {

            val intent2 = Intent(mContext, support()::class.java)
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mContext.startActivity(intent2)
        }

        if (mSessionManager.getswap_battery().equals("1")) {

        } else {
//            val adapter = DrawerAdapter(Arrays.asList(
//                    createItemFor(POS_CART).setChecked(true),
//                    createItemFor(POS_SWAPPING),
//                    SpaceItem(0),
//                    createItemFor(POS_LOGOUT)), this)
//            adapter.setListener(this)
//            list.isNestedScrollingEnabled = false
//            list.layoutManager = LinearLayoutManager(this)
//            list.adapter = adapter
//            adapter.setSelected(POS_DASHBOARD)
        }


        connection_lay2.setOnClickListener {
            recreate()
        }

//        toolbar.setOnClickListener {
//            if (opens) {
//                opens = false
//                slidingRootNav!!.openMenu()
//            }else{
//                opens = true
//                slidingRootNav!!.closeMenu()
//            }
//
//
//        }
        val list = findViewById<RecyclerView>(R.id.list)
        val logouts = findViewById<ImageView>(R.id.logouts)


        animR = iv_line.getBackground() as AnimatedVectorDrawable?
        lgkeyset()
//        intilizecommonvalue()
        initviewmodel()
        getdriverprofile()
        driverpic()

        var id = mSessionManager!!.getApiHeader()
        earnings_today.setText(mSessionManager.gettoday_earnings())
        misstrip.setText(mSessionManager.getmissed_rides())
        comtrip.setText(mSessionManager.getcompleted_rides())
        Log.d("chjyhshd", id.toString())
//        val versionName: String = BuildConfig.VERSION_CODE.toString()
//        binding.setViewModel(viewModel)
        initializeMap()
        open = 0
        opendriverdetails = 0
        val toggle =
            ActionBarDrawerToggle(this, drawer_layout, null, R.string.settings, R.string.settings)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
//        nav_view.setNavigationItemSelectedListener(this)
//        profileeclick.setOnClickListener {
//            opendriverdetails = 1
//////            viewModel.dispalydriverdetails(mContext)
////           val intent2 = Intent(mContext, otpride()::class.java)
////        mContext.startActivity(intent2)
//
//        }
//        rounded_iv_1.setOnClickListener {
//            val intent3 = Intent(mContext, driverprofile()::class.java)
//            mContext.startActivity(intent3)
//        }
//        logouts.setOnClickListener {
//
//            logoutalert()
//
//        }
        if (mSessionManager.getdriver_profile_image().isEmpty()) {
            getData()

        }

    }
    fun sendEmail(activity:Activity, email: String) {
        val to = arrayOf(email)
        val cc = arrayOf("")
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"))

            intent.putExtra(Intent.EXTRA_EMAIL, to)/*
            intent.putExtra(Intent.EXTRA_SUBJECT, sub)
            intent.putExtra(Intent.EXTRA_TEXT, text)*/
            activity.startActivityForResult(intent,333)

        } catch (e: PackageManager.NameNotFoundException) {

            Toast.makeText(activity, "Email has sent successfully", Toast.LENGTH_SHORT).show()
        }

    }

    fun getappversion()
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra("customer", app_curent_version)
        Log.d("vereh","$app_curent_version")
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.version))
        mContext.startService(intent)
    }

    fun getAllData() {
        if(swipeRefresh.isRefreshing){
            swipeRefresh.isRefreshing = false
        }
    }

    fun getwalletbalance() {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.wallet_balance))
        mContext.startService(intent)
    }
    fun callcustomer() {

        customerphno= mSessionManager.getDriverCuntryCode()
//        customerphno=mSessionManager.getsupport_number()
        Log.d("help_number","$customerphno")
        if(customerphno.equals("+228")) {

//            commsnackbaralert(getString(R.string.nonotavaialbe))
            val intent =  Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", customertogophno, null));
            startActivity(intent);
        }else if (customerphno.equals("+229")){
            val intent =  Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", customebenninrphno, null));
            startActivity(intent);
        }else if (customerphno.equals("+250")){
            val intent =  Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", customerrawndaphno, null));
            startActivity(intent);
        }

    }
    //slder run code

    val sliderRunnable = Runnable { isViewImage.currentItem = isViewImage.currentItem + 1 }

    val runnable = Runnable {
        isList.addAll(isList)
        adapter.notifyDataSetChanged()
    }

//    override fun onPause() {
//        super.onPause()
//
//    }


    fun getpaymentpage() {
        val intent_edit_address = Intent(this, PaymentOtherTypePage::class.java)
        intent_edit_address.putExtra("amount", addamountnew)
        intent_edit_address.putExtra("simble", currency_symbol)
        startActivityForResult(intent_edit_address, RESULT_PAYMENT_METHOD)
        Log.d("resulooo","$addamountnew")
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fun offermodule(){
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.offer_copen1))
        intent.putExtra("driver_id", mSessionManager.getDriverId())
        mContext.startService(intent)
    }

    fun chooselanguage() {
        var languagedialog = BottomSheetDialog(this)
        val view = getLayoutInflater().inflate(R.layout.chooselanguagesheet, null);
        languagedialog!!.setContentView(view)
        languagedialog!!.setCancelable(true)
        val english_lay = view.findViewById(R.id.english_lay) as LinearLayout
        val french_lay = view.findViewById(R.id.french_lay) as LinearLayout
        val cloepage = view.findViewById(R.id.cloepageoflanguage) as LinearLayout

        val englishchoosen = view.findViewById(R.id.englishchoosen) as TextView
        val tamilchoosen = view.findViewById(R.id.tamilchoosen) as TextView
        val urduchoosen = view.findViewById(R.id.urduchoosen) as TextView
//        englishchoosen.setOnClickListener {
//            languagedialog!!.dismiss()
//            updateLocale(Locales.English)
//        }

        french_lay.setOnClickListener {
            languagedialog!!.dismiss()
            updateLocale(Locales.French)
            resartapp()

            selectedlanage = "fr"
            mSessionManager.setseletedlagname(selectedlanage!!)


        }


        english_lay.setOnClickListener {
            languagedialog!!.dismiss()


            updateLocale(Locales.English)
            selectedlanage = "en"
            mSessionManager.setseletedlagname(selectedlanage!!)
            resartapp()

        }

        cloepage.setOnClickListener {
            languagedialog!!.dismiss()
        }
        languagedialog!!.show()
    }


    fun resartapp() {
        val mainpage = Intent(mContext, splashpage::class.java)
        mainpage.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(mainpage)
        finish()
    }


    fun locationAndContactsTask() {
        if (hasLocationAndContactsPermissions()) {
            // Permission given
            checklocationonedindevice()
        } else {
            // Ask for both permissions
            EasyPermissions.requestPermissions(
                this,
                "",
                RC_LOCATION_CONTACTS_PERM,
                *LOCATION_AND_CONTACTS
            )

        }
    }
    fun locationservicestaction() {
        if (hasLocationAndContactsPermissions()) {
            // Permission given
            checklocationsevicestaction()
        } else {
            // Ask for both permissions
            EasyPermissions.requestPermissions(
                this,
                "",
                RC_LOCATION_CONTACTS_PERM,
                *LOCATION_AND_CONTACTS
            )

        }
    }
    private fun hasLocationAndContactsPermissions(): Boolean {
        return EasyPermissions.hasPermissions(this, *LOCATION_AND_CONTACTS)
    }

    fun checklocationonedindevice() {
        try {
            val lm =
                applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager;
            var gps_enabled: Boolean = false;
            var network_enabled: Boolean = false;
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if (!gps_enabled && !network_enabled) {
                GpsUtils(this).turnGPSOn(GpsUtils.onGpsListener() {
                    @Override
                    fun gpsStatus(isGPSEnable: Boolean) {
                        isGPS = isGPSEnable;
                    }
                })
            } else {
                val intent2 = Intent(mContext, Swapstations()::class.java)
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                mContext.startActivity(intent2)
                Log.d("hekjejc", "sdfhhshf")
//                gpsenabledfunction()
            }
        } catch (e: Exception) {
        }
    }

    fun SwapstDialogstaction(defaultbanner:String)
//    ,profileimages:String
    {

        val dialog = Dialog(mContext)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.swapstaction_image)
        dialog.window!!.setGravity(Gravity.CENTER)
        val displayMetrics = DisplayMetrics()
        var station_images1=dialog.findViewById<ImageView>(R.id.station_images1)


        dialog.show()
        var profile = defaultbanner
        Glide.with(mContext)
            .load(profile)
            .into(station_images1)
    }



    fun checklocationsevicestaction() {
        try {
            val lm =
                applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager;
            var gps_enabled: Boolean = false;
            var network_enabled: Boolean = false;
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if (!gps_enabled && !network_enabled) {
                GpsUtils(this).turnGPSOn(GpsUtils.onGpsListener() {
                    @Override
                    fun gpsStatus(isGPSEnable: Boolean) {
                        isGPS = isGPSEnable;
                    }
                })
            } else {
                val intent2 = Intent(mContext, Servicestations()::class.java)
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                mContext.startActivity(intent2)
                Log.d("hekjejc", "sdfhhshf")
//                gpsenabledfunction()
            }
        } catch (e: Exception) {
        }
    }



//    @InternalCoroutinesApi
//    fun repeatFun(): Job {
//        val scope = CoroutineScope(Dispatchers.Default)
//        return scope.launch {
//            while(isActive) {
//                delay(10000)
//            }
//        }
//    }

    private fun getkey(key: String): String? {
        return mHelpers.getvalueforkey(key)
    }

    private fun getData() {
//        progress_lay_spoc.visibility=View.VISIBLE
        val aIntent = Intent(this, commonapifetchservice::class.java)
        aIntent.putExtra(getString(R.string.intent_putextra_api_key), getString(R.string.dashboarapicall)
        )
        startService(aIntent)
    }

    private fun getboundary() {
        val aIntent = Intent(this, commonapifetchservice::class.java)
        aIntent.putExtra(getString(R.string.intent_putextra_api_key), getString(R.string.boundary))
        startService(aIntent)
    }

    private fun getgeofenceupdate(contains1: String) {
        val aIntent = Intent(this, commonapifetchservice::class.java)
        aIntent.putExtra(
            getString(R.string.intent_putextra_api_key),
            getString(R.string.geofence_update)
        )
        aIntent.putExtra("type_driver", contains1)
        startService(aIntent)
    }

    fun intilizecommonvalue() {
        myLocationGoogleMap = MyLocationGoogleMap(mContext)
//        layout_ripplepulse3.startRippleAnimation()
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
//            // commonclose()
//        }
//        profilerdit.setOnClickListener {
//            // commonclose()
//            val intent_otppage = Intent(mContext, editdriverdetails::class.java)
//            startActivity(intent_otppage)
//        }
//        tripsum.setOnClickListener {
//            // commonclose()
//            val intent_otppage = Intent(mContext, addoreditvehicle::class.java)
//            startActivity(intent_otppage)
//        }
//        settingspages.setOnClickListener {
////            val intent_otppage = Intent(mContext, settingspage::class.java)
////            startActivity(intent_otppage)
//        }
    }

    //    private fun createItemFor(position: Int): DrawerItem<SimpleItem.ViewHolder> {
////        return SimpleItem(screenIcons[position], screenTitles[position])
////                .withIconTint(color(R.color.text_color_gray))
////                .withTextTint(color(R.color.text_color_gray))
////                .withSelectedIconTint(color(R.color.text_color_gray))
////                .withSelectedTextTint(color(R.color.text_color_gray))
//    }
    private fun initializeMap() {
        try {
            mMapFragment = supportFragmentManager
                .findFragmentById(R.id.firstpagemap) as SupportMapFragment
            mMapFragment.getMapAsync(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun commonclose() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            drawer_layout.openDrawer(GravityCompat.START)
            viewModel.defaultvalueloader(mContext)
        }
    }

    @InternalCoroutinesApi
    override fun onMapReady(mGoogleMap: GoogleMap?) {

        googlemap = mGoogleMap
        defaultcar()
        val locationmaode = mSessionManager!!.getLocationMode().toInt()
        connectToGoogleApiClient()
        if (locationmaode == 1) {
            nightthemecolour()
            googlemap!!.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this,
                    R.raw.night_map_style
                )
            )
            googleApiClient?.connect()
        } else {
            googlemap!!.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            myLocationGoogleMap!!.addTo(
                googlemap,
                driver_marker_resized,
                dummymarker,
                locationmaode
            )
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                mGoogleMap!!.isMyLocationEnabled = false
            }
        } else {
            mGoogleMap!!.isMyLocationEnabled = false
        }
        mGoogleMap!!.getUiSettings().setRotateGesturesEnabled(false)
        mGoogleMap!!.getUiSettings().setMapToolbarEnabled(false)
        toogleListener()
    }

    @InternalCoroutinesApi
    private fun toogleListener() {
//
//        driver_dashboard_toggle_button.setColorOn(getResources().getColor(R.color.colorgreendark));
//        driver_dashboard_toggle_button.setColorOff(getResources().getColor(R.color.black));
//
//        driver_dashboard_toggle_button.setColorOnLabel(getResources().getColor(R.color.white));
//        driver_dashboard_toggle_button.setColorOffLabel(getResources().getColor(R.color.white));
////        val tf: Typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/sfuidisplaybold.otf")
////
////        driver_dashboard_toggle_button.setTypeface(tf)
//
////        driver_dashboard_toggle_button.setColorBorder(getResources().getColor(R.color.black));
//
//        driver_dashboard_toggle_button.setLabelOn(getResources().getString(R.string.Online));
//        driver_dashboard_toggle_button.setLabelOff(getResources().getString(R.string.Offline));
////        km_lay.setBackgroundResource(R.drawable.buttoncurvedbackgroundblack)
//
//
//        driver_dashboard_toggle_button.setOnToggledListener { toggleableView, isOn ->
//            if (isOn){
//                availbiltyon()
//                getboundary()
//
////                km_lay.setBackgroundResource(R.drawable.buttoncurvedbackground)
//
//            }else{
//
////                km_lay.setBackgroundResource(R.drawable.buttoncurvedbackgroundblack)
//
//                availabilityoff()
//
//
//            }
//        }
    }

    fun lgkeyset() {
//        completed_trips.setText(getkey("completed_trips"))
//        today_earnings.setText(getkey("today_earnings"))

    }

    fun driverpic() {
        customer_id_text.setText(mSessionManager.getdriver_names())

        phone_textview.setText(mSessionManager.getdriver_vehicle())
        bike_number_textview.setText(mSessionManager.getdriver_vehicle_news())
        veh_mod_das.setText(mSessionManager.getdriver_model())
        driverpath = mSessionManager.getdriver_profile_image()
//        isList =  mSessionManager.getoffer_banner_image()

        Glide.with(mContext).load(driverpath).addListener(@SuppressLint("CheckResult")
        object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                custom_profile_lay.visibility = View.VISIBLE
                progress_time_faqs.visibility = View.GONE
                return true
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progress_time_faqs.visibility = View.GONE
                custom_profile_lay.visibility = View.VISIBLE
                profile_images.setImageDrawable(resource)
                return true
            }

        }).into(profile_images)


//

    }


    @InternalCoroutinesApi
    fun initviewmodel() {


        viewModel = ViewModelProviders.of(this).get(MainPageViewModel::class.java)
        viewModel.getDefaultStatus(mContext)
        viewModel.getdriverdetails(mContext)
        viewModel.getdocumentstatus(mContext)
        viewModel.dashbaordapiservice(mContext)
        viewModel.getdriverimage(mContext)
        viewModel.defaultvalueloader(mContext)
        bottomSheetDialogs = BottomSheetDialog(this)
        bottomSheetDialogs = BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme)
//        walletdialog()
        viewModel.defaultonlinestatusobserver().observe(this, Observer {
            runOnUiThread {
                commonhidego(it)
            }
        })
        viewModel.defaultdriverdetailobserver().observe(this, Observer {
            runOnUiThread {
//                navigationheadervalue(it)
            }
        })
        viewModel.opendrwaerlayoutobserver().observe(this, Observer {
            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START)
            } else {
                drawer_layout.openDrawer(GravityCompat.START)
                viewModel.defaultvalueloader(mContext)
            }
        })
        viewModel.onstatusobserver().observe(this, Observer {
            availbiltyon()
        })
        viewModel.zoomlevelobserver().observe(this, Observer {
            if (googlemap != null)
                googlemap!!.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            it.get(0).zoomlatitude!!.toDouble(),
                            it.get(0).zoomlongitude!!.toDouble()
                        ), 15.5f
                    ), 1000, null
                );
        })
        viewModel.locationmodeobserver().observe(this, Observer {
            if (it == 1) {
                nightthemecolour()
                googlemap!!.clear()
                myLocationGoogleMap?.removeFrom(googlemap)
                removeFrom()
                if (googleApiClient == null)
                    connectToGoogleApiClient()
                if (googleApiClient != null) {
                    googleApiClient?.isConnected?.let {
                        if (it)
                            googleApiClient?.disconnect()
                    }
                    googleApiClient?.connect()
                }
                googlemap!!.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                        this,
                        R.raw.night_map_style
                    )
                )
            } else {
                morningthemecolour()
                googlemap!!.clear()
                if (googleApiClient != null) {
                    googleApiClient?.isConnected?.let {
                        if (it)
                            googleApiClient?.disconnect()
                    }
                }
                removelocationupdate()
                myLocationGoogleMap?.removeFrom(googlemap)
                myLocationGoogleMap!!.addTo(googlemap, driver_marker_resized, dummymarker, it)
                googlemap!!.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            }
        })
        viewModel.offstatusobserver().observe(this, Observer {
            if (it == 1)
                availabilityoff()
        })

        viewModel.earningtodayobserver().observe(this, Observer {
//            customer_id_text.setText(it)

        })
        viewModel.misstripobserver().observe(this, Observer {
//            bike_number_textview.setText(it)

        })
        viewModel.completedtripobserver().observe(this, Observer {
//            phone_textview.setText(it)

        })
        viewModel.dutyrideobserver().observe(this, Observer {
            if (!it.equals("")) {
                mSessionManager.setDutyrideid(it)
                if (mSessionManager.getis_otp_ride()) {
                    moveToOtpRidePage()
                } else {
                    movetotrippage()
                }

            }

        })
        viewModel.mobilenoobserver().observe(this, Observer {
            navmobilevalue!!.setText(it)
        })
        viewModel.driverimagesobserver().observe(this, Observer {
            mSessionManager.setdriver_profile_image(it)
//            progress_lay_spoc.visibility=View.GONE

//                progress_lay_profiles.visibility=View.VISIBLE


        })
        viewModel.Offerbanerimageobserver().observe(this, Observer {

//            progress_lay_spoc.visibility=View.GONE

//                progress_lay_profiles.visibility=View.VISIBLE


        })

        viewModel.driverfulldetailsobserver().observe(this, Observer {
            if (opendriverdetails == 1) {
                if (it != null)
                    driverprofillee(it)
            }
        })
        viewModel.docstatusobserver().observe(this, Observer {
            runOnUiThread {

                if (mSessionManager.getmstage().equals("NOT_VERIFIED")) {
                    if (bottomSheetDialogs != null) {
                        if (!bottomSheetDialogs!!.isShowing) {
                            verificationdialog()
                        }
                    }
                }
//
            }
        })
        viewModel.onlinestatusobserver().observe(this, Observer {
            runOnUiThread {
                commonhidego(it)
            }
        })
        viewModel.driveretailsviewmodellObserver().observe(this, Observer {
            if (it != null) {
                viewModel.getdocumentstatus(mContext)
                var driverimagepath: String = it!!
                mSessionManager.setdriver_profile_image(it)

                if (!it!!.startsWith("http")) {
                    driverimagepath = getString(R.string.amaozonurlprofileimage) + it!!
                    Log.d("dsdsdsdsdsd", it)
                    Log.d("xfdzfdf", driverimagepath)


                }

            }
        })
    }
    fun getFaqruser() {

        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.vehicle_list))
        mContext.startService(intent)
    }

    fun getdriverprofile() {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(
            mContext.getString(R.string.intent_putextra_api_key),
            mContext.getString(R.string.profile_driver)
        )
        mContext.startService(intent)
    }


    fun updateDailog() {

        val dialog = Dialog(mContext, android.R.style.Theme_Light_NoTitleBar_Fullscreen)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.app_update_dialog)
        dialog.window!!.setGravity(Gravity.CENTER)

        var close = dialog.findViewById<TextView>(R.id.close)
        var update = dialog.findViewById<TextView>(R.id.update)
        Log.d("vaytkfw","mali va;;")

        update.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.mauto.customer"))
            startActivity(intent)
            toast("update the app")
        }

        close.setOnClickListener{
            super.onBackPressed()

        }
        dialog.show()

    }

    @InternalCoroutinesApi
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun doThis(intentServiceResult: IntentServiceResult) {
//        loader.visibility = View.GONE
        var apiName: String = intentServiceResult.apiName
        if (apiName.equals("fleet_immobilized")) {
            walletdialog()

        }

        if (apiName.equals(getString(R.string.wallet_balance))) {
            var finalresponse: String = intentServiceResult.resultValue
            if (finalresponse == "failed") {

            }else{
                val response_json_object = JSONObject(finalresponse)
                val status = response_json_object.getString("status")

                if (status.equals("1")) {
                    val response = response_json_object.getJSONObject("response")
                    var wallet_id=response.getString("wallet_id")
                    var currency_code=response.getString("currency_code")
                    mSessionManager.setwallet_id(wallet_id)
                    mSessionManager.setcurncy_id(currency_code)
                }
            }

        }
        if (apiName.equals(getString(R.string.version)))
        {
            var finalresponse: String = intentServiceResult.resultValue
            val response_json_object = JSONObject(finalresponse)
            val status = response_json_object.getString("status")
            if (status.equals("1")) {
                val response = response_json_object.getJSONObject("response")
                var version = response.getString("version")
                if (version.equals("3.2")){

                }else{
                    updateDailog()
                }

            }
        }


        if (apiName.equals(getString(R.string.boundary))) {
            var response: String = intentServiceResult.resultValue
            if (response != "failed") {
                Log.d("boundaryyss", response)
                var responseObj = JSONObject(response)
                val status = responseObj.getString("status")
                if (status.equals("1")) {
                    var msresponse = responseObj.getJSONObject("response")
                    val response_boundary_array = msresponse.getJSONArray("boundary")

                    if (response_boundary_array.length() > 0) {
                        for (i in 0 until response_boundary_array.length()) {
                            val response_object_faq = response_boundary_array.getJSONObject(i)
                            var mlat = response_object_faq.getString("lat")
                            var mlon = response_object_faq.getString("lon")
                            pts.add(LatLng(mlat.toDouble(), mlon.toDouble()))

//                            pts.add(LatLng(13.0437245 ,80.2722176))
//                            pts.add(LatLng(13.0457313 ,80.2743538))
//                            pts.add(LatLng(13.0432542,80.2764469))
//                            pts.add(LatLng(13.0419372,80.2733555 ))
//                            pts.add(LatLng(13.0435887,80.2722498 ))
//                            pts.add(LatLng(13.0437036, 80.2721962))
                        }
                        check = "1"
//                        repeatFun = repeatFun()

                    }
                }


            }
        }





        if (apiName.equals(getString(R.string.vehicle_list))) {
            var finalresponse: String = intentServiceResult.resultValue
            if (finalresponse == "failed") {
//                toast(getString(R.string.api_failure_toast_label))
            } else {
                Log.d("dfhab", "shjbdhj")
                Log.d("listnew", finalresponse)
                val response_json_object = JSONObject(finalresponse)

                val status = response_json_object.getString("status")
                if (status.equals("1")) {

                    val response = response_json_object.getJSONObject("response")
                    val response_faq_array = response.getJSONArray("vehicles")
                    if (response_faq_array.length() > 0) {

                        for (i in 0 until response_faq_array.length()) {
                            val response_object_faq = response_faq_array.getJSONObject(i)

                            var vehicle_number = response_object_faq.getString("vehicle_number")

                            var vehicle_model = response_object_faq.getString("vehicle_model")
//                            veh_mod_das.setText(vehicle_model)
                            Log.d("fagdfz","$vehicle_model")
                        }

                    }
                }
            }
        }


        if (apiName.equals(getString(R.string.getlocation))) {
            var bothlatandlong: String = intentServiceResult.resultValue
            val latlongvalue = bothlatandlong.split(",").toTypedArray()
            ctlat = latlongvalue[0].toDouble()
            ctlong = latlongvalue[1].toDouble()
            driverLatLng = LatLng(ctlat, ctlong)
            if (driverLatLng != null) {
                if (driverMarker == null)
                    driverMarker = googlemap?.addMarker(
                        MarkerOptions().position(driverLatLng!!)
                            .icon(BitmapDescriptorFactory.fromBitmap(driver_marker_resized))
                    )!!
            }
            if (zoomcamera == 0) {
                val cameraPosition = CameraPosition.Builder()
                    .target(LatLng(latlongvalue[0].toDouble(), latlongvalue[1].toDouble()))
                    .zoom(zoomlevef)
                    .build()
                runOnUiThread {
                    googlemap?.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                }
                zoomcamera = 1
            }
        } else if (apiName.equals(getString(R.string.acceptcall))) {
            var response: String = intentServiceResult.resultValue
            if (response != "failed") {
                var responseObj = JSONObject(response)
                if (responseObj.getString("status").equals("0"))
                    commsnackbaralert(getString(R.string.failedproblem))
            } else
                commsnackbaralert(getString(R.string.failedproblem))
        } else if (apiName.equals(getString(R.string.dashboarapicall))) {
            var response: String = intentServiceResult.resultValue
            if (response != "failed")
                viewModel.getsplitrepsonse(applicationContext, response)

            var responseObj = JSONObject(response)

            if (responseObj.getString("status").equals("1")) {
                var getingres: String = responseObj.getString("response")
                var resultsresponse = JSONObject(getingres)
                Log.d("edggf55gfdgf", resultsresponse.toString())
                var agent_image = resultsresponse.getString("driver_image")

                var agent_name = resultsresponse.getString("driver_name")
                var wbalance =resultsresponse.getString("wbalance")
                var  rental_due_amount = resultsresponse.getString("rental_due_amount")
                currency_symbol = resultsresponse.getString("currency")
                wallet_amount.setText(wbalance)
                h2.setText(rental_due_amount)
                if(rental_due_amount.equals("0")){
                    total.visibility = View.GONE
                }else{
                    total.visibility = View.VISIBLE
                }

                Log.d("enakoduma","$rental_due_amount")

            }


            if (mSessionManager.getis_electric().equals("true")) {
                km_lay.visibility = View.VISIBLE
                kmebike1.setText(mSessionManager.getmileage() + "km")
                percentebike1.setText(mSessionManager.getpercentage() + "%")
//
            } else {
                km_lay.visibility = View.GONE
            }
        }

        else if (apiName.equals(getString(R.string.docpendingstage))) {
//            if (bottomSheetDialog != null)
//            {
//                if (!bottomSheetDialog!!.isShowing)
//                    successdialog(getString(R.string.verfication_pending), getString(R.string.thanks_for_registering_with_us_our_admin_will_be_verfying_your_document))
//            }
//            else
//                successdialog(getString(R.string.verfication_pending), getString(R.string.thanks_for_registering_with_us_our_admin_will_be_verfying_your_document))
        } else if (apiName.equals(getString(R.string.logoutcallapi))) {
            Log.d("hhshhsdsds", "dshfhfsddf")
            backtologin(mContext)
        } else if (apiName.equals(getString(R.string.loadme))) {
            iv_line.visibility = View.VISIBLE
            animationstart()
        } else if (apiName.equals(getString(R.string.driveravailable))) {
            var response: String = intentServiceResult.resultValue
            if (response != "failed")
                viewModel.getonlinestatus(response, availabilystatus, mContext)
            else
                AppUtils.commonerrorsheet(
                    mContext,
                    getString(R.string.failed),
                    getString(R.string.failedproblem)
                )
        }
        if (apiName.equals(getString(R.string.logoutcallapione))) {
            Log.d("hhshsfffhsdsds", "gfddgfd")

            viewModel.logout(mContext)

        } else if (apiName.equals(getString(R.string.profile_driver))) {
            var finalresponse: String = intentServiceResult.resultValue
            if (finalresponse == "failed") {

            } else {
                val response_json_object = JSONObject(finalresponse)
                Log.d("sdfbhsbfd", response_json_object.toString())
                val status = response_json_object.getString("status")
                if (status.equals("1")) {
                    val response = response_json_object.getJSONObject("response")
                    val driver_info = response.getJSONObject("driver_info")
                    var name = driver_info.getString("name")
                    var phone_number = driver_info.getString("phone_number")
                    var dial_code = driver_info.getString("dial_code")
                    mSessionManager.setDriverMobileNO(phone_number)
                    mSessionManager.setDriverCuntryCode(dial_code)

                }
            }
        }
   }

    override fun onResume() {
        super.onResume()
        if (mSessionManager.getdriver_profile_image().isEmpty()) {
            getData()
            getappversion()
        }
        wakeLock.acquire()
        sliderHandler.postDelayed(sliderRunnable, 5000)



        if (AppUtils.isNetworkAvailable(mContext)) {
            nointernetconnectionlay1.visibility=View.GONE

        }else{

            nointernetconnectionlay1.visibility=View.VISIBLE
            dashboardscrol.visibility=View.GONE
        }


//        if (mSessionManager.getmstage().equals("NOT_VERIFIED")){
//            verificationdialog()
//        }
        if (mSessionManager.getis_electric().equals("true")) {
            km_lay.visibility = View.VISIBLE
            kmebike1.setText(mSessionManager.getmileage() + "km")
            percentebike1.setText(mSessionManager.getpercentage() + "%")

        } else {
            km_lay.visibility = View.GONE
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
//            val  mSettingsIntent = Intent(Intent.ACTION_MAIN)
//                    .setAction(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
//            try {
//                startActivity(mSettingsIntent)
//                requestLocationPermission()
//
//            } catch (ex: java.lang.Exception) {
//                Log.w("ErrorLog", "Unable to launch app draw overlay settings $mSettingsIntent", ex)
//            }
//        } else {
//            //Device does not support app overlay
//        }

        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
        viewModel.defaultvalueloader(mContext)
        viewModel.getdriverimage(mContext)
    }

    override fun onDestroy() {
        super.onDestroy()
        clearllallthing()
        stoponlineservice()
        wakeLock.release()

    }

    fun defaultcar() {
        val height = resources.getDimension(R.dimen._50sdp)
        val width = resources.getDimension(R.dimen._50sdp)
        val bitmapdraw = ContextCompat.getDrawable(mContext, R.drawable.greenloc) as BitmapDrawable
        val b = bitmapdraw.bitmap
        driver_marker_resized = Bitmap.createScaledBitmap(b, width.toInt(), height.toInt(), false)
    }

    private fun requestLocationPermission() {
        val foreground = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) === PackageManager.PERMISSION_GRANTED
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
            REQUEST_CODE_PERMISSIONS
        )

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

    fun commonhidego(it: String) {
        runOnUiThread {
//            loader.visibility = View.GONE
        }
        if (it.equals("1")) {
            runOnUiThread {
//                driver_dashboard_toggle_button.isOn=true
//                km_lay.setBackgroundResource(R.drawable.buttoncurvedbackground)
                getboundary()

//                layout_ripplepulse3.visibility = View.GONE
////                layout_ripplepulse3.stopRippleAnimation()
//                offlineripple.visibility = View.GONE
//                profileeclick.visibility=View.GONE
//                layout_ripplepulse3.visibility = View.GONE
                if (!viewModel.isMyServiceRunning(mContext, OnlineJobService::class.java)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    }
                    startonlineservice()
                    startoptimizepermission()
                }
            }
        } else {
            runOnUiThread {
                km_lay.setBackgroundResource(R.drawable.buttoncurvedbackgroundblack)
//                driver_dashboard_toggle_button.isOn=false
//                layout_ripplepulse3.visibility = View.GONE
//                layout_ripplepulse3.startRippleAnimation()
//                offlineripple.visibility = View.GONE
                if (viewModel.isMyServiceRunning(mContext, OnlineJobService::class.java)) {
                    stoponlineservice()
                    animationstop()
                }
            }
        }
    }

    fun button() {

    }


    fun walletdialog() {
//        availabilityoff()
//        val view = getLayoutInflater().inflate(R.layout.waiting_for_driver_wallet_balance_bottom_sheet, null);
//        bottomSheetDialogs!!.setContentView(view)
//        bottomSheetDialogs!!.setCancelable(false)
//        val waiting_bs_ok_btn = view.findViewById(R.id.waiting_bs_ok_btn) as Button
//        waiting_bs_ok_btn.setOnClickListener {
//            val intent_otppage2 = Intent(mContext, WalletPage::class.java)
//            intent_otppage2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//            intent_otppage2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent_otppage2)
//        }
//        bottomSheetDialogs!!.show()
    }

    fun verificationdialog() {
//        availabilityoff()
//        val view = getLayoutInflater().inflate(R.layout.waiting_for_driver_verification_bottom_sheet, null);
//        bottomSheetDialogs!!.setContentView(view)
//        bottomSheetDialogs!!.setCancelable(false)
//
//        val waiting_bs_ok_btn = view.findViewById(R.id.waiting_bs_ok_btn) as Button
//
//
//        waiting_bs_ok_btn.setOnClickListener {
//            finish()
////            mSessionManager.setverificationtatus("0")
////            bottomSheetDialogs!!.dismiss()
////            mSessionManager.setverificationtatus("0")
//        }
//        bottomSheetDialogs!!.show()
    }


    fun exitappalert() {
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle(getString(R.string.confirm_cancel))
        builder.setMessage(getString(R.string.wanttoexit))
        builder.setPositiveButton(getString(R.string.yesia)) { dialog, which ->
            super.onBackPressed()

        }
        builder.setNegativeButton(getString(R.string.closepage)) { dialog, which ->
        }
        builder.show()
    }

    fun logoutalert() {
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle(getString(R.string.confirm_cancel))
        builder.setMessage(getString(R.string.wanttocancel1))
        builder.setPositiveButton(getString(R.string.yesia)) { dialog, which ->
            viewModel.logout(mContext)

        }
        builder.setNegativeButton(getString(R.string.closepage)) { dialog, which ->
        }
        builder.show()
    }

    @InternalCoroutinesApi
    fun successdialog(heading: String, content: String) {
        availabilityoff()
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
////        done.setOnClickListener {
////            selectedbikestype=mSessionManager.getbiketype()
////            if (selectedbikestype.equals("2")){
////                val intent2 = Intent(mContext, com.cabilyhandyforalldinedoo.chd.ui.registeration.documentpagetwo::class.java)
////                mContext.startActivity(intent2)
////            }else{
////                val intent2 = Intent(mContext, com.cabilyhandyforalldinedoo.chd.ui.registeration.documentpageone::class.java)
////                mContext.startActivity(intent2)
////            }
////
////
////        }
//        close.setOnClickListener {
//            bottomSheetDialog!!.dismiss()
//            finish()
//        }
//        bottomSheetDialog!!.show()
    }

    private fun navigationheadervalue(drivearray: ArrayList<DriverDetailDataModel>) {
        mSessionManager = SessionManager(applicationContext!!)
        navdrivername!!.setText(drivearray.get(0).drivername)
        navmobilevalue!!.setText(mSessionManager!!.getUserDetails()!![mSessionManager!!.mobilecode]!! + " " + mSessionManager!!.getUserDetails()!![mSessionManager!!.mobilenumber]!!)
        var ratee: Float = drivearray.get(0).driverrating!!.toFloat()
        navratingBar!!.rating = ratee
        var driverimagepath = drivearray[0].driverimage!!
        if (!driverimagepath.startsWith("http")) {
            driverimagepath =
                getString(R.string.amaozonurlprofileimage) + drivearray[0].driverimage!!
        }

    }

    override fun onBackPressed() {
        Log.d("cjdjdfs", "sjfjsdfff")
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            exitappalert()
        }
    }

    fun expnd(dialogInterface: DialogInterface) {
        var d: BottomSheetDialog = dialogInterface as BottomSheetDialog
        val bottomsheet = d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val bottomsheetbeh = BottomSheetBehavior.from(bottomsheet!!)
        bottomsheetbeh.state = BottomSheetBehavior.STATE_EXPANDED
        bottomsheetbeh.peekHeight = bottomsheet!!.height
    }

    fun driverprofillee(drivearray: ArrayList<DriverDetailDataModel>) {
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
//        val driverphoto = view.findViewById(R.id.driverphoto) as LottieAnimationView
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
////                                driverphoto.setImageBitmap(resource)
//                            }
//                        }
//
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
    }

    @InternalCoroutinesApi
    fun availabilityoff() {
//        repeatFun = repeatFun()

        if (AppUtils.isNetworkConnected(mContext)) {
//            if (loader!!.visibility == View.VISIBLE) {
//                commsnackbaralert(getString(R.string.loading))
//            } else {
//                iv_line.visibility = View.GONE
//                loader.visibility = View.VISIBLE
//                availabilystatus = "0"
//                viewModel.driverstatus(mContext, "0")
//                repeatFun.cancel()
//
//            }
        } else
            commsnackbaralert(getString(R.string.failedproblem))

    }

    fun availbiltyon() {
        if (AppUtils.isNetworkConnected(mContext)) {
//            if (loader!!.visibility == View.VISIBLE) {
//                commsnackbaralert(getString(R.string.loading))
//            } else {
//                loader.visibility = View.VISIBLE
//                availabilystatus = "1"
//                viewModel.driverstatus(mContext, "1")
//
//            }
        } else
            commsnackbaralert(getString(R.string.failedproblem))

    }

    fun clearllallthing() {
        if (googleApiClient != null) {
            googleApiClient?.isConnected?.let {
                if (it) googleApiClient?.disconnect()
            }
        }
        removelocationupdate()
        removeFrom()
        if (myLocationGoogleMap != null && googlemap != null)
            myLocationGoogleMap!!.removeFrom(googlemap)
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this)
//        layout_ripplepulse3.stopRippleAnimation()
        if (viewModel.isMyServiceRunning(mContext, OnlineJobService::class.java))
            stoponlineservice()
    }

    fun animationstart() {
        if (animR != null) {
            if (!animR!!.isRunning) {
                animR!!.start()
            }
        }
    }

    fun animationstop() {
        if (animR != null) {
            iv_line.visibility = View.GONE
            if (animR!!.isRunning)
                animR!!.stop()
        }
    }

    private fun connectToGoogleApiClient() {
        googleApiClient = mContext?.let {
            GoogleApiClient.Builder(it)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
        }
        locationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
            .setInterval(1000)
            .setFastestInterval(1000)
    }

    private fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            mContext!!,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    fun moveToMyLocation(googleMap: GoogleMap?, location: Location?) {
        if (googleMap != null && location != null) {
            val latLng = LatLng(location.latitude, location.longitude)
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17f)
            googleMap.animateCamera(cameraUpdate, 1000, null)
        }
    }

    fun addTo(googleMap: GoogleMap, driver_marker_resized: Bitmap, location: Location) {
        mSessionManager = SessionManager(mContext)
        handler.post(Runnable {
            mSessionManager.setOnlineStatsu(
                location.latitude,
                location.longitude,
                location.bearing,
                ""
            )
            val center = LatLng(location.latitude, location.longitude)
            val radius = location.accuracy / TileSystem.GroundResolution(
                location.latitude,
                googleMap.cameraPosition.zoom.toDouble()
            ).toFloat()
            if (accuracyCircle == null) {
                accuracyCircle = googleMap.addCircle(
                    CircleOptions()
                        .center(center)
                        .radius(radius.toDouble())
                        .fillColor(Color.parseColor(ACCURACY_COLOR))
                        .strokeColor(Color.parseColor(ACCURACY_COLOR_BORDERS))
                        .strokeWidth(accuracyStrokeWidth)
                )
            } else {
                accuracyCircle!!.setCenter(center)
                accuracyCircle!!.setRadius(radius.toDouble())
            }
            if (locationMarker == null) {
                locationMarker = googleMap.addMarker(
                    MarkerOptions()
                        .position(center)
                        .anchor(0.5f, 0.5f)
                        .icon(BitmapDescriptorFactory.fromBitmap(driver_marker_resized))
                )
            } else {
                locationMarker!!.setPosition(center)
            }
            if (location.bearing.toDouble() == 0.0) {
                if (bearingMarker != null) {
                    bearingMarker!!.remove()
                    bearingMarker = null
                }
            } else {
                if (bearingMarker == null) {
                    if (locationMarker != null) {
                        locationMarker!!.remove()
                        locationMarker = null
                    }
                    bearingMarker = googleMap.addMarker(
                        MarkerOptions()
                            .position(center)
                            .flat(true)
                            .anchor(0.5f, 0.5f)
                            .rotation(location.bearing)
                            .icon(BitmapDescriptorFactory.fromBitmap(driver_marker_resized))
                    )
                } else {
                    if (locationMarker != null) {
                        locationMarker!!.remove()
                        locationMarker = null
                    }
                    bearingMarker!!.setPosition(center)
                    bearingMarker!!.setRotation(location.bearing)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(center))
                }
            }
            if (!isMyLocationCentered) {
                isMyLocationCentered = true
                moveToMyLocation(googleMap, location)
            }
        })
    }

    fun removeFrom() {
        if (accuracyCircle != null) {
            accuracyCircle!!.remove()
            accuracyCircle = null
        }
        if (locationMarker != null) {
            locationMarker!!.remove()
            locationMarker = null
        }
        if (bearingMarker != null) {
            bearingMarker!!.remove()
            bearingMarker = null
        }
        isMyLocationCentered = false
    }

    fun removelocationupdate() {
        if (googleApiClient != null) {
            if (locationCallback != null)
                location.removeLocationUpdates(locationCallback)
        }
    }

    fun nightthemecolour() {
//        val sdk = android.os.Build.VERSION.SDK_INT;
//        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//            changestatus.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.greenclickable))
//            profileeclick.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.greenclickablefull))
//            menubackground.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.greenclickablefull))
//            ctmap.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.greenclickablefull))
//            offbut.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.greenclickable))
//        } else {
//            changestatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.greenclickable))
//            profileeclick.setBackground(ContextCompat.getDrawable(mContext, R.drawable.greenclickablefull))
//            menubackground.setBackground(ContextCompat.getDrawable(mContext, R.drawable.greenclickablefull))
//            ctmap.setBackground(ContextCompat.getDrawable(mContext, R.drawable.greenclickablefull))
//            offbut.setBackground(ContextCompat.getDrawable(mContext, R.drawable.greenclickable))
//        }
//        gotext.setTextColor(Color.parseColor("#ffffff"))
//        offlineclick3.setTextColor(Color.parseColor("#ffffff"))
    }

    fun morningthemecolour() {
//        val sdk = android.os.Build.VERSION.SDK_INT;
//        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//            changestatus.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.clickablebutton))
//            profileeclick.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.clickablebutton))
//            menubackground.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.clickablebutton))
//            ctmap.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.clickablebutton))
//            offbut.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.clickablebutton))
//        } else {
//            changestatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.clickablebutton))
//            profileeclick.setBackground(ContextCompat.getDrawable(mContext, R.drawable.clickablebutton))
//            menubackground.setBackground(ContextCompat.getDrawable(mContext, R.drawable.clickablebutton))
//            ctmap.setBackground(ContextCompat.getDrawable(mContext, R.drawable.clickablebutton))
//            offbut.setBackground(ContextCompat.getDrawable(mContext, R.drawable.clickablebutton))
//        }
//        gotext.setTextColor(Color.parseColor("#ffffff"))
//        offlineclick3.setTextColor(Color.parseColor("#ffffff"))
    }

    fun backtologin(mContext: Context) {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.deleteAll()
        realm.commitTransaction()
        val intent2 = Intent(mContext, splashpage::class.java)
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        mContext.startActivity(intent2)
    }

    fun movetotrippage() {
//        val intent2 = Intent(mContext, requestmianscreen::class.java)
//        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        mContext.startActivity(intent2)
    }

    fun moveToOtpRidePage() {
//        val intent2 = Intent(mContext, otpride::class.java)
//        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        mContext.startActivity(intent2)
    }

    fun commsnackbaralert(message: String) {
        val snack =
            Snackbar.make(coordinate_bottom_sheet_ride_book_now, message, Snackbar.LENGTH_LONG)
        var view: View = snack.getView()
        var params: FrameLayout.LayoutParams = FrameLayout.LayoutParams(view.getLayoutParams())
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params)
        snack.show()

    }

    fun startoptimizepermission() {
        val intent = Intent()
        val packageName = getPackageName();
        val pm = applicationContext.getSystemService(Context.POWER_SERVICE) as PowerManager
        if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                !pm.isIgnoringBatteryOptimizations(packageName)
            } else {
                TODO("VERSION.SDK_INT < M")
            }
        ) {
            intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + packageName));
            startActivity(intent);
        }
    }

    private fun startonlineservice() {
        val serviceClass = OnlineJobService::class.java
        val serviceIntent = Intent(applicationContext, serviceClass)
        if (!viewModel.isMyServiceRunning(mContext, OnlineJobService::class.java)) {
            startService(serviceIntent)
            bindService(serviceIntent, arrivalConnection, Context.BIND_AUTO_CREATE)
        } else
            bindService(serviceIntent, arrivalConnection, Context.BIND_AUTO_CREATE)
    }

    private fun stoponlineservice() {
        val serviceClass = OnlineJobService::class.java
        val serviceIntent = Intent(applicationContext, serviceClass)
        try {
            unbindService(arrivalConnection)
        } catch (e: IllegalArgumentException) {
        }
        if (viewModel.isMyServiceRunning(mContext, OnlineJobService::class.java)) {
            stopService(serviceIntent)
        }
    }

    override fun onItemSelected(position: Int) {
        if (position == POS_LOGOUT) {
            finish()
        } else if (position == POS_LOGOUT) {


        }
//        slidingRootNav!!.closeMenu()
    }

    private fun loadScreenTitles(): Array<String> {
        return resources.getStringArray(R.array.ld_activityScreenTitles)
    }





    @ColorInt
    private fun color(@ColorRes res: Int): Int {
        return ContextCompat.getColor(this, res)
    }

//    override fun onDragStart() {
//        var card=findViewById<View>(R.id.activity_main) as CardView
//        card.radius=50f
//    }
//
//    override fun onDragEnd(isMenuOpened: Boolean) {
//        if (!isMenuOpened) {
//            var card=findViewById<View>(R.id.activity_main) as CardView
//            card.radius=0f
//        }
//    }


}

typealias drawble = R.drawable


