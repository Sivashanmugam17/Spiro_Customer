package com.mauto.chd.earningsviewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mauto.chd.R
import com.mauto.chd.backgroundservices.commonapifetchservice
import java.util.*


class Viewmodelforearningstwo(application: Application) : AndroidViewModel(application)
{

    var dashboarddata: Earningpagepagerepostiatry
    private val failedorsuccess = MutableLiveData<Int>()
    private val triplistdata = MutableLiveData<ArrayList<WeekModel>>()
    private val filterlist = MutableLiveData<ArrayList<WeekModel>>()
    private val dobfull = MutableLiveData<String>()
    private val dobcode = MutableLiveData<String>()

    private val todobfull = MutableLiveData<String>()
    private val todobcode = MutableLiveData<String>()

    fun gotdataforlist(): MutableLiveData<Int>
    {
        return failedorsuccess
    }
    fun getdobfullobserver(): MutableLiveData<String>
    {
        return dobfull
    }
    fun getdobcodeobserver(): MutableLiveData<String>
    {
        return dobcode
    }
    fun gettodobfullobserver(): MutableLiveData<String>
    {
        return todobfull
    }
    fun gettodobcodeobserver(): MutableLiveData<String>
    {
        return todobcode
    }
    fun triplistobserver(): MutableLiveData<ArrayList<WeekModel>>
    {
        return triplistdata
    }
    fun filtertriplistobserver(): MutableLiveData<ArrayList<WeekModel>>
    {
        return filterlist
    }
    init
    {
        dashboarddata = Earningpagepagerepostiatry( object : EarningListener
        {
            override fun filterDataReceived(mutableLiveData: ArrayList<WeekModel>) {
                filterlist.value = mutableLiveData
            }

            override fun onSuccessorFailed(mutableLiveData: Int) {
                failedorsuccess.value = mutableLiveData
            }
            override fun onDataReceived(mutableLiveData: ArrayList<WeekModel>) {
                triplistdata.value = mutableLiveData
            }
        })
    }
    fun retrieveapicall(mContext:Context)
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.retrievetripdetail))
        mContext.startService(intent)
    }



    fun splitapiresopnse(mContext:Context,response:String)
    {
        dashboarddata.gettripdetails(mContext,response)
    }

}