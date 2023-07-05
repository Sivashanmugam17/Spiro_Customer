package com.mauto.chd.ui.sidemenus

//import cabily.handyforall.dinedoo.databinding.EarningpagefirstBinding

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.adaptersofchd.TransactionHistoryAdapter
import com.mauto.chd.commonutils.AppUtils
import com.mauto.chd.earningsviewmodel.Earningsviewmodel
import com.mauto.chd.ui.registeration.LocaleAwareCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.mauto.chd.BuildConfig
import com.mauto.chd.Modal.TransactionHistoryModelCustomer
import com.mauto.chd.R
import com.mauto.chd.adaptersofchd.wallettransactionadapternew
import com.mauto.chd.clickableInterface.rideCustomOnClickListener
import kotlinx.android.synthetic.main.activity_wallet.Recenttextview
import kotlinx.android.synthetic.main.activity_wallet.addcardConstrain
import kotlinx.android.synthetic.main.activity_wallet.addmoneybutton
import kotlinx.android.synthetic.main.activity_wallet.appversionwallet
import kotlinx.android.synthetic.main.activity_wallet.card_amount
import kotlinx.android.synthetic.main.activity_wallet_new.cardmoney_31
import kotlinx.android.synthetic.main.activity_wallet.close
import kotlinx.android.synthetic.main.activity_wallet.enteramount
import kotlinx.android.synthetic.main.activity_wallet.imageView7
import kotlinx.android.synthetic.main.activity_wallet.nodata
import kotlinx.android.synthetic.main.activity_wallet.nointernetconnectionlay_2
import kotlinx.android.synthetic.main.activity_wallet.progress_lay_wallet
import kotlinx.android.synthetic.main.activity_wallet.recyclerHistory
import kotlinx.android.synthetic.main.activity_wallet.textView13
import kotlinx.android.synthetic.main.activity_wallet.textView15
import kotlinx.android.synthetic.main.activity_wallet.textView16
import kotlinx.android.synthetic.main.activity_wallet.textView17
import kotlinx.android.synthetic.main.activity_wallet.transaction
import kotlinx.android.synthetic.main.activity_wallet.viewalltextviews
import kotlinx.android.synthetic.main.activity_wallet.wallet_lay
import kotlinx.android.synthetic.main.activity_wallet.walletamount
import kotlinx.android.synthetic.main.activity_wallet.yourpaymenttext
import kotlinx.android.synthetic.main.activity_wallet_new.*
//import kotlinx.android.synthetic.main.earningpagefirst.*
import kotlinx.android.synthetic.main.nointernetconnection.*
//import kotlinx.android.synthetic.main.otpride.*
import mauto_customer.ui.PreDashboard
import mauto_customer.ui.adapter.Amountlistadapter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.util.*

class WalletPageNew : LocaleAwareCompatActivity() {

    private lateinit var mContext: Activity
//    lateinit var binding: EarningpagefirstBinding
    private lateinit var viewModel: Earningsviewmodel
    private lateinit var transactionHistoryAdapter: TransactionHistoryAdapter
    private lateinit var tripadapater: wallettransactionadapternew
    private lateinit var tripadapaters: Amountlistadapter
    var messagecancel:String = ""

    val months = arrayOf("01", "02", "03", "04", "05", "06", "07")
    private var transactionHistoryModel:ArrayList<TransactionHistoryModelCustomer>?=null
   var status="0"
    var min_recharge:String = "5000"
    var max_recharge:String = "33000"
    var mindle_recharge:String = ""
    var currency_symbol:String = ""
    var do_payment:String = ""
    var do_cancel:String = ""

    var addamount:String = ""
    private val RESULT_PAYMENT_METHOD: Int = 1001
    lateinit var triplist:RecyclerView
    lateinit var amountlist:RecyclerView
    lateinit var type_your_comment:EditText
    lateinit var report_send:TextView

    var addamountnew:String = ""

    var app_version:String = ""
    var bottomSheetDialog: BottomSheetDialog? = null
    var bottomSheetDialogCancel: BottomSheetDialog? = null

    var list:String = ""

