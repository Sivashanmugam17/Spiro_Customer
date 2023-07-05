package com.mauto.chd.view_model_trip_detail

import android.app.Application
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mauto.chd.R
import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.ui.registeration.EightFoldsDatePickerDialog
import java.util.*


class Tripdetailviewmodel(application: Application) : AndroidViewModel(application)
{

    var dashboarddata: Trippagerepostiatry
    private val failedorsuccess = MutableLiveData<Int>()
    private val triplistdata = MutableLiveData<ArrayList<TripDataModel>>()
    private val filterlist = MutableLiveData<ArrayList<TripDataModel>>()
    private val dobfull = MutableLiveData<String>()
    private val dobcode = MutableLiveData<String>()

    private val todobfull = MutableLiveData<String>()
    private val todobcode = MutableLiveData<String>()

    fun gotdataforlist(): MutableLiveData<Int>
    {
        return failedorsuccess
    }
    fun getdobfullobserver(): MutableLiveData<String>
    {
        return dobfull
    }
    fun getdobcodeobserver(): MutableLiveData<String>
    {
        return dobcode
    }
    fun gettodobfullobserver(): MutableLiveData<String>
    {
        return todobfull
    }
    fun gettodobcodeobserver(): MutableLiveData<String>
    {
        return todobcode
    }
    fun triplistobserver(): MutableLiveData<ArrayList<TripDataModel>>
    {
        return triplistdata
    }
    fun filtertriplistobserver(): MutableLiveData<ArrayList<TripDataModel>>
    {
        return filterlist
    }
    init
    {
        dashboarddata = Trippagerepostiatry( object : TripListener
        {
            override fun filterDataReceived(mutableLiveData: ArrayList<TripDataModel>) {
                filterlist.value = mutableLiveData
            }

            override fun onSuccessorFailed(mutableLiveData: Int) {
                failedorsuccess.value = mutableLiveData
            }
            override fun onDataReceived(mutableLiveData: ArrayList<TripDataModel>) {
                triplistdata.value = mutableLiveData
            }
        })
    }
    fun retrieveapicall(mContext:Context)
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.retrievetripdetail))
        mContext.startService(intent)
    }

    fun splitarraylistbasedontype(mContext: Context,ridetype:String,fullarray:ArrayList<TripDataModel>,fromdate:String,todate:String)
    {
        dashboarddata.getridetypes(mContext,ridetype,fullarray,fromdate,todate)
    }

    fun fromdateselection(mContext: Context)
    {
        val calendar = Calendar.getInstance()
        val datePickerDialog = EightFoldsDatePickerDialog(
                mContext,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                    var newmonth= (month+1)
                    var days=dayOfMonth.toString()
                    if(dayOfMonth<10)
                        days="0"+dayOfMonth

                    var months=newmonth.toString()
                    if(newmonth<10)
                        months="0"+newmonth

                    val date = "$days/$months/$year"
                    dobfull.value=date
                    val senddate = "$year/$months/$days"
                    dobcode.value= senddate
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE)
        )
        datePickerDialog.setTodayAsMaxDate();

        //   datePickerDialog.setTodayAsMinDate();   // sets today's date as min date
        //   datePickerDialog.setTodayAsMaxDate();    // sets today's date as max date

        datePickerDialog.show()

    }
    fun todateselection(mContext: Context)
    {
        val calendar = Calendar.getInstance()
        val datePickerDialog = EightFoldsDatePickerDialog(
                mContext,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                    var newmonth= (month+1)
                    var days=dayOfMonth.toString()
                    if(dayOfMonth<10)
                        days="0"+dayOfMonth

                    var months=newmonth.toString()
                    if(newmonth<10)
                        months="0"+newmonth

                    val date = "$days/$months/$year"
                    todobfull.value=date
                    val senddate = "$year/$months/$days"
                    todobcode.value= senddate
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE)
        )
        datePickerDialog.setTodayAsMaxDate();

        //   datePickerDialog.setTodayAsMinDate();   // sets today's date as min date
        //   datePickerDialog.setTodayAsMaxDate();    // sets today's date as max date

        datePickerDialog.show()

    }

    fun splitapiresopnse(mContext:Context,response:String)
    {
        dashboarddata.gettripdetails(mContext,response)
    }

}