package com.mauto.chd.ui.viewmodelforvehicllist

import com.mauto.chd.Modal.DefaultVehicleListModel
import com.mauto.chd.Modal.VehicleListModel
import com.mauto.chd.Modal.vehicleinfoedit


interface VehiclePageListener {

    fun errormessage(mutuablelivedata: String)
    fun successmessage(mutuablelivedata: String)
    fun basicvehicleinfo(mutuablelivedata: vehicleinfoedit)
    fun arraylistofvehiclemodel(mutuablelivedata: ArrayList<VehicleListModel>)
    fun defaultvehiclemodel(mutuablelivedata: DefaultVehicleListModel)
    fun documentadded(mutuablelivedata: Int)

}