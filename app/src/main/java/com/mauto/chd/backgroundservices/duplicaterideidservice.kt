package com.mauto.chd.backgroundservices


import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import com.mauto.chd.data.duplicaterideid.duplicaterideidRecord
import com.mauto.chd.data.duplicaterideid.duplicaterideidRepository
//import com.cabilyhandyforalldinedoo.chd.ui.MainPage.riderequestpage


class duplicaterideidservice : IntentService("ApiHit")
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

    override fun onHandleIntent(intent: Intent?)
    {

        if (intent?.hasExtra("ride_id")!!)
        {
            ride_id = intent?.getStringExtra("ride_id")!!
            time_stamp = intent?.getStringExtra("time_stamp")!!
            category_name = intent?.getStringExtra("category_name")!!
            timer = intent?.getStringExtra("timer")!!
            ratting = intent?.getStringExtra("ratting")!!
            duration = intent?.getStringExtra("duration")!!
            distance = intent?.getStringExtra("distance")!!
            pickup_lat = intent?.getStringExtra("pickup_lat")!!
            pickup_lon = intent?.getStringExtra("pickup_lon")!!
            pickup = intent?.getStringExtra("pickup")!!
            category= intent?.getStringExtra("category")!!

            val repository: duplicaterideidRepository = duplicaterideidRepository(application,ride_id,time_stamp)
            if(repository.getAllTodoList().size == 0)
            {
                var todoRecord: duplicaterideidRecord? = null
                val comid = if (todoRecord != null) todoRecord?.id else null
                val todo = duplicaterideidRecord(id = comid, rideid = ride_id, timestamp = time_stamp)
                repository.saveTodo(todo)
//
//                val intent1 = Intent(applicationContext, riderequestpage::class.java)
//                intent1.putExtra("ride_id", ride_id)
//                intent1.putExtra("time_stamp", time_stamp)
//                intent1.putExtra("category_name", category_name)
//                intent1.putExtra("timer", timer)
//                intent1.putExtra("ratting", ratting)
//                intent1.putExtra("duration", duration)
//                intent1.putExtra("distance", distance)
//                intent1.putExtra("pickup_lat", pickup_lat)
//                intent1.putExtra("pickup_lon", pickup_lon)
//                intent1.putExtra("pickup", pickup)
//                intent1.putExtra("category", category)
//                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                startActivity(intent1)
            }

        }
    }


}