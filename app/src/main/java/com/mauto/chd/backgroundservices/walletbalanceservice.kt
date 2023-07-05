package com.mauto.chd.backgroundservices


import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import android.util.Log
//import com.cabilyhandyforalldinedoo.chd.ui.MainPage.riderequestpage
//import com.cabilyhandyforalldinedoo.chd.ui.MainPage.walletbalancenotify


class walletbalanceservice : IntentService("ApiHit")
{


    var ride_id: String = ""
    var time_stamp:String = ""
    var category_name:String = ""
    var timer:String = ""
    var ratting:String = ""
    var duration:String = ""
    var distance:String = ""
    var pickup_lat:String = ""
    var pickup_lon:String = ""
    var pickup:String = ""
    var category:String = ""


    override fun onBind(intent: Intent): IBinder?
    {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onHandleIntent(intent: Intent?) {


Log.d("checking2022","walletbalance202")


//                val intent1 = Intent(applicationContext, walletbalancenotify::class.java)
//                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                startActivity(intent1)
            }

        }



