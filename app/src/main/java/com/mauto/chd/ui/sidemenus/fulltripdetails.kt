package com.mauto.chd.ui.sidemenus

//import android.app.Activity
//import android.os.Bundle
//import android.view.View
//import androidx.databinding.DataBindingUtil
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import cabily.handyforall.dinedoo.R
//import cabily.handyforall.dinedoo.databinding.FulltripdetailsBinding
//import com.mauto.chd.event_bus_connection.IntentServiceResult
//import com.mauto.chd.view_model_tracking_page.earningModel
//import com.mauto.chd.view_model_tracking_page.Fulldetailpageviewmodel
//import com.mauto.chd.view_model_tracking_page.passengerpaidModel
//import com.mauto.chd.commonutils.AppUtils
//import com.mauto.chd.ui.registeration.LocaleAwareCompatActivity
//import kotlinx.android.synthetic.main.fulltripdetails.*
////import kotlinx.android.synthetic.main.requestpagedfa.*
//import org.greenrobot.eventbus.EventBus
//import org.greenrobot.eventbus.Subscribe
//import org.greenrobot.eventbus.ThreadMode
//
//class fulltripdetails : LocaleAwareCompatActivity ()
//{
//    private lateinit var mContext: Activity
//    var rideid:String = ""
//    var mapimage:String = ""
//    lateinit var binding: FulltripdetailsBinding
//    private lateinit var viewModel: Fulldetailpageviewmodel
//    override fun onCreate(savedInstanceState: Bundle?)
//    {
//        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this, R.layout.fulltripdetails)
//        mContext = this@fulltripdetails
//        fullayout.visibility = View.GONE
//        initviewmodel()
//        getextravale()
//
////        binding.close.setOnClickListener {
////            finish()
////        }
//    }
//    //Viewmodel Observer Part
//    fun initviewmodel()
//    {
//        viewModel = ViewModelProviders.of(this).get(Fulldetailpageviewmodel::class.java)
//        viewModel.userpojoobserver().observe(this, Observer {
//
//            fullayout.visibility = View.VISIBLE
//            my_rides_detail_cabname.text = it.user_name
//            driver_dashboard_ratting.rating = it.user_review!!.toFloat()
//
////            Glide.with(mContext)
////                    .load(it.user_image)
////                    .placeholder(R.drawable.profile)
////                    .listener(object : RequestListener<Drawable> {
////                        override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
////                            return false
////                        }
////                        override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
////                            return false
////                        }
////                    })
////                    .into(my_ride_detail_driverImage)
//
//            pickup_address_txt.text = it.pickup_location
//            drop_address_txt.text = it.drop_loc
//            pickup_time.text = it.pickup_time
//            if(it.drop_time.equals(""))
//            drop_time.visibility = View.INVISIBLE
//            else
//            drop_time.text = it.drop_time
//            total_amount_txt.text = it.firstamount
//            splitzeros.text = it.secondamount
//            symbolblack.text = it.currency_code
//            paymenttypee.text = it.payment_method
//            cabtype.text = it.cab_type
//            customeramount.text = it.grand_fare
//            tripdate.text = it.ride_date
//
//            km.text = it.ride_distance
//            unit.text = it.distance_unit
//        })
//
//        viewModel.driverearningobserver().observe(this, Observer {
//            driverearninglist(it!!)
//        })
//        viewModel.passengerpaidobserver().observe(this, Observer {
//            passengerlist(it!!)
//        })
//    }
//    fun getextravale()
//    {
//        rideid = intent.getStringExtra("rideid")
//        mapimage = intent.getStringExtra("mapimage")
//        binding.fulltriprideid.text = rideid
////        binding.loader.visibility = View.VISIBLE
//        viewModel.getridedetails(mContext,rideid)
//    }
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun doThis(intentServiceResult: IntentServiceResult)
//    {
//        var apiName: String = intentServiceResult.apiName
//        if (apiName.equals(getString(R.string.retrievetripdetailfully)))
//        {
////            binding.loader.visibility = View.GONE
//            var response: String = intentServiceResult.resultValue
//            if (response != "failed")  viewModel.splitapiresopnse(applicationContext,response)
//            else
//            {
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
//
//    //address list array
//    fun driverearninglist(it:ArrayList<earningModel>)
//    {
//        if(it.size == 0)
//        {
//            cancelle.visibility = View.GONE
//            customeramount.visibility = View.GONE
//            out.visibility = View.GONE
////            ridelist.visibility = View.GONE
//        }
//
//        else
//        {
////            var listViewAdapter = earningslistadapter(applicationContext, it)
////            driverearninglist.setAdapter(listViewAdapter)
////            driverearninglist.isExpanded = true
////            driverearninglist.visibility = View.VISIBLE
//        }
//    }
//
//    //address list array
//    fun passengerlist(it:ArrayList<passengerpaidModel>)
//    {
////        if(it.size == 0)
////            pasengerlist.visibility = View.GONE
////        else
////        {
////            var listViewAdapter = passengerpaidadapter(applicationContext, it)
////            pasengerlist.setAdapter(listViewAdapter)
////            pasengerlist.isExpanded = true
////            pasengerlist.visibility = View.VISIBLE
////        }
//    }
//
//}