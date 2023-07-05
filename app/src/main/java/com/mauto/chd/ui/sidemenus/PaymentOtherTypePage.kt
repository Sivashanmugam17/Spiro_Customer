package com.mauto.chd.ui.sidemenus

import android.app.Activity
import android.app.ActivityManager
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import co.opensi.kkiapay.uikit.Kkiapay
import co.opensi.kkiapay.uikit.SdkConfig
import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.clickableInterface.CustomOnClickListenertype
import com.mauto.chd.commonutils.AppUtils
import com.mauto.chd.commonutils.Constants
import com.mauto.chd.earningsviewmodel.PaymentMethodsModel
import com.mauto.chd.earningsviewmodel.PaymentPageViewmodel
import com.mauto.chd.ui.registeration.CountryCodeSelection
import com.mauto.chd.ui.registeration.LocaleAwareCompatActivity
import com.cas.exapmle.PaymentAdapterRecyclerAdapter
import com.google.android.material.snackbar.Snackbar
import com.mauto.chd.R

//import kotlinx.android.synthetic.main.activity_earnings.*
import kotlinx.android.synthetic.main.activity_support.*
import kotlinx.android.synthetic.main.activity_wallet.*
//import kotlinx.android.synthetic.main.otpride.*
import kotlinx.android.synthetic.main.payment_layout_other.*
//import kotlinx.android.synthetic.main.registerpagefour.*
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.lang.Runnable
import java.util.*
import kotlin.collections.ArrayList
import com.mauto.chd.BuildConfig

class PaymentOtherTypePage: LocaleAwareCompatActivity() {
    private lateinit var viewModel: PaymentPageViewmodel
    private lateinit var mContext: Activity
    private var paymentMethodArray: ArrayList<PaymentMethodsModel> = ArrayList()
    private var isLoading: Boolean = false
    private var mRunnable: Runnable? = null
    private var avdProgress: AnimatedVectorDrawableCompat? = null
    private var mHandler: Handler? = null
    private lateinit var Payment_adapter:PaymentAdapterRecyclerAdapter
    var api_hit:String="0"
    private var paymentMethodName: String = ""
    private var paymentMethodCode: String = ""
    private var paymentMethodImage: String = ""
    private var selectedCardId: String = ""
    private var selectedCardType: String = ""
    private var selectedCardNumber: String = ""
    var page_s:String=""
    var payment_default=""
    internal lateinit var mSessionManager: SessionManager
    var addamount:String=""
    var simble:String=""
    var mtransactionId=""
    var uuid=""
    var uuitransaction_id = ""
    var transaction_id=""
    var kkiapaystatus=""
    private lateinit var repeatFunpayment : Job
    private lateinit var offerFunpayment : Job
    private lateinit var voucherFunpayment : Job
    private val COUNTRYCODEREQUEST = 113
    var countryname="TG"
    var defaultflag:String = "228,TG"
    var phonemuber:String=""
    var kkipay_mode=""
    var payment_key=""
    var payment_type=""
    var payment_message=""
    var dial_code="+228"
    var app_version:String = ""
    var offer_id:String? = null
    var voucher_count:String? = null

    companion object{

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.payment_layout_other)
        mContext = this@PaymentOtherTypePage
        offer_id = intent.getStringExtra("offer_id")
        voucher_count = intent.getStringExtra("voucher_count")
        Log.d("fdgfchgjhjgyudtd","$voucher_count")
        intviews()



        backbutton_lay_new.setOnClickListener {
            ratingpage_lay.visibility=View.GONE
            kkiapaystatus="FAILED"
            repeatFunpayment.cancel()
            offerFunpayment.cancel()
            voucherFunpayment.cancel()
            onBackPressed()


        }
        flagimage_wallet.setOnClickListener {
            countryclassintent()

        }
        iconlinear_lay_wallet.setOnClickListener {
            countryclassintent()

        }

        numberlinear_layss.setOnClickListener {
            countryclassintent()

        }