    internal lateinit var mSessionManager: SessionManager

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this);
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this);
        if (AppUtils.isNetworkAvailable(mContext)) {
            nointernetconnectionlay_2.visibility=View.GONE
            card_amount.visibility=View.VISIBLE
//            cardmoney_3.visibility=View.VISIBLE
        }else{
            card_amount.visibility=View.GONE
            cardmoney_31.visibility=View.GONE
            nointernetconnectionlay_2.visibility=View.VISIBLE

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this);
    }

    //    private var columnData: ColumnChartData? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet_new)
        mContext = this@WalletPageNew
        mSessionManager = SessionManager(mContext)
        app_version= BuildConfig.VERSION_NAME
        appversionwallet.setText("V "+app_version)
//        Kkiapay.get().setListener{ status, transactionId  ->
//            if(status.toString().equals("SUCCESS")){
//                Toast.makeText(mContext, "Transaction: ${status.name} -> $transactionId", Toast.LENGTH_LONG).show()
//            }
//
//
//
//            // ecoutez la fin du paiement ( status contient les différents status possibles )
//        }
        initviewmodel()
        getpaymentkey()
        getprebookingdetails()
//        generateColumnData()
        cardmoney_31.visibility= View.GONE
        addcardConstrain.visibility= View.GONE
        yourpaymenttext.visibility= View.GONE
        addmoneybutton.visibility= View.GONE
        status="0"
        prebookingcancel.setOnClickListener{
            prebookingcanceldialog()
        }
        close.setOnClickListener{
            val intent_otppage2 = Intent(mContext, PreDashboard::class.java)
            intent_otppage2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent_otppage2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent_otppage2)
        }
        viewalltextviews.setOnClickListener{
            val intent_otppage = Intent(mContext, wallettransactionsdetailspage::class.java)
            mContext.startActivity(intent_otppage)
        }
        textView13.setOnClickListener {
            paymentlistdialog()
        }

        addmoneybutton.setOnClickListener {
            addamount=enteramount.text.toString()
            if (addamount.isEmpty()){
                Log.d("value",addamount)
                commsnackbaralert("Enter the Amount")

            }else if (min_recharge.toLong()>=addamount.toLong()){
                commsnackbaralert("Minimum recharge should be "+currency_symbol+" "+min_recharge)

            }else if (max_recharge.toLong()<addamount.toLong()){
                commsnackbaralert("Max recharge should be "+currency_symbol+" "+max_recharge)

            }else{

                val intent_edit_address = Intent(this, PaymentCusttomerTypePage::class.java)
                intent_edit_address.putExtra("amount", addamount)
                startActivityForResult(intent_edit_address, RESULT_PAYMENT_METHOD)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

//                val intent_edit_address = Intent(this, PaymentOtherTypePage::class.java)
//                intent_edit_address.putExtra("amount", addamount)
//                startActivityForResult(intent_edit_address, RESULT_PAYMENT_METHOD)
//                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
//                Kkiapay.get().requestPayment(this, addamount,"Paiement de services",mSessionManager.getUserDetails()[mSessionManager.driver_name]!!,"97000000")

                Log.d("miniamout","02002")
            }

        }

        textView15.setOnClickListener {
            enteramount.setText(textView15.text.toString())
        }
        textView16.setOnClickListener {
            enteramount.setText(max_recharge)

        }
        textView17.setOnClickListener {
            enteramount.setText(mindle_recharge)

        }
        connection_lay2.setOnClickListener {
            recreate()
        }
        Recenttextview.setOnClickListener {
            if (status.equals("0")){
                transaction.visibility=View.VISIBLE
                status="1"
                cardmoney_31.visibility=View.GONE
                addmoneybutton.visibility=View.GONE
                imageView7.visibility=View.GONE
            }else{
                status="0"
//                cardmoney_3.visibility=View.VISIBLE
                addmoneybutton.visibility=View.GONE
                transaction.visibility=View.GONE
                imageView7.visibility=View.VISIBLE

            }
        }



    }


    fun SwapstDialog(message: String) {
        val dialog = Dialog(mContext)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.message_dialog)
        dialog.window!!.setGravity(Gravity.CENTER)

        val displayMetrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val widthLcl = (displayMetrics.widthPixels * 0.9f)
        val heightLcl = WindowManager.LayoutParams.WRAP_CONTENT
        var swap_lay=dialog.findViewById<RelativeLayout>(R.id.swap_lays)
        val paramsLcl = swap_lay.getLayoutParams() as FrameLayout.LayoutParams
        paramsLcl.width = widthLcl.toInt()
        paramsLcl.height = heightLcl.toInt()
        paramsLcl.gravity = Gravity.CENTER_VERTICAL
        val window: Window = dialog.getWindow()!!
        swap_lay.setLayoutParams(paramsLcl)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

