package com.mauto.chd.view_model_trip_detail

import com.mauto.chd.view_model_tracking_page.earningModel
import com.mauto.chd.view_model_tracking_page.passengerpaidModel
import java.util.ArrayList


interface FullTripDetailListener {
    fun onDataReceived(mutableLiveData: fullridedetailsDataModel)
    fun onEarningList(mutableLiveData: ArrayList<earningModel>)
    fun onPassengerPaid(mutableLiveData: ArrayList<passengerpaidModel>)
}