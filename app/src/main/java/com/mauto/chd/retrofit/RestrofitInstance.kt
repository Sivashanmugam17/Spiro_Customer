package com.mauto.chd.retrofit

import com.mindorks.kotnetworking.interceptors.HttpLoggingInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

//
//  private const val url = "https://epoint.mautoafrica.com/"


  private const val url = "https://uat-epoint.mautoafrica.com/"



//  private const val url = "https://uat-epoint.mautoafrica.com/"


  //  private const val baseUrl = url + "driver/v1/"
  private const val baseUrl = url + "v3/"

  private const val baseUrl2 = url + "trips/v1/"

//  private const val url = "https://cabily-e.zoplay.com/"
//  private const val baseUrl = url + "v1/"


  val appmoreinfo = baseUrl + "api/driver/init"
  val triplist = baseUrl + "api/trip/driver/get"
//  val verifymobile = baseUrl + "api/driver/signup/vcode/get"
  val verifymobile = baseUrl + "api/driver/get/code"
  val updatemobileno = baseUrl + "api/driver/update/mobile"
  val updateemailid = baseUrl + "api/driver/update/email"
  val resendotp = baseUrl + "api/driver/signup/vcode/get"
  val checkuserexist = baseUrl + "api/driver/login"
  val onlineofflinecall = baseUrl + "api/driver/workstatus/update"
  val updatedriverlat = baseUrl + "api/driver/location/update"
  val dashboardcallapi = baseUrl + "api/driver/dashboard/get"
  val servicelocationapi = baseUrl + "api/driver/loccat/get"
  val getdocument = baseUrl + "api/driver/document/get"
//  val getcatmakemodel = baseUrl + "api/driver/vehicleinfo/get"
  val getcatmakemodel = baseUrl + "api/driver/makemodel/get"
  val getvehicleid = baseUrl + "api/driver/vehicle/update"
  val getdriverprofile = baseUrl + "api/driver/profile/get"
  val getboundary = baseUrl + "api/driver/geofence/get"
  val getboundaryupdate = baseUrl + "api/driver/geofence/update"
  val paymentsucceschecking = baseUrl + "api/payment/trip/success"



  val paymenttripinit = baseUrl + "api/payment/trip/init"
  val offerpaymentinit = baseUrl + "api/offers/payment/init"
  val offerpaymentstatuscheck = baseUrl+"api/offers/payment/check"
  val voucherpaymentstatuscheck = baseUrl+"api/voucher/purchase/check"
  val offer = baseUrl + "api/offers/list"
  val appversion = baseUrl + "api/version/latest"


  val swapstation = baseUrl + "api/driver/swapstation/get"
  val servicestation = baseUrl + "api/driver/servicestation/get"

//  https://uat-epoint.mautoafrica.com/v2/api/driver/servicestation/get

  val referrals = baseUrl + "api/driver/referrals/submit"
  val vehiclelist = baseUrl + "api/driver/vehicle/list"
  val changepasswords = baseUrl + "api/driver/password/change"




  val paymentlist = baseUrl + "api/payment/list"
  val paymentinit = baseUrl + "api/wallet/recharge/init"
  val voucherpaymentinit = baseUrl+"api/voucher/purchase/init"
    val paymenprebookingtinit = baseUrl + "api/prebooking/payment/init"

    val rechargeupdate = baseUrl + "api/wallet/recharge/update"
  val rechargecheck = baseUrl + "api/wallet/recharge/check"
    val rechargecheckprebooking = baseUrl + "api/prebooking/payment/check"


  val fianalmovepage = baseUrl + "api/driver/stages/update"
  val documentsubmit = baseUrl + "api/driver/documents/update"
  val driverprofile = baseUrl + "api/driver/profile/get"
  val applanage = baseUrl + "api/lg/get"
