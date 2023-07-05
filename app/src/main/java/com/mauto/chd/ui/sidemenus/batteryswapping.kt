//package com.cabilyhandyforalldinedoo.chd.ui.sidemenus
//
//import android.Manifest
//import android.app.Activity
//import android.app.Dialog
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.graphics.Color
//import android.graphics.drawable.ColorDrawable
//import android.net.Uri
//import android.os.Bundle
//import android.provider.Settings
//import android.util.Log
//import android.view.Gravity
//import android.view.View
//import android.view.Window
//import android.widget.FrameLayout
//import android.widget.LinearLayout
//import android.widget.Toast
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import cabily.handyforall.dinedoo.R
//import com.cabilyhandyforalldinedoo.chd.Backgroundservices.commonapifetchservice
//import com.cabilyhandyforalldinedoo.chd.EventBusConnection.IntentServiceResult
//import com.cabilyhandyforalldinedoo.chd.SessionManagerPackage.SessionManager
//import com.cabilyhandyforalldinedoo.chd.ui.MainPage.mianscreenpage
//import com.cabilyhandyforalldinedoo.chd.ui.registeration.LocaleAwareCompatActivity
//import com.google.android.gms.vision.CameraSource
//import com.google.android.gms.vision.barcode.BarcodeDetector
//import com.google.android.material.snackbar.Snackbar
//import com.ncorti.slidetoact.SlideToActView
//import kotlinx.android.synthetic.main.activity_rental.*
//import kotlinx.android.synthetic.main.activity_support.*
//import kotlinx.android.synthetic.main.activity_wallet.*
//import kotlinx.android.synthetic.main.batteryswapping.*
//import kotlinx.android.synthetic.main.batteryswapping.close
//import kotlinx.android.synthetic.main.batteryswapping.nextlayout
//import kotlinx.android.synthetic.main.batteryswapping.top_lay
//import kotlinx.android.synthetic.main.faq_user.*
//import kotlinx.android.synthetic.main.nointernetconnection.*
//import kotlinx.android.synthetic.main.otpride.*
//import kotlinx.android.synthetic.main.registerpagetwo.view.*
////import kotlinx.android.synthetic.main.requestpagedfa.*
//import mauto_customer.ui.mainscreencustomers
//import org.greenrobot.eventbus.EventBus
//import org.greenrobot.eventbus.Subscribe
//import org.greenrobot.eventbus.ThreadMode
//import org.json.JSONObject
//
//
//class batteryswapping : LocaleAwareCompatActivity() {
//    private lateinit var mContext: Activity
//    internal lateinit var mSessionManager: SessionManager
//    var customerphno:String = ""
//    private val barcodeDetector: BarcodeDetector? = null
//    private val cameraSource: CameraSource? = null
//    var intentData = ""
//    var savedFilePath=""
//    var barcode=""
//    var battery_number:String = ""
//    var qrcodetype:String = ""
//    var REQUEST_CAMERA_PERMISSION = 500
//
//    private val COUNTRYCODEREQUESTS = 117
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        setContentView(R.layout.batteryswapping)
//        mContext = this@batteryswapping
//        mSessionManager = SessionManager(mContext)
//        initViews()
//
//    }
//
//
//    private fun initViews() {
//        current_card.visibility=View.VISIBLE
//        close.setOnClickListener {
//            val intent2 = Intent(mContext, mainscreencustomers()::class.java)
//            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//            mContext.startActivity(intent2)
//        }
//
//        nextlayout2.setOnClickListener {
//            if (!checkAccessCameraPermission()) {
//                requestPermission(REQUEST_CAMERA_PERMISSION)
//            } else {
//                val mServiceIntent: Intent = Intent(mContext, ScannedBarcodeActivity::class.java)
//                startActivityForResult(mServiceIntent, COUNTRYCODEREQUESTS)
//            }
//
//        }
//        current_card_3.setOnClickListener {
//            var toast = Toast.makeText(mContext, "Scan the Current Battery First", Toast.LENGTH_LONG)
//            toast.show()
//        }
//
//        nextlayout.setOnClickListener {
//
//            if (!checkAccessCameraPermission()) {
//                requestPermission(REQUEST_CAMERA_PERMISSION)
//            } else {
//                val mServiceIntent: Intent = Intent(mContext, ScannedBarcodeActivity::class.java)
//                startActivityForResult(mServiceIntent, COUNTRYCODEREQUESTS)
//                qrcodetype="old"
//            }
//
//        }
//        current_scan_old.setOnClickListener {
//            val mServiceIntent: Intent = Intent(mContext, ScannedBarcodeActivity::class.java)
//            startActivityForResult(mServiceIntent, COUNTRYCODEREQUESTS)
//            qrcodetype="old"
//        }
//
////        next.setOnClickListener(View.OnClickListener {
////            val mServiceIntent: Intent = Intent(mContext, ScannedBarcodeActivity::class.java)
////            startActivityForResult(mServiceIntent, COUNTRYCODEREQUESTS)
////            qrcodetype = "old"
////
////        })
//        nextlayout2.setOnClickListener {
//            val mServiceIntent: Intent = Intent(mContext, ScannedBarcodeActivity::class.java)
//            startActivityForResult(mServiceIntent, COUNTRYCODEREQUESTS)
//            qrcodetype="new"
//
//        }
//        next2.setOnClickListener {
//            val mServiceIntent: Intent = Intent(mContext, ScannedBarcodeActivity::class.java)
//            startActivityForResult(mServiceIntent, COUNTRYCODEREQUESTS)
//            qrcodetype="new"
//
//        }
//
//        current_scan.setOnClickListener {
//            val mServiceIntent: Intent = Intent(mContext, ScannedBarcodeActivity::class.java)
//            startActivityForResult(mServiceIntent, COUNTRYCODEREQUESTS)
//            qrcodetype="new"
//        }
//
//        slide_to_Battery.onSlideUserFailedListener = object : SlideToActView.OnSlideUserFailedListener {
//            override fun onSlideFailed(view: SlideToActView, isOutside: Boolean) {
//                swappingetbattery()
//
//            }
//        }
//
//
//    }
//    override fun onBackPressed() {
//        val intent2 = Intent(mContext, mainscreencustomers()::class.java)
//        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        mContext.startActivity(intent2)
//
//    }
//
//    private fun requestPermission(myRequestCode: Int) {
//        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),
//                myRequestCode)
//    }
//
//    fun getbatteryswapping() {
//        val serviceClass = commonapifetchservice::class.java
//        val intent = Intent(mContext, serviceClass)
//        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.user_battery))
//        intent.putExtra("qrcodetype", qrcodetype)
//        intent.putExtra("mbarcode", barcode)
//        mContext.startService(intent)
//    }
//
//    fun getbatteryswappingnew() {
//        val serviceClass = commonapifetchservice::class.java
//        val intent = Intent(mContext, serviceClass)
//        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.user_battery_new))
//        intent.putExtra("qrcodetype", qrcodetype)
//        intent.putExtra("mbarcode", barcode)
//        mContext.startService(intent)
//    }
//
//    fun swappingetbattery() {
//        val serviceClass = commonapifetchservice::class.java
//        val intent = Intent(mContext, serviceClass)
//        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.battery_submit))
//        mContext.startService(intent)
//    }
//
//
//
//    //calling customer
//    //common error notification page
//    fun commsnackbaralert(message: String)
//    {
////        val snack = Snackbar.make(coordinate_bottom_sheet_ride_book_now, message, Snackbar.LENGTH_LONG)
////        var view: View = snack.getView()
////        var params: FrameLayout.LayoutParams = FrameLayout.LayoutParams(view.getLayoutParams())
////        params.gravity = Gravity.TOP;
////        view.setLayoutParams(params)
////        snack.show()
//    }
//    override fun onResume() {
//        super.onResume()
//        EventBus.getDefault().register(this);
//
//    }
//    override fun onPause() {
//        super.onPause()
//        EventBus.getDefault().unregister(this);
//    }
//    override fun onDestroy() {
//        super.onDestroy()
//        EventBus.getDefault().unregister(this);
//    }
//
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        Log.d("check_battert", resultCode.toString())
//        if (resultCode == Activity.RESULT_OK) {
//            barcode = data?.getStringExtra("code").toString()
//            if (qrcodetype.equals("new")){
//                getbatteryswappingnew()
//            }else{
//                getbatteryswapping()
//
//            }
//
//            Log.d("check_battert_bar", barcode.toString())
//            Log.d("122qrcodetype", qrcodetype)
//
//
//        }
//    }
//
//    private fun checkAccessCameraPermission(): Boolean {
//        val result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//        return if (result == PackageManager.PERMISSION_GRANTED) {
//            true
//        } else {
////            requestPermission(REQUEST_CAMERA_PERMISSION)
//            Log.d("checkingpermission","permission")
//            false
//        }
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            500 -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                val mServiceIntent: Intent = Intent(mContext, ScannedBarcodeActivity::class.java)
//                startActivityForResult(mServiceIntent, COUNTRYCODEREQUESTS)
//            } else {
//                    val intent = Intent()
//                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//                    val uri: Uri = Uri.fromParts("package", packageName, null)
//                    intent.data = uri
//                    startActivity(intent)
//            }
//        }
//    }
//    fun paymentSuccess(){
//        val dialog = Dialog(mContext!!, R.style.Theme_Dialog)
//        dialog.window
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.setContentView(R.layout.payment_success)
//        dialog.setCanceledOnTouchOutside(false)
//        dialog.setCancelable(false)
//        val rate_now=dialog.findViewById<LinearLayout>(R.id.rate_now)
//        rate_now.setOnClickListener {
//            val intent_otppage = Intent(mContext, mianscreenpage::class.java)
//            intent_otppage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//            intent_otppage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent_otppage)
//            dialog.dismiss()
//        }
//        dialog.show()
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun doThis(intentServiceResult: IntentServiceResult) {
//        var apiName: String = intentServiceResult.apiName
//        var finalresponse: String = intentServiceResult.resultValue
//
//        if (apiName.equals(getString(R.string.battery_submit))) {
//            if (finalresponse == "failed") {
//
//            }else{
//                val response_json_object = JSONObject(finalresponse)
//                Log.d("successbattery", finalresponse)
//                val status = response_json_object.getString("status")
//                if (status.equals("0")) {
//                    val response = response_json_object.getString("response")
//                    var toast = Toast.makeText(mContext, response.toString(), Toast.LENGTH_LONG)
//                    toast.show()
//                }else if (status.equals("1")) {
//                    paymentSuccess()
//                }
//
//            }
//
//        }
//        if (apiName.equals(getString(R.string.user_battery))) {
//            if (finalresponse == "failed") {
//                current_card_value.visibility=View.GONE
//                current_card.visibility=View.VISIBLE
//                top_lay_value.visibility=View.GONE
//                current_card_2.visibility=View.GONE
//                current_card_3.visibility=View.VISIBLE
//                top_lay.visibility=View.VISIBLE
//
////                toast(getString(R.string.api_failure_toast_label))
//            } else {
//                Log.d("users_batterys", finalresponse)
//                val response_json_object = JSONObject(finalresponse)
//
//                val status = response_json_object.getString("status")
//
//                if (status.equals("1")) {
//                    current_card_value.visibility=View.VISIBLE
//                    top_lay_value.visibility=View.VISIBLE
//                    current_card_2.visibility=View.VISIBLE
//                    current_card_3.visibility=View.GONE
//                    val response = response_json_object.getJSONObject("response")
//                    val betteryInfo = response.getJSONObject("battery_info")
//                     battery_number = betteryInfo.getString("battery_number")
//                    var battery_voltage = betteryInfo.getString("battery_voltage")
//                    var driver_mobile = betteryInfo.getString("driver_mobile")
//                    var driver_name = betteryInfo.getString("driver_name")
//                    var vehicle_no = betteryInfo.getString("vehicle_no")
//                    var qrcode = betteryInfo.getString("qrcode")
//                    mSessionManager.setqrcode_old(qrcode)
//
//
//                    var brand = betteryInfo.getString("brand")
//                    batery_number_text.setText(battery_number)
//                    batery_Percentage.setText(brand)
//                    batery_Volts.setText(battery_voltage)
//                    driver_name_text.setText(driver_name)
//                    vehiclenumber_number_text.setText(vehicle_no)
//                    driver_mobile_number_text.setText(driver_mobile)
//
//                }else if (status.equals("0")) {
//                    val response = response_json_object.getString("response")
//                    var toast = Toast.makeText(mContext, response.toString(), Toast.LENGTH_LONG)
//                    toast.show()
//                    current_card.visibility=View.VISIBLE
//                    top_lay.visibility=View.VISIBLE
//                    current_card_3.visibility=View.VISIBLE
//
//                }
//
//            }
//        }
//
//        if (apiName.equals(getString(R.string.user_battery_new))) {
//            if (finalresponse == "failed") {
//                current_card_2.visibility=View.VISIBLE
//
//                current_card_value_new.visibility=View.GONE
//
////                toast(getString(R.string.api_failure_toast_label))
//            } else {
//
//                Log.d("users_batterys_new", finalresponse)
//                val response_json_object = JSONObject(finalresponse)
//
//                val status = response_json_object.getString("status")
//                current_card_value_new.visibility=View.GONE
//                if (status.equals("0")) {
//                    current_card_value_new.visibility=View.GONE
//                    current_card_2.visibility=View.VISIBLE
//
//                    val response = response_json_object.getString("response")
//                    var toast = Toast.makeText(mContext, response.toString(), Toast.LENGTH_LONG)
//                    toast.show()
//
//                }
//
//                if (status.equals("1")) {
//                    current_card_2.visibility=View.GONE
//
//
//                    current_card_value_new.visibility=View.VISIBLE
//                    val response = response_json_object.getJSONObject("response")
//                    val betteryInfo = response.getJSONObject("battery_info")
//                    battery_number = betteryInfo.getString("battery_number")
//                    var battery_voltage = betteryInfo.getString("battery_voltage")
//                    var qrcode = betteryInfo.getString("qrcode")
//                    mSessionManager.setqrcode_new(qrcode)
//
//                    var brand = betteryInfo.getString("brand")
//                    batery_number_text_new.setText(battery_number)
//                    batery_Percentage_new.setText(brand)
//                    batery_Volts_new.setText(battery_voltage)
//                    slide_to_Battery_lay.visibility=View.VISIBLE
//
//                }
//
//            }
//        }
//
//    }
//
//}