//package com.cabilyhandyforalldinedoo.chd.ui.sidemenus
//
////import cabily.handyforall.dinedoo.databinding.EarningpagefirstBinding
//
//import android.app.Activity
//import android.app.DatePickerDialog
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.DatePicker
//import androidx.lifecycle.ViewModelProviders
//import cabily.handyforall.dinedoo.R
//import com.cabilyhandyforalldinedoo.chd.Backgroundservices.commonapifetchservice
//import com.cabilyhandyforalldinedoo.chd.EventBusConnection.IntentServiceResult
//import com.cabilyhandyforalldinedoo.chd.data.LanguageDb
//import com.cabilyhandyforalldinedoo.chd.earningsviewmodel.earningsviewmodel
//import com.cabilyhandyforalldinedoo.chd.ui.registeration.LocaleAwareCompatActivity
////import kotlinx.android.synthetic.main.activity_earnings.*
//import kotlinx.android.synthetic.main.activity_support.*
//import kotlinx.android.synthetic.main.activity_support.backbutton
//import kotlinx.android.synthetic.main.earningpagefirst.*
//import kotlinx.android.synthetic.main.faq_user.*
//import org.greenrobot.eventbus.EventBus
//import org.greenrobot.eventbus.Subscribe
//import org.greenrobot.eventbus.ThreadMode
//import org.json.JSONObject
//import java.text.SimpleDateFormat
//import java.util.*
//
//
//class earningspage : LocaleAwareCompatActivity()
//{
//
//    private var dateCount = 0
//    private var dateCountplus = 0
//
//    private lateinit var mContext: Activity
////    lateinit var binding: EarningpagefirstBinding
//    private lateinit var viewModel: earningsviewmodel
//
//    val months = arrayOf("01", "02", "03", "04", "05", "06", "07")
//    var total_value_text =""
//
//    var cal = Calendar.getInstance()
//    var datevalue =""
//    lateinit var mHelpers: LanguageDb
//
//    override fun onPause() {
//        super.onPause()
//        EventBus.getDefault().unregister(this);
//    }
//
//    override fun onResume() {
//        super.onResume()
//        EventBus.getDefault().register(this);
//
//
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        EventBus.getDefault().unregister(this);
//    }
////    private var columnData: ColumnChartData? = null
//    override fun onCreate(savedInstanceState: Bundle?)
//    {
//        super.onCreate(savedInstanceState)
////        setContentView(R.layout.activity_earnings)
//        mContext = this@earningspage
//        mHelpers = LanguageDb(applicationContext)
//
//        initviewmodel()
//
//
//        imageView5.setOnClickListener {
//            val aCalendar = Calendar.getInstance()
//            val aDateFormat = SimpleDateFormat("yyyy-MM-dd")
//            val aCurrentDate = aDateFormat.format(aCalendar.getTime())
//            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//            val date = dateFormat.parse(aCurrentDate)
//            val calendar = Calendar.getInstance()
//            calendar.time = date
//            dateCount = dateCount-1
//            calendar.add(Calendar.DATE, dateCount)
//            datevalue = dateFormat.format(calendar.time)
//            Log.d("checkingss", datevalue)
//            time_period_tv.setText(datevalue)
//
//            getearningsapi()
//
//        }
//
//        imageView6.setOnClickListener {
//            val aCalendar = Calendar.getInstance()
//            val aDateFormat = SimpleDateFormat("yyyy-MM-dd")
//            val aCurrentDate = aDateFormat.format(aCalendar.getTime())
//            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//            val date = dateFormat.parse(aCurrentDate)
//            val calendar = Calendar.getInstance()
//            calendar.time = date
//            dateCountplus=dateCountplus + 1
//            calendar.add(Calendar.DATE, dateCountplus)
//            datevalue = dateFormat.format(calendar.time)
//            Log.d("checkiddffengss", datevalue)
//            time_period_tv.setText(datevalue)
//
//            getearningsapi()
//
//        }
//
//
//
//
//        lgkeyset()
//        getearningsapi()
////        generateColumnData()
//
////        closeall.setOnClickListener{
////            finish()
////        }
//
//        backbutton.setOnClickListener {
//            finish()
//        }
//        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
//            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
//                                   dayOfMonth: Int) {
//                cal.set(Calendar.YEAR, year)
//                cal.set(Calendar.MONTH, monthOfYear)
//                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//                updateDateInView()
//            }
//        }
//
//        time_period_tv!!.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(view: View) {
//                DatePickerDialog(this@earningspage,
//                        dateSetListener,
//                        // set DatePickerDialog to point to today's date when it loads up
//                        cal.get(Calendar.YEAR),
//                        cal.get(Calendar.MONTH),
//                        cal.get(Calendar.DAY_OF_MONTH)).show()
//            }
//
//        })
//
//
//    }
//    private fun updateDateInView() {
//        val myFormat = "yyyy-MM-dd" // mention the format you need
//        val sdf = SimpleDateFormat(myFormat, Locale.US)
//        datevalue  = sdf.format(cal.getTime())
//        Log.d("chechk22a", datevalue)
//        time_period_tv.setText(datevalue)
//        getearningsapi()
//
//    }
//    private fun getkey(key: String): String? {
//        return mHelpers.getvalueforkey(key)
//    }
//
//    fun lgkeyset(){
//        my_earnings_txt.setText(getkey("myearnings"))
//        online_payment_tv.setText(getkey("onlinepayment"))
//        subtotal_tv.setText(getkey("subtota"))
//        admin_fee_online_tv.setText(getkey("adminfee"))
//        total_tv.setText(getkey("total"))
//
//    }
//    //Viewmodel Observer Part
//    fun initviewmodel()
//    {
//        val aCalendar = Calendar.getInstance()
//        val aDateFormat = SimpleDateFormat("yyyy-MM-dd")
//        datevalue = aDateFormat.format(aCalendar.getTime())
//        time_period_tv.setText(datevalue)
//
//        viewModel = ViewModelProviders.of(this).get(earningsviewmodel::class.java)
//    }
//
//
//    fun getearningsapi() {
//        progress_lay_earnings.visibility=View.VISIBLE
//        val serviceClass = commonapifetchservice::class.java
//        val intent = Intent(mContext, serviceClass)
//        intent.putExtra("date_value", datevalue)
//        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.get_earning))
//        mContext.startService(intent)
//    }
//
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun doThis(intentServiceResult: IntentServiceResult) {
//        var apiName: String = intentServiceResult.apiName
//        var finalresponse: String = intentServiceResult.resultValue
//        if (apiName.equals(getString(R.string.get_earning))) {
//            if (finalresponse == "failed") {
//                progress_lay_earnings.visibility=View.GONE
//
////                toast(getString(R.string.api_failure_toast_label))
//            } else {
////                mViewModel.FaqApiParser(finalresponse)
//                Log.d("dfkjdh4w855", finalresponse)
//
//                val response_json_object = JSONObject(finalresponse)
//
//                val status = response_json_object.getString("status")
//                if (status.equals("1")) {
//                    val response = response_json_object.getJSONObject("response")
//                    var response_data_array2 = response.getJSONArray("data")
//                    if (response_data_array2.length() > 0) {
//                        for (i in 0 until response_data_array2.length()) {
//                            val response_object_earing = response_data_array2.getJSONObject(i)
//                            var total_rides = response_object_earing.getString("total_rides")
//                            var total_revenue = response_object_earing.getString("total_revenue")
//                            var currency = response_object_earing.getString("currency")
//                            var driver_earnings = response_object_earing.getString("driver_earnings")
//
//
//                            var online_rides = response_object_earing.getString("online_rides")
//                            var offline_rides = response_object_earing.getString("offline_rides")
//                            var online_revenue = response_object_earing.getString("online_revenue")
//                            var settled_amt = response_object_earing.getString("settled_amt")
//                            var offline_revenue = response_object_earing.getString("offline_revenue")
//                            var site_earnings = response_object_earing.getString("site_earnings")
//                            var rental_fee = response_object_earing.getString("rental_fee")
//                            var bill_payer = response_object_earing.getString("bill_payer")
//                            var in_driver = response_object_earing.getString("in_driver")
//                            var invoice_id = response_object_earing.getString("invoice_id")
//                            if(invoice_id.equals("today") || settled_amt.equals("0")){
//                                collect_earnings_text.setText("Bill Is Not Generated")
//                                collect_lay.visibility=View.GONE
//
//                            }else{
//                                collect_lay.visibility=View.VISIBLE
//
//                            }
//
//
//                            if (settled_amt.equals("0")){
//                                collect_earnings_text.setText("Bill Adjustment")
//                            }
//                            if (in_driver.equals("0")){
//                                discount_text_values.visibility=View.GONE
//                                discount_text.visibility=View.GONE
//                            }else{
//                                discount_text_values.visibility=View.VISIBLE
//                                discount_text.visibility=View.VISIBLE
//                                discount_text_values.setText(in_driver)
//
//                            }
//
//                            if (in_driver.equals("0")){
//                                total_value_text= (total_revenue.toInt()-site_earnings.toInt()-rental_fee.toInt()-in_driver.toInt()).toString().replace("-", "")
//
//                            }else{
//                                total_value_text= ((total_revenue.toInt()-site_earnings.toInt()-rental_fee.toInt()).toString())
//
//                            }
//
//
//                            totla_value.setText(currency + " " + total_value_text)
//
//
//
//                            if (invoice_id.equals("today")){
//
//                            }else{
//                                if (bill_payer.equals("driver")){
//                                    debit.setText("Debited ")
//                                    collect_earnings_text.setText("Deduction From Your Wallet")
//
//                                }
//
//                                if (bill_payer.equals("site")){
//                                    debit.setText("Credited ")
//                                    collect_earnings_text.setText("Added To Your Wallet")
//
//
//                                }
//                            }
//                            if (settled_amt.equals("0")){
//                                collect_earnings_text.setText("Bill Adjusted")
//
//
//                            }
//
//
//
//                            Log.d("settled_amt", settled_amt)
//
//                            settled_amt_text.setText(currency + " " + settled_amt)
//                            textView2.setText(currency + " " + driver_earnings)
//                            no_of_cash.setText(online_rides + " " + getString(R.string.s_ride))
//                            no_of_cards.setText(offline_rides + " " + getString(R.string.Card))
//                            no_of_rides_tv.setText(total_rides + " " + getString(R.string.f_Rides))
//                            online_payment_value.setText(online_revenue)
//                            offline_payment_value.setText(offline_revenue)
//                            subtotal_value.setText(total_revenue)
//                            admin_fee_online_value.setText(site_earnings)
//                            admin_fee_offline_value.setText(rental_fee)
//
//                            progress_lay_earnings.visibility=View.GONE
//
//                        }
//
//
//                    }else{
//                        progress_lay_earnings.visibility=View.GONE
//                        Log.d("nodata", "checkingsk")
//                    }
//                    }
//
//                }
//
//            }
//        }
//    }
//
//
//
////    fun drawRoundRect(canvas: Canvas, left: Float, top: Float, right: Float, bottom: Float) {
////        val rad = (right - left) / 2
////        canvas.drawRoundRect(left, top, right, bottom, rad, rad, mRenderPaint)
////    }
//
////    private fun generateColumnData()
////    {
////        val numColumns: Int = months.size
////        val axisValues: MutableList<AxisValue> = ArrayList()
////        var columns: MutableList<Column> = ArrayList()
////        var values: MutableList<SubcolumnValue?>
////        for (i in 0 until numColumns)
////        {
////            values = ArrayList()
////            values.add(SubcolumnValue(Math.random().toFloat() * 5f + 1, ChartUtils.pickColor()))
////            axisValues!!.add(AxisValue(i.toFloat()).setLabel(months.get(i)))
////            columns.add(Column(values).setHasLabelsOnlyForSelected(true))
////        }
////        columnData = ColumnChartData(columns)
////        columnData!!.setAxisXBottom(Axis(axisValues).setHasLines(true))
////        columnData!!.setAxisYLeft(Axis().setHasLines(true).setMaxLabelChars(0))
////        chart_bottom!!.columnChartData = columnData
////        chart_bottom.isValueSelectionEnabled = true
//////        chart_bottom.zoomType = ZoomType.HORIZONTAL
////
////
////        // Start new data animation with 300ms duration;
//////        chart_bottom.startDataAnimation(300)
//////        chart_bottom.setce
////
////    }
////
////    fun weeklyreport()
////    {
////        val intent_otppage = Intent(mContext, earningpagetwo::class.java)
////        startActivity(intent_otppage)
////    }
////
////    fun weeklysummaryreport()
////    {
////        val intent_otppage = Intent(mContext, earningpagethree::class.java)
////        startActivity(intent_otppage)
////    }
//
