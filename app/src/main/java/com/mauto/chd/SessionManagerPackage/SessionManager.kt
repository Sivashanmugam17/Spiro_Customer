package com.mauto.chd.SessionManagerPackage

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mauto.chd.BuildConfig

class SessionManager(var mContext: Context)
{
    var mSharedPreferenceName = "cabilyhandy"
    var mSharedPreferenceMode = 0
    private var mSharedPreference: SharedPreferences = mContext.getSharedPreferences(mSharedPreferenceName, mSharedPreferenceMode)
    private var mEditor = mSharedPreference.edit()

    // flag & country code saving
    val flagsaved = "flagsaved"
    val countrycodesaved = "countrycodesaved"
    val countrynamesaved = "countrynamesaved"

    // fcm key
    val fcmKey = "fcmkey"

    val onlineavailable = "onlineavailable"

    // online car update
    val onlinelatitude = "onlinelatitude"
    val distanceforotp = "distanceforotp"
    val onlinelongitude = "onlinelongitude"
    val onlinebearing = "onlinebearing"
    val onlineride_id = "onlineride_id"

    // online otp ride
    val onlinelatitudeotp = "onlinelatitudeotp"
    val onlinelongitudeotp = "onlinelongitudeotp"
    val onlinelatitudeprevious = "onlinelatitudeprevious"
    val onlinelongitudeprevious = "onlinelongitudeprevious"
    val distance_km_otp = "distance_km_otp"
    val onlinelatitudeSpeed = "onlinelatitudeSpeed"
    val onlinelatitudepickadress = "onlinelatitudepickadress"
    val onlinelongitudpickadress = "onlinelongitudpickadress"


    // onride page
    val onridelat = "onridelat"
    val onridelong = "onridelong"
    val onridebearing = "onridebearing"
    val onridestatsu = "onridestatsu"
    val onridecount = "onridecount"

    // step 1
    val setlocationid = "locationid"
    val setlocationname = "locationname"

    val setcategoryid = "setcategoryid"
    val setcategoryname = "setcategoryname"
    val seletedlagname= "seletedlagname"
    val reportlist = "reportlist"
    val modellist = "modellist"
    val amountlist = "amountlist"

    val currencysymbol = "currencysymbol"
    val currentmodelname = "currentmodelname"

    val setlocationmodee = "setlocationmodee"

    val tempvehicleid = "tempvehicleid"
    val vehicleid = "vehicleid"
    val driver_profile_image = "driver_profile_image"
    val offer_banare_image ="offer_banare_image"

    val profileimages = "profileimages"
    val image = "image"




    val setvehicletypeid = "setvehicletypeid"
    val setvehicletypename = "setvehicletypename"

    val setmakeid = "setmakeid"
    val setmakename = "setmakename"

    val setmodelid = "setmodelid"
    val setmmodelname = "setmmodelname"
    val setvehicleoneid = "setvehicleoneid"
    val defautbanner = "defautbanner"



    val setvehicleimage = "setvehicleimage"

    val setyear = "setyear"
    val setveehiclenumber = "setveehiclenumber"
    val messagestatus = "messagestatus"
    val verificationtatus = "messagestatus"



    val stepstoselect = "stepstoselect"

    private val distance_km: String = "distance_km"
    private val distance_meter: String = "distance_meter"
    private val duration_hours: String = "duration_hours"
    private val duration_secs: String = "duration_secs"

    private val savehittedrideid: String = "savehittedrideid"

    val catkeyres = "catkey"

    // profile details
    val hasSession = "hasSession"
    val id = "id"
    val first_name = "first_name"
    val last_name = "last_name"
    val driver_name = "driver_name"

    //s3 bucket
    val KEY_S3_BUCKET_NAME = "bucket_name"
    val KEY_S3_ACCESS_KEY = "access_key"
    val KEY_S3_SECRET_KEY = "secret_key"

    //Xmpp Data
    val XMPPHOSTURL = "xmpp_host_url"
    val XMPPHOSTNAME = "xmpp_host_name"

    //Xmpp Data
    val XMPPprofile_picture = "xmppprofile_picture"
    val XMPPvehicle_image = "xmpppvehicle_image"
    val XMPPdocuments = "xmpppdocuments"

    //on ride
    val fulljson = "fulljson"
    val ridehasSession = "ridehasSession"
    val ridestatus = "ridestatus"
    val ridestatustwo = "ridestatustwo"



    //support
    val support_email = "support_email"
    val support_messeage = "support_messeage"
    val support_subject = "support_subject"
    val dropAddressone = "dropAddressone"
    val pickpcurrenttimeotp = "pickpcurrenttimeotp"
    val for_location = "for_location"
    val locationdistance ="locationdistance"




    val support_number = "support_number"
    val dist_one = "dist_one"
    val documents_secondimage = "documents_secondimage"
    val wallet_id = "wallet_id"
    val currency_id = "currency_id"
    val kkipay_key = "kkipay_key"
    val kkipay_mode = "kkipay_mode"

    val customer_id = "customer_id"
    val selected_model = "selected_model"
    val leadname = "leadname"
    val leadid = "leadid"

    val mstroredistance = "mstroredistance"
    val google_api_key ="google_api_key"





    //settindpage
    val reroutingopetionn = "reroutingopetionn"
    val nightmodeotpion = "nightmodeotpion"
    val navigateoptionn = "navigateoptionn"


    var regis_temp_driver_id = "regis_temp_driver_id"

    val storeauthkey = "storeauthkey"

    val ridepickuplatitude = "ridepickuplatitude"
    val ridepickuplongitude = "ridepickuplongitude"
    val ridedroplatitiude = "ridedroplatitiude"
    val ridedroplongitude = "ridedroplongitude"
    val otpridedroplatitiude = "otpridedroplatitiude"
    val otpridedroplongitude = "otpridedroplongitude"
    val rideid = "rideid"
    val whreyougot = "whreyougot"

    val documentpending = "documentpending"

    val mobilecode = "mobilecode"
    val mobilenumber = "mobilenumber"
    val hasSessiontomainpage = "hasSessiontomainpage"



    val apartment = "apartment"
    val address = "address"
    val countryid = "countryid"
    val state = "state"
    val driverreview = "driverreview"
    val biketype = "biketype"
    val percentage = "percentage"
    val mileage = "mileage"
    val is_electric="is_electric"
    val is_otp_ride="is_otp_ride"
    val mstage="mstage"
    val swap_battery="swap_battery"
    val qrcode_old="qrcode_old"
    val qrcode_new="qrcode_new"


    val user_id="user_id"

    val stage="stage"
    val picktime = "picktime"
    val customerphno="customerphno"


    val begin_otp="begin_otp"

