package mauto_customer.ui.mainpage

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager

import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.view_model_tracking_page.FaqViewModel
import com.mauto.chd.adaptersofchd.Faqadapter
import com.mauto.chd.clickableInterface.rideCustomOnClickListener
import com.mauto.chd.data.LanguageDb
import com.mauto.chd.ui.registeration.LocaleAwareCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.mauto.chd.BuildConfig
import com.mauto.chd.R

import kotlinx.android.synthetic.main.activity_support.backbutton
import kotlinx.android.synthetic.main.faq_user.*
//import kotlinx.android.synthetic.main.otpride.*
//import kotlinx.android.synthetic.main.requestpagedfa.*
import kotlinx.android.synthetic.main.service_request.*
import mauto_customer.ui.adapter.servicelistadapter
import mauto_customer.ui.MainscreenCustomers
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

class ServiceRequest : LocaleAwareCompatActivity() {
    private lateinit var mContext: Activity
    internal lateinit var mSessionManager: SessionManager
    var customerphno:String = "0"
    var vehicledetailslay:String = "0"
    var app_lay_check:String = "0"
    var bottomSheetDialog: BottomSheetDialog? = null
    var app_version:String = ""


    private lateinit var mViewModel: FaqViewModel
    lateinit var recycler_faq:RecyclerView
    lateinit var batery_low_tv:TextView
    lateinit var triplist:RecyclerView
    private lateinit var tripadapater: servicelistadapter

    private lateinit var faqadapter: Faqadapter
    lateinit var mHelpers: LanguageDb
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this);
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this);
//        if (AppUtils.isNetworkAvailable(mContext)) {
//            nointernetconnectionlay_3.visibility=View.GONE
//
//        }else{
//            nointernetconnectionlay_3.visibility=View.VISIBLE
//
//        }

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.service_request)
        mContext = this@ServiceRequest
        mSessionManager = SessionManager(mContext)
        mHelpers = LanguageDb(applicationContext)
        nestedScrollView.fullScroll(View.FOCUS_UP);
        nestedScrollView.smoothScrollTo(0,0);
        val list=mSessionManager.getreportlist()
        Log.d("cheklistnew",list)

        app_version= BuildConfig.VERSION_NAME
        appversionservice.setText("V "+app_version)
        arrow_payment.setOnClickListener {

            if (vehicledetailslay.equals("0")){
                payments_lay.visibility=View.VISIBLE
                vehicledetailslay="1"
            }else{
                payments_lay.visibility=View.GONE
                vehicledetailslay="0"
            }
        }
        arrow_app.setOnClickListener {
            if (app_lay_check.equals("0")){
                app_lay.visibility=View.VISIBLE
                app_lay_check="1"
            }else{
                app_lay.visibility=View.GONE
                app_lay_check="0"
            }
        }
        arrow_vehicle.setOnClickListener {
            if (customerphno.equals("0")){
                type_lay.visibility=View.VISIBLE
                customerphno="1"
            }else{
                type_lay.visibility=View.GONE
                customerphno="0"


            }

        }
        report_send_payment.setOnClickListener {
            if (type_your_comment_payment.text.toString().isEmpty()){
                Toast.makeText(this, "Type Your comments", Toast.LENGTH_SHORT).show()

            }else{
//                Toast.makeText(this, "Report has been submitted successfully", Toast.LENGTH_SHORT).show()
//                successdialog()
                mSessionManager.setsupport_messeage(type_your_comment_payment.text.toString())

//                getreportsubmit()

            }

        }
        report_send_two.setOnClickListener {
            if (type_your_comment_second.text.toString().isEmpty()){
                Toast.makeText(this, "Type Your comments", Toast.LENGTH_SHORT).show()

            }else{
//                Toast.makeText(this, "Report has been submitted successfully", Toast.LENGTH_SHORT).show()
//                successdialog()
                mSessionManager.setsupport_messeage(type_your_comment_second.text.toString())


            }

        }
        report_send_app.setOnClickListener {
            if (type_your_comment_app.text.toString().isEmpty()){
                Toast.makeText(this, "Type Your comments", Toast.LENGTH_SHORT).show()

            }else{
//                Toast.makeText(this, "Report has been submitted successfully", Toast.LENGTH_SHORT).show()
//                successdialog()
                mSessionManager.setsupport_messeage(type_your_comment_app.text.toString())


            }

        }
        initialize()
        initrecyclerviews(list!!)

