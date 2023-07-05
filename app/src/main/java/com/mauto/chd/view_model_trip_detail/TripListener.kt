package com.mauto.chd.view_model_trip_detail


import java.util.ArrayList

interface TripListener {
    fun onDataReceived(mutableLiveData: ArrayList<TripDataModel>)
    fun filterDataReceived(mutableLiveData: ArrayList<TripDataModel>)
    fun onSuccessorFailed(mutableLiveData: Int)
}