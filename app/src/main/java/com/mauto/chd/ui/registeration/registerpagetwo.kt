package com.mauto.chd.ui.registeration

//import android.app.Activity
//import android.content.Intent
//import android.os.Bundle
//import android.text.Editable
//import android.text.TextWatcher
//import android.util.Log
//import android.view.Gravity
//import android.view.View
//import android.widget.FrameLayout
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.databinding.DataBindingUtil
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import cabily.handyforall.dinedoo.R
//import cabily.handyforall.dinedoo.databinding.RegisterpagetwoBinding
//import com.mauto.chd.backgroundservices.commonapifetchservice
//import com.mauto.chd.event_bus_connection.IntentServiceResult
//import com.mauto.chd.Locales
//import com.mauto.chd.view_model_register_module.RegisterpagetwoViewModel
//import com.mauto.chd.commonutils.AppUtils
//import com.mauto.chd.commonutils.Constants
//import com.mauto.chd.data.LanguageDb
//import com.google.android.material.bottomsheet.BottomSheetDialog
//import com.google.android.material.snackbar.Snackbar
//import kotlinx.android.synthetic.main.registerpagetwo.*
//import org.greenrobot.eventbus.EventBus
//import org.greenrobot.eventbus.Subscribe
//import org.greenrobot.eventbus.ThreadMode
//import org.json.JSONException
//import org.json.JSONObject
//
//class registerpagetwo : LocaleAwareCompatActivity()
//{
//    lateinit var binding: RegisterpagetwoBinding
//    private lateinit var mContext: Activity
//    lateinit var mViewModel: RegisterpagetwoViewModel
//    var defaultflag:String = "228,TG"
//    private val COUNTRYCODEREQUEST = 113
//    var Country_name="TG"
//    lateinit var mHelpers: LanguageDb
//
//    var check:String="0"
//    var click:String="0"
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        mContext = this@registerpagetwo
//        initViewModel()
//        binding = DataBindingUtil.setContentView(this, R.layout.registerpagetwo)
//        mHelpers = LanguageDb(applicationContext)
//        binding.setViewModel(mViewModel)
//        text_changesListeners()
//        getapplanuage()
//        editextlistenercall()
//        binding.mobileno.requestFocus()
//        AppUtils.showKeyboard(mContext, binding.mobileno!!)
//        clear.setOnClickListener {
//            binding.mobileno.getText()?.clear()
//        }
////        binding.langaugechoosen.setOnClickListener {
////            AppUtils.hideKeyboard(mContext, mobileno!!)
////            chooselanguage()
////        }
//    }
//    private fun getapplanuage()
//    {
//        val registerpagetwopage = Intent(applicationContext, commonapifetchservice::class.java)
//        registerpagetwopage.putExtra(getString(R.string.intent_putextra_api_key), getString(R.string.getapplanuage))
//        startService(registerpagetwopage)
//    }
//
//    private fun text_changesListeners() {
//        privacy_check.setBackgroundResource(R.drawable.ic_rectange)
//
//        privacy_check.setOnClickListener {
//
//            if (click.equals("0")){
//                privacy_check.setBackgroundResource(R.drawable.ic_check_box)
//                check="1"
//                click="1"
//            }else{
//                privacy_check.setBackgroundResource(R.drawable.ic_rectange)
//                click="0"
//                check="0"
//            }
//
//
//        }
//
//    }
//
//    private fun initViewModel()
//    {
//        mViewModel = ViewModelProviders.of(this).get(RegisterpagetwoViewModel::class.java)
//        mViewModel.getflagdetailsfromsession(mContext)
//        mViewModel.flagbase64stringobservervalue().observe(this, Observer {
//            binding.flagimage?.setImageBitmap(it)
//        })
//        mViewModel.countrycodeobservervalue().observe(this, Observer {
//            binding.code.text = "+"+it
//            Log.d("dd4fhhf",it)
////            if (it.equals("91")){
////                Country_name="IN"
////
////            }else{
////                 Country_name="TG"
////
////            }
//        })
//        mViewModel.countrynameobservervalue().observe(this, Observer {
//            Country_name =it
//            Log.d("dd4fhhf",it)
////            if (it.equals("91")){
////                Country_name="IN"
////
////            }else{
////                 Country_name="TG"
////
////            }
//        })
//        mViewModel.loadmeobserver().observe(this, Observer {
//            if(it == 1)
//            {
////                if(loader.visibility == View.VISIBLE)  commsnackbaralert(getString(R.string.enternumber))
////
////                else
////                {
////                    loader.visibility = View.VISIBLE
////                    mViewModel.startmobileapi(mContext,binding.mobileno.getText().toString(),binding.code.getText().toString(),Country_name)
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
//            if(it == 1) binding.mobileno.getText()?.clear()
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
//            if(it == 1)
//                countryclassintent()
//        })
//    }
//    fun editextlistenercall() {
//        binding.mobileno.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(p0: Editable?) {
//            }
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if (p0!!.length in 1..7)
//                {
//                    binding.clear.visibility = View.VISIBLE
//                    binding.nextlayout.visibility = View.INVISIBLE
//                }
//                else if (p0!!.isEmpty())
//                {
//                    binding.nextlayout.visibility = View.INVISIBLE
//                    binding.clear.visibility = View.INVISIBLE
//                }
//                else  if (p0!!.length >= 8)
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
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_FIRST_USER)
//        {
//            Log.d("dgfhdfhd","sghfhsfghd")
//            Log.d("d555dfhddf34d",data.toString())
//
//            defaultflag = data?.getStringExtra(Constants.INTENT_OBJECT).toString()
//
//            mViewModel.defaultflagprocessor(mContext,defaultflag)
//        }
//    }
//    fun otppageintent(otp:String,otpstatus:String,doaction:String)
//    {
//        val intent_otppage = Intent(mContext, registerpagewithotpthree::class.java)
//        intent_otppage.putExtra("mobileno","" + binding.mobileno.text)
//        intent_otppage.putExtra("otp",otp)
//        intent_otppage.putExtra("code",""+binding.code.text)
//        intent_otppage.putExtra("otpstatus",otpstatus)
//        intent_otppage.putExtra("doaction",doaction)
//        startActivity(intent_otppage)
//    }
//    //Event Bus Part Listening response from server
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun doThis(intentServiceResult: IntentServiceResult)
//    {
////        loader.visibility = View.INVISIBLE
//        var finalresponse: String = intentServiceResult.resultValue
//        var apiName: String = intentServiceResult.apiName
//        if (apiName.equals(getString(R.string.registerpageoneapi)))
//        {
//            if (finalresponse != "failed")  mViewModel.splitresponse(mContext,finalresponse)
//            else AppUtils.commonerrorsheet(mContext,getString(R.string.failed),getString(R.string.failedproblem))
//        }
//        if(apiName.equals(getString(R.string.getapplanuage))){
//
//            var response: String = intentServiceResult.resultValue
//            Log.d("cjehshd",response)
//
//            savelankey(response)
//
//        }
//    }
//    fun savelankey(response: String)
//    {
//        try
//        {
//            val response_json_object = JSONObject(response)
//            Log.d("langkey", response_json_object.toString())
//
//            val status = response_json_object.getString("status")
//            val info = `response_json_object`.getJSONObject("response")
//            if (status.equals("1"))
//            {
//                deleteDatabase(LanguageDb.DATABASE_NAME)
//                val language_key = info.getJSONObject("content")
//                val iter = language_key.keys()
//                while (iter.hasNext()) {
//                    val key = iter.next().toString()
//                    try {
//                        val value = language_key[key].toString()
//                        println("Keysssssxd--$key--$value")
//                        mHelpers.saveData(key, value.toString())
//                        Log.d("dfdyyggcghghhh", value.toString())
//                    } catch (e: JSONException) {
//                        e.printStackTrace()
//                    }
//                }
//                mHelpers.closedatabase();
//
//            }
//            else
//            {
//
//            }
//        }
//        catch (e: Exception) {
//            Log.d("dfdyyggcghghhh", e.toString())
//
//        }
//    }
//
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
//    // langauge change part
//    fun chooselanguage()
//    {
//        var languagedialog =  BottomSheetDialog(this)
//        val view = getLayoutInflater().inflate(R.layout.chooselanguagesheet, null);
//        languagedialog!!.setContentView(view)
//        languagedialog!!.setCancelable(true)
//        val cloepage = view.findViewById(R.id.cloepageoflanguage) as LinearLayout
//        val englishchoosen = view.findViewById(R.id.englishchoosen) as TextView
//        val tamilchoosen = view.findViewById(R.id.tamilchoosen) as TextView
//        val urduchoosen = view.findViewById(R.id.urduchoosen) as TextView
//        englishchoosen.setOnClickListener {
//            mViewModel.languagechoosen(mContext,"2")
//            languagedialog!!.dismiss()
//            updateLocale(Locales.English)
//            closeallactivity()
//        }
//        tamilchoosen.setOnClickListener {
//            mViewModel.languagechoosen(mContext,"1")
//            languagedialog!!.dismiss()
//            updateLocale(Locales.Tamil)
//            closeallactivity()
//        }
//        urduchoosen.setOnClickListener {
//            mViewModel.languagechoosen(mContext,"3")
//            languagedialog!!.dismiss()
//            updateLocale(Locales.Urdu)
//            closeallactivity()
//        }
//        cloepage.setOnClickListener {
//            languagedialog!!.dismiss()
//        }
//        languagedialog!!.show()
//    }
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
//    fun closeallactivity()
//    {
//        val intent2 = Intent(mContext, splashpage::class.java)
//        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        mContext.startActivity(intent2)
//    }
//}