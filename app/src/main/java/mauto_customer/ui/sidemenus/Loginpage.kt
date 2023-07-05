package mauto_customer.ui.sidemenus

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mauto.chd.BuildConfig
import com.mauto.chd.R
import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.view_model_register_module.RegisterpagetwoViewModel
import com.mauto.chd.commonutils.AppUtils
import com.mauto.chd.commonutils.Constants
import com.mauto.chd.data.LanguageDb
import com.mauto.chd.databinding.LoginPageNewBinding
import com.mauto.chd.ui.registeration.CountryCodeSelection
import com.mauto.chd.ui.registeration.LocaleAwareCompatActivity
import com.mauto.chd.ui.registeration.registerpagewithotpthree

import kotlinx.android.synthetic.main.login_page_new.*
import kotlinx.android.synthetic.main.login_page_new.appversion
import mauto_customer.ui.MainscreenCustomers
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.toast
import org.json.JSONObject

class Loginpage : LocaleAwareCompatActivity()
{
    lateinit var binding: LoginPageNewBinding
    private lateinit var mContext: Activity
    lateinit var mViewModel: RegisterpagetwoViewModel
    var defaultflag:String = "228,TG"
    private val COUNTRYCODEREQUEST = 113
    var Country_name="TG"
    lateinit var mHelpers: LanguageDb
    var app_curent_version:String = "customer"
    var check:String="0"
    var click:String="0"
    var dial_code:String=""
    var apicheck:String="0"
    internal lateinit var mSessionManager: SessionManager
    var app_version:String = ""

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        mContext = this@Loginpage
//        setContentView(R.layout.login_page_new)
        binding = DataBindingUtil.setContentView(this, R.layout.login_page_new)
        mSessionManager = SessionManager(mContext)
        app_version= BuildConfig.VERSION_NAME
        appversion.setText("V "+app_version)


        getappversion()


        Login_with_password.setOnClickListener {
            password_lay.visibility=View.VISIBLE
            Login_with_password.visibility=View.GONE
            Login_with_otp.visibility=View.VISIBLE
            apicheck="1"

            next_new.setText("Login")
            login_text.setText("Enter your phone number and password\nto login to your account")


        }
        Login_with_otp.setOnClickListener {
            login_text.setText("Login with your account password or OTP")
            next_new.setText("Receive OTP")
            Login_with_password.visibility=View.VISIBLE
            Login_with_otp.visibility=View.GONE
            password_lay.visibility=View.GONE
            apicheck="0"
        }
        phone_code.setOnClickListener {
            countryclassintent()
        }
        initViewModel()

        next_new.setOnClickListener {
            if (apicheck.equals("0")){
                startmobileapi(mContext,binding.emailid.getText().toString(),dial_code,Country_name)
            }else{
                startmobileapilogin(mContext,binding.emailid.getText().toString(),dial_code,binding.PasswordEditext.getText().toString())
                Log.d("passwordsschecks",binding.PasswordEditext.getText().toString())
            }

        }


//        mHelpers = LanguageDb(applicationContext)
//
//        binding.mobileno.requestFocus()
//        AppUtils.showKeyboard(mContext, binding.mobileno!!)
//        clear.setOnClickListener {
//            binding.mobileno.getText()?.clear()
//        }
//        binding.langaugechoosen.setOnClickListener {
//            AppUtils.hideKeyboard(mContext, mobileno!!)
//            chooselanguage()
//        }
    }

    fun startmobileapilogin(mContext: Context, mobileno: String, code:String, password:String) {
        progress_lay_spoc.visibility=View.VISIBLE
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.login_with_password))
        intent.putExtra("mobileno", mobileno)
        intent.putExtra("code", code)
        intent.putExtra("pword",password)
        mContext.startService(intent)
    }
    private fun getData() {
        val aIntent = Intent(this, commonapifetchservice::class.java)
        aIntent.putExtra(getString(R.string.intent_putextra_api_key), getString(R.string.dashboarapicall))
        startService(aIntent)
    }


    private fun initViewModel()
    {
        mViewModel = ViewModelProviders.of(this).get(RegisterpagetwoViewModel::class.java)
        mViewModel.getflagdetailsfromsession(mContext)
        mViewModel.flagbase64stringobservervalue().observe(this, Observer {
//            binding.flagimage?.setImageBitmap(it)
        })
        mViewModel.countrycodeobservervalue().observe(this, Observer {
            dial_code= "+"+it
            Log.d("dd4fhhf",it)
            phone_code.setText(dial_code)

//            if (it.equals("91")){
//                Country_name="IN"
//
//            }else{
//                 Country_name="TG"
//
//            }
        })
        mViewModel.countrynameobservervalue().observe(this, Observer {
            Country_name =it
            Log.d("dd4fhhf",it)
//            if (it.equals("91")){
//                Country_name="IN"
//
//            }else{
//                 Country_name="TG"
//
//            }
        })
        mViewModel.loadmeobserver().observe(this, Observer {
            if(it == 1)
            {
//                if(loader.visibility == View.VISIBLE)  commsnackbaralert(getString(R.string.enternumber))
//
//                else
//                {
//                    loader.visibility = View.VISIBLE
//                    mViewModel.startmobileapi(mContext,binding.mobileno.getText().toString(),binding.code.getText().toString(),Country_name)
//                }
            }
        })
        mViewModel.decideclassobserver().observe(this, Observer {
            if(it == 0)
//                    movetoregisterpage()
            else
                getData()

        })

        mViewModel.closeoperationobserver().observe(this, Observer {
            if(it == 1)
            {
//                AppUtils.hideKeyboard(mContext, binding.mobileno!!)
                finish()
            }
        })
//        mViewModel.clearmobilenumberobserver().observe(this, Observer {
//            if(it == 1) binding.mobileno.getText()?.clear()
//        })
        mViewModel.countrycodewithshortnameobservervalue().observe(this, Observer {
           if(it.equals("0,0"))  mViewModel.defaultflagprocessor(mContext,defaultflag)
            else   mViewModel.defaultflagprocessor(mContext,it)
        })
        mViewModel.otpwithstatusobservervalue().observe(this, Observer {
            val otpoutput = it.split(",").toTypedArray()
            otppageintent(otpoutput[0],otpoutput[1],otpoutput[2])
        })
        mViewModel.countryselectionobserver().observe(this, Observer {
            if(it == 1)
                countryclassintent()
        })
    }
    fun startmobileapi(mContext: Context, mobileno: String, code:String, Country_name:String) {
        progress_lay_spoc.visibility=View.VISIBLE
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.registerpageoneapi))
        intent.putExtra("mobileno", mobileno)
        intent.putExtra("code", code)
        intent.putExtra("Country_name",Country_name)
        mContext.startService(intent)
    }
    fun movetomainpage()
    {
        val intent_otppage = Intent(mContext, MainscreenCustomers::class.java)
        intent_otppage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent_otppage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent_otppage)
    }


    //    fun editextlistenercall()
