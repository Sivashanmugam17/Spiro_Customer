package com.mauto.chd.view_model_with_repositary_main


import com.mauto.chd.Modal.DriverBasicData

interface editListener {
    fun onUserDataReceived(mutableLiveData: DriverBasicData)
    fun errormessage(mutuablelivedata: String)
    fun documentadded(mutuablelivedata: Int)
    fun updatemobileno(mutuablelivedata: String)
    fun uodatemailid(mutuablelivedata: String)

}