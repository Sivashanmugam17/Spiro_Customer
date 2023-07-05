package com.mauto.chd.view_model_ride_request


import java.util.ArrayList

interface RideRequestListener {
    fun onDataReceived(mutableLiveData: ArrayList<ItemInfo>)
    fun onOnlinestatusChange(onlinesttau: String)
    fun onError(error: Int)
}