    val city = "city"
    val servicelocationid = "servicelocationid"
    val zipcode = "zipcode"
    val fleet_immobilized = "fleet_immobilized"

    val drivermobileno = "drivermobileno"
    val driver_code ="driver_code"
    val fcmtokenusers = "fcmtokenusers"
    val dutyrideid = "dutyrideid"

    val email = "email"
    val gender = "gender"
    val dob = "dob"
    val driver_image = "driver_image"
    val documentstage = "stage"
    val documentstage2 = "stage2"
    val completed_rides = "completed_rides"
    val missed_rides = "missed_rides"
    val today_earnings = "today_earnings"
    val pickup_location = "pickup_location"
    val drop_location = "drop_location"
    val muserid = "muserid"
    val mride_completed = "mride_completed"
    val normalride_completed = "normal_ride_completed"


    val driver_names = "driver_names"
    val dial_code = "dial_code"
    val driver_vehicle_new = "drivervehiclenew"
    val driver_vehicle = "agentphone"

    val loginUserType = "loginUserType"
    val model_list = "model_list"
    val model_list2= "model_list2"



    private val LOCATION_FARE_API_RESPONSE_KEY: String = "location_fare_api_response"

    fun setFlagDetails(flag: String, countrycode: String, countryname: String)
    {
        mEditor.putString(flagsaved, flag)
        mEditor.putString(countrycodesaved, countrycode)
        mEditor.putString(countrynamesaved, countryname)
        mEditor.commit()
        mEditor.apply()
    }

    fun setLoggedInUserType(flag: String)
    {
        mEditor.putString(loginUserType, flag)
        mEditor.commit()
        mEditor.apply()
    }

    fun getLoggedInUserType(): String
    {
        return mSharedPreference.getString(loginUserType, "")!!
    }

    fun getFlagDetails(): HashMap<String, String>
    {
        var flagdetails = HashMap<String, String>()
        flagdetails[flagsaved]        = mSharedPreference.getString(flagsaved, "0")!!
        flagdetails[countrycodesaved] = mSharedPreference.getString(countrycodesaved, "0")!!
        flagdetails[countrynamesaved]      = mSharedPreference.getString(countrynamesaved, "0")!!
        return flagdetails
    }
    fun setfcmkey(fcmkeys: String)
    {
        mEditor.putString(this.fcmKey, fcmkeys)
        mEditor.apply()
    }
    fun getfcmkey(): String
    {
        return mSharedPreference.getString(fcmKey, "")!!
    }

    fun setTravellingDistanceDuration(distance_km: String, distance_meter: String, duration_hours: String, duration_secs: String) {
        mEditor.putString(this.distance_km, distance_km)
        mEditor.putString(this.distance_meter, distance_meter)
        mEditor.putString(this.duration_hours, duration_hours)
        mEditor.putString(this.duration_secs, duration_secs)
        mEditor.apply()
    }

    fun storeauthkeyvalue(authkey: String) {
        mEditor.putString(this.storeauthkey, authkey)
        mEditor.apply()
    }

    fun getStoreauthKey(): String {
        return mSharedPreference.getString(storeauthkey, "")!!
    }


    fun getTravellingDistanceMeter(): String {
        return mSharedPreference.getString(distance_meter, "")!!
    }

    fun getTravellingDurationSecs(): String {
        return mSharedPreference.getString(duration_secs, "")!!
    }



    fun gethittedrideid(): String {
        return mSharedPreference.getString(savehittedrideid, "")!!
    }
    fun savehittedrideidvalue(rideid: String)
    {
        mEditor.putString(savehittedrideid, rideid)
        mEditor.commit()
    }

    fun setS3Info(str_s3_bucket_name: String, str_s3_access_key: String, str_s3_secret_key: String)
    {
        mEditor.putString(KEY_S3_BUCKET_NAME, str_s3_bucket_name)
        mEditor.putString(KEY_S3_ACCESS_KEY, str_s3_access_key)
        mEditor.putString(KEY_S3_SECRET_KEY, str_s3_secret_key)
        mEditor.commit()
    }

    fun setXmppDetails(xmpp_host_url: String, xmpp_host_name: String)
    {
        mEditor.putString(XMPPHOSTURL, xmpp_host_url)
        mEditor.putString(XMPPHOSTNAME, xmpp_host_name)
        mEditor.commit()
    }

    fun setXmppFolderPath(profile_picture: String, vehicle_image: String, documents: String)
    {
        mEditor.putString(XMPPprofile_picture, profile_picture)
        mEditor.putString(XMPPvehicle_image, vehicle_image)
        mEditor.putString(XMPPdocuments, documents)
        mEditor.commit()
    }

    fun getxmppprofile_picture(): String? {
        return mSharedPreference.getString(XMPPprofile_picture, "")
    }

    fun getxmppvehicle_image(): String? {
        return mSharedPreference.getString(XMPPvehicle_image, "")
    }

    fun getxmppdocuments(): String? {
        return mSharedPreference.getString(XMPPdocuments, "")
    }


    fun getxmpp_host_url(): String? {
        return mSharedPreference.getString(XMPPHOSTURL, "")
    }

    fun getxmpp_host_name(): String? {
        return mSharedPreference.getString(XMPPHOSTNAME, "")
    }



    fun getKeyS3BucketName(): String? {
        return mSharedPreference.getString(KEY_S3_BUCKET_NAME, "")

    }

    fun getKeyS3AccessKey(): String? {
        return mSharedPreference.getString(KEY_S3_ACCESS_KEY, "")

    }

    fun getKeyS3SecretKey(): String? {
        return mSharedPreference.getString(KEY_S3_SECRET_KEY, "")

    }


    fun getApiHeaderwithoutcontenttype(): HashMap<String, String>
    {
        var header = HashMap<String, String>()
        header.put("Apptoken", mSharedPreference.getString(fcmKey, "")!!)
        header.put("Authkey", getStoreauthKey())
        header.put("isapplication", "1")
        header.put("applanguage",getseletedlagname())
        header.put("apptype", "android")
        header.put("DeviceMaker", "")
        header.put("DeviceModel", "")
        header.put("Drivertype", "customer")
        header.put("DeviceOS", "")
        header.put("Appversion", BuildConfig.VERSION_NAME)

//        header.put("Content-Type", "application/x-www-form-urlencoded")
        header.put("driverid",getDriverId())
        return header
    }

