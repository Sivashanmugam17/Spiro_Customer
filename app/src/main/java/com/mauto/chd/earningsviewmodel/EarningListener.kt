package com.mauto.chd.earningsviewmodel


import java.util.*

interface EarningListener {
    fun onDataReceived(mutableLiveData: ArrayList<WeekModel>)
    fun filterDataReceived(mutableLiveData: ArrayList<WeekModel>)
    fun onSuccessorFailed(mutableLiveData: Int)
}