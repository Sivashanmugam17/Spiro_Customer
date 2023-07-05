package com.mauto.chd.ui.sidemenus

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*

import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.commonutils.AppUtils
import com.mauto.chd.data.LanguageDb
import com.mauto.chd.ui.registeration.LocaleAwareCompatActivity
import com.mauto.chd.ui.registeration.vehicletypeCustomAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.mauto.chd.BuildConfig
import com.mauto.chd.R
import kotlinx.android.synthetic.main.activity_support.*
import kotlinx.android.synthetic.main.activity_support.appversionsupport
import kotlinx.android.synthetic.main.activity_support.backbutton
import kotlinx.android.synthetic.main.activity_support.call_lay
import kotlinx.android.synthetic.main.activity_support.email_click
import kotlinx.android.synthetic.main.activity_support.nointernetconnectionlay
import kotlinx.android.synthetic.main.activity_support.report_mail_tv
import kotlinx.android.synthetic.main.activity_support.report_send
import kotlinx.android.synthetic.main.activity_support.report_spinner
import kotlinx.android.synthetic.main.activity_support.report_spinnericon
import kotlinx.android.synthetic.main.activity_support.report_title_tv
import kotlinx.android.synthetic.main.activity_support.type_your_comment
import kotlinx.android.synthetic.main.activity_voucher_purchesd.*
import kotlinx.android.synthetic.main.maipagedesign.*
import kotlinx.android.synthetic.main.nointernetconnection.*
//import kotlinx.android.synthetic.main.otpride.*
//import kotlinx.android.synthetic.main.registerpagefour.*
//import kotlinx.android.synthetic.main.requestpagedfa.*
//import kotlinx.android.synthetic.main.requestpagedfa.coordinate_bottom_sheet_ride_book_now
import mauto_customer.ui.MainscreenCustomers
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

class Voucher_purchesd : LocaleAwareCompatActivity() {
    private lateinit var mContext: Activity
    internal lateinit var mSessionManager: SessionManager
    var customerphno:String = ""
    var bottomSheetDialog: BottomSheetDialog? = null
    var supportemail ="support.melectric@gmail.com"
    var selectedlanage: String? = "English"
    private val RESULT_PAYMENT_METHOD: Int = 1001
    lateinit var mHelpers: LanguageDb
    var app_version:String = ""
    var currencycode:String=""
    var voucher_count:String=""
    var addamountnew:String = ""
    var voucher_purchase_value: String? = ""
    var curency_id:String?=""

    var spinner: Spinner?=null
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
        setContentView(R.layout.activity_voucher_purchesd)
        mContext = this@Voucher_purchesd
        mSessionManager = SessionManager(mContext)
        mHelpers = LanguageDb(applicationContext)
        app_version= BuildConfig.VERSION_NAME
        appversionsupport.setText("V "+app_version)
        currencycode = intent.getStringExtra("currencycode")!!
        voucher_purchase_value = intent.getStringExtra("voucher_purchase_value")!!
        curency_id= mSessionManager.getwcurncy_id()
        Log.d("fgisdfhlsjfdlk","$curency_id")
        val languages = resources.getStringArray(R.array.vouchers)
//        lg_keyset()
//        vehicle_select()
        backbutton.setOnClickListener {
            val intent2 = Intent(mContext, MainscreenCustomers()::class.java)
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mContext.startActivity(intent2)
        }
        call_lay.setOnClickListener {
            callcustomer()
        }
        connection_lay2.setOnClickListener {
            recreate()
        }

        spinner = findViewById<Spinner>(R.id.amount_select)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, languages
            )
            spinner!!.adapter = adapter

            spinner!!.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    amount!!.setText(languages!![position])
