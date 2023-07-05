package com.mauto.chd.backgroundservices


import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import com.mauto.chd.R
import com.mauto.chd.SessionManagerPackage.SessionManager
//import com.google.firebase.messaging.FirebaseMessaging
import com.mindorks.kotnetworking.KotNetworking
import com.mindorks.kotnetworking.common.Priority
import java.util.*


class fcmsendingprocess : IntentService("ApiHit")
{


    val FCM_API = "https://fcm.googleapis.com/fcm/send"
    val contentType = "application/json"
    lateinit private var mSessionManager: SessionManager
    var rideid = ""
    var ridestatus = ""
    var actions = ""
    var messages = ""
    var fcmtoken = ""
    var useridtosend=""

    override fun onBind(intent: Intent): IBinder?
    {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onHandleIntent(intent: Intent?)
    {

        mSessionManager = SessionManager(applicationContext!!)
        if (intent?.hasExtra("rideid")!! && intent?.hasExtra("ridestatus")!! && intent?.hasExtra("actions")!! && intent?.hasExtra("messages")!! && intent?.hasExtra("fcmtoken")!!)
        {
            rideid = intent?.getStringExtra("rideid")!!
             ridestatus = intent?.getStringExtra("ridestatus")!!
             actions = intent?.getStringExtra("actions")!!
             messages = intent?.getStringExtra("messages")!!
             fcmtoken = intent?.getStringExtra("fcmtoken")!!
//             FirebaseMessaging.getInstance().subscribeToTopic("/topics/Enter_your_topic_name")
             sendNotification()
        }
    }


    private fun sendNotification()
    {
        var header = HashMap<String, String>()
        header.put("Authorization",applicationContext.getString(R.string.fcmkey))
        header.put("Content-Type",contentType)
        header.put("to",fcmtoken)
        header.put("data",actions+","+rideid+","+ridestatus+","+messages+","+"")
        KotNetworking.post(FCM_API)
                .addBodyParameter(header)
                .addHeaders(header)
                .setTag(this)
                .setPriority(Priority.HIGH)
                .build()
                .getAsString { response, error ->
                    if (error != null)
                    {

                    }
                    else
                    {

                    }
                }
    }






}