    fun getApiHeader(): HashMap<String, String>
    {
        var header = HashMap<String, String>()
        header.put("Apptoken", mSharedPreference.getString(fcmKey, "")!!)
        header.put("Authkey", getStoreauthKey())
        header.put("isapplication", "1")
        header.put("applanguage", getseletedlagname())
        header.put("apptype", "android")
        header.put("Drivertype", "customer")
        header.put("DeviceMaker", "")
        header.put("DeviceModel", "")
        header.put("DeviceOS", "")
        header.put("Appversion", BuildConfig.VERSION_NAME)
        header.put("Content-Type", "application/x-www-form-urlencoded")
        header.put("driverid",getDriverId())
        Log.d("hgftwe","${header.put("driverid",getDriverId())}")
        return header
    }
    fun createLoginSession(idvalue: String, first_namevalue: String, last_namevalue: String,
                           driver_namevalue: String, emailvalue: String, gendervalue: String,
                           dobvalue: String, driver_imagevalue: String, stagevalue: String, mobilecodesend: String, mobilenumbersend: String, addresssend: String, countryidsend: String, statesend: String, citysend: String, servicelocationsend: String, zipcodesend: String)
    {
        mEditor.putString(id, idvalue)
        mEditor.putString(first_name, first_namevalue)
        mEditor.putString(last_name, last_namevalue)
        mEditor.putString(driver_name, driver_namevalue)
        mEditor.putString(email, emailvalue)
        mEditor.putString(gender, gendervalue)
        mEditor.putString(dob, dobvalue)
        mEditor.putString(driver_image, driver_imagevalue)
        mEditor.putString(stage, stagevalue)
        mEditor.putString(mobilecode, mobilecodesend)
        mEditor.putString(mobilenumber, mobilenumbersend)

        mEditor.putString(driverreview, "0")


        mEditor.putString(address, addresssend)
        mEditor.putString(countryid, countryidsend)
        mEditor.putString(state, statesend)
        mEditor.putString(city, citysend)
        mEditor.putString(servicelocationid, servicelocationsend)
        mEditor.putString(zipcode, zipcodesend)

        mEditor.putBoolean(hasSession, false)
        mEditor.apply()
    }

    fun driverProfileData(driver_id: String, driver_names: String, driver_imagevalue: String, emailvalue: String, mobilecodesend: String, mobilenumbersend: String)
    {
        mEditor.putString(id, driver_id)
        mEditor.putString(driver_name, driver_names)
        mEditor.putString(driver_image, driver_imagevalue)
        mEditor.putString(email, emailvalue)

        mEditor.putString(mobilecode, mobilecodesend)
        mEditor.putString(mobilenumber, mobilenumbersend)

        mEditor.apply()
    }


    fun updatemobileno(code: String, mobileno: String)
    {
        mEditor.putString(mobilecode, code)
        mEditor.putString(mobilenumber, mobileno)
        mEditor.apply()
    }

    fun updateemailalone(emailx: String)
    {
        mEditor.putString(email, emailx)
        mEditor.apply()
    }







    fun onridedetails(fulljsossn: String,status:String,whreyougotvalue:String)
    {
        mEditor.putString(fulljson, fulljsossn)
        mEditor.putString(ridestatus,status)
        mEditor.putString(ridepickuplatitude, "")
        mEditor.putString(ridepickuplongitude, "")
        mEditor.putString(ridedroplatitiude, "")
        mEditor.putString(ridedroplongitude, "")
        mEditor.putString(rideid, "")
        mEditor.putString(whreyougot,whreyougotvalue)
        mEditor.putBoolean(ridehasSession, true)
        mEditor.apply()
    }


    fun getWhereyougot(): String {
        return mSharedPreference.getString(whreyougot, "")!!
    }

    fun setUsertripfcmtoken(fcmtokenuser: String)
    {
        mEditor.putString(this.fcmtokenusers, fcmtokenuser)
        mEditor.apply()
    }


    fun setotpridedroplatitiude(otpridedroplatitiude: String)
    {
        mEditor.putString(this.otpridedroplatitiude, otpridedroplatitiude)
        mEditor.apply()
    }


    fun getotpridedroplatitiude(dest_drop_lat: String): String {
        return mSharedPreference.getString(otpridedroplatitiude, "")!!
    }

    fun setotpridedroplongitude(otpridedroplongitude: String)
    {
        mEditor.putString(this.otpridedroplongitude, otpridedroplongitude)
        mEditor.apply()
    }


    fun getotpridedroplongitude(dest_drop_lng: Double): String {
        return mSharedPreference.getString(otpridedroplongitude, "")!!
    }

    fun setDutyrideid(dutyrideids: String)
    {
        mEditor.putString(this.dutyrideid, dutyrideids)
        mEditor.apply()
    }


    fun getdutyrideid(): String {
        return mSharedPreference.getString(dutyrideid, "")!!
    }

    fun getUserfcmtoken(): String {
        return mSharedPreference.getString(fcmtokenusers, "")!!
    }

    fun setDriverMobileNO(mobno: String)
    {
        mEditor.putString(this.drivermobileno, mobno)
        mEditor.apply()
    }

    fun setDriverCuntryCode(dial_code: String)
    {
        mEditor.putString(this.driver_code, dial_code)
        mEditor.apply()
    }
    fun getDriverCuntryCode(): String {
        return mSharedPreference.getString(driver_code, "")!!
    }

    fun setmissed_rides(missed_rides: String)
    {
        mEditor.putString(this.missed_rides, missed_rides)
        mEditor.apply()
    }

    fun getmissed_rides(): String {
        return mSharedPreference.getString(missed_rides, "")!!
    }

    fun setdrop_location(drop_location: String)
    {
        mEditor.putString(this.drop_location, drop_location)
        mEditor.apply()
    }

    fun getdrop_location(): String {
        return mSharedPreference.getString(drop_location, "")!!
    }


    fun setmfleet_immobilized(fleet_immobilized: String)
    {
        mEditor.putString(this.fleet_immobilized, fleet_immobilized)
        mEditor.apply()
    }

    fun getmfleet_immobilized(): String {
        return mSharedPreference.getString(fleet_immobilized, "")!!
    }


    fun setmride_completed(mride_completed: String)
    {
        mEditor.putString(this.mride_completed, mride_completed)
        mEditor.apply()
    }

    fun getmride_completed(): String {
        return mSharedPreference.getString(mride_completed, "")!!
    }


    fun setdriver_vehicle(driver_vehicle: String)
    {
        mEditor.putString(this.driver_vehicle, driver_vehicle)
        mEditor.apply()
    }

    fun getdriver_vehicle(): String {
        return mSharedPreference.getString(driver_vehicle, "")!!
    }

    fun setdriver_vehicle_news(driver_vehicle_new: String)
    {
        mEditor.putString(this.driver_vehicle_new, driver_vehicle_new)
        mEditor.apply()
    }

    fun getdriver_vehicle_news(): String {
        return mSharedPreference.getString(driver_vehicle_new, "")!!
    }

