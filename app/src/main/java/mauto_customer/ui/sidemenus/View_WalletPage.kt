package mauto_customer.ui.sidemenus

//import cabily.handyforall.dinedoo.databinding.EarningpagefirstBinding

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.Modal.TransactionHistoryModel
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.adaptersofchd.TransactionHistoryAdapter
import com.mauto.chd.commonutils.AppUtils
import com.mauto.chd.earningsviewmodel.Earningsviewmodel
import com.mauto.chd.ui.registeration.LocaleAwareCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.mauto.chd.BuildConfig
import com.mauto.chd.R
import com.mauto.chd.adaptersofchd.wallettransactionadapter
import com.mauto.chd.clickableInterface.rideCustomOnClickListener
import com.mauto.chd.ui.sidemenus.PaymentOtherTypePage
import com.mauto.chd.ui.sidemenus.wallettransactionsdetailspage
import kotlinx.android.synthetic.main.activity_wallet.*
import kotlinx.android.synthetic.main.activity_wallet.Recenttextview
import kotlinx.android.synthetic.main.activity_wallet.addcardConstrain
import kotlinx.android.synthetic.main.activity_wallet.addmoneybutton
import kotlinx.android.synthetic.main.activity_wallet.appversionwallet
import kotlinx.android.synthetic.main.activity_wallet.card_amount
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
//import kotlinx.android.synthetic.main.earningpagefirst.*
import kotlinx.android.synthetic.main.nointernetconnection.*
import mauto_customer.ui.BucketlistModel
//import kotlinx.android.synthetic.main.otpride.*
import mauto_customer.ui.MainscreenCustomers
import mauto_customer.ui.adapter.Amountlistadapter
import mauto_customer.ui.adapter.BucketListAdapter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import java.lang.Exception
import kotlin.collections.ArrayList

class View_WalletPage : LocaleAwareCompatActivity() {

    private lateinit var mContext: Activity
//    lateinit var binding: EarningpagefirstBinding
    private lateinit var viewModel: Earningsviewmodel
    private lateinit var transactionHistoryAdapter: TransactionHistoryAdapter
    private lateinit var tripadapater: wallettransactionadapter

    val months = arrayOf("01", "02", "03", "04", "05", "06", "07")
    private var transactionHistoryModel:ArrayList<TransactionHistoryModel>?=null
    private var dueHistoryModel:ArrayList<BucketlistModel>?=null
   var status="0"
    var min_recharge:String = ""
    var max_recharge:String = ""
    var mindle_recharge:String = ""
    var currency_symbol:String = ""
    private lateinit var tripadapaters: Amountlistadapter
    private lateinit var tripadapaters1: BucketListAdapter
    var addamount:String = ""
    lateinit var amountlist:RecyclerView
    var bottomSheetDialog: BottomSheetDialog? = null
    var bottomSheetDialogCancel: BottomSheetDialog? = null
    private val RESULT_PAYMENT_METHOD: Int = 1001
    lateinit var triplist:RecyclerView
    var app_version:String = ""
    var list:String = ""
    var addamountnew:String = ""


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
            cardmoney_3.visibility=View.GONE
        }else{
            card_amount.visibility=View.GONE
            cardmoney_3.visibility=View.GONE
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
        setContentView(R.layout.activity_view_wallet)
        mContext = this@View_WalletPage
        mSessionManager = SessionManager(mContext)
        app_version= BuildConfig.VERSION_NAME
        appversionwallet.setText("V "+app_version)
        name.setText(mSessionManager.getdriver_names())
        numbers_ooi.setText(mSessionManager.getdriver_vehicle())
//        paymentlistdialog()
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
        getwalletbalance()
//        generateColumnData()
        cardmoney_3.visibility= View.GONE
        addcardConstrain.visibility= View.GONE
        yourpaymenttext.visibility= View.GONE

        status="0"
        close.setOnClickListener{
            val intent_otppage2 = Intent(mContext, MainscreenCustomers::class.java)
            intent_otppage2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent_otppage2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent_otppage2)
        }
        dialog.setOnClickListener{
            getwalletbalance()
            paymentlistdialog()

//            addmoneybutton.visibility= View.VISIBLE

        }
        viewalltextviews.setOnClickListener{
            val intent_otppage = Intent(mContext, wallettransactionsdetailspage::class.java)
            mContext.startActivity(intent_otppage)
        }

        addmoneybutton.setOnClickListener {
            addamount=enteramount.text.toString()
            if (addamount.isEmpty()){
                commsnackbaralert("Enter the Amount")

            }else if (min_recharge.toLong()>addamount.toLong()){
                commsnackbaralert("Minimum recharge should be "+currency_symbol+" "+min_recharge)

            }else if (max_recharge.toLong()<addamount.toLong()){
                commsnackbaralert("Max recharge should be "+currency_symbol+" "+max_recharge)

            }else{

                val intent_edit_address = Intent(this,PaymentOtherTypePage::class.java)
                intent_edit_address.putExtra("amount", addamount)
                startActivityForResult(intent_edit_address, RESULT_PAYMENT_METHOD)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
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
                cardmoney_3.visibility=View.GONE
                addmoneybutton.visibility=View.GONE
                imageView7.visibility=View.GONE
            }else{
                status="0"
                cardmoney_3.visibility=View.GONE
                addmoneybutton.visibility=View.GONE
                transaction.visibility=View.GONE
                imageView7.visibility=View.VISIBLE

            }
        }




    }
    override fun onBackPressed() {
        val intent2 = Intent(mContext, MainscreenCustomers()::class.java)
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        mContext.startActivity(intent2)

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
        val intent_edit_address = Intent(this, PaymentOtherTypePage::class.java)
        intent_edit_address.putExtra("amount", addamountnew)
        intent_edit_address.putExtra("simble", currency_symbol)

        startActivityForResult(intent_edit_address, RESULT_PAYMENT_METHOD)
        Log.d("resulooo","$addamountnew")
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fun getwalletbalance() {
        progress_lay_wallet.visibility=View.VISIBLE
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.wallet_balance))
        mContext.startService(intent)
    }
    fun getpaymentkey() {
        progress_lay_wallet.visibility=View.VISIBLE
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.payment_key))
        mContext.startService(intent)
    }
    fun paymentlistdialog() {
//        list="[\"5000\","25000","33000"]"
//        list= dueHistoryModel
//        addmoneybutton.visibility = View.VISIBLE
        bottomSheetDialog = BottomSheetDialog(this)
//        cardmoney_31.visibility = View.VISIBLE
        val view = getLayoutInflater().inflate(R.layout.paymentlistdialog, null);
        bottomSheetDialog!!.setContentView(view)
        bottomSheetDialog!!.setCancelable(true)
        amountlist = view.findViewById(R.id.amountlist) as RecyclerView
       var others = view.findViewById<TextView>(R.id.others)
        others .visibility=View.GONE
//        initrecyclerviews(list)

        bottomSheetDialog!!.show()
        others.setOnClickListener{
            bottomSheetDialog!!.cancel()

        }
    }

