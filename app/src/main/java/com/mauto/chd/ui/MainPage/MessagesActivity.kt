//package com.cabilyhandyforalldinedoo.chd.ui.MainPage
//
////import com.google.firebase.database.ktx.database
////import com.google.firebase.ktx.Firebase
//
//import android.app.Activity
//import android.content.Intent
//import android.database.Cursor
//import android.database.sqlite.SQLiteDatabase
//import android.graphics.Bitmap
//import android.graphics.drawable.Drawable
//import android.net.Uri
//import android.os.Bundle
//import android.util.Log
//import android.view.Gravity
//import android.view.View
//import android.widget.FrameLayout
//import android.widget.TextView
//import androidx.databinding.DataBindingUtil
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import androidx.recyclerview.widget.LinearLayoutManager
//import cabily.handyforall.dinedoo.R
//import com.bumptech.glide.Glide
//import com.bumptech.glide.request.RequestOptions
//import com.bumptech.glide.request.target.CustomTarget
//import com.bumptech.glide.request.transition.Transition
//import com.cabilyhandyforalldinedoo.chd.EventBusConnection.ChatIntentServiceResult
//import com.cabilyhandyforalldinedoo.chd.Modal.TextMessage
//import com.cabilyhandyforalldinedoo.chd.SessionManagerPackage.SessionManager
//import com.cabilyhandyforalldinedoo.chd.ViewModelTrackingPage.CancellationReasonDataModel
//import com.cabilyhandyforalldinedoo.chd.Viewmodelforchat.chatviewmodel
//import com.cabilyhandyforalldinedoo.chd.adaptersofchd.MessagesAdapter
//import com.cabilyhandyforalldinedoo.chd.adaptersofchd.cancelreasonadapter
//import com.cabilyhandyforalldinedoo.chd.clickableInterface.cancelreasonclick
//import com.cabilyhandyforalldinedoo.chd.commonutils.AppUtils
//import com.cabilyhandyforalldinedoo.chd.data.chatdb.DbHelper
//import com.cabilyhandyforalldinedoo.chd.ui.registeration.LocaleAwareCompatActivity
//import com.cabilyhandyforalldinedoo.chd.xmpp.RoosterConnection
//import com.google.android.material.bottomsheet.BottomSheetDialog
//import com.google.android.material.snackbar.Snackbar
//import com.google.firebase.database.*
//
//import kotlinx.android.synthetic.main.requestpagedfa.*
//import org.greenrobot.eventbus.EventBus
//import org.greenrobot.eventbus.Subscribe
//import org.greenrobot.eventbus.ThreadMode
//import java.util.*
//import java.util.stream.Collectors.toMap
//import kotlin.collections.ArrayList
//import kotlin.math.log
//
//
//class MessagesActivity : LocaleAwareCompatActivity()
//{
//    private var mHelper: DbHelper? = null
//    private var dataBase: SQLiteDatabase? = null
////    lateinit var binding: ActivityMessagesBinding
//    private lateinit var messagesAdapter: MessagesAdapter
//    lateinit var mess:ArrayList<TextMessage>
//    private lateinit var viewModel: chatviewmodel
//    private lateinit var mContext: Activity
//    var driverid:String=""
//    var rideid:String=""
//    var username:String=""
//    var sConnectionState: RoosterConnection.ConnectionState? = null
//    var userimagee:String=""
//    var useridtosend:String=""
//    var messagestatus:String=""
//    var hidebuttons:Int = 0
//    var customerphno:String = ""
//
//
//    var messagetemplate : BottomSheetDialog ? =null
//    lateinit private var mSessionManager: SessionManager
//    lateinit var myRef : DatabaseReference
//    override fun onCreate(savedInstanceState: Bundle?)
//    {
//        super.onCreate(savedInstanceState)
////        binding = DataBindingUtil.setContentView(this, R.layout.activity_messages)
//        mContext = this@MessagesActivity
//        intlizerpart()
//        getExtravalue()
//        setdataonview()
//        initlizeadapter()
//        initviewmodel()
//        binding.setViewModel(viewModel)
//        callcustomer.setOnClickListener { _ -> callcustomer() }
//
//
////        val database =  FirebaseDatabase.getInstance().getReference();
//
//
//        myRef = FirebaseDatabase.getInstance().getReference("RideChat/"+mSessionManager.gerrideid())
//
//        send_message_lay.setOnClickListener {
//            if (AppUtils.isNetworkConnected(mContext))
//                sendMessage(enter_message.text.toString())
//            else commsnackbaralert(getString(R.string.failedproblem))
//
////            writeNewPost(enter_message.text.toString(),mSessionManager.getuser_id(),mSessionManager.gerrideid(),mSessionManager.getDriverId(),Calendar.getInstance().toString())
//        }
//        var mJournalEntries: ArrayList<JournalEntry> = ArrayList()
//
////        myRef.addValueEventListener(object : ValueEventListener {
////            override fun onDataChange(dataSnapshot: DataSnapshot) {
////                // This method is called once with the initial value and again
////                // whenever data at this location is updated.
////                val value = dataSnapshot.getValue()
////
////                mess.clear()
////                for (noteSnapshot in dataSnapshot.children) {
////                    val note=TextMessage(
////                            noteSnapshot.child("message").value.toString(),
////                            noteSnapshot.child("receiver_id").value.toString(),
////                            noteSnapshot.child("sender_id").value.toString())
////
////
////                    mess.add(note!!)
////                    Log.i("fhfghfgjhf", "Value is: $note")
////
////                }
////                messagesAdapter.notifyDataSetChanged()
////
////                Log.i("TAyhdG", "Value is: $value")
////            }
////
////            override fun onCancelled(error: DatabaseError) {
////                // Failed to read value
////            }
////        })
//       /* myRef.addChildEventListener(object : ChildEventListener {
//            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
//            }
//
//            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
//                Log.d("jfoi", "onChildChanged: a")
//            }
//
//            override fun onChildRemoved(p0: DataSnapshot) {
//
//            }
//
//            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
//
//            }
//
//            override fun onCancelled(p0: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })*/
//    }
//
//
////    private fun writeNewPost(message: String, receiver_id: String, ride_id: String, sender_id: String,time:String) {
////        // Create new post at /user-posts/$userid/$postid and at
////        // /posts/$postid simultaneously
////        val key = myRef.push().key
////        if (key == null) {
////            return
////        }
////
////        val post = TextMessage(message, ride_id, sender_id,time,receiver_id)
////        val postValues = post.toMap()
////
////        val childUpdates = hashMapOf<String, Any>(
////                "/"+key to postValues
////        )
////
////        myRef.updateChildren(childUpdates).addOnSuccessListener {
////            enter_message.setText("")
////
////        }
////    }
//
//    fun getSampleJournalEntries() {
//        val journalEnrties: MutableList<JournalEntry> = ArrayList()
//        //create the dummy journal
//        val journalEntry1 = JournalEntry()
////        journalEntry1.setTitle("DisneyLand Trip")
////        journalEntry1.setContent("We went to Disneyland today and the kids had lots of fun!")
////        val calendar1 = GregorianCalendar.getInstance()
////        journalEntry1.setDateModified(calendar1.timeInMillis)
//        journalEnrties.add(journalEntry1)
//    }
//
//    fun callcustomer()
//    {
//        if(hidebuttons == 0)
//        {
//
//                val intent =  Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", mSessionManager.getcustomerphno(), null));
//                startActivity(intent);
//
//        }
//    }
//
//
//    fun intlizerpart()
//    {
//        mSessionManager = SessionManager(applicationContext!!)
//        mHelper= DbHelper(this);
//        driverid= mSessionManager!!.getDriverId()
//        mess = ArrayList<TextMessage>()
//    }
//    fun getExtravalue()
//    {
//        rideid = intent?.getStringExtra("rideid").toString()
//        username= intent?.getStringExtra("name").toString()
//        userimagee= intent?.getStringExtra("userimage").toString()
//        useridtosend= intent?.getStringExtra("useridtosend").toString()
//    }
//    fun setdataonview()
//    {
//        setimage(userimagee)
//        usernames.text = username
//    }
//    fun initviewmodel()
//    {
//        viewModel = ViewModelProviders.of(this).get(chatviewmodel::class.java)
//        viewModel.closemodelobserver().observe(this, Observer {
//            finish()
//        })
//        viewModel.templateclickobserver().observe(this, Observer {
//            templatebootomsheet()
//        })
//        viewModel.sendmessageobserver().observe(this, Observer {
//            if (enter_message.length() > 0) {
//                if (AppUtils.isNetworkConnected(mContext)) sendMessage(enter_message.text.toString())
//                else commsnackbaralert(getString(R.string.failedproblem))
//            } else commsnackbaralert(getString(R.string.entermessagebefore))
//        })
//        retrivelistfromdb()
//    }
//    private fun sendMessage(message: String)
//    {
//        var tsLong:Long = System.currentTimeMillis()/1000;
//        var ts:String = tsLong.toString()
////        viewModel.sendmessagebasedonurl(mContext, message, ts, rideid, `useridtosend`)
//        savemessagetodb(message, driverid, ts)
//        val stringtoid = useridtosend + "@" + mSessionManager.getxmpp_host_name()
//       var driverId= mSessionManager.getDriverId()
//        Log.d("dgfgdhf",driverId)
//
//        viewModel.sendxmppmessage(mContext, stringtoid, driverId, rideid, ts, message)
//        Log.d("cghec",driverId)
//
//        scrollToBottom()
//    }
//    private fun scrollToBottom()
//    {
//        messages.scrollToPosition(messagesAdapter.itemCount - 1)
//    }
//    fun initlizeadapter()
//    {
//        val layoutMgr = LinearLayoutManager(this)
//        layoutMgr.stackFromEnd = true
//        messages.layoutManager = layoutMgr
//        messagesAdapter = MessagesAdapter(mess, driverid)
//        messages.adapter = messagesAdapter
//    }
//    fun setimage(userimagee: String)
//    {
//        if(userimagee.startsWith("http"))
//        {
//            Glide.with(mContext)
//                    .asBitmap()
//                    .apply(RequestOptions().override(60, 60))
//                    .load(userimagee)
//                    .into(object : CustomTarget<Bitmap>() {
//                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                            runOnUiThread {
//                                driverphoto.setImageBitmap(resource)
//                            }
//                        }
//
//                        override fun onLoadCleared(placeholder: Drawable?) {
//                        }
//                    })
//        }
//    }
//    override fun onResume()
//    {
//        super.onResume()
//        if(!EventBus.getDefault().isRegistered(this))
//            EventBus.getDefault().register(this)
//    }
//    override fun onDestroy()
//    {
//        super.onDestroy()
//        if(EventBus.getDefault().isRegistered(this))
//            EventBus.getDefault().unregister(this)
//    }
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun doThis(intentServiceResult: ChatIntentServiceResult)
//    {
//        var desc: String = intentServiceResult.desc
//        var sender_ID: String = "others"
//        var timestamp: String = intentServiceResult.timestamp
//        var ride_id: String = intentServiceResult.ride_id
//        if(ride_id.equals(rideid))
//        {
//            val textMessage = TextMessage(rideid, desc, sender_ID, timestamp, "1")
//            enter_message.setText("")
//            messagesAdapter.appendMessage(textMessage)
//            scrollToBottom()
//        }
//        viewModel.updateallstatsu(mContext, rideid)
//    }
//    fun savemessagetodb(message: String, senderid: String, timestamp: String)
//    {
//        if(checkreocrdexist(timestamp) == 0)
//        {
//            viewModel.savemessagetodb(mContext, rideid, message, senderid, timestamp)
//            val textMessage = TextMessage(rideid, message, senderid, timestamp, "1")
//            enter_message.setText("")
//            messagesAdapter.appendMessage(textMessage)
//            scrollToBottom()
//        }
//    }
//    fun retrivelistfromdb()
//    {
//        dataBase = mHelper!!.getWritableDatabase();
//        var mCursor: Cursor = dataBase!!.rawQuery("SELECT * FROM " + DbHelper.TABLE_NAME + " WHERE rideid='" + rideid + "'", null);
//        if (mCursor.moveToFirst()) {
//            do {
//                var rideid= mCursor.getString(mCursor.getColumnIndex(DbHelper.rideid))
//                var message= mCursor.getString(mCursor.getColumnIndex(DbHelper.message))
//                var senderid= mCursor.getString(mCursor.getColumnIndex(DbHelper.senderid))
//                var timestamp= mCursor.getString(mCursor.getColumnIndex(DbHelper.timestamp))
//                var status= mCursor.getString(mCursor.getColumnIndex(DbHelper.status))
//                val textMessage = TextMessage(rideid, message, senderid, timestamp, status)
//                messagesAdapter.appendMessage(textMessage)
//            } while (mCursor.moveToNext());
//        }
//        mCursor.close()
//        scrollToBottom()
//        viewModel.updateallstatsu(mContext, rideid)
//    }
//    fun checkreocrdexist(timestamp: String):Int
//    {
//        var valueexist:Int=0
//        dataBase = mHelper!!.getWritableDatabase();
//        var mCursor: Cursor = dataBase!!.rawQuery("SELECT * FROM " + DbHelper.TABLE_NAME + " WHERE rideid='" + rideid + "' AND timestamp='" + timestamp + "'", null);
//        if (mCursor.moveToFirst()) {
//            do {
//                valueexist=1
//            } while (mCursor.moveToNext());
//        }
//        mCursor.close()
//        return valueexist
//    }
//    //common error notification page
//    fun commsnackbaralert(message: String)
//    {
//        val snack = Snackbar.make(toppage, message, Snackbar.LENGTH_LONG)
//        var view: View = snack.getView()
//        var params: FrameLayout.LayoutParams = FrameLayout.LayoutParams(view.getLayoutParams())
//        params.gravity = Gravity.TOP;
//        view.setLayoutParams(params)
//        snack.show()
//    }
//    //Cancellation Bottom Sheet
//    fun templatebootomsheet()
//    {
//        var templatelist:ArrayList<CancellationReasonDataModel> = ArrayList<CancellationReasonDataModel>()
////
////        templatelist.add(CancellationReasonDataModel(getString(R.string.template1),getString(R.string.template1),"","",""))
////        templatelist.add(CancellationReasonDataModel(getString(R.string.template2),getString(R.string.template2),"","",""))
////        templatelist.add(CancellationReasonDataModel(getString(R.string.template3),getString(R.string.template3),"","",""))
////        templatelist.add(CancellationReasonDataModel(getString(R.string.template4),getString(R.string.template4),"","",""))
////        templatelist.add(CancellationReasonDataModel(getString(R.string.template5),getString(R.string.template5),"","",""))
////        templatelist.add(CancellationReasonDataModel(getString(R.string.template6),getString(R.string.template6),"","",""))
//        messagetemplate =  BottomSheetDialog(this)
//        val view = getLayoutInflater().inflate(R.layout.messagetemplate, null);
//        messagetemplate!!.setContentView(view)
//        messagetemplate!!.setCancelable(true)
//        val closetext = view.findViewById(R.id.closetext) as TextView
//        val cancellist = view.findViewById(R.id.cancellist) as com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView
//        val listViewAdapter = cancelreasonadapter(applicationContext, templatelist, object : cancelreasonclick {
//            override fun onItemClickListener(view: View, id: String, reason: String) {
//                if (AppUtils.isNetworkConnected(mContext))
//                    sendMessage(reason)
//                else
//                    commsnackbaralert(getString(R.string.failedproblem))
//
//                messagetemplate!!.dismiss()
//            }
//        })
//        closetext.setOnClickListener { messagetemplate!!.dismiss() }
//        cancellist.setAdapter(listViewAdapter)
//        cancellist.isExpanded = true
//        cancellist.visibility = View.VISIBLE
//        messagetemplate!!.show()
//    }
//
//}
