package com.mauto.chd.ui.sidemenus

//import android.app.Activity
//import android.content.Intent
//import android.os.Bundle
//import android.text.Editable
//import android.text.TextWatcher
//import android.view.Gravity
//import android.view.View
//import android.widget.FrameLayout
//import androidx.databinding.DataBindingUtil
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import cabily.handyforall.dinedoo.databinding.MobileandemailupdateBinding
//import com.mauto.chd.event_bus_connection.IntentServiceResult
//import com.mauto.chd.commonutils.AppUtils
//import com.mauto.chd.commonutils.Constants
//import com.mauto.chd.ui.registeration.CountryCodeSelection
//import com.mauto.chd.viewmodelformobileupdate.MobileemailupdateViewModel
//import com.google.android.material.snackbar.Snackbar
//import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity
//import kotlinx.android.synthetic.main.mobileandemailupdate.*
//import org.greenrobot.eventbus.EventBus
//import org.greenrobot.eventbus.Subscribe
//import org.greenrobot.eventbus.ThreadMode
//
//class mobileandemailupdate : LocaleAwareCompatActivity()
//{
//    lateinit var binding: MobileandemailupdateBinding
//    private lateinit var mContext: Activity
//    lateinit var mViewModel: MobileemailupdateViewModel
//    var defaultflag:String = "91,IN"
//    private val COUNTRYCODEREQUEST = 113
//    override fun onCreate(savedInstanceState: Bundle?)
//    {
//        super.onCreate(savedInstanceState)
//        mContext = this@mobileandemailupdate
//        initViewModel()
//        binding = DataBindingUtil.setContentView(this, R.layout.mobileandemailupdate)
//        binding.setViewModel(mViewModel)
//        editextlistenercall()
//        binding.mobileno.requestFocus()
//        AppUtils.showKeyboard(mContext, binding.mobileno!!)
//
//    }
//    private fun initViewModel()
//    {
//        mViewModel = ViewModelProviders.of(this).get(MobileemailupdateViewModel::class.java)
//        mViewModel.getflagdetailsfromsession(mContext)
//        mViewModel.flagbase64stringobservervalue().observe(this, Observer {
//            binding.flagimage?.setImageBitmap(it)
//        })
//        mViewModel.countrycodeobservervalue().observe(this, Observer {
//            binding.code.text = "+"+it
//        })
//        mViewModel.loadmeobserver().observe(this, Observer {
//            if(it == 1)
//            {
////                if(loader.visibility == View.VISIBLE)  commsnackbaralert(getString(R.string.loading))
////                else
////                {
////                    loader.visibility = View.VISIBLE
////                    mViewModel.startmobileapi(mContext,binding.mobileno.getText().toString(),binding.code.getText().toString())
////                }
//            }
//        })
//        mViewModel.closeoperationobserver().observe(this, Observer {
//            if(it == 1)
//            {
//                AppUtils.hideKeyboard(mContext, binding.mobileno!!)
//                finish()
//            }
//        })
//        mViewModel.clearmobilenumberobserver().observe(this, Observer {
//            if(it == 1)  binding.mobileno.getText().clear()
//        })
//        mViewModel.errormessageobserver().observe(this, Observer {
//            AppUtils.commonerrorsheet(mContext,getString(R.string.failed),it)
//        })
//        mViewModel.countrycodewithshortnameobservervalue().observe(this, Observer {
//           if(it.equals("0,0"))  mViewModel.defaultflagprocessor(mContext,defaultflag)
//            else   mViewModel.defaultflagprocessor(mContext,it)
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
//        binding.mobileno.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(p0: Editable?) {
//            }
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if (p0!!.length in 1..9)
//                {
//                    binding.clear.visibility = View.VISIBLE
//                    binding.nextlayout.visibility = View.INVISIBLE
//                }
//                else if (p0!!.isEmpty())
//                {
//                    binding.nextlayout.visibility = View.INVISIBLE
//                    binding.clear.visibility = View.INVISIBLE
//                }
//                else  if (p0!!.length >= 10)
//                {
//                    binding.clear.visibility = View.VISIBLE
//                    binding.nextlayout.visibility = View.VISIBLE
//                }
//            }
//        })
//    }
//    fun countryclassintent()
//    {
//        val intent_book_now = Intent(mContext, CountryCodeSelection::class.java)
//        startActivityForResult(intent_book_now, COUNTRYCODEREQUEST)
//    }
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
//    {
//        if (resultCode == Activity.RESULT_OK)
//        {
//            defaultflag = data?.getStringExtra(Constants.INTENT_OBJECT).toString()
//            mViewModel.defaultflagprocessor(mContext,defaultflag)
//        }
//    }
//    fun otppageintent(otp:String,otpstatus:String,doaction:String)
//    {
////        val intent_otppage = Intent(mContext, updatepageotp::class.java)
////        intent_otppage.putExtra("mobileno","" + binding.mobileno.text)
////        intent_otppage.putExtra("otp",otp)
////        intent_otppage.putExtra("otpstatus",otpstatus)
////        intent_otppage.putExtra("doaction",doaction)
////        startActivity(intent_otppage)
////        finish()
//    }
//    //Event Bus Part Listening response from server
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun doThis(intentServiceResult: IntentServiceResult)
//    {
////        loader.visibility = View.INVISIBLE
//        var finalresponse: String = intentServiceResult.resultValue
//        var apiName: String = intentServiceResult.apiName
//        if (apiName.equals(getString(R.string.updatemobilenumber)))
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
//
//}