    fun setdriver_names(driver_names: String)
    {
        mEditor.putString(this.driver_names, driver_names)
        mEditor.apply()
    }
    fun setdriver_dialcode(dial_code: String)
    {
        mEditor.putString(this.dial_code, dial_code)
        mEditor.apply()
    }
    fun getdriver_dialcode(): String {
        return mSharedPreference.getString(dial_code, "")!!
    }
    fun getdriver_names(): String {
        return mSharedPreference.getString(driver_names, "")!!
    }

    fun setnormalride_completed(normalride_completed: String)
    {
        mEditor.putString(this.normalride_completed, normalride_completed)
        mEditor.apply()
    }

    fun getnormalride_completed(): String {
        return mSharedPreference.getString(normalride_completed, "")!!
    }


    fun setpickup_location(pickup_location: String)
    {
        mEditor.putString(this.pickup_location, pickup_location)
        mEditor.apply()
    }

    fun getpickup_location(): String {
        return mSharedPreference.getString(pickup_location, "")!!
    }



    fun settoday_earnings(today_earnings: String)
    {
        mEditor.putString(this.today_earnings, today_earnings)
        mEditor.apply()
    }

    fun gettoday_earnings(): String {
        return mSharedPreference.getString(today_earnings, "")!!
    }

    fun setcompleted_rides(completed_rides: String)
    {
        mEditor.putString(this.completed_rides, completed_rides)
        mEditor.apply()
    }

    fun getcompleted_rides(): String {
        return mSharedPreference.getString(completed_rides, "")!!
    }

    fun setbiketype(biketype: String)
    {
        mEditor.putString(this.biketype, biketype)
        mEditor.apply()
    }

    fun getbiketype(): String {
        return mSharedPreference.getString(biketype, "")!!
    }

    fun setpercentage(percentage: String)
    {
        mEditor.putString(this.percentage, percentage)
        mEditor.apply()
    }

    fun getpercentage(): String {
        return mSharedPreference.getString(percentage, "")!!
    }

    fun setmileage(mileage: String)
    {
        mEditor.putString(this.mileage, mileage)
        mEditor.apply()
    }

    fun getmileage(): String {
        return mSharedPreference.getString(mileage, "")!!
    }



    fun setdocuments_secondimage(documents_secondimage: String)
    {
        mEditor.putString(this.documents_secondimage, documents_secondimage)
        mEditor.apply()
    }

    fun getdocuments_secondimage(): String {
        return mSharedPreference.getString(documents_secondimage, "")!!
    }


    fun setdist_one(dist_one: String)
    {
        mEditor.putString(this.dist_one, dist_one)
        mEditor.apply()
    }

    fun getdist_one(): String {
        return mSharedPreference.getString(dist_one, "")!!
    }


    fun setgoogle_api_key(google_api_key: String)
    {
        mEditor.putString(this.google_api_key, google_api_key)
        mEditor.apply()
    }

    fun getgoogle_api_key(): String? {
        return mSharedPreference.getString(google_api_key, "")
    }




    fun setsupport_number(support_number: String)
    {
        mEditor.putString(this.support_number, support_number)
        mEditor.apply()
    }

    fun getsupport_number(): String {
        return mSharedPreference.getString(support_number, "")!!
    }


    fun setridestatustwo(ridestatustwo: String)
    {
        mEditor.putString(this.ridestatustwo, ridestatustwo)
        mEditor.apply()
    }

    fun getridestatustwo(): String {
        return mSharedPreference.getString(ridestatustwo, "")!!
    }

    fun setmstroredistance(mstroredistance: String)
    {
        mEditor.putString(this.mstroredistance, mstroredistance)
        mEditor.apply()
    }

    fun getmstroredistance(): String {
        return mSharedPreference.getString(mstroredistance, "")!!
    }

    fun setkkipay_mode(kkipay_mode: String)
    {
        mEditor.putString(this.kkipay_mode, kkipay_mode)
        mEditor.apply()
    }

    fun getkkipay_mode(): String {
        return mSharedPreference.getString(kkipay_mode, "")!!
    }

    fun setkkipay_key(kkipay_key: String)
    {
        mEditor.putString(this.kkipay_key, kkipay_key)
        mEditor.apply()
    }

    fun getkkipay_key(): String {
        return mSharedPreference.getString(kkipay_key, "")!!
    }


    fun setleadid(leadid: String)
    {
        mEditor.putString(this.leadid, leadid)
        mEditor.apply()
    }

    fun getleadid(): String {
        return mSharedPreference.getString(leadid, "")!!
    }


    fun setleadname(leadname: String)
    {
        mEditor.putString(this.leadname, leadname)
        mEditor.apply()
    }

    fun getleadname(): String {
        return mSharedPreference.getString(leadname, "")!!
    }


    fun setselected_model(selected_model: String)
    {
        mEditor.putString(this.selected_model, selected_model)
        mEditor.apply()
    }

    fun getselected_model(): String {
        return mSharedPreference.getString(selected_model, "")!!
    }

    fun setcustomer_id(customer_id: String)
    {
        mEditor.putString(this.customer_id, customer_id)
        mEditor.apply()
    }

    fun getcustomer_id(): String {
        return mSharedPreference.getString(customer_id, "")!!
    }




    fun setcurncy_id(currency_id: String)
    {
        mEditor.putString(this.currency_id, currency_id)
        mEditor.apply()
    }
    fun getwcurncy_id(): String {
        return mSharedPreference.getString(currency_id, "")!!
    }
    fun setwallet_id(wallet_id: String)
    {
        mEditor.putString(this.wallet_id, wallet_id)
        mEditor.apply()
    }

    fun getwallet_id(): String {
        return mSharedPreference.getString(wallet_id, "")!!
    }

    fun setpickpcurrenttimeotp(pickpcurrenttimeotp: String)
    {
        mEditor.putString(this.pickpcurrenttimeotp, pickpcurrenttimeotp)
        mEditor.apply()
    }

    fun getpickpcurrenttimeotp(): String {
        return mSharedPreference.getString(pickpcurrenttimeotp, "")!!
    }
    fun setsupport_subject(support_subject: String)
    {
        mEditor.putString(this.support_subject, support_subject)
        mEditor.apply()
    }

    fun getsupport_subjecte(): String {
        return mSharedPreference.getString(support_subject, "")!!
    }
    fun setsupport_messeage(support_messeage: String)
    {
        mEditor.putString(this.support_messeage, support_messeage)
        mEditor.apply()
    }

    fun getsupport_messeage(): String {
        return mSharedPreference.getString(support_messeage, "")!!
    }



    fun setonlinelatitudeprevious(onlinelatitudeprevious: String)
    {
        mEditor.putString(this.onlinelatitudeprevious, onlinelatitudeprevious)
        mEditor.apply()
    }


    fun getonlinelongitudpickadress(): String {
        return mSharedPreference.getString(onlinelongitudpickadress, "")!!
    }


