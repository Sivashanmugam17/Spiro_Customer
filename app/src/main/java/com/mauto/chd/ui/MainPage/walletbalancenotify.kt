package com.mauto.chd.ui.MainPage

//import android.app.Activity
//import android.app.Dialog
//import android.content.Intent
//import android.graphics.Color
//import android.graphics.drawable.ColorDrawable
//import android.net.Uri
//import android.os.Bundle
//import android.util.Log
//import android.view.Gravity
//import android.view.View
//import android.view.Window
//import android.widget.*
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
//import com.cabilyhandyforalldinedoo.chd.ui.sidemenus.WalletPage
//import com.google.android.material.bottomsheet.BottomSheetDialog
//import com.google.android.material.snackbar.Snackbar
//import kotlinx.android.synthetic.main.activity_rental.*
//import kotlinx.android.synthetic.main.activity_support.*
//import kotlinx.android.synthetic.main.activity_support.backbutton
//import kotlinx.android.synthetic.main.activity_support.nointernetconnectionlay
//import kotlinx.android.synthetic.main.driverprofile.*
//import kotlinx.android.synthetic.main.faq_user.*
//import kotlinx.android.synthetic.main.faq_user.nointernetconnectionlay_3
//import kotlinx.android.synthetic.main.faq_user.progress_lay_faq
//import kotlinx.android.synthetic.main.nointernetconnection.*
//import kotlinx.android.synthetic.main.otpride.*
//import kotlinx.android.synthetic.main.payment_process.*
//import kotlinx.android.synthetic.main.registerpagetwo.view.*
////import kotlinx.android.synthetic.main.requestpagedfa.*
//import kotlinx.coroutines.*
//import org.greenrobot.eventbus.EventBus
//import org.greenrobot.eventbus.Subscribe
//import org.greenrobot.eventbus.ThreadMode
//import org.jetbrains.anko.toast
//import org.json.JSONObject
//import java.util.ArrayList
//
//class walletbalancenotify : LocaleAwareCompatActivity() {
//    private lateinit var mContext: Activity
//    internal lateinit var mSessionManager: SessionManager
//    var customerphno:String = ""
//    private lateinit var repeatFunpayment : Job
//    var rideid:String=""
//    var bottomSheetDialogs: BottomSheetDialog? = null
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
//        setContentView(R.layout.wallet_balance_notify)
//        mContext = this@walletbalancenotify
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
//        Log.d("checkingride",rideid)
//        bottomSheetDialogs = BottomSheetDialog(this)
//        bottomSheetDialogs =  BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme)
//        walletdialog()
////
////        connection_lay2.setOnClickListener {
////            recreate()
////        }
//
//    }
//    fun walletdialog() {
////        availabilityoff()
////        val view = getLayoutInflater().inflate(R.layout.waiting_for_driver_wallet_balance_bottom_sheet, null);
////        bottomSheetDialogs!!.setContentView(view)
////        bottomSheetDialogs!!.setCancelable(false)
////        val waiting_bs_ok_btn = view.findViewById(R.id.waiting_bs_ok_btn) as Button
////        waiting_bs_ok_btn.setOnClickListener {
////            val intent_otppage2 = Intent(mContext, WalletPage::class.java)
////            intent_otppage2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
////            intent_otppage2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
////            startActivity(intent_otppage2)
////        }
////        bottomSheetDialogs!!.show()
//    }
//
//
//
//
//    private fun initrecyclerviews() {
//        progress_lay_faq.visibility=View.GONE
//
//
//    }
//
//
//    @InternalCoroutinesApi
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun doThis(intentServiceResult: IntentServiceResult) {
//        var apiName: String = intentServiceResult.apiName
//        var finalresponse: String = intentServiceResult.resultValue
//         if (apiName.equals("fleet_immobilized")) {
//             walletdialog()
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
//
//
//}