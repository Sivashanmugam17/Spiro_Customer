//package com.cabilyhandyforalldinedoo.chd.ui.sidemenus
//
//import android.app.Activity
//import android.content.Intent
//import android.os.Bundle
//import android.view.View
//import android.widget.ImageView
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.core.content.ContextCompat
//import androidx.databinding.DataBindingUtil
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import androidx.recyclerview.widget.LinearLayoutManager
//import cabily.handyforall.dinedoo.R
//import cabily.handyforall.dinedoo.databinding.TripdetailspageBinding
//import com.cabilyhandyforalldinedoo.chd.EventBusConnection.IntentServiceResult
//import com.cabilyhandyforalldinedoo.chd.ViewModelTripDetail.TripDataModel
//import com.cabilyhandyforalldinedoo.chd.ViewModelTripDetail.tripdetailviewmodel
//import com.cabilyhandyforalldinedoo.chd.adaptersofchd.TripsAdapter
//import com.cabilyhandyforalldinedoo.chd.clickableInterface.tripdetailclick
//import com.cabilyhandyforalldinedoo.chd.commonutils.AppUtils
//import com.cabilyhandyforalldinedoo.chd.ui.registeration.LocaleAwareCompatActivity
//import com.google.android.material.bottomsheet.BottomSheetDialog
//import org.greenrobot.eventbus.EventBus
//import org.greenrobot.eventbus.Subscribe
//import org.greenrobot.eventbus.ThreadMode
//import java.text.SimpleDateFormat
//import java.util.*
//
//
//class tripdetails : LocaleAwareCompatActivity ()
//{
//
//
//    var fromdate:TextView ?= null
//    var todate:TextView ?= null
//    var savefromdate:String=""
//    var savetodate:String=""
//    var allselectvalue:Int = 1
//    var completeselectedvalue:Int = 0
//    var cancelledselectvalue:Int = 0
//    var saveoverallarraylis:ArrayList<TripDataModel> = ArrayList<TripDataModel>()
//    private lateinit var mContext: Activity
//    lateinit var binding: TripdetailspageBinding
//    private lateinit var viewModel: tripdetailviewmodel
//    override fun onCreate(savedInstanceState: Bundle?)
//    {
//        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this, R.layout.tripdetailspage)
//        mContext = this@tripdetails
//        initviewmodel()
//        binding.setViewModel(viewModel)
//        binding.close.setOnClickListener {
//            finish()
//        }
//        binding.filter.setOnClickListener {
//           filter()
//        }
//    }
//    //Viewmodel Observer Part
//    fun initviewmodel()
//    {
//        viewModel = ViewModelProviders.of(this).get(tripdetailviewmodel::class.java)
//        binding.loader.visibility = View.VISIBLE
//        viewModel.retrieveapicall(mContext)
//        viewModel.gotdataforlist().observe(this, Observer {
//            if(it == 1)
//            {
//            }
//            else if(it == 3)
//            {
//                binding.loader.visibility = View.GONE
//                AppUtils.commonerrorsheet(mContext,getString(R.string.failed),getString(R.string.nodataaviable))
//            }
//            else
//            {
//               binding.loader.visibility = View.GONE
//               AppUtils.commonerrorsheet(mContext,getString(R.string.failed),getString(R.string.failedproblem))
//            }
//        })
//        viewModel.filtertriplistobserver().observe(this, Observer {
//            triplistview(it)
//        })
//        viewModel.triplistobserver().observe(this, Observer {
//            saveoverallarraylis = it
//                triplistview(it)
//        })
//        viewModel.getdobfullobserver().observe(this, Observer {
//            if(fromdate != null) {
//                fromdate!!.setText(it)
//                if(todate != null) todate!!.text = ""
//            }
//        })
//        viewModel.gettodobfullobserver().observe(this, Observer {
//            if(todate != null)
//            {
//                datediffernce(fromdate!!.text.toString(),it)
//            }
//        })
//    }
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun doThis(intentServiceResult: IntentServiceResult)
//    {
//        var apiName: String = intentServiceResult.apiName
//        if (apiName.equals(getString(R.string.retrievetripdetail)))
//        {
//            var response: String = intentServiceResult.resultValue
//            if (response != "failed")  viewModel.splitapiresopnse(applicationContext,response)
//            else
//            {
//                binding.loader.visibility = View.GONE
//                AppUtils.commonerrorsheet(mContext,getString(R.string.failed),getString(R.string.failedproblem))
//            }
//        }
//    }
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
//    fun triplistview(tripdata: ArrayList<TripDataModel>)
//    {
//        if(tripdata.size > 0)
//        {
//
//            binding.tripsrecylerview.also {
//                it.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//                it.setHasFixedSize(true)
//                it.adapter = TripsAdapter(this,tripdata, object : tripdetailclick {
//                    override fun onRecyclerViewItemClick(view: View, rideid: String,mapimage:String) {
//                        val activityclass = fulltripdetails::class.java
//                        val intent = Intent(mContext, activityclass)
//                        intent.putExtra("rideid",rideid)
//                        intent.putExtra("mapimage", mapimage)
//                        mContext.startActivity(intent)
//                    }
//                })
//            }
//            binding.loader.visibility = View.GONE
//            binding.filter.visibility = View.VISIBLE
//        }
//        else  AppUtils.commonerrorsheet(mContext,getString(R.string.failed),getString(R.string.nodataaviable))
//    }
//
//    fun filter()
//    {
//        var filterdialog =  BottomSheetDialog(this)
//        val view = getLayoutInflater().inflate(R.layout.filterpopup, null);
//        filterdialog!!.setContentView(view)
//        filterdialog!!.setCancelable(true)
//
//        val closetext = view.findViewById(R.id.closetext) as ImageView
//        closetext.setOnClickListener {
//            filterdialog!!.dismiss()
//        }
//        val allselect = view.findViewById(R.id.allselect) as TextView
//        val completedselect = view.findViewById(R.id.completedselect) as TextView
//        val cancelledselect = view.findViewById(R.id.cancelledselect) as TextView
//        val applypage = view.findViewById(R.id.applypage) as LinearLayout
//        allselectview(allselect)
//        allselect.setOnClickListener {
//            allselectclick(allselect,completedselect,cancelledselect)
//        }
//
//        completedselectview(completedselect)
//        completedselect.setOnClickListener {
//            completedselectclick(allselect,completedselect,cancelledselect)
//        }
//
//        cancelledselectview(cancelledselect)
//        cancelledselect.setOnClickListener {
//            cancelledselectclick(allselect,completedselect,cancelledselect)
//        }
//        fromdate = view.findViewById(R.id.fromdate) as TextView
//        fromdate!!.text = savefromdate
//        fromdate!!.setOnClickListener {
//            viewModel.fromdateselection(mContext)
//        }
//        todate = view.findViewById(R.id.todate) as TextView
//        todate!!.text = savetodate
//        todate!!.setOnClickListener {
//            if(!fromdate!!.text.equals("")) viewModel.todateselection(mContext)
//            else AppUtils.commonerrorsheet(mContext,getString(R.string.failed),getString(R.string.pleasefillfromdate))
//        }
//        applypage.setOnClickListener {
//            savefromdate=fromdate!!.text.toString()
//            savetodate=todate!!.text.toString()
//            if(completeselectedvalue == 1)
//            {
//                   viewModel.splitarraylistbasedontype(mContext,"completed",saveoverallarraylis,fromdate!!.text.toString(),todate!!.text.toString())
//            }
//            else  if(allselectvalue == 1)
//            {
//                viewModel.splitarraylistbasedontype(mContext,"showall",saveoverallarraylis,fromdate!!.text.toString(),todate!!.text.toString())
//            }
//            else  if(cancelledselectvalue == 1)
//            {
//                viewModel.splitarraylistbasedontype(mContext,"all",saveoverallarraylis,fromdate!!.text.toString(),todate!!.text.toString())
//            }
//            else
//            {
//                viewModel.splitarraylistbasedontype(mContext,"showall",saveoverallarraylis,fromdate!!.text.toString(),todate!!.text.toString())
//            }
//            filterdialog!!.dismiss()
//        }
//        filterdialog!!.show()
//    }
//    fun allselectclick(allselect:TextView,completedselect:TextView,cancelledselect:TextView)
//    {
//        if(allselectvalue == 0)
//        {
//            allselectvalue = 1
//            allselect.setBackgroundResource(R.drawable.centerfullhightlight)
//            allselect.setTextColor(ContextCompat.getColor(mContext, R.color.colorwhite))
//            completeselectedvalue = 0
//            completedselect.setBackgroundResource(R.drawable.centnerstraight)
//            completedselect.setTextColor(ContextCompat.getColor(mContext, R.color.colorblack))
//            cancelledselectvalue = 0
//            cancelledselect.setBackgroundResource(R.drawable.centnerstraight)
//            cancelledselect.setTextColor(ContextCompat.getColor(mContext, R.color.colorblack))
//        }
//        else  if(allselectvalue == 1)
//        {
//            allselectvalue = 0
//            allselect.setBackgroundResource(R.drawable.centnerstraight)
//            allselect.setTextColor(ContextCompat.getColor(mContext, R.color.colorblack))
//        }
//    }
//    fun allselectview(allselect:TextView)
//    {
//        if(allselectvalue == 1)
//        {
//            allselect.setBackgroundResource(R.drawable.centerfullhightlight)
//            allselect.setTextColor(ContextCompat.getColor(mContext, R.color.colorwhite))
//        }
//        else  if(allselectvalue == 0)
//        {
//            allselect.setBackgroundResource(R.drawable.centnerstraight)
//            allselect.setTextColor(ContextCompat.getColor(mContext, R.color.colorblack))
//        }
//    }
//    fun completedselectview(completedselect:TextView)
//    {
//        if(completeselectedvalue == 1)
//        {
//            completedselect.setBackgroundResource(R.drawable.centerfullhightlight)
//            completedselect.setTextColor(ContextCompat.getColor(mContext, R.color.colorwhite))
//        }
//        else  if(completeselectedvalue == 0)
//        {
//            completedselect.setBackgroundResource(R.drawable.centnerstraight)
//            completedselect.setTextColor(ContextCompat.getColor(mContext, R.color.colorblack))
//        }
//    }
//    fun completedselectclick(allselect:TextView,completedselect:TextView,cancelledselect:TextView)
//    {
//        if(completeselectedvalue == 0)
//        {
//            completeselectedvalue = 1
//            completedselect.setBackgroundResource(R.drawable.centerfullhightlight)
//            completedselect.setTextColor(ContextCompat.getColor(mContext, R.color.colorwhite))
//            cancelledselectvalue = 0
//            cancelledselect.setBackgroundResource(R.drawable.centnerstraight)
//            cancelledselect.setTextColor(ContextCompat.getColor(mContext, R.color.colorblack))
//            allselectvalue = 0
//            allselect.setBackgroundResource(R.drawable.centnerstraight)
//            allselect.setTextColor(ContextCompat.getColor(mContext, R.color.colorblack))
//        }
//        else  if(completeselectedvalue == 1)
//        {
//            completeselectedvalue = 0
//            completedselect.setBackgroundResource(R.drawable.centnerstraight)
//            completedselect.setTextColor(ContextCompat.getColor(mContext, R.color.colorblack))
//        }
//    }
//    fun cancelledselectview(cancelledselect:TextView)
//    {
//        if(cancelledselectvalue == 1)
//        {
//            cancelledselect.setBackgroundResource(R.drawable.centerfullhightlight)
//            cancelledselect.setTextColor(ContextCompat.getColor(mContext, R.color.colorwhite))
//        }
//
//        else  if(cancelledselectvalue == 0)
//        {
//            cancelledselect.setBackgroundResource(R.drawable.centnerstraight)
//            cancelledselect.setTextColor(ContextCompat.getColor(mContext, R.color.colorblack))
//        }
//    }
//    fun cancelledselectclick(allselect:TextView,completedselect:TextView,cancelledselect:TextView)
//    {
//        if(cancelledselectvalue == 0)
//        {
//            cancelledselectvalue = 1
//            cancelledselect.setBackgroundResource(R.drawable.centerfullhightlight)
//            cancelledselect.setTextColor(ContextCompat.getColor(mContext, R.color.colorwhite))
//            completeselectedvalue = 0
//            completedselect.setBackgroundResource(R.drawable.centnerstraight)
//            completedselect.setTextColor(ContextCompat.getColor(mContext, R.color.colorblack))
//            allselectvalue = 0
//            allselect.setBackgroundResource(R.drawable.centnerstraight)
//            allselect.setTextColor(ContextCompat.getColor(mContext, R.color.colorblack))
//        }
//        else  if(cancelledselectvalue == 1)
//        {
//            cancelledselectvalue = 0
//            cancelledselect.setBackgroundResource(R.drawable.centnerstraight)
//            cancelledselect.setTextColor(ContextCompat.getColor(mContext, R.color.colorblack))
//        }
//    }
//    fun datediffernce(fromdates:String,todates:String)
//    {
//        todate!!.text = todates
//        try {
//            val CurrentDate = fromdates
//            val FinalDate = todates
//            try {
//                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
//                val date1 = sdf.parse(CurrentDate)
//                val date2 = sdf.parse(FinalDate)
//                if(date1.after(date2)){
//                    if(fromdate != null) fromdate!!.text = todates
//                    if(todate != null) todate!!.text = fromdates
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            val date1: Date
//            val date2: Date
//            val dates = SimpleDateFormat("dd/MM/yyyy")
//            date1 = dates.parse(CurrentDate)
//            date2 = dates.parse(FinalDate)
//            val difference = Math.abs(date1.time - date2.time)
//            val differenceDates = difference / (24 * 60 * 60 * 1000)
//            val dayDifference = java.lang.Long.toString(differenceDates)
//            if(dayDifference.toInt() > 7)
//            {
//              todate!!.setText("")
//              AppUtils.commonerrorsheet(mContext,getString(R.string.failed),getString(R.string.sorrydateismore))
//            }
//          } catch (exception: Exception) {
//        }
//    }
//}