    fun setonlinelongitudpickadress(onlinelongitudpickadress: String)
    {
        mEditor.putString(this.onlinelongitudpickadress, onlinelongitudpickadress)
        mEditor.apply()
    }



    fun getonlinelatitudepickadress(): String {
        return mSharedPreference.getString(onlinelatitudepickadress, "")!!
    }


    fun setonlinelatitudepickadress(onlinelatitudepickadress: String)
    {
        mEditor.putString(this.onlinelatitudepickadress, onlinelatitudepickadress)
        mEditor.apply()
    }






    fun getonlinelatitudeprevious(): String {
        return mSharedPreference.getString(onlinelatitudeprevious, "")!!
    }


    fun setonlinelongitudeprevious(onlinelongitudeprevious: String)
    {
        mEditor.putString(this.onlinelongitudeprevious, onlinelongitudeprevious)
        mEditor.apply()
    }

    fun getonlinelongitudeprevious(): String {
        return mSharedPreference.getString(onlinelongitudeprevious, "")!!
    }

    fun getlocationspeed(): String
    {
        return mSharedPreference.getString(onlinelatitudeSpeed, "0").toString()
    }

    fun setOnlinelocationspeed(onlinelatitudeSpeed:String)
    {
        mEditor.putString(this.onlinelatitudeSpeed,  onlinelatitudeSpeed.toString())
        mEditor.apply()
    }

    fun setdistance_km_otp(distance_km_otp: String)
    {
        mEditor.putString(this.distance_km_otp, distance_km_otp)
        mEditor.apply()
    }

    fun getdistance_km_otp(): String {
        return mSharedPreference.getString(distance_km_otp, "")!!
    }





    fun setonlinelatitudeotp(onlinelatitudeotp: String)
    {
        mEditor.putString(this.onlinelatitudeotp, onlinelatitudeotp)
        mEditor.apply()
    }

    fun getonlinelatitudeotp(): String {
        return mSharedPreference.getString(onlinelatitudeotp, "")!!
    }


    fun setonlinelongitudeotp(onlinelongitudeotp: String)
    {
        mEditor.putString(this.onlinelongitudeotp, onlinelongitudeotp)
        mEditor.apply()
    }

    fun getonlinelongitudeotp(): String {
        return mSharedPreference.getString(onlinelongitudeotp, "")!!
    }

    fun setlocationdistance(locationdistance: String)
    {
        mEditor.putString(this.locationdistance, locationdistance)
        mEditor.apply()
    }

    fun getlocationdistance(): String {
        return mSharedPreference.getString(locationdistance, "")!!
    }


    fun setfor_location(for_location: String)
    {
        mEditor.putString(this.for_location, for_location)
        mEditor.apply()
    }

    fun getfor_location(): String {
        return mSharedPreference.getString(for_location, "")!!
    }


    fun setsupport_email(support_email: String)
    {
        mEditor.putString(this.support_email, support_email)
        mEditor.apply()
    }

    fun getsupport_email(): String {
        return mSharedPreference.getString(support_email, "")!!
    }



    fun setmuserid(muserid: String)
    {
        mEditor.putString(this.muserid, muserid)
        mEditor.apply()
    }

    fun getmuserid(): String {
        return mSharedPreference.getString(muserid, "")!!
    }



    fun setbegin_otp(begin_otp: String)
    {
        mEditor.putString(this.begin_otp, begin_otp)
        mEditor.apply()
    }

    fun getbegin_otp(): String {
        return mSharedPreference.getString(begin_otp, "")!!
    }

    fun setpicktime(picktime: String)
    {
        mEditor.putString(this.picktime, picktime)
        mEditor.apply()
    }

    fun getpicktime(): String {
        return mSharedPreference.getString(picktime, "")!!
    }

    fun setcustomerphno(customerphno: String)
    {
        mEditor.putString(this.customerphno, customerphno)
        mEditor.apply()
    }

    fun getcustomerphno(): String {
        return mSharedPreference.getString(customerphno, "")!!
    }

    fun setdocumentstage2(documentstage2: String)
    {
        mEditor.putString(this.documentstage2, documentstage2)
        mEditor.apply()
    }

    fun getdocumentstage2(): String {
        return mSharedPreference.getString(documentstage2, "")!!
    }

    fun setdocumentstage(documentstage: String)
    {
        mEditor.putString(this.documentstage, documentstage)
        mEditor.apply()
    }

    fun getdocumentstage(): String {
        return mSharedPreference.getString(documentstage, "")!!
    }


    fun setuser_id(user_id: String)
    {
        mEditor.putString(this.user_id, user_id)
        mEditor.apply()
    }

    fun getuser_id(): String {
        return mSharedPreference.getString(user_id, "")!!
    }




    fun setis_otp_ride(is_electric: Boolean)
    {
        mEditor.putBoolean(this.is_otp_ride, is_electric)
        mEditor.apply()
    }

    fun getis_otp_ride(): Boolean {
        return mSharedPreference.getBoolean(is_otp_ride, false)
    }

    fun setqrcode_new(qrcode_new: String)
    {
        mEditor.putString(this.qrcode_new, qrcode_new)
        mEditor.apply()
    }

    fun getqrcode_new(): String {
        return mSharedPreference.getString(qrcode_new, "")!!
    }


    fun setqrcode_old(qrcode_old: String)
    {
        mEditor.putString(this.qrcode_old, qrcode_old)
        mEditor.apply()
    }

    fun getqrcode_old(): String {
        return mSharedPreference.getString(qrcode_old, "")!!
    }



    fun setswap_battery(swap_battery: String)
    {
        mEditor.putString(this.swap_battery, swap_battery)
        mEditor.apply()
    }

    fun getswap_battery(): String {
        return mSharedPreference.getString(swap_battery, "")!!
    }



    fun setmstage(mstage: String)
    {
        mEditor.putString(this.mstage, mstage)
        mEditor.apply()
    }

    fun getmstage(): String {
        return mSharedPreference.getString(mstage, "")!!
    }
    fun setis_electric(is_electric: String)
    {
        mEditor.putString(this.is_electric, is_electric)
        mEditor.apply()
    }

    fun getis_electric(): String {
        return mSharedPreference.getString(is_electric, "")!!
    }

    fun getDriverMobileNo(): String {
        return mSharedPreference.getString(drivermobileno, "")!!
    }

    fun setRidepickuplatitude(ridepickuplatitude: String)
    {
        mEditor.putString(this.ridepickuplatitude, ridepickuplatitude)
        mEditor.apply()
    }

    fun setservicelocation(locaid: String)
    {
        mEditor.putString(this.servicelocationid, locaid)
        mEditor.apply()
    }