        Kkiapay.get().setListener{ status, transactionId  ->
            mtransactionId= transactionId.toString()
            kkiapaystatus=status.toString()
            Log.d("cjecjjc", kkiapaystatus)

            if(status.toString().equals("SUCCESS")){
                Toast.makeText(mContext, "Transaction: ${status.name} -> $transactionId", Toast.LENGTH_LONG).show()
                startServicerechargeupdate()
            }



            // ecoutez la fin du paiement ( status contient les différents status possibles )
        }


    }


    fun defaultflagprocessor(mContext: Context, defaultflagcode: String)
    {
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
        mSessionManager = SessionManager(mContext)
        mSessionManager!!.setFlagDetails(flagbase64string, codes, imageName)
        flagimage_wallet.setImageBitmap(bitmap)
        countryname=imageName
        Log.d("cdjfjf", codes)
        dial_code="+"+codes
//        bitmapimage.value=StringToBitMap(flagbase64string)
        code_wallet.setText("+" + codes)
    }
    private fun getCountryZipCode(ssid: String): String
    {
        val loc = Locale("", ssid)
        return loc.displayCountry.trim { it <= ' ' }
    }

    fun countryclassintent()
    {
        val intent_book_now = Intent(mContext, CountryCodeSelection::class.java)
        startActivityForResult(intent_book_now, COUNTRYCODEREQUEST)
    }

    private fun intviews() {
        mSessionManager = SessionManager(applicationContext)
        payment_key=mSessionManager.getkkipay_key()
        kkipay_mode=mSessionManager.getkkipay_mode()
        app_version= BuildConfig.VERSION_NAME
        appversionpayment.setText("V "+app_version)
        Log.d("checkmodekki20", payment_key + "---" + kkipay_mode)
        if (kkipay_mode.equals("test")){
            Kkiapay.init(applicationContext, payment_key, SdkConfig(themeColor = R.color.colorPrimary,
                    imageResource = R.raw.kkipay, enableSandbox = true)
            )
        }else{
            Kkiapay.init(applicationContext, payment_key, SdkConfig(themeColor = R.color.colorPrimary,
                    imageResource = R.raw.kkipay, enableSandbox = false)
            )
        }
        addamount= intent.getStringExtra("amount")!!
        simble= intent.getStringExtra("simble")!!

        modul.text=simble
        amout4.text=addamount

        Log.d("vvvvvvvv","$simble")
        if (intent.hasExtra("page")){
        }
        if (avdProgress == null)
            avdProgress = AnimatedVectorDrawableCompat.create(getApplicationContext(), R.drawable.avd_line)!!;
        if (mHandler == null)
            mHandler = Handler()
        paymentMethodArray = ArrayList<PaymentMethodsModel>()
        mContext = this@PaymentOtherTypePage
        viewModel = ViewModelProviders.of(this).get(PaymentPageViewmodel::class.java)
        startServicePaymentType()

        viewModel.getpaymentmethod().observe(this, Observer {
            paymentMethodArray!!.clear()
            paymentMethodArray = it

            setAdapters()

        })

        add_card.setOnClickListener {
            api_hit="1"
//            val payment_method_intent = Intent(this, AddNewCardActivity::class.java)
//            startActivity(payment_method_intent)
        }

        back_payment_lnr.setOnClickListener {
            onBackPressed()
        }

        done_lnr.setOnClickListener {
            if (payment_default.equals("")){
                for (i in paymentMethodArray ){
                    if (i.paymentMethodSelected.equals("1")){
                        paymentMethodName= i.paymentMethodName!!
                        paymentMethodCode= i.paymentMethodCode!!
                        selectedCardType= i.card_type!!
                        selectedCardNumber= i.card_number!!
                        paymentMethodImage= "" } } }

            Log.d("paymentMethod5", paymentMethodCode)
            phonemuber=enternumber_wallet.text.toString()
            AppUtils.hideKeyboard(mContext, done!!)

            if (paymentMethodCode.equals("kkiapay")){
                startServicepaymentinit()

            }else if (paymentMethodCode.equals("tmoney")){
                payment_type="tmoney"
                imageView_togo.visibility=View.VISIBLE

                startServicepaymentinittmoney(payment_type)
            }else if (paymentMethodCode.equals("mtnmomo")){
                payment_type="mtnmomo"
                imageView_togo.visibility=View.GONE
                startServicepaymentinittmoney(payment_type)
            }else if (paymentMethodCode.equals("moovtg")){
                payment_type="moovtg"
                imageView_togo.visibility=View.GONE
                startServicepaymentinittmoney(payment_type)
            }else if (paymentMethodCode.equals("moovbn")){
                payment_type="moovbn"
                imageView_togo.visibility=View.GONE
                startServicepaymentinittmoney(payment_type)
            }else if (paymentMethodCode.equals("airtel")){
                payment_type="airtel"
                imageView_togo.visibility=View.GONE
                startServicepaymentinittmoney(payment_type)
            }


            else if (paymentMethodCode.equals("tmoney")){
                payment_type="tmoney"
                imageView_togo.visibility=View.VISIBLE

                offerpaymentinit(payment_type)
            }else if (paymentMethodCode.equals("mtnmomo")){
                payment_type="mtnmomo"
                imageView_togo.visibility=View.GONE
                offerpaymentinit(payment_type)
            }else if (paymentMethodCode.equals("moovtg")){
                payment_type="moovtg"
                imageView_togo.visibility=View.GONE
                offerpaymentinit(payment_type)
            }else if (paymentMethodCode.equals("moovbn")){
                payment_type="moovbn"
                imageView_togo.visibility=View.GONE
                offerpaymentinit(payment_type)
            }
            else if (paymentMethodCode.equals("airtel")){
                payment_type="airtel"
                imageView_togo.visibility=View.GONE
                offerpaymentinit(payment_type)
            }



            else if (paymentMethodCode.equals("tmoney")){
                payment_type="tmoney"
                imageView_togo.visibility=View.VISIBLE

                VoucherPurchesPaymentinit(payment_type)
            }else if (paymentMethodCode.equals("mtnmomo")){
                payment_type="mtnmomo"
                imageView_togo.visibility=View.GONE
                VoucherPurchesPaymentinit(payment_type)
            }else if (paymentMethodCode.equals("moovtg")){
                payment_type="moovtg"
                imageView_togo.visibility=View.GONE
                VoucherPurchesPaymentinit(payment_type)
            }else if (paymentMethodCode.equals("moovbn")){
                payment_type="moovbn"
                imageView_togo.visibility=View.GONE
                VoucherPurchesPaymentinit(payment_type)
            }
            else if (paymentMethodCode.equals("airtel")){
                payment_type="airtel"
                imageView_togo.visibility=View.GONE
                VoucherPurchesPaymentinit(payment_type)
            }




            //            val intent_payment_method_result = Intent()
//            intent_payment_method_result.putExtra("payment_mehtod_name", paymentMethodName)
//            intent_payment_method_result.putExtra("payment_mehtod_code", paymentMethodCode)
//            intent_payment_method_result.putExtra("card_type", selectedCardType)
//            intent_payment_method_result.putExtra("card_number", selectedCardNumber)
//            intent_payment_method_result.putExtra("payment_image", paymentMethodImage)
//            setResult(Activity.RESULT_OK, intent_payment_method_result)
//            finish()



        }

    }
