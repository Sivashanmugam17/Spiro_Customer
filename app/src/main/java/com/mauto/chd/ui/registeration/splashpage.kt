package com.mauto.chd.ui.registeration

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.IntentSender
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.view_model_register_module.SplashpageViewModel
import com.mauto.chd.commonutils.AppUtils
import com.mauto.chd.data.LanguageDb
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.tasks.Task
import com.mauto.chd.BuildConfig
import com.mauto.chd.Locales
import com.mauto.chd.R
import kotlinx.android.synthetic.main.login_page_new.*

import kotlinx.android.synthetic.main.splashpage.*
import kotlinx.android.synthetic.main.splashpage.appversion
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.toast
import org.json.JSONException
import org.json.JSONObject

class splashpage : AppCompatActivity()
{
    private lateinit var mContext: Activity
    lateinit var mViewModel: SplashpageViewModel
    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 1000
    var hassessionn:Boolean = false
    var stepsvalue:String = ""
    private var installStateUpdatedListener: InstallStateUpdatedListener? = null
    private var appUpdateManager: AppUpdateManager? = null
    private val REQ_CODE_VERSION_UPDATE = 530
    lateinit var mHelpers: LanguageDb
    var lang_type:String = "fr"
    var app_version:String = ""
    var app_curent_version:String = "customer"

    internal lateinit var mSessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashpage)
        mContext = this@splashpage
        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        mHelpers = LanguageDb(applicationContext)
        mSessionManager = SessionManager(mContext)
        lang_type=mSessionManager.getseletedlagname()
        app_version= BuildConfig.VERSION_NAME
        appversion.setText("V "+app_version)
        Log.d("mapp_version",app_version)
        mSessionManager.setoffer_banner_image("NO")
//        getappversion()
        checkForAppUpdate()
        initViewModel()

