package com.mauto.chd.viewmodelfortriplist

import com.mauto.chd.Modal.ridelistsummaryModel

import java.util.ArrayList

interface ridedetailspageListener {





    fun drivername(MutableLiveData:String)
    fun driverimage(MutableLiveData:String)
    fun driverrating(MutableLiveData:String)
    fun drivervehicletype(MutableLiveData:String)
    fun drivervehiclenumber(MutableLiveData:String)

    fun pickup_address_txt (MutableLiveData:String)
    fun drop_address_txt (MutableLiveData:String)

    fun pickuptime (MutableLiveData:String)
    fun droptime (MutableLiveData:String)
    fun paymentmethod(MutableLiveData:String)
    fun symbl(MutableLiveData:String)
    fun paidamount(MutableLiveData:String)
    fun ridekm(MutableLiveData:String)
    fun mapimagesave(MutableLiveData:String)
    fun cancelreason(MutableLiveData:String)
    fun fundriverrevenue(MutableLiveData:String)

    fun farebreakup (MutableLiveData:ArrayList<ridelistsummaryModel>)
}