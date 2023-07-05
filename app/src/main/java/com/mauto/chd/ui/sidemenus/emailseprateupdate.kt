package com.mauto.chd.ui.sidemenus

//import android.app.Activity
//import android.content.Intent
//import android.os.Bundle
//import android.text.Editable
//import android.text.TextUtils
//import android.text.TextWatcher
//import android.util.Patterns
//import android.view.Gravity
//import android.view.View
//import android.widget.FrameLayout
//import androidx.databinding.DataBindingUtil
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import cabily.handyforall.dinedoo.R
//import cabily.handyforall.dinedoo.databinding.EmailseprateeditBinding
//import com.cabilyhandyforalldinedoo.chd.EventBusConnection.IntentServiceResult
//import com.cabilyhandyforalldinedoo.chd.commonutils.AppUtils
//import com.cabilyhandyforalldinedoo.chd.commonutils.Constants
//import com.cabilyhandyforalldinedoo.chd.ui.registeration.CountryCodeSelection
//import com.cabilyhandyforalldinedoo.chd.viewmodelformobileupdate.emailidupdateViewModel
//import com.google.android.material.snackbar.Snackbar
//import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity
//import kotlinx.android.synthetic.main.emailseprateedit.*
//import org.greenrobot.eventbus.EventBus
//import org.greenrobot.eventbus.Subscribe
//import org.greenrobot.eventbus.ThreadMode
//
//class emailseprateupdate : LocaleAwareCompatActivity()
//{
//    lateinit var binding: EmailseprateeditBinding
//    private lateinit var mContext: Activity
//    lateinit var mViewModel: emailidupdateViewModel
//    private val COUNTRYCODEREQUEST = 113
//    override fun onCreate(savedInstanceState: Bundle?)
//    {
//        super.onCreate(savedInstanceState)
//        mContext = this@emailseprateupdate
//        initViewModel()
//        binding = DataBindingUtil.setContentView(this, R.layout.emailseprateedit)
//        binding.setViewModel(mViewModel)
//        editextlistenercall()
//        binding.emailid.requestFocus()
//        AppUtils.showKeyboard(mContext, binding.emailid!!)
//
//    }
//    private fun initViewModel()
//    {
//        mViewModel = ViewModelProviders.of(this).get(emailidupdateViewModel::class.java)
//
//
//        mViewModel.loadmeobserver().observe(this, Observer {
//            if(it == 1)
//            {
//                if(loader.visibility == View.VISIBLE)
//                    commsnackbaralert(getString(R.string.loading))
//                else
//                {
//                    loader.visibility = View.VISIBLE
//                    mViewModel.startmobileapi(mContext,binding.emailid.getText().toString())
//                }
//            }
//        })
//        mViewModel.closeoperationobserver().observe(this, Observer {
//            if(it == 1)
//            {
//                AppUtils.hideKeyboard(mContext, binding.emailid!!)
//                finish()
//            }
//        })
//        mViewModel.clearmobilenumberobserver().observe(this, Observer {
//            if(it == 1)  binding.emailid.getText().clear()
//        })
//        mViewModel.errormessageobserver().observe(this, Observer {
//            AppUtils.commonerrorsheet(mContext,getString(R.string.failed),it)
//        })
//        mViewModel.otpwithstatusobservervalue().observe(this, Observer {
//            val otpoutput = it.split(",").toTypedArray()
//            otppageintent(otpoutput[0],otpoutput[1],otpoutput[2])
//        })
//        mViewModel.countryselectionobserver().observe(this, Observer {
//            if(it == 1)   countryclassintent()
//        })
//    }
//    fun editextlistenercall()
//    {
//        binding.emailid.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(p0: Editable?) {
//            }
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                 if (p0!!.isEmpty())
//                {
//                    binding.nextlayout.visibility = View.INVISIBLE
//                    binding.clear.visibility = View.INVISIBLE
//                }
//                else
//                {
//                    binding.clear.visibility = View.VISIBLE
//                    if (isValidEmail(p0))
//                    {
//                        binding.nextlayout.visibility = View.VISIBLE
//                    }
//                    else
//                    {
//                        binding.nextlayout.visibility = View.INVISIBLE
//                    }
//                }
//            }
//        })
//    }
//    fun countryclassintent()
//    {
//        val intent_book_now = Intent(mContext, CountryCodeSelection::class.java)
//        startActivityForResult(intent_book_now, COUNTRYCODEREQUEST)
//    }
//
//    fun otppageintent(otp:String,otpstatus:String,doaction:String)
//    {
//        val intent_otppage = Intent(mContext, updatepageemailotp::class.java)
//        intent_otppage.putExtra("mobileno","" + binding.emailid.text)
//        intent_otppage.putExtra("otp",otp)
//        intent_otppage.putExtra("otpstatus",otpstatus)
//        intent_otppage.putExtra("doaction",doaction)
//        startActivity(intent_otppage)
//        finish()
//    }
//    //Event Bus Part Listening response from server
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun doThis(intentServiceResult: IntentServiceResult)
//    {
//        loader.visibility = View.INVISIBLE
//        var finalresponse: String = intentServiceResult.resultValue
//        var apiName: String = intentServiceResult.apiName
//        if (apiName.equals(getString(R.string.updateemailidalone)))
//        {
//            if (finalresponse != "failed")  mViewModel.splitresponse(mContext,finalresponse)
//            else AppUtils.commonerrorsheet(mContext,getString(R.string.failed),getString(R.string.failedproblem))
//        }
//    }
//    override fun onResume()
//    {
//        super.onResume()
//        if(!EventBus.getDefault().isRegistered(this))  EventBus.getDefault().register(this)
//    }
//    override fun onDestroy()
//    {
//        super.onDestroy()
//        if(EventBus.getDefault().isRegistered(this))   EventBus.getDefault().unregister(this)
//    }
//
//    //common error notification page
//    fun commsnackbaralert(message:String)
//    {
//        val snack = Snackbar.make(topscreen,message, Snackbar.LENGTH_LONG)
//        var view:View = snack.getView()
//        var params: FrameLayout.LayoutParams = FrameLayout.LayoutParams(view.getLayoutParams())
//        params.gravity = Gravity.TOP;
//        view.setLayoutParams(params)
//        snack.show()
//    }
//    fun isValidEmail(target: CharSequence?): Boolean {
//        return if (TextUtils.isEmpty(target)) {
//            false
//        } else {
//            Patterns.EMAIL_ADDRESS.matcher(target).matches()
//        }
//    }
//
//
//}