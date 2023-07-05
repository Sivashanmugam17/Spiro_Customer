package com.mauto.chd.viewmodelformobileupdate


import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mauto.chd.R
import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.SessionManagerPackage.SessionManager
import org.json.JSONObject


class UpdtemobileotpthreeViewModel : ViewModel()
{
    private val countrycodestringemit = MutableLiveData<String>()
    private var mSessionManager: SessionManager? = null
    private val otpwithstatus = MutableLiveData<String>()
    private val movetonextpage = MutableLiveData<Int>()
    private val closeactvitiy = MutableLiveData<Int>()
    private val resendotp = MutableLiveData<Int>()

    private val decdeclasstosend = MutableLiveData<Int>()

    fun countrycodeobservervalue(): MutableLiveData<String>
    {
        return countrycodestringemit
    }
    fun otpwithstatusobservervalue(): MutableLiveData<String>
    {
        return otpwithstatus
    }
    fun decideclassobserver(): MutableLiveData<Int>
    {
        return decdeclasstosend
    }
    fun getflagdetailsfromsession(mContext: Context)
    {
        mSessionManager = SessionManager(mContext)
        var countrycode= mSessionManager?.getFlagDetails()!![mSessionManager!!.countrycodesaved]!!
        countrycodestringemit.value = countrycode
    }


    fun movetonextpageobserver(): MutableLiveData<Int>
    {
        return movetonextpage
    }
    fun closescreenobserver(): MutableLiveData<Int>
    {
        return closeactvitiy
    }

    fun resendobserver(): MutableLiveData<Int>
    {
        return resendotp
    }



    fun closepage()
    {
        closeactvitiy.value = 1
    }

    fun resendotpcall()
    {
        resendotp.value = 1
    }

    fun splitresponse(finalresponse:String)
    {
        try
        {
            val response_json_object = JSONObject(finalresponse)
            try
            {
                val status = response_json_object.getString("status")
                if (status.equals("1"))
                {
                    val otp = response_json_object.getString("otp")
                    val otp_status = response_json_object.getString("otp_status")
                    otpwithstatus.value=otp+","+otp_status
                }
            }
            catch (e: Exception)
            {
            }
        }
        catch (e: Exception)
        {

        }
    }


    fun splitexistingcall(finalresponse:String)
    {
        try
        {
            val response_json_object = JSONObject(finalresponse)
            try
            {
                val status = response_json_object.getString("status")
                if (status.equals("1"))
                {
                    val country_code = response_json_object.getString("country_code")
                    val phone_number = response_json_object.getString("phone_number")

                    mSessionManager!!.updatemobileno(country_code,phone_number)
                    decdeclasstosend.value = 1
                }
                else
                {
                    decdeclasstosend.value = 0
                }
            }
            catch (e: Exception)
            {
            }
        }
        catch (e: Exception)
        {

        }
    }

    fun startmobileapi(mContext: Context,mobileno: String,code:String)
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.resendotp))
        intent.putExtra("mobileno", mobileno)
        intent.putExtra("code", code)
        mContext.startService(intent)
    }

    override fun onCleared()
    {
    }
}