    fun getridepickuplatitude(): String {
        return mSharedPreference.getString(ridepickuplatitude, "")!!
    }
    fun getrideridepickuplongitude(): String {
        return mSharedPreference.getString(ridepickuplongitude, "")!!
    }


    fun setRidepickuplongitude(ridepickuplongitude: String)
    {
        mEditor.putString(this.ridepickuplongitude, ridepickuplongitude)
        mEditor.apply()
    }
    fun setRidedroplatitiude(ridedroplatitiude: String)
    {
        mEditor.putString(this.ridedroplatitiude, ridedroplatitiude)
        mEditor.apply()
    }
    fun setRidedroplongitude(ridedroplongitude: String)
    {
        mEditor.putString(this.ridedroplongitude, ridedroplongitude)
        mEditor.apply()
    }

    fun setRideid(rideidd: String)
    {
        mEditor.putString(this.rideid, rideidd)
        mEditor.apply()
    }

    fun gerrideid(): String {
        return mSharedPreference.getString(rideid, "")!!
    }


    fun setDocPending(docstatus: String)
    {
        mEditor.putString(this.documentpending, docstatus)
        mEditor.apply()
    }

    fun getDocPending(): String {
        return mSharedPreference.getString(documentpending, "")!!
    }



    fun setReview(review: String)
    {
        mEditor.putString(this.driverreview, review)
        mEditor.apply()
    }

    fun getReview(): String {
        return mSharedPreference.getString(driverreview, "0")!!
    }


    fun clearridedetails()
    {
        mEditor.putString(fulljson, "")
        mEditor.putString(ridestatus, "")
        mEditor.putString(ridestatustwo, "")
        mEditor.putString(ridepickuplatitude, "")
        mEditor.putString(ridepickuplongitude, "")
        mEditor.putString(ridedroplatitiude, "")
        mEditor.putString(ridedroplongitude, "")
        mEditor.putBoolean(ridehasSession, false)
        mEditor.apply()
    }


    fun settridestatusforcash()
    {
        mEditor.putString(ridestatus, "8")
        mEditor.apply()
    }
    fun settridestatusforcashtwo()
    {
        mEditor.putString(ridestatustwo, "8")
        mEditor.apply()
    }

    fun setreroutingoption(isChecked:Boolean)
    {
        mEditor.putBoolean(reroutingopetionn, isChecked)
        mEditor.apply()
    }

    fun setnightmodeoption(isChecked:Boolean)
    {
        mEditor.putBoolean(nightmodeotpion, isChecked)
        mEditor.apply()
    }

    fun setNavigationOption(value:String)
    {
        mEditor.putString(navigateoptionn, value)
        mEditor.apply()
    }


    fun getnavigateopition(): String {
        return mSharedPreference.getString(navigateoptionn, "1")!!
    }

    fun getreoutesettingboolean(): Boolean {
        return mSharedPreference.getBoolean(reroutingopetionn, false)
    }

    fun getnightmodeboolean(): Boolean {
        return mSharedPreference.getBoolean(nightmodeotpion, false)
    }

    fun getdriverstatus(): String {
        return mSharedPreference.getString(ridestatus, "")!!
    }

    fun ridehasSession(): Boolean {
        return mSharedPreference.getBoolean(ridehasSession, false)
    }

    fun gettripdetails(): String {
        return mSharedPreference.getString(fulljson, "")!!
    }

    fun hasSession(): Boolean {
        return mSharedPreference.getBoolean(hasSessiontomainpage, false)
    }


    fun setHasseesion(hasSessionis: Boolean) {
        mEditor.putBoolean(this.hasSessiontomainpage, hasSessionis)
        mEditor.apply()
    }



    fun getUserDetails(): HashMap<String, String> {
        var userDetails = HashMap<String, String>()
        userDetails[id] = mSharedPreference.getString(id, "")!!
        userDetails[first_name] = mSharedPreference.getString(first_name, "")!!
        userDetails[last_name] = mSharedPreference.getString(last_name, "")!!
        userDetails[driver_name] = mSharedPreference.getString(driver_name, "")!!
        userDetails[driver_image] = mSharedPreference.getString(driver_image, "")!!
        userDetails[email] = mSharedPreference.getString(email, "")!!

        userDetails[gender] = mSharedPreference.getString(gender, "")!!
        userDetails[dob] = mSharedPreference.getString(dob, "")!!
        userDetails[stage] = mSharedPreference.getString(stage, "")!!
        userDetails[mobilecode] = mSharedPreference.getString(mobilecode, "")!!
        userDetails[mobilenumber] = mSharedPreference.getString(mobilenumber, "")!!

        userDetails[apartment] = mSharedPreference.getString(apartment, "")!!
        userDetails[address] = mSharedPreference.getString(address, "")!!
        userDetails[countryid] = mSharedPreference.getString(countryid, "")!!
        userDetails[state] = mSharedPreference.getString(state, "")!!
        userDetails[city] = mSharedPreference.getString(city, "")!!
        userDetails[servicelocationid] = mSharedPreference.getString(servicelocationid, "")!!
        userDetails[zipcode] = mSharedPreference.getString(zipcode, "")!!







        return userDetails
    }

    fun createLoginSuccess()
    {
        mEditor.putBoolean(hasSession, true)
        mEditor.apply()
    }


    fun settempid( temp_driver_id: String)
    {
        mEditor.putString(regis_temp_driver_id, temp_driver_id)
        mEditor.commit()
    }

    fun gettempdriverID(): String
    {
        return mSharedPreference.getString(regis_temp_driver_id, "")!!
    }


    fun getDriverId(): String
    {
        return mSharedPreference.getString(id, "")!!
    }

    fun getLoginSuccess(): Boolean
    {
        return mSharedPreference.getBoolean(hasSession, false)
    }
    fun setCategoryDetails(catrespoanse: String)
    {
        mEditor.putString(this.catkeyres, catrespoanse)
        mEditor.apply()
    }
    fun getCategoryDetails(): String
    {
        return mSharedPreference.getString(catkeyres, "")!!
    }



    fun setOnlineAvailability(onlinestatsu: String)
    {
        mEditor.putString(this.onlineavailable, onlinestatsu)
        mEditor.apply()
    }
    fun getOnlineAvailability(): String
    {
        return mSharedPreference.getString(onlineavailable, "No")!!
    }



    fun setOnlineStatsu(onlinelatitude: Double,onlinelongitude:Double,onlinebearing:Float,onlineride_id:String)
    {
        mEditor.putString(this.onlinelatitude,  onlinelatitude.toString())
        mEditor.putString(this.onlinelongitude, onlinelongitude.toString())
        mEditor.putString(this.onlinebearing,   onlinebearing.toString())
        mEditor.putString(this.onlineride_id,   onlineride_id)
        mEditor.apply()
    }


