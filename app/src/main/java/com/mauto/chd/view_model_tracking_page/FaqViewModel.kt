package com.mauto.chd.view_model_tracking_page


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.backgroundservices.ridedetailsapicall
import com.mauto.chd.backgroundservices.tripupdatecallapi
import com.mauto.chd.SessionManagerPackage.LanguageSessionManager
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.google.android.gms.maps.model.LatLng
import com.mauto.chd.R
//import kotlinx.android.synthetic.main.otpride.*
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.util.*


class FaqViewModel : ViewModel()
{

    lateinit var otpRideRepostiatry:OtpRideRepostiatry

    private val onlocationfareapiresult = MutableLiveData<LocationFareApiParserResult>()
    private val estimatefareobserver = MutableLiveData<EstimateFareApiResult>()
    private val driverdetailsviewmodell = MutableLiveData<ArrayList<TrackingPageDataModel>>()
    private val errormessage = MutableLiveData<String>()
    private val countrycodestringemit = MutableLiveData<String>()
    private val otpwithstatus = MutableLiveData<String>()
    private val do_action = MutableLiveData<String>()
    private val bitmapimage = MutableLiveData<Bitmap>()
    var mSessionManager: SessionManager? = null
    private var languagemSessionManager: LanguageSessionManager? = null
    private val getcountrycodewithshortname = MutableLiveData<String>()
    private val countryselectionactvitiy = MutableLiveData<Int>()
    private val closeoperation = MutableLiveData<Int>()
    private val clearmobilenumber = MutableLiveData<Int>()
    private val loadme = MutableLiveData<Int>()
    private val oncashrecived = MutableLiveData<Int>()
    private val estDistanceS = MutableLiveData<String>()
    private val mesttime = MutableLiveData<String>()
    private val mtopic = MutableLiveData<JSONObject>()



    init {
        otpRideRepostiatry= OtpRideRepostiatry(object :OtpRidePageListener{
            override fun locationfareapiresult(mutuablelivedata: LocationFareApiParserResult) {
                onlocationfareapiresult.value = mutuablelivedata
            }

            override fun estimatefareapi(mutuablelivedata: EstimateFareApiResult) {
                estimatefareobserver.value = mutuablelivedata
            }

            override fun onDataReceived(mutableLiveDataforvehicledetails: ArrayList<TrackingPageDataModel>) {
                driverdetailsviewmodell.value = mutableLiveDataforvehicledetails
            }

            override fun oncashrecived(cashrecived: Int) {
                oncashrecived.value = cashrecived
            }

            override fun estDistance(estDistance: String) {
                estDistanceS.value=estDistance

            }

            override fun estTime(estTime: String) {
                mesttime.value=estTime
            }

            override fun setonhourd(esthours: String) {
            }

            override fun estimateamont(mutableLiveData: String) {
            }


        })
    }

    fun choosecountryclass(click:Int)
    {
        if(click == 1)
            countryselectionactvitiy.value = 1
    }
    fun esttimeobserver(): MutableLiveData<String>
    {
        return mesttime
    }

    fun estDistanceeobservers(): MutableLiveData<String>
    {
        return estDistanceS
    }

    fun mtopicobserver(): MutableLiveData<JSONObject> {
        return mtopic
    }

    fun driveretailsviewmodellObserver(): MutableLiveData<ArrayList<TrackingPageDataModel>>
    {
        return driverdetailsviewmodell
    }
    fun countryselectionobserver(): MutableLiveData<Int>
    {
        return countryselectionactvitiy
    }

    fun flagbase64stringobservervalue(): MutableLiveData<Bitmap>
    {
        return bitmapimage
    }



    fun getestimatefareobserver(): MutableLiveData<EstimateFareApiResult>
    {
        return estimatefareobserver
    }

    fun getlocationfareapiresultobserver(): MutableLiveData<LocationFareApiParserResult>
    {
        return onlocationfareapiresult
    }

    fun locationFareApiParser(mContext: Activity, response:String, pickup_latlng: LatLng, rideModeIntent:String, nearest_driver_arriving_time:String, cab_avail:String)
    {
        otpRideRepostiatry.locationFareApiParser(mContext,response,pickup_latlng,rideModeIntent,nearest_driver_arriving_time,cab_avail)
    }

    fun estimatedFareCalculation(mContext: Activity,response:String,pickup_latlng:LatLng,estimated_travel_time:Double,estimated_km:Double)
    {
        otpRideRepostiatry.estimatedFareCalculation(mContext,response,pickup_latlng,estimated_travel_time,estimated_km)
    }

    fun countrycodeobservervalue(): MutableLiveData<String>
    {
        return countrycodestringemit
    }
    fun otpwithstatusobservervalue(): MutableLiveData<String>
    {
        return otpwithstatus
    }

    fun oncashrecivedobsercver(): MutableLiveData<Int>
    {
        return oncashrecived
    }

    fun etacalculation(context:Context,start_latlng: LatLng, end_latlng: LatLng)
    {
        mSessionManager = SessionManager(context!!)
        otpRideRepostiatry.etacalculation(context,start_latlng,end_latlng)
    }