//                    Toast.makeText(this@MainActivity,
//                        getString(R.string.selected_item) + " " +
//                                "" + languages[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }



        }

        btn_login.setOnClickListener{
            voucher_count=amount.text.toString()
            addamountnew=((voucher_count.toInt() * voucher_purchase_value!!.toInt()).toString())
            Log.d("vosieme","$addamountnew")
            val intent_edit_address = Intent(this, PaymentOtherTypePage::class.java)
            intent_edit_address.putExtra("amount", addamountnew)
            intent_edit_address.putExtra("simble", curency_id)
            intent_edit_address.putExtra("voucher_count", voucher_count)
            startActivityForResult(intent_edit_address, RESULT_PAYMENT_METHOD)

        }


        report_spinnericon.setOnClickListener{
            report_spinner.performClick()
        }
        email_click.setOnClickListener {
            sendEmail(this,supportemail)
        }



        report_send.setOnClickListener {
            if (type_your_comment.text.toString().isEmpty()){
                Toast.makeText(this, "Type Your comments", Toast.LENGTH_SHORT).show()

            }else{
//                Toast.makeText(this, "Report has been submitted successfully", Toast.LENGTH_SHORT).show()
//                type_your_comment.text.clear()
//                successdialog()
              mSessionManager.setsupport_messeage(type_your_comment.text.toString())

                getreportsubmit()

            }

        }
    }
    private fun getkey(key: String): String? {
        return mHelpers.getvalueforkey(key)
    }


    fun sendEmail(activity:Activity, email: String) {
        val to = arrayOf(email)
        val cc = arrayOf("")
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"))

            intent.putExtra(Intent.EXTRA_EMAIL, to)/*
            intent.putExtra(Intent.EXTRA_SUBJECT, sub)
            intent.putExtra(Intent.EXTRA_TEXT, text)*/
            activity.startActivityForResult(intent,333)

        } catch (e: PackageManager.NameNotFoundException) {

            Toast.makeText(activity, "Email has sent successfully", Toast.LENGTH_SHORT).show()
        }

    }

    fun getreportsubmit() {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.reports_submit))
        mContext.startService(intent)
    }

    fun successdialog()
    {

        bottomSheetDialog = BottomSheetDialog(this)
        val view = getLayoutInflater().inflate(R.layout.successdialogsupport, null);
        bottomSheetDialog!!.setContentView(view)
        bottomSheetDialog!!.setCancelable(false)
        val title = view.findViewById(R.id.title) as TextView



        val done = view.findViewById(R.id.done) as LinearLayout
        done.setOnClickListener {
                type_your_comment.text.clear()

            bottomSheetDialog!!.dismiss()

        }
        bottomSheetDialog!!.show()
    }
    private fun lg_keyset(){
        report_mail_tv.setText(mSessionManager.getsupport_email())
//        batery_low_tv.setText(getkey("Contact Us"))
//        bottomsheet_btn.setText("How Can We Help You?")
//        call_tv.setText("Call Customer Support")
        report_title_tv.setText("Support")
        report_send.setText("Send Your Comments")
//        report_mail_title_tv.setText("Send us e-mail")


    }





    private fun vehicle_select() {
        var vehicle= ArrayList<String>()
//             vehicle.addAll(VehicleDetailsViewModel.myCategorytype)
//
            vehicle.add(getString(R.string.select_report_issue))

//            vehicle.add("Earnings not credited")
//        vehicle.add("Auto")

        val list= JSONArray(mSessionManager.getreportlist())
//             vehicle.addAll(VehicleDetailsViewModel.myCategorytype)
//        vehicle.add("select  report issuse")
        for (a in 0..list.length()-1){
            vehicle.add(list[a].toString())
        }
        if (report_spinner != null) {
            val adapterd = vehicletypeCustomAdapter(this, vehicle)
            report_spinner.adapter = adapterd
            report_spinner.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {

                    mSessionManager.setsupport_subject("Earnings not credited")


                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
    }



    //calling customer
    fun callcustomer() {

        customerphno=mSessionManager.getsupport_number()
            if(customerphno.equals(""))
                commsnackbaralert(getString(R.string.nonotavaialbe))
            else
            {
                val intent =  Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", customerphno, null));
                startActivity(intent);
            }

    }


    //common error notification page
    fun commsnackbaralert(message:String)
    {
        val snack = Snackbar.make(coordinate_bottom_sheet_ride_book_now,message, Snackbar.LENGTH_LONG)
        var view: View = snack.getView()
        var params: FrameLayout.LayoutParams = FrameLayout.LayoutParams(view.getLayoutParams())
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params)
        snack.show()
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
        if (apiName.equals(getString(R.string.reports_submit))) {
            if (finalresponse == "failed") {
//                toast(getString(R.string.api_failure_toast_label))
            } else {
                Log.d("chekreport","shjccbdhj")
                Log.d("dfhcdccadsfb",finalresponse)
                val response_json_object = JSONObject(finalresponse)

                    val status = response_json_object.getString("status")
                    if (status.equals("1")) {
                        successdialog()


                    }

            }
        }
    }

}