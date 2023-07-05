package com.mauto.chd.ui.MainPage

//import android.app.Activity
//import android.app.Dialog
//import android.content.Intent
//import android.graphics.Color
//import android.graphics.drawable.ColorDrawable
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.view.Window
//import android.widget.LinearLayout
//import android.widget.TextView
//import com.mauto.chd.backgroundservices.commonapifetchservice
//
//import com.mauto.chd.event_bus_connection.IntentServiceResult
//import com.mauto.chd.SessionManagerPackage.SessionManager
//import com.mauto.chd.commonutils.AppUtils
//import com.mauto.chd.ui.registeration.LocaleAwareCompatActivity
//import com.mauto.customer.R
//import kotlinx.android.synthetic.main.activity_rental.*
//import kotlinx.android.synthetic.main.activity_support.*
//import kotlinx.android.synthetic.main.driverprofile.*
//import kotlinx.android.synthetic.main.faq_user.*
//import kotlinx.android.synthetic.main.faq_user.nointernetconnectionlay_3
//import kotlinx.android.synthetic.main.faq_user.progress_lay_faq
//import kotlinx.android.synthetic.main.nointernetconnection.*
////import kotlinx.android.synthetic.main.otpride.*
//import kotlinx.android.synthetic.main.payment_process.*
//import kotlinx.android.synthetic.main.registerpagetwo.view.*
////import kotlinx.android.synthetic.main.requestpagedfa.*
//import kotlinx.coroutines.*
//import org.greenrobot.eventbus.EventBus
//import org.greenrobot.eventbus.Subscribe
//import org.greenrobot.eventbus.ThreadMode
//import org.json.JSONObject
//
//class Paymentprocess : LocaleAwareCompatActivity() {
//    private lateinit var mContext: Activity
//    internal lateinit var mSessionManager: SessionManager
//    var customerphno:String = ""
//    private lateinit var repeatFunpayment : Job
//    var rideid:String=""
//
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
//
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
//    @InternalCoroutinesApi
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.payment_process)
//        mContext = this@Paymentprocess
//        mSessionManager = SessionManager(mContext)
//        initialize()
//
//
//
//    }
//
//
//    @InternalCoroutinesApi
//    fun initialize() {
//        rideid= intent.getStringExtra("rideid").toString()
//        Log.d("checkingride",rideid)
//
//        repeatFunpayment = repeatFunpayment()
//
//        backbutton_lay.setOnClickListener {
////            val mainpage = Intent(mContext, otpride::class.java)
////            mainpage.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
////            startActivity(mainpage)
//        }
////
////        connection_lay2.setOnClickListener {
////            recreate()
////        }
//
//    }
//
//
//    @InternalCoroutinesApi
//    fun repeatFunpayment(): Job {
//        val scope = CoroutineScope(Dispatchers.Default)
//        return scope.launch {
//            while(isActive) {
//                startServicePaymenttmoneycheckride()
//                delay(5000)
//            }
//        }
//    }
//
//
//    private fun initrecyclerviews() {
//        progress_lay_faq.visibility=View.GONE
//
//
//    }
//
//    fun startServicePaymenttmoneycheckride() {
//        val serviceClass = commonapifetchservice::class.java
//        val intent = Intent(applicationContext, serviceClass)
//        intent.putExtra("api_name", getString(R.string.tmoneycheckride))
//        intent.putExtra("rideid", rideid)
//        startService(intent)
//    }
//
//    @InternalCoroutinesApi
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun doThis(intentServiceResult: IntentServiceResult) {
//        var apiName: String = intentServiceResult.apiName
//        var finalresponse: String = intentServiceResult.resultValue
//         if (apiName.equals(getString(R.string.tmoneycheckride))) {
//            if (finalresponse == "failed") {
//
//            } else {
//                PaymentsuccestmoneyApiParser(finalresponse)
//            }
//        }
////        if (apiName.equals(getString(R.string.user_referra))) {
////            if (finalresponse == "failed") {
//////                toast(getString(R.string.api_failure_toast_label))
////            } else {
////                Log.d("dfhab","shjbdhj")
////                Log.d("dfhadsfb",finalresponse)
////
////            }
////        }
//    }
//
//    @InternalCoroutinesApi
//    private fun PaymentsuccestmoneyApiParser(finalresponse: String) {
//        println("-----------finalresponsesss-----" + finalresponse)
//        val response_json_object = JSONObject(finalresponse)
//        try {
//            val status = response_json_object.getString("status")
//            if (status.equals("1")) {
//                val response =response_json_object.getJSONObject("response")
//                val is_paid=response.getBoolean("is_paid")
//                val retry_payment=response.getBoolean("retry_payment")
//                if (is_paid==true){
//                    paymentSuccess()
//                }else if(retry_payment==true){
////                    val mainpage = Intent(mContext, otpride::class.java)
////                    mainpage.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
////                    startActivity(mainpage)
//                }
//                Log.d("dfd33hdhf", is_paid.toString())
//
//
//            }
//        } catch (e: java.lang.Exception) {
//
//            e.printStackTrace()
//        }
//    }
//    @InternalCoroutinesApi
//    fun paymentSuccess(){
//        val dialog = Dialog(mContext!!, R.style.Theme_Dialog)
//        dialog.window
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.setContentView(R.layout.payment_success_wallet)
//        dialog.setCanceledOnTouchOutside(false)
//        dialog.setCancelable(false)
//        val rate_now=dialog.findViewById<LinearLayout>(R.id.rate_now)
//        val done=dialog.findViewById<TextView>(R.id.done)
//        done.setText("Done")
//        rate_now.setOnClickListener {
//            dialog.dismiss()
//
//            mSessionManager.clearridedetails()
//            mSessionManager.setmride_completed("")
//            mSessionManager.setlocationdistance("0")
//            repeatFunpayment = repeatFunpayment()
//            repeatFunpayment.cancel()
//
//            val intent_otppage2 = Intent(mContext, mianscreenpage::class.java)
//            intent_otppage2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//            intent_otppage2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent_otppage2)
//
//        }
//        dialog.show()
//
//
//
//    }
//
//
//}