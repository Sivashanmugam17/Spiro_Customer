//package com.cabilyhandyforalldinedoo.chd.ui.MainPage
//
//import android.Manifest
//import android.annotation.SuppressLint
//import android.app.Activity
//import android.app.ActivityManager
//import android.app.Dialog
//import android.content.*
//import android.content.pm.PackageManager
//import android.database.sqlite.SQLiteDatabase
//import android.graphics.*
//import android.graphics.drawable.BitmapDrawable
//import android.location.Geocoder
//import android.location.Location
//import android.location.LocationManager
//import android.os.*
//import android.text.Editable
//import android.text.TextWatcher
//import android.util.Base64
//import android.util.Log
//import android.view.Gravity
//import android.view.View
//import android.widget.*
//import androidx.appcompat.app.AlertDialog
//import androidx.core.content.ContextCompat
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import cabily.handyforall.dinedoo.R
//import co.opensi.kkiapay.uikit.Kkiapay
//import co.opensi.kkiapay.uikit.SdkConfig
//import com.cabilyhandyforalldinedoo.chd.Backgroundservices.OnlineJobService
//import com.cabilyhandyforalldinedoo.chd.Backgroundservices.UpdateForgroundService
////import com.cabilyhandyforalldinedoo.chd.Backgroundservices.UpdateForgroundService
//import com.cabilyhandyforalldinedoo.chd.Backgroundservices.commonapifetchservice
//import com.cabilyhandyforalldinedoo.chd.EventBusConnection.IntentServiceResult
//import com.cabilyhandyforalldinedoo.chd.SessionManagerPackage.SessionManager
//import com.cabilyhandyforalldinedoo.chd.ViewModelTrackingPage.EstimateFareApiResult
//import com.cabilyhandyforalldinedoo.chd.ViewModelTrackingPage.OtpRideRepostiatry
//import com.cabilyhandyforalldinedoo.chd.ViewModelTrackingPage.OtpRideViewModel
//import com.cabilyhandyforalldinedoo.chd.ViewModelWIthRepositaryMain.CurrentZoomModel
//import com.cabilyhandyforalldinedoo.chd.adaptersofchd.PaymenttypeCustomAdapter
//import com.cabilyhandyforalldinedoo.chd.commonutils.AppUtils
//import com.cabilyhandyforalldinedoo.chd.commonutils.BookNowDropIconGen
//import com.cabilyhandyforalldinedoo.chd.commonutils.BookNowPickupIconGen
//import com.cabilyhandyforalldinedoo.chd.commonutils.Constants
//import com.cabilyhandyforalldinedoo.chd.data.LanguageDb
//import com.cabilyhandyforalldinedoo.chd.data.onridelatandlong.onrideDbHelper
//import com.cabilyhandyforalldinedoo.chd.data.onridelatandlong.onrideDbHelper.TABLE_NAME_FOR_OTP
//import com.cabilyhandyforalldinedoo.chd.mylocation.*
//import com.cabilyhandyforalldinedoo.chd.ui.registeration.CountryCodeSelection
//import com.cabilyhandyforalldinedoo.chd.ui.registeration.LocaleAwareCompatActivity
//import com.cabilyhandyforalldinedoo.chd.ui.registeration.splashpage
//import com.cabilyhandyforalldinedoo.chd.ui.registeration.vehicletypeCustomAdapter
//import com.cabilyhandyforalldinedoo.chd.xmpp.RoosterConnection
//import com.google.android.gms.common.ConnectionResult
//import com.google.android.gms.common.api.GoogleApiClient
//import com.google.android.gms.location.LocationRequest
//import com.google.android.gms.location.LocationServices
//import com.google.android.gms.maps.*
//import com.google.android.gms.maps.model.*
//import com.google.android.material.bottomsheet.BottomSheetDialog
//import com.google.android.material.snackbar.Snackbar
//import com.google.maps.android.SphericalUtil
//import com.ncorti.slidetoact.SlideToActView
//import io.realm.Realm
//import kotlinx.android.synthetic.main.activity_support.*
//import kotlinx.android.synthetic.main.activity_wallet.*
//import kotlinx.android.synthetic.main.collectcashotpride.*
//import kotlinx.android.synthetic.main.custom_marker.*
//import kotlinx.android.synthetic.main.fairs_dialogue.*
//import kotlinx.android.synthetic.main.mainpage1design.*
//import kotlinx.android.synthetic.main.maipagedesign.*
//import kotlinx.android.synthetic.main.nointernetconnection.*
//import kotlinx.android.synthetic.main.otpride.*
//import kotlinx.android.synthetic.main.otpride.backbutton
//import kotlinx.android.synthetic.main.otpride.nointernetconnectionlay
//import kotlinx.android.synthetic.main.otpride.otp1
//import kotlinx.android.synthetic.main.otpride.otp2
//import kotlinx.android.synthetic.main.otpride.otp3
//import kotlinx.android.synthetic.main.otpride.otp4
//import kotlinx.android.synthetic.main.requestpagedfa.*
//import kotlinx.coroutines.*
//import org.greenrobot.eventbus.EventBus
//import org.greenrobot.eventbus.Subscribe
//import org.greenrobot.eventbus.ThreadMode
//import org.jetbrains.anko.doAsync
//import org.jetbrains.anko.uiThread
//import org.jivesoftware.smack.SmackException
//import org.jivesoftware.smack.XMPPException
//import org.json.JSONObject
//import pub.devrel.easypermissions.EasyPermissions
//import java.io.ByteArrayOutputStream
//import java.io.IOException
//import java.lang.Runnable
//import java.text.SimpleDateFormat
//import java.util.*
//import java.util.concurrent.TimeUnit
//import kotlin.collections.ArrayList
//
//
//class otpride : LocaleAwareCompatActivity(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, RouteListeners {
//    private lateinit var mContext: Activity
//    private lateinit var viewModel: OtpRideViewModel
////    lateinit var binding: OtprideBinding
//private val LOCATION_AND_CONTACTS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
//    private var RC_LOCATION_CONTACTS_PERM:Int = 124
//    private var isGPS:Boolean = false;
//
//    private lateinit var mMapFragment: SupportMapFragment
//    private var myLocationGoogleMap: MyLocationGoogleMap? = null
//    private var googlemap: GoogleMap? = null
//    var defaultflag:String = "228,TG"
//    lateinit var dropMarker: Marker
//    var map_camera_move_reason: Int = 0
//    private var rideModeIntent: String = ""
//
//    lateinit var pickUpMarker: Marker
//    private var googleApiClient: GoogleApiClient? = null
//    private var locationRequest: LocationRequest? = null
//    private var driver_marker_resized: Bitmap? = null
//    var timer: CountDownTimer? = null
//    var releaseresendclick = 1
//    var counttimecheck = 0
//    var dummymarker: BitmapDescriptor? = null
//    internal lateinit var mSessionManager: SessionManager
//    var pickupLatLng: LatLng? = null
//    var pickupLatLngotp: LatLng? = null
//    var pickupLatLngotpone: LatLng? = null
//    var drop_latlng: LatLng? = null
//    var drop_latlngotp: LatLng? = null
//    var  latLngA : LatLng? = null
//    var stroredistance = "0"
//    private var nearest_driver_arriving_time: String = "0"
//    var latLngList: ArrayList<LatLng> = ArrayList()
//    var latLngListotp: ArrayList<LatLng> = ArrayList()
//    var pick_lat_distance=""
//    var pick_long_distance=""
//    var pick_lat_otp = ""
//    var pick_log_otp = ""
//    var mtransactionId=""
//
//    var pick_lat_otp1 = ""
//    var pick_log_otp1 = ""
//
//    var dropAddress = ""
//    var dropAddressone = ""
//    var useridtosend = ""
//    private var  mdistance: Double = 0.0
//    private var dest_drop_lat: Double = 0.0
//    private var dest_drop_lng: Double = 0.0
//    private var dest_drop_lat1: String = ""
//    private var dest_drop_lng1: String =""
//    private var isMarkerDropRight = false
//    private var isMarkerDropLeft = false
//    private var isMarkerPickupRight = false
//    private var isMarkerPickupLeft = false
//    var currency_symbol = ""
//    var selectedCategoryEstAmount = "0.0"
//    var username = ""
//    var pickup_location = ""
//    var trip_amount = ""
//    var rideid = ""
//    var currency_code = ""
//    var otpTxt = ""
//    var currentType=""
//    var distancestring="0"
//    var pickpcurrenttime=""
//    var currenttime=""
//    var minmeter="0"
//    var countryname="TG"
//    var mHelper: onrideDbHelper? = null
//    var dataBase: SQLiteDatabase? = null
//    var collectcash : BottomSheetDialog ? =null
//    var meterDialog : Dialog ? =null
//    var keyDel=0
//    var isKeyDel=false
//    private lateinit var bookNowPickupDrop: BookNowDropIconGen
//    private lateinit var bookNowPickupDropIconGen: BookNowPickupIconGen
//    lateinit var mHelperslan: LanguageDb
//    var selectedtype:String = ""
//    var selectedstage:String = "0"
//    var paymentnumberlink:String = ""
//
//
//    var retry_location_fare_api: Int = 0
//    val max_retry_api_count: Int = 1
//    private var dest_distance: Double = 0.0
//
//    private var dest_time: Double = 0.0
//    var drawply: FindRoute? = null
//    var pickup_lat: String? = null
//    var pickup_lon: String? = null
//    var fullresponse: String = ""
//    lateinit var routeLatLngArray: ArrayList<LatLng>
//    private val COUNTRYCODEREQUEST = 113
//    private val mHandler = Handler()
//    private var mTimer: Timer? = null
//    val INTERVAL: Long = 10000
//    var ridecancelleddialog:BottomSheetDialog ?= null
//    var forlocation = "0"
//    lateinit var wakeLock: PowerManager.WakeLock
//    private lateinit var repeatFun :Job
//    var locationdistance = "0"
//    var buttonslide = 0
//    var isBound = false
//    var aService: OnlineJobService? = null
//    var mride_completed=""
//    private var mConnection: RoosterConnection? = null
//    private var mActive: Boolean = false
//    private var mThread: Thread? = null
//    private var mTHandler: Handler? = null
//    private lateinit var repeatFunpayment :Job
//    var mphone_numbers=""
//    var otpphone_number=""
//    var kkipay_mode=""
//    var payment_key=""
//
//
//
////    private val arrivalConnection = object : ServiceConnection {
////        override fun onServiceConnected(className: ComponentName, service: IBinder) {
////            val binder = service as OnlineJobService.MyLocalBinder
////            aService = binder.getService()
////            isBound = true
////        }
////
////        override fun onServiceDisconnected(name: ComponentName) {
////            isBound = false
////        }
////    }
//
//
//    override fun onPause() {
//        super.onPause()
//        if (EventBus.getDefault().isRegistered(this))
//            EventBus.getDefault().unregister(this)
//    }
//
//    @InternalCoroutinesApi
//    override fun onResume() {
//        super.onResume()
//        if (!EventBus.getDefault().isRegistered(this))
//            EventBus.getDefault().register(this)
//        wakeLock.acquire()
//        defaultcar()
//        callLocationFareApi()
//        locationAndContactsTask()
//
//        if (AppUtils.isNetworkAvailable(mContext)) {
//            nointernetconnectionlay.visibility=View.GONE
//            header_lay.visibility=View.VISIBLE
//
//        }else{
//            header_lay.visibility=View.GONE
//            endtrip_card.visibility = View.GONE
//            otp_card.visibility=View.GONE
//            nointernetconnectionlay.visibility=View.VISIBLE
//            endtrip_card.visibility=View.GONE
//
//        }
//
//        var mcurrent_lat=mSessionManager!!.getOnlineLatitiude()
//        var mcurrent_long=mSessionManager!!.getOnlineLongitude()
//
//        var mpickup_lat: Double = mcurrent_lat.toDouble()
//        var mpickup_lon: Double = mcurrent_long.toDouble()
//        pickupLatLngotpone = LatLng(mpickup_lat, mpickup_lon)
//
//
//
//        if (mSessionManager.getis_electric().equals("true")) {
//            linearLayout2s.visibility=View.VISIBLE
//        }else{
//            linearLayout2s.visibility=View.GONE
//
//        }
//        viewModel.getdataforride(mContext)
//
//        if(mSessionManager.ridehasSession())
//        {
//            callLocationFareApi()
//
//        }
//    }
//    override fun onDestroy()
//    {
//        super.onDestroy()
//        EventBus.getDefault().unregister(this);
//        if(timer != null)
//        {
//            (timer as CountDownTimer).cancel()
//        }
//        wakeLock.release()
//    }
//
//    @InternalCoroutinesApi
//    @SuppressLint("InvalidWakeLockTag")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.otpride)
//        mContext = this@otpride
//        myLocationGoogleMap = MyLocationGoogleMap(mContext)
//        mSessionManager = SessionManager(mContext)
//        getpaymentkey()
//        mHelper= onrideDbHelper(applicationContext)
//        mHelperslan= LanguageDb(applicationContext)
//        dataBase=mHelper!!.getWritableDatabase()
//        bookNowPickupDropIconGen = BookNowPickupIconGen(applicationContext, 1)
//        bookNowPickupDrop = BookNowDropIconGen(applicationContext, 2)
//        routeLatLngArray = ArrayList()
//        val powerManager: PowerManager = mContext.getSystemService(POWER_SERVICE) as PowerManager
//        wakeLock= powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Lock")
//        kkipay_mode=mSessionManager.getkkipay_mode()
//        payment_key=mSessionManager.getkkipay_key()
//
//        Log.d("checkmodekki20",payment_key+"---"+kkipay_mode)
//        if (kkipay_mode.equals("test")){
//            Kkiapay.init(applicationContext, payment_key, SdkConfig(themeColor = R.color.colorPrimary,
//                    imageResource = R.raw.armoiries,enableSandbox = true)
//            )
//        }else{
//            Kkiapay.init(applicationContext, payment_key, SdkConfig(themeColor = R.color.colorPrimary,
//                    imageResource = R.raw.armoiries,enableSandbox = false)
//            )
//        }
//        lgkeyset()
//        initViewModel()
//        initializeMap()
//        Kkiapay.get().setListener{ status, transactionId  ->
//            Toast.makeText(mContext, "Transaction: ${status.name} -> $transactionId", Toast.LENGTH_LONG).show()
//            mtransactionId= transactionId.toString()
//
//            if(status.toString().equals("SUCCESS")){
//                startServicePaymentsuccess()
//
//            }
//
//            // ecoutez la fin du paiement ( status contient les différents status possibles )
//        }
//
//
//        otmap.setOnClickListener {
//            zoommap(mContext)
//        }
//        otmaps.setOnClickListener {
//            zoommap(mContext)
//        }
//
//        dropaddress.setOnClickListener {
//            val intent2 = Intent(mContext, AutoCompleteSearchActivity::class.java)
//            mContext.startActivityForResult(intent2, 10)
//
//        }
//        /* nextlayout.setOnClickListener {
//             mins_away.text=dest_time.toString()+" minutes"
//             km_aways.text=dest_distance.toString()+" Km"
//         bootm_card.visibility= View.VISIBLE
//         card_categ_image.visibility= View.VISIBLE
//     }*/
//        defaultflagprocessor(mContext, defaultflag)
//        next.setOnClickListener {
//            showProgress(true)
//            var dest_ti= Math.round(dest_time * 100).toFloat() / 100
//            var dest_diste= Math.round(dest_distance * 100).toFloat() / 100
////            mins_away.text = String.format("%.2f", dest_ti) + " minutes"
////            km_aways.text = String.format("%.2f", dest_diste) + " Km"
//            mins_away.text =""+ dest_ti + " minutes"
//            km_aways.text =""+ dest_diste + " Km"
////            callLocationFareApi()
//        }
//
//
//        confim_book_m.setOnClickListener {
////            otp_card.visibility = View.VISIBLE
//            card_categ_image.visibility = View.GONE
//
//        }
//        backbutton.setOnClickListener {
//            val intent_otppage1 = Intent(mContext, mianscreenpage::class.java)
//            intent_otppage1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//            intent_otppage1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent_otppage1)
//        }
//
//        iconlinear.setOnClickListener {
//            countryclassintent()
//
//        }
//
//        flagimage.setOnClickListener {
//            countryclassintent()
//
//        }
//        numberlinear.setOnClickListener {
//            countryclassintent()
//
//        }
//        close_btn_1.setOnClickListener {
//            otp_card.visibility = View.GONE
//            pickup_drop_lay.visibility = View.GONE
//            endtrip_card.visibility = View.VISIBLE
//            otp_meter_lay.visibility = View.GONE
//            endcard_1.visibility = View.VISIBLE
//
//
//        }
//        meter_icon_lay.setOnClickListener {
//            otp_meter_lay.visibility = View.VISIBLE
//            endtrip_card.visibility = View.VISIBLE
//            endcard_1.visibility = View.GONE
//
//
//        }
//        connection_lay2.setOnClickListener {
//            recreate()
//        }
//
//
//
//
//
//
//
//
//
//
//        enternumber.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                if (s.toString().length > 7) {
//                    submit_number.visibility = View.VISIBLE
//                    card_categ_image.visibility = View.GONE
//
//                } else {
//                    submit_number.visibility = View.GONE
//                    card_categ_image.visibility = View.GONE
//
//                }
//            }
//
//        })
//
//        submit_number.setOnClickListener {
//            pick_lat_otp=mSessionManager!!.getOnlineLatitiude()
//            pick_log_otp=mSessionManager!!.getOnlineLongitude()
//            mSessionManager!!.setonlinelongitudeotp(pick_log_otp)
//            mSessionManager!!.setonlinelatitudeotp(pick_lat_otp)
//            var pickup_longs=mSessionManager!!.getonlinelongitudeotp()
//            var pickup_latit= mSessionManager!!.getonlinelatitudeotp()
//            pickup_location = ""+getAddressForLocation(this@otpride, pickup_latit.toDouble(), pickup_longs.toDouble())
//
//
////            Log.d("dsfsdfdfdgf",pickup_longs+" "+pickup_latit)
//            pickupLatLngotp = LatLng(pickup_latit.toDouble(), pickup_longs.toDouble())
//            Log.d("dfhjhdfhjdf", pickupLatLngotp.toString())
//
//            if(counttimecheck == 1){
//                startcountdown()
//            }
//            if(releaseresendclick == 1){
////                mphone_numbers=enternumber.text.toString()
//                mphone_numbers= mSessionManager.getDriverMobileNo()
//                otpphone_number=enternumber.text.toString()
//                Log.d("mchephones",mphone_numbers)
//                Log.d("mchephones202",otpphone_number)
//
//                if (mphone_numbers.equals(otpphone_number)){
//                    Log.d("mchephones203","22")
//                    commsnackbaralert("Driver Number Should Not Be Same")
//
//                }else{
//                    viewModel.callGetOtpApi(this@otpride, pickupLatLngotp!!, enternumber.text.toString(), countryname)
//                    releaseresendclick= 0
//                    counttimecheck = 1
//                    startcountdown()
//                    mSessionManager.setmride_completed("")
//                }
//
//
//
//            }
//        }
//
//
//
//        slide_to_begin_btn.onSlideUserFailedListener = object : SlideToActView.OnSlideUserFailedListener {
//            override fun onSlideFailed(view: SlideToActView, isOutside: Boolean) {
//                if (progress_time.visibility == View.VISIBLE)
//                    commsnackbaralert(getString(R.string.slideerror))
//
//                else {
//                    val otp = otp1.text.toString() + otp2.text.toString() + otp3.text.toString() + otp4.text.toString()
//                    if (enternumber.text.toString().equals("")){
//                        commsnackbaralert("Enter your Customer Number")
//
////                        commontoast("Enter your Customer Number")
//                    } else if (otp.isEmpty()){
//                        commsnackbaralert("Enter your OTP")
////                        commontoast("Enter your OTP")
//                    }else if (!isOtpValid()) {
//                        commsnackbaralert("OTP is Invalid.Try Again")
////                        commontoast("OTP is Invalid.Try Again")
//                    }else if(isOtpValid()) {
////                        stoponlineservice()
////                        setMarker()
//                        val calendar = Calendar.getInstance()
//                        val mdformat = SimpleDateFormat("HH:mm")
//                        pickpcurrenttime = mdformat.format(calendar.time)
//                        mSessionManager.setpickpcurrenttimeotp(pickpcurrenttime)
//                        Log.d("pickpcurrenttimess", pickpcurrenttime)
//                        showProgress(true)
//                        slide_to_begin_btn.visibility = View.GONE
//                        slide_tobegin_progress.visibility = View.VISIBLE
//                        card_otp_lay.visibility = View.GONE
//                        viewModel.callBeginOtpRide(this@otpride, enternumber.text.toString(), pickupLatLngotp!!, pickup_location)
//                        dataBase!!.execSQL("delete from " + TABLE_NAME_FOR_OTP);
//                        repeatFun = repeatFun()
//                        mSessionManager.setdist_one("0.0")
//                        var pick_addres_lat=mSessionManager!!.getOnlineLatitiude()
//                        var pick_addres_long=mSessionManager!!.getOnlineLongitude()
//                        mSessionManager.setmride_completed("")
//
//                        if(pick_addres_lat.isEmpty()){
//                            pick_addres_lat="0.0"
//                            pick_addres_long="0.0"
//                        }else{
//                            mSessionManager.setonlinelatitudepickadress(pick_addres_lat)
//                            mSessionManager.setonlinelongitudpickadress(pick_addres_long)
////                            pickup_location = ""+getAddressForLocation(this@otpride, pick_addres_lat.toDouble(), pick_addres_long.toDouble())
//
//                        }
////                        startUpdateForegroundService()
//
//
////                        viewModel.callBeginOtpRide(this@otpride, enternumber.text.toString(), pickup_location, pickupLatLng!!, dropAddress, drop_latlng!!,
////                                selectedCategoryEstAmount, dest_distance.toString(), dest_time.toString())
//                    }
//                }
//            }
//        }
//
//
//            slide_to_end_btn.onSlideUserFailedListener = object : SlideToActView.OnSlideUserFailedListener {
//                override fun onSlideFailed(view: SlideToActView, isOutside: Boolean) {
//                    if (progress_time.visibility == View.VISIBLE)
//                        commsnackbaralert(getString(R.string.slideerror))
//                    else
//                        tripupdate()
//
//                }
//            }
//
//
//        meter_card.setOnClickListener {
//            callMeterDialog()
//        }
//        /*
//         slide_to_begin_btn.setOnClickListener {
//             viewModel.callBeginOtpRide(this@otpride,enternumber.text.toString(),"pickup",pickupLatLng!!,dropAddress,drop_latlng!!,
//                     selectedCategoryEstAmount,dest_distance.toString(),dest_time.toString())
//         }*/
//
//        defaultcar()
//
//        textChangedListener()
//
//    }
//
//
//        @InternalCoroutinesApi
//        fun repeatFun(): Job {
//            val scope = CoroutineScope(Dispatchers.Default)
//            return scope.launch {
//                while(isActive) {
//                    calculatedistance()
//                    delay(10000)
//                }
//            }
//        }
//
//    @InternalCoroutinesApi
//    fun repeatFunpayment(): Job {
//        val scope = CoroutineScope(Dispatchers.Default)
//        return scope.launch {
//            while(isActive) {
//                delay(5000)
//            }
//        }
//    }
//
////    private fun textchangedListener() {
////        otp1.addTextChangedListener(object : TextWatcher {
////            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
////            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
////                if (s.length > 0) {
////                    otp2.requestFocus()
////                }
////            }
////
////            override fun afterTextChanged(s: Editable) {}
////        })
////        otp2.addTextChangedListener(object : TextWatcher {
////            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
////            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
////                if (s.length > 0) {
////                    otp3.requestFocus()
////                } else {
////                    otp1.requestFocus()
////                    otp1.setSelection(otp1.getText().length)
////                }
////            }
////
////            override fun afterTextChanged(s: Editable) {}
////        })
////        otp3.addTextChangedListener(object : TextWatcher {
////            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
////            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
////                Log.e("value", s.toString())
////                if (s.length > 0) {
////                    otp4.requestFocus()
////
////                } else {
////                    otp2.requestFocus()
////                    otp2.setSelection(otp2.getText().length)
////                }
////            }
////
////            override fun afterTextChanged(s: Editable) {}
////        })
////        otp4.addTextChangedListener(object : TextWatcher {
////            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
////            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
////                if (s.length > 0) {
////                } else {
////                    otp3.requestFocus()
////                    otp3.setSelection(otp3.getText().length)
////                }
////            }
////            override fun afterTextChanged(s: Editable) {}
////        })
////    }
//
//    fun startServicePaymentsuccess() {
//        val serviceClass = commonapifetchservice::class.java
//        val intent = Intent(applicationContext, serviceClass)
//        intent.putExtra("api_name", getString(R.string.payment_success))
//        intent.putExtra("rideid", rideid)
//        intent.putExtra("transactionId", mtransactionId)
//        startService(intent)
//    }
//    fun startServicePaymenttripinit(phonenumber:String) {
//        val serviceClass = commonapifetchservice::class.java
//        val intent = Intent(applicationContext, serviceClass)
//        intent.putExtra("api_name", getString(R.string.Paymenttripinit))
//        intent.putExtra("rideid", rideid)
//        intent.putExtra("phone_number", phonenumber)
//        startService(intent)
//    }
//
//    fun startServicePaymentlist() {
//        val serviceClass = commonapifetchservice::class.java
//        val intent = Intent(applicationContext, serviceClass)
//        intent.putExtra("api_name", getString(R.string.payment_list))
//        startService(intent)
//    }
//    fun getpaymentkey() {
//        val serviceClass = commonapifetchservice::class.java
//        val intent = Intent(mContext, serviceClass)
//        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.payment_key))
//        mContext.startService(intent)
//    }
//
//
//    private fun showCurvedPolyline(p1: LatLng, p2: LatLng, k: Double) {
//        //Calculate distance and heading between two points
//        val d: Double = SphericalUtil.computeDistanceBetween(p1, p2)
//        val h: Double = SphericalUtil.computeHeading(p1, p2)
//
//        //Midpoint position
//        val p: LatLng = SphericalUtil.computeOffset(p1, d * 0.5, h)
//
//        //Apply some mathematics to calculate position of the circle center
//        val x = (1 - k * k) * d * 0.5 / (2 * k)
//        val r = (1 + k * k) * d * 0.5 / (2 * k)
//        val c: LatLng = SphericalUtil.computeOffset(p, x, h + 90.0)
//
//        //Polyline options
//        val options = PolylineOptions()
//
//
//
//
//        //Calculate heading between circle center and two points
//        val h1: Double = SphericalUtil.computeHeading(c, p1)
//        val h2: Double = SphericalUtil.computeHeading(c, p2)
//
//        //Calculate positions of points on circle border and add them to polyline options
//        val numpoints = 100
//        var pattern= Any()
//        val step = (h2 - h1) / numpoints
//        for (i in 0 until numpoints) {
//            val pi: LatLng = SphericalUtil.computeOffset(c, r, h1 + i * step)
//            options.add(pi)
//            options.pattern(listOf(Dash(10f), Gap(20f))) //Skip if you want solid line
//            options.width(10f).color(android.R.color.holo_red_dark)
//        }
//
//        //Draw polyline
//        googlemap!!.addPolyline(options.width(10f).color(R.color.colorPrimary).geodesic(true).pattern(options.pattern))
//
//
////        if (origin_status.equals("Pickup")){
////            googlemap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(origin_lat,origin_lon), 10.0f))
////        }else{
////            googlemap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(provider_origin_lat,provider_origin_lon), 10.0f))
////
////        }
//
//
//
//
//
//    }
//
//
//
//    fun startcountdown()
//    {
//        if(timer != null)
//        {
//            (timer as CountDownTimer).cancel()
//        }
//        timer = object: CountDownTimer(30000, 1000) {
//            override fun onTick(millisUntilFinished: Long)
//            {
//                val hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished), TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))
//                submit_number.setText(hms)
//                counttimecheck = 0
//
//            }
//            override fun onFinish()
//            {
//                releaseresendclick = 1
//                counttimecheck = 1
//
//                submit_number.setText(getString(R.string.resendcode))
//            }
//        }
//        (timer as CountDownTimer).start()
//    }
//
//
////    private fun updatelocationtoservices()
////    {
////        mSessionManager = SessionManager(applicationContext)
////        val intent = Intent(applicationContext, UpdateForgroundService::class.java)
////        intent.putExtra("distance", mSessionManager!!.getdistance_km_otp())
////        applicationContext.startService(intent)
////    }
//
//
//    fun startcountdowns() {
//        googlemap!!.clear()
//        latLngList.clear()
//        dest_drop_lat1 =mSessionManager.getotpridedroplatitiude(dest_drop_lat1)
//        dest_drop_lng1 = mSessionManager.getotpridedroplongitude(dest_drop_lng)
//        var lat: String = mSessionManager.getOnlineLatitiude()
//        var long: String = mSessionManager.getOnlineLongitude()
//        var pickup_lat: Double = lat.toDouble()
//        var pickup_lon: Double = long.toDouble()
//        var dest_drop_lat2: Double = dest_drop_lat1.toDouble()
//        var dest_drop_lng2: Double = dest_drop_lng1.toDouble()
//        pickupLatLng = LatLng(pickup_lat, pickup_lon)
//        drop_latlng = LatLng(dest_drop_lat2, dest_drop_lng2)
//        latLngList.add(pickupLatLng!!)
//        latLngList.add(drop_latlng!!)
//
////        viewModel.estimatedFareCalculation(mContext, mSessionManager.getLocationFareApiResponse(), pickupLatLng!!, dest_time, dest_distance)
//        Log.d("farevalue", pickupLatLng.toString() + " " + dest_time + " " + dest_distance)
//
//        drawply = FindRoute(this@otpride)
//        drawply?.clearpolyline()
//        googlemap?.clear()
//        drawply?.setUpPolyLine(googlemap!!, this@otpride, pickupLatLng, drop_latlng, latLngList, true)
//
//        setMarker()
//        markerAdjust()
//        val builder = LatLngBounds.Builder()
//        builder.include(pickupLatLng)
//        builder.include(drop_latlng)
//        var bounds = builder.build()
//        googlemap!!.setOnMapLoadedCallback { googlemap!!.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 300)) }
//
//
//        if(timer != null)
//        {
//            (timer as CountDownTimer).cancel()
//        }
//        timer = object: CountDownTimer(30000, 1000) {
//            override fun onTick(millisUntilFinished: Long)
//            {
//
////                drawply = FindRoute(this@otpride)
////
////                drawply?.setUpPolyLine(googlemap!!, this@otpride, pickupLatLng, drop_latlng, latLngList, true)
//            }
//            override fun onFinish()
//            {
//
//            }
//        }
//        (timer as CountDownTimer).start()
//    }
//
//
//    fun lgkeyset(){
//        OTP_Ride.setText(getkey("otp_ride"))
//        pickup_addresss.setText(getkey("current_location"))
//        enter_the_customer_phone_number.setText(getkey("enter_the_customer_phone_number"))
//        slide_to_begin_btn.text= getkey("slide_to_begin").toString()
//        slide_to_end_btn.text= getkey("slide_to_end").toString()
//        meter.setText(getkey("meter"))
//        enter_otp.setText(getkey("enter_otp"))
//    }
//
//    private fun getkey(key: String): String? {
//        return mHelperslan.getvalueforkey(key)
//    }
//
//    fun countryclassintent()
//    {
//        val intent_book_now = Intent(mContext, CountryCodeSelection::class.java)
//        startActivityForResult(intent_book_now, COUNTRYCODEREQUEST)
//    }
//    @InternalCoroutinesApi
//    private fun initViewModel() {
//        loaderotp.visibility = View.GONE
//        var mcurrent_lat=mSessionManager!!.getOnlineLatitiude()
//        var mcurrent_long=mSessionManager!!.getOnlineLongitude()
//
//        var mpickup_lat: Double = mcurrent_lat.toDouble()
//        var mpickup_lon: Double = mcurrent_long.toDouble()
//        pickupLatLngotpone = LatLng(mpickup_lat, mpickup_lon)
//        otp_card.visibility = View.VISIBLE
//        backbutton.visibility = View.VISIBLE
//
//
//
//
//        viewModel = ViewModelProviders.of(this).get(OtpRideViewModel::class.java)
//        viewModel.mSessionManager = mSessionManager
//        if (mSessionManager.getis_electric().equals("true")) {
//            confim_book_m.text="Confirm E-Bike"
//        }else{
//            confim_book_m.text="Confirm Bike"
//
//        }
//
//
//
//        viewModel.countryselectionobserver().observe(this, Observer {
//            if (it == 1)
//                countryclassintent()
//        })
//
//        viewModel.estamountobserver().observe(this, Observer {
//          Log.d("chhddhedd",it)
//        })
//
//        viewModel.getestimatefareobserver().observe(this, Observer {
//        //    selectedCategoryEstAmount = it.estimated_price_array?.get(0)!!
//            Log.d("dhdfff", selectedCategoryEstAmount)
//           // estimate_fare_amount.text = currency_symbol + " " + selectedCategoryEstAmount
//           // moveToConfirmScreen()
//        })
//        viewModel.getlocationfareapiresultobserver().observe(this, Observer {
//            currency_symbol = it.currency_symbol!!
////            viewModel.estimatedFareCalculation(mContext, mSessionManager.getLocationFareApiResponse(), pickupLatLng!!, dest_time, dest_distance)
//            Log.d("farevalue1545", pickupLatLng.toString() + " " + dest_time + " " + dest_distance)
//
//        })
//        viewModel.oncashrecivedobsercver().observe(this, Observer {
//            Log.d("dfd22fdf", it.toString())
//            if (it == 1) {
//                if (collectcash != null) {
//                    if (collectcash!!.isShowing)
//                        collectcash!!.dismiss()
//                }
//                viewModel.getsplitrepsonse(mContext)
//                mSessionManager.clearridedetails()
//                val intent_otppage = Intent(mContext, mianscreenpage::class.java)
//                intent_otppage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                intent_otppage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                startActivity(intent_otppage)
//            } else
//                commontoast(getString(R.string.failedproblem))
//        })
//        handleData()
//    }
//
//    private fun initializeMap() {
//        try {
//            mMapFragment = supportFragmentManager
//                    .findFragmentById(R.id.otppagemap) as SupportMapFragment
//            mMapFragment.getMapAsync(this)
////            loaderotp.visibility = View.GONE
//
//        } catch (e: Exception) {
////            loaderotp.visibility = View.GONE
//            e.printStackTrace()
//        }
//    }
//
//    fun zoommap(mContext: Context) {
//        var mSessionManager: SessionManager
//        mSessionManager = SessionManager(mContext)
//        var lat: String = mSessionManager.getOnlineLatitiude()
//        var long: String = mSessionManager.getOnlineLongitude()
//
//        if (!lat.equals("0")) {
//            var zoomdetails = java.util.ArrayList<CurrentZoomModel>()
//            zoomdetails.add(CurrentZoomModel(lat, long))
//        }
//        googlemap?.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat.toDouble(), long.toDouble()), 15f), 1000, null)
//
//
//    }
//
//    override fun onMapReady(p0: GoogleMap?) {
//        googlemap = p0
//
//        connectToGoogleApiClient()
//
//        val locationmaode = mSessionManager.getLocationMode().toInt()
//
//
//
//        if (locationmaode == 1) {
//            googlemap!!.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.night_map_style))
//            googleApiClient?.connect()
//        } else {
//
//            googlemap!!.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
//            myLocationGoogleMap!!.addTo(googlemap, driver_marker_resized, dummymarker, locationmaode)
//
//        }
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                p0!!.isMyLocationEnabled = false
//            }
//        } else {
//            p0!!.isMyLocationEnabled = false
//        }
//        p0!!.uiSettings.isRotateGesturesEnabled = false
//        p0.uiSettings.isMapToolbarEnabled = false
//
//        googlemap!!.setOnCameraMoveStartedListener { reason: Int ->
//            doAsync {
//                //Execute all the lon running tasks here
//                map_camera_move_reason = reason
//                uiThread {
//                    //Update the UI thread here
//                    when (map_camera_move_reason) {
//                        GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE -> {
////                            markerAdjust()
//                        }
//                        GoogleMap.OnCameraMoveStartedListener
//                                .REASON_API_ANIMATION -> {
//                        }
//                        GoogleMap.OnCameraMoveStartedListener
//                                .REASON_DEVELOPER_ANIMATION -> {
//                        }
//                    }
//                }
//            }
//        }
//
//    }
//    private fun setMarker()
//    {
////        mSessionManager!!.getOnlineLatitiude()
////        mSessionManager!!.getOnlineLongitude()
////        pickupLatLng = LatLng(13.0826802, 81.0128651)
//
//        val bookNowPickup = BookNowPickupIconGen(applicationContext, 1)
//        pickUpMarker = googlemap!!.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(bookNowPickup.makeIcon(pickup_location, 1))).anchor(0.5F, 0.5F).position(pickupLatLng!!).title(getString(R.string.book_now_google_marker_pickup_label)))
//        googlemap?.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.Builder().target(pickupLatLng!!).build()))
//        googlemap?.animateCamera(CameraUpdateFactory.zoomTo(15f));
//        val bookNowDropIconGen = BookNowDropIconGen(applicationContext, 2)
////        if (rideModeIntent.equals(""))
////            dropMarker = googlemap!!.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(bookNowDropIconGen.makeIcon(pickup_location, 2))).anchor(0.5F, 0.5F).position(drop_latlng!!).title(getString(R.string.book_now_google_marker_drop_label)))
////        for (i in 0 until waypoints_latlng_array.size)
////        {
////            setMarkerGlide(waypoints_latlng_array.get(i), R.drawable.ic_new_yellow_dot_30)
////        }
//    }
//
//    private fun connectToGoogleApiClient() {
//        googleApiClient = mContext.let {
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
//
//
//
//
//    private fun textChangedListener() {
//        otp1.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                if (s.length > 0) {
//                    otp2.requestFocus()
//                    if(s.length > 1){
//                        otp1.setText(s[0].toString())
//                        otp2.setText(s[1].toString())
//                        otp2.setSelection(otp2.text!!.length)
//                    }
//                }
//            }
//
//            override fun afterTextChanged(s: Editable) {}
//        })
//        otp2.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                if (s.length > 0) {
//                    otp3.requestFocus()
//                    if(s.length > 1){
//                        otp2.setText(s[0].toString())
//                        otp3.setText(s[1].toString())
//                        otp3.setSelection(otp3.text!!.length)
//                    }
//                } else {
//                    otp1.requestFocus()
//                    otp1.setSelection(otp1.text!!.length)
//                }
//            }
//
//            override fun afterTextChanged(s: Editable) {}
//        })
//        otp3.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                Log.e("value", s.toString())
//                if (s.length > 0) {
//                    otp4.requestFocus()
//                    if(s.length > 1){
//                        otp3.setText(s[0].toString())
//                        otp4.setText(s[1].toString())
//                        otp4.setSelection(otp4.text!!.length)
//                    }
//                }
//                else {
//                    otp2.requestFocus()
//                    otp2.setSelection(otp2.text!!.length)
//                }
//            }
//
//            override fun afterTextChanged(s: Editable) {}
//        })
//        otp4.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                if (s.length > 0) {
//                }else {
//                    otp3.requestFocus()
//                    otp3.setSelection(otp3.text!!.length)
//                }
//            }
//
//            override fun afterTextChanged(s: Editable) {}
//        })
//    }
//
//    fun defaultcar() {
//        val height = resources.getDimension(R.dimen._35sdp)
//        val width = resources.getDimension(R.dimen._15sdp)
//        val bitmapdraw = ContextCompat.getDrawable(mContext, R.drawable.bikelivetwo) as BitmapDrawable
//        val b = bitmapdraw.bitmap
//        driver_marker_resized = Bitmap.createScaledBitmap(b, width.toInt(), height.toInt(), false)
//    }
//
//    private fun hasLocationAndContactsPermissions(): Boolean {
//        return EasyPermissions.hasPermissions(this, *LOCATION_AND_CONTACTS)
//    }
//
//    fun locationAndContactsTask() {
//        if (hasLocationAndContactsPermissions()){
//            checklocationonedindevice()
//            Log.d("sgsgw44", "hhdhdgfvc")
//
//            // Ask for both permissions
//            EasyPermissions.requestPermissions(this, "", RC_LOCATION_CONTACTS_PERM, *LOCATION_AND_CONTACTS)
//
//        }else{
//            Log.d("fdr3455445", "hhdhdgfvc")
//
//
//
//            checklocationonedindevice()
//
//        }
//
//
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int,
//                                            permissions: Array<String>,
//                                            grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        if (requestCode == 124)
//        {
//            for (grantResult in grantResults) {
//                if (grantResult == PackageManager.PERMISSION_GRANTED)
//                {
//                    locationAndContactsTask()
//                }
//            }
//        }
//    }
//
//
//
//
//
//
//    private fun createMarker(type: String, address: String, pickLatLng: LatLng, dropLatLng: LatLng) {
//        val markerLayout = layoutInflater.inflate(R.layout.custom_marker, null)
//        val markerImage = markerLayout.findViewById(R.id.marker_image) as ImageView
//        val name = markerLayout.findViewById(R.id.name_text) as TextView
//        val addresss = markerLayout.findViewById(R.id.address_text) as TextView
//        val img = markerLayout.findViewById(R.id.imgview) as ImageView
//
//
//        if (type.equals("user")) {
//
//            markerImage.setImageResource(R.drawable.otpcurrenticon)
//
//
////                Picasso.get().load(mData.userDetails!!.userAvatar).into(object : com.squareup.picasso.Target {
////                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
////                        // loaded bitmap is here (bitmap)
////                        println("---------->>> $bitmap")
////                        img.setImageBitmap(bitmap)
////                    }
////
////                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
////                        println("-------->>> error drawableP: $placeHolderDrawable")
////                    }
////
////                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
////                        println("-------->>> error drawableP: $errorDrawable")
////                    }
////
////                })
//
//
////            Picasso.with(this).load(mData.TrackRestaurantDetails!!.restaurantAvatar).into(img)
//            name.text="PICK UP AT"
//            addresss.text = address
//            img.setBackgroundResource(R.drawable.map_left_curve);
//            markerLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
//            markerLayout.layout(0, 0, markerLayout.measuredWidth, markerLayout.measuredHeight)
//            val bitmap = Bitmap.createBitmap(markerLayout.measuredWidth, markerLayout.measuredHeight, Bitmap.Config.ARGB_8888)
//            val canvas = Canvas(bitmap)
//            markerLayout.draw(canvas)
//            currentType = "user"
//            googlemap!!.addMarker(MarkerOptions()
//                    .position(pickLatLng)
//                    .anchor(0.5f, 0.5f)
//                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap)))
//
//        } else {
//
//            markerImage.setImageResource(R.drawable.dropicon)
//
//
////                Picasso.get().load(mData.TrackRestaurantDetails!!.restaurantAvatar).into(object : com.squareup.picasso.Target {
////                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
////                        // loaded bitmap is here (bitmap)
////                        println("---------->>> $bitmap")
////                        img.setImageBitmap(bitmap)
////                    }
////
////                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
////                        println("-------->>> error drawableP: $placeHolderDrawable")
////                    }
////
////                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
////                        println("-------->>> error drawableP: $errorDrawable")
////                    }
////
////                })
//
//            /* Picasso.with(myActivity).setLoggingEnabled(true)
//             Picasso.with(myActivity).load(url).into(imgView, object : Callback() {
//                 fun onSuccess() {
//                     Log.e("tag", "onSuccess")
//                 }
//
//                 fun onError() {
//                     Log.e("tag", "OnError")
//                 }
//             })*/
//            name.text="Drop AT"
//            addresss.text = address
//            img.setBackgroundResource(R.drawable.map_left_curve_drop);
//            markerLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
//            markerLayout.layout(0, 0, markerLayout.measuredWidth, markerLayout.measuredHeight)
//            val bitmap = Bitmap.createBitmap(markerLayout.measuredWidth, markerLayout.measuredHeight, Bitmap.Config.ARGB_8888)
//            val canvas = Canvas(bitmap)
//            markerLayout.draw(canvas)
//            currentType = "rest"
//            googlemap!!.addMarker(MarkerOptions()
//                    .position(dropLatLng)
//                    .anchor(0.5f, 0.5f)
//                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap)))
//        }
//
//    }
//
//
//
//
//
//    override fun onConnected(p0: Bundle?) {
//
//    }
//
//    override fun onConnectionSuspended(p0: Int) {
//    }
//
//    override fun onConnectionFailed(p0: ConnectionResult) {
//    }
//    fun defaultflagprocessorpay(mContext: Context, defaultflagcode: String)
//    {
//        val codeNameArray = defaultflagcode.split(",")
//        val stringBuilder = StringBuilder()
//        stringBuilder.append(getCountryZipCode(codeNameArray[1].trim()))
//        val codes = codeNameArray[0].trim()
//        val imageName = codeNameArray[1].trim()
//        val imageBuilder = StringBuilder()
//        imageBuilder.append("flags/flag_").append(imageName.toLowerCase()).append(".png")
//        val inputStream = mContext.assets.open(imageBuilder.toString())
//        val bitmap = BitmapFactory.decodeStream(inputStream)
//        val baos = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
//        val b = baos.toByteArray()
//        val flagbase64string = Base64.encodeToString(b, Base64.DEFAULT)
//        mSessionManager = SessionManager(mContext)
//        mSessionManager!!.setFlagDetails(flagbase64string, codes, imageName)
//        flagimage_pay.setImageBitmap(bitmap)
//        countryname=imageName
//        Log.d("cdjfjf", imageName)
////        bitmapimage.value=StringToBitMap(flagbase64string)
//        code_pay.setText("+" + codes)
//    }
//
//    fun defaultflagprocessor(mContext: Context, defaultflagcode: String)
//    {
//        val codeNameArray = defaultflagcode.split(",")
//        val stringBuilder = StringBuilder()
//        stringBuilder.append(getCountryZipCode(codeNameArray[1].trim()))
//        val codes = codeNameArray[0].trim()
//        val imageName = codeNameArray[1].trim()
//        val imageBuilder = StringBuilder()
//        imageBuilder.append("flags/flag_").append(imageName.toLowerCase()).append(".png")
//        val inputStream = mContext.assets.open(imageBuilder.toString())
//        val bitmap = BitmapFactory.decodeStream(inputStream)
//        val baos = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
//        val b = baos.toByteArray()
//        val flagbase64string = Base64.encodeToString(b, Base64.DEFAULT)
//        mSessionManager = SessionManager(mContext)
//        mSessionManager!!.setFlagDetails(flagbase64string, codes, imageName)
//        flagimage.setImageBitmap(bitmap)
//        countryname=imageName
//        Log.d("cdjfjf", imageName)
////        bitmapimage.value=StringToBitMap(flagbase64string)
//        code.setText("+" + codes)
//    }
//    private fun getCountryZipCode(ssid: String): String
//    {
//        val loc = Locale("", ssid)
//        return loc.displayCountry.trim { it <= ' ' }
//    }
//
//
//    fun checklocationonedindevice()
//    {
//        try
//        {
//            val lm = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager;
//            var gps_enabled:Boolean = false;
//            var network_enabled:Boolean = false;
//            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
//            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
//            if(!gps_enabled && !network_enabled)
//            {
//                GpsUtils(this).turnGPSOn(GpsUtils.onGpsListener() {
//                    @Override
//                    fun gpsStatus(isGPSEnable: Boolean) {
//                        Log.d("vojjs", "sjjswwww")
//                        isGPS = isGPSEnable;
//                    }
//                })
//            }
//            else
//            {
//                Log.d("hfdhhdfd", "sdfhhshf")
//            }
//        }
//        catch (e: Exception)
//        {
//        }
//    }
//
//
//    @SuppressLint("MissingPermission")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//
//        if(requestCode==113){
//            if (resultCode == Activity.RESULT_FIRST_USER)
//            {
//                defaultflag = data?.getStringExtra(Constants.INTENT_OBJECT).toString()
//                Log.d("de3rew5te", defaultflag)
//                if (selectedstage.equals("1")){
//                    defaultflagprocessorpay(mContext, defaultflag)
//
//                }else{
//                    defaultflagprocessor(mContext, defaultflag)
//
//                }
//
//            }
//        }else if (requestCode == AppConstants.GPS_REQUEST){
//            if (resultCode == Activity.RESULT_OK)
//            {
//                if (requestCode == AppConstants.GPS_REQUEST)
//                {
//                    isGPS = true; // flag maintain before get location
//                    gpslocationlay.visibility=View.GONE
//                    Log.d("dfhjehrere22", "fdggfgh")
//                }
//                else
//                {
//
//                    Toast.makeText(applicationContext, "failed", Toast.LENGTH_LONG).show()
//                }
//            }
//        } else {
//            Kkiapay.get().handleActivityResult(requestCode, resultCode, data)
//
//        }
//
//
//
////        if (resultCode == Activity.RESULT_FIRST_USER) {
////            googlemap?.clear()
////            if (requestCode.equals(10)) {
////                val location_name = data!!.getStringExtra("location_name")
////                Log.e("location_name", location_name)
////                dropAddress = location_name
////                dropaddress.text = location_name
////                dropAddressone=dropAddress
////                Log.e("dropAddress_one", dropAddressone)
////
////
////                if (!location_name.equals("null")) {
////                    Log.e("Place name In Resume", location_name)
////                    val base_url = "https://maps.googleapis.com/maps/api/"
////                    val retrofit = Retrofit.Builder().baseUrl(base_url)
////                            .addConverterFactory(GsonConverterFactory.create())
////                            .build()
////                    val getPlacesApi = retrofit.create(RetrofitInterface::class.java)
////                    val listCall: Call<ResponseBody>? = getPlacesApi.getLatLng(location_name)
////                    listCall?.enqueue(object : Callback<ResponseBody> {
////                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
////
////                            val responseNew = JSONObject(response.body()!!.string())
////                            Log.e("dfgj223dsj", responseNew.toString())
////
////                            val selectedLocationNEw = responseNew.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location")
////                            var newlat = selectedLocationNEw.getString("lat")
////                            var newlng = selectedLocationNEw.getString("lng")
////                            dest_drop_lat = newlat!!.toDouble()
////                            dest_drop_lng = newlng!!.toDouble()
////                            mSessionManager.setotpridedroplatitiude(dest_drop_lat.toString())
////                            mSessionManager.setotpridedroplongitude(dest_drop_lng.toString())
////
////
////                            var mSessionManager: SessionManager
////                            mSessionManager = SessionManager(mContext)
////                            var lat: String = mSessionManager.getOnlineLatitiude()
////                            var long: String = mSessionManager.getOnlineLongitude()
////                            var pickup_lat: Double = lat.toDouble()
////                            var pickup_lon: Double = long.toDouble()
////                            latLngList.clear()
////                            pickupLatLng = LatLng(pickup_lat, pickup_lon)
////                            drop_latlng = LatLng(dest_drop_lat, dest_drop_lng)
////                            latLngList.add(pickupLatLng!!)
////                            latLngList.add(drop_latlng!!)
//////                            pickup_location = getAddressForLocation(this@otpride, pickup_lat, pickup_lon)
////                            Log.d("pick_loa", pickup_location)
////                            googlemap?.clear()
////                            drawply?.clearpolyline()
////
////                            drawply = FindRoute(this@otpride)
////                            drawply?.setUpPolyLine(googlemap!!, this@otpride, pickupLatLng, drop_latlng, latLngList, true)
//////                            googlemap?.addMarker(MarkerOptions().position(drop_latlng!!).icon(BitmapDescriptorFactory.fromResource(R.drawable.dropicon)))!!
//////                            createMarker("user",pickup_location,pickupLatLng!!,drop_latlng!!)
//////                            createMarker("",dropAddress,pickupLatLng!!,drop_latlng!!)
////                            println("cete65c36w35534d" + pickupLatLng + "---" + drop_latlng + "------" + latLngList)
////
////                            setMarker()
////
////                            markerAdjust()
//////                            googlemap!!.addMarker(MarkerOptions()
//////                                    .position(RestLoc)
//////                                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap)))
////                            val builder = LatLngBounds.Builder()
////                            builder.include(pickupLatLng)
////                            builder.include(drop_latlng)
////                            var bounds = builder.build()
////                            googlemap!!.setOnMapLoadedCallback { googlemap!!.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 300)) }
////                        }
////
////                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
////                            Log.e("Error..........", t.toString())
////                        }
////                    })
////                }
////
////            }
////        }
//    }
//
//
//
//
//    override fun routeDrawnPickToDrop(time: Double?, dist: Double?) {
//        try {
//
//            dest_distance = dist!!
//            dest_time = time!!
//            nextlayout.visibility = View.GONE
//            next.visibility = View.GONE
//            time_to_reach.text = String.format("%.2f", dest_time)
//
//
//        }catch (ex: java.lang.Exception){
////            toast("No routs found")
//        }
//    }
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun dothis(estimateFareApiResult: EstimateFareApiResult) {
//
//      Log.e("estimateFareApiResult",""+estimateFareApiResult)
//
//        selectedCategoryEstAmount= estimateFareApiResult.estimated_price_array!![0]
//    }
//
//
//    @InternalCoroutinesApi
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun locationFareApiResult(intentServiceResult: IntentServiceResult) {
//        var finalresponse: String = intentServiceResult.resultValue
//        var apiName: String = intentServiceResult.apiName
//        var driverLatLng: LatLng? = null
//        Log.d("cdkfjdfdf", finalresponse)
//        Log.d("vdgfft", apiName)
//        if(finalresponse.equals("406")){
//            Log.d("chekesdd","dsfdfdffgf")
//            logoutalert()
//
//        }
//
//
//        //Getting Location of Driver
////        if (apiName.equals(getString(R.string.gettingridecurrentlocation))) {
////            var bothlatandlong: String = intentServiceResult.resultValue
////            val latlongvalue = bothlatandlong.split(",").toTypedArray()
////            var ctlat:Double = latlongvalue[0].toDouble()
////            var ctlong:Double = latlongvalue[1].toDouble()
////            driverLatLng = LatLng(ctlat, ctlong)
//////            if(routeLatLngArray.size != 0 && foregroundPolyline != null && turnoffrerouting == 1)
//////            {
//////                var  latLng =  LatLng(ctlat, ctlong)
//////                try
//////                {
//////                    viewModel.checkdriverisonpathornot(mContext,foregroundPolyline!!,reroutingsuccessorfailed,latLng)
//////                }
//////                catch (io:java.lang.Exception)
//////                {
//////                }
//////            }
//////            if(sucesofdropmarker == 0)
//////            {
//////                if(pickupLatLng != null  && googlemap != null)
//////                    dropmarker(pickupLatLng!!)
//////
//////
//////            }
//////
//////            if(pickupLatLng != null && boundfixed == 0)
//////            {
//////                boundfixed = 1
//////                fixboundsbasedonpickupanddrop(pickupLatLng!!,driverLatLng!!)
//////            }
//////            if(pickupLatLng != null && routeapihitted == 0 && googlemap != null)
//////            {
//////                routeapihitted = 1
//////                startedtrackingyoucandrawroutehere(pickupLatLng!!)
//////            }
////            if(pickupLatLng != null)
////                viewModel?.etacalculation(mContext,driverLatLng!!, drop_latlng!!)
////        }
//
//
//        if (apiName.equals("canceltri")) {
//            //Slider Update
//            var rasponse: String = intentServiceResult.resultValue
//            if(rasponse.equals("failed"))
//                commsnackbaralert(getString(R.string.failedproblem))
//            else
//            {
//                movetomainpage()
//            }
//        }
//        if (apiName.equals(getString(R.string.api_location_fare))) {
//            if (finalresponse == "failed") {
//                if (retry_location_fare_api <= max_retry_api_count) {
//                    retry_location_fare_api++
//                    callLocationFareApi()
//                }
//            } else {
//                fullresponse = finalresponse
//                viewModel.locationFareApiParser(mContext, fullresponse, pickupLatLngotpone!!, "normal", "0", "Yes")
//            }
//        } else if (apiName.equals(getString(R.string.api_user_get_otp))) {
//            showProgress(false)
//            if (finalresponse != "failed") {
//                var finalJSON = JSONObject(finalresponse)
//                var finalResponseJson = finalJSON.getJSONObject("response")
//                var otp = finalResponseJson.getString("otp")
//                otpTxt=otp
//                otp1.setText(otp.get(0).toString())
//                otp2.setText(otp.get(1).toString())
//                otp3.setText(otp.get(2).toString())
//                otp4.setText(otp.get(3).toString())
//            }
//        } else if (apiName.equals(getString(R.string.api_otp_ride_begin))) {
//            showProgress(false)
//            if (finalresponse != "failed") {
//                var finalJSON = JSONObject(finalresponse)
//                var finalResponseJson = finalJSON.getJSONObject("response")
//                Log.d("checkingotp", finalResponseJson.toString())
//
//
//
//
//                if (mTimer != null) run {
//                    mTimer!!.cancel()
//                    mTimer = null
//                }
//                else
//                {
//                    mTimer = Timer() // recreate new timer
//                    mTimer!!.scheduleAtFixedRate(TimeDisplayTimerTask(), 0, INTERVAL)
//
//                }
////                moveToEndTrip()
//                mSessionManager.setis_otp_ride(true)
//                mSessionManager.setDutyrideid(finalResponseJson.getJSONObject("data").getString("duty_id"))
//                mSessionManager.onridedetails(finalresponse, "1", "0")
//                viewModel.getdataforride(mContext)
//            }
//        }
//
//        //Get Ride Details
//        else if (apiName.equals(getString(R.string.ridedetails))) {
//
//            var rasponse: String = intentServiceResult.resultValue
//            if (rasponse.equals("0"))
//                viewModel.cleartripdetails2(mContext)
//            else
//                viewModel.getsplitrepsonse(mContext)
//
//        }
//
//        else  if (apiName.equals(getString(R.string.tripupdate)))
//        {
//            var rasponse: String = intentServiceResult.resultValue
//            if(rasponse.equals("failed"))
//                commsnackbaralert(getString(R.string.failedproblem))
//            else
//            {
//                mSessionManager.setdist_one("0.0")
//                viewModel.splittripupdate(mContext, rasponse)
//            }
//        }
//        //Cash Recived Splitation
//        else  if (apiName.equals(getString(R.string.cashreceived)))
//        {
//            var rasponse: String = intentServiceResult.resultValue
//            if(rasponse.equals("failed"))
//                commsnackbaralert(getString(R.string.failedproblem))
//            else
//                viewModel.splitbasedoncashresponse(mContext, rasponse)
//        } else if (apiName.equals(getString(R.string.logoutcallapi))) {
//            backtologin(mContext)
//        }else if (apiName.equals(getString(R.string.payment_success))) {
//            if (finalresponse == "failed") {
//
//            } else {
//                Log.d("checkingkkipaysatsua",finalresponse)
//                PaymentsuccessApiParser(finalresponse)
//
//            }
//        }else if (apiName.equals(getString(R.string.Paymenttripinit))) {
//            if (finalresponse == "failed") {
//
//            } else {
//                Log.d("htomoney",finalresponse)
//                PaymenttriApiParser(finalresponse)
//            }
//        }else if (apiName.equals(getString(R.string.payment_list))) {
//            if (finalresponse == "failed") {
//
//            } else {
//                Log.d("htomoney",finalresponse)
//                PaymentlistApiParser(finalresponse)
//            }
//        }else if (apiName.equals(getString(R.string.payment_key))) {
//            if (finalresponse == "failed") {
//
//            } else {
//                Log.d("htomoney",finalresponse)
//                PaymentkeyApiParser(finalresponse)
//            }
//        }
//    }
//
//    private fun PaymentkeyApiParser(finalresponse: String) {
//        println("-----------finalresponse-----"+finalresponse)
//        val response_json_object = JSONObject(finalresponse)
//        try
//        {
//            val status = response_json_object.getString("status")
//            if (status.equals("1")) {
//                val response = response_json_object.getJSONObject("response")
//                val payment_settings =response.getJSONObject("payment_settings")
//                val kkiapay =payment_settings.getJSONObject("kkiapay")
//                val paymentapikey=kkiapay.getString("apikey")
//                val mode=kkiapay.getString("mode")
//
//                mSessionManager.setkkipay_key(paymentapikey)
//                mSessionManager.setkkipay_mode(mode)
//
//                Log.d("mmpaymentapikey",paymentapikey)
//
//
//            }
//        }catch (e: java.lang.Exception){
//
//            e.printStackTrace()
//        }
//    }
//
//
//    @InternalCoroutinesApi
//    private fun PaymenttriApiParser(finalresponse: String) {
//        println("-----------finalresponsesss-----" + finalresponse)
//        val response_json_object = JSONObject(finalresponse)
//        try {
//            val status = response_json_object.getString("status")
//            if (status.equals("1")) {
//                val response =response_json_object.getJSONObject("response")
//                ratingpage_lay.visibility=View.GONE
//
//                val intent_otppage2 = Intent(mContext, Paymentprocess::class.java)
//                intent_otppage2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                intent_otppage2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                intent_otppage2.putExtra("rideid",rideid)
//                startActivity(intent_otppage2)
//
//            }
//        } catch (e: java.lang.Exception) {
//
//            e.printStackTrace()
//        }
//    }
//
//
//    private fun PaymentlistApiParser(finalresponse: String) {
//        println("-----------finalresponsesss-----" + finalresponse)
//        val response_json_object = JSONObject(finalresponse)
//        try {
//            val status = response_json_object.getString("status")
//            if (status.equals("1")) {
//                val response_object = response_json_object.getJSONObject("response")
//                val payment_method_array = response_object.getJSONArray("payment_method")
//                for (i in 0 until payment_method_array.length()) {
//                    val payment_method_type = payment_method_array.getJSONObject(i).getString("type")
//                    val payment_method_code = payment_method_array.getJSONObject(i).getString("code")
//                    val payment_method_is_default = payment_method_array.getJSONObject(i).getString("is_default")
//                    Log.d("checkingarray",payment_method_type)
//                }
//
//            }
//        } catch (e: java.lang.Exception) {
//
//            e.printStackTrace()
//        }
//    }
//
//
//
//    private fun PaymentsuccestmoneyApiParser(finalresponse: String) {
//           println("-----------finalresponsesss-----" + finalresponse)
//           val response_json_object = JSONObject(finalresponse)
//           try {
//               val status = response_json_object.getString("status")
//               if (status.equals("1")) {
//                   val response =response_json_object.getJSONObject("response")
//                   val is_paid=response.getBoolean("is_paid")
//                   if (is_paid==true){
//                       mSessionManager.clearridedetails()
//                       mSessionManager.setmride_completed("")
//                       mSessionManager.setlocationdistance("0")
//                       stop()
//
//                       val intent_otppage2 = Intent(mContext, mianscreenpage::class.java)
//                       intent_otppage2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                       intent_otppage2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                       startActivity(intent_otppage2)
//                   }
//                   Log.d("dfd33hdhf", is_paid.toString())
//
//
//               }
//           } catch (e: java.lang.Exception) {
//
//               e.printStackTrace()
//           }
//       }
//
//    @InternalCoroutinesApi
//    private fun PaymentsuccessApiParser(finalresponse: String) {
//        println("-----------finalresponsesss-----"+finalresponse)
//        val response_json_object = JSONObject(finalresponse)
//        try
//        {
//            val status = response_json_object.getString("status")
//            if (status.equals("1")) {
//                mSessionManager.clearridedetails()
//                mSessionManager.setmride_completed("")
//                mSessionManager.setlocationdistance("0")
//                repeatFunpayment = repeatFunpayment()
//                repeatFunpayment.cancel()
//
//                stop()
//                val intent_otppage2 = Intent(mContext, mianscreenpage::class.java)
//                intent_otppage2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                intent_otppage2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                startActivity(intent_otppage2)
//            }else if (status.equals("0")){
//
//                commontoast("")
//            }
//        }catch (e: java.lang.Exception){
//
//            e.printStackTrace()
//        }
//    }
//
//
//    fun movetomainpage()
//    {
//
//        if(ridecancelleddialog !=null)
//        {
//            if(!ridecancelleddialog!!.isShowing)
//                commoncanceldialog()
//        }
//        else
//            commoncanceldialog()
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
//            ridecancelleddialog =  BottomSheetDialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
//            val view = getLayoutInflater().inflate(R.layout.cancelpopup, null);
//            ridecancelleddialog!!.setContentView(view)
//            ridecancelleddialog!!.setCancelable(false)
//            val rideids = view.findViewById(R.id.rideid) as TextView
//            rideids.text = getString(R.string.ride_id)+" "+rideid
//            val closetext = view.findViewById(R.id.closetext) as ImageView
//            val cloepage = view.findViewById(R.id.cloepage) as LinearLayout
//            closetext.setOnClickListener {
//                ridecancelleddialog!!.dismiss()
//                mSessionManager.clearridedetails()
//
//                val intent_otppage = Intent(mContext, mianscreenpage::class.java)
//                intent_otppage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                intent_otppage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                startActivity(intent_otppage)
//
//            }
//            cloepage.setOnClickListener {
//                ridecancelleddialog!!.dismiss()
//                mSessionManager.clearridedetails()
//                val intent_otppage = Intent(mContext, mianscreenpage::class.java)
//                intent_otppage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                intent_otppage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                startActivity(intent_otppage)
//            }
//            ridecancelleddialog!!.show()
//        }
//    }
//
//
//    fun logoutalert() {
//        val builder = AlertDialog.Builder(mContext)
//        builder.setTitle("Session Timeout")
//        builder.setPositiveButton(getString(R.string.yesia)) { dialog, which ->
//            EventBus.getDefault().post(IntentServiceResult(Activity.RESULT_OK, "1", applicationContext.getString(R.string.logoutcallapione)))
//        }
//        builder.show()
//    }
//
//
//    fun backtologin(mContext: Context) {
//        val realm: Realm = Realm.getDefaultInstance()
//        realm.beginTransaction()
//        realm.deleteAll()
//        realm.commitTransaction()
//        val intent2 = Intent(mContext, splashpage::class.java)
//        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        mContext.startActivity(intent2)
//    }
//
//
//    fun callLocationFareApi() {
//        val serviceClass = commonapifetchservice::class.java
//        val intent = Intent(applicationContext, serviceClass)
//        intent.putExtra("api_name", getString(R.string.api_location_fare))
//        intent.putExtra("pickup_lat", mSessionManager!!.getOnlineLatitiude())
//        intent.putExtra("pickup_lng", mSessionManager!!.getOnlineLongitude())
//        startService(intent)
////        if (!isMyServiceRunning(mContext, commonapifetchservice::class.java)) {
////            if (AppUtils.isNetworkConnected(mContext)) {
////                val serviceClass = commonapifetchservice::class.java
////                val intent = Intent(applicationContext, serviceClass)
////                intent.putExtra("api_name", getString(R.string.api_location_fare))
////                intent.putExtra("pickup_lat", mSessionManager!!.getOnlineLatitiude())
////                intent.putExtra("pickup_lng", mSessionManager!!.getOnlineLongitude())
////                startService(intent)
////            }
////        }
//    }
//
//    private fun markerAdjust()
//    {
//        var screenPositionPickup: Point
//        var addressViewSizeLeft: Int = 0
//        var screenPositionDrop: Point
//        screenPositionPickup = googlemap!!.projection.toScreenLocation(pickupLatLng)
//        var mapView = mMapFragment.getView()
//        try {
//            val maxX = mapView!!.getMeasuredWidth().toFloat()
//            val x = 0.0f
//            if (maxX == 720.0f) {
//                addressViewSizeLeft = 300;
//            } else if (maxX == 1080.0f) {
//                addressViewSizeLeft = 450;
//            } else if (maxX == 1440.0f) {
//                addressViewSizeLeft = 600;
//            } else if (maxX >= 1080.0f) {
//                addressViewSizeLeft = 500;
//            } else {
//                addressViewSizeLeft = 320;
//            }
//            if (screenPositionPickup.x - addressViewSizeLeft <= x && !isMarkerPickupRight) {
//                isMarkerPickupRight = true
//                isMarkerPickupLeft = false
//                adjustMarkerPickup(bookNowPickupDropIconGen, "PICKUP", pickupLatLng!!, 1, false, nearest_driver_arriving_time)
//            } else if (screenPositionPickup.x + addressViewSizeLeft >= maxX && !isMarkerPickupLeft) {
//                isMarkerPickupRight = false
//                isMarkerPickupLeft = true
//                adjustMarkerPickup(bookNowPickupDropIconGen, "PICKUP", pickupLatLng!!, 1, true, nearest_driver_arriving_time)
//            } else if (screenPositionPickup.x - addressViewSizeLeft > x && screenPositionPickup.x + addressViewSizeLeft < maxX && !isMarkerPickupRight) {
//                isMarkerPickupRight = true
//                isMarkerPickupLeft = false
//                adjustMarkerPickup(bookNowPickupDropIconGen, "PICKUP", pickupLatLng!!, 1, false, nearest_driver_arriving_time)
//            }
//            adjustMarkerDrop(bookNowPickupDrop, "DROP", drop_latlng!!, 2, true)
//
//            screenPositionDrop = googlemap!!.projection.toScreenLocation(drop_latlng)
//            if (screenPositionDrop.x - addressViewSizeLeft <= x && !isMarkerDropRight) {
//                isMarkerDropRight = true
//                isMarkerDropLeft = false
//                adjustMarkerDrop(bookNowPickupDrop, "DROP", drop_latlng!!, 2, false)
//            } else if (screenPositionDrop.x + addressViewSizeLeft >= maxX && !isMarkerDropLeft) {
//                isMarkerDropRight = false
//                isMarkerDropLeft = true
//                adjustMarkerDrop(bookNowPickupDrop, "DROP", drop_latlng!!, 2, true)
//            } else if (screenPositionDrop.x - addressViewSizeLeft > x && screenPositionDrop.x + addressViewSizeLeft < maxX && !isMarkerDropRight) {
//                isMarkerDropRight = true
//                isMarkerDropLeft = false
//                adjustMarkerDrop(bookNowPickupDrop, "DROP", drop_latlng!!, 2, false)
//            }
//
//        } catch (e: Exception) {
//        }
//    }
//
//    private fun adjustMarkerDrop(bookNowPickupDropIconGen: BookNowDropIconGen, address_text: String, drop_latlng: LatLng, flag: Int, isMove: Boolean) {
//        if (flag == 2) {
//            if (dropMarker != null) {
//                dropMarker.remove()
//            }
//            dropMarker =googlemap!!.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(bookNowPickupDropIconGen.adjustAddressview(dropAddressone, 2, isMove))).anchor(0.5F, 0.5F).position(drop_latlng).title(getString(R.string.book_now_google_marker_drop_label)))
//            Log.d("ae54rt43t", dropAddressone)
//        }
//    }
//
//    private fun adjustMarkerPickup(bookNowPickupDropIconGen: BookNowPickupIconGen, address_text: String, pickup_latlng: LatLng, flag: Int, isMove: Boolean, arriving_time: String)
//    {
//        if (flag == 1)
//        {
//            if (pickUpMarker != null)
//            {
//                pickUpMarker.remove()
//            }
//            pickUpMarker = googlemap!!.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(bookNowPickupDropIconGen.adjustAddressview(pickup_location, 1, isMove, arriving_time))).anchor(0.5F, 0.5F).position(pickup_latlng).title(getString(R.string.book_now_google_marker_pickup_label)))
//        }
//    }
//
//
//    private fun tripupdate()
//    {
//        if(pickupLatLngotp != null)
//        {
////            progress_time.visibility = View.VISIBLE
////            viewModel.tripupdatestatus(mContext, pickupLatLngotp!!.latitude.toString(), pickupLatLngotp!!.longitude.toString())
//            slide_to_end_btn.visibility = View.GONE
//            slide_toend_progress.visibility = View.VISIBLE
//            endcard_1.visibility = View.GONE
//            viewModel.tripupdatestatusotp(mContext, pickupLatLngotp!!.latitude.toString(), pickupLatLngotp!!.longitude.toString(), useridtosend)
//            Log.d("userid_check", useridtosend)
//
//
//        }
//        else
//            commsnackbaralert(getString(R.string.problemwitlocation))
//    }
//
//    fun isMyServiceRunning(context: Activity?, serviceClass: Class<*>): Boolean {
//        var result = false
//        val manager = context?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
//            if (serviceClass.name == service.service.className) {
//                result = true
//                break
//            } else {
//                result = false
//            }
//        }
//        return result
//    }
//
//    fun moveToConfirmScreen() {
//        showProgress(false)
//        bootm_card.visibility = View.GONE
//        card_categ_image.visibility = View.GONE
//        otmaps.visibility = View.VISIBLE
//    }
//
//    fun moveToEndTrip(){
//
//        backbutton.visibility = View.GONE
//        otp_card.visibility = View.GONE
//        top_lay.visibility = View.GONE
//        pickup_drop_lay.visibility = View.GONE
//        endtrip_card.visibility = View.VISIBLE
//        card_categ_image.visibility = View.GONE
//        otmaps.visibility = View.VISIBLE
//
//
//    }
//    fun setEndTripData(){
//        drop_address_txt.text=dropAddress
//    }
//    @InternalCoroutinesApi
//    fun moveToCompletePage(){
//        pickup_drop_lay.visibility = View.GONE
//        endtrip_card.visibility = View.GONE
//        bootm_card.visibility = View.GONE
//        card_categ_image.visibility = View.GONE
//        top_lay.visibility = View.GONE
//        collectbelowamountfromcustomer(trip_amount, currency_code)
//    }
//
//    fun moveToCompletePagecash(){
//        pickup_drop_lay.visibility = View.GONE
//        endtrip_card.visibility = View.GONE
//        bootm_card.visibility = View.GONE
//        card_categ_image.visibility = View.GONE
//        top_lay.visibility = View.GONE
//        collectbelowamountfromcustomercash(trip_amount, currency_code)
//    }
//
//
//    fun showProgress(show: Boolean) {
//        if (show) {
//            progress_time.visibility = View.GONE
//        } else {
//            progress_time.visibility = View.GONE
//        }
//    }
//
//    fun commsnackbaralert(message: String) {
//        val snack = Snackbar.make(main_lay, message, Snackbar.LENGTH_LONG)
//        var view: View = snack.view
//        var params: FrameLayout.LayoutParams = FrameLayout.LayoutParams(view.layoutParams)
//        params.gravity = Gravity.TOP
//        view.layoutParams = params
//        snack.show()
//    }
//
//    @InternalCoroutinesApi
//    private fun handleData() {
//        viewModel.driveretailsviewmodellObserver().observe(this, Observer {
//            var userphoto: String = it.get(0).user_image.toString()
//            var rating: String = it.get(0).user_review.toString()
//            rideid = it.get(0).ride_id.toString()
//            username = it.get(0).user_name.toString()
////            pickup_location = it.get(0).pickup_location.toString()
////            dropAddress = it.get(0).drop_location.toString()
//            useridtosend = it.get(0).user_id.toString()
//            trip_amount = it.get(0).tripamount.toString()
//            currency_code = it.get(0).currency_code.toString()
//            pickup_lat = it.get(0).pickup_lat
//            pickup_lon = it.get(0).pickup_lon
//            pickupLatLng = LatLng(pickup_lat!!.toDouble(), pickup_lon!!.toDouble())
////            pickupLatLng = LatLng(pickup_lat!!.toDouble(), pickup_lon!!.toDouble())
//
//            var paymethodd: String = it.get(0).payment_method.toString()
//            var ride_status: String = it.get(0).ride_status.toString()
//            Log.d("aewr54", ride_status)
//            Log.d("xchjdhfjrer", useridtosend)
//
//            Log.d("fsbndhfbesr", pickupLatLng.toString())
//
//
////            if (mSessionManager.ridehasSession() == true) {
////                startcountdowns()
////            }
//            viewModel.esttimeobserver().observe(this, Observer {
//                runOnUiThread {
////                    time_to_reach.text = it
//                }
//
//            })
//            viewModel.estDistanceeobservers().observe(this, Observer {
//
//            })
//
//            if (ride_status.equals("Onride")) {
//                Log.d("xcgcbgcbh", ride_status)
////                var pick_lat_adrress=mSessionManager.getonlinelatitudepickadress()
////                var pick_long_adrress=mSessionManager.getonlinelongitudpickadress()
////                pickupLatLng = LatLng(pick_lat_adrress.toDouble(), pick_long_adrress.toDouble())
//
////                val bookNowPickup = BookNowPickupIconGen(applicationContext, 1)
////
////                pickUpMarker = googlemap!!.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(bookNowPickup.makeIcon(pickup_location, 1))).anchor(0.5F, 0.5F).position(pickupLatLng!!).title(getString(R.string.book_now_google_marker_pickup_label)))
//
////                startUpdateForegroundService()
////                stoponlineservice()
//                endcard_1.visibility = View.GONE
//                otp_meter_lay.visibility = View.VISIBLE
//                endtrip_card.visibility = View.GONE
//                startServicePaymentlist()
//
//                callLocationFareApi()
//                repeatFun = repeatFun()
//
//
//                if (mTimer != null) run {
//                    mTimer!!.cancel()
//                    mTimer = null
//                }
//                else {
//                    mTimer = Timer() // recreate new timer
//                    mTimer!!.scheduleAtFixedRate(TimeDisplayTimerTask(), 0, INTERVAL)
//
//                }
//
////                setEndTripData()
////                kmebikes.setText(mSessionManager.getmileage() + "km")
////                percentebikes.setText(mSessionManager.getpercentage() + "%")
////                time_to_reach.text = String.format("%.2f", dest_time)
////                startedtrackingyoucandrawroutehere(drop_latlng!!)
////                Log.d("checkcvdrop", drop_latlng.toString())
//
//                moveToEndTrip()
////                var driverLatLng = LatLng(mSessionManager!!.getOnlineLatitiude().toDouble(), mSessionManager!!.getOnlineLongitude().toDouble())
////                viewModel?.etacalculation(mContext, driverLatLng!!, drop_latlng!!)
//
//            } else if (ride_status.equals("Finished")) {
//                otp_meter_lay.visibility = View.GONE
//                otp_card.visibility = View.GONE
//                selectedstage="1"
//                startServicePaymentlist()
//                moveToCompletePage()
//                stop()
//
////                stopUpdateForegroundService()
////                stoponlineservice()
//
//                mSessionManager.setmstroredistance("0")
//                repeatFun.cancel()
//
////                stopUpdateForegroundService()
//
////                setDataOnCompletePage()
//                //mHelper.execSQL("delete from " + onrideDbHelper.TABLE_NAME_FOR_OTP);
//
//                dataBase!!.execSQL("delete from " + TABLE_NAME_FOR_OTP);
//
//            } else if (ride_status.equals("8")) {
//                mSessionManager.clearridedetails()
//
//                val intent_otppage = Intent(mContext, mianscreenpage::class.java)
//                intent_otppage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                intent_otppage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                startActivity(intent_otppage)
//            } else if (ride_status.equals("Completed")) {
//                otp_meter_lay.visibility = View.GONE
////                stopUpdateForegroundService()
////                stoponlineservice()
////                stopUpdateForegroundService()
//                startServicePaymentlist()
//
//                repeatFun.cancel()
//                dataBase!!.execSQL("delete from " + TABLE_NAME_FOR_OTP);
//                mSessionManager.setmstroredistance("0")
//                moveToCompletePagecash()
//
//
//            }
//
//
//        })
//
//    }
//
//    /*fun setDataOnCompletePage(){
//        usernames.text=username
//        first_address_.text=pickup_location
//        end_address_.text=dropAddress
//        symbol.setText(currency_code)
//        amount1.setText(trip_amount)
//
//        if (OtpRideRepostiatry.arrayList.size>0) {
//            TripFare.setText(OtpRideRepostiatry.arrayList[0])
//            tripamount.setText("Rs "+OtpRideRepostiatry.arrayListvalue[0])
//            CouponApplied.setText(OtpRideRepostiatry.arrayList[1])
//            CouponAppliedamout.setText("Rs "+OtpRideRepostiatry.arrayListvalue[1])
//            CommisionAmountitle.setText(OtpRideRepostiatry.arrayList[2])
//            CommisionAmount.setText("Rs "+OtpRideRepostiatry.arrayListvalue[2])
//            TotalEarningstitle.setText(OtpRideRepostiatry.arrayList[3])
//            TotalEarningsamount.setText("Rs "+OtpRideRepostiatry.arrayListvalue[3])
//        }
//        if(trip_amount.contains("."))
//        {
//            amount1.setText(trip_amount.split(".")[0])
//            amount2.setText("."+trip_amount.split(".")[1])
//        }
//    }*/
//
//    fun startedtrackingyoucandrawroutehere(pickupLatLng: LatLng)
//    {
//        if(googlemap != null && pickupLatLng != null)
//            viewModel.startroutecall(mContext, pickupLatLng.latitude, pickupLatLng.longitude)
//    }
//
//    lateinit var reportSpinnerOtp : Spinner
//    lateinit var Request_Payment_kkipay : Button
//    lateinit var Request_Payment_tmoeny : Button
//    lateinit var received : TextView
//    lateinit var code_pay : TextView
//
//    lateinit var tmoney_enter_lay : LinearLayout
//    lateinit var flagimage_pay : ImageView
//    lateinit var iconlinear_pay : LinearLayout
//    lateinit var numberlinear_pay : LinearLayout
//    lateinit var enternumber_pay : TextView
//    lateinit var ratingpage_lay : LinearLayout
//    lateinit var tmoney_progress : ProgressBar
//
//
//
//    @InternalCoroutinesApi
//    fun collectbelowamountfromcustomer(payableamount: String, symbosl: String)
//    {
//        collectcash =  BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme)
//        val view = layoutInflater.inflate(R.layout.collectcashotpride, null)
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
//        val paymentmode = view.findViewById(R.id.paymentmode) as TextView
//        val end_address_ = view.findViewById(R.id.end_address_) as TextView
//        val report_spinnericon = view.findViewById(R.id.report_spinnericon) as ImageView
//        reportSpinnerOtp = view.findViewById(R.id.report_spinner_otp) as Spinner
//        flagimage_pay = view.findViewById(R.id.flagimage_pay) as ImageView
//        iconlinear_pay = view.findViewById(R.id.iconlinear_pay) as LinearLayout
//        numberlinear_pay = view.findViewById(R.id.numberlinear_pay) as LinearLayout
//        ratingpage_lay = view.findViewById(R.id.ratingpage_lay) as LinearLayout
//        code_pay = view.findViewById(R.id.code_pay) as TextView
//        enternumber_pay = view.findViewById(R.id.enternumber_pay) as TextView
//        tmoney_progress = view.findViewById(R.id.tmoney_progress) as ProgressBar
//
//        Request_Payment_kkipay = view.findViewById(R.id.Request_Payment_kkipay) as Button
//        Request_Payment_tmoeny = view.findViewById(R.id.Request_Payment_tmoeny) as Button
//        tmoney_enter_lay = view.findViewById(R.id.tmoney_enter_lay) as LinearLayout
//        val loaderreceivecash = view.findViewById(R.id.loaderreceivecash) as fr.castorflex.android.smoothprogressbar.SmoothProgressBar
////        val closetext = view.findViewById(R.id.closetext) as ImageView
//         received = view.findViewById(R.id.cashcollected) as TextView
//        symbol.text = symbosl
//        Log.d("check122", enternumber_pay.toString())
//        amount1.text = payableamount
//        vehicle_select()
//        defaultflagprocessorpay(mContext,defaultflag)
//
//
//        if (OtpRideRepostiatry.arrayList!=null&&OtpRideRepostiatry.arrayList.size>0) {
//            TripFare.text = OtpRideRepostiatry.arrayList[0]
//            tripamount.text = symbosl + OtpRideRepostiatry.arrayListvalue[0]
//            CouponApplied.text = OtpRideRepostiatry.arrayList[1]
//            CouponAppliedamout.text = symbosl + OtpRideRepostiatry.arrayListvalue[1]
//            CommisionAmountitle.text = OtpRideRepostiatry.arrayList[2]
//            CommisionAmount.text = symbosl + OtpRideRepostiatry.arrayListvalue[2]
//            TotalEarningstitle.text = OtpRideRepostiatry.arrayList[3]
//            TotalEarningsamount.text = symbosl + OtpRideRepostiatry.arrayListvalue[3]
//        }
//
//        val firstname = mSessionManager.getUserDetails()[mSessionManager.first_name]!!
//        name.text = firstname
//        first_address_.text = mSessionManager.getpickup_location()
//        end_address_.text = mSessionManager.getdrop_location()
//        if(payableamount.contains("."))
//        {
//            amount1.text = payableamount.split(".")[0]
//            amount2.text = "." + payableamount.split(".")[1]
//        }
//        /* closetext.setOnClickListener {
//             if(loaderreceivecash.visibility == View.VISIBLE)
//                 commsnackbaralert(getString(R.string.slideerror))
//             else
//             {
//                 collectcash!!.dismiss()
//                 finish()
//             }
//         }*/
//
//        Request_Payment_tmoeny.setOnClickListener {
//            paymentnumberlink=enternumber_pay.text.toString()
//            Request_Payment_tmoeny.visibility=View.GONE
//            tmoney_progress.visibility=View.VISIBLE
//            startServicePaymenttripinit(paymentnumberlink)
//        }
//        Request_Payment_kkipay.setOnClickListener {
//            Kkiapay.get().requestPayment(this, payableamount,"Paiement de services",mSessionManager.getUserDetails()[mSessionManager.driver_name]!!,"97000000")
//
//        }
//
//        paymentmode.setOnClickListener{
//            reportSpinnerOtp.performClick()
//
//        }
//
//        report_spinnericon.setOnClickListener{
//            reportSpinnerOtp.performClick()
//
//        }
//        flagimage_pay.setOnClickListener {
//            countryclassintent()
//
//        }
//        iconlinear_pay.setOnClickListener {
//            countryclassintent()
//
//        }
//
//        numberlinear_pay.setOnClickListener {
//            countryclassintent()
//
//        }
//
//        received.setOnClickListener {
//            if(loaderreceivecash.visibility == View.VISIBLE)
//                commsnackbaralert(getString(R.string.slideerror))
//            else
//            {
//                mSessionManager.setmride_completed("")
//                mSessionManager.setdist_one("0.0")
//                mSessionManager.setlocationdistance("0")
//                stop()
//
//                loaderreceivecash.visibility = View.VISIBLE
//                viewModel.receivedcash(mContext, rideid, payableamount)
//
//            }
//
//        }
//        collectcash!!.show()
//    }
//
//    private fun vehicle_select() {
//        var vehicle=ArrayList<String>()
////             vehicle.addAll(VehicleDetailsViewModel.myCategorytype)
//        vehicle.add("Cash")
//        vehicle.add("TMoney")
//        vehicle.add("KKiapay")
//        vehicle.add("Cash")
//
////            vehicle.add("Earnings not credited")
////        vehicle.add("Auto")
//
//
//        if (reportSpinnerOtp != null) {
//            val adapterd = PaymenttypeCustomAdapter(this, vehicle)
//            reportSpinnerOtp.adapter = adapterd
//            reportSpinnerOtp.onItemSelectedListener = object :
//                    AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(parent: AdapterView<*>,
//                                            view: View, position: Int, id: Long) {
//
//                    selectedtype= position.toString()
//                    if (selectedtype.equals("0")){
//                        Request_Payment_kkipay.visibility=View.GONE
//                        Request_Payment_tmoeny.visibility=View.GONE
//                        received.visibility=View.VISIBLE
//                        tmoney_enter_lay.visibility=View.GONE
//                    }else if (selectedtype.equals("1")){
//                        Request_Payment_kkipay.visibility=View.GONE
//                        Request_Payment_tmoeny.visibility=View.VISIBLE
//                        received.visibility=View.GONE
//                        tmoney_enter_lay.visibility=View.VISIBLE
//
//                    }else if (selectedtype.equals("2")){
//                        Request_Payment_kkipay.visibility=View.VISIBLE
//                        Request_Payment_tmoeny.visibility=View.GONE
//                        received.visibility=View.GONE
//                        tmoney_enter_lay.visibility=View.GONE
//
//                    }else if (selectedtype.equals("3")){
//                        Request_Payment_kkipay.visibility=View.GONE
//                        Request_Payment_tmoeny.visibility=View.GONE
//                        received.visibility=View.VISIBLE
//                        tmoney_enter_lay.visibility=View.GONE
//
//                    }
//
//                    Log.d("checkpaymenttype",selectedtype)
//
//
//
//                }
//                override fun onNothingSelected(parent: AdapterView<*>) {
//                    // write code to perform some action
//                }
//            }
//        }
//    }
//
//    fun collectbelowamountfromcustomercash(payableamount: String, symbosl: String)
//    {
//        collectcash =  BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme)
//        val view = layoutInflater.inflate(R.layout.collectcashotpridecompleted, null)
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
////        val closetext = view.findViewById(R.id.closetext) as ImageView
//        val received = view.findViewById(R.id.cashcollected) as TextView
//
//        symbol.text = symbosl
//        amount1.text = payableamount
//        if (OtpRideRepostiatry.arrayList!=null&&OtpRideRepostiatry.arrayList.size>0) {
//            TripFare.text = OtpRideRepostiatry.arrayList[0]
//            tripamount.text = symbosl+ OtpRideRepostiatry.arrayListvalue[0]
//            CouponApplied.text = OtpRideRepostiatry.arrayList[1]
//            CouponAppliedamout.text = symbosl + OtpRideRepostiatry.arrayListvalue[1]
//            CommisionAmountitle.text = OtpRideRepostiatry.arrayList[2]
//            CommisionAmount.text = symbosl + OtpRideRepostiatry.arrayListvalue[2]
//            TotalEarningstitle.text = OtpRideRepostiatry.arrayList[3]
//            TotalEarningsamount.text = symbosl + OtpRideRepostiatry.arrayListvalue[3]
//        }
//
//        val firstname = mSessionManager.getUserDetails()[mSessionManager.first_name]!!
//        name.text = firstname
//        first_address_.text = pickup_location
//        end_address_.text = dropAddress
//        if(payableamount.contains("."))
//        {
//            amount1.text = payableamount.split(".")[0]
//            amount2.text = "." + payableamount.split(".")[1]
//        }
//        /* closetext.setOnClickListener {
//             if(loaderreceivecash.visibility == View.VISIBLE)
//                 commsnackbaralert(getString(R.string.slideerror))
//             else
//             {
//                 collectcash!!.dismiss()
//                 finish()
//             }
//         }*/
//
//
//
//        received.setOnClickListener {
//            mSessionManager.clearridedetails()
//            mSessionManager.setmride_completed("")
//            mSessionManager.setlocationdistance("0")
//            stop()
//
//            val intent_otppage2 = Intent(mContext, mianscreenpage::class.java)
//            intent_otppage2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//            intent_otppage2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent_otppage2)
//
//
//        }
//        collectcash!!.show()
//    }
//
//
//
//
//    internal inner class TimeDisplayTimerTask : TimerTask() {
//        override fun run() {
//            mHandler.post(Runnable {
////                calculatedistance()
//                Log.d("fdefdsf", "asdbbgf")
//
//            })
//        }
//
//    }
//
//
//
//    fun start() {
//        if (!mActive) {
//            mActive = true
//            if (mThread == null || !mThread!!.isAlive()) {
//                mThread = Thread(Runnable {
//                    Looper.prepare()
//                    mTHandler = Handler()
//                    initConnection()
//                    Looper.loop()
//                })
//                mThread!!.start()
//            }
//        }
//    }
//
//    fun stop() {
//        mActive = false
////        mTHandler!!.post(Runnable { if (mConnection != null) mConnection!!.disconnect() })
//
//    }
//
//
//    private fun initConnection() {
//        if (mConnection == null) mConnection = RoosterConnection(this)
//        try {
//            mConnection!!.connect()
//        } catch (e: IOException) {
//        } catch (e: SmackException) {
//        } catch (e: XMPPException) {
//        }
//    }
//
//    fun livetracking(){
//        val jobtrack = JSONObject()
//        jobtrack.put("action", "trackuser")
//        jobtrack.put("latitude", mSessionManager!!.getOnlineLatitiude())
//        jobtrack.put("longitude",mSessionManager!!.getOnlineLongitude())
//        jobtrack.put("ride_id",rideid)
//        jobtrack.put("driver_id", mSessionManager.getDriverId())
//        if (mConnection != null) {
//            mConnection!!.trackingemiteerusertrack(jobtrack.toString(),mSessionManager.getuser_id()+"@"+mSessionManager.getxmpp_host_name())
//
//        }
//    }
//
//
//    fun calculatedistance(){
////        livetracking()
//        mride_completed =mSessionManager. getmride_completed()
//        if (mride_completed.equals("ride_completed")){
//            viewModel.getdataforride(mContext)
//        }
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
//        dataBase!!.insert(onrideDbHelper.TABLE_NAME_FOR_OTP, null, values)
//
//        if (locationdistance.equals("")){
//            mSessionManager.setlocationdistance("0")
//
//        }else{
//            locationdistance=mSessionManager.getlocationdistance()
//
//        }
//
//
//        if(locationdistance.equals("1")){
//            pick_lat_distance=mSessionManager!!.getonlinelatitudeprevious()
//            pick_long_distance=mSessionManager!!.getonlinelongitudeprevious()
//            Log.e("--->previous", "" + pick_lat_distance + "" + pick_long_distance)
//
//        }else{
//            locationdistance="1"
//            mSessionManager.setlocationdistance("1")
//            var current_lat1=mSessionManager!!.getOnlineLatitiude()
//            var current_long1=mSessionManager!!.getOnlineLongitude()
//
//            pick_lat_distance=mSessionManager!!.getonlinelatitudeotp()
//            pick_long_distance=mSessionManager!!.getonlinelongitudeotp()
//            Log.e("--->pick_lat_otp123", "checking")
//
//            Log.e("--->pick_lat_otp1", "" + pick_lat_distance + "" + pick_long_distance)
//        }
//        if (forlocation.equals("")){
//            mSessionManager.setfor_location("0")
//
//        }else{
//            forlocation=mSessionManager.getfor_location()
//
//        }
//
//
//
//        if(forlocation.equals("1")){
////            pick_lat_otp1=mSessionManager!!.getonlinelatitudeprevious()
////            pick_log_otp1=mSessionManager!!.getonlinelongitudeprevious()
////            Log.e("--->dzfhjdhfjd", pick_lat_otp1)
////            Log.e("pick_lasst", pick_lat_otp1)
//
//
//            var pickup_lat: Double = pick_lat_distance.toDouble()
//            var pickup_lon: Double = pick_long_distance.toDouble()
//
//            pickupLatLngotp = LatLng(pickup_lat, pickup_lon)
//
//            var current_latdouble: Double= current_lat.toDouble()
//            var current_longdouble: Double= current_long.toDouble()
//
//            var pick_latdouble: Double= pickup_lat.toDouble()
//            var pick_longdouble: Double= pickup_lon.toDouble()
//
//            latLngA = LatLng(pick_latdouble, pick_longdouble)
//            var latLngB = LatLng(current_latdouble, current_longdouble)
//
//
//            var locationA = Location("point A")
//            locationA.latitude = latLngA!!.latitude
//            locationA.longitude = latLngA!!.longitude
//
//
//            var locationB = Location("point B")
//            locationB.latitude = latLngB.latitude
//            locationB.longitude = latLngB.longitude
//
//            mdistance= locationA.distanceTo(locationB).toDouble()
//
//            var distance = (locationA.distanceTo(locationB).toDouble() / 1000).toDouble()
//            val distanceInMeters: Float = locationA.distanceTo(locationB)
//            Log.e("--->distanceInMeters_1", distanceInMeters.toString())
//
//            var distankm=   distanceInMeters / 1000
//            var distanceg=   distance
//
//            Log.e("--->currentditance", "" + mdistance)
//
//            var distprev =mSessionManager.getdist_one()
//
//            var speed=mSessionManager.getlocationspeed()
//            stroredistance=mSessionManager.getmstroredistance()
//
//
//            if(stroredistance.equals("1")) {
//                Log.e("--->current205", "" + mdistance)
//                if (mdistance >= 100||mdistance <= 300) {
//                    Log.e("--->current201", "" + distprev)
//
//                    var newdistance = distprev.toDouble() + mdistance.toDouble()
//                    mSessionManager.setdist_one(newdistance.toString())
//                    Log.e("--->current202", "" + newdistance)
//                    Log.e("--->current203", "" + mdistance)
//
//                    mSessionManager!!.setonlinelongitudeprevious(current_longdouble.toString())
//                    mSessionManager!!.setonlinelatitudeprevious(current_latdouble.toString())
//                }
//            }
//            else{
//                mSessionManager.setmstroredistance("1")
//
//                if (mdistance >= 100) {
//                    Log.e("--->current204", "" + mdistance)
//                    mSessionManager.setdist_one(mdistance.toString())
//                }
//            }
//        //forlocation=0
//        }else{
//            var current_lat1=mSessionManager!!.getOnlineLatitiude()
//            var current_long1=mSessionManager!!.getOnlineLongitude()
//            mSessionManager!!.setonlinelongitudeprevious(current_long1)
//            mSessionManager!!.setonlinelatitudeprevious(current_lat1)
//            forlocation="1"
//            mSessionManager.setfor_location("1")
//        }
//
//
//
//
//
//     /*   Log.e("--->peiv", "" + distprev)
//        Log.e("--->speed", "" + speed)
//        var accuracy =""
//        var speedmeter=""
//   if(speed.toString().equals("0")){
//
//    }else{
//    val stringArray: List<String> = speed.split(",")
//     accuracy=stringArray[0]
//     speedmeter=stringArray[1]
//      }
//
//
//        Log.e("--->accuracy", "" + accuracy)
//
//        var chekcnewdistance =mSessionManager.getdist_one().toDouble()
//        Log.e("mmmhekcnewdistance", "" + chekcnewdistance)
//
//
//        var speed_distance= speedmeter.toDouble()*2.5
//        Log.e("--->speed_distance", "" + speed_distance)
//        Log.e("--->mstroredistance", "" + stroredistance)
//
//        Log.e("mmstroredistancetow", "" + stroredistance)
//
//        stroredistance=mSessionManager.getmstroredistance()
//
//        if(stroredistance.equals("1")){
//
//            if (accuracy.toDouble()<mdistance) {
//
//                if (mdistance.toDouble() < speed_distance)
//                {
//
//                    if (distprev.toDouble() < 2.0)
//                    {
//                        var newdistance = distprev.toDouble() + mdistance.toDouble()
//                        newdistance - 0.5
//                        mSessionManager.setdist_one(newdistance.toString())
//                        Log.e("--->current20", "" + newdistance)
//
//                    } else
//                    {
//                        var newdistance = distprev.toDouble() + mdistance.toDouble()
//                        mSessionManager.setdist_one(newdistance.toString())
//                        Log.e("--->current20", "" + newdistance)
//                    }
//
//                    //Log.e("--->current", "" + newdistance)
//                    Log.e("--->current", "" + mdistance)
//                } else
//                {
//                    Log.e("--->currentbig", "" + mdistance)
//
//                }
//            }
//        }else{
//            mSessionManager.setmstroredistance("1")
//            mSessionManager.setdist_one(mdistance.toString())
//            stroredistance=mSessionManager.getmstroredistance()
//            Log.e("mmstroredistance", "" + stroredistance)
//
//            Log.e("--->pick", "" + mdistance)
//        }*/
//        var sum =mSessionManager.getdist_one().toDouble()
//        sum /= 1000.0
//        Log.d("check20ss21", sum.toString())
//        var distcals : Int=((sum)+1).toInt()
//        Log.d("check2d0221", distcals.toString())
//        val dis = String.format("%.1f", sum)
//        Log.e("--->sum", "" + dis + " km")
//
////        var distcal : Int=dis.toInt()+ 1
////        Log.d("distcal", distcal.toString())
//
///*        if ( distanceInMeters > 1000){
//            var distankm=   distanceInMeters / 1000
//            Log.d("distanceInMeters_55", distankm.toString())
//            distancestring = distankm.toString().substring(0, 3)
////            distancestring = distancestring.substring(0, 4)+" Km"
//             mSessionManager.setdist_one(distancestring)
//             var distprev =mSessionManager.getdist_one()
//
//            var  newdistance =distprev.toDouble() + distancestring.toDouble()
//            Log.d("newdistanc2e", newdistance.toString())
//
//        }else{
//            (distanceInMeters / 1000).toInt()
//            Log.d("yhdfdgdgdydyd", distanceInMeters.toString())
//            distancestring = distanceg.toString()
//            distancestring = distancestring.substring(0, 3)
//            mSessionManager.setdist_one(distancestring)
//            var distprev =mSessionManager.getdist_one()
//            var  newdistance =distprev.toDouble() + distancestring.toDouble()
//            Log.d("checkneww", newdistance.toString())
//            Log.d("distanceInMeters_201", distancestring.toString())
//        }*/
//
//
////        distancestring = distance.toString()
//        val calendar = Calendar.getInstance()
//        drop_latlngotp = LatLng(current_lat.toDouble(), current_long.toDouble())
//
////        Log.d("distance_from", distance.toString())
//
//        Log.d("current_lat", drop_latlng.toString())
////        var pick_lat_adrress=mSessionManager.getonlinelatitudepickadress()
////        var pick_long_adrress=mSessionManager.getonlinelongitudpickadress()
//
//
//
////        pickup_location =""+getAddressForLocation(this@otpride, pick_lat_adrress.toDouble(), current_longdouble.toDouble())
//        dropAddress =  getAddressForLocation(this@otpride, current_lat.toDouble(), current_long.toDouble())
//        Log.d("pickup_locationsfdf", pickup_location.toString())
//
//        Log.d("dropAddressdsgxf55", dropAddress.toString())
//
////        drawplypolylineotp()
//
//        pickpcurrenttime=mSessionManager!!.getpickpcurrenttimeotp()
//        Log.d("pickuptimeOtp", pickpcurrenttime)
//
//        if (pickpcurrenttime.equals("")){
//
//        }else {
//            if (latLngA != null) {
//
//                val simpleDateFormat = SimpleDateFormat("HH:mm")
//                currenttime = simpleDateFormat.format(calendar.time)
//                val startDate = simpleDateFormat.parse(mSessionManager.getpickpcurrenttimeotp())
//                val endDate = simpleDateFormat.parse(currenttime)
//                var difference: Long = endDate.getTime() - startDate.getTime()
//                if (difference < 0) {
//                    val dateMax: Date = simpleDateFormat.parse("24:00")
//                    val dateMin: Date = simpleDateFormat.parse("00:00")
//                    difference = dateMax.time - startDate.getTime() + (endDate.getTime() - dateMin.time)
//                }
//                val days = (difference / (1000 * 60 * 60 * 24)).toInt()
//                val hours = ((difference - 1000 * 60 * 60 * 24 * days) / (1000 * 60 * 60)).toInt()
//                minmeter = ((difference - 1000 * 60 * 60 * 24 * days - 1000 * 60 * 60 * hours).toInt() / (1000 * 60)).toString()
//                viewModel.estimatedFareCalculation(mContext, mSessionManager.getLocationFareApiResponse(), latLngA!!, minmeter.toDouble(), distcals.toDouble())
//                Log.d("dgsdfssgdg", minmeter.toString())
//                Log.d("dsdasfsfdsdf", dis.toString())
//
////            address_tv.text = dropAddress
////            distance_value_et.text = dropAddress
//                distance_value_et.text = minmeter + " min"
//                kms_value_et.text = dis + "KM"
//                fair_value_et.text = currency_symbol + " " + selectedCategoryEstAmount
//
//                time_to_reach.text = String.format("%.2f", dest_time)
//                mSessionManager.setdistance_km_otp(dis.toString())
////            updatelocationtoservices()
//
//
//            }
//        }
//    }
//    fun drawplypolylineotp(){
////        latLngListotp.clear()
////        latLngListotp.add(pickupLatLngotp!!)
////        latLngListotp.add(drop_latlngotp!!)
//////        showCurvedPolyline(pickupLatLngotp!!, drop_latlngotp!!, 2.0)
//////        val line: Polyline = googlemap!!.addPolyline(PolylineOptions()
//////                .add(pickupLatLngotp, drop_latlngotp)
//////                .width(10f)
//////                .color(Color.RED))
//////        Log.wtf("Activity", "Poits=" + line.points)
////
////        drawply = FindRoute(this@otpride)
////        drawply?.clearpolyline()
////        drawply?.setUpPolyLine(googlemap!!, this@otpride, pickupLatLngotp, drop_latlngotp, latLngListotp, true)
////
//
//
//
//
//    }
//
//
//
//
//
//
//    fun callMeterDialog(){
////        meterDialog =  Dialog(this, R.style.dialogTheme)
////        val view = layoutInflater.inflate(R.layout.fairs_dialogue, null)
////        meterDialog!!.setContentView(view)
////        meterDialog!!.setCancelable(true)
////        var closeBtn=meterDialog!!.findViewById<ImageView>(R.id.close_btn)
////        var address_tv=meterDialog!!.findViewById<TextView>(R.id.address_tv)
////        var kms_value_et=meterDialog!!.findViewById<TextView>(R.id.kms_value_et)
////        var distance_value_et=meterDialog!!.findViewById<TextView>(R.id.distance_value_et)
////        var fair_value_et=meterDialog!!.findViewById<TextView>(R.id.fair_value_et)
////        address_tv.text = dropAddress
////        distance_value_et.text = dropAddress
////        distance_value_et.text = minmeter+" min"
////        kms_value_et.text = distancestring
////        fair_value_et.text = currency_symbol + " " + selectedCategoryEstAmount
////
////        time_to_reach.text = String.format("%.2f", dest_time)
////
////
////        closeBtn.setOnClickListener { meterDialog!!.dismiss() }
////        meterDialog!!.show()
//    }
//
//    fun commontoast(message: String)
//    {
//        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
//    }
//
//    fun getAddressForLocation(context: Activity, latitude: Double?, longitude: Double?): String {
//        var pickupaddress = ""
//        val geocoder = Geocoder(context, Locale.getDefault())
//        try {
//            val addresses = geocoder.getFromLocation(latitude!!, longitude!!, 1)
//            if (addresses != null && !addresses.isEmpty()) {
//                pickupaddress = addresses[0].getAddressLine(0)
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return pickupaddress
//    }
//
//    fun isOtpValid(): Boolean {
//        var otp=otp1.text.toString()+otp2.text.toString()+otp3.text.toString()+otp4.text.toString()
//        return otpTxt==otp
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
////    private fun stoponlineservice()
////    {
////        val serviceClass = OnlineJobService::class.java
////        val serviceIntent = Intent(applicationContext, serviceClass)
////        try {
////            unbindService(arrivalConnection)
////        } catch (e: IllegalArgumentException) {
////        }
////        if (viewModel.isMyServiceRunning(mContext, OnlineJobService::class.java)) {
////            stopService(serviceIntent)
////        }
////    }
//
//}
