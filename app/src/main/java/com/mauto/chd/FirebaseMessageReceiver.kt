package com.mauto.chd
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import mauto_customer.ui.MainscreenCustomers
const val channelId = "com.mauto.customer"
const val channelName ="com.mauto.customer"

class FirebaseMessageReceiver
    : FirebaseMessagingService(){


    override fun onMessageReceived(remotemessage: RemoteMessage) {
        Log.d("firbase","$remotemessage")
        if(remotemessage.getNotification() != null){
            generateNotification(remotemessage.notification!!.title!!,remotemessage.notification!!.body!!)
        }

    }


    fun getRemoteView(title:String,message: String):RemoteViews
    {
        val remoteView = RemoteViews("com.mauto.chd",R.layout.notification)

        remoteView.setTextViewText(R.id.title, title)
        remoteView.setTextViewText(R.id.message, message)
        remoteView.setImageViewResource(R.id.icon,R.drawable.notication )

        return remoteView
    }
    fun generateNotification(title:String,message: String){
        val intent = Intent(this, MainscreenCustomers::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_NEW_TASK)
        deepLink(message)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        var builder: NotificationCompat.Builder= NotificationCompat
            .Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.logo_new)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000,1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title,message))


        val  notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= VERSION_CODES.O){
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)

        }
        notificationManager.notify(0,builder.build())

    }


    private fun deepLink(txt: String) {
        val intent1: Intent
        when (txt) {
            "ScreenA" -> {
                intent1 = Intent(Intent.ACTION_VIEW)
                intent1.setPackage(packageName)
                intent1.data = Uri.parse("mauto://gotowallet")
                startActivity(intent1)
            }

            "ScreenB" -> {
                intent1 = Intent(Intent.ACTION_VIEW)
                intent1.setPackage(packageName)
                intent1.data = Uri.parse("mauto://gotopayment")
                startActivity(intent1)
            }
        }


    }


}