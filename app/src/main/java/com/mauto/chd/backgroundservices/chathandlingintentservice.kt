package com.mauto.chd.backgroundservices


import android.app.IntentService
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.IBinder
import com.mauto.chd.event_bus_connection.ChatIntentServiceResult
import com.mauto.chd.data.chatdb.DbHelper
//import com.cabilyhandyforalldinedoo.chd.ui.MainPage.riderequestpage
import org.greenrobot.eventbus.EventBus


class chathandlingintentservice : IntentService("ApiHit")
{

    var desc:String = ""
    var sender_ID:String = ""
    var time_stamp:String = ""
    var ride_id: String = ""
    private var mHelper: DbHelper? = null
    private var dataBase: SQLiteDatabase? = null

    override fun onBind(intent: Intent): IBinder?
    {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onHandleIntent(intent: Intent?)
    {

        if (intent?.hasExtra("ride_id")!!)
        {
            desc = intent?.getStringExtra("desc")!!
            sender_ID = intent?.getStringExtra("sender_ID")!!
            time_stamp = intent?.getStringExtra("timestamp")!!
            ride_id = intent?.getStringExtra("ride_id")!!

            savemessagetodb(ride_id,desc,sender_ID,time_stamp)

        }
    }

    fun savemessagetodb(rideid:String,message:String,senderid:String,timestamp:String)
    {
        mHelper= DbHelper(applicationContext)
        dataBase=mHelper!!.getWritableDatabase()
        var values: ContentValues = ContentValues()
        values.put(DbHelper.rideid,rideid)
        values.put(DbHelper.message,message )
        values.put(DbHelper.senderid,  "others" )
        values.put(DbHelper.timestamp,timestamp)
        values.put(DbHelper.status,"0")
        if(checkreocrdexist(timestamp,rideid) == 0)
        {
            dataBase!!.insert(DbHelper.TABLE_NAME, null, values)
            EventBus.getDefault().post(ChatIntentServiceResult(message, senderid, timestamp,rideid))
        }
    }
    fun checkreocrdexist(timestamp:String,rideid:String):Int
    {
        mHelper= DbHelper(applicationContext)
        var valueexist:Int=0
        dataBase = mHelper!!.getWritableDatabase();
        var mCursor: Cursor = dataBase!!.rawQuery("SELECT * FROM " + DbHelper.TABLE_NAME+" WHERE rideid='"+rideid+"' AND timestamp='"+timestamp+"'", null);
        if (mCursor.moveToFirst()) {
            do {
                valueexist=1
            } while (mCursor.moveToNext());
        }
        mCursor.close()
        return valueexist
    }
}