package com.mauto.chd.backgroundservices

import android.app.*
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.location.Location
import android.os.*
import android.util.Log
import com.mauto.chd.R
import com.mauto.chd.SessionManagerPackage.LanguageSessionManager
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.clickableInterface.TripIntentServiceResult
import com.mauto.chd.commonutils.AppUtils
import com.mauto.chd.data.onridelatandlong.onrideDbHelper
//import com.cabilyhandyforalldinedoo.chd.ui.MainPage.requestmianscreen
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
//import org.jivesoftware.smack.SmackException
//import org.jivesoftware.smack.XMPPException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
class FService : Service()
{
    private var mThread: Thread? = null
    private var mTHandler: Handler? = null
    var mHelper: onrideDbHelper? = null
    var dataBase: SQLiteDatabase? = null
    var ride_status = ""
    var starcount:Int =0
    private var mActive: Boolean = false
//    private var mConnection: RoosterConnection? = null
    lateinit private var mSessionManager: SessionManager
    lateinit private var langmSessionManager: LanguageSessionManager
    private val mHandler = Handler()
    private var mTimer: Timer? = null
    private val myBinder = MyLocalBinder()
    val INTERVAL: Long = 2000
    var rideid:String=""
    var useridtosend:String = ""
    var from:String=""
    lateinit var mNotification:Notification
    override fun onBind(intent: Intent): IBinder?
    {
        return myBinder
    }
    inner class MyLocalBinder : Binder()
    {
        fun getService() : FService {
            return this@FService
        }
    }
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int
    {
        //Start Foreground
          var extras: Bundle = intent.getExtras()!!;
          if(extras != null)
          {
              mSessionManager = SessionManager(applicationContext!!)
              langmSessionManager = LanguageSessionManager(applicationContext!!)
              mHelper= onrideDbHelper(applicationContext)
              from= extras.get("From") as String
              rideid = extras.get("rideid") as String
              ride_status = extras.get("ride_status") as String
              useridtosend = extras.get("useridtosend") as String
              dataBase=mHelper!!.getWritableDatabase()
              val counts = mHelper!!.getProfilesCount()
              if(counts > 2)
              {
                  var totalkm:Float=(addallrecord()/1000)
                  if(langmSessionManager!!.getlanguageoption().equals("1"))
                      getNotification(applicationContext,from,getString(R.string.travelletamil)+" "+String.format("%.2f",totalkm)+" "+getString(R.string.kms))
                  else  if(langmSessionManager!!.getlanguageoption().equals("2"))
                      getNotification(applicationContext,from,getString(R.string.travelle)+" "+String.format("%.2f",totalkm)+" "+getString(R.string.kms))
                  else
                      getNotification(applicationContext,from,getString(R.string.travelleurudu)+" "+String.format("%.2f",totalkm)+" "+getString(R.string.kms))
              }
              else
                  if(langmSessionManager!!.getlanguageoption().equals("1"))
                    getNotification(applicationContext,from,getString(R.string.tripstatreedtamil))
              else   if(langmSessionManager!!.getlanguageoption().equals("2"))
                      getNotification(applicationContext,from,getString(R.string.tripstatreed))
              else
                      getNotification(applicationContext,from,getString(R.string.tripstatreedurudu))

              if (mTimer != null) run {
                  mTimer!!.cancel()
                  mTimer = null
              }
              else
              {
                  mTimer = Timer() // recreate new timer
                  mTimer!!.scheduleAtFixedRate(TimeDisplayTimerTask(), 0, INTERVAL)
              }
              start()
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
    //timer part for notification change
    internal inner class TimeDisplayTimerTask : TimerTask() {
        override fun run() {
            // run on another thread
            mHandler.post(Runnable {
                if(mSessionManager != null)
                {
                    var ctlat:Double  =   mSessionManager.getTrackingLat().toDouble()
                    var ctlong:Double =  mSessionManager.getTrackingLong().toDouble()
                    var bearing:Double =  mSessionManager.getbearing().toDouble()
                    var onridecount:Int =  mSessionManager.getOnrideCount().toInt()
                    Log.d("vjeirejrjj3w", ctlat.toString())
                    if(starcount < onridecount)
                    {
                        starcount = onridecount
                        if(!rideid.equals(""))
                        {
                            if(ctlat != 0.0 && ctlong != 0.0)
                            savelatandlong(ctlat.toString(),ctlong.toString(),bearing.toString())
                        }
                    }
                }
            })
        }
    }
    fun savelatandlong(latitude:String,logitude:String,bearing:String)
    {
        val aCalendar = Calendar.getInstance()
        val aDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val aCurrentDate = aDateFormat.format(aCalendar.getTime())
        dataBase=mHelper!!.getWritableDatabase()
        val counts = mHelper!!.getProfilesCount()
        if(counts == 0)
        {
            if(latitude.toDouble() != 0.0 && logitude.toDouble() != 0.0)
            {
                var values: ContentValues = ContentValues()
                values.put(onrideDbHelper.rideid,rideid)
                values.put(onrideDbHelper.latitude,latitude )
                values.put(onrideDbHelper.longitude,logitude )
                values.put(onrideDbHelper.km,"0")
                values.put(onrideDbHelper.time,aCurrentDate)
                values.put(onrideDbHelper.stattus,"0")
                dataBase!!.insert(onrideDbHelper.TABLE_NAME, null, values)
            }
        }
        else
        {
            if(latitude.toDouble() != 0.0 && logitude.toDouble() != 0.0)
            {
                var retrunkm =getlastrecordfromdb(latitude,logitude)
                if(retrunkm > 50)
                {
                    var values: ContentValues = ContentValues()
                    values.put(onrideDbHelper.rideid,rideid)
                    values.put(onrideDbHelper.latitude,latitude )
                    values.put(onrideDbHelper.longitude,logitude )
                    values.put(onrideDbHelper.km,retrunkm)
                    values.put(onrideDbHelper.time,aCurrentDate)

                    if(retrunkm > 1000)
                        values.put(onrideDbHelper.stattus,"1")
                    else
                        values.put(onrideDbHelper.stattus,"0")

                    dataBase!!.insert(onrideDbHelper.TABLE_NAME, null, values)
                    var totalkm:Float=(addallrecord()/1000)

                    if(langmSessionManager!!.getlanguageoption().equals("1"))
                        getNotification(applicationContext,from,getString(R.string.travelletamil)+" "+String.format("%.2f",totalkm)+" "+getString(R.string.kms))
                     else   if(langmSessionManager!!.getlanguageoption().equals("2"))
                        getNotification(applicationContext,from,getString(R.string.travelle)+" "+String.format("%.2f",totalkm)+" "+getString(R.string.kms))
                    else
                    getNotification(applicationContext,from,getString(R.string.travelleurudu)+" "+String.format("%.2f",totalkm)+" "+getString(R.string.kms))


                    val job = JSONObject()
                    job.put("action", "driver_loc")
                    job.put("latitude", latitude)
                    job.put("longitude", logitude)
                    job.put("ride_id",rideid)
                    job.put("bearing",bearing)
                    job.put("user_id", useridtosend)
                    job.put("ride_status", ride_status)
                    job.put("driver_id", mSessionManager.getDriverId())
                    job.put("chatID", useridtosend+"@"+mSessionManager.getxmpp_host_name())



//                    if (mConnection != null) {
//                        Log.d("mcheckingssdsds","otpchecking")
//
//                        mConnection!!.trackingemiteer(job.toString(),useridtosend+"@"+mSessionManager.getxmpp_host_name())
//                    }
                }
            }
        }
    }
    fun getlastrecordfromdb(latitude:String,logitude:String):Float
    {
        var getlastlat:Double = 0.0
        var getlastlong:Double = 0.0
        var distance:Float = 0f
        dataBase = mHelper!!.getWritableDatabase();
        var mCursor: Cursor = dataBase!!.rawQuery("SELECT * FROM " + onrideDbHelper.TABLE_NAME+" WHERE rideid='"+rideid+"' ORDER BY id DESC LIMIT 1", null);
        if (mCursor.moveToFirst()) {
            do {
                getlastlat= mCursor.getString(mCursor.getColumnIndex(onrideDbHelper.latitude)).toDouble()
                getlastlong= mCursor.getString(mCursor.getColumnIndex(onrideDbHelper.longitude)).toDouble()
                var  id= mCursor.getString(mCursor.getColumnIndex(onrideDbHelper.KEY_ID)).toString()
                Log.e("Last Reocrd--->",""+id)
            } while (mCursor.moveToNext());
        }
        mCursor.close()
        if(getlastlat != 0.0 && getlastlong != 0.0)
        {
            val startPoint = Location("locationA")
            startPoint.setLatitude(latitude.toDouble())
            startPoint.setLongitude(logitude.toDouble())
            val endPoint = Location("locationA")
            endPoint.setLatitude(getlastlat)
            endPoint.setLongitude(getlastlong)
             distance = startPoint.distanceTo(endPoint)
        }
        return distance
    }
    fun addallrecord():Float
    {
        var addkm:Float = 0f
        dataBase = mHelper!!.getWritableDatabase();
        var mCursor: Cursor = dataBase!!.rawQuery("SELECT * FROM " + onrideDbHelper.TABLE_NAME+" WHERE rideid='"+rideid+"'", null);
        if (mCursor.moveToFirst()) {
            do {
                var addinnear= mCursor.getString(mCursor.getColumnIndex(onrideDbHelper.km)).toFloat()
                var statuss= mCursor.getString(mCursor.getColumnIndex(onrideDbHelper.stattus)).toString()
                addkm = addkm+addinnear
                if(statuss.equals("1"))
                {
                    var coloumnid= mCursor.getString(mCursor.getColumnIndex(onrideDbHelper.KEY_ID)).toString()
                     if(AppUtils.isNetworkConnecteddfromservice(applicationContext!!))
                     {
                         fillmissingapi(applicationContext,coloumnid,rideid)
                     }
                }
            } while (mCursor.moveToNext());
        }
        mCursor.close()
        return addkm
    }
    fun fillmissingapi(mContext: Context,coloumnid:String,rideid:String)
    {
        if (!isServiceRunning(fillroutecall::class.java))
        {
            val serviceClass = fillroutecall::class.java
            val intent = Intent(mContext, serviceClass)
            intent.putExtra("coloumnid",coloumnid)
            intent.putExtra("rideid",rideid)
            mContext.startService(intent)
        }
    }
    private fun isServiceRunning(serviceClass: Class<*>): Boolean
    {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
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
        Log.d("mchecking20233s",intentServiceResult.message)

//        if (mConnection != null)
//        {
//
//            var message: String = intentServiceResult.message
//            var jid: String = intentServiceResult.jid
//            mConnection!!.tripupdatemitter(message,jid)
//        }
    }
}