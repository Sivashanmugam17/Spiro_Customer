package com.mauto.chd.earningsviewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mauto.chd.R
import com.mauto.chd.backgroundservices.commonapifetchservice


class Viewmodelforearningsthree(application: Application) : AndroidViewModel(application)
{


    private val failedorsuccess = MutableLiveData<Int>()


    fun gotdataforlist(): MutableLiveData<Int>
    {
        return failedorsuccess
    }

    fun retrieveapicall(mContext:Context)
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.retrievetripdetail))
        mContext.startService(intent)
    }





}