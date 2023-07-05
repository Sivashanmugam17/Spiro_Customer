package com.mauto.chd.view_model_tracking_page

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mauto.chd.R
import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.view_model_trip_detail.FullTripDetailListener
import com.mauto.chd.view_model_trip_detail.TripDetailfullrepostiatry
import com.mauto.chd.view_model_trip_detail.fullridedetailsDataModel
import java.util.ArrayList


class Fulldetailpageviewmodel(application: Application) : AndroidViewModel(application)
{
    var dashboarddata: TripDetailfullrepostiatry
    private val userpojoclass = MutableLiveData<fullridedetailsDataModel>()
    private val earninglistt = MutableLiveData<ArrayList<earningModel>>()
    private val passengerpaidd = MutableLiveData<ArrayList<passengerpaidModel>>()

    fun userpojoobserver(): MutableLiveData<fullridedetailsDataModel>
    {
        return userpojoclass
    }
    fun driverearningobserver(): MutableLiveData<ArrayList<earningModel>>
    {
        return earninglistt
    }
    fun passengerpaidobserver(): MutableLiveData<ArrayList<passengerpaidModel>>
    {
        return passengerpaidd
    }
    init
    {
        dashboarddata = TripDetailfullrepostiatry( object : FullTripDetailListener
        {
            override fun onPassengerPaid(mutableLiveData: ArrayList<passengerpaidModel>) {

                passengerpaidd.value = mutableLiveData
            }

            override fun onEarningList(mutableLiveData: ArrayList<earningModel>) {

                earninglistt.value = mutableLiveData
            }

            override fun onDataReceived(mutableLiveData: fullridedetailsDataModel) {

                userpojoclass.value = mutableLiveData
            }

        })
    }

    //hitting api call
    fun getridedetails(mContext: Context, rideid: String)
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.retrievetripdetailfully))
        intent.putExtra("rideid", rideid)
        mContext.startService(intent)
    }

    //splitresponse
    fun splitapiresopnse(mContext: Context, response: String)
    {
        dashboarddata.splitrideinfo(response,mContext)
    }

}