//        val nextlayout_new : Button
//        val custom_dialog_library_title_textview : TextView
        val dates : TextView
        val next_new_button : TextView
//
        dates = dialog.findViewById(R.id.dates)
        dates.setText(message)
        next_new_button = dialog.findViewById(R.id.next_new_button)
//        dates = dialog.findViewById(R.id.dates)
//        amountts = dialog.findViewById(R.id.amountts)

//        confirm_popup = dialog.findViewById(R.id.confirm_popup)
        dialog.show()


        next_new_button.setOnClickListener {
            dialog.dismiss()
            getprebookingdetails()

        }



    }

    fun prebookingcanceldialog() {
        bottomSheetDialogCancel = BottomSheetDialog(this)
        val view = getLayoutInflater().inflate(R.layout.prebookingcanceldialog, null);
        bottomSheetDialogCancel!!.setContentView(view)
        bottomSheetDialogCancel!!.setCancelable(true)
        type_your_comment = view.findViewById(R.id.type_your_comment) as EditText
        report_send = view.findViewById(R.id.report_send) as TextView
        report_send.setOnClickListener {
            if (type_your_comment.text.toString().isEmpty()){
                Toast.makeText(this, "Type Your comments", Toast.LENGTH_SHORT).show()

            }else{
                messagecancel=type_your_comment.text.toString()
                getreportsubmit()
            }
        }

        bottomSheetDialogCancel!!.show()
    }

    fun getreportsubmit() {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.prebooking_cancel))
        intent.putExtra("message", messagecancel)
        mContext.startService(intent)
    }

    fun paymentlistdialog() {
         list=mSessionManager!!.getamountlist()
        addmoneybutton.visibility = View.VISIBLE
        bottomSheetDialog = BottomSheetDialog(this)
        cardmoney_31.visibility = View.VISIBLE
        val view = getLayoutInflater().inflate(R.layout.paymentlistdialog, null);
        bottomSheetDialog!!.setContentView(view)
        bottomSheetDialog!!.setCancelable(true)
        amountlist = view.findViewById(R.id.amountlist) as RecyclerView
        initrecyclerviews(list)

        bottomSheetDialog!!.show()
    }

    private fun initrecyclerviews(list:String) {
        var vehicle: java.util.ArrayList<String> = java.util.ArrayList()
        val list= JSONArray(list)
        for (a in 0..list.length()-1){
            vehicle.add(list[a].toString())
        }
        amountlist.layoutManager = LinearLayoutManager(mContext)
        tripadapaters = Amountlistadapter(mContext!!,vehicle, object : rideCustomOnClickListener {
            override fun onItemClickListener(view: View, position: Int, rideid:String) {

                var v= view as TextView
                v.text
                addamountnew=v.text.toString()
                getpaymentpage()


            }
        })
        amountlist.adapter = tripadapaters
        amountlist.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

            }
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }




    override fun onBackPressed() {
        val intent_otppage2 = Intent(mContext, PreDashboard::class.java)
        intent_otppage2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent_otppage2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent_otppage2)
    }


    //Viewmodel Observer Part
    fun initviewmodel()
    {
        viewModel = ViewModelProviders.of(this).get(Earningsviewmodel::class.java)
        triplist = findViewById(R.id.transactionlist)
    }

    fun commsnackbaralert(message: String) {
        val snack = Snackbar.make(wallet_lay, message, Snackbar.LENGTH_LONG)
        var view: View = snack.view
        var params: FrameLayout.LayoutParams = FrameLayout.LayoutParams(view.layoutParams)
        params.gravity = Gravity.TOP
        view.layoutParams = params
        snack.show()
    }


    fun getpaymentpage() {
        val intent_edit_address = Intent(this, PaymentCusttomerTypePage::class.java)
        intent_edit_address.putExtra("amount", addamountnew)
        startActivityForResult(intent_edit_address, RESULT_PAYMENT_METHOD)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }


    fun getprebookingdetails() {
        progress_lay_wallet.visibility=View.VISIBLE
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.prebooking_details))
        mContext.startService(intent)
    }
    fun getpaymentkey() {
        progress_lay_wallet.visibility=View.VISIBLE
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.payment_key))
        mContext.startService(intent)
    }


    fun transactionHistory(){
        recyclerHistory.visibility= View.VISIBLE
        cardmoney_31.visibility= View.GONE
        addcardConstrain.visibility= View.GONE
        yourpaymenttext.visibility= View.GONE
        addmoneybutton.visibility= View.GONE

        recyclerHistory.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        transactionHistoryAdapter = TransactionHistoryAdapter(this)
        recyclerHistory.adapter = transactionHistoryAdapter
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        Kkiapay.get().handleActivityResult(requestCode, resultCode, data)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun doThis(intentServiceResult: IntentServiceResult) {
        var apiName: String = intentServiceResult.apiName
        var finalresponse: String = intentServiceResult.resultValue
        if (apiName.equals(getString(R.string.prebooking_details))) {
            if (finalresponse == "failed") {
//                toast(getString(R.string.api_failure_toast_label))
                progress_lay_wallet.visibility=View.GONE

            } else {
                Log.d("defrtdgddry", finalresponse)
                progress_lay_wallet.visibility=View.GONE

                val response_json_object = JSONObject(finalresponse)
                val status = response_json_object.getString("status")

                if (status.equals("1")) {
                    val response = response_json_object.getJSONObject("response")
                    currency_symbol=response.getString("currency_code")
                    do_payment=response.getString("do_payment")
                    do_cancel=response.getString("do_cancel")
                    if (do_payment.equals("1")){
                        textView13.visibility=View.VISIBLE
                    }
                    if (do_cancel.equals("1")){
                        prebookingcancel.visibility=View.VISIBLE
                    }

                    var balance=response.getInt("total_amount")
                    walletamount.setText(currency_symbol+" "+balance)
                    var payment_amount=response.getJSONArray("payment_amount")
                    mSessionManager.setamountlist(payment_amount.toString())
                    var type = ""

                    val recent_transactions_arrays = response.getJSONArray("transactions")
                    transactionHistoryModel = ArrayList()
                    if (recent_transactions_arrays.length() > 0) {
                        for (i in 0 until recent_transactions_arrays.length()) {
                            val response_object_wallet = recent_transactions_arrays.getJSONObject(i)
                            var currency_code = response_object_wallet.getString("currency_code")
                            var txn_id = response_object_wallet.getString("txn_id")

                            var gateway = response_object_wallet.getString("gateway")
                            var txn_date = response_object_wallet.getString("txn_date")
                            var txn_amount = response_object_wallet.getString("txn_amount")
                            transactionHistoryModel?.add(TransactionHistoryModelCustomer(
                                gateway,txn_date,txn_amount,currency_code,txn_id));

                        }
                    }else{
                        nodata.visibility=View.VISIBLE
                    }
                    initrecyclerviewsnew(transactionHistoryModel!!)


//                    max_recharge =
//                    textView13.setText()
                    textView15.setText(min_recharge)
                    textView17.setText(mindle_recharge)
                    textView16.setText(max_recharge)
                    progress_lay_wallet.visibility=View.GONE

                }else if (status.equals("0")){
                    progress_lay_wallet.visibility=View.GONE

                }


            }
        }else if (apiName.equals(getString(R.string.payment_key))) {
            if (finalresponse == "failed") {
//                toast(getString(R.string.api_failure_toast_label))
                progress_lay_wallet.visibility=View.GONE

            } else {
                Log.d("checkkkidsw",finalresponse)
                PaymentkeyApiParser(finalresponse)

            }
        }else if (apiName.equals(getString(R.string.prebooking_cancel))) {
            if (finalresponse == "failed") {
//                toast(getString(R.string.api_failure_toast_label))
                progress_lay_wallet.visibility=View.GONE

            } else {
                Log.d("checkkkid225",finalresponse)

                val response_json_object = JSONObject(finalresponse)
                val status = response_json_object.getString("status")
                if (status.equals("1")) {
                    val response = response_json_object.getJSONObject("response")
                    var message=response.getString("message")
                    bottomSheetDialogCancel!!.dismiss()

                    SwapstDialog(message)

//                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()


                }

            }
        }
    }

    private fun initrecyclerviewsnew(transactionHistoryModels: ArrayList<TransactionHistoryModelCustomer>) {
        try {
            triplist.layoutManager = LinearLayoutManager(mContext)
            tripadapater = wallettransactionadapternew(mContext!!,transactionHistoryModels, object : rideCustomOnClickListener {
                override fun onItemClickListener(view: View, position: Int,rideid:String)
                {


                }
            })
            triplist.adapter = tripadapater
            triplist.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                }
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                }
            })
        }catch (ex:Exception){
            ex.printStackTrace()
        }

    }

    private fun PaymentkeyApiParser(finalresponse: String) {
        println("-----------finalresponse-----"+finalresponse)
        val response_json_object = JSONObject(finalresponse)
        try
        {
            val status = response_json_object.getString("status")
            if (status.equals("1")) {
                val response = response_json_object.getJSONObject("response")
                val payment_settings =response.getJSONObject("payment_settings")
                val kkiapay =payment_settings.getJSONObject("kkiapay")
                val paymentapikey=kkiapay.getString("apikey")
                val mode=kkiapay.getString("mode")

                mSessionManager.setkkipay_key(paymentapikey)
                mSessionManager.setkkipay_mode(mode)

                Log.d("mmpaymentapikey",paymentapikey)


            }
        }catch (e: Exception){

            e.printStackTrace()
        }
    }


