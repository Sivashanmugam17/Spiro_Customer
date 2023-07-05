package com.mauto.chd.ui.sidemenus

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mauto.chd.BuildConfig

import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.Modal.TransactionHistoryModel
import com.mauto.chd.Modal.tripdetailsModel
import com.mauto.chd.R
import com.mauto.chd.adaptersofchd.wallettransactionadapter
import com.mauto.chd.clickableInterface.rideCustomOnClickListener
import com.mauto.chd.ui.registeration.LocaleAwareCompatActivity
import com.mauto.chd.viewmodelformobileupdate.WalletransactionViewModel
import kotlinx.android.synthetic.main.wallettransactiondetails.*
//import fr.castorflex.android.smoothprogressbar.SmoothProgressBar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import java.util.*
class wallettransactionsdetailspage : LocaleAwareCompatActivity()
{
    lateinit var mViewModel: WalletransactionViewModel
    private var transactionHistoryModel:ArrayList<TransactionHistoryModel>?=null

    private lateinit var tripadapater: wallettransactionadapter
    private lateinit var tripfulldata: ArrayList<tripdetailsModel>
    private var loading = false
    lateinit var triplist:RecyclerView
//    lateinit var  newone: SmoothProgressBar
    lateinit var nodatamessage:LinearLayout
    lateinit var fullplay:RelativeLayout
    lateinit var close:ImageView
    private lateinit var mContext: Activity
    var stoploading:Int = 0
    var app_version:String = ""

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wallettransactiondetails)
        mContext = this@wallettransactionsdetailspage
        initialize()
        getwalletbalance()
        initViewModel()
    }
    private fun initViewModel()
    {
        mViewModel = ViewModelProviders.of(this).get(WalletransactionViewModel::class.java)
        mViewModel.getdatafromrealmrecord(mContext!!)
        mViewModel.triparrayobserver().observe(this, Observer {
           if(it!!.size == 0)
               startservicehit(mContext!!)
           else
           {
               var firstbookingdateandtime:String = it.get(0).timestampid.toString()
               startfirstorlasthit(mContext!!,firstbookingdateandtime)
           }
        })
        mViewModel.failedcompletedobserver().observe(this, Observer {
            nodatamessage.visibility = View.VISIBLE
        })
        mViewModel.insertcompletedobserver().observe(this, Observer {
            mViewModel.getdatafromrealmrecord(mContext!!)
        })
        mViewModel.stoploadingobserver().observe(this, Observer {
            stoploading = it.toInt()
        })
        mViewModel.firstsplitobserver().observe(this, Observer {
            if(it.equals("1"))
                mViewModel.getdatafromrealmrecordfirst(mContext!!)
        })
        mViewModel.secondsplitobserver().observe(this, Observer {
        })
        mViewModel.responseLiveDataSecondobserver().observe(this, Observer {
           if(it!!.size != 0)
           {
               for (j in 0 until it!!.size)
               {
                   tripfulldata.add(tripdetailsModel(it.get(j).pickupdate.toString(),it.get(j).pickuptime.toString(),it.get(j).amount.toString(),it.get(j).vehicletype.toString(),it.get(j).paymentmode.toString(),it.get(j).status.toString(),it.get(j).invoice_src.toString(),it.get(j).ride_status.toString(),it.get(j).ride_id.toString(),it.get(j).booking_date_time.toString(),it.get(j).timestampid.toString()))
               }
               tripadapater.notifyDataSetChanged()
               loading = false
           }
        })
        mViewModel.responseLiveDataFirstobserver().observe(this, Observer {
//            initrecyclerviews(it!!)
        })
    }

    fun getwalletbalance() {
        Log.d("ftdfsd","ghfvjsdfskkj")
        progress_lay_transation.visibility=View.VISIBLE
        val serviceClass = commonapifetchservice::class.java

        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.wallet_Transaction))
        mContext.startService(intent)
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
    fun initialize()
    {
        app_version= BuildConfig.VERSION_NAME
        appversiontransaction.setText("V "+app_version)
        triplist = findViewById(R.id.transactionlist)
//        newone = findViewById(R.id.newone)
        nodatamessage = findViewById(R.id.nodatamessage)
        fullplay = findViewById(R.id.fullplay)
        close = findViewById(R.id.close)
        close.setOnClickListener {
            finish()
        }
    }
    fun StopProgress()
    {
//        newone.visibility = View.GONE
    }
    fun start()
    {
//        newone.visibility = View.VISIBLE
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun doThis(intentServiceResult: IntentServiceResult)
    {
        StopProgress()
        var apiName: String = intentServiceResult.apiName
        progress_lay_transation.visibility=View.GONE

        if(apiName.equals(getString(R.string.wallet_Transaction))) {
            var finalresponse: String = intentServiceResult.resultValue
            Log.d("dtsftsrt",finalresponse)

            val response_json_object = JSONObject(finalresponse)
            val status = response_json_object.getString("status")
            if (status.equals("1")) {
                val response = response_json_object.getJSONObject("response")
                val response_faq_array = response.getJSONArray("reports_list")
                transactionHistoryModel = ArrayList()

                if (response_faq_array.length() > 0) {
                    nodatamessage.visibility=View.GONE
                    for (i in 0 until response_faq_array.length()) {
                        val response_object_wallet = response_faq_array.getJSONObject(i)
                        var currency_code = response_object_wallet.getString("currency_code")
                        var type = response_object_wallet.getString("type")
                        var txn_id = response_object_wallet.getString("txn_id")

                        var gateway = response_object_wallet.getString("gateway")
                        var txn_date = response_object_wallet.getString("txn_date")
                        var txn_amount = response_object_wallet.getInt("txn_amount")
                        transactionHistoryModel?.add(TransactionHistoryModel(type,gateway,txn_date,txn_amount,currency_code,txn_id));
                    }
                    Log.d("dtsftdfertsrt", transactionHistoryModel.toString())

                }else{
                    nodatamessage.visibility=View.VISIBLE

                }
                initrecyclerviews(transactionHistoryModel!!)


            }

        }

    }
    override fun onPause()
    {
        super.onPause()
        EventBus.getDefault().unregister(this);
    }
    override fun onResume()
    {
        super.onResume()
        EventBus.getDefault().register(this);
    }
    override fun onDestroy()
    {
        super.onDestroy()
        StopProgress()
        EventBus.getDefault().unregister(this);
    }
    fun fulldetailpage(rideid:String)
    {
//        val intent_book_now = Intent(mContext, tripdetailspageshow::class.java)
//        intent_book_now.putExtra("rideid", rideid)
//        startActivity(intent_book_now)
    }
    fun startservicehit(mContext: Context)
    {
        start()
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra("api_name", mContext.getString(R.string.wallettransactions))
        mContext.startService(intent)
    }
    fun startfirstorlasthit(mContext: Context, bookingdateandtime:String)
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra("api_name", mContext.getString(R.string.startfirstorlast))
        intent.putExtra("bookingdateandtime", bookingdateandtime)
        mContext.startService(intent)
    }
    fun startlasthit(mContext: Context, bookingdateandtime:String)
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra("api_name", mContext.getString(R.string.startlast))
        intent.putExtra("bookingdateandtime", bookingdateandtime)
        mContext.startService(intent)
    }
    
}