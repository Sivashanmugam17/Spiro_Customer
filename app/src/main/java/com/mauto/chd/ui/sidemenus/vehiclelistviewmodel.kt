package com.mauto.chd.ui.sidemenus

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mauto.chd.Modal.DefaultVehicleListModel
import com.mauto.chd.Modal.VehicleListModel
import com.mauto.chd.Modal.vehicleinfoedit
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.data.steptwodocumentdb.documenttwoRepository
import com.mauto.chd.ui.viewmodelforvehicllist.VehiclePageListener
import com.mauto.chd.ui.viewmodelforvehicllist.vehiclelistpagerepostiatry


class vehiclelistviewmodel(application: Application) : AndroidViewModel(application)
{


    private val closepage = MutableLiveData<Int>()
    private val documentadded = MutableLiveData<Int>()
    private val addnewvehicle = MutableLiveData<Int>()
    private val basicvehicleinfoobserver = MutableLiveData<vehicleinfoedit>()
    private val errormessage = MutableLiveData<String>()
    private val successmessage = MutableLiveData<String>()
    var vehiclelisarray = MutableLiveData<ArrayList<VehicleListModel>>()
    var dashboarddata: vehiclelistpagerepostiatry
    var defaultvehicle = MutableLiveData<DefaultVehicleListModel>()
    private val repository: documenttwoRepository = documenttwoRepository(application)

    fun addnewvehicleobserver(): MutableLiveData<Int>
    {
        return addnewvehicle
    }

    fun doscumentaddedobserver(): MutableLiveData<Int>
    {
        return documentadded
    }

    fun closemodelobserver(): MutableLiveData<Int>
    {
        return closepage
    }

    fun basicvehicleinfoobserver(): MutableLiveData<vehicleinfoedit>
    {
        return basicvehicleinfoobserver
    }


    fun defaultvehiclemodelobserver(): MutableLiveData<DefaultVehicleListModel>
    {
        return defaultvehicle
    }
    fun vehiclearrayobserver(): MutableLiveData<ArrayList<VehicleListModel>>
    {
        return vehiclelisarray
    }
    fun closeoperation()
    {
        closepage.value = 1
    }
    fun addnew()
    {
        addnewvehicle.value = 1
    }

    fun errormessageobserver(): MutableLiveData<String>
    {
        return errormessage
    }
    fun successobserver(): MutableLiveData<String>
    {
        return successmessage
    }


    init
    {

        dashboarddata = vehiclelistpagerepostiatry( object : VehiclePageListener
        {
            override fun errormessage(mutuablelivedata: String) {
                errormessage.value = mutuablelivedata
            }

            override fun successmessage(mutuablelivedata: String) {
                successmessage.value = mutuablelivedata
            }

            override fun basicvehicleinfo(mutuablelivedata: vehicleinfoedit) {
                basicvehicleinfoobserver.value = mutuablelivedata
            }

            override fun arraylistofvehiclemodel(mutuablelivedata: ArrayList<VehicleListModel>) {
                vehiclelisarray.value = mutuablelivedata
            }

            override fun defaultvehiclemodel(mutuablelivedata: DefaultVehicleListModel) {
                defaultvehicle.value = mutuablelivedata
            }

            override fun documentadded(mutuablelivedata: Int) {
                documentadded.value = mutuablelivedata
            }

        })
    }

    fun splitresponse(mContext: Context, response:String)
    {
        dashboarddata.splitdata(mContext,response)
    }
    fun splitresponsedefaultupdate(mContext: Context, response:String)
    {
        dashboarddata.dataupdated(mContext,response)
    }

    fun splitvehicleinfo(mContext: Context, response:String)
    {
        dashboarddata.vehicleinfo(mContext,response)
    }

    fun splitdocumentinfo(mContext: Context, response:String)
    {
        dashboarddata.documentinfo(mContext,response)
    }

    fun cleardocumentdata(mContext:Context)
    {
        var mSessionManager = SessionManager(mContext!!)
        repository.deletallrecord()
        mSessionManager.clearvehicleimage()
        mSessionManager.clearvehiclenumber()
//        mSessionManager.cleatempvehicleid()
        mSessionManager.clearlocation()
        mSessionManager.clearCategory()
        mSessionManager.clearVehicleType()
        mSessionManager.clearMake()
        mSessionManager.clearModel()
        mSessionManager.clearYear()
    }
    fun deletedb(mContext:Context)
    {
        repository.deletallrecord()
    }



}