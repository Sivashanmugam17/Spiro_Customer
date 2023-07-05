package com.mauto.chd.view_model_register_module


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mauto.chd.R
import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.SessionManagerPackage.LanguageSessionManager
import com.mauto.chd.SessionManagerPackage.SessionManager
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.util.*


class RegisterpagetwoViewModel : ViewModel()
{
    private val countrycodestringemit = MutableLiveData<String>()
    private val countryname = MutableLiveData<String>()
    private val otpwithstatus = MutableLiveData<String>()
    private val do_action = MutableLiveData<String>()
    private val bitmapimage = MutableLiveData<Bitmap>()
    private var mSessionManager: SessionManager? = null
    private var languagemSessionManager: LanguageSessionManager? = null
    private val getcountrycodewithshortname = MutableLiveData<String>()
    private val countryselectionactvitiy = MutableLiveData<Int>()
    private val closeoperation = MutableLiveData<Int>()
    private val clearmobilenumber = MutableLiveData<Int>()
    private val loadme = MutableLiveData<Int>()
    private val decdeclasstosend = MutableLiveData<Int>()

    // start of observer part
    fun countryselectionobserver(): MutableLiveData<Int>
    {
        return countryselectionactvitiy
    }
    fun do_actionobserver(): MutableLiveData<String>
    {
        return do_action
    }
    fun clearmobilenumberobserver(): MutableLiveData<Int>
    {
        return clearmobilenumber
    }
    fun loadmeobserver(): MutableLiveData<Int>
    {
        return loadme
    }
    fun closeoperationobserver(): MutableLiveData<Int>
    {
        return closeoperation
    }
    fun decideclassobserver(): MutableLiveData<Int>
    {
        return decdeclasstosend
    }

    fun flagbase64stringobservervalue(): MutableLiveData<Bitmap>
    {
        return bitmapimage
    }
    fun countrycodeobservervalue(): MutableLiveData<String>
    {
        return countrycodestringemit
    }
    fun countrynameobservervalue(): MutableLiveData<String>
    {
        return countryname
    }
    fun otpwithstatusobservervalue(): MutableLiveData<String>
    {
        return otpwithstatus
    }
    fun countrycodewithshortnameobservervalue(): MutableLiveData<String>
    {
        return getcountrycodewithshortname
    }
    // End of observer part
    //get default flag details
    fun getflagdetailsfromsession(mContext: Context)
    {
        mSessionManager = SessionManager(mContext)
        var countrycode= mSessionManager?.getFlagDetails()!![mSessionManager!!.countrycodesaved]!!
        var countrynamesaved= mSessionManager?.getFlagDetails()!![mSessionManager!!.countrynamesaved]!!
        getcountrycodewithshortname.value = countrycode+","+countrynamesaved
    }
    //get default flag details
    fun defaultflagprocessor(mContext: Context,defaultflagcode:String)
    {
        val codeNameArray = defaultflagcode.split(",")
        val stringBuilder = StringBuilder()
        stringBuilder.append(getCountryZipCode(codeNameArray[1].trim()))
        val codes = codeNameArray[0].trim()
        val imageName = codeNameArray[1].trim()
        val imageBuilder = StringBuilder()
        imageBuilder.append("flags/flag_").append(imageName.toLowerCase()).append(".png")
        val inputStream = mContext.assets.open(imageBuilder.toString())
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        val flagbase64string = Base64.encodeToString(b, Base64.DEFAULT)
        mSessionManager = SessionManager(mContext)
        mSessionManager!!.setFlagDetails(flagbase64string,codes,imageName)
        bitmapimage.value=StringToBitMap(flagbase64string)
        countrycodestringemit.value=codes
        countryname.value=imageName
    }
    // hit otp page call
    fun choosecountryclass(click:Int)
    {
        if(click == 1)
        countryselectionactvitiy.value = 1
    }
    // close app
    fun closeoperation()
    {
        closeoperation.value = 1
    }
    // clear mobile number
    fun clearmobilenumber()
    {
        clearmobilenumber.value = 1

    }
    fun languagechoosen(mContext: Context,value:String)
    {
        languagemSessionManager = LanguageSessionManager(mContext!!)
        try
        {
            languagemSessionManager!!.setLanguageChoosen(value)
        }
        catch (e: Exception)
        {
        }
    }
    // hit otp page call
    fun otppage()
    {
        loadme.value = 1
    }
    // convert string to bitmap
    fun StringToBitMap(encodedString: String): Bitmap?
    {
        try
        {
            val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        }
        catch (e: Exception)
        {
            e.message
            return null
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
                    Log.d("responses202",response)

                    val response_json_object = JSONObject(response)
                    val profile = response_json_object.getJSONObject("profile")
                    val driver_id = profile.getString("driver_id")
                    val firstname = profile.getString("driver_name")
                    val last_name = profile.getString("last_name")
                    val email = profile.getString("email")
                    val gender = ""
                    val dob = profile.getString("unique_code")
                    val driver_image = profile.getString("driver_image")
                    val dial_code = profile.getString("dial_code")
                    val mobile_number = profile.getString("phone_number")
                    val address = ""
                    val country = ""
                    val state = ""
                    val city = ""
                    val zipcode = ""
                    val date=""
                    Log.d("checking25852s",driver_id)


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







    //getting response and splitting otp
    fun splitresponse(mContext:Context,finalresponse:String)
    {
        try
        {
            val response_json_object = JSONObject(finalresponse)
            try
            {
                val status = response_json_object.getString("status")
                if (status.equals("1"))
                {
//                    val otp = response_json_object.getString("otp")
//                    val otp_status = response_json_object.getString("otp_status")
//                    if(response_json_object.has("temp_driver_id"))
//                    {
//                        mSessionManager = SessionManager(mContext)
//                        mSessionManager!!.settempid(response_json_object.getString("temp_driver_id"))
//                    }
//                    val do_actions = response_json_object.getString("do_action")
//
//                    otpwithstatus.value=otp+","+otp_status+","+do_actions

                    val response =response_json_object.getJSONObject("response")
                    Log.d("checkingslogin", response.toString())
                    val otp = response.getString("otp")
                    val do_actions = response.getString("do_action")
                    val otp_status = response.getString("otp_status")
                    otpwithstatus.value=otp+","+otp_status+","+do_actions





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
    //hitting api call
    fun startmobileapi(mContext: Context,mobileno: String,code:String,Country_name:String)
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.registerpageoneapi))
        intent.putExtra("mobileno", mobileno)
        intent.putExtra("code", code)
        intent.putExtra("Country_name",Country_name)
        mContext.startService(intent)
    }
    //method for getting country code
    private fun getCountryZipCode(ssid: String): String
    {
        val loc = Locale("", ssid)
        return loc.displayCountry.trim { it <= ' ' }
    }
    public override fun onCleared()
    {

    }
}

