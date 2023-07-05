package com.mauto.chd.ui.sidemenus

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.mauto.chd.R
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.commonutils.AppUtils
import com.mauto.chd.data.LanguageDb
import com.mauto.chd.ui.registeration.LocaleAwareCompatActivity
import kotlinx.android.synthetic.main.activity_rental.*
import kotlinx.android.synthetic.main.activity_support.*
import kotlinx.android.synthetic.main.activity_support.backbutton
import kotlinx.android.synthetic.main.faq_user.*
import kotlinx.android.synthetic.main.nointernetconnection.*
import kotlinx.android.synthetic.main.registerpagetwo.view.*
//import kotlinx.android.synthetic.main.requestpagedfa.*


class bikeretrieval : LocaleAwareCompatActivity() {
    private lateinit var mContext: Activity
    internal lateinit var mSessionManager: SessionManager
    var customerphno:String = ""
    lateinit var mHelpers: LanguageDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rental)
        mContext = this@bikeretrieval
        mSessionManager = SessionManager(mContext)
        mHelpers = LanguageDb(applicationContext)
        rental_call_tv.setText(getkey("call_customer_support"))
        backbutton.setOnClickListener {
            finish()
        }
//        renral_call.setOnClickListener {
//            callcustomer()
//        }
        connection_lay2.setOnClickListener {
            recreate()
        }



    }
    private fun getkey(key: String): String? {
        return mHelpers.getvalueforkey(key)
    }


    //calling customer
    fun callcustomer() {

        customerphno=mSessionManager.getsupport_number()
        if(customerphno.equals(""))
            commsnackbaralert(getString(R.string.nonotavaialbe))
        else
        {
            val intent =  Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", customerphno, null));
            startActivity(intent);
        }

    }
    //common error notification page
    fun commsnackbaralert(message:String)
    {
//        val snack = Snackbar.make(coordinate_bottom_sheet_ride_book_now,message, Snackbar.LENGTH_LONG)
//        var view: View = snack.getView()
//        var params: FrameLayout.LayoutParams = FrameLayout.LayoutParams(view.getLayoutParams())
//        params.gravity = Gravity.TOP;
//        view.setLayoutParams(params)
//        snack.show()
    }
    override fun onResume() {
        super.onResume()
        if (AppUtils.isNetworkAvailable(mContext)) {
            nointernetconnectionlay_4.visibility=View.GONE

        }else{

            nointernetconnectionlay_4.visibility=View.VISIBLE

        }

    }

}