package com.mauto.chd.viewmodelfortriplist

import com.mauto.chd.Modal.tripdetailsModel
import java.util.ArrayList

interface tripdetailsListener {
    fun failedobserver(mutuablelivedata: String)
    fun insertedobserver(mutuablelivedata: String)
    fun secondsplit(mutuablelivedata: String)
    fun firstsplit(mutuablelivedata: String)
    fun stoploadinglast(mutuablelivedata: String)
    fun responseLiveData(mutuablelivedata: ArrayList<tripdetailsModel>)
    fun responseLiveDataSecond(mutuablelivedata: ArrayList<tripdetailsModel>)
    fun responseLiveDataFirst(mutuablelivedata: ArrayList<tripdetailsModel>)

}