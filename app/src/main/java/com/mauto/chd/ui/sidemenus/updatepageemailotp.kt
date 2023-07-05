package com.mauto.chd.ui.sidemenus
//
//import android.app.Activity
//import android.content.Intent
//import android.os.Bundle
//import android.os.CountDownTimer
//import android.text.Editable
//import android.text.TextWatcher
//import android.view.Gravity
//import android.view.View
//import android.view.inputmethod.EditorInfo
//import android.widget.FrameLayout
//import androidx.databinding.DataBindingUtil
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import cabily.handyforall.dinedoo.R
//import cabily.handyforall.dinedoo.databinding.OtppagethreeBinding
//import cabily.handyforall.dinedoo.databinding.UpdateemailwitotpBinding
//import cabily.handyforall.dinedoo.databinding.UpdatemobilewitotpBinding
//import com.cabilyhandyforalldinedoo.chd.Backgroundservices.commonapifetchservice
//import com.cabilyhandyforalldinedoo.chd.EventBusConnection.IntentServiceResult
//import com.cabilyhandyforalldinedoo.chd.ViewModelRegisterModule.registerpagewithotpthreeViewModel
//import com.cabilyhandyforalldinedoo.chd.commonutils.AppUtils
//import com.cabilyhandyforalldinedoo.chd.ui.MainPage.turnlocationonandintenet
//import com.cabilyhandyforalldinedoo.chd.viewmodelformobileupdate.updteemailotpthreeViewModel
//import com.cabilyhandyforalldinedoo.chd.viewmodelformobileupdate.updtemobileotpthreeViewModel
//import com.google.android.material.snackbar.Snackbar
//import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity
//import kotlinx.android.synthetic.main.updatemobilewitotp.*
//
//import org.greenrobot.eventbus.EventBus
//import org.greenrobot.eventbus.Subscribe
//import org.greenrobot.eventbus.ThreadMode
//import java.util.concurrent.TimeUnit
//
//
//class updatepageemailotp : LocaleAwareCompatActivity()
//{
//    //variable Decalaration
//    private lateinit var mContext: Activity
//    lateinit var mViewModel: updteemailotpthreeViewModel
//    var  mobilenovalue:String=""
//    var  otpvalue:String=""
//    var  otpstatusvalue:String=""
//    var doaction:String = ""
//
//    lateinit var binding: UpdateemailwitotpBinding
//    var releaseresendclick = 0
//    var timer: CountDownTimer? = null
//    override fun onCreate(savedInstanceState: Bundle?)
//    {
//        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this, R.layout.updateemailwitotp)
//        mContext = this@updatepageemailotp
//        getExtraValue()
//        initViewModel()
//        binding.setViewModel(mViewModel)
//        editextlistenercall()
//        setdevelopmentcode()
//        resendlay.visibility = View.GONE
//        next.setOnClickListener {
//            movetonextpage()
//        }
//        nextlayout.setOnClickListener {
//            movetonextpage()
//        }
//    }
//    fun setdevelopmentcode()
//    {
//        if(otpstatusvalue.equals("development"))
//        {
//            var firststring:String = otpvalue.substring(0,1)
//            otp1?.setText(firststring)
//            var second:String = otpvalue.substring(1,2)
//            otp2?.setText(second)
//            var third:String = otpvalue.substring(2,3)
//            otp3?.setText(third)
//            var four:String = otpvalue.substring(3,4)
//            otp4?.setText(four)
//            otp4.setSelection(otp4.getText().length)
//            var five:String = otpvalue.substring(4,5)
//            otp5?.setText(five)
//            otp5.setSelection(otp5.getText().length)
//            var six:String = otpvalue.substring(5,6)
//            otp6?.setText(six)
//            otp6.setSelection(otp6.getText().length)
//        }
//        else
//        {
//            otp1.requestFocus()
//        }
//        AppUtils.showKeyboard(mContext, otp1!!)
//    }
//    private fun getExtraValue()
//    {
//        mobilenovalue = intent.getStringExtra("mobileno")
//        otpvalue = intent.getStringExtra("otp")
//        otpstatusvalue = intent.getStringExtra("otpstatus")
//        doaction = intent.getStringExtra("doaction")
//        senttovalue.hint = getString(R.string.sentto)+" "+mobilenovalue
//    }
//    private fun initViewModel()
//    {
//        mViewModel = ViewModelProviders.of(this).get(updteemailotpthreeViewModel::class.java)
//
//
//
//
//        mViewModel.movetonextpageobserver().observe(this, Observer {
//           if(it == 1)
//               movetonextpage()
//        })
//
//        mViewModel.decideclassobserver().observe(this, Observer {
//          finish()
//        })
//
//        mViewModel.closescreenobserver().observe(this, Observer {
//            if(it == 1)
//                AppUtils.hideKeyboard(mContext, otp1!!)
//                finish()
//        })
//
//
//
//
//
//        mViewModel.otpwithstatusobservervalue().observe(this, Observer {
//            val otpoutput = it.split(",").toTypedArray()
//            otpvalue = otpoutput[0]
//            otpstatusvalue = otpoutput[1]
//            setdevelopmentcode()
//        })
//    }
//    fun editextlistenercall()
//    {
//        otp1.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(p0: Editable?) {
//                if (p0!!.length  == 1)
//                {
//                    otp2.requestFocus()
//                    visiblenextbutton()
//                }
//            }
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//        })
//        otp2.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(p0: Editable?) {
//                if (p0!!.length == 0)
//                    otp1.requestFocus()
//                else
//                    if (otp1.text.length == 1)
//                    {
//                        otp1.setSelection(1)
//                        otp3.requestFocus()
//                        visiblenextbutton()
//                    }
//                visiblenextbutton()
//
//            }
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//        })
//        otp3.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(p0: Editable?) {
//                if (p0!!.length == 0)
//                {
//                    otp2.requestFocus()
//                }
//                else
//                    if (otp2.text.length == 1)
//                    {
//                        otp2.setSelection(1)
//                        otp4.requestFocus()
//                    }
//                visiblenextbutton()
//            }
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//        })
//        otp4.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(p0: Editable?) {
//                if (p0!!.length == 0)
//                {
//                    otp3.requestFocus()
//                }
//                else
//                    if (otp3.text.length == 1)
//                    {
//                        otp3.setSelection(1)
//                        otp5.requestFocus()
//                    }
//                visiblenextbutton()
//
//            }
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//        })
//        otp5.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(p0: Editable?) {
//                if (p0!!.length == 0)
//                    otp4.requestFocus()
//                else
//                    if (otp4.text.length == 1)
//                    {
//                        otp4.setSelection(1)
//                        otp6.requestFocus()
//                    }
//                visiblenextbutton()
//
//            }
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//        })
//        otp6.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(p0: Editable?) {
//                if (p0!!.length == 0)
//                {
//                    otp5.requestFocus()
//                }
//                else
//                    if (otp5.text.length == 1)
//                    {
//                        otp5.setSelection(1)
//                    }
//                visiblenextbutton()
//
//            }
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//        })
//        otp6.setOnEditorActionListener { v, actionId, event ->
//            if(actionId == EditorInfo.IME_ACTION_DONE){
//                var closekeypad:Boolean = false
//                if(otp6.text.length>0)
//                {
//                    movetonextpage()
//                    closekeypad = true
//                }
//                closekeypad
//            } else {
//                false
//            }
//        }
//    }
//    fun registerpagefour(concatopt:String)
//    {
//        movetonextscreen(concatopt)
//    }
//
//
//    fun visiblenextbutton()
//    {
//          if(otp1.text.length == 1 && otp2.text.length == 1 && otp3.text.length == 1 && otp4.text.length == 1 && otp5.text.length == 1 && otp6.text.length == 1)
//              nextlayout.visibility = View.VISIBLE
//        else
//              nextlayout.visibility = View.INVISIBLE
//    }
//    fun movetonextpage()
//    {
//    var concatopt:String = ""+otp1.text+otp2.text+otp3.text+otp4.text+otp5.text+otp6.text
//    if(otpvalue.equals(concatopt))
//    {
//        if(loader.visibility == View.VISIBLE)
//        {
//            commsnackbaralert(getString(R.string.loading))
//        }
//        else
//        {
//            registerpagefour(concatopt)
//        }
//    }
//    else if(concatopt.equals(""))
//    {
//        //enter otp
//        AppUtils.commonerrorsheet(mContext,getString(R.string.failed),getString(R.string.ot_empty))
//    }
//    else
//    {
//        //invalid otp
//        AppUtils.commonerrorsheet(mContext,getString(R.string.failed),getString(R.string.otp_wrong))
//    }
//    }
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun doThis(intentServiceResult: IntentServiceResult)
//    {
//        var finalresponse: String = intentServiceResult.resultValue
//        loader.visibility = View.INVISIBLE
//        var apiName: String = intentServiceResult.apiName
//        if (apiName.equals(getString(R.string.updateemailidalonewithotp)))
//        {
//            if (finalresponse != "failed")
//            {
//                mViewModel.splitexistingcall(mContext,finalresponse,mobilenovalue)
//            }
//        }
//    }
//    override fun onPause()
//    {
//        super.onPause()
//        EventBus.getDefault().unregister(this);
//    }
//    override fun onResume()
//    {
//        super.onResume()
//        EventBus.getDefault().register(this);
//    }
//    override fun onDestroy()
//    {
//        super.onDestroy()
//        EventBus.getDefault().unregister(this);
//        if(timer != null)
//        {
//            (timer as CountDownTimer).cancel()
//        }
//    }
//
//    fun movetonextscreen(concatopt:String)
//    {
//        loader.visibility = View.VISIBLE
//        val serviceClass = commonapifetchservice::class.java
//        val intent = Intent(mContext, serviceClass)
//        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.updateemailidalonewithotp))
//        intent.putExtra("email", mobilenovalue)
//        intent.putExtra("otp", concatopt)
//        mContext.startService(intent)
//    }
//    //common error notification page
//    fun commsnackbaralert(message:String)
//    {
//        val snack = Snackbar.make(toplayout,message, Snackbar.LENGTH_LONG)
//        var view:View = snack.getView()
//        var params: FrameLayout.LayoutParams = FrameLayout.LayoutParams(view.getLayoutParams())
//        params.gravity = Gravity.TOP;
//        view.setLayoutParams(params)
//        snack.show()
//    }
//}