//    private fun initrecyclerviews1(dueHistoryModel: java.util.ArrayList<BucketlistModel>) {
//        amountlist.layoutManager = LinearLayoutManager(mContext)
//        tripadapaters1 = BucketListAdapter(mContext!!,dueHistoryModel, object : rideCustomOnClickListener {
//            override fun onItemClickListener(view: View, position: Int, rideid:String) {
//
//                var v= view as TextView
//                v.text
//                addamountnew=v.text.toString()
//                Log.d("kkkkkk","$addamountnew")
//                getpaymentpage()
//
//
//            }
//        })
//        amountlist.adapter = tripadapaters1
//        amountlist.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//            }
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//            }
//        })
//    }

    fun transactionHistory(){
        recyclerHistory.visibility= View.VISIBLE
        cardmoney_3.visibility= View.GONE
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
        if (apiName.equals(getString(R.string.wallet_balance))) {
            if (finalresponse == "failed") {
//                toast(getString(R.string.api_failure_toast_label))
                progress_lay_wallet.visibility=View.GONE

            }
            else {
                Log.d("defrtdgddry", finalresponse)

                val response_json_object = JSONObject(finalresponse)
                val status = response_json_object.getString("status")

                if (status.equals("1")) {
                    val response = response_json_object.getJSONObject("response")
                    var wallet_id=response.getString("wallet_id")
                     min_recharge=response.getString("min_recharge")
                     max_recharge=response.getString("max_recharge")
                     currency_symbol=response.getString("currency_symbol")
                    var currency_name=response.getString("currency_name")
                    var balance=response.getInt("balance")

                    val recent_transactions_arrays = response.getJSONArray("recent_transactions")
                    transactionHistoryModel = ArrayList()
                    if (recent_transactions_arrays.length() > 0) {
                        for (i in 0 until recent_transactions_arrays.length()) {
                            val response_object_wallet = recent_transactions_arrays.getJSONObject(i)
                            var currency_code = response_object_wallet.getString("currency_code")
                            var type = response_object_wallet.getString("type")
                            var txn_id = response_object_wallet.getString("txn_id")
                            var gateway = response_object_wallet.getString("gateway")
                            var txn_date = response_object_wallet.getString("txn_date")
                            var txn_amount = response_object_wallet.getInt("txn_amount")
                            transactionHistoryModel?.add(TransactionHistoryModel(type,gateway,txn_date,txn_amount,currency_code,txn_id));

                        }
                    }else{
                        nodata.visibility=View.VISIBLE
                    }
                    var payment_amount_list = response.getJSONArray("payment_amount_list")
                    dueHistoryModel = ArrayList()
                    if (payment_amount_list.length() > 0) {
                        for (i in 0 until payment_amount_list.length()) {
                            val payment_amount_list1 = payment_amount_list.getJSONObject(i)

                            var editable = payment_amount_list1.getString("editable")
                            var label = payment_amount_list1.getString("label")
                            var amount = payment_amount_list1.getString("amount")


                            dueHistoryModel?.add(BucketlistModel(
                                editable = editable,
                                label=label,
                                amount=amount,
                                currency_symbol=currency_symbol
                            ))
                        }
                    }



                            initrecyclerviews(transactionHistoryModel!!)
//                            initrecyclerviews1(dueHistoryModel!!)

                    mSessionManager.setwallet_id(wallet_id)
                    mindle_recharge = ((min_recharge.toInt()+max_recharge.toInt())/2).toString()

                    walletamount.setText(balance.toString()+" "+currency_symbol)
                    textView13.setText(currency_name)
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
        }
    }

    private fun initrecyclerviews(transactionHistoryModel: ArrayList<TransactionHistoryModel>) {
        triplist.layoutManager = LinearLayoutManager(mContext)
        tripadapater = wallettransactionadapter(mContext!!,transactionHistoryModel, object : rideCustomOnClickListener {
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