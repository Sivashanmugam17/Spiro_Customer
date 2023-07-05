package com.mauto.chd.backgroundservices

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.*
import android.util.Log
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.clickableInterface.TripIntentServiceResult
//import com.cabilyhandyforalldinedoo.chd.xmpp.RoosterConnection
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
//import org.jivesoftware.smack.SmackException
//import org.jivesoftware.smack.XMPPException
import org.json.JSONObject
import java.util.*

class AService : Service()
{
    private var mActive: Boolean = false
    private var mThread: Thread? = null
    private var mTHandler: Handler? = null
//    private var mConnection: RoosterConnection? = null
    lateinit private var mSessionManager: SessionManager
    private val mHandler = Handler()
    private val myBinder = MyLocalBinder()
    var rideid:String=""
    var userid:String=""
    var ride_status = ""
    var pickuplocation:String=""
    var from:String=""
    private var mTimer: Timer? = null
    val INTERVAL: Long = 5000
    lateinit var mNotification:Notification
    override fun onBind(intent: Intent): IBinder?
    {
        return myBinder
    }

    inner class MyLocalBinder : Binder()
    {
        fun getService() : AService {
            return this@AService
        }
    }
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int
    {
          var extras: Bundle = intent.getExtras()!!;
          if(extras != null) {
              mSessionManager = SessionManager(applicationContext!!)
              from= extras.get("From") as String
              rideid = extras.get("rideid") as String
              userid = extras.get("userid") as String
              ride_status = extras.get("ride_status") as String
              pickuplocation = extras.get("pickuplocation") as String
              start()
              getNotification(applicationContext,from,pickuplocation)

              if (mTimer != null) run {
                  mTimer!!.cancel()
                  mTimer = null
              }
              else
              {
                  mTimer = Timer() // recreate new timer
                  mTimer!!.scheduleAtFixedRate(TimeDisplayTimerTask(), 0, INTERVAL)
              }
          }
        return START_STICKY
    }
    override fun onDestroy()
    {
        super.onDestroy()
        if (mTimer != null) {
            mTimer!!.cancel()
        }
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

    //timer part for notification change
    internal inner class TimeDisplayTimerTask : TimerTask() {
        override fun run() {
            // run on another thread
            mHandler.post(Runnable {
                if(mSessionManager != null)
                {
                    var ctlat:Double  =   mSessionManager.getTrackingLat().toDouble()
                    var ctlong:Double =  mSessionManager.getTrackingLong().toDouble()
                    Log.d("erwer5345", mSessionManager.getTrackingLat().toDouble().toString())
                    if(!rideid.equals(""))
                    {
                        if(ctlat != 0.0 && ctlong != 0.0)
                        {
                            val job = JSONObject()
                            job.put("action", "driver_loc")
                            job.put("latitude", ctlat.toString())
                            job.put("longitude", ctlong.toString())
                            job.put("ride_id",rideid)
                            job.put("user_id", userid)
                            job.put("ride_status", ride_status)
                            job.put("bearing", mSessionManager.getbearing().toDouble())
                            job.put("driver_id", mSessionManager.getDriverId())
                            job.put("chatID", userid+"@"+mSessionManager.getxmpp_host_name())

//

//                            if (mConnection != null) {
//                                mConnection!!.trackingemiteer(job.toString(),userid+"@"+mSessionManager.getxmpp_host_name())
//                            }

                        }
                    }
                }
            })
        }
    }
    private fun getNotification(context: Context,content1:String,content2:String)
    {
//        createChannel(context)
//        val notifyIntent = Intent(context, requestmianscreen::class.java)
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
//                    .setContentIntent(pendingIntent)
//                    .setSmallIcon(R.drawable.appicon)
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
//                    .setSmallIcon(R.drawable.appicon)
//                    .setAutoCancel(true)
//                    .setContentTitle(title)
//                    .setStyle(Notification.BigTextStyle()
//                            .bigText(message))
//                    .setContentText(message).build()
//        }
//        startForeground(999, mNotification)
    }



    private fun initConnection() {
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

    fun start() {
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
        if(EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun doThis(intentServiceResult: TripIntentServiceResult)
    {
        Log.d("mchecking2021s",intentServiceResult.message)

//        if (mConnection != null) {
//            var message: String = intentServiceResult.message
//            var jid: String = intentServiceResult.jid
//            mConnection!!.tripupdatemitter(message,userid+"@"+mSessionManager.getxmpp_host_name())
//        }

    }
}