//package com.cabilyhandyforalldinedoo.chd.ui.MainPage
//
//import android.app.Activity
//import android.content.Intent
//import android.os.Bundle
//import android.provider.Settings
//import android.text.format.DateFormat
//import androidx.appcompat.app.AppCompatActivity
//import cabily.handyforall.dinedoo.R
//import kotlinx.android.synthetic.main.adjusdateandtime.*
//import kotlinx.android.synthetic.main.locationonoffmissing.backbutton
//import kotlinx.android.synthetic.main.locationonoffmissing.btn_done
//import java.util.*
//
//
//class adjusclockdateandtime : AppCompatActivity ()
//{
//    private lateinit var mContext: Activity
//    override fun onCreate(savedInstanceState: Bundle?)
//    {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.adjusdateandtime)
//        mContext = this@adjusclockdateandtime
//        val tsLong = System.currentTimeMillis() / 1000
//        var cals: Calendar = Calendar.getInstance(Locale.ENGLISH);
//        cals.setTimeInMillis(tsLong * 1000);
//        var mobiledate:String  = DateFormat.format("dd-MMM-yyyy HH:mm", cals).toString()
//        showdateandtime.text = mobiledate
//        backbutton.setOnClickListener {
//            finish()
//        }
//        btn_done.setOnClickListener {
//            startActivityForResult( Intent(Settings.ACTION_DATE_SETTINGS), 0);
//            finish()
//        }
//    }
//}