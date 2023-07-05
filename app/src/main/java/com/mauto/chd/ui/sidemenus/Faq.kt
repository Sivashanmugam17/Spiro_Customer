//package com.cabilyhandyforalldinedoo.chd.ui.sidemenus
//
//import android.app.Activity
//import android.content.Intent
//import android.net.Uri
//import android.os.Bundle
//import android.util.Log
//import android.view.Gravity
//import android.view.View
//import android.widget.FrameLayout
//import android.widget.TextView
//import android.widget.Toast
//import androidx.databinding.DataBindingUtil
//import androidx.lifecycle.ViewModelProviders
//import cabily.handyforall.dinedoo.R
//import androidx.lifecycle.Observer
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.cabilyhandyforalldinedoo.chd.Backgroundservices.commonapifetchservice
//
//import com.cabilyhandyforalldinedoo.chd.EventBusConnection.IntentServiceResult
//import com.cabilyhandyforalldinedoo.chd.Modal.tripdetailsModels
//import com.cabilyhandyforalldinedoo.chd.SessionManagerPackage.SessionManager
//import com.cabilyhandyforalldinedoo.chd.ViewModelTrackingPage.FaqViewModel
//import com.cabilyhandyforalldinedoo.chd.ViewModelTrackingPage.OtpRideViewModel
//import com.cabilyhandyforalldinedoo.chd.adaptersofchd.Faqadapter
//import com.cabilyhandyforalldinedoo.chd.clickableInterface.driverOnClickListener
//import com.cabilyhandyforalldinedoo.chd.clickableInterface.rideCustomOnClickListener
//import com.cabilyhandyforalldinedoo.chd.commonutils.AppUtils
//import com.cabilyhandyforalldinedoo.chd.data.LanguageDb
//import com.cabilyhandyforalldinedoo.chd.ui.registeration.LocaleAwareCompatActivity
//import com.google.android.material.snackbar.Snackbar
//import kotlinx.android.synthetic.main.activity_rental.*
//import kotlinx.android.synthetic.main.activity_support.*
//import kotlinx.android.synthetic.main.activity_support.backbutton
//import kotlinx.android.synthetic.main.activity_support.nointernetconnectionlay
//import kotlinx.android.synthetic.main.driverprofile.*
//import kotlinx.android.synthetic.main.faq_user.*
//import kotlinx.android.synthetic.main.nointernetconnection.*
//import kotlinx.android.synthetic.main.otpride.*
//import kotlinx.android.synthetic.main.registerpagetwo.view.*
////import kotlinx.android.synthetic.main.requestpagedfa.*
//import org.greenrobot.eventbus.EventBus
//import org.greenrobot.eventbus.Subscribe
//import org.greenrobot.eventbus.ThreadMode
//import org.jetbrains.anko.toast
//import java.util.ArrayList
//
//class Faq : LocaleAwareCompatActivity() {
//    private lateinit var mContext: Activity
//    internal lateinit var mSessionManager: SessionManager
//    var customerphno:String = ""
//    private lateinit var mViewModel: FaqViewModel
//    lateinit var recycler_faq:RecyclerView
//    lateinit var batery_low_tv:TextView
//
//    var faqQAdata: ArrayList<questionAnswerModel> ?= null
//    var faqfulldata: ArrayList<tripdetailsModels> = ArrayList()
//    private lateinit var faqadapter: Faqadapter
//    lateinit var mHelpers: LanguageDb
//
//    override fun onPause() {
//        super.onPause()
//        EventBus.getDefault().unregister(this);
//    }
//
//    override fun onResume() {
//        super.onResume()
//        EventBus.getDefault().register(this);
//        if (AppUtils.isNetworkAvailable(mContext)) {
//            nointernetconnectionlay_3.visibility=View.GONE
//
//        }else{
//            nointernetconnectionlay_3.visibility=View.VISIBLE
//
//        }
//
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        EventBus.getDefault().unregister(this);
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.faq_user)
//        mContext = this@Faq
//        mSessionManager = SessionManager(mContext)
//        mHelpers = LanguageDb(applicationContext)
//        initialize()
//        getFaqruser()
//        initViewModel()
//
//
//
//    }
//
//    private fun initViewModel() {
//        mViewModel = ViewModelProviders.of(this).get(FaqViewModel::class.java)
//
//        mViewModel.mtopicobserver().observe(this, Observer {
//            faqfulldata.clear()
//            Log.d("chgsdg", it.toString())
//            val response_faq_array = it.getJSONArray("faq")
//            Log.d("sfzbhsdbhjf", response_faq_array.toString())
//
//            if (response_faq_array.length() > 0) {
//                for (i in 0 until response_faq_array.length()) {
//                    val response_object_faq = response_faq_array.getJSONObject(i)
//
//                    var topic = response_object_faq.getString("topic")
//                    var response_faq_array2 = response_object_faq.getJSONArray("faq")
//                    faqQAdata = ArrayList()
//
//                    if (response_faq_array2.length() > 0) {
//                        for (k in 0 until response_faq_array2.length()) {
//                            val response_object_faq2 = response_faq_array2.getJSONObject(k)
//
//                            var question = response_object_faq2.getString("question")
//                            var answer = response_object_faq2.getString("answer")
//                             answer=answer.substring(3,answer.length-4)
//
//                            faqQAdata!!.add(questionAnswerModel(question,answer))
//                        }
//                    }
//                    faqfulldata.add(tripdetailsModels(topic,faqQAdata!!))
//                    initrecyclerviews()
//
//                }
//            }
//        })
//    }
//
//    fun initialize() {
//        recycler_faq = findViewById(R.id.recycler_faq)
//        batery_low_tv = findViewById(R.id.batery_low_tv)
//        batery_low_tv.setText(getkey("faq"))
//        backbutton.setOnClickListener {
//            finish()
//        }
//
//        connection_lay2.setOnClickListener {
//            recreate()
//        }
//
//    }
//
//    private fun getkey(key: String): String? {
//        return mHelpers.getvalueforkey(key)
//    }
//
//    private fun initrecyclerviews() {
//        progress_lay_faq.visibility=View.GONE
//
//        if (faqfulldata.size > 0){
//            recycler_faq.layoutManager = LinearLayoutManager(mContext,RecyclerView.VERTICAL,false)
//            faqadapter = Faqadapter(mContext,faqfulldata, object : driverOnClickListener {
//                override fun onItemClickListener(view: View, position: Int, rideid:String) {
////                fulldetailpage(rideid)
//                }
//            })
//            recycler_faq.adapter = faqadapter
//            recycler_faq.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    super.onScrolled(recyclerView, dx, dy)
//                }
//                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                    super.onScrollStateChanged(recyclerView, newState)
//                }
//            })
//        }
//    }
//
//    fun getFaqruser() {
//        progress_lay_faq.visibility=View.VISIBLE
//
//        val serviceClass = commonapifetchservice::class.java
//        val intent = Intent(mContext, serviceClass)
//        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.user_referra))
//        mContext.startService(intent)
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun doThis(intentServiceResult: IntentServiceResult) {
//        var apiName: String = intentServiceResult.apiName
//        var finalresponse: String = intentServiceResult.resultValue
//        if (apiName.equals(getString(R.string.user_referra))) {
//            if (finalresponse == "failed") {
////                toast(getString(R.string.api_failure_toast_label))
//            } else {
//                Log.d("dfhab","shjbdhj")
//                mViewModel.FaqApiParser(finalresponse)
//                Log.d("dfhadsfb",finalresponse)
//
//            }
//        }
//    }
//
//}