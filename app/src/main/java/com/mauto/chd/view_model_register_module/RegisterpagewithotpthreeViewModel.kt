package com.mauto.chd.view_model_register_module


import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mauto.chd.R
import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.data.chatdb.DbHelper
import com.mauto.chd.data.onridelatandlong.onrideDbHelper
import org.json.JSONObject


class RegisterpagewithotpthreeViewModel : ViewModel()
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

    fun clearchatrecord(mContext:Context)
    {
        mContext.deleteDatabase(DbHelper.DATABASE_NAME)
        mContext.deleteDatabase(onrideDbHelper.DATABASE_NAME)
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
            Log.d("resendotpm", response_json_object.toString())
            try
            {
                val status = response_json_object.getString("status")
                if (status.equals("1")) {
                    val response = response_json_object.getJSONObject("response")

                    val otp = response.getString("otp")
                    val otp_status = response.getString("otp_status")
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
                if (status.equals("1")) {
                    val response = response_json_object.getString("response")
                    val response_json_object = JSONObject(response)
                    Log.d("checkresponse",response)
                    var driver_id = ""
                    var id = ""
                    var lead_id = ""
                    var name = ""
                    var selected_model = ""

                    var firstname = ""
                    var last_name = ""
                    var email = ""
                    var gender = ""
                    var dob = ""
                    var driver_image = ""
                    var dial_code = ""
                    var mobile_number = ""
                    var stage = ""
                    var type =""
                    val profile = response_json_object.getJSONObject("profile")
                    if(response_json_object.has("type")) {
                         type = response_json_object.getString("type")
                        mSessionManager!!.setLoggedInUserType(type)
                        if(type=="1"){
                            val modelArray = profile.getJSONArray("model_list")
                            mSessionManager!!.setmodellist(modelArray.toString())

                            var mModelArrayList = ArrayList<String>()
                            for (i in 0 until modelArray.length()) {
                                mModelArrayList.add(modelArray[i].toString())
                                println("--->> "+modelArray[i])
                            }
                            mSessionManager!!.putModelPojo(mModelArrayList)
                            mSessionManager!!.getModelPojo()
                        }
                    }
                    if(profile.has("id")) {
                        id = profile.getString("id")
                        mSessionManager!!.setcustomer_id(id)

                    }
                    if(profile.has("lead_id")) {
                        lead_id = profile.getString("lead_id")
                        mSessionManager!!.setleadid(lead_id)

                    }
                    if(profile.has("name")) {
                        name = profile.getString("name")
                        mSessionManager!!.setleadname(name)

                    }
                    if(profile.has("selected_model")) {
                        selected_model = profile.getString("selected_model")
                        mSessionManager!!.setselected_model(selected_model)

                    }

                    if(profile.has("driver_id")) {
                        driver_id = profile.getString("driver_id")
                    }
                    if(profile.has("first_name")) {
                        firstname = profile.getString("first_name")
                    }
                    if(profile.has("last_name")) {
                        last_name = profile.getString("last_name")
                    }
                    if(profile.has("email")) {
                        email = profile.getString("email")
                    }
                     gender = ""
                    if(profile.has("unique_code")) {
                        dob = profile.getString("unique_code")
                    }
                    if(profile.has("driver_image")) {
                        driver_image = profile.getString("driver_image")
                    }
                    if(profile.has("dial_code")) {
                        dial_code = profile.getString("dial_code")
                    }
                    if(profile.has("phone_number")) {
                        mobile_number = profile.getString("phone_number")
                    }
                    if(profile.has("stage")) {
                     stage =profile.getString("stage")
                    }
                    val address = ""
                    val country = ""
                    val state = ""
                    val city = ""
                    val zipcode = ""
                    val date=""


//                    var spf = SimpleDateFormat("yyyy-MM-dd")
//                    val newDate: Date = spf.parse(dob)
//                    spf = SimpleDateFormat("dd/MM/yyyy")
//                    var date = spf.format(newDate)

                    mSessionManager!!.createLoginSession(driver_id,firstname,last_name,firstname,email,gender,date,driver_image,"2",dial_code,mobile_number,address,country,state,city,"",zipcode)
                    mSessionManager!!.setvehiclenumber("")
                    mSessionManager!!.setModelnamealone("")
                    mSessionManager!!.setReview("0")
                    mSessionManager!!.setDocPending("1")
                    mSessionManager!!.setCategoryNamealone("")
                    mSessionManager!!.setHasseesion(true)
                    if(type=="0") {
                        decdeclasstosend.value = 1
                    }else{
                        decdeclasstosend.value = 2
                    }
                }
                else {
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

