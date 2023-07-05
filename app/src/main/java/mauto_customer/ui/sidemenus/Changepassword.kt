package mauto_customer.ui.sidemenus

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
import kotlinx.android.synthetic.main.change_password.*
//import kotlinx.android.synthetic.main.otpride.*
//import kotlinx.android.synthetic.main.requestpagedfa.*
import mauto_customer.ui.MainscreenCustomers
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.toast
import org.json.JSONObject

class Changepassword : LocaleAwareCompatActivity() {
    private lateinit var mContext: Activity
    internal lateinit var mSessionManager: SessionManager
    var customerphno: String = "0"
    private lateinit var mViewModel: FaqViewModel
    lateinit var recycler_faq: RecyclerView
    lateinit var batery_low_tv: TextView
    var oldpasswords: String = ""
    var newpasswords: String = ""
    private lateinit var faqadapter: Faqadapter
    lateinit var mHelpers: LanguageDb
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    var app_version:String = ""

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
        setContentView(R.layout.change_password)
        mContext = this@Changepassword
        mSessionManager = SessionManager(mContext)
        mHelpers = LanguageDb(applicationContext)
        initialize()
//        SwapstDialog()
//        getFaqruser()
        app_version= BuildConfig.VERSION_NAME
        appversionnews.setText("version "+app_version)

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
//        var swap_lay = dialog.findViewById<RelativeLayout>(R.id.swap_lays)
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

        backbutton.setOnClickListener {
            val intent2 = Intent(mContext, MainscreenCustomers()::class.java)
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mContext.startActivity(intent2)
        }

        changepassword_lays.setOnClickListener {
            oldpasswords = editTextTextPassword.text.toString()
            newpasswords = editTextTextPasswordtwo.text.toString()
            if (oldpasswords.isEmpty()) {
                toast("Type Your Old Password")

            } else if (editTextTextPasswordone.text.toString().isEmpty()) {
                toast("Type Your New Password")

            } else if (newpasswords.isEmpty()) {
                toast("Type Your Confirm Password")


            } else {
                change_password_api()

            }


        }


    }

    private fun getkey(key: String): String? {
        return mHelpers.getvalueforkey(key)
    }


    fun change_password_api() {
        progress_time_password.visibility=View.VISIBLE
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.change_password))
        intent.putExtra("oldpasswords", oldpasswords)
        intent.putExtra("newpasswords", newpasswords)
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
        if (apiName.equals(getString(R.string.change_password))) {
            if (finalresponse == "failed") {
//                toast(getString(R.string.api_failure_toast_label))
            } else {
                Log.d("dfhab", "shjbdhj")
                Log.d("dfhadsfb", finalresponse)
                val response_json_object = JSONObject(finalresponse)
                progress_time_password.visibility=View.GONE

                val status = response_json_object.getString("status")
                if (status.equals("1")) {
                    toast("Password Changed Successfully")
                    val intent2 = Intent(mContext, MainscreenCustomers()::class.java)
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    mContext.startActivity(intent2)

                } else if (status.equals("0")){
                    val response = response_json_object.getString("response")
                    toast(response)
                }
            }
        }
    }
}