    fun startroutecall(mContext: Context,droplat:Double,droplong:Double)
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.routeapicall))
        intent.putExtra("droplat", droplat.toString())
        intent.putExtra("droplong", droplong.toString())
        mContext.startService(intent)
    }

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
    }

    fun closeoperation()
    {
        closeoperation.value = 1
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

    fun FaqApiParser(response: String) {
        val response_json_object = JSONObject(response)
        try {
            val status = response_json_object.getString("status")
            if (status.equals("1")) {
//                coupon_code_et.setText("")

                val response = response_json_object.getJSONObject("response")
//                val response_faq_array = response.getJSONArray("faq")
//
//                if (response_faq_array.length() > 0){
//                    for (i in 0 until response_faq_array.length()){
//                        val response_object_faq= response_faq_array.getJSONObject(i)
//
//                        val topic = response_object_faq.getString("topic")
//                        var response_faq_array2 = response_object_faq.getJSONArray("faq")
//
//                        if (response_faq_array2.length() > 0){
//                            for (k in 0 until response_faq_array2.length()){
//                                val response_object_faq2= response_faq_array2.getJSONObject(k)
//
//                                question = response_object_faq2.getString("question")
//                                 answer = response_object_faq2.getString("answer")
//
//
//
//                            }
//
//                        }

                        mtopic.value = response










            } else if (status.equals("0")) {
                val response_error = response_json_object.getString("response")
//                response_list.add(status)
//                response_list.add(response_error)
            }
        } catch (e: java.lang.Exception) {
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
                    val otp = response_json_object.getString("otp")
                    val otp_status = response_json_object.getString("otp_status")
                    val do_actions = response_json_object.getString("do_action")
                    otpwithstatus.value=otp+","+otp_status+","+do_actions
                }
                else
                {
                    val response = response_json_object.getString("response")
                    errormessage.value = response
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
    fun startmobileapi(mContext: Context,mobileno: String)
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.updateemailidalone))
        intent.putExtra("email", mobileno)
        mContext.startService(intent)
    }
    //method for getting country code
    private fun getCountryZipCode(ssid: String): String
    {
        val loc = Locale("", ssid)
        return loc.displayCountry.trim { it <= ' ' }
    }

    fun callGetOtpApi(context: Context,pickupLatLng:LatLng,number:String)
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(context, serviceClass)
        intent.putExtra("api_name",context.getString(R.string.api_user_get_otp))
        intent.putExtra("driver_id",mSessionManager?.getDriverId())
        intent.putExtra("dial_code",mSessionManager!!.getUserDetails()!![mSessionManager!!.mobilecode]!!)
        intent.putExtra("mobile_number",number)
        intent.putExtra("lat", pickupLatLng?.latitude.toString())
        intent.putExtra("lon", pickupLatLng?.longitude.toString())
        context.startService(intent)

    }

    fun callBeginOtpRide(context: Context,number:String,pickUp:String,pickupLatLng:LatLng,drop:String,dropLatLng:LatLng,est_amount:String,est_distance:String,est_duration:String)
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(context, serviceClass)
        intent.putExtra("api_name",context.getString(R.string.api_otp_ride_begin))
        intent.putExtra("driver_id",mSessionManager?.getDriverId())
        intent.putExtra("dial_code",mSessionManager!!.getUserDetails()!![mSessionManager!!.mobilecode]!!)
        intent.putExtra("mobile_number",number)
        intent.putExtra("pickup",pickUp)
        intent.putExtra("pickup_lat", pickupLatLng?.latitude.toString())
        intent.putExtra("pickup_lon", pickupLatLng?.longitude.toString())
        intent.putExtra("drop",drop)
        intent.putExtra("drop_lat",dropLatLng?.latitude.toString())
        intent.putExtra("drop_lon",dropLatLng?.longitude.toString())
        intent.putExtra("est_amount",est_amount)
        intent.putExtra("est_distance",est_distance)
        intent.putExtra("est_duration",est_duration)
        context.startService(intent)
    }

    fun tripupdatestatus(mContext: Context,droplatitude:String,droplongitude:String,useridtosend:String)
    {
        val serviceClass = tripupdatecallapi::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.tripupdate))
        intent.putExtra("droplat",droplatitude)
        intent.putExtra("droplong", droplongitude)
        intent.putExtra("useridtosend", useridtosend)
        mContext.startService(intent)
    }

    fun receivedcash(mContext:Context,rideid:String,amount:String)
    {
        mSessionManager = SessionManager(mContext!!)
        try
        {
            val serviceClass = commonapifetchservice::class.java
            val intent = Intent(mContext, serviceClass)
            intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.cashreceived))
            intent.putExtra("ride_id", rideid)
            intent.putExtra("amount", amount)
            mContext.startService(intent)
        }
        catch (e: Exception)
        {
        }
    }

    fun getdataforride(mContext:Context)
    {
        val serviceClass = ridedetailsapicall::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.ridedetails))
        mContext.startService(intent)
    }


    fun cleartripdetails2(context:Context)
    {
        mSessionManager = SessionManager(context!!)
        mSessionManager!!.clearridedetails()
//        cancellationfailedtryagain.value = 1
    }

    fun getsplitrepsonse(context:Context)
    {
        mSessionManager = SessionManager(context!!)
        if(!mSessionManager!!.gettripdetails().equals(""))
            otpRideRepostiatry.getdrivertails(mSessionManager!!.gettripdetails(),context)
//        dashboarddata.getdrivertailarrived(mSessionManager!!.gettripdetails(),context)
    }

    fun splittripupdate(context:Context,rasponse:String)
    {
        otpRideRepostiatry.getdrivertails(rasponse,context)
//        dashboarddata.getdrivertailarrived(rasponse,context)
    }

    fun splitbasedoncashresponse(context:Context,rasponse:String)
    {
        otpRideRepostiatry.getcashresponse(rasponse,context)
    }

}