    fun setTrackinPagesession(onlinelatitude: Double,onlinelongitude:Double,onlinebearing:Float,onlineride_id:String,ridecount:String)
    {
        mEditor.putString(this.onridelat,  onlinelatitude.toString())
        mEditor.putString(this.onridelong, onlinelongitude.toString())
        mEditor.putString(this.onridebearing,   onlinebearing.toString())
        mEditor.putString(this.onridestatsu,   onlineride_id)
        mEditor.putString(this.onridecount,   ridecount)
        mEditor.apply()
    }


//    fun getonlinelongitudeotp(): String
//    {
//        return mSharedPreference.getString(onlinelongitudeotp, "")
//    }
//    fun setonlinelongitudeotp(onlinelongitudeotp: String): String
//    {
//        return mSharedPreference.getString(this.onlinelongitudeotp, onlinelongitudeotp)
//    }
//    fun getonlinelatitudeotp(): String
//    {
//        return mSharedPreference.getString(onlinelatitudeotp, "")
//    }
//    fun setonlinelatitudeotp(onlinelatitudeotp : String): String
//    {
//        return mSharedPreference.getString(this.onlinelatitudeotp, onlinelatitudeotp)
//    }

    fun getTotalDistanceForOtp(): String
    {
        return mSharedPreference.getString(distanceforotp, "0")!!
    }
    fun setTotalDistanceForOtp(distance : String)
    {
        mEditor.putString(this.distanceforotp, distance)
        mEditor.apply()
    }



    fun getOnlineLatitiude(): String
    {
        return mSharedPreference.getString(onlinelatitude, "0")!!
    }
    fun getOnlineLongitude(): String
    {
        return mSharedPreference.getString(onlinelongitude, "0")!!
    }
    fun getOnlineBEaring(): String
    {
        return mSharedPreference.getString(onlinebearing, "0")!!
    }
    fun getOnrideCount(): String
    {
        return mSharedPreference.getString(onridecount, "0")!!
    }


    fun getTrackingLat(): String
    {
        return mSharedPreference.getString(onridelat, "0.0")!!
    }
    fun getTrackingLong(): String
    {
        return mSharedPreference.getString(onridelong, "0.0")!!
    }
    fun getbearing(): String
    {
        return mSharedPreference.getString(onridebearing, "0.0")!!
    }


    fun setLocation(locationid: String,locationname:String)
    {
        mEditor.putString(this.setlocationid, locationid)
        mEditor.putString(this.setlocationname, locationname)
        mEditor.apply()
    }
    fun clearlocation()
    {
        mEditor.putString(this.setlocationid, "")
        mEditor.putString(this.setlocationname, "")
        mEditor.apply()
    }
    fun getLocationID(): String
    {
        return mSharedPreference.getString(setlocationid, "")!!
    }
    fun getLocationName(): String
    {
        return mSharedPreference.getString(setlocationname, "")!!
    }















    fun setCategory(locationid: String,locationname:String)
    {
        mEditor.putString(this.setcategoryid, locationid)
        mEditor.putString(this.setcategoryname, locationname)
        mEditor.apply()
    }
    fun clearCategory()
    {
        mEditor.putString(this.setcategoryid, "")
        mEditor.putString(this.setcategoryname, "")
        mEditor.apply()
    }
    fun getCategoryID(): String
    {
        return mSharedPreference.getString(setcategoryid, "")!!
    }
    fun geCategoryName(): String
    {
        return mSharedPreference.getString(setcategoryname, "")!!
    }

    fun setCategoryNamealone(locationname:String)
    {
        mEditor.putString(this.setcategoryname, locationname)
        mEditor.apply()
    }



    fun getseletedlagname(): String
    {
        return mSharedPreference.getString(seletedlagname, "")!!
    }

    fun setseletedlagname(seletedlagname:String)
    {
        mEditor.putString(this.seletedlagname, seletedlagname)
        mEditor.apply()
    }

    fun setamountlist(amountlist: String) {
        mEditor.putString(this.amountlist, amountlist)
        mEditor.apply()
    }
    fun getamountlist(): String {
        return mSharedPreference.getString(amountlist, "0")!!
    }

    fun setmodellist(modellist: String) {
        mEditor.putString(this.modellist, modellist)
        mEditor.apply()
    }
    fun getmodellist(): String {
        return mSharedPreference.getString(modellist, "0")!!
    }
    fun setreportlist(reportlist: String) {
        mEditor.putString(this.reportlist, reportlist)
        mEditor.apply()
    }
    fun getreportlist(): String {
        return mSharedPreference.getString(reportlist, "0")!!
    }



    fun setcurrentmodelname(currentmodelname:String)
    {
        mEditor.putString(this.currentmodelname, currentmodelname)
        mEditor.apply()
    }

    fun getcurrentmodelname(): String
    {
        return mSharedPreference.getString(currentmodelname, "")!!
    }

    fun setcurrencysymbol(symbol:String)
    {
        mEditor.putString(this.currencysymbol, symbol)
        mEditor.apply()
    }

    fun getCurrencySymbol(): String
    {
        return mSharedPreference.getString(currencysymbol, "")!!
    }


    fun setlocationmode(setmode:String)
    {
        mEditor.putString(this.setlocationmodee, setmode)
        mEditor.apply()
    }



    fun setdriver_profile_image(driver_profile_image:String)
    {
        mEditor.putString(this.driver_profile_image, driver_profile_image)
        mEditor.apply()
    }
    fun setoffer_banner_image(offer_banare_image:String)
    {
        mEditor.putString(this.offer_banare_image, offer_banare_image)
        mEditor.apply()
    }

    fun getoffer_banner_image(): String
    {
        return mSharedPreference.getString(offer_banare_image, "")!!
    }

//    fun Slider_banare_image(offer_banare_image:String)
//    {
//        mEditor.putString(this.offer_banare_image, driver_profile_image)
//        mEditor.apply()
//    }

    fun getdriver_profile_image(): String
    {
        return mSharedPreference.getString(driver_profile_image, "")!!
    }

    fun setImageResource(profileimages:String)
    {
        mEditor.putString(this.profileimages, profileimages)
        mEditor.apply()
    }

//    fun setImage(vehicleid:String)
//    {
//        mEditor.putString(this.vehicleid, vehicleid)
//        mEditor.apply()
//    }

    fun getImageResource():String
    {
        return mSharedPreference.getString(profileimages, "")!!
    }

    fun setvehicleid(vehicleid:String)
    {
        mEditor.putString(this.vehicleid, vehicleid)
        mEditor.apply()
    }

    fun getvehicleid(): String
    {
        return mSharedPreference.getString(vehicleid, "")!!
    }


