package com.mauto.chd.ui.registeration

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.backgroundservices.dashboardaapii
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.view_model_register_module.RegisterpagewithotpthreeViewModel
import com.mauto.chd.commonutils.AppUtils
import com.google.android.material.snackbar.Snackbar
import com.mauto.chd.BuildConfig
import com.mauto.chd.R
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.databinding.OtppagethreeBinding

import kotlinx.android.synthetic.main.otppagethree.*
import kotlinx.android.synthetic.main.otppagethree.appversion
import kotlinx.android.synthetic.main.otppagethree.toplayout
import mauto_customer.ui.MainscreenCustomers
import mauto_customer.ui.PreDashboard
import mauto_customer.ui.sidemenus.Loginpage
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import java.util.ArrayList
import java.util.concurrent.TimeUnit


class registerpagewithotpthree : LocaleAwareCompatActivity()
{
    //variable Decalaration
    private lateinit var mContext: Activity
    lateinit var mViewModel: RegisterpagewithotpthreeViewModel
    var  mobilenovalue:String=""
    var  otpvalue:String=""
    var  otpstatusvalue:String=""
    var doaction:String = ""
    var code:String = ""

    var  codevalue:String=""
    lateinit var binding: OtppagethreeBinding
    var releaseresendclick = 0
    var timer: CountDownTimer? = null
    internal lateinit var mSessionManager: SessionManager
    var app_version:String = ""

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.otppagethree)
        mContext = this@registerpagewithotpthree
        mSessionManager = SessionManager(mContext)
        app_version= BuildConfig.VERSION_NAME
        appversion.setText("V "+app_version)
        getExtraValue()
        initViewModel()
        binding.setViewModel(mViewModel)
        editextlistenercall()
        setdevelopmentcode()
        startcountdown()
        next.setOnClickListener {
            movetonextpage()
        }
        nextlayout.setOnClickListener {
            movetonextpage()
        }
    }

    fun dashbaordapiservice(mContext: Context) {
        val serviceClass = dashboardaapii::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.dashboarapicall))
        mContext.startService(intent)
    }
    fun setdevelopmentcode()
    {
        if(otpstatusvalue.equals("development"))
        {
            var firststring:String = otpvalue.substring(0,1)
            otp1?.setText(firststring)
            var second:String = otpvalue.substring(1,2)
            otp2?.setText(second)
            var third:String = otpvalue.substring(2,3)
            otp3?.setText(third)
            var four:String = otpvalue.substring(3,4)
            otp4?.setText(four)
            otp4.setSelection(otp4.getText().length)
//            var five:String = otpvalue.substring(4,5)
//            otp5?.setText(five)
//            otp5.setSelection(otp5.getText().length)
//            var six:String = otpvalue.substring(5,6)
//            otp6?.setText(six)
//            otp6.setSelection(otp6.getText().length)
        }
        else
        {
            otp1.requestFocus()
        }
        AppUtils.showKeyboard(mContext, otp1!!)
    }
    private fun getExtraValue()
    {
        mobilenovalue = intent.getStringExtra("mobileno")!!
        otpvalue = intent.getStringExtra("otp")!!
        otpstatusvalue = intent.getStringExtra("otpstatus")!!
        doaction = intent.getStringExtra("doaction")!!
        code=intent.getStringExtra("code")!!
        Log.d("TAG", "getExtraValue: $doaction")

        Enter_your_phone.setText(code+" "+mobilenovalue)
    }
    private fun initViewModel()
    {
        mViewModel = ViewModelProviders.of(this).get(RegisterpagewithotpthreeViewModel::class.java)
        mViewModel.getflagdetailsfromsession(mContext)
        mViewModel.clearchatrecord(mContext)

        mViewModel.countrycodeobservervalue().observe(this, Observer {
            senttovalue.hint = getString(R.string.sentto)+" +"+it+" "+mobilenovalue
            codevalue = it
        })

        mViewModel.movetonextpageobserver().observe(this, Observer {
           if(it == 1)
               movetonextpage()
        })

        mViewModel.closescreenobserver().observe(this, Observer {
            if(it == 1)
                AppUtils.hideKeyboard(mContext, otp1!!)
                finish()
        })

        mViewModel.decideclassobserver().observe(this, Observer {
            if(it == 0)
                 movetoregisterpage()
            else if (it ==2){
                movetoPreDashboard()
            }else
                getData()


//            movetomainpage()

        })

        mViewModel.resendobserver().observe(this, Observer {
            if(releaseresendclick == 1)
            {
/*
                if(loader.visibility != View.VISIBLE)
                {
                    loader.visibility = View.VISIBLE
                    mViewModel.startmobileapi(mContext,mobilenovalue,codevalue)
                    releaseresendclick= 0

                }
*/
            }
        })

        mViewModel.otpwithstatusobservervalue().observe(this, Observer {
            startcountdown()
            Log.d("checkinssg",it)
            val otpoutput = it.split(",").toTypedArray()
            otpvalue = otpoutput[0]
            otpstatusvalue = otpoutput[1]
            setdevelopmentcode()
        })
    }
    fun editextlistenercall()
    {
        otp1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0!!.length  == 1)
                {
                    otp2.requestFocus()
                    visiblenextbutton()
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
        otp2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0!!.length == 0)
                    otp1.requestFocus()
                else
                    if (otp1.text.length == 1)
                    {
                        otp1.setSelection(1)
                        otp3.requestFocus()
                        visiblenextbutton()
                    }
                visiblenextbutton()

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        otp3.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0!!.length == 0)
                {
                    otp2.requestFocus()
                }
                else
                    if (otp2.text.length == 1)
                    {
                        otp2.setSelection(1)
                        otp4.requestFocus()
                    }
                visiblenextbutton()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        otp4.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0!!.length == 0)
                {
                    otp3.requestFocus()
                }
                else
                    if (otp3.text.length == 1)
                    {
                        otp3.setSelection(1)
                        otp5.requestFocus()
                    }
                visiblenextbutton()

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        otp5.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0!!.length == 0)
                    otp4.requestFocus()
                else
                    if (otp4.text.length == 1)
                    {
                        otp4.setSelection(1)
                        otp6.requestFocus()
                    }
                visiblenextbutton()

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        otp6.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0!!.length == 0)
                {
                    otp5.requestFocus()
                }
                else
                    if (otp5.text.length == 1)
                    {
                        otp5.setSelection(1)
                    }
                visiblenextbutton()

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
        otp6.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                var closekeypad:Boolean = false
                if(otp6.text.length>0)
                {
                    movetonextpage()
                    closekeypad = true
                }
                closekeypad
            } else {
                false
            }
        }
    }
    fun registerpagefour()
    {
        movetonextscreen()
    }
    fun movetoPreDashboard()
    {
        progress_time_otp.visibility= View.GONE
        val intent_otppage = Intent(mContext, PreDashboard::class.java)
        intent_otppage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent_otppage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent_otppage)
    }
    fun movetoregisterpage()
    {
         val intent_registerpagefour = Intent(mContext, Loginpage::class.java)
         intent_registerpagefour.putExtra("mobileno","" + mobilenovalue)
         intent_registerpagefour.putExtra("code","" + codevalue)
         startActivity(intent_registerpagefour)
         finish()
    }
    fun movetomainpage()
    {
        progress_time_otp.visibility=View.GONE
        val intent_otppage = Intent(mContext, MainscreenCustomers::class.java)
        intent_otppage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent_otppage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent_otppage)
    }


    fun visiblenextbutton()
    {
          if(otp1.text.length == 1 && otp2.text.length == 1 && otp3.text.length == 1 && otp4.text.length == 1)
              nextlayout.visibility = View.VISIBLE
        else
              nextlayout.visibility = View.GONE
    }
    fun movetonextpage()
    {
        progress_time_otp.visibility=View.VISIBLE
    var concatopt:String = ""+otp1.text+otp2.text+otp3.text+otp4.text+otp5.text+otp6.text
    if(otpvalue.equals(concatopt))
    {
//        if(loader.visibility == View.VISIBLE)
//        {
//            commsnackbaralert(getString(R.string.loading))
//        }
//        else
//        {
            registerpagefour()
//        }
    }
    else if(concatopt.equals(""))
    {
        //enter otp
        AppUtils.commonerrorsheet(mContext,getString(R.string.failed),getString(R.string.ot_empty))
    }
    else
    {
        //invalid otp
        AppUtils.commonerrorsheet(mContext,getString(R.string.failed),getString(R.string.otp_wrong))
    }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun doThis(intentServiceResult: IntentServiceResult) {
        var finalresponse: String = intentServiceResult.resultValue
//        loader.visibility = View.INVISIBLE
        var apiName: String = intentServiceResult.apiName
        if (apiName.equals(getString(R.string.resendotp))) {
            if (finalresponse != "failed") {
                mViewModel.splitresponse(finalresponse)
            }
        } else if (apiName.equals(getString(R.string.checkinuserexist))) {
            if (finalresponse != "failed") {
                mViewModel.splitexistingcall(finalresponse)
            }
        } else if (apiName.equals(getString(R.string.dashboarapicall))) {
            if (finalresponse != "failed") {
                var responseObj = JSONObject(finalresponse)

                if (responseObj.getString("status").equals("1")) {
                    var getingres: String = responseObj.getString("response")
                    var resultsresponse = JSONObject(getingres)
                    Log.d("edggf55gfdgf", resultsresponse.toString())


                    var agent_image = resultsresponse.getString("driver_image")

                    var agent_name = resultsresponse.getString("driver_name")
                    var driver_phone = resultsresponse.getString("driver_phone")
                    var driver_vehicle = resultsresponse.getString("driver_vehicle")
                   var  driver_model= resultsresponse.getString("driver_model")
//                    var vdial_code =
                    mSessionManager.setdriver_profile_image(agent_image)
                    mSessionManager.setdriver_names(agent_name)
                    mSessionManager.setdriver_vehicle_news(driver_vehicle)
                    mSessionManager.setdriver_vehicle(driver_phone)
                    mSessionManager. setdriver_model(driver_model)
////                    progress_lay_otp_page.visibility=View.GONE

                    val serviceClass = commonapifetchservice::class.java
                    val intent = Intent(mContext, serviceClass)
                    intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.offer_copen1))
                    intent.putExtra("driver_id", mSessionManager.getDriverId())
                    mContext.startService(intent)

                    //movetomainpage()

//                    val serviceClass = commonapifetchservice::class.java
//                    val intent = Intent(mContext, serviceClass)
//                    intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.offer_copen))
//                    mContext.startService(intent)

                }

            }

        }
    }
    override fun onPause()
    {
        super.onPause()
        EventBus.getDefault().unregister(this);
    }
    override fun onResume()
    {
        super.onResume()
        EventBus.getDefault().register(this);
    }
    override fun onDestroy()
    {
        super.onDestroy()
        EventBus.getDefault().unregister(this);
        if(timer != null)
        {
            (timer as CountDownTimer).cancel()
        }
    }
    private fun getData() {
        val aIntent = Intent(this, commonapifetchservice::class.java)
        aIntent.putExtra(getString(R.string.intent_putextra_api_key), getString(R.string.dashboarapicall))
        startService(aIntent)
    }

    fun startcountdown()
    {
        if(timer != null)
        {
            (timer as CountDownTimer).cancel()
        }
        timer = object: CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long)
            {
                val hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished), TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))
                binding.resendcode.setText(hms)
                releaseresendclick= 0

            }
            override fun onFinish()
            {
                releaseresendclick = 1
                binding.resendcode.setText(getString(R.string.resendcode))
            }
        }
        (timer as CountDownTimer).start()
    }
    fun movetonextscreen()
    {
        if(doaction.equals("0"))
        {
            movetoregisterpage()
        }
        else
        {
//            loader.visibility = View.VISIBLE
            val serviceClass = commonapifetchservice::class.java
            val intent = Intent(mContext, serviceClass)
            intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.checkinuserexist))
            intent.putExtra("dail_code", codevalue)
            intent.putExtra("mobile_number", mobilenovalue)
            mContext.startService(intent)
        }

    }
    //common error notification page
    fun commsnackbaralert(message:String)
    {
        val snack = Snackbar.make(toplayout,message, Snackbar.LENGTH_LONG)
        var view:View = snack.getView()
        var params: FrameLayout.LayoutParams = FrameLayout.LayoutParams(view.getLayoutParams())
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params)
        snack.show()
    }
}