package com.mauto.chd.view_model_tracking_page

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mauto.chd.SessionManagerPackage.LanguageSessionManager
import com.mauto.chd.SessionManagerPackage.SessionManager


class settingspageviewmodel(application: Application) : AndroidViewModel(application)
{

    private val reroutingoptionsettings = MutableLiveData<Boolean>()
    private val nightmodeoptionsettings = MutableLiveData<Boolean>()
    private val navigateoption = MutableLiveData<String>()
    private val languageoption = MutableLiveData<String>()
    private var mSessionManager: SessionManager? = null
    private val navigatesheetopen = MutableLiveData<Int>()

    fun reroutingoptionsettingsobserver(): MutableLiveData<Boolean>
    {
        return reroutingoptionsettings
    }
    fun navigatevalueobserver(): MutableLiveData<String>
    {
        return navigateoption
    }
    fun languageoptionobserver(): MutableLiveData<String>
    {
        return languageoption
    }
    fun navigateobserver(): MutableLiveData<Int>
    {
        return navigatesheetopen
    }

    fun nighmodesettingsobeser(): MutableLiveData<Boolean>
    {
        return nightmodeoptionsettings
    }
    fun reroutingoption(mContext: Context,isChecked:Boolean)
    {
        mSessionManager = SessionManager(mContext!!)
        try
        {
            mSessionManager!!.setreroutingoption(isChecked)
            reroutingoptionsettings.value = isChecked
        }
        catch (e: Exception)
        {
        }
    }

    fun nightmodeoption(mContext: Context,isChecked:Boolean)
    {
        mSessionManager = SessionManager(mContext!!)
        try
        {
            mSessionManager!!.setnightmodeoption(isChecked)
            nightmodeoptionsettings.value = isChecked
        }
        catch (e: Exception)
        {
        }
    }

    fun navigationchoosen(mContext: Context,value:String)
    {
        mSessionManager = SessionManager(mContext!!)
        try
        {
            mSessionManager!!.setNavigationOption(value)
        }
        catch (e: Exception)
        {
        }
    }
    fun languagechoosen(mContext: Context,value:String)
    {
        var mSessionManager = LanguageSessionManager(mContext!!)
        try
        {
            mSessionManager!!.setLanguageChoosen(value)
        }
        catch (e: Exception)
        {
        }
    }


    fun navigationsheetopen()
    {
        navigatesheetopen.value = 1
    }

    fun getreroutingoption(mContext: Context)
    {
        mSessionManager = SessionManager(mContext!!)
        try
        {
            reroutingoptionsettings.value = mSessionManager!!.getreoutesettingboolean()
        }
        catch (e: Exception)
        {
        }
    }
    fun getnightmodestyle(mContext: Context)
    {
        mSessionManager = SessionManager(mContext!!)
        try
        {
            nightmodeoptionsettings.value = mSessionManager!!.getnightmodeboolean()
        }
        catch (e: Exception)
        {
        }
    }

    fun getnavigationoption(mContext: Context)
    {
        mSessionManager = SessionManager(mContext!!)
        try
        {
            navigateoption.value = mSessionManager!!.getnavigateopition()
        }
        catch (e: Exception)
        {
        }
    }

    fun getlanguagechoosen(mContext: Context)
    {
        var mSessionManager = LanguageSessionManager(mContext!!)
        try
        {
            languageoption.value = mSessionManager!!.getlanguageoption()
        }
        catch (e: Exception)
        {
        }
    }



}