//        checkForAppUpdate()
//        initViewModel()           // initlizing view model
    }
    private fun initViewModel()
    {
        mViewModel = ViewModelProviders.of(this).get(SplashpageViewModel::class.java)
        mViewModel.checkautvalue(mContext)
        mViewModel.getMainPage(mContext)
        mViewModel.hassessionobservervalue().observe(this, Observer {
           if(it == false)
               mViewModel.getLoginsession(mContext)
            else
               mViewModel.mainpage(mContext)
        })
        mViewModel.sessionobservervalue().observe(this, Observer {
            hassessionn=it
            mViewModel.getStepsValue(mContext)
        })
        mViewModel.getauthval().observe(this, Observer {
            if(!EventBus.getDefault().isRegistered(this))
                EventBus.getDefault().register(this)
        })
        mViewModel.getstepsobservervalue().observe(this, Observer {
            stepsvalue=it
            mDelayHandler = Handler()
            mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)
        })
    }
    // post delayed class
    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing) movetonextpage()
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

        // clearing of used operation
    public override fun onDestroy()
    {
        if (mDelayHandler != null)
        {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }
        if(EventBus.getDefault().isRegistered(this))  EventBus.getDefault().unregister(this)
        unregisterInstallStateUpdListener()
        super.onDestroy()
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun doThis(intentServiceResult: IntentServiceResult)
    {
        var finalresponse: String = intentServiceResult.resultValue
        var apiName: String = intentServiceResult.apiName

        if (apiName.equals(getString(R.string.version)))
        {
            val response_json_object = JSONObject(finalresponse)
            val status = response_json_object.getString("status")
            if (status.equals("1")) {
                val response = response_json_object.getJSONObject("response")
                var version = response.getString("version")
                if (version.equals("3.1")){

                }else{
                    updateDailog()
                }

            }
        }
        if (apiName.equals(getString(R.string.getappinfohit)))
        {
            Log.d("zdfdzf","dfdsfdf")
            var response: String = intentServiceResult.resultValue
            Log.d("checkingappinfo",response)
            if (response == "1")  movetonextpage()
            else  AppUtils.commonerrorsheet(mContext,getString(R.string.failed),getString(R.string.failedproblem))
        }

        if(apiName.equals(getString(R.string.getapplanuage))){

            var response: String = intentServiceResult.resultValue
            Log.d("cjehshd",response)

            savelankey(response)

        }

    }


    private fun getappinfodetails()
    {
        val registerpagetwopage = Intent(applicationContext, commonapifetchservice::class.java)
        registerpagetwopage.putExtra(getString(R.string.intent_putextra_api_key), getString(R.string.getappinfohit))
        startService(registerpagetwopage)
    }

    fun savelankey(response: String)
    {
        try
        {
            val response_json_object = JSONObject(response)
            Log.d("langkey", response_json_object.toString())

            val status = response_json_object.getString("status")
            val info = `response_json_object`.getJSONObject("response")
            if (status.equals("1"))
            {
                deleteDatabase(LanguageDb.DATABASE_NAME)
                val language_key = info.getJSONObject("content")
                val iter = language_key.keys()
                while (iter.hasNext()) {
                    val key = iter.next().toString()
                    try {
                        val value = language_key[key].toString()
                        println("Keysssssxd--$key--$value")
                        mHelpers.saveData(key, value.toString())
                        Log.d("dfdyyggcghghhh", value.toString())
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                mHelpers.closedatabase();

            }
            else
            {

            }
        }
        catch (e: Exception) {
            Log.d("dfdyyggcghghhh", e.toString())

        }
    }


    private fun getapplanuage()
    {
        val registerpagetwopage = Intent(applicationContext, commonapifetchservice::class.java)
        registerpagetwopage.putExtra(getString(R.string.intent_putextra_api_key), getString(R.string.getapplanuage))
        startService(registerpagetwopage)
    }
     fun getappversion()
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra("customer", app_curent_version)
        Log.d("vereh","$app_curent_version")
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.version))
        mContext.startService(intent)
    }

    // Decide which page to move on
    fun movetonextpage()
    {
       if (hassessionn == true)  mViewModel.documentpageone(mContext)
       else mViewModel.registerpageones(mContext)
    }

    private fun checkForAppUpdate() {

        val appUpdateInfoTask: Task<AppUpdateInfo> = appUpdateManager!!.appUpdateInfo

        installStateUpdatedListener = InstallStateUpdatedListener { installState ->
            if (installState.installStatus() == InstallStatus.DOWNLOADED){
                unregisterInstallStateUpdListener()

                getappinfodetails() // App info call hit
                getapplanuage()

            }

        }

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                println("Update Availabe....")
                appUpdateManager!!.registerListener(installStateUpdatedListener!!)
                if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    startAppUpdateImmediate(appUpdateInfo)
                } /*else if (appUpdateInfo.isUpdateTypeAllowed(FLEXIBLE)) {
                startAppUpdateFlexible(appUpdateInfo)
            }*/
            } else {
                println("Update Not Availabe....")
                getappinfodetails() // App info call hit
                getapplanuage()

            }
        }
    }
    private fun unregisterInstallStateUpdListener() {
        if (appUpdateManager != null && installStateUpdatedListener != null) appUpdateManager!!.unregisterListener(installStateUpdatedListener!!)
    }

    private fun startAppUpdateImmediate(appUpdateInfo: AppUpdateInfo) {
        try {
            appUpdateManager!!.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.IMMEDIATE,  // The current activity making the update request.
                    this,  // Include a request code to later monitor this update request.
                    REQ_CODE_VERSION_UPDATE)
        } catch (e: IntentSender.SendIntentException) {
            e.printStackTrace()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_CODE_VERSION_UPDATE -> {
                if (resultCode !== RESULT_OK) { //RESULT_OK / RESULT_CANCELED / RESULT_IN_APP_UPDATE_FAILED
                    Log.e("Update flow code", resultCode.toString())
                    // If the update is cancelled or fails,
                    // you can request to start the update again.
                    unregisterInstallStateUpdListener()
                }
            }
        }
    }
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


    override fun onResume() {
        super.onResume()
        if(!EventBus.getDefault().isRegistered(this))  EventBus.getDefault().register(this)
        getappinfodetails() // App info call hit

//        val config = resources.configuration
//        val lang = "fa" // your language code
//        val locale = Locale(lang)
//        Locale.setDefault(locale)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
//            config.setLocale(locale)
//        else
//            config.locale = locale
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
//            createConfigurationContext(config)
//        resources.updateConfiguration(config, resources.displayMetrics)
    }
}