//package com.cabilyhandyforalldinedoo.chd.ui.MainPage
//
//import android.Manifest
//import android.app.Activity
//import android.app.ActivityManager
//import android.app.KeyguardManager
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.content.res.Resources
//import android.location.Location
//import android.media.AudioManager
//import android.media.MediaPlayer
//import android.os.Build
//import android.os.Bundle
//import android.os.Handler
//import android.util.SparseArray
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.view.WindowManager
//import android.widget.*
//import androidx.appcompat.app.AppCompatActivity
//import androidx.cardview.widget.CardView
//import androidx.core.content.ContextCompat
//import androidx.lifecycle.ViewModelProviders
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import cabily.handyforall.dinedoo.R
//import com.cabilyhandyforalldinedoo.chd.Backgroundservices.acceptcallapi
//import com.cabilyhandyforalldinedoo.chd.EventBusConnection.IntentServiceResult
//import com.cabilyhandyforalldinedoo.chd.SessionManagerPackage.SessionManager
//import com.cabilyhandyforalldinedoo.chd.ViewModelRideRequest.ItemInfo
//import com.cabilyhandyforalldinedoo.chd.ViewModelRideRequest.RideRequestViewModel
//import com.cabilyhandyforalldinedoo.chd.clickableInterface.Requestlistener
//import com.cabilyhandyforalldinedoo.chd.ui.registeration.LocaleAwareCompatActivity
//import com.cabilyhandyforalldinedoo.chd.ui.registeration.splashpage
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.*
//import com.google.android.material.snackbar.Snackbar
//import fr.castorflex.android.smoothprogressbar.SmoothProgressBar
//import kotlinx.android.synthetic.main.requestpage.*
//import org.greenrobot.eventbus.EventBus
//import org.greenrobot.eventbus.Subscribe
//import org.greenrobot.eventbus.ThreadMode
//import org.jetbrains.anko.toast
//import org.json.JSONObject
//import java.util.*
//import kotlin.collections.ArrayList
//
//
//class riderequestpage : LocaleAwareCompatActivity(), OnMapReadyCallback {
//
//    private var pickup_lat: Double = 0.0
//    private var pickup_lng: Double = 0.0
//    private var drop_lat: Double = 0.0
//    private var drop_lng: Double = 0.0
//    private var comingpicklat: Double = 0.0
//    private var comingpicklong: Double = 0.0
//    var scrollpostion: Int = 0
//    private lateinit var mContext: Activity
//    private var googlemap: GoogleMap? = null
//    private lateinit var mMapFragment: SupportMapFragment
//    private lateinit var viewModel: RideRequestViewModel
//    var riderequestadapters: riderequestjava? = null
//    private lateinit var mediaPlayer: MediaPlayer
//    var padding: Int = 0
//    private var bearingValue = 0f
//    var fullarraylist: ArrayList<ItemInfo> = ArrayList()
//    lateinit var mSessionManager: SessionManager
//
//    override fun onCreate(savedInstanceState: Bundle?)
//    {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.requestpage)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1)
//        {
//            setShowWhenLocked(true)
//            setTurnScreenOn(true)
//            val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
//            keyguardManager.requestDismissKeyguard(this, null)
//        }
//        else
//        {
//            this.window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
//                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
//                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
//        }
//        mContext = this@riderequestpage
//        mSessionManager = SessionManager(mContext!!)
//        initviewmodel()
//        callcommonintent(intent)
//        initializeMap()
//        initizemediaplayer()
//
//        decline.setOnClickListener {
//            if (loader.getVisibility() == View.VISIBLE)
//                Toast.makeText(mContext,mContext.getString(R.string.loading), Toast.LENGTH_LONG).show()
//            else
//            {
//                if (fullarraylist.size > 0)
//                {
//                    viewModel.requestackkno(mContext, fullarraylist.get(scrollpostion).getrideid().toString(),fullarraylist.get(scrollpostion).getcategory().toString(), "2")
//                    fullarraylist.removeAt(scrollpostion)
//                    initrecyclerviews()
//                }
//                if (fullarraylist.size == 0)
//                {
//                    finish()
//                }
//            }
//        }
//    }
//
//    override fun onNewIntent(intent: Intent?)
//    {
//        super.onNewIntent(intent)
//        if (intent != null)
//        {
//            if (fullarraylist.size > 0)
//            {
//                callcommonintent(intent)
//            }
//        }
//    }
//
//
//    fun callcommonintent(intent: Intent)
//    {
//        val ride_id = intent.getStringExtra("ride_id")
//        val time_stamp = intent.getStringExtra("time_stamp")
//        val ack_id = intent.getStringExtra("category_name")
//        val timer = intent.getStringExtra("timer")
//        var ratting: String = intent.getStringExtra("ratting")
//        val duration = intent.getStringExtra("duration")
//        val distance = intent.getStringExtra("distance")
//        val pickup_lat= intent.getStringExtra("pickup_lat")
//        val pickup_lon = intent.getStringExtra("pickup_lon")
//        val category = intent.getStringExtra("category")
//        comingpicklat=pickup_lat.toDouble()
//        comingpicklong=pickup_lon.toDouble()
//        val pickup = intent.getStringExtra("pickup")
//        if (ratting.equals("0"))
//        {
//            ratting = "0.00"
//        }
//        val curTime = System.currentTimeMillis()
//        var timertoin: Int = timer.toInt()
//        var timertolong: Long = (timertoin * 1000).toLong()
//        var iam: Int = 0
//        for (j in 0 until fullarraylist.size)
//        {
//            if (fullarraylist.get(j).getrideid().equals(ride_id))
//            {
//                iam = 1
//            }
//        }
//        if (iam == 0)
//        {
//            viewModel.requestackkno(mContext, ride_id,ack_id, "0")
//            fullarraylist.add(ItemInfo(ride_id, time_stamp, ack_id, timer.toLong(), ratting, duration, distance, category, pickup_lat, pickup_lon, timertolong, (curTime + timertolong), pickup))
//            if (fullarraylist.size == 1)
//            {
//                initrecyclerviews()
//            }
//            else
//            {
//                riderequestadapters!!.notifyDataSetChanged()
//            }
//        }
//    }
//
//
//
//    fun initizemediaplayer()
//    {
//        mediaPlayer = MediaPlayer.create(applicationContext,R.raw.ub__speeding)
//        mediaPlayer.start()
//        mediaPlayer.setLooping(true)
//        val MAX_VOLUME = 100
//        val volume = (1 - Math.log((MAX_VOLUME - 80).toDouble()) / Math.log(MAX_VOLUME.toDouble())).toFloat()
//        var audioManager:AudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager;
//        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
//        try
//        {
//            mediaPlayer.setVolume(volume, volume)
//        }
//        catch (e: Exception)
//        {
//        }
//    }
//
//    fun mapdisplay(droplat: Double, droplong: Double) {
//        googlemap!!.clear()
//        var ctlat: Double = mSessionManager.getOnlineLatitiude().toDouble()
//        var ctlong: Double = mSessionManager.getOnlineLongitude().toDouble()
//        var droplat: Double = droplat
//        var droplong: Double = droplong
//        pickup_lat = mSessionManager.getOnlineLatitiude().toDouble()
//        pickup_lng = mSessionManager.getOnlineLongitude().toDouble()
//        drop_lat = droplat
//        drop_lng = droplong
//        val pickupLocation = Location("pickup point");
//        pickupLocation.latitude = ctlat
//        pickupLocation.longitude = ctlong
//        val dropLocation = Location("drop point");
//        dropLocation.latitude = droplat
//        dropLocation.longitude = droplong
//        bearingValue = pickupLocation.bearingTo(dropLocation)
//        val coordinate = LatLng(droplat, droplong)
//        var marker:Marker? = null
//        val markerOptions = MarkerOptions()
//        markerOptions.position(coordinate)
//        marker = googlemap!!.addMarker(markerOptions)
//        marker!!.showInfoWindow()
//        val location = CameraUpdateFactory.newLatLngZoom(
//                coordinate, 15f)
//        googlemap!!.moveCamera(location)
//    }
//
//    fun initviewmodel() {
//        viewModel = ViewModelProviders.of(this).get(RideRequestViewModel::class.java)
//        viewModel.clearchatrecord(mContext)
//    }
//
//    private fun initrecyclerviews() {
//        decline.visibility = View.GONE
//        val linearLayoutManager = LinearLayoutManager(mContext)
//        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
//        requestcard.layoutManager = linearLayoutManager
//        riderequestadapters = riderequestjava(this,loader, fullarraylist, object : Requestlistener {
//            override fun onItemClickListener(codeshortname: String) {
//
//                if (codeshortname.equals("reject")){
//                    decline.performClick()
//                }else{
//                    laststack()
//                }
//            }
//        })
//        requestcard.adapter = riderequestadapters
//        requestcard.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val scrollpostions = linearLayoutManager.findFirstCompletelyVisibleItemPosition()
//                if (scrollpostions > -1) {
//                    scrollpostion = scrollpostions
//                    if (googlemap != null) {
//                        googlemap!!.clear()
//                        if (fullarraylist.size > 0) {
//                            fullarraylist.get(scrollpostion).getpickup()
//                            var droplat: Double = fullarraylist.get(scrollpostion).getpickuplat()!!.toDouble()
//                            var droplong: Double = fullarraylist.get(scrollpostion).getpickuplong()!!.toDouble()
//                            mapdisplay(droplat, droplong)
//                        }
//                    }
//                }
//            }
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//            }
//        })
//    }
//
//    private fun initializeMap()
//    {
//        try
//        {
//            mMapFragment = supportFragmentManager
//                    .findFragmentById(R.id.requestpagemap) as SupportMapFragment
//            mMapFragment.getMapAsync(this)
//        }
//        catch (e: Exception)
//        {
//            e.printStackTrace()
//        }
//    }
//
//    override fun onMapReady(mGoogleMap: GoogleMap?)
//    {
//        googlemap = mGoogleMap
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//        {
//            if (ContextCompat.checkSelfPermission(this,
//                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                mGoogleMap!!.isMyLocationEnabled = false
//            }
//        } else {
//            mGoogleMap!!.isMyLocationEnabled = false
//        }
//        mGoogleMap!!.getUiSettings().setRotateGesturesEnabled(false)
//        setMapStyle(googlemap!!)
//        mGoogleMap!!.getUiSettings().setMapToolbarEnabled(false)
//        mapdisplay(comingpicklat, comingpicklong)
//    }
//
//    private fun setMapStyle(googleMap: GoogleMap) {
//        try {
//            var success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
//        } catch (e: Resources.NotFoundException) {
//        } catch (e1: Exception) {
//        }
//    }
//
//    override fun onDestroy()
//    {
//        super.onDestroy()
//        if(EventBus.getDefault().isRegistered(this))
//        {
//            EventBus.getDefault().unregister(this)
//        }
//
//        if(mediaPlayer!=null)
//        {
//            mediaPlayer.stop()
//            mediaPlayer.release()
//        }
//        if (null != riderequestadapters)
//        {
//            riderequestadapters!!.cancelRefreshTime();
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        if(!EventBus.getDefault().isRegistered(this))
//        {
//            EventBus.getDefault().register(this);
//        }
//
//        if (null != riderequestadapters) {
//            riderequestadapters!!.startRefreshTime()
//        }
//    }
//
//    override fun onPause() {
//        super.onPause()
//        if (null != riderequestadapters) {
//            riderequestadapters!!.cancelRefreshTime()
//        }
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun decline(decline : String){
//
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun acceptrespose(intentServiceRouteResult: IntentServiceResult)
//    {
//        var apiName: String = intentServiceRouteResult.apiName
//        if (apiName.equals(getString(R.string.acceptcall)))
//        {
//            var response: String = intentServiceRouteResult.resultValue
//            if (response != "failed")
//            {
//                var responseObj = JSONObject(response)
//                if (responseObj.getString("status").equals("1"))
//                {
//                    loader.visibility=View.GONE
//                    mSessionManager.onridedetails(response,"1","0")
//                    val intent_otppage = Intent(mContext, requestmianscreen::class.java)
//                    intent_otppage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                    intent_otppage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                    startActivity(intent_otppage)
//                }
//                else  if (responseObj.getString("status").equals("0"))
//                {
//                    loader.visibility=View.GONE
//                    toast(responseObj.getString("response"))
//                    finish()
//                }
//            }
//            else
//            {
//                loader.visibility=View.GONE
//                val snack = Snackbar.make(decline,getString(R.string.failedproblem),Snackbar.LENGTH_LONG)
//                snack.show()
//            }
//        }
//    }
//
//    // adapter for riderequest
//    class riderequestjava(private val mContext: Context,private val loader:SmoothProgressBar, private val mDatas: MutableList<ItemInfo>, private  val mCustomOnClickListener: Requestlistener) : RecyclerView.Adapter<MyViewHolder>() {
//        private var mCountdownVHList: SparseArray<MyViewHolder>? = null
//        private val mHandler = Handler()
//        private var mTimer: Timer? = null
//        private var isCancel = true
//        private val mRefreshTimeRunnable = Runnable {
//            if (mCountdownVHList!!.size() == 0) return@Runnable
//            try {
//                synchronized(mCountdownVHList!!) {
//                    val currentTime = System.currentTimeMillis()
//                    var key: Int = 0
//                    for (i in 0 until mCountdownVHList!!.size()) {
//                        key = mCountdownVHList!!.keyAt(i)
//                        val curMyViewHolder = mCountdownVHList!!.get(key)
//                        try {
//                            if (currentTime >= curMyViewHolder.bean!!.getendtime()!!) {
//                                curMyViewHolder.bean!!.setCountdowntime(0)
////                                val removedid = "" + curMyViewHolder.bean!!.getrideid()!!
//                                val removedid = "" + curMyViewHolder.bean!!.gettimestamp()!!
//                                mCountdownVHList!!.remove(key)
//                                var removepostion = 0
//                                for (jk in mDatas.indices) {
//                                    val id = "" + mDatas[jk].getrideid()!!
//                                    if (id == removedid) {
//                                        removepostion = jk
//                                    }
//                                }
//                                mDatas.removeAt(removepostion)
//                                notifyDataSetChanged()
//                                if (mDatas.size == 0) {
//                                    mCustomOnClickListener.onItemClickListener("stop")
//                                }
//                            } else {
//                                curMyViewHolder.refreshTime(currentTime)
//                            }
//                        } catch (e: Exception) {
//                        }
//                    }
//                }
//            } catch (e: IndexOutOfBoundsException) {
//                e.printStackTrace()
//            }
//        }
//
//        init
//        {
//            mCountdownVHList = SparseArray()
//            startRefreshTime()
//        }
//        fun startRefreshTime()
//        {
//            if (!isCancel) return
//            if (null != mTimer) {
//                mTimer!!.cancel()
//            }
//            isCancel = false
//            mTimer = Timer()
//            mTimer!!.schedule(object : TimerTask() {
//                override fun run() {
//                    mHandler.post(mRefreshTimeRunnable)
//                }
//            }, 0, 10)
//        }
//        fun cancelRefreshTime()
//        {
//            isCancel = true
//            if (null != mTimer) {
//                mTimer!!.cancel()
//            }
//            mHandler.removeCallbacks(mRefreshTimeRunnable)
//        }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
////            return MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.driverideaccpted, parent, false))
//        }
//        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//            val curItemInfo = mDatas[position]
//            holder.decline.setOnClickListener {
//                mCustomOnClickListener.onItemClickListener("reject")
//            }
//            holder.bindData(curItemInfo, position, mContext,loader)
//            if (curItemInfo.getcountdowntime()!! > 0) {
//                mCountdownVHList?.let {
//                    synchronized(it) {
////                        mCountdownVHList!!.put(Integer.parseInt(curItemInfo.getrideid()!!), holder)
//                        mCountdownVHList!!.put(Integer.parseInt(curItemInfo.gettimestamp()!!), holder)
//                    }
//                }
//            }
//        }
//        override fun getItemCount(): Int {
//            return mDatas.size
//        }
//        override fun onViewRecycled(holder: MyViewHolder) {
//            super.onViewRecycled(holder)
//            val curAnnounceGoodsInfo = holder.bean
//            if (null != curAnnounceGoodsInfo && curAnnounceGoodsInfo.getcountdowntime()!! > 0) {
////                mCountdownVHList!!.remove(Integer.parseInt(curAnnounceGoodsInfo.getrideid()!!))
//                mCountdownVHList!!.remove(Integer.parseInt(curAnnounceGoodsInfo.gettimestamp()!!))
//            }
//        }
//    }
//
//    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val category: TextView
//        private val rating: TextView
//        private val min: TextView
//        private val miles: TextView
//        private val areaname: TextView
//        private val drivernames: TextView
//        private val mProgress: ProgressBar
//        private val amountcard: RelativeLayout
//        private val imageView: ImageView
//        val decline: LinearLayout
//        val textView3 : TextView
//
//
//        var bean: ItemInfo? = null
//            private set
//        init {
//            category = itemView.findViewById(R.id.category) as TextView
//            rating = itemView.findViewById(R.id.rating) as TextView
//            min = itemView.findViewById(R.id.min) as TextView
//            drivernames = itemView.findViewById(R.id.drivernames) as TextView
//            miles = itemView.findViewById(R.id.miles) as TextView
//            areaname = itemView.findViewById(R.id.areaname) as TextView
//            mProgress = itemView.findViewById(R.id.loadthis) as ProgressBar
//            amountcard = itemView.findViewById(R.id.amountcard) as RelativeLayout
//            decline = itemView.findViewById(R.id.decline) as LinearLayout
//            textView3 = itemView.findViewById(R.id.textView3) as TextView
//
//            imageView = itemView.findViewById(R.id.imageView) as ImageView
//
//        }
//        fun bindData(itemInfo: ItemInfo, postion: Int, ctx: Context,loader:SmoothProgressBar)
//        {
//
//            var mSessionManager: SessionManager
//            mSessionManager = SessionManager(ctx)
//            val category_name = itemInfo.getcategory()
//            bean = itemInfo
//            if (itemInfo.getcountdowntime()!! > 0) {
//                refreshTime(System.currentTimeMillis())
//            }
//            areaname.text = itemInfo.getpickup()
//            drivernames.text=mSessionManager.getUserDetails()[mSessionManager.driver_name]!!
//            category.text = itemInfo.droppoint
//            rating.text = itemInfo.getrating()
//            min.text = itemInfo.getmin()
//            miles.text = itemInfo.getmiles()
//
//            imageView.setOnClickListener {
//                if (loader.getVisibility() == View.VISIBLE)
//                    Toast.makeText(ctx,ctx.getString(R.string.loading), Toast.LENGTH_LONG).show()
//                else
//                {
//                    loader.visibility = View.VISIBLE
//                    val serviceClass = acceptcallapi::class.java
//                    val intent = Intent(ctx, serviceClass)
//                    intent.putExtra("ride_id", itemInfo.getrideid())
//                    intent.putExtra("ack_id", itemInfo.getcategory())
//                    ctx.startService(intent)
//                }
//            }
//            val res = ctx.getResources()
//            val drawable = res.getDrawable(R.drawable.circular)
//            mProgress.progress = 0   // Main Progress
//            mProgress.max = itemInfo.gettimer()!!.toInt() // Maximum Progress
//            mProgress.progressDrawable = drawable
//        }
//
//        fun refreshTime(curTimeMillis: Long) {
//            if (null == bean || bean!!.getcountdowntime()!! <= 0) return
//            var values: Long = (bean!!.getendtime()!! - curTimeMillis) / 1000
//            var valuesint: Int = values.toInt()
//            mProgress.setProgress(valuesint)
//        }
//    }
//
//    private fun laststack() {
//        var returuncount = 0
//        val mngr = mContext.getSystemService(ACTIVITY_SERVICE) as ActivityManager
//        val taskList = mngr.getRunningTasks(10)
//        returuncount = taskList[0].numActivities
//        if (returuncount == 1) {
//            val intents = Intent(mContext, splashpage::class.java)
//            intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//            mContext.startActivity(intents)
//            mContext.finish()
//        } else {
//            mContext.finish()
//        }
//    }
//}