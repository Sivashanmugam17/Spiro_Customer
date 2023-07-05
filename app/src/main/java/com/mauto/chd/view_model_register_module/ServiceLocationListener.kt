package com.mauto.chd.view_model_register_module


import com.mauto.chd.Modal.ServiceLocationModel
import java.util.ArrayList

interface ServiceLocationListener {
    fun onDataReceived(mutableLiveData: ArrayList<ServiceLocationModel>)
    fun onError(error: Int)
}