    fun settempvehicleid(tempid:String)
    {
        mEditor.putString(this.tempvehicleid, tempid)
        mEditor.apply()
    }

    fun gettempvehicleid(): String
    {
        return mSharedPreference.getString(tempvehicleid, "")!!
    }

    fun cleatempvehicleid()
    {
        mEditor.putString(this.tempvehicleid, "")
        mEditor.apply()
    }




    fun getLocationMode(): String
    {
        return mSharedPreference.getString(setlocationmodee, "0")!!
    }


    fun setVehicleType(VehicleTypeid: String,VehicleTypename:String)
    {
        mEditor.putString(this.setvehicletypeid, VehicleTypeid)
        mEditor.putString(this.setvehicletypename, VehicleTypename)
        mEditor.apply()
    }
    fun clearVehicleType()
    {
        mEditor.putString(this.setvehicletypeid, "")
        mEditor.putString(this.setvehicletypename, "")
        mEditor.apply()
    }
    fun getVehicleTypeID(): String
    {
        return mSharedPreference.getString(setvehicletypeid, "")!!
    }
    fun getVehicleTypeName(): String
    {
        return mSharedPreference.getString(setvehicletypename, "")!!
    }





    fun setVehicleImage(setvehicleimages: String)
    {
        mEditor.putString(this.setvehicleimage, setvehicleimages)
        mEditor.apply()
    }

    fun clearvehicleimage()
    {
        mEditor.putString(this.setvehicleimage, "")
        mEditor.apply()
    }

    fun getVehicleImage(): String
    {
        return mSharedPreference.getString(setvehicleimage, "")!!
    }





    fun setMake(makeid: String,makename:String)
    {
        mEditor.putString(this.setmakeid, makeid)
        mEditor.putString(this.setmakename, makename)
        mEditor.apply()
    }
    fun clearMake()
    {
        mEditor.putString(this.setmakeid, "")
        mEditor.putString(this.setmakename, "")
        mEditor.apply()
    }
    fun getMakeID(): String
    {
        return mSharedPreference.getString(setmakeid, "")!!
    }
    fun getMakeName(): String
    {
        return mSharedPreference.getString(setmakename, "")!!
    }





    fun setVehicleStepOneId(vehicleoneid: String)
    {
        mEditor.putString(this.setvehicleoneid, vehicleoneid)
        mEditor.apply()
    }

    fun getVehicleStepOneId(): String
    {
        return mSharedPreference.getString(setvehicleoneid, "")!!
    }

    fun setModelnamealone(Modelname: String)
    {
        mEditor.putString(this.setmmodelname, Modelname)
        mEditor.apply()
    }

    fun setModel(Modelid: String,Modelname:String)
    {
        mEditor.putString(this.setmodelid, Modelid)
        mEditor.putString(this.setmmodelname, Modelname)
        mEditor.apply()
    }
    fun clearModel()
    {
        mEditor.putString(this.setmodelid, "")
        mEditor.putString(this.setmmodelname, "")
        mEditor.apply()
    }
    fun getModelID(): String
    {
        return mSharedPreference.getString(setmodelid, "")!!
    }
    fun getModelName(): String
    {
        return mSharedPreference.getString(setmmodelname, "")!!
    }



















    fun setYear(year: String)
    {
        mEditor.putString(this.setyear, year)
        mEditor.apply()
    }
    fun clearYear()
    {
        mEditor.putString(this.setyear, "")
        mEditor.apply()
    }
    fun getYearID(): String
    {
        return mSharedPreference.getString(setyear, "")!!
    }



    fun setvehiclenumber(vehiclenumber: String)
    {
        mEditor.putString(this.setveehiclenumber, vehiclenumber)
        mEditor.apply()
    }

    fun clearvehiclenumber()
    {
        mEditor.putString(this.setveehiclenumber, "")
        mEditor.apply()
    }

    fun getVehicleno(): String
    {
        return mSharedPreference.getString(setveehiclenumber, "")!!
    }




    fun getverificationtatus(): String
    {
        return mSharedPreference.getString(verificationtatus, "")!!
    }

    fun setverificationtatus(verificationtatus: String)
    {
        mEditor.putString(this.verificationtatus, verificationtatus)
        mEditor.apply()
    }

    fun getmessagestatus(): String
    {
        return mSharedPreference.getString(messagestatus, "")!!
    }

    fun setmessagestatus(messagestatus: String)
    {
        mEditor.putString(this.messagestatus, messagestatus)
        mEditor.apply()
    }




    fun clearalldata()
    {
        mEditor.clear().commit()
    }






    fun setSteps(steps: String)
    {
        mEditor.putString(this.stepstoselect, steps)
        mEditor.apply()
    }
    fun getSteps(): String
    {
        return mSharedPreference.getString(stepstoselect, "")!!
    }

    fun setLocationFareApiResponse(response_str: String) {
        mEditor.putString(this.LOCATION_FARE_API_RESPONSE_KEY, response_str)
        mEditor.apply()
    }

    fun getLocationFareApiResponse(): String {
        return mSharedPreference.getString(LOCATION_FARE_API_RESPONSE_KEY, "")!!
    }

    fun getModelPojo(): ArrayList<String> {
        var aModelList: ArrayList<String> = ArrayList<String>()
        val aSessionParentInfoJSON: String = mSharedPreference.getString(model_list, null)!!
        if (aSessionParentInfoJSON != null) {
            aModelList = Gson().fromJson(
                aSessionParentInfoJSON,
                object : TypeToken<ArrayList<String>>() {}.type
            )
        }
        return aModelList
    }

    fun putModelPojo(aModelList: ArrayList<String>) {
        var aModelJson: String? = null
        val aGson = Gson()
        aModelJson = aGson.toJson(aModelList)
        mEditor.putString(model_list, aModelJson)
        mEditor.commit()
    }


    fun getModelImage(): ArrayList<String> {
        var aModelList: ArrayList<String> = ArrayList<String>()
        val aSessionParentInfoJSON: String? = mSharedPreference.getString(model_list2, null)
        if (aSessionParentInfoJSON != null ) {
            aModelList = Gson().fromJson(
                aSessionParentInfoJSON,
                object : TypeToken<ArrayList<String>>() {}.type
            )
        }
        return aModelList
    }

    fun putModelImage(aModelList: ArrayList<String>) {
        var aModelJson: String? = null
        val aGson = Gson()
        aModelJson = aGson.toJson(aModelList)
        mEditor.putString(model_list2, aModelJson)
        mEditor.commit()
    }
    fun putDefaultbanner(offer_banner_default: String)
    {
        mEditor.putString(this.defautbanner, offer_banner_default)
        mEditor.apply()
    }
    fun getDefaultbanner(): String
    {
        return mSharedPreference.getString(defautbanner, "")!!
    }

}
