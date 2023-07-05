package com.mauto.chd.retrofit

import com.dineshm.AutoCompleteWithDB.Pojo.AutoCompleteResponcePojo
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface RetrofitInterface
{
    @FormUrlEncoded
    @POST("api/driver/registration/verify/mobile")
    fun getlogindetails(@HeaderMap header: HashMap<String, String>, @FieldMap params: HashMap<String, String>): Call<ResponseBody>

    @GET
    fun getRouteList(@Url url: String): Call<ResponseBody>


    @GET("place/autocomplete/json?key=AIzaSyBqaj6IjQPjaQ7ahlVe5ulccfOpBqoAz2s")
    fun getAddressListApi(@Query("input") input:String) : Call<AutoCompleteResponcePojo>?
    @GET("geocode/json?geocode/json?sensor=false&key=AIzaSyBqaj6IjQPjaQ7ahlVe5ulccfOpBqoAz2s")
    fun getLatLng(@Query("address") address:String): Call<ResponseBody>?

    @GET
    fun getPolylineDataWithWayPoint(@Url url: String?, @Query("origin") origin: String?, @Query("destination") destination: String?, @Query(value = "waypoints", encoded = true) waypoints: String?, @Query("key") key: String?): Call<ResponseBody>

    @FormUrlEncoded
    @POST("api/user/otp/resend")
    fun resendotp(@HeaderMap header: HashMap<String, String>, @FieldMap params: HashMap<String, String>): Call<ResponseBody>


    @FormUrlEncoded
    @POST("api/get-app-info")
    fun getappinfodetails(@HeaderMap header: HashMap<String, String>, @FieldMap params: HashMap<String, String>): Call<ResponseBody>


    @FormUrlEncoded
    @POST("api/driver/registration/personal")
    fun sendprofiledetails(@HeaderMap header: HashMap<String, String>, @FieldMap params: HashMap<String, String>): Call<ResponseBody>


    @FormUrlEncoded
    @POST("api/driver/registration/vehicles/get")
    fun categoryfetchapi(@HeaderMap header: HashMap<String, String>, @FieldMap params: HashMap<String, String>): Call<ResponseBody>

    @FormUrlEncoded
    @POST("api/driver/registration/init")
    fun locationfetchapi(@HeaderMap header: HashMap<String, String>, @FieldMap params: HashMap<String, String>): Call<ResponseBody>



    @FormUrlEncoded
    @POST("api/driver/registration/update/vehicle")
    fun savestep1(@HeaderMap header: HashMap<String, String>, @FieldMap params: HashMap<String, String>): Call<ResponseBody>


    @FormUrlEncoded
    @POST("api/driver/dashboard")
    fun driverdashboardapi(@HeaderMap header: HashMap<String, String>, @FieldMap params: HashMap<String, String>): Call<ResponseBody>


    @FormUrlEncoded
    @POST("api/driver/registration/document/get")
    fun getdocument(@HeaderMap header: HashMap<String, String>, @FieldMap params: HashMap<String, String>): Call<ResponseBody>


    @FormUrlEncoded
    @POST("api/driver/registration/document/upload")
    fun imageuploadpai(@HeaderMap header: HashMap<String, String>, @FieldMap params: HashMap<String, String>): Call<ResponseBody>



    @FormUrlEncoded
    @POST("api/driver/registration/document/update")
    fun finaldocumenttwosend(@HeaderMap header: HashMap<String, String>, @FieldMap params: HashMap<String, String>): Call<ResponseBody>



    @FormUrlEncoded
    @POST("api/driver/document/verification")
    fun verficationcall(@HeaderMap header: HashMap<String, String>, @FieldMap params: HashMap<String, String>): Call<ResponseBody>


    @FormUrlEncoded
    @POST("api/driver/update/document")
    fun reuploadimage(@HeaderMap header: HashMap<String, String>, @FieldMap params: HashMap<String, String>): Call<ResponseBody>



    @FormUrlEncoded
    @POST("api/location/update/driver")
    fun onlinecallupdate(@HeaderMap header: HashMap<String, String>, @FieldMap params: HashMap<String, String>): Call<ResponseBody>



    @FormUrlEncoded
    @POST("api/driver/update/availability")
    fun driveravailable(@HeaderMap header: HashMap<String, String>, @FieldMap params: HashMap<String, String>): Call<ResponseBody>


    @FormUrlEncoded
    @POST("api/trip/request/ack")
    fun ackapi(@HeaderMap header: HashMap<String, String>, @FieldMap params: HashMap<String, String>): Call<ResponseBody>



    @FormUrlEncoded
    @POST("api/booking/accept")
    fun acceptapi(@HeaderMap header: HashMap<String, String>, @FieldMap params: HashMap<String, String>): Call<ResponseBody>


    @FormUrlEncoded
    @POST("api/trip/location/fare")
    fun getlocationFare(@HeaderMap header: HashMap<String, String>, @FieldMap params: HashMap<String, String>): Call<ResponseBody>

    @FormUrlEncoded
    @POST("api/trip/otp/get")
    fun getUserOtp(@HeaderMap header: HashMap<String, String>, @FieldMap params: HashMap<String, String>): Call<ResponseBody>

    @FormUrlEncoded
    @POST("api/trip/otp/begun")
    fun getOtpRideBegun(@HeaderMap header: HashMap<String, String>, @FieldMap params: HashMap<String, String>): Call<ResponseBody>
    @FormUrlEncoded
    @POST("api/user/referral")
    fun getreferralDelete(@HeaderMap header: HashMap<String, String>, @FieldMap params: HashMap<String, String>): Call<ResponseBody>
    @FormUrlEncoded
    @POST("api/faq/get")
    fun getFaquser(@HeaderMap header: HashMap<String, String>, @FieldMap params: HashMap<String, String>): Call<ResponseBody>
    @FormUrlEncoded
    @POST("api/driver/earnings")
    fun getearnings(@HeaderMap header: HashMap<String, String>, @FieldMap params: HashMap<String, String>): Call<ResponseBody>

}


