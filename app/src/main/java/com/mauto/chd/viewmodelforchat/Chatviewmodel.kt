package com.mauto.chd.viewmodelforchat

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mauto.chd.R
import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.clickableInterface.TripIntentServiceResult
import com.mauto.chd.data.chatdb.DbHelper
import org.greenrobot.eventbus.EventBus
//import org.jivesoftware.smack.SmackException
//import org.jivesoftware.smack.chat2.ChatManager
//import org.jivesoftware.smack.packet.Message
import org.json.JSONObject
//import org.jxmpp.jid.EntityBareJid
//import org.jxmpp.jid.impl.JidCreate
//import org.jxmpp.stringprep.XmppStringprepException


class Chatviewmodel(application: Application) : AndroidViewModel(application)
{


    private val closepage = MutableLiveData<Int>()
    private val sendmessage = MutableLiveData<Int>()
    private val templateclick = MutableLiveData<Int>()



    fun closemodelobserver(): MutableLiveData<Int>
    {
        return closepage
    }
    fun templateclickobserver(): MutableLiveData<Int>
    {
        return templateclick
    }

    fun sendmessageobserver(): MutableLiveData<Int>
    {
        return sendmessage
    }
    fun closeoperation()
    {
        closepage.value = 1
    }
    fun sendmessage()
    {
        sendmessage.value = 1
    }
    fun tempateclick()
    {
        templateclick.value = 1
    }
    fun sendmessagebasedonurl(mContext:Context,message:String,timestamp:String,rideid:String,useridtosend:String)
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.chatmessageurl))
        intent.putExtra("message", message)
        intent.putExtra("timestamp", timestamp)
        intent.putExtra("rideid", rideid)
        intent.putExtra("useridtosend", useridtosend)
        mContext.startService(intent)
    }
    fun sendxmppmessage(mcontext:Context,jid:String,useridtosend:String,rideid:String,ts:String,message:String)
    {
        val job = JSONObject()
        job.put("action", "dectar_chat")
        job.put("type", "OTHER")
        job.put("sender_ID", useridtosend)
        job.put("ride_id", rideid)
        job.put("desc", message)
        job.put("driver_image", "")
        job.put("driver_name", "")
        job.put("voice_timing", ts)
        job.put("time_stamp", ts)
        EventBus.getDefault().post(TripIntentServiceResult(job.toString(),jid))
    }
    fun updateallstatsu(mContext:Context,rideid:String)
    {
        var mHelper: DbHelper? = null
        var dataBase: SQLiteDatabase? = null
        mHelper= DbHelper(mContext)
        var values: ContentValues = ContentValues()
        values.put(DbHelper.status,"1")
        dataBase = mHelper!!.getWritableDatabase();
        var mCursor: Cursor = dataBase!!.rawQuery("SELECT * FROM " + DbHelper.TABLE_NAME+" WHERE rideid='"+rideid+"'", null);
        if (mCursor.moveToFirst()) {
            do {
                var id:Int= mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_ID)).toInt()
                dataBase!!.update(DbHelper.TABLE_NAME, values, DbHelper.KEY_ID+"="+id, null);
            } while (mCursor.moveToNext());
        }
        mCursor.close()
    }
    fun savemessagetodb(mContext:Context,rideid:String,message:String,senderid:String,timestamp:String)
    {
        var mHelper: DbHelper? = null
        var dataBase: SQLiteDatabase? = null
        mHelper= DbHelper(mContext)
        dataBase=mHelper!!.getWritableDatabase()
        var values:ContentValues= ContentValues()
        values.put(DbHelper.rideid,rideid)
        values.put(DbHelper.message,message )
        values.put(DbHelper.senderid,senderid )
        values.put(DbHelper.timestamp,timestamp)
        values.put(DbHelper.status,"1")
        dataBase!!.insert(DbHelper.TABLE_NAME, null, values)
    }
}