//        getFaqruser()
    }

    private fun initrecyclerviews(list:String) {

        var vehicle: ArrayList<String> = ArrayList()
        val list= JSONArray(list)
        for (a in 0..list.length()-1){
            vehicle.add(list[a].toString())
        }
        triplist.layoutManager = LinearLayoutManager(mContext)
        tripadapater = servicelistadapter(mContext!!, vehicle, object : rideCustomOnClickListener {
            override fun onItemClickListener(view: View, position: Int,rideid:String)
            {
                var v= view as TextView
                v.text
                Log.d("checklistss", v.text.toString())
                getreportsubmit(v.text.toString(),rideid)


            }
        })
        triplist.adapter = tripadapater
        triplist.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

            }
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }


    fun getreportsubmit(message:String,subject:String) {
        progress_time_faqs.visibility=View.VISIBLE
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.reports_submit))
        intent.putExtra("message", message)
        intent.putExtra("subject", subject)
        mContext.startService(intent)
    }



    fun initialize() {
        triplist = findViewById(R.id.servicelist)

        backbutton.setOnClickListener {
            val intent2 = Intent(mContext, MainscreenCustomers()::class.java)
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mContext.startActivity(intent2)
        }
        val uri = Uri.parse("https://blackmaria-01.s3.amazonaws.com/images/banner/12053885d71647df91edb1dafbdb0ced.mp4")
        videoView.setMediaController(MediaController(this))
        videoView.setVideoURI(uri)
        videoView.setMediaController(null)
        videoView.start()

        val uris = Uri.parse("https://blackmaria-01.s3.amazonaws.com/images/banner/12053885d71647df91edb1dafbdb0ced.mp4")
        videoViewtwo.setMediaController(MediaController(this))
        videoViewtwo.setVideoURI(uris)
        videoView.setMediaController(null)
        videoViewtwo.start()
//
//        val uri = Uri.parse("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4")
//        videoView.setVideoURI(uri)
//        val mediaController = MediaController(this)
//        mediaController.setAnchorView(videoView)
//        mediaController.setMediaPlayer(videoView)
//        videoView.setMediaController(mediaController)
//        videoView.start()
    }

    private fun getkey(key: String): String? {
        return mHelpers.getvalueforkey(key)
    }
    fun getFaqruser() {
        progress_lay_faq.visibility=View.GONE
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.user_referra))
        mContext.startService(intent)
    }
    override fun onBackPressed() {
        val intent2 = Intent(mContext, MainscreenCustomers()::class.java)
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        mContext.startActivity(intent2)

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun doThis(intentServiceResult: IntentServiceResult) {
        var apiName: String = intentServiceResult.apiName
        var finalresponse: String = intentServiceResult.resultValue
        if (apiName.equals(getString(R.string.reports_submit))) {
            if (finalresponse == "failed") {
//                toast(getString(R.string.api_failure_toast_label))
            } else {
                progress_time_faqs.visibility=View.GONE

                Log.d("chekreport","shjccbdhj")
                Log.d("dfhcdccadsfb",finalresponse)
                val response_json_object = JSONObject(finalresponse)

                val status = response_json_object.getString("status")
                if (status.equals("1")) {
                    successdialog()


                }

            }
        }
    }
    fun successdialog()
    {

        bottomSheetDialog = BottomSheetDialog(this)
        val view = getLayoutInflater().inflate(R.layout.successdialogsupport, null);
        bottomSheetDialog!!.setContentView(view)
        bottomSheetDialog!!.setCancelable(false)
        val title = view.findViewById(R.id.title) as TextView

        val done = view.findViewById(R.id.done) as LinearLayout
        done.setOnClickListener {
            type_your_comment_payment.text.clear()
            type_your_comment_second.text.clear()
            type_your_comment_app.text.clear()
            bottomSheetDialog!!.dismiss()
            val intent2 = Intent(mContext, MainscreenCustomers()::class.java)
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mContext.startActivity(intent2)

        }
        bottomSheetDialog!!.show()
    }

}