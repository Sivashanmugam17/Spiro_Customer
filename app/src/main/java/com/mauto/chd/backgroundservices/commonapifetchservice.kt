@file:Suppress("Annotator")

package com.mauto.chd.backgroundservices


import android.annotation.SuppressLint
import android.app.Activity
import android.app.IntentService
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.IBinder
import android.util.Base64
import android.util.Log
import com.mauto.chd.event_bus_connection.IntentServiceReroutingRouteResult
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.event_bus_connection.IntentServiceRouteResult
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.view_model_ride_request.RouteDataParser
import com.mauto.chd.retrofit.RetrofitInstance
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import com.google.firebase.iid.FirebaseInstanceId
import com.mauto.chd.R
import com.mauto.chd.retrofit.RetrofitInstance.offerpaymentstatuscheck
import com.mindorks.kotnetworking.KotNetworking
import com.mindorks.kotnetworking.common.Priority
import okhttp3.ResponseBody
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset
import java.util.*
import kotlin.collections.ArrayList


class commonapifetchservice : IntentService("ApiHit") {
    lateinit private var mSessionManager: SessionManager
    private var apiCall: Call<ResponseBody>? = null
    var responsetosend: String = "failed"
    var api_name: String = ""
    lateinit var waypoints_array: ArrayList<LatLng>
    private var stops_location_array: ArrayList<String>? = null

    var header = HashMap<String, String>()
    var urltohit: String = ""

    // mobily verify call
    var mobmobileno: String = ""
    var mobcode: String = ""
    var Country_name = ""

