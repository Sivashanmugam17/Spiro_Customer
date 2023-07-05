package com.mauto.chd.backgroundservices
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.*
import android.util.Log
import com.mauto.chd.R
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.SessionManagerPackage.LanguageSessionManager
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.clickableInterface.TripIntentServiceResult
//import com.cabilyhandyforalldinedoo.chd.ui.MainPage.mianscreen
//import com.cabilyhandyforalldinedoo.chd.ui.MainPage.requestmianscreen
//import com.cabilyhandyforalldinedoo.chd.xmpp.RoosterConnection
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
//import org.jivesoftware.smack.SmackException
//import org.jivesoftware.smack.XMPPException
import org.json.JSONObject
import java.util.*

class OnlineJobService : Service()
{
    private var mActive: Boolean = false
    private var mThread: Thread? = null
    private var mTHandler: Handler? = null
//    private var mConnection: RoosterConnection? = null
    private var mSessionManager: SessionManager ?= null
    private val mHandler = Handler()
    private val myBinder = MyLocalBinder()
    lateinit private var langmSessionManager: LanguageSessionManager
    private var mTimer: Timer? = null
    val INTERVAL: Long = 10000
    lateinit var mNotification:Notification
    override fun onBind(intent: Intent): IBinder?
    {
        return myBinder
    }
    inner class MyLocalBinder : Binder()
    {
        fun getService() : OnlineJobService {
            return this@OnlineJobService
        }
    }
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int
    {
        start()
        langmSessionManager = LanguageSessionManager(applicationContext!!)
        if(langmSessionManager!!.getlanguageoption().equals("1"))
            getNotification(applicationContext,getString(R.string.zojekonlinetamil),getString(R.string.onlinenotifymessagetamil))
        else if(langmSessionManager!!.getlanguageoption().equals("2"))
            getNotification(applicationContext,getString(R.string.zojekonline),getString(R.string.onlinenotifymessage))
        else
            getNotification(applicationContext,getString(R.string.zojekonlineurudu),getString(R.string.onlinenotifymessageurdu))
        if (mTimer != null) run {
            mTimer!!.cancel()
            mTimer = null
        }
        else
        {
            mTimer = Timer() // recreate new timer
            mTimer!!.scheduleAtFixedRate(TimeDisplayTimerTask(), 0, INTERVAL)
        }
        return START_STICKY
    }
    override fun onDestroy()
    {
        super.onDestroy()
        if (mTimer != null)  mTimer!!.cancel()
        stop()
        stopForeground(true)
    }
    override fun onTaskRemoved(rootIntent: Intent?)
    {
        super.onTaskRemoved(rootIntent)
        if(EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this)
        stopSelf()
    }
    companion object {
        const val CHANNEL_ID = "samples.notification.devdeeds.com.CHANNEL_ID"
        const val CHANNEL_NAME = "Sample Notification"
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
    internal inner class TimeDisplayTimerTask : TimerTask() {
        override fun run() {
            mHandler.post(Runnable {
                updatelocationtoservice()
                checkingxmpplistener()
            })
        }
    }
    private fun getNotification(context: Context,title:String,message:String)
    {
//        createChannel(context)
////        val notifyIntent = Intent(context, mianscreenpage::class.java)
////        notifyIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
////        val pendingIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//        {
//            mNotification = Notification.Builder(context, CHANNEL_ID)
//                    .setContentIntent(pendingIntent)
//                    .setSmallIcon(R.drawable.appicons)
//                    .setAutoCancel(true)
//                    .setContentTitle(title)
//                    .setStyle(Notification.BigTextStyle()
//                            .bigText(message))
//                    .setContentText(message).build()
//        }
//        else
//        {
//            mNotification = Notification.Builder(context)
//                    .setContentIntent(pendingIntent)
//                    .setSmallIcon(R.drawable.appicons)
//                    .setAutoCancel(true)
//                    .setContentTitle(title)
//                    .setStyle(Notification.BigTextStyle()
//                            .bigText(message))
//                    .setContentText(message).build()
//        }
//        startForeground(999, mNotification)
    }
    private fun initConnection()
    {
//        if (mConnection == null) mConnection = RoosterConnection(this)
//        try {
//            mConnection!!.connect()
//        } catch (e: IOException) {
//            stopSelf()
//        } catch (e: SmackException) {
//            stopSelf()
//        } catch (e: XMPPException) {
//            stopSelf()
//        }
    }
    fun start()
    {
        if (!mActive) {
            mActive = true
            if (mThread == null || !mThread!!.isAlive()) {
                mThread = Thread(Runnable {
                    Looper.prepare()
                    mTHandler = Handler()
                    initConnection()
                    Looper.loop()
                })
                mThread!!.start()
            }
        }
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
    }
    fun stop() {
        mActive = false
//        mTHandler!!.post(Runnable { if (mConnection != null) mConnection!!.disconnect() })
//        if(EventBus.getDefault().isRegistered(this))
//            EventBus.getDefault().unregister(this)
    }
    private fun updatelocationtoservice()
    {
        EventBus.getDefault().post(IntentServiceResult(Activity.RESULT_OK, "loadme", "loadme"))
        mSessionManager = SessionManager(applicationContext)
        val intent = Intent(applicationContext, updatelocationtoserverapi::class.java)
        intent.putExtra(applicationContext.getString(R.string.intent_putextra_api_key), applicationContext.getString(R.string.onlinestatuysupdate))
        intent.putExtra("onlinelatitude", mSessionManager!!.getOnlineLatitiude())
        intent.putExtra("onlinelongitude", mSessionManager!!.getOnlineLongitude())
        intent.putExtra("onlinebearing", mSessionManager!!.getOnlineBEaring())
        applicationContext.startService(intent)
    }
    fun checkingxmpplistener()
    {
        val job = JSONObject()
        Log.d("mchecking2021","otpchecking")
        job.put("action", "xmppactivecheck")

//        if (mConnection != null)
//        {
//
//            mConnection!!.trackingemiteer(job.toString(),mSessionManager!!.getDriverId()+"@"+mSessionManager!!.getxmpp_host_name())
//            Log.d("mchecking2011",job.toString())
//
//        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun doThis(intentServiceResult: TripIntentServiceResult) {
        Log.d("mchecking20244s",intentServiceResult.message)

//        if (mConnection != null)
//        {
//
//            var message: String = intentServiceResult.message
//            var jid: String = intentServiceResult.jid
//            mConnection!!.tripupdatemitter(message,jid)
//        }
    }

}