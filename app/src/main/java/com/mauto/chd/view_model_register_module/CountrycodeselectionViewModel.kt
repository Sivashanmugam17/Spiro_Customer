package com.mauto.chd.view_model_register_module


import android.app.Application
import android.content.Context
import android.graphics.BitmapFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mauto.chd.Modal.CountryselectionModel
import com.mauto.chd.R
import java.util.*


class CountrycodeselectionViewModel(application: Application) : AndroidViewModel(application)
{
    lateinit var array: Array<String>
    private val responseLiveData = MutableLiveData<ArrayList<CountryselectionModel>>()
    private val filterresponseLiveData = MutableLiveData<ArrayList<CountryselectionModel>>()

    fun countryarrayobserver(): MutableLiveData<ArrayList<CountryselectionModel>>
    {
        return responseLiveData
    }
    fun filtercountryarrayobserver(): MutableLiveData<ArrayList<CountryselectionModel>>
    {
        return filterresponseLiveData
    }
    fun filter(text: String,fullcountryarray: ArrayList<CountryselectionModel>)
    {
        val filteredCourseAry: ArrayList<CountryselectionModel> = ArrayList()
        val courseAry : ArrayList<CountryselectionModel> = fullcountryarray
        for (eachCourse in courseAry)
        {
            if (eachCourse.countryname!!.toLowerCase().contains(text.toLowerCase()) || eachCourse.countrycode!!.toLowerCase().contains(text.toLowerCase()))
            {
                filteredCourseAry.add(eachCourse)
            }
        }
        filterresponseLiveData.value=filteredCourseAry
    }
    fun splitingarray(mContext: Context)
    {
        var countryarraylist = ArrayList<CountryselectionModel>()
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
        responseLiveData.value=countryarraylist
    }
    private fun getCountryZipCode(ssid: String): String
    {
        val loc = Locale("", ssid)
        return loc.displayCountry.trim { it <= ' ' }
    }
}

