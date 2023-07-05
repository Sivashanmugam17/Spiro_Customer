package com.mauto.chd.backgroundservices

//import com.cabilyhandyforalldinedoo.chd.ui.MainPage.requestmianscreen
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.*
import com.mauto.chd.SessionManagerPackage.SessionManager
//import com.cabilyhandyforalldinedoo.chd.ui.MainPage.otpride
//import kotlinx.android.synthetic.main.fairs_dialogue.*
//import kotlinx.android.synthetic.main.otpride.*
import kotlinx.coroutines.*
import java.util.*


class UpdateForgroundService : Service() {
    private var mTimer: Timer? = null
    var INTERVAL: Long = 25000
    var mHandler = Handler()
//    var factory = ConnectionFactory()
    var subscribeThread: Thread? = null
    var publishThread: Thread? = null
    var current_lat = ""
    var current_lon = ""
    var old_lat = 0.0
    var old_long = 0.0
//    private lateinit var connection:Connection
//    private lateinit var gps: GPSTracker
//    private lateinit var ch: Channel
//    private lateinit var mSessionManager: SessionManager
    var temp=0;
    var Mq_user = ""
    var Mq_pass = ""
    var Mq_host = ""
    var Mq_vhost = ""
    var Mq_port = ""
    var Mq_queue = ""
    var Mq_exchange = ""
    var Mq_uptime = ""
    var Mq_status = ""
    lateinit var mNotification:Notification
    private lateinit var repeatFun :Job
    var distance=""

    var timer=Timer()

    override fun onBind(p0: Intent?): IBinder? {
        return null;
    }

    companion object {
        const val CHANNEL_ID = "samples.notification.devdeeds.com.CHANNEL_ID"
        const val CHANNEL_NAME = "Sample Notification"

    }

    //on start command
    @InternalCoroutinesApi
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

//       repeat()
//       repeatFun = repeatFun()
                var mSessionManager: SessionManager? = null
            mSessionManager = SessionManager(applicationContext)
//           val distance = intent!!.getStringExtra("distance")



        if (distance != null) {
//           getNotification(applicationContext,"Ongoing OTP Trip", "Trip Distance: "+ distance +" KM")
        }

        return START_STICKY
    }








    internal class TimeDisplayTimerTask : TimerTask() {
        override fun run() {
            Handler().postDelayed({
                //doSomethingHere()
                println("-----called--------")

            }, 3000)

            // run on another thread

        }
    }

//    @InternalCoroutinesApi
//    fun repeatFun(): Job {
//        val scope = CoroutineScope(Dispatchers.Default)
//        return scope.launch {
//            while(isActive) {
//                repeat()
//
//                delay(5000)
//            }
//        }
//    }

//    fun repeat(){
//        var mSessionManager: SessionManager? = null
//        mSessionManager = SessionManager(applicationContext)
//        var current_lat=mSessionManager!!.getOnlineLatitiude()
//        var current_long=mSessionManager!!.getOnlineLongitude()
//        var pick_lat=mSessionManager!!.getonlinelatitudeotp()
//        var pick_long=mSessionManager!!.getonlinelongitudeotp()
//        Log.d("fgghfhf",current_lat)
//        var pickup_lat: Double = pick_lat.toDouble()
//        var pickup_lon: Double = pick_long.toDouble()
//        var current_latdouble: Double= current_lat.toDouble()
//        var current_longdouble: Double= current_long.toDouble()
//        var pick_latdouble: Double= pickup_lat.toDouble()
//        var pick_longdouble: Double= pickup_lon.toDouble()
//        var latLngA = LatLng(pick_latdouble, pick_longdouble)
//        var latLngB = LatLng(current_latdouble, current_longdouble)
//        var locationA = Location("point A")
//        locationA.latitude = latLngA.latitude
//        locationA.longitude = latLngA.longitude
//        var locationB = Location("point B")
//        locationB.latitude = latLngB.latitude
//        locationB.longitude = latLngB.longitude
//        distance  = (locationA.distanceTo(locationB).toDouble() / 1000).toDouble().toString()
//        Log.d("gfrdgf",distance)
//
//    }



    override fun onDestroy() {
        super.onDestroy()
        disconnectChannel()
        stopForeground(true);
        if (mTimer != null) {
            mTimer!!.cancel();
        }

    }

    private fun getNotification(context: Context, content1:String, content2:String)
    {
//        createChannel(context)
//        val notifyIntent = Intent(context, otpride::class.java)
//        val title = content1
//        val message = content2
//        notifyIntent.putExtra("title", title)
//        notifyIntent.putExtra("message", message)
//        notifyIntent.putExtra("notification", true)
//        notifyIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        val pendingIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//        {
//            mNotification = Notification.Builder(context, CHANNEL_ID)
//                .setContentIntent(pendingIntent)
//                .setSmallIcon(R.drawable.appicon)
//                .setAutoCancel(true)
//                .setContentTitle(title)
//                .setStyle(
//                    Notification.BigTextStyle()
//                    .bigText(message))
//                .setContentText(message).build()
//        }
//        else
//        {
//            mNotification = Notification.Builder(context)
//                .setContentIntent(pendingIntent)
//                .setSmallIcon(R.drawable.appicon)
//                .setAutoCancel(true)
//                .setContentTitle(title)
//                .setStyle(
//                    Notification.BigTextStyle()
//                    .bigText(message))
//                .setContentText(message).build()
//        }
//        startForeground(999, mNotification)
    }

    private fun createChannel(context: Context)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val importance = NotificationManager.IMPORTANCE_LOW
            val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            notificationChannel.enableVibration(true)
            notificationChannel.setShowBadge(true)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.parseColor("#e8334a")
            notificationChannel.description = "notification channel description"
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    fun disconnectChannel(){


    }

}