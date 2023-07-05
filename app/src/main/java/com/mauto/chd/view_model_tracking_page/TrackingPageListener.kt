package com.mauto.chd.view_model_tracking_page


import java.util.ArrayList

interface TrackingPageListener {
    fun onDataReceived(mutableLiveData: ArrayList<TrackingPageDataModel>)
    fun onStopsAdapter(mutableLiveData: ArrayList<stopsModel>)
    fun estTime(estTime: String)
    fun estDistance(estDistance: String)
    fun setonhourd(esthours: String)
    fun onError(error: Int)
    fun onTripCancelled(tripcancelled: Int)
    fun oncashrecived(cashrecived: Int)
    fun onReRoutingenabled(reroutingenabled: Int)
    fun onCancelDataReceived(mutableLiveData: ArrayList<CancellationReasonDataModel>)
}