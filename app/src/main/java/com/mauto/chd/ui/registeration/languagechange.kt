package com.mauto.chd.ui.registeration

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
//import kotlinx.android.synthetic.main.languagechange.*
import kotlinx.android.synthetic.main.tripdetails.*

class languagechange : AppCompatActivity() {
    private lateinit var mContext: Activity


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.driverideaccpted)
        mContext = this@languagechange
        Log.d("dvfs","");
//        button2.setOnClickListener {
////            registerpageones(mContext)
//        }
    }
//    fun registerpageones(mContext: Context) {
//        val intent2 = Intent(mContext, registermobilenumber()::class.java)
//        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        mContext.startActivity(intent2)
//    }
}