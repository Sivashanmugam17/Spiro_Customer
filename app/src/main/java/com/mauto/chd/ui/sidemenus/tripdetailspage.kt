package com.mauto.chd.ui.sidemenus

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.Modal.tripdetailsModel
import com.mauto.chd.R
//import com.cabilyhandyforalldinedoo.chd.adaptersofchd.trippageadapter
import com.mauto.chd.commonutils.AppUtils
import com.mauto.chd.ui.registeration.LocaleAwareCompatActivity
import com.mauto.chd.viewmodelfortriplist.tripdetailsViewModel
//import fr.castorflex.android.smoothprogressbar.SmoothProgressBar
import kotlinx.android.synthetic.main.nointernetconnection.*
//import kotlinx.android.synthetic.main.otpride.*
//import kotlinx.android.synthetic.main.otpride.nointernetconnectionlay
import kotlinx.android.synthetic.main.tripdetails.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
class tripdetailspage : LocaleAwareCompatActivity()
{
    lateinit var mViewModel: tripdetailsViewModel
//    private lateinit var tripadapater: trippageadapter
    private lateinit var tripfulldata: ArrayList<tripdetailsModel>
    private var loading = false
    lateinit var triplist:RecyclerView
//    lateinit var  newone: SmoothProgressBar
    lateinit var nodatamessage:LinearLayout
    lateinit var fullplay:RelativeLayout
    lateinit var close:ImageView
    private lateinit var mContext: Activity
    var stoploading:Int = 0
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tripdetails)
        mContext = this@tripdetailspage
        initialize()
        initViewModel()
    }
    private fun initViewModel()
    {
        mViewModel = ViewModelProviders.of(this).get(tripdetailsViewModel::class.java)
        mViewModel.getdatafromrealmrecord(mContext!!)
        mViewModel.triparrayobserver().observe(this, Observer {
           if(it!!.size == 0)
               startservicehit(mContext!!)
           else
           {
               initrecyclerviews(it)
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
//               tripadapater.notifyDataSetChanged()
               loading = false
           }
        })
        mViewModel.responseLiveDataFirstobserver().observe(this, Observer {
            initrecyclerviews(it!!)
        })
    }
    private fun initrecyclerviews(triplistara: ArrayList<tripdetailsModel>)
    {

//        tripfulldata=triplistara
//        triplist.layoutManager = LinearLayoutManager(mContext)
//        tripadapater = trippageadapter(mContext!!,tripfulldata, object : rideCustomOnClickListener {
//            override fun onItemClickListener(view: View, position: Int,rideid:String)
//            {
//                 fulldetailpage(rideid)
//            }
//        })
//        triplist.adapter = tripadapater
//        triplist.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val linearLayoutManager = recyclerView
//                        .layoutManager as LinearLayoutManager?
//                if (!loading && linearLayoutManager!!.itemCount <= linearLayoutManager.findLastVisibleItemPosition() + 2 && stoploading == 0)
//                {
//                    loading = true
//                    var lastbookingdateandtime:String = tripfulldata.get((tripfulldata.size-1)).timestampid.toString()
//                    startlasthit(mContext!!,lastbookingdateandtime)
//                }
//            }
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//            }
//        })
    }
    fun initialize()
    {
        triplist = findViewById(R.id.triplist)
//        newone = findViewById(R.id.newone)
        nodatamessage = findViewById(R.id.nodatamessage)
        fullplay = findViewById(R.id.fullplay)
        close = findViewById(R.id.close)
        close.setOnClickListener {
            finish()
        }
        connection_lay2.setOnClickListener {
            recreate()
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
        if(apiName.equals(getString(R.string.tripservice)))
        {
            progress_lay_trip.visibility=View.GONE

            var finalresponse: String = intentServiceResult.resultValue
            mViewModel.splitresponse(mContext!!,finalresponse)
        }
        else if(apiName.equals(getString(R.string.startfirstorlast)))
        {
            progress_lay_trip.visibility=View.GONE

            var finalresponse: String = intentServiceResult.resultValue
            mViewModel.splitfirstresponse(mContext!!,finalresponse)

        }
        else if(apiName.equals(getString(R.string.startlast)))
        {
            progress_lay_trip.visibility=View.GONE

            var finalresponse: String = intentServiceResult.resultValue
            mViewModel.splitlastresponse(mContext!!,finalresponse)
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

        if (AppUtils.isNetworkAvailable(mContext)) {
            nointernetconnectionlay.visibility=View.GONE

        }else{

            nointernetconnectionlay.visibility=View.VISIBLE

        }

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
        progress_lay_trip.visibility=View.VISIBLE
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra("api_name", mContext.getString(R.string.tripservice))
        mContext.startService(intent)
    }
    fun startfirstorlasthit(mContext: Context, bookingdateandtime:String)
    {
        progress_lay_trip.visibility=View.VISIBLE
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra("api_name", mContext.getString(R.string.startfirstorlast))
        intent.putExtra("bookingdateandtime", bookingdateandtime)
        mContext.startService(intent)
    }
    fun startlasthit(mContext: Context, bookingdateandtime:String)
    {
        progress_lay_trip.visibility=View.VISIBLE

        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra("api_name", mContext.getString(R.string.startlast))
        intent.putExtra("bookingdateandtime", bookingdateandtime)
        mContext.startService(intent)
    }
}