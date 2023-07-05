package mauto_customer.ui.sidemenus

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.commonutils.AppUtils
import com.mauto.chd.commonutils.Constants
import com.mauto.chd.data.LanguageDb
import com.mauto.chd.ui.registeration.CountryCodeSelection
import com.mauto.chd.ui.registeration.LocaleAwareCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mauto.chd.BuildConfig
import com.mauto.chd.R
import com.mauto.chd.ui.sidemenus.driverprofile
import kotlinx.android.synthetic.main.activity_referrals.*
import kotlinx.android.synthetic.main.activity_referrals.appversionnew
import kotlinx.android.synthetic.main.activity_referrals.editTextTextPersonName
import kotlinx.android.synthetic.main.activity_referrals.editTextTextPersonName2
import kotlinx.android.synthetic.main.activity_referrals.editTextTextPersonName3
import kotlinx.android.synthetic.main.activity_referrals.phone_code_textview
import kotlinx.android.synthetic.main.activity_referrals.progress_time_referrals
import kotlinx.android.synthetic.main.activity_referrals.refer_lay
import kotlinx.android.synthetic.main.activity_support.backbutton
import kotlinx.android.synthetic.main.activity_support.nointernetconnectionlay
import kotlinx.android.synthetic.main.mainpage_spoc.*
import kotlinx.android.synthetic.main.stactions_sp.*
import mauto_customer.ui.MainscreenCustomers
import mauto_customer.ui.mainpage.Servicestations
import mauto_customer.ui.mainpage.Swapstations
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.util.*