//    {
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

    fun getappversion()
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra("customer", app_curent_version)
        Log.d("vereh","$app_curent_version")
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.version))
        mContext.startService(intent)
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun doThis(intentServiceResult: IntentServiceResult)
    {
        var finalresponse: String = intentServiceResult.resultValue
        var apiName: String = intentServiceResult.apiName
        if (apiName.equals(getString(R.string.registerpageoneapi)))
        {
            progress_lay_spoc.visibility=View.GONE
            val response_json_object = JSONObject(finalresponse)
            val status = response_json_object.getString("status")
            if (status.equals("0")) {
                val response = response_json_object.getString("response")
                toast(response)

            }else if (finalresponse != "failed")
                mViewModel.splitresponse(mContext,finalresponse)

            else AppUtils.commonerrorsheet(mContext,getString(R.string.failed),getString(R.string.failedproblem))
        }
        if (apiName.equals(getString(R.string.version)))
        {
            val response_json_object = JSONObject(finalresponse)
            val status = response_json_object.getString("status")
            if (status.equals("1")) {
                val response = response_json_object.getJSONObject("response")
                var version = response.getString("version")
                if (version.equals("3.2")){

                }else{
                    updateDailog()
                }

            }
        }

        if(apiName.equals(getString(R.string.getapplanuage))){

            var response: String = intentServiceResult.resultValue
            Log.d("cjehshd",response)

//            savelankey(response)

        }else if (apiName.equals(getString(R.string.login_with_password))) {

                val response_json_object = JSONObject(finalresponse)
                val status = response_json_object.getString("status")
                if (status.equals("0")) {
                    progress_lay_spoc.visibility=View.GONE
                    val response = response_json_object.getString("response")
                    toast(response)

                }else if (finalresponse != "failed") {

                mViewModel.splitexistingcall(finalresponse)
            }
        }else if (apiName.equals(getString(R.string.dashboarapicall))) {
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

                    mSessionManager.setdriver_profile_image(agent_image)
                    mSessionManager.setdriver_names(agent_name)
                    mSessionManager.setdriver_vehicle_news(driver_vehicle)
                    mSessionManager.setdriver_vehicle(driver_phone)
////                    progress_lay_otp_page.visibility=View.GONE
                    progress_lay_spoc.visibility=View.GONE

                    movetomainpage()

                }

            }

        }
    }
    fun updateDailog() {

        val dialog = Dialog(mContext, android.R.style.Theme_Light_NoTitleBar_Fullscreen)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.app_update_dialog)
        dialog.window!!.setGravity(Gravity.CENTER)

        var close = dialog.findViewById<TextView>(R.id.close)
        var update = dialog.findViewById<TextView>(R.id.update)
        Log.d("vaytkfw","mali va;;")

        update.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.mauto.customer"))
            startActivity(intent)
            toast("upadet the app")
        }

        close.setOnClickListener{
            super.onBackPressed()

        }
        dialog.show()

    }
    fun countryclassintent()
    {
        val intent_book_now = Intent(mContext, CountryCodeSelection::class.java)
        startActivityForResult(intent_book_now, COUNTRYCODEREQUEST)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_FIRST_USER)
        {
            Log.d("dgfhdfhd","sghfhsfghd")
            Log.d("d555dfhddf34d",data.toString())

            defaultflag = data?.getStringExtra(Constants.INTENT_OBJECT).toString()

            mViewModel.defaultflagprocessor(mContext,defaultflag)
        }
    }
    fun otppageintent(otp:String,otpstatus:String,doaction:String)
    {
        val intent_otppage = Intent(mContext, registerpagewithotpthree::class.java)
        intent_otppage.putExtra("mobileno","" + binding.emailid.text)
        intent_otppage.putExtra("otp",otp)
        intent_otppage.putExtra("code",""+dial_code)
        intent_otppage.putExtra("otpstatus",otpstatus)
        intent_otppage.putExtra("doaction",doaction)
        startActivity(intent_otppage)
    }
    //Event Bus Part Listening response from server

    override fun onResume()
    {
        super.onResume()
        if(!EventBus.getDefault().isRegistered(this))  EventBus.getDefault().register(this)
    }
    override fun onDestroy()
    {
        super.onDestroy()
        if(EventBus.getDefault().isRegistered(this))   EventBus.getDefault().unregister(this)
    }
    // langauge change part
    //common error notification page
}