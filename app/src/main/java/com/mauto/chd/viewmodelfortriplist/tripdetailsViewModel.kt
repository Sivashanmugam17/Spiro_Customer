package com.mauto.chd.viewmodelfortriplist


import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mauto.chd.Modal.tripdetailsModel
import io.realm.Realm
import java.util.*


class tripdetailsViewModel(application: Application) : AndroidViewModel(application)
{
    val realm by lazy { Realm.getDefaultInstance() }
    private val insertcompleted = MutableLiveData<String>()
    private val failedcompleted = MutableLiveData<String>()
    private val firstsplit = MutableLiveData<String>()
    private val secondsplit = MutableLiveData<String>()
    private val responseLiveData = MutableLiveData<ArrayList<tripdetailsModel>>()
    private val responseLiveDataFirst = MutableLiveData<ArrayList<tripdetailsModel>>()
    private val responseLiveDataSecond = MutableLiveData<ArrayList<tripdetailsModel>>()
    private val stoploading = MutableLiveData<String>()

    fun triparrayobserver(): MutableLiveData<ArrayList<tripdetailsModel>>
    {
        return responseLiveData
    }
    fun stoploadingobserver(): MutableLiveData<String>
    {
        return stoploading
    }
    fun responseLiveDataFirstobserver(): MutableLiveData<ArrayList<tripdetailsModel>>
    {
        return responseLiveDataFirst
    }
    fun responseLiveDataSecondobserver(): MutableLiveData<ArrayList<tripdetailsModel>>
    {
        return responseLiveDataSecond
    }
    fun insertcompletedobserver(): MutableLiveData<String>
    {
        return insertcompleted
    }
    fun failedcompletedobserver(): MutableLiveData<String>
    {
        return failedcompleted
    }
    fun firstsplitobserver(): MutableLiveData<String>
    {
        return firstsplit
    }
    fun secondsplitobserver(): MutableLiveData<String>
    {
        return secondsplit
    }


    var dashboarddata:tripdetailsrepostiary

    init
    {
        dashboarddata = tripdetailsrepostiary( object : tripdetailsListener
        {
            override fun failedobserver(mutuablelivedata: String) {
                failedcompleted.value = mutuablelivedata
            }
            override fun insertedobserver(mutuablelivedata: String) {
                insertcompleted.value = mutuablelivedata
            }
            override fun secondsplit(mutuablelivedata: String) {
                secondsplit.value = mutuablelivedata
            }
            override fun firstsplit(mutuablelivedata: String) {
                firstsplit.value = mutuablelivedata
            }

            override fun stoploadinglast(mutuablelivedata: String) {
                stoploading.value = mutuablelivedata
            }

            override fun responseLiveData(mutuablelivedata: ArrayList<tripdetailsModel>) {
                responseLiveData.value = mutuablelivedata
            }
            override fun responseLiveDataSecond(mutuablelivedata: ArrayList<tripdetailsModel>) {
                responseLiveDataSecond.value = mutuablelivedata
            }
            override fun responseLiveDataFirst(mutuablelivedata: ArrayList<tripdetailsModel>) {
                responseLiveDataFirst.value = mutuablelivedata
            }
        })
    }

    fun splitfirstresponse(mContext: Context,respnse:String)
    {
        dashboarddata.splitfirstresponse(mContext,respnse)
    }
    fun splitresponse(mContext: Context,respnse:String)
    {
        dashboarddata.splitresponse(mContext,respnse)
    }
    fun splitlastresponse(mContext: Context,respnse:String)
    {
        dashboarddata.splitlastresponse(mContext,respnse)
    }
    fun getdatafromrealmrecord(mContext: Context)
    {
        dashboarddata.getdatafromrealmrecord(mContext)
    }
    fun getdatafromrealmrecordfirst(mContext: Context)
    {
        dashboarddata.getdatafromrealmrecordfirst(mContext)
    }


}

