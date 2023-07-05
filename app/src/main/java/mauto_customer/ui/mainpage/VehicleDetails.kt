package mauto_customer.ui.mainpage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager

import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.view_model_tracking_page.FaqViewModel
import com.mauto.chd.adaptersofchd.Faqadapter
import com.mauto.chd.data.LanguageDb
import com.mauto.chd.ui.registeration.LocaleAwareCompatActivity
import com.google.android.material.tabs.TabLayout
import com.mauto.chd.BuildConfig
import com.mauto.chd.R
import kotlinx.android.synthetic.main.activity_support.backbutton
//import kotlinx.android.synthetic.main.otpride.*
//import kotlinx.android.synthetic.main.requestpagedfa.*
import kotlinx.android.synthetic.main.vehicle_details.*
import kotlinx.android.synthetic.main.vehicle_details.appversionnew
import mauto_customer.ui.MainscreenCustomers
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject

class VehicleDetails : LocaleAwareCompatActivity() {
    private lateinit var mContext: Activity
    internal lateinit var mSessionManager: SessionManager
    var customerphno:String = "0"
    private lateinit var mViewModel: FaqViewModel
    lateinit var recycler_faq:RecyclerView
    lateinit var batery_low_tv:TextView
    var app_version:String = ""

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
        setContentView(R.layout.vehicle_details)
        mContext = this@VehicleDetails
        mSessionManager = SessionManager(mContext)
        mHelpers = LanguageDb(applicationContext)
        initialize()
//        SwapstDialog()
        getFaqruser()


    }

    fun SwapstDialog() {
//        val dialog = Dialog(mContext)
//        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setCancelable(true)
//        dialog.setContentView(R.layout.new_dialog)
//        dialog.window!!.setGravity(Gravity.CENTER)
//
//        val displayMetrics = DisplayMetrics()
//        this.windowManager.defaultDisplay.getMetrics(displayMetrics)
//        val widthLcl = (displayMetrics.widthPixels * 0.9f)
//        val heightLcl = WindowManager.LayoutParams.WRAP_CONTENT
//        var swap_lay=dialog.findViewById<RelativeLayout>(R.id.swap_lays)
//        val paramsLcl = swap_lay.getLayoutParams() as FrameLayout.LayoutParams
//        paramsLcl.width = widthLcl.toInt()
//        paramsLcl.height = heightLcl.toInt()
//        paramsLcl.gravity = Gravity.CENTER_VERTICAL
//        val window: Window = dialog.getWindow()!!
//        swap_lay.setLayoutParams(paramsLcl)
//        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
////        val cancel_popup : TextView
////        val confirm_popup : TextView
////        cancel_popup = dialog.findViewById(R.id.cancel_popup)
////        confirm_popup = dialog.findViewById(R.id.confirm_popup)
//        dialog.show()

//        cancel_popup.setOnClickListener {
//            dialog.dismiss()
//
//        }

//        confirm_popup.setOnClickListener {
//            val intent = Intent(context, com.voizout.view.activity.subscription.SubscriptionActivity::class.java)
//            startActivity(intent)
//            dialog.dismiss()
//        }



    }



    fun initialize() {
        app_version= BuildConfig.VERSION_NAME
        appversionnew.setText("V "+app_version)
        backbutton.setOnClickListener {
            val intent2 = Intent(mContext, MainscreenCustomers()::class.java)
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mContext.startActivity(intent2)
        }



    }

    private fun getkey(key: String): String? {
        return mHelpers.getvalueforkey(key)
    }


    fun getFaqruser() {
        progress_lay_vechile.visibility=View.VISIBLE

        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.vehicle_list))
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
        if (apiName.equals(getString(R.string.vehicle_list))) {
            if (finalresponse == "failed") {
//                toast(getString(R.string.api_failure_toast_label))
            } else {
                Log.d("dfhab", "shjbdhj")
                Log.d("listnew", finalresponse)
                val response_json_object = JSONObject(finalresponse)
                progress_lay_vechile.visibility=View.GONE

                val status = response_json_object.getString("status")
                if (status.equals("1")) {

                    val response = response_json_object.getJSONObject("response")
                    val response_faq_array = response.getJSONArray("vehicles")
                    if (response_faq_array.length() > 0) {

                        for (i in 0 until response_faq_array.length()) {
                            val response_object_faq = response_faq_array.getJSONObject(i)

                            var vehicle_number = response_object_faq.getString("vehicle_number")

                            var vehicle_model = response_object_faq.getString("vehicle_model")
                            var contract = response_object_faq.getString("contract")

                            Log.d("checlinglist",vehicle_number)
                            var down_payments = response_object_faq.getJSONObject("down_payments")
                            var paid_amount = down_payments.getString("paid_amount")
                            var pending_amount = down_payments.getString("pending_amount")
                            var due_payments = response_object_faq.getJSONObject("due_payments")

                            var mpaid_amount = due_payments.getString("paid_amount")
                            var mpending_amount = due_payments.getString("pending_amount")
                            var days_paid = due_payments.getString("days_paid")
                            var day_to_pay = due_payments.getString("day_to_pay")
                            //bucket
                            var bucketinfo = response_object_faq.getJSONObject("bucket_info")
                            var bucket_name = bucketinfo.getString("bucket_name")
                            var bucket_type = bucketinfo.getString("bucket_type")
                            var con_misspayment = bucketinfo.getString("con_misspayment")
                            var rental_due_amount = bucketinfo.getString("rental_due_amount")
                            var payment_default_event = bucketinfo.getString("payment_default_event")
                            var next_bucket = bucketinfo.getString("next_bucket")


                            Log.d("checlinglist202",paid_amount)

                            vehicle_number_textview.setText(vehicle_number)
                            vehicle_model_textview.setText(vehicle_model)
                            Bucketname.setText(bucket_name)
                            Bucket_type.setText(bucket_type)
                            Bucket_paymeny.setText(con_misspayment)
                            retaldue.setText(rental_due_amount)
                            paymentdefalt.setText(payment_default_event)
                            Nect_bucket.setText(next_bucket)




//                            if(vehicle_model_textview! == "CHAP_CHAP"){
//
//                            }else if(vehicle_model_textview == ""){
//
//                            }else if (vehicle_model_textview == ""){
//
//                            }

//
                            //vehicle_model_textview.setText(contract)
                            contract_textview.setText(contract)
                            paid_amount_textview.setText(paid_amount)
                            pending_amount_textview.setText(pending_amount)
                            paid_amount_due.setText(mpaid_amount)
                            pending_amount_due.setText(mpending_amount)
                            paid_days_textview.setText(days_paid)
                            pending_days_textview.setText(day_to_pay)


                            if(vehicle_number.equals("-")){
                                down.visibility=View.GONE
                            }
                            if(vehicle_model.equals("CHAP CHAP")){
                                cha_chap.visibility=View.VISIBLE
                                camando.visibility=View.GONE
                                meletric.visibility=View.GONE
                            }
                            else if(vehicle_model.equals("COMMANDO")){

                                camando.visibility=View.VISIBLE
                                cha_chap.visibility=View.GONE
                                meletric.visibility=View.GONE
                            }
                            else if(vehicle_model.equals("E-Bike")){
                                meletric.visibility=View.VISIBLE
                                camando.visibility=View.GONE
                                cha_chap.visibility=View.GONE

                            }else{

                                novehicel.visibility=View.VISIBLE
                                meletric.visibility=View.GONE
                                camando.visibility=View.GONE
                                cha_chap.visibility=View.GONE
                            }

                        }
                    }

                }

            }
        }
    }

}