package com.mauto.chd.view_model_tracking_page

import java.util.ArrayList

interface OtpRidePageListener {

    fun locationfareapiresult(mutuablelivedata: LocationFareApiParserResult)
    fun estimatefareapi(mutuablelivedata: EstimateFareApiResult)
    fun onDataReceived(mutableLiveData: ArrayList<TrackingPageDataModel>)
    fun oncashrecived(cashrecived: Int)
    fun estDistance(estDistance: String)
    fun estTime(estTime: String)
    fun setonhourd(esthours: String)
    fun estimateamont(mutableLiveData: String)


}