//    private fun getkey(key: String): String? {
//        return mHelpers.getvalueforkey(key)
//    }
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if(requestCode==113){
        if (resultCode == Activity.RESULT_FIRST_USER) {
            defaultflag = data?.getStringExtra(Constants.INTENT_OBJECT).toString()
            Log.d("de3rew5te", defaultflag)

                defaultflagprocessor(mContext, defaultflag)



        }
    }else{
        Kkiapay.get().handleActivityResult(requestCode, resultCode, data)

    }
}

    private fun setAdapters() {
        val recyclerView = findViewById<RecyclerView>(R.id.payment_list_view)
        recyclerView.visibility=View.VISIBLE
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        Payment_adapter = PaymentAdapterRecyclerAdapter(this, paymentMethodArray, object : CustomOnClickListenertype {
            override fun onItemClickListener(view: View, position: Int) {
                paymentMethodName = paymentMethodArray?.get(position)!!.paymentMethodName!!
                paymentMethodCode = paymentMethodArray?.get(position)!!.paymentMethodCode!!
//                paymentMethodImage = paymentMethodArray?.get(position)!!.cardImage!!
                payment_default = "1"
                selectedCardId = paymentMethodArray!!.get(position).card_id!!
                selectedCardType = paymentMethodArray!!.get(position).card_type!!
                selectedCardNumber = paymentMethodArray!!.get(position).card_number!!
                paymentMethodArray.get(Payment_adapter.selectedPosition).apply { paymentMethodSelected = "0" }
                paymentMethodArray.get(position).apply { paymentMethodSelected = "1" }
                Payment_adapter.notifyDataSetChanged()
//                startServiceUpdatePaymentType(paymentMethodCode, selectedCardId)
                if (paymentMethodName.equals("TMoney")){
                    numbertype_lay.visibility = View.VISIBLE

                } else if (paymentMethodName.equals("KKiapay")){
                    numbertype_lay.visibility = View.VISIBLE

                }else if (paymentMethodCode.equals("moovbn")){
                    numbertype_lay.visibility = View.VISIBLE

                }else if (paymentMethodCode.equals("moovtg")){
                    numbertype_lay.visibility = View.VISIBLE

                }else if (paymentMethodCode.equals("mtnmomo")){
                    numbertype_lay.visibility = View.VISIBLE

                }
                else if (paymentMethodCode.equals("airtel")){
                    numbertype_lay.visibility = View.VISIBLE
//                    currency_code

                }
                if (page_s.equals("booking", ignoreCase = true)) {
//                    val intent_payment_method_result = Intent()
//                    intent_payment_method_result.putExtra("payment_mehtod_name", paymentMethodName)
//                    intent_payment_method_result.putExtra("payment_mehtod_code", paymentMethodCode)
//                    intent_payment_method_result.putExtra("card_type", selectedCardType)
//                    intent_payment_method_result.putExtra("card_number", selectedCardNumber)
//                    intent_payment_method_result.putExtra("payment_image", paymentMethodImage)
//                    setResult(Activity.RESULT_OK, intent_payment_method_result)
//                    finish()
                }


            }

        }, "")
        recyclerView.adapter = Payment_adapter
    }


    fun startServicePaymentType()
    {
        if (!isMyServiceRunning(mContext, commonapifetchservice::class.java))
        {
            startLoader()

                val serviceClass = commonapifetchservice::class.java
                val intent = Intent(applicationContext, serviceClass)
                intent.putExtra("api_name", getString(R.string.payment_list_wallet))
                startService(intent)

        }
    }

