package com.mauto.chd.viewmodelfortriplist


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import android.content.Context
import com.mauto.chd.Modal.ridelistsummaryModel
import com.mauto.chd.commonutils.CurrencySymbolConventer

import java.util.*


class tripdetailssummaryViewModel(application: Application) : AndroidViewModel(application)
{

    private val drivername = MutableLiveData<String>()
    private val driverimage = MutableLiveData<String>()
    private val driverrating = MutableLiveData<String>()
    private val drivervehicletype = MutableLiveData<String>()
    private val drivervehiclenumber = MutableLiveData<String>()
    private lateinit var currencySymbolConventer: CurrencySymbolConventer
    private val pickup_address_txt = MutableLiveData<String>()
    private val drop_address_txt = MutableLiveData<String>()

    private val pickuptime = MutableLiveData<String>()
    private val droptime = MutableLiveData<String>()
    private val paymentmethod = MutableLiveData<String>()
    private val symbl = MutableLiveData<String>()
    private val paidamount = MutableLiveData<String>()
    private val driverreveneue = MutableLiveData<String>()
    private val ridekm = MutableLiveData<String>()
    private val mapimagesave = MutableLiveData<String>()
    private val cancelreason = MutableLiveData<String>()

    private val farebreakup = MutableLiveData<ArrayList<ridelistsummaryModel>>()


    fun drivernameobserver(): MutableLiveData<String>
    {
        return drivername
    }
    fun driverrevenueobserver(): MutableLiveData<String>
    {
        return driverreveneue
    }

    fun cancelreasonobserver(): MutableLiveData<String>
    {
        return cancelreason
    }

    fun mapimagesaveobserver(): MutableLiveData<String>
    {
        return mapimagesave
    }

    fun farebreakupobserver(): MutableLiveData<ArrayList<ridelistsummaryModel>>
    {
        return farebreakup
    }
    fun paymentmethodobserver(): MutableLiveData<String>
    {
        return paymentmethod
    }
    fun ridekmobserver(): MutableLiveData<String>
    {
        return ridekm
    }
    fun driverimageobserver(): MutableLiveData<String>
    {
        return driverimage
    }
    fun driverratingobserver(): MutableLiveData<String>
    {
        return driverrating
    }
    fun drivervehicletypeobserver(): MutableLiveData<String>
    {
        return drivervehicletype
    }
    fun drivervehiclenumberobserver(): MutableLiveData<String>
    {
        return drivervehiclenumber
    }

    fun pickup_address_txtobserver(): MutableLiveData<String>
    {
        return pickup_address_txt
    }
    fun drop_address_txtobserver(): MutableLiveData<String>
    {
        return drop_address_txt
    }


    fun pickuptimeobserver(): MutableLiveData<String>
    {
        return pickuptime
    }
    fun droptimeobserver(): MutableLiveData<String>
    {
        return droptime
    }


    fun paidamountobserver(): MutableLiveData<String>
    {
        return paidamount
    }

    fun symblobserver(): MutableLiveData<String>
    {
        return symbl
    }

    var dashboarddata:ridedetailrepostiatry
    init
    {
        dashboarddata = ridedetailrepostiatry( object : ridedetailspageListener
        {
            override fun drivername(MutableLiveData: String) {
                drivername.value = MutableLiveData
            }

            override fun driverimage(MutableLiveData: String) {
                driverimage.value = MutableLiveData
            }

            override fun driverrating(MutableLiveData: String) {
                driverrating.value = MutableLiveData
            }

            override fun drivervehicletype(MutableLiveData: String) {
                drivervehicletype.value = MutableLiveData
            }

            override fun drivervehiclenumber(MutableLiveData: String) {
                drivervehiclenumber.value = MutableLiveData
            }

            override fun pickup_address_txt(MutableLiveData: String) {
                pickup_address_txt.value = MutableLiveData
            }

            override fun drop_address_txt(MutableLiveData: String) {
                drop_address_txt.value = MutableLiveData
            }

            override fun pickuptime(MutableLiveData: String) {
                pickuptime.value = MutableLiveData
            }

            override fun droptime(MutableLiveData: String) {
                droptime.value = MutableLiveData
            }

            override fun paymentmethod(MutableLiveData: String) {
                paymentmethod.value = MutableLiveData
            }

            override fun symbl(MutableLiveData: String) {
                symbl.value = MutableLiveData
            }

            override fun paidamount(MutableLiveData: String) {
                paidamount.value = MutableLiveData
            }

            override fun ridekm(MutableLiveData: String) {
                ridekm.value = MutableLiveData
            }

            override fun mapimagesave(MutableLiveData: String) {
                mapimagesave.value = MutableLiveData
            }

            override fun cancelreason(MutableLiveData: String) {
                cancelreason.value = MutableLiveData
            }

            override fun fundriverrevenue(MutableLiveData: String) {
                driverreveneue.value = MutableLiveData
            }

            override fun farebreakup(MutableLiveData: ArrayList<ridelistsummaryModel>) {
                farebreakup.value = MutableLiveData
            }

        })
    }



    fun serachrideiddata(mContext: Context,rideid:String)
    {
        dashboarddata.serachrideiddata(mContext,rideid)

    }







}

