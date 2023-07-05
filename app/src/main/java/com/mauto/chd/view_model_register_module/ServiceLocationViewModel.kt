package com.mauto.chd.view_model_register_module


import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mauto.chd.Modal.ServiceLocationModel
import java.util.*


class ServiceLocationViewModel(application: Application) : AndroidViewModel(application)
{
    lateinit var array: Array<String>
    private val responseLiveData = MutableLiveData<ArrayList<ServiceLocationModel>>()
    private val filterresponseLiveData = MutableLiveData<ArrayList<ServiceLocationModel>>()
    var dashboarddata: SLpagerepostiatry


    fun countryarrayobserver(): MutableLiveData<ArrayList<ServiceLocationModel>>
    {
        return responseLiveData
    }
    fun filtercountryarrayobserver(): MutableLiveData<ArrayList<ServiceLocationModel>>
    {
        return filterresponseLiveData
    }


    init
    {

        dashboarddata = SLpagerepostiatry( object : ServiceLocationListener
        {
            override fun onDataReceived(mutableLiveDataforvehicledetails: ArrayList<ServiceLocationModel>)
            {
                responseLiveData.value = mutableLiveDataforvehicledetails
            }
            override fun onError(error: Int)
            {
            }
        })
    }

    fun filter(text: String,fullcountryarray: ArrayList<ServiceLocationModel>)
    {
        val filteredCourseAry: ArrayList<ServiceLocationModel> = ArrayList()
        val courseAry : ArrayList<ServiceLocationModel> = fullcountryarray
        for (eachCourse in courseAry)
        {
            if (eachCourse.slcode!!.toLowerCase().contains(text.toLowerCase()) || eachCourse.slname!!.toLowerCase().contains(text.toLowerCase()))
            {
                filteredCourseAry.add(eachCourse)
            }
        }
        filterresponseLiveData.value=filteredCourseAry
    }

    fun splitlocationfromresponse(mContext: Context,SLresponse:String)
    {

        dashboarddata.getlocationvalue(SLresponse)
    }

    fun splitingarray(mContext: Context)
    {
       /* var countryarraylist = ArrayList<ServiceLocationModel>()
        array = mContext.resources.getStringArray(R.array.CountryCodes)
        for (j in 0 until array.size)
        {
            val codeNameArray = array[j].split(",")
            val stringBuilder = StringBuilder()
            stringBuilder.append(getCountryZipCode(codeNameArray[1].trim()))
            val countryname = stringBuilder.toString()
            val code = codeNameArray[0].trim()
            val countryshortname = codeNameArray[1].trim()
            val imageBuilder = StringBuilder()
            imageBuilder.append("flags/flag_").append(countryshortname.toLowerCase()).append(".png")
            val inputStream = mContext.assets.open(imageBuilder.toString())
            val bitmap = BitmapFactory.decodeStream(inputStream)
            countryarraylist.add(CountryselectionModel(code, stringBuilder.toString(),bitmap,countryshortname))
        }
        responseLiveData.value=countryarraylist*/
    }
    private fun getCountryZipCode(ssid: String): String
    {
        val loc = Locale("", ssid)
        return loc.displayCountry.trim { it <= ' ' }
    }
}

