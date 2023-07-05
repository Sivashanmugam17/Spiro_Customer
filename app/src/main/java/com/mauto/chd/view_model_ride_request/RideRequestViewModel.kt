package com.mauto.chd.view_model_ride_request

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mauto.chd.backgroundservices.acknowledgeapi
import com.mauto.chd.data.chatdb.DbHelper
import com.mauto.chd.data.onridelatandlong.onrideDbHelper


class RideRequestViewModel(application: Application) : AndroidViewModel(application)
{

    var dashboarddata: Riderequestpagerepostiatry
    private val riderequestviewmodels = MutableLiveData<ArrayList<ItemInfo>>()
    private val driveronlinestatuschanged = MutableLiveData<String>()



    fun riderequestviewmodellObserver(): MutableLiveData<ArrayList<ItemInfo>>
    {
        return riderequestviewmodels
    }

    init
    {
         dashboarddata = Riderequestpagerepostiatry( object : RideRequestListener
         {
             override fun onOnlinestatusChange(onlinesttau: String) {
                 driveronlinestatuschanged.value = onlinesttau
             }

             override fun onDataReceived(mutableLiveDataforvehicledetails: ArrayList<ItemInfo>)
            {
                riderequestviewmodels.value = mutableLiveDataforvehicledetails
            }
            override fun onError(error: Int)
            {
            }
         })
    }

    fun getsplitrepsonse(response:String)
    {
        dashboarddata.getdrivertails(response)
    }

    fun clearchatrecord(mContext:Context)
    {
        mContext.deleteDatabase(DbHelper.DATABASE_NAME)
        mContext.deleteDatabase(onrideDbHelper.DATABASE_NAME)
    }



    fun requestackkno(mContext: Context,ride_id:String,ack_id:String,ack:String)
    {
        val serviceClass = acknowledgeapi::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra("ride_id", ride_id)
        intent.putExtra("ack_id", ack_id)
        intent.putExtra("ack", ack)
        mContext.startService(intent)
    }





}