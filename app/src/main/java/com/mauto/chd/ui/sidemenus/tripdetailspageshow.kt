//package com.cabilyhandyforalldinedoo.chd.ui.sidemenus
//
//import android.app.Activity
//import android.graphics.Bitmap
//import android.graphics.drawable.Drawable
//import android.os.Bundle
//import android.view.View
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import cabily.handyforall.dinedoo.R
//import com.bumptech.glide.Glide
//import com.bumptech.glide.request.RequestOptions
//import com.bumptech.glide.request.target.CustomTarget
//import com.bumptech.glide.request.transition.Transition
//import com.cabilyhandyforalldinedoo.chd.Modal.ridelistsummaryModel
//import com.cabilyhandyforalldinedoo.chd.adaptersofchd.ridelistsummaryModelAdapter
//import com.cabilyhandyforalldinedoo.chd.data.LanguageDb
//import com.cabilyhandyforalldinedoo.chd.viewmodelfortriplist.tripdetailssummaryViewModel
//import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity
//import kotlinx.android.synthetic.main.tripdeatilpageshow.*
//
//
//
//class tripdetailspageshow : LocaleAwareCompatActivity() {
//    private lateinit var mContext: Activity
//    var rideid:String=""
//    lateinit var mViewModel: tripdetailssummaryViewModel
//    lateinit var mHelpers: LanguageDb
//
//    override fun onCreate(savedInstanceState: Bundle?)
//    {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.tripdeatilpageshow)
//        mContext = this@tripdetailspageshow
//        mHelpers = LanguageDb(applicationContext)
//        getExtraValue()
//        initViewModel()
//        close.setOnClickListener {
//            finish()
//        }
//    }
//    //Close
//    fun closeOnClicks(view: View)
//    {
//        finish()
//    }
//    fun getExtraValue() {
//        usernames.setText(getkey("tripdetails"))
//        yourearning.setText(getkey("yourearning"))
//        driverrevenue_text.setText(getkey("driverrevenue"))
//        driverrevenuestatemen.setText(getkey("driverrevenuestatemen"))
//        my_rides_detail_Faredetail.setText(getkey("driverearning"))
//        rideid = intent?.getStringExtra("rideid").toString()
//    }
//    private fun getkey(key: String): String? {
//        return mHelpers.getvalueforkey(key)
//    }
//
//    private fun initViewModel()
//    {
//        mViewModel = ViewModelProviders.of(this).get(tripdetailssummaryViewModel::class.java)
//        mViewModel.serachrideiddata(mContext,rideid)
//        mViewModel.drivernameobserver().observe(this, Observer {
//            if(!it.equals(""))
//            driverlay.visibility = View.VISIBLE
//            my_rides_detail_cabname.text = it
//        })
//
//        mViewModel.driverrevenueobserver().observe(this, Observer {
//            driverrevenue.visibility = View.VISIBLE
//            driverearningsamount.text = it
//        })
//
//        mViewModel.mapimagesaveobserver().observe(this, Observer {
//            Glide.with(mContext)
//                    .asBitmap()
//                    .load(it!!)
//                    .into(object : CustomTarget<Bitmap>() {
//                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?)
//                        {
//                            mapimage?.setImageBitmap(resource)
//                        }
//                        override fun onLoadCleared(placeholder: Drawable?)
//                        {
//                        }
//                    })
//        })
//        mViewModel.driverimageobserver().observe(this, Observer {
//            Glide.with(mContext)
//                    .asBitmap()
//                    .load(it!!)
//                    .into(object : CustomTarget<Bitmap>() {
//                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?)
//                        {
//                            my_ride_detail_driverImage?.setImageBitmap(resource)
//                        }
//                        override fun onLoadCleared(placeholder: Drawable?)
//                        {
//                        }
//                    })
//        })
//        mViewModel.driverratingobserver().observe(this, Observer {
//            driver_dashboard_ratting.rating = it!!.toFloat()
//        })
//        mViewModel.drivervehicletypeobserver().observe(this, Observer {
//            vehiclemake.text = it
//        })
//        mViewModel.drivervehiclenumberobserver().observe(this, Observer {
//            vehiclenumber.text = it
//        })
//        mViewModel.cancelreasonobserver().observe(this, Observer {
//            if(!it.equals(""))
//            {
//                cancelreason.text = it
//                cancellay.visibility = View.VISIBLE
//            }
//        })
//
//        mViewModel.pickup_address_txtobserver().observe(this, Observer {
//            pickup_address_txt.text = it
//        })
//
//        mViewModel.drop_address_txtobserver().observe(this, Observer {
//            drop_address_txt.text = it
//        })
//
//        mViewModel.pickuptimeobserver().observe(this, Observer {
//            pickup_time.text = it
//        })
//        mViewModel.droptimeobserver().observe(this, Observer {
//            drop_time.text = it
//            if(!it.equals(""))
//            drop_time.visibility = View.VISIBLE
//        })
//
//        mViewModel.ridekmobserver().observe(this, Observer {
//            km.text = it + " KM"
//        })
//        mViewModel.paidamountobserver().observe(this, Observer {
//            total_amount_txt.text = it
//        })
//
//        mViewModel.symblobserver().observe(this, Observer {
//            symbolblack.text = it
//        })
//        mViewModel.paymentmethodobserver().observe(this, Observer {
//            paymenttypee.text = it
//        })
//
//        mViewModel.farebreakupobserver().observe(this, Observer {
//           farebareakuplist(it!!)
//        })
//    }
//
//    fun farebareakuplist(it:ArrayList<ridelistsummaryModel>)
//    {
//        if(it.size == 0)
//        {
//            faerlay.visibility = View.GONE
//        }
//        else
//        {
//            var listViewAdapter = ridelistsummaryModelAdapter(applicationContext, it)
//            my_rides_payment_detail_listView.setAdapter(listViewAdapter)
//            my_rides_payment_detail_listView.isExpanded = true
//            faerlay.visibility = View.VISIBLE
//        }
//
//    }
//}