class Stations : LocaleAwareCompatActivity() {
    private lateinit var mContext: Activity
    internal lateinit var mSessionManager: SessionManager
    var customerphno:String = ""
    var bottomSheetDialog: BottomSheetDialog? = null
    var supportemail ="support.melectric@gmail.com"
    var selectedlanage: String? = "English"
    lateinit var mHelpers: LanguageDb
    var defaultflag:String = "228,TG"
    private val COUNTRYCODEREQUEST = 113
    private var phone_codess: String? ="+91"
    var app_version:String = ""

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this);

    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this);

        if (AppUtils.isNetworkAvailable(mContext)) {
            nointernetconnectionlay.visibility=View.GONE

        }else{

            nointernetconnectionlay.visibility=View.VISIBLE

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this);

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stactions_sp)
        mContext = this@Stations
        mSessionManager = SessionManager(mContext)
        mHelpers = LanguageDb(applicationContext)
        app_version= BuildConfig.VERSION_NAME
        appversionnew.setText("V "+app_version)
        backbutton.setOnClickListener {
            val intent2 = Intent(mContext, MainscreenCustomers()::class.java)
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mContext.startActivity(intent2)
        }

        swapstation.setOnClickListener {
            val intent2 = Intent(mContext, Swapstations()::class.java)
//            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mContext.startActivity(intent2)
        }
        srvice.setOnClickListener {
            val intent2 = Intent(mContext, Servicestations()::class.java)
//            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mContext.startActivity(intent2)
        }
        phone_code_textview.setOnClickListener {
            countryclassintent()
        }
        refer_lay.setOnClickListener {
            if (editTextTextPersonName.text.toString().isNotEmpty() && editTextTextPersonName2.text.toString().isNotEmpty() && editTextTextPersonName3.text.toString().isNotEmpty()){
                getreferrals()

            }else{
                toast("Type your fields")

            }
        }



    }
    fun defaultflagprocessor(mContext: Context, defaultflagcode:String) {
        val codeNameArray = defaultflagcode.split(",")
        val stringBuilder = StringBuilder()
        stringBuilder.append(getCountryZipCode(codeNameArray[1].trim()))
        val codes = codeNameArray[0].trim()
        val imageName = codeNameArray[1].trim()
        val imageBuilder = StringBuilder()
        imageBuilder.append("flags/flag_").append(imageName.toLowerCase()).append(".png")
        val inputStream = mContext.assets.open(imageBuilder.toString())
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        val flagbase64string = Base64.encodeToString(b, Base64.DEFAULT)
//    mSessionManager = SessionManager(mContext)
//    mSessionManager!!.setFlagDetails(flagbase64string,codes,imageName)
//    bitmapimage.value=StringToBitMap(flagbase64string)
        phone_code_textview.setText("+"+codes).toString()

    }
    private fun getCountryZipCode(ssid: String): String
    {
        val loc = Locale("", ssid)
        return loc.displayCountry.trim { it <= ' ' }
    }
    fun getreferrals() {
        progress_time_referrals.visibility=View.VISIBLE
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra("name", editTextTextPersonName.text.toString())
        intent.putExtra("dial_code", phone_code_textview.text.toString())
        intent.putExtra("phone_number", editTextTextPersonName2.text.toString())
        intent.putExtra("email", editTextTextPersonName3.text.toString())
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.cusstermo_referra))
        mContext.startService(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_FIRST_USER)
        {
            Log.d("dgfhdfhd","sghfhsfghd")
            Log.d("d555dfhddf34d",data.toString())

            defaultflag = data?.getStringExtra(Constants.INTENT_OBJECT).toString()

            defaultflagprocessor(mContext,defaultflag)
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun doThis(intentServiceResult: IntentServiceResult) {
        var apiName: String = intentServiceResult.apiName
        var finalresponse: String = intentServiceResult.resultValue
        if (apiName.equals(getString(R.string.cusstermo_referra))) {
            if (finalresponse == "failed") {
//                toast(getString(R.string.api_failure_toast_label))
            } else {
                Log.d("chekreport","shjccbdhj")
                Log.d("checvkjckj",finalresponse)
                val response_json_object = JSONObject(finalresponse)
                progress_time_referrals.visibility=View.GONE
                val status = response_json_object.getString("status")
                if (status.equals("1")) {
                    val response = response_json_object.getJSONObject("response")
                    var message=response.getString("message")

                    successdialog(message)
                }else if (status.equals("0")){
                    val response = response_json_object.getString("response")
                    toast(response)
                }

            }
        }
    }
    fun successdialog(message:String)
    {

        bottomSheetDialog = BottomSheetDialog(this)
        val view = getLayoutInflater().inflate(R.layout.successdialogsupport, null);
        bottomSheetDialog!!.setContentView(view)
        bottomSheetDialog!!.setCancelable(false)
        val title = view.findViewById(R.id.title) as TextView
        title.setText(message)

        val done = view.findViewById(R.id.done) as LinearLayout
        done.setOnClickListener {
            bottomSheetDialog!!.dismiss()
            val intent2 = Intent(mContext, MainscreenCustomers()::class.java)
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mContext.startActivity(intent2)

        }
        bottomSheetDialog!!.show()
    }

    fun countryclassintent()
    {
        val intent_book_now = Intent(mContext, CountryCodeSelection::class.java)
        startActivityForResult(intent_book_now, COUNTRYCODEREQUEST)
    }

    override fun onBackPressed() {
        val intent2 = Intent(mContext, MainscreenCustomers()::class.java)
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        mContext.startActivity(intent2)
    }


    }



//method for getting country code








//    fun sendEmail(activity: Activity, email: String) {
//        val to = arrayOf(email)
//        val cc = arrayOf("")
//        try {
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"))
//
//            intent.putExtra(Intent.EXTRA_EMAIL, to)/*
//            intent.putExtra(Intent.EXTRA_SUBJECT, sub)
//            intent.putExtra(Intent.EXTRA_TEXT, text)*/
//            activity.startActivityForResult(intent,333)
//
//        } catch (e: PackageManager.NameNotFoundException) {
//
//            Toast.makeText(activity, "Email has sent successfully", Toast.LENGTH_SHORT).show()
//        }
//
//    }
//

    private fun lg_keyset(){
//        report_mail_tv.setText(mSessionManager.getsupport_email())
////        batery_low_tv.setText(getkey("Contact Us"))
//        bottomsheet_btn.setText(getkey("how_can_we_help_you"))
//        call_tv.setText(getkey("call_customer_support"))
//        report_title_tv.setText(getkey("support"))
//        report_send.setText(getkey("send_your_comments"))
//        report_mail_title_tv.setText(getkey("send_us_e-mail"))


    }








    //calling customer


    //common error notification page


