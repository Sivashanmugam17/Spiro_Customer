package com.mauto.chd.view_model_tracking_page

interface MyearningsPageListener {

    fun total_rides(mutableLiveData: String)
    fun total_revenue(mutableLiveData: String)
    fun online_rides(mutableLiveData: String)
    fun offline_rides(mutableLiveData: String)
    fun settled_amt(mutableLiveData: String)

}