//    fun startServicePaymentkey() {
//                val serviceClass = commonapifetchservice::class.java
//                val intent = Intent(applicationContext, serviceClass)
//                intent.putExtra("api_name", getString(R.string.payment_key))
//                startService(intent)
//    }


    fun isMyServiceRunning(context: Activity?, serviceClass: Class<*>): Boolean
    {
        var result = false
        val manager = context?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                result = true
                break
            } else {
                result = false
            }
        }
        return result
    }


    //Api Result
    @InternalCoroutinesApi
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun paymentTypeApiResult(intentServiceResult: IntentServiceResult)
    {
        var finalresponse: String = intentServiceResult.resultValue
        val apiName: String = intentServiceResult.apiName
        if (apiName.equals(getString(R.string.payment_list_wallet))) {
            stopLoader()
            payment_list_view.visibility=View.VISIBLE
            add_card.visibility=View.GONE
            done_lnr.visibility=View.VISIBLE

            if (finalresponse == "failed")
            {
//                toast(getString(R.string.sorrywent))
            }
            else
                viewModel.paymentTypeApiParser(mContext, finalresponse)
        }else if (apiName.equals(getString(R.string.payment_init))) {
            stopLoader()
            payment_list_view.visibility=View.VISIBLE
            add_card.visibility=View.GONE
            done_lnr.visibility=View.VISIBLE
            if (finalresponse == "failed") {
//                toast(getString(R.string.sorrywent))
            } else
                Log.d("chehhh", finalresponse)
                PaymentinitApiParser(finalresponse)
        }else if (apiName.equals(getString(R.string.recharge_update))) {
            stopLoader()
            payment_list_view.visibility=View.VISIBLE
            add_card.visibility=View.GONE
            done_lnr.visibility=View.VISIBLE
            if (finalresponse == "failed") {
//                toast(getString(R.string.sorrywent))
            } else
                recharge_updatetApiParser(finalresponse)
            Log.d("recharelresponse", finalresponse)
        }else if (apiName.equals(getString(R.string.payment_init_tmoney))) {
            stopLoader()
            payment_list_view.visibility=View.VISIBLE
            add_card.visibility=View.GONE
            done_lnr.visibility=View.VISIBLE
            if (finalresponse == "failed") {
//                toast(getString(R.string.sorrywent))
            } else
                PaymentinittmoneyApiParser(finalresponse)
            Log.d("recharelresponse", finalresponse)
        }else if (apiName.equals(getString(R.string.recharge_check))) {
            stopLoader()
            payment_list_view.visibility=View.VISIBLE
            add_card.visibility=View.GONE
            done_lnr.visibility=View.VISIBLE
            if (finalresponse == "failed") {
//                toast(getString(R.string.sorrywent))
            } else
                PaymentcheckmoneyApiParser(finalresponse)
            Log.d("rechargecheck", finalresponse)
        }
        else if (apiName.equals(getString(R.string.offerpaymentinit))) {
            stopLoader()
            payment_list_view.visibility=View.VISIBLE
            add_card.visibility=View.GONE
            done_lnr.visibility=View.VISIBLE
            if (finalresponse == "failed") {
//                toast(getString(R.string.sorrywent))
            } else
                OfferPaymentmoneyApiParser(finalresponse)
            Log.d("offerpaymentstatusinit", finalresponse)
        }

        else if (apiName.equals(getString(R.string.voucher_payment_init))) {
            stopLoader()
            ratingpage_lay.visibility=View.VISIBLE
//            payment_list_view.visibility=View.VISIBLE
            add_card.visibility=View.GONE
            done_lnr.visibility=View.VISIBLE
            if (finalresponse == "failed") {
                toast("Payment Failed ")
            } else
                VoucherPaymentmoneyApiParser(finalresponse)
            Log.d("offerpaymentstatusinit", finalresponse)
        }

        else if (apiName.equals(getString(R.string.offerpaymentstatuscheck))) {
            stopLoader()
            payment_list_view.visibility=View.VISIBLE
            add_card.visibility=View.GONE
            done_lnr.visibility=View.VISIBLE
            if (finalresponse == "failed") {
//                toast(getString(R.string.sorrywent))
            } else
                Offerpaymentcheck(finalresponse)
            Log.d("offerpaymentstatuscheck", finalresponse)
        }
        else if (apiName.equals(getString(R.string.voucherpaymentstatuscheck))) {
            stopLoader()
            payment_list_view.visibility=View.VISIBLE
            add_card.visibility=View.GONE
            done_lnr.visibility=View.VISIBLE
            if (finalresponse == "failed") {
//                toast(getString(R.string.sorrywent))
            } else
                Voucherpaymentcheck(finalresponse)
            Log.d("offerpaymentstatuscheck", finalresponse)
        }

    }


    private fun recharge_updatetApiParser(finalresponse: String) {
        println("-----------finalresponsesss-----" + finalresponse)
        val response_json_object = JSONObject(finalresponse)
        try {
            val status = response_json_object.getString("status")
            if (status.equals("1")) {
                val response =response_json_object.getJSONObject("response")
                Log.d("cheking", "wowos")
                paymentSuccess()
            }
        } catch (e: java.lang.Exception) {

            e.printStackTrace()
        }
    }

    private fun PaymentcheckmoneyApiParser(finalresponse: String) {
        println("-----------finalresponsesss-----" + finalresponse)
        val response_json_object = JSONObject(finalresponse)
        try {
            val status = response_json_object.getString("status")
            if (status.equals("1")) {
                val response =response_json_object.getJSONObject("response")
                val status =response.getString("status")
                Log.d("mmstatus", status)
                if (status.equals("failed")){
                    repeatFunpayment.cancel()
                    ratingpage_lay.visibility=View.GONE
                    toast("Payment failed")
                }else if (status.equals("paid")){
                    repeatFunpayment.cancel()
                    ratingpage_lay.visibility=View.GONE
                    paymentSuccess()
                }

            }else if (status.equals("0")){
                repeatFunpayment.cancel()
                val response =response_json_object.getString("response")
                toast(response)


            }
        } catch (e: java.lang.Exception) {

            e.printStackTrace()
        }
    }



    private fun Offerpaymentcheck(finalresponse: String) {
        println("----------- offer status finalresponsesss-----" + finalresponse)
        val response_json_object = JSONObject(finalresponse)
        try {
            val status = response_json_object.getString("status")
            if (status.equals("1")) {
                val response =response_json_object.getJSONObject("response")
                val status =response.getString("status")

                if(offerFunpayment==null)
                offerFunpayment = offerFunpayment()
                Log.d("mmstatus", status)
                if (status.equals("failed")){
                    offerFunpayment.cancel()
                    ratingpage_lay.visibility=View.GONE
                    toast("Payment failed")
                }else if (status.equals("paid")){
                    offerFunpayment.cancel()
                    ratingpage_lay.visibility=View.GONE
                    paymentSuccess()
                }

            }else if (status.equals("0")){
                offerFunpayment.cancel()
                ratingpage_lay.visibility=View.GONE
                val response =response_json_object.getString("response")
                toast(response)


            }
        } catch (e: java.lang.Exception) {

            e.printStackTrace()
        }
    }
    private fun Voucherpaymentcheck(finalresponse: String) {
        println("----------- offer status finalresponsesss-----" + finalresponse)
        val response_json_object = JSONObject(finalresponse)
        try {
            val status = response_json_object.getString("status")
            if (status.equals("1")) {
                val response =response_json_object.getJSONObject("response")
                val status =response.getString("status")

                if(voucherFunpayment==null)
                    voucherFunpayment = offerFunpayment()
                Log.d("mmstatus", status)
                if (status.equals("failed")){
                    voucherFunpayment.cancel()
                    ratingpage_lay.visibility=View.GONE
                    toast("Payment failed")
                }else if (status.equals("paid")){
                    voucherFunpayment.cancel()
                    ratingpage_lay.visibility=View.GONE
                    paymentSuccess()
                }

            }else if (status.equals("0")){
                voucherFunpayment.cancel()
                ratingpage_lay.visibility=View.GONE
                val response =response_json_object.getString("response")
                toast(response)


            }
        } catch (e: java.lang.Exception) {

            e.printStackTrace()
        }
    }
    @InternalCoroutinesApi
    fun repeatFunpayment(): Job {
        val scope = CoroutineScope(Dispatchers.Default)
        return scope.launch {
            while(isActive) {
                startServicerechargecheck()
                delay(500)
            }
        }
    }

    fun offerFunpayment(): Job {
        val scope1 = CoroutineScope(Dispatchers.Default)
        return scope1.launch {
            while(isActive) {
                //startServicerechargecheck()
                offerpaymentstatuscheck()
                delay(500)
            }
        }
    }
    fun voucherFunpayment(): Job {
        val scope1 = CoroutineScope(Dispatchers.Default)
        return scope1.launch {
            while(isActive) {
                //startServicerechargecheck()
//                offerpaymentstatuscheck()
                voucherpaymentstatuscheck()
                delay(500)
            }
        }
    }

    @InternalCoroutinesApi
    private fun PaymentinittmoneyApiParser(finalresponse: String) {
        println("-----------finalresponsesss-----" + finalresponse)
        val response_json_object = JSONObject(finalresponse)
        try {
            val status = response_json_object.getString("status")
            if (status.equals("1")) {
                ratingpage_lay.visibility=View.VISIBLE
                val response =response_json_object.getJSONObject("response")
                val data =response.getJSONObject("data")
                uuid =data.getString("uuid")
//
                Log.d("checkinguuid", uuid)
                repeatFunpayment = repeatFunpayment()
                val message=response.getString("message")
                textView31.setText(message)

//                Kkiapay.get().requestPayment(this, addamount,"Paiement de services",mSessionManager.getUserDetails()[mSessionManager.driver_name]!!,"97000000")

            }else if (status.equals("0")){
                val response =response_json_object.getString("response")
                toast(response)


            }
        } catch (e: java.lang.Exception) {

            e.printStackTrace()
        }
    }


    @InternalCoroutinesApi
    private fun OfferPaymentmoneyApiParser(finalresponse: String) {
        println("-----------offer initial finalresponsesss-----" + finalresponse)
        val response_json_object = JSONObject(finalresponse)
        try {
            val status = response_json_object.getString("status")
            if (status.equals("1")) {
                ratingpage_lay.visibility=View.VISIBLE
                val response =response_json_object.getJSONObject("response")
                //val data =response.getJSONObject("data")
                uuitransaction_id =response.getString("transaction_id")
//
                Log.d("checkinguuid", uuitransaction_id)
                //uuitransaction_id=response.getString("transaction_id")
                offerFunpayment = offerFunpayment()
                val message=response.getString("message")
                textView31.setText(message)

//                Kkiapay.get().requestPayment(this, addamount,"Paiement de services",mSessionManager.getUserDetails()[mSessionManager.driver_name]!!,"97000000")

            }else if (status.equals("0")){
                val response =response_json_object.getString("response")
                toast(response)


            }
        } catch (e: java.lang.Exception) {

            e.printStackTrace()
        }
    }


    @InternalCoroutinesApi
    private fun VoucherPaymentmoneyApiParser(finalresponse: String) {
        println("-----------offer initial finalresponsesss-----" + finalresponse)
        val response_json_object = JSONObject(finalresponse)
        try {
            val status = response_json_object.getString("status")
            if (status.equals("1")) {
                ratingpage_lay.visibility=View.VISIBLE
                val response =response_json_object.getJSONObject("response")
                val data =response.getJSONObject("data")
                transaction_id =data.getString("transaction_id")


//                val pay_load =data.getJSONObject("pay_load")
//                val paylode_data = pay_load.getJSONObject("data")
//                val transaction= paylode_data.getJSONObject("transaction")
//                val sucessstaces = transaction.getString("status")
//                val status_rut = pay_load.getJSONObject("status")
//                if (sucessstaces.equals("Success")) {
//                    ratingpage_lay.visibility=View.GONE
//                    paymentSuccess()
//                }else{
//                    toast("please try again leatter")
//                }
                voucherFunpayment = voucherFunpayment()
                val message=response.getString("message")
                textView31.setText(message)

//                Kkiapay.get().requestPayment(this, addamount,"Paiement de services",mSessionManager.getUserDetails()[mSessionManager.driver_name]!!,"97000000")

            }else if (status.equals("0")){
                val response =response_json_object.getString("response")
                toast(response)


            }
        } catch (e: java.lang.Exception) {

            e.printStackTrace()
        }
    }


    fun commsnackbaralert(message: String) {
        val snack = Snackbar.make(wallet_lay, message, Snackbar.LENGTH_LONG)
        var view: View = snack.view
        var params: FrameLayout.LayoutParams = FrameLayout.LayoutParams(view.layoutParams)
        params.gravity = Gravity.TOP
        view.layoutParams = params
        snack.show()
    }


    private fun PaymentinitApiParser(finalresponse: String) {
        println("-----------finalresponsesss-----" + finalresponse)
        val response_json_object = JSONObject(finalresponse)
        try {
            val status = response_json_object.getString("status")
            if (status.equals("1")) {
                val response =response_json_object.getJSONObject("response")
                val data =response.getJSONObject("data")
                uuid =data.getString("uuid")
//                uuitransaction_id=data.getString("uuitransaction_id")
                Log.d("checkinguuid", uuid)
                Kkiapay.get().requestPayment(this, addamount, "Paiement de services", mSessionManager.getUserDetails()[mSessionManager.driver_name]!!, "97000000")

            }
        } catch (e: java.lang.Exception) {

            e.printStackTrace()
        }
    }

    private fun updatePaymentApiParser(finalresponse: String) {
        println("-----------finalresponse-----" + finalresponse)
        val response_json_object = JSONObject(finalresponse)
        stopLoader()
        try
        {
            val status = response_json_object.getString("status")
            if (status.equals("1"))
            {

            }
        }catch (e: Exception){

            e.printStackTrace()
        }
    }