    // mobily verify call
    var otpmobileno: String = ""
    var otpcode: String = ""

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onHandleIntent(intent: Intent?) {
        waypoints_array = ArrayList()
        stops_location_array = ArrayList()
        mSessionManager = SessionManager(applicationContext!!)
        if (intent?.hasExtra(getString(R.string.intent_putextra_api_key))!!) {
            api_name = intent.getStringExtra(getString(R.string.intent_putextra_api_key)).toString()
            if (api_name.equals(getString(R.string.profile_driver))) {
                header.put("driver_id", mSessionManager.getDriverId())

                urltohit = RetrofitInstance.driverprofile

                commonapihit(header, api_name)
            }

            if (api_name.equals(getString(R.string.offer_copen1))) {
                header.put("driver_id", mSessionManager.getDriverId())

                urltohit = RetrofitInstance.offer
                Log.d("checking22", header.toString())
                commonapihit(header, api_name)
            }
            if (api_name.equals(getString(R.string.version))) {
//                header.put("driver_id", mSessionManager.getDriverId())

                var customer = intent.getStringExtra("customer")
                header.put("app_name", customer!!)
                urltohit = RetrofitInstance.appversion
                Log.d("checking22", header.toString())
                commonapihit(header, api_name)
            }
            if (api_name.equals(getString(R.string.offer_copen))) {
                header.put("driver_id", mSessionManager.getDriverId())

                urltohit = RetrofitInstance.offer
                Log.d("checking22", header.toString())
                commonapihit(header, api_name)
            }
            if (api_name.equals(getString(R.string.getapplanuage))) {
                var lag_type = mSessionManager.getseletedlagname()!!
                if (lag_type.equals("")) {
                    lag_type = "en"
                    lag_type="fr"
                }




                header.put("lang_code", lag_type)
                header.put("type", "customer")
                Log.d("checking22", header.toString())


                urltohit = RetrofitInstance.applanage

                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.payment_list))) {
                header.put("id", mSessionManager!!.getDriverId())
                header.put("type", "driver")
                header.put("lat", mSessionManager.getOnlineLatitiude())
                header.put("lon", mSessionManager.getOnlineLongitude())
                header.put("mode", "trip")

                // url to hit
                urltohit = RetrofitInstance.paymentlist
                Log.d("mcheckinglist", header.toString())

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.payment_list_wallet))) {
                header.put("id", mSessionManager!!.getDriverId())
                header.put("type", "driver")
                header.put("lat", mSessionManager.getOnlineLatitiude())
                header.put("lon", mSessionManager.getOnlineLongitude())
                header.put("mode", "wallet")

                // url to hit
                urltohit = RetrofitInstance.paymentlist
                Log.d("mcheckinglist", header.toString())

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.payment_list_wallet_customer))) {
                header.put("id", mSessionManager!!.getcustomer_id())
                header.put("type", "customer")
                header.put("lat", "13.0212805")
                header.put("lon", "80.2231037")
                header.put("mode", "prebooking")

                // url to hit
                urltohit = RetrofitInstance.paymentlist
                Log.d("mcheckinglist", header.toString())

                // common network call
                commonapihit(header, api_name)
            }
            if (api_name.equals(getString(R.string.payment_success))) {

                var rideid = intent.getStringExtra("rideid")
                var transactionId = intent.getStringExtra("transactionId")

                header.put("id", mSessionManager.getDriverId())
                header.put("type", "driver")
                header.put("pay_method", "kkiapay")
                header.put("ride_id", rideid.toString())
                header.put("pay_data", "")
                header.put("transaction_id", transactionId.toString())


                urltohit = RetrofitInstance.paymentsucceschecking

                commonapihit(header, api_name)
            }
            else if (api_name.equals(getString(R.string.payment_init))) {

                var amount = intent.getStringExtra("amount")

                header.put("id", mSessionManager.getDriverId())
                header.put("type", "driver")
                header.put("pay_method", "kkiapay")
                header.put("amount", amount!!)
//                header.put("phone_number", "70597201")


                urltohit = RetrofitInstance.paymentinit

                commonapihit(header, api_name)
            }
            else if (api_name.equals(getString(R.string.payment_init_tmoney))) {

                var amount = intent.getStringExtra("amount")
                var phonenumber = intent.getStringExtra("phonenumber")
                var pay_method = intent.getStringExtra("paymentype")
                var dial_code = intent.getStringExtra("dial_code")

                header.put("id", mSessionManager.getDriverId())
                header.put("type", "driver")
                header.put("pay_method", pay_method!!)
                header.put("amount", amount!!)
                header.put("phone_number", phonenumber!!)
                header.put("dial_code", dial_code!!)

                urltohit = RetrofitInstance.paymentinit
                Log.d("checking11", header.toString())
                commonapihit(header, api_name)
            }



            else if (api_name.equals(getString(R.string.payment_init_tmoney_customer))) {

                var amount = intent.getStringExtra("amount")
                var phonenumber = intent.getStringExtra("phonenumber")
                var pay_method = intent.getStringExtra("paymentype")
                var dial_code = intent.getStringExtra("dial_code")

                header.put("id", mSessionManager.getcustomer_id())
                header.put("type", "customer")
                header.put("pay_method", pay_method!!)
                header.put("amount", amount!!)
                header.put("phone_number", phonenumber!!)
                header.put("dial_code", dial_code!!)
                urltohit = RetrofitInstance.paymenprebookingtinit
                Log.d("checking11", header.toString())
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.recharge_update))) {

                var uuid = intent.getStringExtra("uuid")
                var mtransactionId = intent.getStringExtra("mtransactionId")
                var kkiapaystatus = intent.getStringExtra("kkiapaystatus")

                header.put("uuid", uuid!!)
                header.put("status", "success")
                header.put("pay_method", "kkiapay")
                header.put("transaction_id", mtransactionId!!)

                urltohit = RetrofitInstance.rechargeupdate

                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.login_with_password))) {

                getfcmtoken()

                // header value of checking user
                header.put("dial_code", intent?.getStringExtra("code")!!)

                header.put("mobile_number", intent?.getStringExtra("mobileno")!!)

                header.put("pword", intent?.getStringExtra("pword")!!)

                Log.d("checkingsheaderss", header.toString())


                // url to hit
                urltohit = RetrofitInstance.checkuserexist

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.change_password))) {
                header.put("old_password", intent?.getStringExtra("oldpasswords")!!)
                header.put("new_password", intent?.getStringExtra("newpasswords")!!)

                header.put("driver_id", mSessionManager!!.getDriverId())


                Log.d("desgfsgtdyg", header.toString())

                // url to hit
                urltohit = RetrofitInstance.changepasswords

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.cusstermo_referra))) {

                var name = intent.getStringExtra("name")
                var dial_code = intent.getStringExtra("dial_code")
                var phone_number = intent.getStringExtra("phone_number")
                var email = intent.getStringExtra("email")

                header.put("name", name!!)
                header.put("dial_code", dial_code!!)
                header.put("phone_number", phone_number!!)
                header.put("driver_id", mSessionManager.getDriverId())
                header.put("email", email!!)

                urltohit = RetrofitInstance.referrals
                Log.d("referralsheade", header.toString())

                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.vehicle_list))) {


                header.put("driver_id", mSessionManager.getDriverId())


                urltohit = RetrofitInstance.vehiclelist
                Log.d("referralsheade", header.toString())

                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.recharge_check))) {

                var uuid = intent.getStringExtra("uuid")

                header.put("uuid", uuid!!)

                urltohit = RetrofitInstance.rechargecheck

                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.recharge_check_prebooking))) {

                var uuid = intent.getStringExtra("uuid")

                header.put("uuid", uuid!!)

                urltohit = RetrofitInstance.rechargecheckprebooking

                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.offerpaymentstatuscheck))) {

                var uuitransaction_id = intent.getStringExtra("transaction_id")

                header.put("transaction_id", uuitransaction_id!!)


                Log.d("checkingsstmoney", header.toString())
                urltohit = RetrofitInstance.offerpaymentstatuscheck

                commonapihit(header, api_name)
            }
            else if (api_name.equals(getString(R.string.voucherpaymentstatuscheck))) {

                var transaction_id = intent.getStringExtra("transaction_id")

                header.put("transaction_id", transaction_id!!)


                Log.d("checkingsstmoney", header.toString())
                urltohit = RetrofitInstance.voucherpaymentstatuscheck

                commonapihit(header, api_name)
            }



            if (api_name.equals(getString(R.string.Paymenttripinit))) {

                var rideid = intent.getStringExtra("rideid")
                var phone_number = intent.getStringExtra("phone_number")

                header.put("id", mSessionManager.getDriverId())
                header.put("type", "driver")
                header.put("pay_method", "tmoney")
                header.put("ride_id", rideid.toString())
                header.put("phone_number", phone_number.toString())


                urltohit = RetrofitInstance.paymenttripinit
                Log.d("checkingsstmoney", header.toString())

                commonapihit(header, api_name)
            }

            if (api_name.equals(getString(R.string.offerpaymentinit))) {

                var dial_code = intent.getStringExtra("dial_code")
                var offerid = intent.getStringExtra("offer_id")
                var phone_number = intent.getStringExtra("phonenumber")
                var paymentype = intent.getStringExtra("paymentype")

                header.put("driver_id", mSessionManager.getDriverId())
                if (paymentype != null)
                    header.put("pay_mode", paymentype.toString())
                header.put("offer_id", offerid.toString())
                header.put("dial_code", dial_code.toString())
                header.put("phone_number", phone_number.toString())


                urltohit = RetrofitInstance.offerpaymentinit
                Log.d("checkingsstmoney", header.toString())

                commonapihit(header, api_name)
            }

            else if (api_name.equals(getString(R.string.voucher_payment_init))) {


                var phonenumber = intent.getStringExtra("phonenumber")
                var paymentype = intent.getStringExtra("paymentype")
                var dial_code = intent.getStringExtra("dial_code")
                var quantity = intent.getStringExtra("quantity")

                header.put("id", mSessionManager.getDriverId())
                if (paymentype != null)
                header.put("type", "driver")
                header.put("pay_method", paymentype.toString())
                header.put("phone_number", phonenumber.toString())
                header.put("dial_code", dial_code.toString())
                header.put("quantity", quantity.toString())

                urltohit = RetrofitInstance.voucherpaymentinit
                Log.d("checking11", header.toString())
                commonapihit(header, api_name)
            }


            if (api_name.equals(getString(R.string.tmoneycheckride))) {

                var rideid = intent.getStringExtra("rideid")

                header.put("id", mSessionManager.getDriverId())
                header.put("ride_id", rideid.toString())
                header.put("type", "driver")


                urltohit = RetrofitInstance.paymentmoneycheckride
                Log.d("checkingsstmoney", header.toString())

                commonapihit(header, api_name)
            }
            if (api_name.equals(getString(R.string.user_battery))) {
                var qrcodetype = intent.getStringExtra("qrcodetype")
                var mbarcode = intent.getStringExtra("mbarcode")

                header.put("qrcode", mbarcode!!)
                header.put("driver_id", mSessionManager.getDriverId())
                header.put("battery_type", qrcodetype!!)
                Log.d("battert_heasder_old", header.toString())



                urltohit = RetrofitInstance.swipe_battery

                commonapihit(header, api_name)
            }


            if (api_name.equals(getString(R.string.user_battery_new))) {
                var qrcodetype = intent.getStringExtra("qrcodetype")
                var mbarcode = intent.getStringExtra("mbarcode")
                header.put("qrcode", mbarcode!!)
                header.put("driver_id", mSessionManager.getDriverId())
                header.put("battery_type", qrcodetype!!)
                Log.d("battert_heasder_new", header.toString())



                urltohit = RetrofitInstance.swipe_battery

                commonapihit(header, api_name)
            }

            if (api_name.equals(getString(R.string.battery_submit))) {
                header.put("driver_id", mSessionManager.getDriverId())
                header.put("old_battery", mSessionManager.getqrcode_old())
                header.put("new_battery", mSessionManager.getqrcode_new())
                Log.d("battert_sumbit_new", header.toString())



                urltohit = RetrofitInstance.submit_battery

                commonapihit(header, api_name)
            }



            if (api_name.equals(getString(R.string.getappinfohit))) {
                getfcmtoken()

                // header value of app more info
                header["user_type"] = "driver"
                header["driver_id"] = mSessionManager!!.getDriverId()
                Log.d("fghgf","fhdsfhds")

                // url to hit
                urltohit = RetrofitInstance.appmoreinfo
                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.api_location_fare))) {
                val lat = intent.getStringExtra("pickup_lat")
                val lng = intent.getStringExtra("pickup_lng")
                locationFareApi(lat!!, lng!!)
            } else if (api_name.equals(getString(R.string.api_user_get_otp))) {

                val driver_id = intent.getStringExtra("driver_id")
                val dial_code = intent.getStringExtra("dial_code")
                val mobile_number = intent.getStringExtra("mobile_number")
                val lat = intent.getStringExtra("lat")
                val countryname = intent.getStringExtra("countryname")
                val lng = intent.getStringExtra("lon")
                getOtpAPI(driver_id!!, dial_code!!, mobile_number!!, lat!!, lng!!, countryname!!)
            } else if (api_name.equals(getString(R.string.api_otp_ride_begin))) {

                val driver_id = intent.getStringExtra("driver_id")
                val dial_code = intent.getStringExtra("dial_code")
                val mobile_number = intent.getStringExtra("mobile_number")
                val pickup = intent.getStringExtra("pickup")
                val pickup_lat = intent.getStringExtra("pickup_lat")
                val pickup_lon = intent.getStringExtra("pickup_lon")
                val drop = intent.getStringExtra("drop")
                val drop_lat = intent.getStringExtra("drop_lat")
                val drop_lon = intent.getStringExtra("drop_lon")
                val est_amount = intent.getStringExtra("est_amount")
                val est_distance = intent.getStringExtra("est_distance")
                val est_duration = intent.getStringExtra("est_duration")
                getOtpRideBeginAPI(
                    driver_id!!,
                    dial_code!!,
                    mobile_number!!,
                    pickup!!,
                    pickup_lat!!,
                    pickup_lon!!,
                    drop!!,
                    drop_lat!!,
                    drop_lon!!,
                    est_amount!!,
                    est_distance!!,
                    est_duration!!
                )
            } else if (api_name.equals(getString(R.string.registerpageoneapi))) {
                // mobile login check api
                getfcmtoken()

                mobmobileno = intent?.getStringExtra("mobileno")!!
                mobcode = intent?.getStringExtra("code")!!
                Country_name = intent?.getStringExtra("Country_name")!!


                // header value of phoneverify
                header.put("mobile_number", mobmobileno)
                header.put("dial_code", mobcode)
                header.put("country_code", Country_name)


                // url to hit
                urltohit = RetrofitInstance.verifymobile
                Log.d("chej54cj", header.toString())

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.tripservice))) {

                header.put("driver_id", mSessionManager!!.getDriverId())
                header.put("booking_timestamp", "")
                header.put("record", "")

                // url to hit
                urltohit = RetrofitInstance.triplist

                // common network call
                commonapihit(header, api_name)

            } else if (api_name.equals(getString(R.string.startfirstorlast))) {
                val bookingdateandtime = intent.getStringExtra("bookingdateandtime")
                header.put("driver_id", mSessionManager!!.getDriverId())
                header.put("booking_timestamp", bookingdateandtime!!)
                header.put("record", "first")

                // url to hit
                urltohit = RetrofitInstance.triplist

                Log.d("chehejjf", header.toString())

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.startlast))) {
                val bookingdateandtime = intent.getStringExtra("bookingdateandtime")
                header.put("driver_id", mSessionManager!!.getDriverId())
                header.put("booking_timestamp", bookingdateandtime!!)
                header.put("record", "last")

                // url to hit
                urltohit = RetrofitInstance.triplist
                Log.d("cghgfsdsf", header.toString())

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.updatemobilenumber))) {

                mobmobileno = intent?.getStringExtra("mobileno")!!
                mobcode = intent?.getStringExtra("code")!!

                // header value of phoneverify
                header.put("phone_number", mobmobileno)
                header.put("country_code", mobcode)
                header.put("driver_id", mSessionManager!!.getDriverId())
                header.put("otp", "")

                // url to hit
                urltohit = RetrofitInstance.updatemobileno

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.updateemailidalone))) {

                var email = intent?.getStringExtra("email")


                // header value of phoneverify
                header.put("email", email!!)
                header.put("driver_id", mSessionManager!!.getDriverId())
                header.put("otp", "")

                // url to hit
                urltohit = RetrofitInstance.updateemailid

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.updateemailidalonewithotp))) {

                var email = intent?.getStringExtra("email")
                var otp = intent?.getStringExtra("otp")


                // header value of phoneverify
                header.put("email", email!!)
                header.put("driver_id", mSessionManager!!.getDriverId())
                header.put("otp", otp!!)

                // url to hit
                urltohit = RetrofitInstance.updateemailid

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.updatemobilewitotp))) {

                mobmobileno = intent?.getStringExtra("mobile_number")!!
                mobcode = intent?.getStringExtra("dail_code")!!
                var otp = intent?.getStringExtra("otp")

                // header value of phoneverify
                header.put("phone_number", mobmobileno)
                header.put("country_code", mobcode)
                header.put("driver_id", mSessionManager!!.getDriverId())
                header.put("otp", otp!!)

                // url to hit
                urltohit = RetrofitInstance.updatemobileno

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.cashreceived))) {
                var ride_id = intent?.getStringExtra("ride_id")
                // header value of phoneverify
                header.put("ride_id", ride_id!!)
                header.put("driver_id", mSessionManager!!.getDriverId())
                header.put("amount", intent?.getStringExtra("amount")!!)

                // url to hit
                urltohit = RetrofitInstance.receivecash

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.defaultvehicleupdte))) {
                var vehicle_id = intent?.getStringExtra("vehicleid")
                // header value of phoneverify
                header.put("vehicle_id", vehicle_id!!)
                header.put("driver_id", mSessionManager!!.getDriverId())

                // url to hit
                urltohit = RetrofitInstance.defaultvechicleupdate

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.getvehicleinfoforedit))) {
                var vehicle_id = intent?.getStringExtra("vehicleid")
                // header value of phoneverify
                header.put("vehicle_id", vehicle_id!!)
                header.put("driver_id", mSessionManager!!.getDriverId())

                // url to hit
                urltohit = RetrofitInstance.editvehicleinfo

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.getdriverdocument))) {
                header.put("driver_id", mSessionManager!!.getDriverId())

                // url to hit
                urltohit = RetrofitInstance.driverdocinfo

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.getvehiclelist))) {

                header.put("driver_id", mSessionManager!!.getDriverId())

                // url to hit
                urltohit = RetrofitInstance.getvehiclelistt

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.retrievetripdetail))) {
                var ride_id = intent?.getStringExtra("ride_id")
                // header value of tripdetails
                header.put("driver_id", mSessionManager!!.getDriverId())
                header.put("trip_type", "all")

                // url to hit
                urltohit = RetrofitInstance.tripdetailss

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.retrievetripdetailfully))) {
                var ride_id = intent?.getStringExtra("rideid")
                // header value of tripdetails full
                header.put("driver_id", mSessionManager!!.getDriverId())
                header.put("ride_id", ride_id!!)

                // url to hit
                urltohit = RetrofitInstance.tripdetailssfull

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.resendotp))) {
                // otp resend api
                otpmobileno = intent?.getStringExtra("mobileno")!!
                otpcode = intent?.getStringExtra("code")!!

                // header value of otp
                header.put("mobile_number", otpmobileno)
                header.put("dial_code", "+" + otpcode)
                header.put("country_code", "IN")

                // url to hit
                urltohit = RetrofitInstance.verifymobile

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.getservicelocationresponse)) || api_name.equals(
                    getString(R.string.getmakemodelyear)
                )
            ) {
                // url to hit
                urltohit = RetrofitInstance.servicelocationapi

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.cancelreason))) {

                // header value of phoneverify
                header.put("driver_id", mSessionManager!!.getDriverId())
                header.put("ride_id", intent?.getStringExtra("rideid")!!)
                header.put("user_type", "driver")

                // url to hit
                urltohit = RetrofitInstance.cancelreason

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.chatmessageurl))) {


                val job = JSONObject()
                job.put("action", "dectar_chat")
                job.put("type", "OTHER")
                job.put("sender_ID", intent?.getStringExtra("useridtosend"))
                job.put("ride_id", intent?.getStringExtra("rideid"))
                job.put("desc", intent?.getStringExtra("message"))
                job.put("driver_image", "")
                job.put("driver_name", "")
                job.put("voice_timing", intent?.getStringExtra("timestamp"))
                job.put("time_stamp", intent?.getStringExtra("timestamp"))


                // header value of chatmessage
                header.put("id", mSessionManager!!.getDriverId())
                header.put("user_type", "driver")
                header.put("ride_id", intent?.getStringExtra("rideid")!!)
                header.put("message_type", "text")
                header.put("message_content", job.toString())
                header.put("message", job.toString())
                header.put("message", intent?.getStringExtra("message")!!)
                header.put("timestamp", intent?.getStringExtra("timestamp")!!)
                header.put("time_stamp", intent?.getStringExtra("timestamp")!!)


                // url to hit
                urltohit = RetrofitInstance.chaturl

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.cancelthisride))) {

                // header value of phoneverify
//                header.put("driver_id", mSessionManager!!.getDriverId())
//                header.put("ride_id", intent?.getStringExtra("rideid"))
//                header.put("reason_id", intent?.getStringExtra("cancelid"))
//                header.put("user_type", "driver")

                header.put("driver_id", mSessionManager!!.getDriverId())
                header.put("ride_id", intent?.getStringExtra("rideid")!!)
                header.put("reason", intent?.getStringExtra("cancelid")!!)

//                header.put("user_type", "driver")


                // url to hit
                urltohit = RetrofitInstance.cancelthisride

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.finalcalltohomepage))) {

                // header value of phoneverify
                header.put("driver_id", mSessionManager!!.getDriverId())
                header.put("vehicle_id", mSessionManager!!.gettempvehicleid())
                header.put("type", intent?.getStringExtra("type")!!)


                // url to hit
                urltohit = RetrofitInstance.fianalmovepage

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.emailcheckk))) {

                val emailid = intent?.getStringExtra("emailid")

                // header value of verifyemail
                header.put("email", emailid!!)

                // url to hit
                urltohit = RetrofitInstance.verifyemail

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.checkinuserexist))) {

                getfcmtoken()

                // header value of checking user
                if (intent?.getStringExtra("dail_code")!!.startsWith("+"))
                    header.put("dial_code", intent?.getStringExtra("dail_code")!!)
                else
                    header.put("dial_code", "+" + intent?.getStringExtra("dail_code"))

                header.put("mobile_number", intent?.getStringExtra("mobile_number")!!)
                header.put("gcm_id", mSessionManager!!.getfcmkey())
                header.put("latitude", "0.0")
                header.put("longitude", "0.0")

                // url to hit
                urltohit = RetrofitInstance.checkuserexist

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.locationfetchapi))) {
                // url to hit

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.steptwoapi))) {
                // url to hit document
                header.put("type", mSessionManager.getdocumentstage())
                Log.d("dscfsdfs", mSessionManager.getdocumentstage())
                urltohit = RetrofitInstance.getdocument
                // common network call
//                steptwodocument()
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.verifyvehiclenumber))) {
                // header value of finaldocumentsubmit
                val params = intent.getSerializableExtra("params") as HashMap<String, String>
                header = params
                // url to hit final reg call
                urltohit = RetrofitInstance.getvehicleid
                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.basicdriverprofileupdate))) {
                // header value of finaldocumentsubmit
                val params = intent.getSerializableExtra("params") as HashMap<String, String>
                header = params

                // url to hit final reg call
                urltohit = RetrofitInstance.editprofile

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.finaldocument))) {
                // header value of finaldocumentsubmit
                val params = intent.getSerializableExtra("params") as HashMap<String, String>
                header = params
                header.put("driver_id", mSessionManager!!.getDriverId())
                header.put("vehicle_id", mSessionManager!!.gettempvehicleid())

                // url to hit final reg call
                urltohit = RetrofitInstance.basicregister

                // common network call
                commonapihit(header, api_name)
                Log.d("sduhyw434", header.toString()!!)

            } else if (api_name.equals(getString(R.string.finaldocumentupload))) {
                // header value of finaldocumentsubmit
                val params = intent.getSerializableExtra("params") as HashMap<String, String>
                header = params
                header.put("driver_id", mSessionManager!!.getDriverId())
                var categroy = intent?.getStringExtra("categroy")
                if (categroy.equals("Vehicle") || categroy.equals("vehicle")) {
                    header.put("vehicle_id", mSessionManager.getvehicleid())
                } else {
                    header.put("vehicle_id", "")
                }


                // url to hit final reg call
                urltohit = RetrofitInstance.documentsubmit

                // common network call
                commonapihit(header, api_name)
                Log.d("drfetretvgy", header.toString())
            } else if (api_name.equals(getString(R.string.driveravailable))) {
                var availablestatus = intent?.getStringExtra("availablestatus")

                header.put("driver_id", mSessionManager!!.getDriverId())
                header.put("work_status", availablestatus!!)

                // url to hit final reg call
                urltohit = RetrofitInstance.onlineofflinecall

                // common network call
                commonapihit(header, api_name)
//                getdriverstatus(availablestatus!!)
            } else if (api_name.equals(getString(R.string.dashboarapicall))) {

                header.put("driver_id", mSessionManager!!.getDriverId())
                header.put("driver_lat", mSessionManager.getOnlineLatitiude())
                header.put("driver_lon", mSessionManager.getOnlineLongitude())

                Log.d("desgfsgtdyg", header.toString())

                // url to hit
                urltohit = RetrofitInstance.dashboardcallapi

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.boundary))) {

                header.put("driver_id", mSessionManager!!.getDriverId())

                Log.d("fgfhhgjgj", header.toString())

                // url to hit
                urltohit = RetrofitInstance.getboundary

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.geofence_update))) {
                var type_driver = intent?.getStringExtra("type_driver")
                Log.d("mtype_driverss", type_driver.toString())
                if (type_driver.equals("true")) {
                    type_driver = "in"
                } else {
                    type_driver = "out"

                }

                header.put("driver_id", mSessionManager!!.getDriverId())
                header.put("type", type_driver)
                header.put("lat", mSessionManager!!.getOnlineLatitiude())
                header.put("lon", mSessionManager!!.getOnlineLongitude())

                Log.d("boundrayupadate", header.toString())

                // url to hit
                urltohit = RetrofitInstance.getboundaryupdate

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.routeapicall))) {

                var ctlat: Double = mSessionManager.getOnlineLatitiude().toDouble()
                var ctlong: Double = mSessionManager.getOnlineLongitude().toDouble()

                var droplat: Double = intent?.getStringExtra("droplat")!!.toDouble()
                var droplong: Double = intent?.getStringExtra("droplong")!!.toDouble()

                val mycuurentloc = LatLng(ctlat, ctlong)
                val deslat = LatLng(droplat, droplong)
                val routeUrl = getRouteApiUrl(mycuurentloc, deslat)
                fetchRouteUrl(routeUrl)
            } else if (api_name.equals(getString(R.string.reroutingapicall))) {

                var ctlat: Double = mSessionManager.getOnlineLatitiude().toDouble()
                var ctlong: Double = mSessionManager.getOnlineLongitude().toDouble()

                var droplat: Double = intent?.getStringExtra("droplat")!!.toDouble()
                var droplong: Double = intent?.getStringExtra("droplong")!!.toDouble()

                val mycuurentloc = LatLng(ctlat, ctlong)
                val deslat = LatLng(droplat, droplong)
                val routeUrl = getRouteApiUrl(mycuurentloc, deslat)
                fetchreroutingRouteUrl(routeUrl)
            }

            /*  else if (api_name.equals(getString(R.string.getmakemodelyear)))
              {
                  // url to hit
                  urltohit = RetrofitInstance.dummycategory

                 *//* // header value of dummycat
                header.put("Authkey", "1534599312")*//*

                // common network call
                dummycommonapihit(header)
            }*/
            else if (api_name.equals(getString(R.string.getonlymake))) {
                // url to hit
                urltohit = RetrofitInstance.getcatmakemodel
                // common network call
                dummycommonapihit(header)
            } else if (api_name.equals(getString(R.string.user_referra))) {

                faqFareApi()
            } else if (api_name.equals(getString(R.string.get_earning))) {
                val date_value = intent.getStringExtra("date_value")

                earningsapi(date_value!!)

            } else if (api_name.equals(getString(R.string.wallet_balance))) {
                header.put("id", mSessionManager!!.getDriverId())
                header.put("user_type", "driver")

                // url to hit
                urltohit = RetrofitInstance.walletbalance

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.prebooking_details))) {
                header.put("id", mSessionManager!!.getcustomer_id())

                // url to hit
                urltohit = RetrofitInstance.prebookingdetails

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.payment_key))) {
                header.put("id", mSessionManager!!.getDriverId())
                header.put("type", "driver")
                header.put("lat", mSessionManager.getOnlineLatitiude())
                header.put("lon", mSessionManager.getOnlineLongitude())

                // url to hit
                urltohit = RetrofitInstance.payment_key

                // common network call
                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.reports_submit))) {
                var message = intent?.getStringExtra("message")
                var subject = intent?.getStringExtra("subject")
                header.put("id", mSessionManager!!.getDriverId())
                header.put("subject", subject!!)
                header.put("user_type", "driver")

                header.put("message", message!!)

                urltohit = RetrofitInstance.reportssubmit
                Log.d("d22fsgdv", header.toString())

                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.prebooking_cancel))) {
                var message = intent?.getStringExtra("message")
                header.put("id", mSessionManager!!.getcustomer_id())

                header.put("message", message!!)

                urltohit = RetrofitInstance.prebookingcancel
                Log.d("d22fsgdv", header.toString())

                commonapihit(header, api_name)
            } else if (api_name.equals(getString(R.string.prebooking_model))) {
                var modelname = intent?.getStringExtra("modelname")
                header.put("id", mSessionManager!!.getcustomer_id())
                header.put("model", modelname!!)
                header.put("type", "customer")


                urltohit = RetrofitInstance.prebookingmodel
                Log.d("d22fsgdv", header.toString())

                commonapihit(header, api_name)
            }
            else if (api_name.equals(getString(R.string.swaps_tation))) {
                header.put("driver_id", mSessionManager!!.getDriverId())
//                header.put("image", mSessionManager!!.getimage())
                header.put("lat", mSessionManager.getOnlineLatitiude())
                header.put("lon", mSessionManager.getOnlineLongitude())


                urltohit = RetrofitInstance.swapstation
                Log.d("d22fsgdv", header.toString())

                commonapihit(header, api_name)
            }
            else if (api_name.equals(getString(R.string.service_station))) {
                header.put("driver_id", mSessionManager!!.getDriverId())
//                header.put("image", mSessionManager!!.getimage())
                header.put("lat", mSessionManager.getOnlineLatitiude())
                header.put("lon", mSessionManager.getOnlineLongitude())


                urltohit = RetrofitInstance.servicestation
                Log.d("d22fsgdv", header.toString())

                commonapihit(header, api_name)
            }
            else if (api_name.equals(getString(R.string.wallet_Transaction))) {
                header.put("wallet_id", mSessionManager!!.getwallet_id())
                header.put("user_type", "driver")

                // url to hit
                urltohit = RetrofitInstance.wallettransactions

                // common network call
                commonapihit(header, api_name)
            }

        } else if (api_name.equals(getString(R.string.profilesaveapi))) {
            // otp resend api
            var dial_code = intent?.getStringExtra("dial_code")
            var mobile_number = intent?.getStringExtra("mobile_number")
            var email = intent?.getStringExtra("email")
            var first_name = intent?.getStringExtra("first_name")
            var last_name = intent?.getStringExtra("last_name")
            var image = intent?.getStringExtra("image")
            var gender = intent?.getStringExtra("gender")
            var dob = intent?.getStringExtra("dob")
            profilesend(
                dial_code!!,
                mobile_number!!,
                email!!,
                first_name!!,
                last_name!!,
                image!!,
                gender!!,
                dob!!
            )
        } else if (api_name.equals(getString(R.string.categoryfetchapi))) {
            getfcmtoken()
            categoryfetchapi()
        } else if (api_name.equals(getString(R.string.steponeapi))) {
            steponefetchapi()
        } else if (api_name.equals(getString(R.string.imageupload))) {
            var base64string = intent?.getStringExtra("base64string")!!
            imageuploadapi(base64string)
        } else if (api_name.equals(getString(R.string.starverficationapi))) {
            startverficationapi()
        } else if (api_name.equals(getString(R.string.reuploadapi))) {
            var id = intent?.getStringExtra("id")
            var image = intent?.getStringExtra("image")
            var name = intent?.getStringExtra("name")
            var type = intent?.getStringExtra("type")
            var choosedate = intent?.getStringExtra("choosedate")
            reuploadimageapi(id!!, image!!, name!!, type!!, choosedate!!)
        } else if (api_name.equals(getString(R.string.acknowlcall))) {
            var ack = intent?.getStringExtra("ack")
            var ride_id = intent?.getStringExtra("ride_id")
            acknwoldegementcallhit(ride_id!!, ack!!)
        }

    }


    private fun locationFareApi(lat: String, lng: String) {
        var params = HashMap<String, String>()
        params["lat"] = mSessionManager!!.getOnlineLatitiude()
        params["lon"] = mSessionManager!!.getOnlineLongitude()
//        params["user_id"] = mSessionManager!!.getDriverId()

        Log.d("xxdafjsfj", params.toString())
        Log.d("sdfghdvgfgd", mSessionManager!!.getApiHeader().toString())


        apiCall = RetrofitInstance.getInstance()
            .getlocationFare(mSessionManager!!.getApiHeaderwithoutcontenttype(), params)
        apiCall?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            var responseStr = response.body()?.string()
                            responsetosend = responseStr.toString()
                            Log.d("gdfgdg", responsetosend)
                            EventBus.getDefault().post(
                                IntentServiceResult(
                                    Activity.RESULT_OK,
                                    responsetosend!!,
                                    getString(R.string.api_location_fare)
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    EventBus.getDefault().post(
                        IntentServiceResult(
                            Activity.RESULT_OK,
                            responsetosend!!,
                            getString(R.string.api_location_fare)
                        )
                    )
                    Log.d("daf34454jsfj", responsetosend)

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                EventBus.getDefault().post(
                    IntentServiceResult(
                        Activity.RESULT_OK,
                        responsetosend!!,
                        getString(R.string.api_location_fare)
                    )
                )
                Log.d("chusryge3424", responsetosend)


            }
        })
    }


    private fun getdriverstatus(availablestatus: String) {
        var params = HashMap<String, String>()

        params["driver_id"] = mSessionManager!!.getDriverId()
        params["work_status"] = availablestatus


        Log.d("dfsdfgfdgsdgf", params.toString())
        Log.d("fsdtgdyt", mSessionManager!!.getApiHeader().toString())


        apiCall =
            RetrofitInstance.getInstance().getUserOtp(mSessionManager!!.getApiHeader(), params)
        apiCall?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    println("otp.dgdg.." + response.code())

                    Log.d("vckjxjvjg", response.code().toString())
//                        EventBus.getDefault().post(IntentServiceResult(Activity.RESULT_OK, response.code().toString()!!, getString(R.string.api_user_get_otp)))


                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d("cvcddzbxbcbvv", e.toString())

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                println("otp... Failed")
                Log.d("dggfgfgfgf", responsetosend)


            }
        })
    }


    private fun getOtpAPI(
        driver_id: String,
        dial_code: String,
        mobile_number: String,
        lat: String,
        lng: String,
        countryname: String
    ) {
        var params = HashMap<String, String>()
        params["lat"] = lat
        params["lon"] = lng
        params["dial_code"] = dial_code
        params["mobile_number"] = mobile_number
        params["driver_id"] = mSessionManager!!.getDriverId()
        params["country_code"] = countryname

        Log.d("dfsdfgfdgsdgf", params.toString())
        Log.d("fsdtgdyt", mSessionManager!!.getApiHeader().toString())


        apiCall =
            RetrofitInstance.getInstance().getUserOtp(mSessionManager!!.getApiHeader(), params)
        apiCall?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    println("otp..." + response.code())
                    if (response.code() != 406) {

                        if (response.isSuccessful) {
                            if (response.body() != null) {
                                var responseStr = response.body()?.string()
                                responsetosend = responseStr.toString()
                                Log.d("chdhhfd", responsetosend)
                                EventBus.getDefault().post(
                                    IntentServiceResult(
                                        Activity.RESULT_OK,
                                        responsetosend!!,
                                        getString(R.string.api_user_get_otp)
                                    )
                                )
                            }
                        }
                    } else {
                        Log.d("dxhjshwesdfc", response.code().toString())
                        EventBus.getDefault().post(
                            IntentServiceResult(
                                Activity.RESULT_OK,
                                response.code().toString()!!,
                                getString(R.string.api_user_get_otp)
                            )
                        )


                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d("cvcddzbxbcbvv", e.toString())

                    EventBus.getDefault().post(
                        IntentServiceResult(
                            Activity.RESULT_OK,
                            responsetosend!!,
                            getString(R.string.api_user_get_otp)
                        )
                    )
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                println("otp... Failed")
                Log.d("dggfgfgfgf", responsetosend)

                EventBus.getDefault().post(
                    IntentServiceResult(
                        Activity.RESULT_OK,
                        responsetosend!!,
                        getString(R.string.api_user_get_otp)
                    )
                )

            }
        })
    }


    private fun getOtpRideBeginAPI(
        driver_id: String,
        dial_code: String,
        mobile_number: String,
        pickUp: String,
        pickupLat: String,
        pickupLng: String,
        drop: String,
        dropLat: String,
        dropLng: String,
        est_amount: String,
        est_distance: String,
        est_duration: String
    ) {
        var params = HashMap<String, String>()
        params["pickup_lat"] = pickupLat
        params["pickup_lon"] = pickupLng
        params["pickup"] = pickUp
        params["dial_code"] = dial_code
        params["mobile_number"] = mobile_number
        params["driver_id"] = mSessionManager!!.getDriverId()
        Log.d("dstgjdtrsdr4trertyg", (mSessionManager!!.getApiHeader().toString()))
        Log.d("chjrtjv", params.toString())


        apiCall =
            RetrofitInstance.getInstance().getOtpRideBegun(mSessionManager!!.getApiHeader(), params)
        apiCall?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    Log.d("check.........", "wow")
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            var responseStr = response.body()?.string()
                            responsetosend = responseStr.toString()
                            EventBus.getDefault().post(
                                IntentServiceResult(
                                    Activity.RESULT_OK,
                                    responsetosend!!,
                                    getString(R.string.api_otp_ride_begin)
                                )
                            )
                            Log.d("checkingotpdd", responsetosend.toString())

                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    EventBus.getDefault().post(
                        IntentServiceResult(
                            Activity.RESULT_OK,
                            responsetosend!!,
                            getString(R.string.api_otp_ride_begin)
                        )
                    )
                    Log.d("checkingoddedtp", responsetosend.toString())
                    Log.d("mExceptions", e.toString())


                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("check.........", "failed")

                EventBus.getDefault().post(
                    IntentServiceResult(
                        Activity.RESULT_OK,
                        responsetosend!!,
                        getString(R.string.api_otp_ride_begin)
                    )
                )
                Log.d("checkdsdingotp", responsetosend.toString())


            }
        })
    }


    private fun profilesend(
        code: String,
        mobileno: String,
        email: String,
        first_name: String,
        last_name: String,
        image: String,
        gender: String,
        dob: String
    ) {
        var params = HashMap<String, String>()
        params["mobile_number"] = mobileno
        params["dial_code"] = "+" + code
        params["email"] = email
        params["first_name"] = first_name
        params["last_name"] = last_name
        params["image"] = "data:image/png;base64," + image
        params["gender"] = gender
        params["dob"] = dob
        params["token"] = mSessionManager.getfcmkey()



        apiCall = RetrofitInstance.getInstance()
            .sendprofiledetails(mSessionManager!!.getApiHeader(), params)
        apiCall?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            var responseStr = response.body()?.string()
                            responsetosend = responseStr.toString()

                            EventBus.getDefault().post(
                                IntentServiceResult(
                                    Activity.RESULT_OK,
                                    responsetosend!!,
                                    getString(R.string.profilesaveapi)
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    EventBus.getDefault().post(
                        IntentServiceResult(
                            Activity.RESULT_OK,
                            responsetosend!!,
                            getString(R.string.profilesaveapi)
                        )
                    )
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                EventBus.getDefault().post(
                    IntentServiceResult(
                        Activity.RESULT_OK,
                        responsetosend!!,
                        getString(R.string.profilesaveapi)
                    )
                )
            }
        })
    }


    // location api
    private fun locationfetchapi() {
        var params = HashMap<String, String>()
        apiCall = RetrofitInstance.getInstance()
            .locationfetchapi(mSessionManager!!.getApiHeader(), params)
        apiCall?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            var responseStr = response.body()?.string()
                            responsetosend = responseStr.toString()
                            EventBus.getDefault().post(
                                IntentServiceResult(
                                    Activity.RESULT_OK,
                                    responsetosend!!,
                                    getString(R.string.locationfetchapi)
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    EventBus.getDefault().post(
                        IntentServiceResult(
                            Activity.RESULT_OK,
                            responsetosend!!,
                            getString(R.string.locationfetchapi)
                        )
                    )
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                EventBus.getDefault().post(
                    IntentServiceResult(
                        Activity.RESULT_OK,
                        responsetosend!!,
                        getString(R.string.locationfetchapi)
                    )
                )
            }
        })
    }


    // categoryfetchapi
    private fun categoryfetchapi() {
        var params = HashMap<String, String>()
        apiCall = RetrofitInstance.getInstance()
            .categoryfetchapi(mSessionManager!!.getApiHeader(), params)
        apiCall?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            var responseStr = response.body()?.string()
                            responsetosend = responseStr.toString()
                            val response_json_object = JSONObject(responsetosend)
                            try {
                                val status = response_json_object.getString("status")
                                if (status.equals("1")) {
                                    mSessionManager = SessionManager(applicationContext!!)
                                    mSessionManager!!.setCategoryDetails(responsetosend)
                                }
                            } catch (e: Exception) {
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }
        })
    }


    // getappinfo api
    private fun getappinfodetails() {
        var params = HashMap<String, String>()

        params["user_type"] = "driver"
        params["driver_id"] = mSessionManager!!.getDriverId()
        apiCall = RetrofitInstance.getInstance()
            .getappinfodetails(mSessionManager!!.getApiHeader(), params)
        apiCall?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
        })
    }

    // driverdashboard api
    private fun driverdashboardapi() {
        var params = HashMap<String, String>()
        params["driver_id"] = mSessionManager!!.getDriverId()
        apiCall = RetrofitInstance.getInstance()
            .driverdashboardapi(mSessionManager!!.getApiHeader(), params)
        apiCall?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            var responseStr = response.body()?.string()
                            responsetosend = responseStr.toString()
                            EventBus.getDefault().post(
                                IntentServiceResult(
                                    Activity.RESULT_OK,
                                    responsetosend!!,
                                    getString(R.string.dashboarapicall)
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    EventBus.getDefault().post(
                        IntentServiceResult(
                            Activity.RESULT_OK,
                            responsetosend!!,
                            getString(R.string.dashboarapicall)
                        )
                    )
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                EventBus.getDefault().post(
                    IntentServiceResult(
                        Activity.RESULT_OK,
                        responsetosend!!,
                        getString(R.string.dashboarapicall)
                    )
                )
            }
        })
    }


    // acknwoldegementcallhit  api
    private fun acknwoldegementcallhit(rideid: String, ack: String) {
        var params = HashMap<String, String>()
        params["driver_id"] = mSessionManager!!.getDriverId()
        params["ride_id"] = rideid
        params["action"] = ack
        apiCall = RetrofitInstance.getInstance().ackapi(mSessionManager!!.getApiHeader(), params)
        apiCall?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            var responseStr = response.body()?.string()
                            responsetosend = responseStr.toString()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
        })
    }

    // location api
    private fun steponefetchapi() {
        var params = HashMap<String, String>()
        params["id"] = mSessionManager!!.getDriverId()
        params["category"] = mSessionManager!!.getCategoryID()
        params["location"] = mSessionManager!!.getLocationID()
        params["vehicle_number"] = mSessionManager!!.getVehicleno()
        params["vehicle_type"] = mSessionManager!!.getVehicleTypeID()
        params["vehicle_maker"] = mSessionManager!!.getMakeID()
        params["vehicle_model"] = mSessionManager!!.getModelID()
        params["vehicle_model_year"] = mSessionManager!!.getYearID()
        params["vehicle_id"] = mSessionManager!!.getVehicleStepOneId()

        apiCall = RetrofitInstance.getInstance().savestep1(mSessionManager!!.getApiHeader(), params)
        apiCall?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            var responseStr = response.body()?.string()
                            responsetosend = responseStr.toString()
                            EventBus.getDefault().post(
                                IntentServiceResult(
                                    Activity.RESULT_OK,
                                    responsetosend!!,
                                    getString(R.string.steponeapi)
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    EventBus.getDefault().post(
                        IntentServiceResult(
                            Activity.RESULT_OK,
                            responsetosend!!,
                            getString(R.string.steponeapi)
                        )
                    )
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                EventBus.getDefault().post(
                    IntentServiceResult(
                        Activity.RESULT_OK,
                        responsetosend!!,
                        getString(R.string.steponeapi)
                    )
                )
            }
        })
    }


    // steptwodocument api
    private fun steptwodocument() {
//        header.put("type", "driver")

        var params = HashMap<String, String>()
        params.put("type", "driver")

        apiCall =
            RetrofitInstance.getInstance().getdocument(mSessionManager!!.getApiHeader(), params)
        apiCall?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            var responseStr = response.body()?.string()
                            responsetosend = responseStr.toString()
                            Log.d("chvxdfbhbvf", responsetosend)
                            EventBus.getDefault().post(
                                IntentServiceResult(
                                    Activity.RESULT_OK,
                                    responsetosend!!,
                                    getString(R.string.steptwoapi)
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    EventBus.getDefault().post(
                        IntentServiceResult(
                            Activity.RESULT_OK,
                            responsetosend!!,
                            getString(R.string.steptwoapi)
                        )
                    )
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                EventBus.getDefault().post(
                    IntentServiceResult(
                        Activity.RESULT_OK,
                        responsetosend!!,
                        getString(R.string.steptwoapi)
                    )
                )
            }
        })
    }


    // imageupload api
    private fun imageuploadapi(base64string: String) {
        var params = HashMap<String, String>()
        params["image"] = base64string
        apiCall =
            RetrofitInstance.getInstance().imageuploadpai(mSessionManager!!.getApiHeader(), params)
        apiCall?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            var responseStr = response.body()?.string()
                            responsetosend = responseStr.toString()
                            EventBus.getDefault().post(
                                IntentServiceResult(
                                    Activity.RESULT_OK,
                                    responsetosend!!,
                                    getString(R.string.imageupload)
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    EventBus.getDefault().post(
                        IntentServiceResult(
                            Activity.RESULT_OK,
                            responsetosend!!,
                            getString(R.string.imageupload)
                        )
                    )
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                EventBus.getDefault().post(
                    IntentServiceResult(
                        Activity.RESULT_OK,
                        responsetosend!!,
                        getString(R.string.imageupload)
                    )
                )
            }
        })
    }


    // startverficationapi api
    private fun startverficationapi() {
        var params = HashMap<String, String>()
        params["driver_id"] = mSessionManager!!.getDriverId()
        apiCall =
            RetrofitInstance.getInstance().verficationcall(mSessionManager!!.getApiHeader(), params)
        apiCall?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            var responseStr = response.body()?.string()
                            responsetosend = responseStr.toString()
                            EventBus.getDefault().post(
                                IntentServiceResult(
                                    Activity.RESULT_OK,
                                    responsetosend!!,
                                    getString(R.string.starverficationapi)
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    EventBus.getDefault().post(
                        IntentServiceResult(
                            Activity.RESULT_OK,
                            responsetosend!!,
                            getString(R.string.starverficationapi)
                        )
                    )
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                EventBus.getDefault().post(
                    IntentServiceResult(
                        Activity.RESULT_OK,
                        responsetosend!!,
                        getString(R.string.starverficationapi)
                    )
                )
            }
        })
    }


    // reuploadimageapi api
    private fun reuploadimageapi(
        id: String,
        image: String,
        name: String,
        type: String,
        choosedate: String
    ) {
        var params = HashMap<String, String>()
        params["driver_id"] = mSessionManager!!.getDriverId()
        params["document_id"] = id
        params["image"] = "data:image/png;base64," + image
        params["doc_name"] = name
        params["doc_type"] = type
        params["vehicle_id"] = mSessionManager!!.getVehicleStepOneId()
        params["expiry_date"] = choosedate
        params["document"] = ""

        apiCall =
            RetrofitInstance.getInstance().reuploadimage(mSessionManager!!.getApiHeader(), params)
        apiCall?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            var responseStr = response.body()?.string()
                            responsetosend = responseStr.toString()
                            EventBus.getDefault().post(
                                IntentServiceResult(
                                    Activity.RESULT_OK,
                                    responsetosend!!,
                                    getString(R.string.reuploadapi)
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    EventBus.getDefault().post(
                        IntentServiceResult(
                            Activity.RESULT_OK,
                            responsetosend!!,
                            getString(R.string.reuploadapi)
                        )
                    )
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                EventBus.getDefault().post(
                    IntentServiceResult(
                        Activity.RESULT_OK,
                        responsetosend!!,
                        getString(R.string.reuploadapi)
                    )
                )
            }
        })
    }

    private fun getRouteApiUrl(origin: LatLng, dest: LatLng): String {

        // Origin of route
        val str_origin = "origin=" + origin.latitude + "," + origin.longitude
        // Destination of route
        val str_dest = "destination=" + dest.latitude + "," + dest.longitude
        // Sensor enabled
        val sensor = "sensor=false&mode=driving"
        //waypoints

        var waypoints: String = ""
        for (i in 0 until waypoints_array.size) {
            if (i == 0)
                waypoints = "waypoints="
            waypoints += "via:" + waypoints_array.get(i).latitude.toString() + "," + waypoints_array.get(
                i
            ).longitude.toString() + "|"
        }
        // api_key
//        val api_key = "key=" + resources.getString(R.string.routeKeynew)
        val api_key = "key=" + mSessionManager.getgoogle_api_key()!!
        // Building the parameters to the web service
        val parameters = "$str_origin&$str_dest&$sensor&$waypoints&$api_key"
        // Output format
        val output = "json"
        // Building the url to the web service
        val url = "https://maps.googleapis.com/maps/api/directions/$output?$parameters"
        return url
    }

    private fun fetchRouteUrl(url: String) {

        val responseCall = RetrofitInstance.getGoogleInstance().getRouteList(url)
        responseCall.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        // mLoader?.dismiss()
                        try {
                            val responseStr = response.body()!!.string()
                            Log.e("getAddressListRfhghes", responseStr)
                            val parserTask = ParserTask()
                            // Invokes the thread for parsing the JSON data
                            if (Build.VERSION.SDK_INT >= 11) {
                                parserTask.executeOnExecutor(
                                    AsyncTask.THREAD_POOL_EXECUTOR,
                                    responseStr
                                )
                            } else {
                                parserTask.execute(responseStr)
                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }
                } else {
                    Log.e("getAddressListRes", "Failed")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                EventBus.getDefault().post(
                    IntentServiceResult(
                        Activity.RESULT_OK,
                        "0",
                        getString(R.string.mapfailedtointernet)
                    )
                )
            }
        })
    }

    @SuppressLint("StaticFieldLeak")
    private inner class ParserTask : AsyncTask<String, Int, List<List<HashMap<String, String>>>>() {

        // Parsing the data in non-ui thread
        override fun doInBackground(vararg jsonData: String): List<List<HashMap<String, String>>> {
            val jObject: JSONObject
            try {
                jObject = JSONObject(jsonData[0])
                Log.d("ParserTask", jsonData[0])
                val parser = RouteDataParser()
                Log.d("ParserTask", parser.toString())
                // Starts parsing data
                var routes: List<List<HashMap<String, String>>> =
                    parser.parse(jObject, applicationContext)
                Log.d("ParserTask", "Executing routes")
                Log.d("ParserTask", routes.toString())
                return routes
            } catch (e: Exception) {
                Log.d("ParserTask", e.toString())
                e.printStackTrace()
            }
            val r: List<List<HashMap<String, String>>> =
                ArrayList<ArrayList<HashMap<String, String>>>()
            return r
        }

        // Executes in UI thread, after the parsing process
        override fun onPostExecute(result: List<List<HashMap<String, String>>>) {
            var points: List<LatLng>
            var lineOptions: PolylineOptions? = null
            var wayPointBuilder: LatLngBounds.Builder? = null
            points = ArrayList<LatLng>()
            lineOptions = PolylineOptions()
            wayPointBuilder = LatLngBounds.builder()
            // Traversing through all the routes
            for (i in result.indices) {
                // Fetching i-th route
                val path = result[i]
                // Fetching all the points in i-th route
                for (j in path.indices) {
                    val point = path[j]
                    val lat = java.lang.Double.parseDouble(point["lat"])
                    val lng = java.lang.Double.parseDouble(point["lng"])
                    val position = LatLng(lat, lng)
                    points.add(position)
                }
                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points)
                /*      lineOptions.width(14f)
                      lineOptions.color(R.color.green)*/
                for (k in 0 until points.size) {
                    wayPointBuilder.include(points.get(k))
                }
                Log.d("onPostExecute", "onPostExecute lineoptions decoded")
            }
            var result_code: Int
            if (result.size != 0) {
                EventBus.getDefault().post(
                    IntentServiceRouteResult(
                        Activity.RESULT_OK,
                        lineOptions,
                        wayPointBuilder!!,
                        points
                    )
                )
            } else {
                EventBus.getDefault().post(
                    IntentServiceRouteResult(
                        Activity.RESULT_CANCELED,
                        lineOptions,
                        wayPointBuilder!!,
                        points
                    )
                )
                Log.d("onPostExecute", "without Polylines drawn")
            }
        }
    }


    //Fetch rerouting api call
    private fun fetchreroutingRouteUrl(url: String) {

        val responseCall = RetrofitInstance.getGoogleInstance().getRouteList(url)
        responseCall.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        // mLoader?.dismiss()
                        try {
                            val responseStr = response.body()!!.string()
                            Log.e("getAddressListRes22", responseStr)
                            val parserTask = ParserreroutingTask()
                            // Invokes the thread for parsing the JSON data
                            if (Build.VERSION.SDK_INT >= 11) {
                                parserTask.executeOnExecutor(
                                    AsyncTask.THREAD_POOL_EXECUTOR,
                                    responseStr
                                )
                            } else {
                                parserTask.execute(responseStr)
                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }
                } else {
                    Log.e("getAddressListRes", "Failed")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                EventBus.getDefault().post(
                    IntentServiceResult(
                        Activity.RESULT_OK,
                        "0",
                        getString(R.string.mapfailedtointernet)
                    )
                )
            }
        })
    }

    @SuppressLint("StaticFieldLeak")
    private inner class ParserreroutingTask :
        AsyncTask<String, Int, List<List<HashMap<String, String>>>>() {

        // Parsing the data in non-ui thread
        override fun doInBackground(vararg jsonData: String): List<List<HashMap<String, String>>> {
            val jObject: JSONObject
            try {
                jObject = JSONObject(jsonData[0])
                Log.d("ParserTask", jsonData[0])
                val parser = RouteDataParser()
                Log.d("ParserTask", parser.toString())
                // Starts parsing data
                var routes: List<List<HashMap<String, String>>> =
                    parser.parse(jObject, applicationContext)
                Log.d("ParserTask", "Executing routes")
                Log.d("ParserTask", routes.toString())
                return routes
            } catch (e: Exception) {
                Log.d("ParserTask", e.toString())
                e.printStackTrace()
            }
            val r: List<List<HashMap<String, String>>> =
                ArrayList<ArrayList<HashMap<String, String>>>()
            return r
        }

        // Executes in UI thread, after the parsing process
        override fun onPostExecute(result: List<List<HashMap<String, String>>>) {
            var points: List<LatLng>
            var lineOptions: PolylineOptions? = null
            var wayPointBuilder: LatLngBounds.Builder? = null
            points = ArrayList<LatLng>()
            lineOptions = PolylineOptions()
            wayPointBuilder = LatLngBounds.builder()
            // Traversing through all the routes
            for (i in result.indices) {
                // Fetching i-th route
                val path = result[i]
                // Fetching all the points in i-th route
                for (j in path.indices) {
                    val point = path[j]
                    val lat = java.lang.Double.parseDouble(point["lat"])
                    val lng = java.lang.Double.parseDouble(point["lng"])
                    val position = LatLng(lat, lng)
                    points.add(position)
                }
                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points)
                /*      lineOptions.width(14f)
                      lineOptions.color(R.color.green)*/
                for (k in 0 until points.size) {
                    wayPointBuilder.include(points.get(k))
                }
                Log.d("onPostExecute", "onPostExecute lineoptions decoded")
            }
            var result_code: Int
            if (result.size != 0) {
                EventBus.getDefault().post(
                    IntentServiceReroutingRouteResult(
                        Activity.RESULT_OK,
                        lineOptions,
                        wayPointBuilder!!,
                        points
                    )
                )
            } else {
                EventBus.getDefault().post(
                    IntentServiceReroutingRouteResult(
                        Activity.RESULT_CANCELED,
                        lineOptions,
                        wayPointBuilder!!,
                        points
                    )
                )
                Log.d("onPostExecute", "without Polylines drawn")
            }
        }
    }


    // common api fetch service
    private fun commonapihit(header: HashMap<String, String>, api_name: String) {
        KotNetworking.post(urltohit)
            .addBodyParameter(header)
            .addHeaders(mSessionManager!!.getApiHeader())
            .setTag(this)
            .setPriority(Priority.HIGH)
            .build()
            .getAsString { response, error ->
                if (error != null) {
//                        if(api_name.equals(offerpaymentstatuscheck)){
//                            EventBus.getDefault().post(IntentServiceResult(Activity.RESULT_OK, responsetosend!!, getString(R.string. offerpaymentstatuscheck)))
//                          Log.d("offercheck $responsetosend","")
//                        }


                    EventBus.getDefault()
                        .post(IntentServiceResult(Activity.RESULT_OK, responsetosend!!, api_name))
                    Log.d("djgtssg", responsetosend!!)
                    Log.d("dstgjertyg", (mSessionManager!!.getApiHeader().toString()))
                    Log.d("dsdgrtgjertyg", (header.toString()))

                } else {
                    // Success part

                    if (api_name.equals(getString(R.string.getappinfohit))) {
                        saveauthkey(response.toString()!!)
                    } else if (api_name.equals(getString(R.string.verifyvehiclenumber))) {
                        val response_json_object = JSONObject(response)
                        try {
                            val status = response_json_object.getString("status")
                            val response = response_json_object.getString("response")
                            val vehicleinfo = JSONObject(response)
                            val vehicleinfoobj = vehicleinfo.getString("vehicleinfo")
                            val vehicle_id = JSONObject(vehicleinfoobj)
                            val storevehicle_id = vehicle_id.getString("vehicle_id")
                            val driver_id = vehicle_id.getString("driver_id")
                            val verify_status = vehicle_id.getString("verify_status")
                            Log.d("storevehicle_id2", storevehicle_id)
                            var stage = "UPLOAD_DRIVER_DOC"
                            mSessionManager.setdocumentstage2(stage)





                            mSessionManager = SessionManager(this)
                            mSessionManager.settempvehicleid(storevehicle_id)
                            mSessionManager.setvehicleid(storevehicle_id)
                            mSessionManager.settempvehicleid(storevehicle_id)


                            EventBus.getDefault().post(
                                IntentServiceResult(
                                    Activity.RESULT_OK,
                                    status,
                                    getString(R.string.verifyvehiclenumber)
                                )
                            )
                            Log.d("vhjxhfghcb", response.toString()!!)

                        } catch (e: Exception) {
                        }
                    } else if (api_name.equals(getString(R.string.emailcheckk))) {
                        try {
                            val response_json_object = JSONObject(response.toString())
                            val status = response_json_object.getString("status")
                            if (status.equals("1")) {
                                EventBus.getDefault().post(
                                    IntentServiceResult(
                                        Activity.RESULT_OK,
                                        status,
                                        getString(R.string.emailcheckk)
                                    )
                                )
                            }
                        } catch (e: Exception) {
                        }
                    } else {

                        Log.d("dfhhchc", response.toString()!!)
                        if (api_name.equals(getString(R.string.offer_copen1))) {
                            try {
                                val response_json_object = JSONObject(response.toString())
                                val status = response_json_object.getString("status")


                                var response = response_json_object.getString("response")
                                var offerList = response_json_object.getJSONArray("offers")
                                val offer_banner_default=response_json_object.getString("offer_banner_default")
                                mSessionManager!!.putDefaultbanner(offer_banner_default)
                                var listData = ArrayList<String>()

                                if (offerList.length() > 0) {
                                    for (g in 0 until offerList.length()) {
                                        val offerList2 = offerList.getJSONObject(g)
                                        var banner_image = offerList2.getString("banner_image")
                                        listData.add(banner_image)
                                    }
                                    if(mSessionManager==null)
                                    mSessionManager = SessionManager(this)
                                    mSessionManager.putModelImage(listData)
//                                    mSessionManager.putDefaultbanner(offer_banner_default)
                                }


                            } catch (e: Exception) {
                            }
                        } else {

                            EventBus.getDefault().post(
                                IntentServiceResult(
                                    Activity.RESULT_OK,
                                    response.toString()!!,
                                    api_name
                                )
                            )

                            Log.d("vxcvhvbv", (mSessionManager!!.getApiHeader().toString()))
                        }
                    }
                }
            }
    }

    // common api fetch service
    private fun dummycommonapihit(header: HashMap<String, String>) {
        KotNetworking.post(urltohit)
            .addBodyParameter(header)
            .addHeaders(mSessionManager!!.getApiHeader())
            .setTag(this)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsString { response, error ->
                if (error != null) {
                    // failure part
                    if (api_name.equals(getString(R.string.getmakemodelyear))) {
                        EventBus.getDefault().post(
                            IntentServiceResult(
                                Activity.RESULT_OK,
                                responsetosend!!,
                                getString(R.string.getmakemodelyear)
                            )
                        )
                    } else if (api_name.equals(getString(R.string.getonlymake))) {
                        EventBus.getDefault().post(
                            IntentServiceResult(
                                Activity.RESULT_OK,
                                responsetosend!!,
                                getString(R.string.getonlymake)
                            )
                        )
                    }
                } else {
                    // Success part
                    if (api_name.equals(getString(R.string.getmakemodelyear))) {
                        EventBus.getDefault().post(
                            IntentServiceResult(
                                Activity.RESULT_OK,
                                response.toString()!!,
                                getString(R.string.getmakemodelyear)
                            )
                        )
                    } else if (api_name.equals(getString(R.string.getonlymake))) {
                        savemakeandmodel(response.toString())
                        EventBus.getDefault().post(
                            IntentServiceResult(
                                Activity.RESULT_OK,
                                response.toString()!!,
                                getString(R.string.getonlymake)
                            )
                        )
                    }
                }
            }
    }


    private fun earningsapi(date_value: String) {
        var params = HashMap<String, String>()
        params.put("driver_id", mSessionManager!!.getDriverId())
        params.put("date_from", date_value)
        params.put("date_to", date_value)

        Log.d("params23", params.toString())


        apiCall =
            RetrofitInstance.getInstance().getearnings(mSessionManager!!.getApiHeader(), params)
        apiCall?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            var responseStr = response.body()?.string()
                            responsetosend = responseStr.toString()
                            Log.d("get_earning2", responsetosend)
                            EventBus.getDefault().post(
                                IntentServiceResult(
                                    Activity.RESULT_OK,
                                    responsetosend!!,
                                    getString(R.string.get_earning)
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    EventBus.getDefault().post(
                        IntentServiceResult(
                            Activity.RESULT_OK,
                            responsetosend!!,
                            getString(R.string.get_earning)
                        )
                    )
                    Log.d("dfsgtdfgh", responsetosend)

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                EventBus.getDefault().post(
                    IntentServiceResult(
                        Activity.RESULT_OK,
                        responsetosend!!,
                        getString(R.string.get_earning)
                    )
                )
                Log.d("dassddf", responsetosend)


            }
        })
    }


    private fun faqFareApi() {
        var params = HashMap<String, String>()
        params.put("id", mSessionManager!!.getDriverId())
        params.put("user_type", "driver")
        Log.d("dhfd2g", params.toString())


        apiCall =
            RetrofitInstance.getInstance().getFaquser(mSessionManager!!.getApiHeader(), params)
        apiCall?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            var responseStr = response.body()?.string()
                            responsetosend = responseStr.toString()
                            Log.d("fsjbf", responsetosend)
                            EventBus.getDefault().post(
                                IntentServiceResult(
                                    Activity.RESULT_OK,
                                    responsetosend!!,
                                    getString(R.string.user_referra)
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    EventBus.getDefault().post(
                        IntentServiceResult(
                            Activity.RESULT_OK,
                            responsetosend!!,
                            getString(R.string.user_referra)
                        )
                    )
                    Log.d("dfsgtdfgh", responsetosend)

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                EventBus.getDefault().post(
                    IntentServiceResult(
                        Activity.RESULT_OK,
                        responsetosend!!,
                        getString(R.string.user_referra)
                    )
                )
                Log.d("dassddf", responsetosend)


            }
        })
    }


    // saving auth key to session
    fun saveauthkey(response: String) {
        var list = ArrayList<String>()
        try {
            val response_json_object = JSONObject(response)
            Log.d("cbtystydvffd", response)

            val status = response_json_object.getString("status")
            val response_object = `response_json_object`.getJSONObject("response")
            if (status.equals("1")) {
                //Xmpp Hostnames
                var xmpp_host_url = response_object.getString("xmpp_host_url")
                var xmpp_host_name = response_object.getString("xmpp_host_name")
                var support_email = response_object.getString("support_email")
                var support_number = response_object.getString("support_number")


                val app_identity_name = response_object.getString("app_identity_name")


                //Amazon bucket details
                var Str_s3_bucket_name = response_object.getString("s3_bucket")
                var Str_s3_access_key = response_object.getString("s3_access_key")
                var Str_s3_secret_key = response_object.getString("s3_secret_key")
                var google_placesearch_key_android =
                    response_object.getString("google_placesearch_key_android")
                val keydata = Base64.decode(google_placesearch_key_android, Base64.DEFAULT)
                google_placesearch_key_android = String(keydata, Charset.forName("UTF-8"))
                val data = Base64.decode(Str_s3_bucket_name, Base64.DEFAULT)
                Str_s3_bucket_name = String(data, Charset.forName("UTF-8"))
                val data1 = Base64.decode(Str_s3_access_key, Base64.DEFAULT)
                Str_s3_access_key = String(data1, Charset.forName("UTF-8"))
                val data2 = Base64.decode(Str_s3_secret_key, Base64.DEFAULT)
                Str_s3_secret_key = String(data2, Charset.forName("UTF-8"))
                mSessionManager.setgoogle_api_key(google_placesearch_key_android)
                mSessionManager!!.setS3Info(
                    Str_s3_bucket_name,
                    Str_s3_access_key,
                    Str_s3_secret_key
                )
                mSessionManager = SessionManager(applicationContext!!)
                mSessionManager!!.storeauthkeyvalue(app_identity_name)
                mSessionManager!!.setXmppDetails(xmpp_host_url, xmpp_host_name)
                mSessionManager.setsupport_email(support_email)
                mSessionManager.setsupport_number(support_number)

                val upload_directories = response_object.getString("upload_directories")
                val response_json_object = JSONObject(upload_directories)
                val profile_picture =
                    response_json_object.getString("profile_picture").replace("\\/", "/")
                val vehicle_image =
                    response_json_object.getString("vehicle_image").replace("\\/", "/")
                val documentsFiles = response_json_object.getString("documents").replace("\\/", "/")
                var reports_subjects = response_object.getJSONArray("reports_subjects")
                Log.e("Array......", reports_subjects.toString())
                mSessionManager.setreportlist(reports_subjects.toString())
//---[
//                if (reports_subjects.length() > 0){
//                    for (l in 0 until reports_subjects.length()){
//                        list.add(reports_subjects[l].toString())
//                    }
//                }
//                Log.d("dsfsgdd34", list.toString())


                mSessionManager!!.setXmppFolderPath(profile_picture, vehicle_image, documentsFiles)
                EventBus.getDefault().post(
                    IntentServiceResult(
                        Activity.RESULT_OK,
                        "1",
                        getString(R.string.getappinfohit)
                    )
                )
            } else {
                EventBus.getDefault().post(
                    IntentServiceResult(
                        Activity.RESULT_OK,
                        "0",
                        getString(R.string.getappinfohit)
                    )
                )
            }
        } catch (e: Exception) {
            Log.e("asdfasdf", e.toString())
        }
    }

    fun savemakeandmodel(response: String) {
        val response_json_object = JSONObject(response)
        try {
            val status = response_json_object.getString("status")
            if (status.equals("1")) {
                mSessionManager = SessionManager(applicationContext!!)
                mSessionManager!!.setCategoryDetails(response)
            }
        } catch (e: Exception) {
        }
    }

    fun getfcmtoken() {
        if (mSessionManager!!.getfcmkey().length < 5) {
            FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { instanceIdResult ->
                val token = instanceIdResult.token
                saveFcmId((token))
            }
        }
    }

    private fun saveFcmId(token: String?) {
        var mSessionManager: SessionManager?
        mSessionManager = SessionManager(applicationContext)
        mSessionManager.setfcmkey(token!!)
    }

}