//  val swipe_battery = baseUrl + "api/battery/qrverify"
  val swipe_battery = baseUrl + "api/swapping/battery/scan"
  val submit_battery = baseUrl + "api/swapping/battery/submit"
  val logoutcall = baseUrl + "api/driver/logout"
//  val basicregister = baseUrl + "api/driver/signup/submit"
  val basicregister = baseUrl + "api/driver/register"
  val editprofile = baseUrl + "api/driver/update/profile"
  val walletbalance = baseUrl + "api/wallet/get"
    val prebookingdetails = baseUrl + "api/prebooking/details"
    val wallettransactions = baseUrl + "api/wallet/transactions/get"
  val reportssubmit = baseUrl + "api/reports/submit"
  val prebookingcancel = baseUrl + "api/prebooking/payment/cancel"

  val prebookingmodel = baseUrl + "api/prebooking/model/update"

    val payment_key = baseUrl + "api/payment/settings"




  //  val ridedetailss = baseUrl2 + "api/trip/driver/track/info"
  val ridedetailss = baseUrl + "api/trip/driver/track"
//  val acknowledgecallhit = baseUrl + "api/trip/request/ack/update"
    val acknowledgecallhit = baseUrl + "api/trip/acknowledge"

  val cancelreason = baseUrl + "api/trip/cancel/reason/driver"
//  val cancelthisride = baseUrl2 + "api/trip/driver/cancel/submit"
  val cancelthisride = baseUrl + "api/trip/cancel/user"

  //  val tripupdatearrived = baseUrl2 + "api/trip/location/arrived"
  val tripupdatearrived = baseUrl + "api/trip/arrived"

//  val begintripurl = baseUrl2 + "api/trip/begin"
  val begintripurl = baseUrl + "api/trip/begin"
  val endtripurl = baseUrl + "api/trip/finish"
  val receivecash = baseUrl + "api/trip/cash-collected"
  val submitrating = baseUrl + "api/trip/ratting/submit/driver"
  val getvehiclelistt = baseUrl + "api/driver/vehicle/get"
  val defaultvechicleupdate = baseUrl + "api/driver/vehicle/default"
  val editvehicleinfo = baseUrl + "api/driver/vehicle/info"
  val driverdocinfo = baseUrl + "api/driver/info"
  val paymentmoneycheckride = baseUrl + "api/payment/check-ride"


  // Start of end points
  val verifyemail = baseUrl + "api/driver/registration/verify/email"
  val nearingcallhit = baseUrl + "api/send-user-alert"

  val skiprideid = baseUrl + "api/reviews/skip"




  val chaturl = url + "v7/app/chat/push-chat-message"
  val tripdetailss = baseUrl + "api/driver/trip/list"
  val tripdetailssfull = baseUrl + "api/driver/trip/view"

  // End of end points


  private val loggingInterceptor = HttpLoggingInterceptor().apply {
    this.setLevel(HttpLoggingInterceptor.Level.BODY)
  }


    private var retrofit: Retrofit? = null
    private var retrofitGoogle: Retrofit? = null
    private val okHttpClient = OkHttpClient.Builder().connectTimeout(90, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)




    fun getInstance(): RetrofitInterface {
      okHttpClient.addInterceptor(object : Interceptor {
       override fun intercept(chain: Interceptor.Chain): Response? {
          val requestBuilder: Request.Builder = chain.request().newBuilder()
          requestBuilder.header("Content-Type", "application/json")
          requestBuilder.header("Accept", "application/json")
          return chain.proceed(requestBuilder.build())
        }
      })
        if (retrofit == null)
            retrofit = Retrofit.Builder()
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build()
        return retrofit!!.create(RetrofitInterface::class.java)
    }

    fun getGoogleInstance(): RetrofitInterface {


        if (retrofitGoogle == null)
            retrofitGoogle = Retrofit.Builder()
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://maps.googleapis.com/")
                    .build()
        return retrofitGoogle!!.create(RetrofitInterface::class.java)
    }


}