//    private fun PaymentkeyApiParser(finalresponse: String) {
//        println("-----------finalresponse-----"+finalresponse)
//        val response_json_object = JSONObject(finalresponse)
//        try
//        {
//            val status = response_json_object.getString("status")
//            if (status.equals("1")) {
//                val response = response_json_object.getJSONObject("response")
//                val payment_settings =response.getJSONObject("payment_settings")
//                val kkiapay =payment_settings.getJSONObject("kkiapay")
//                val paymentapikey=kkiapay.getString("apikey")
//                mSessionManager.setpayment_key(paymentapikey)
//                Log.d("mmpaymentapikey",paymentapikey)
//
//
//            }
//        }catch (e:Exception){
//
//            e.printStackTrace()
//        }
//    }


    override fun onResume()
    {
        super.onResume()
        if (!EventBus.getDefault().isRegistered(this)){
        EventBus.getDefault().register(this);
        }
        if (api_hit.equals("1")){

            startServicePaymentType()
        }
    }
    override fun onDestroy()
    {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }


    }


    fun startLoader()
    {
        isLoading = true
        payment_type_loader_iv.visibility = View.VISIBLE
        payment_type_loader_iv.background = avdProgress
        mRunnable = Runnable {
            repeatAnimation();
            mHandler!!.postDelayed(
                    mRunnable!!,
                    500
            )
        }
        mHandler!!.postDelayed(
                mRunnable!!,
                1000
        )
    }

    fun stopLoader()
    {
        isLoading = false
        payment_type_loader_iv.visibility = View.INVISIBLE
        mHandler!!.removeCallbacks(mRunnable!!)
        avdProgress?.stop();
    }
    fun repeatAnimation()
    {
        avdProgress?.start()
    }



    fun paymentSuccess(){
        val dialog = Dialog(mContext!!, R.style.Theme_Dialog)
        dialog.window
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.payment_success_wallet)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        val rate_now=dialog.findViewById<LinearLayout>(R.id.rate_now)
        val done=dialog.findViewById<TextView>(R.id.done)
        done.setText("Done")
        rate_now.setOnClickListener {
            dialog.dismiss()

            val intent_otppage2 = Intent(mContext, WalletPage::class.java)
            intent_otppage2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent_otppage2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent_otppage2)

        }
        dialog.show()



    }

    fun startServicepaymentinittmoney(paymenttype:String) {
        startLoader()


        if(voucher_count.isNullOrEmpty()) {
            if (offer_id.isNullOrEmpty()) {
                val serviceClass = commonapifetchservice::class.java
                val intent = Intent(mContext, serviceClass)
                intent.putExtra("amount", addamount)
                intent.putExtra("phonenumber", phonemuber)
                intent.putExtra("paymentype", paymenttype)
                intent.putExtra("dial_code", dial_code)
                intent.putExtra(
                    mContext.getString(R.string.intent_putextra_api_key),
                    mContext.getString(R.string.payment_init_tmoney)
                )
                mContext.startService(intent)
            } else {
                offerpaymentinit(paymenttype)
            }
        }else{
            VoucherPurchesPaymentinit(paymenttype)
        }


    }
    fun offerpaymentinit(paymenttype:String){
        startLoader()
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra("phonenumber", phonemuber)
        intent.putExtra("paymentype", paymenttype)
        intent.putExtra("dial_code", dial_code)
        intent.putExtra("offer_id",offer_id)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.offerpaymentinit))
        mContext.startService(intent)
    }


    fun VoucherPurchesPaymentinit(paymenttype:String){
        startLoader()
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra("phonenumber", phonemuber)
        intent.putExtra("paymentype", paymenttype)
        intent.putExtra("dial_code", dial_code)
        intent.putExtra("quantity", voucher_count)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.voucher_payment_init))
        mContext.startService(intent)
    }


    fun startServicepaymentinit() {
        startLoader()
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra("amount", addamount)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.payment_init))
        mContext.startService(intent)
    }
    fun startServicerechargeupdate() {
        startLoader()
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra("mtransactionId", mtransactionId)
        intent.putExtra("uuid", uuid)
        intent.putExtra("kkiapaystatus", kkiapaystatus)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.recharge_update))
        mContext.startService(intent)
    }

    fun startServicerechargecheck() {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra("uuid", uuid)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.recharge_check))
        mContext.startService(intent)
    }
    fun offerpaymentstatuscheck() {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra("transaction_id", uuitransaction_id)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.offerpaymentstatuscheck))
        mContext.startService(intent)
    }
    fun voucherpaymentstatuscheck() {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra("transaction_id", transaction_id)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.voucherpaymentstatuscheck))
        mContext.startService(intent)
    }




//    fun startServiceUpdatePaymentType(default_payment: String, card_id: String)
//    {
//        if (!isMyServiceRunning(mContext, commonapifetchservice::class.java))
//        {
//            startLoader()
//            if (AppUtils.isConnectedToInternet(mContext))
//            {
//                val serviceClass = commonapifetchservice::class.java
//                val intent = Intent(applicationContext, serviceClass)
//                intent.putExtra("api_name", getString(R.string.api_update_payment_type))
//                intent.putExtra("default_payment", default_payment)
//                intent.putExtra("card_id", card_id)
//                startService(intent)
//            }
//        }
//    }
}