//    fun drawRoundRect(canvas: Canvas, left: Float, top: Float, right: Float, bottom: Float) {
//        val rad = (right - left) / 2
//        canvas.drawRoundRect(left, top, right, bottom, rad, rad, mRenderPaint)
//    }

//    private fun generateColumnData()
//    {
//        val numColumns: Int = months.size
//        val axisValues: MutableList<AxisValue> = ArrayList()
//        var columns: MutableList<Column> = ArrayList()
//        var values: MutableList<SubcolumnValue?>
//        for (i in 0 until numColumns)
//        {
//            values = ArrayList()
//            values.add(SubcolumnValue(Math.random().toFloat() * 5f + 1, ChartUtils.pickColor()))
//            axisValues!!.add(AxisValue(i.toFloat()).setLabel(months.get(i)))
//            columns.add(Column(values).setHasLabelsOnlyForSelected(true))
//        }
//        columnData = ColumnChartData(columns)
//        columnData!!.setAxisXBottom(Axis(axisValues).setHasLines(true))
//        columnData!!.setAxisYLeft(Axis().setHasLines(true).setMaxLabelChars(0))
//        chart_bottom!!.columnChartData = columnData
//        chart_bottom.isValueSelectionEnabled = true
////        chart_bottom.zoomType = ZoomType.HORIZONTAL
//
//
//        // Start new data animation with 300ms duration;
////        chart_bottom.startDataAnimation(300)
////        chart_bottom.setce
//
//    }
//
//    fun weeklyreport()
//    {
//        val intent_otppage = Intent(mContext, earningpagetwo::class.java)
//        startActivity(intent_otppage)
//    }
//
//    fun weeklysummaryreport()
//    {
//        val intent_otppage = Intent(mContext, earningpagethree::class.java)
//        startActivity(intent_otppage)
//    }

}