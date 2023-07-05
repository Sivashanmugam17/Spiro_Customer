package com.mauto.chd.view_model_with_repositary_main


import java.util.ArrayList

interface Listener {
    fun onDataReceived(mutableLiveData: ArrayList<MianPageDataModel>)
    fun onOnlinestatusChange(onlinesttau: String)
    fun onError(error: Int)
    fun onAmount(amount: String)
    fun onEarning(mutableLiveData: String)
    fun onEarningsAdded(mutableLiveData: String)
    fun onDocstatus(mutableLiveData:String)
    fun onDutyride(mutableLiveData:String)
    fun onMobileNo(mobileno: String)
    fun driverimage(driverimage: String)

}