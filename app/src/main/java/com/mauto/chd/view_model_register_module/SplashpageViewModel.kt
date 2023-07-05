package com.mauto.chd.view_model_register_module


import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.ui.MainPage.turnlocationonandintenet
import mauto_customer.ui.MainscreenCustomers
import mauto_customer.ui.PreDashboard
import mauto_customer.ui.sidemenus.Loginpage

class SplashpageViewModel : ViewModel()
{
    private val countrycodestringemit = MutableLiveData<Boolean>()
    private val hasseeionornot = MutableLiveData<Boolean>()
    private var mSessionManager: SessionManager? = null
    private val getstepsvalue = MutableLiveData<String>()
    private val checkauth = MutableLiveData<String>()
    fun sessionobservervalue(): MutableLiveData<Boolean>
    {
        return countrycodestringemit
    }
    fun getauthval(): MutableLiveData<String>
    {
        return checkauth
    }
    fun hassessionobservervalue(): MutableLiveData<Boolean>
    {
        return hasseeionornot
    }
    fun getstepsobservervalue(): MutableLiveData<String>
    {
        return getstepsvalue
    }
    fun getLoginsession(mContext: Context)
    {
        mSessionManager = SessionManager(mContext)
        var loginstatus= mSessionManager?.getLoginSuccess()
        countrycodestringemit.value = loginstatus
    }
    fun getMainPage(mContext: Context)
    {
        mSessionManager = SessionManager(mContext)
        var loginstatus= mSessionManager?.hasSession()
        hasseeionornot.value = loginstatus
    }
    fun getStepsValue(mContext: Context)
    {
        mSessionManager = SessionManager(mContext)
        if(!mSessionManager!!.getStoreauthKey().equals(""))
        {
            var steps= mSessionManager?.getSteps()
            getstepsvalue.value = steps
        }
    }
    fun checkautvalue(mContext: Context)
    {
        mSessionManager = SessionManager(mContext)
        if(mSessionManager!!.getStoreauthKey().equals("")) checkauth.value = mSessionManager!!.getStoreauthKey()
    }
    //intent calling part
    fun documentpageone(mContext:Context)
    {
        Log.d("chesdfck","fsgdfghgh")
        val intent2 = Intent(mContext, turnlocationonandintenet::class.java)
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        mContext.startActivity(intent2)
    }
    fun registerpageones(mContext:Context)
    {
        val intent2 = Intent(mContext, Loginpage()::class.java)
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        mContext.startActivity(intent2)
    }
    fun mainpage(mContext:Context)
    {
        Log.d("cvdjsfhds","dshfhjdhfgh")

        if (mSessionManager!!.getLoggedInUserType().equals("0")){
            val intent2 = Intent(mContext, MainscreenCustomers()::class.java)
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mContext.startActivity(intent2)
        }else{
            val intent2 = Intent(mContext, PreDashboard()::class.java)
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mContext.startActivity(intent2)
        }

    }
    override fun onCleared()
    {
    }
}

