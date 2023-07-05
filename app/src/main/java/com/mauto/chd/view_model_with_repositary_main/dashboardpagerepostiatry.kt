package com.mauto.chd.view_model_with_repositary_main

import android.app.Activity
import android.content.Context
import android.util.Log
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.SessionManagerPackage.SessionManager
import org.greenrobot.eventbus.EventBus
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList


class dashboardpagerepostiatry(private val listener: Listener)
{
    //for getting driver details
    fun getdrivertails(mcontext:Context,response:String)
    {
        try
        {
            var vehicledetailsarraylist = ArrayList<MianPageDataModel>()
            var earningarraylist = ArrayList<EarningModel>()
            var responseObj = JSONObject(response)
            var mSessionManager: SessionManager? = null
            mSessionManager = SessionManager(mcontext!!)
            if (responseObj.getString("status").equals("0")){
                var stage = ""
                Log.d("efdgf",stage)
                mSessionManager.setdocumentstage2(stage)
            }
            if (responseObj.getString("status").equals("1"))
            {
                if (responseObj.has("response"))
                {

                    var getingres: String = responseObj.getString("response")
                    var resultsresponse = JSONObject(getingres)
                    Log.d("edggf55gfdgf", resultsresponse.toString())



                    var availability = resultsresponse.getString("availability")
                    var verify_status = resultsresponse.getString("verify_status")
                    var stage = resultsresponse.getString("stage")

                    var completed_rides=  resultsresponse.getString("completed_rides")
                    var missed_rides =resultsresponse.getString("missed_rides")
                    var today_earnings =resultsresponse.getString("today_earnings")
                    var swap_battery =resultsresponse.getString("swap_battery")
                    var driver_name =resultsresponse.getString("driver_name")
                    var driver_phone =resultsresponse.getString("driver_phone")
                    var driver_vehicle =resultsresponse.getString("driver_vehicle")
                    var driver_image =resultsresponse.getString("driver_image")

                    val walletcheck=resultsresponse.getBoolean("walletcheck")
                    Log.d("mjccwalletcheck", walletcheck.toString())
                    if (walletcheck==false){
                        EventBus.getDefault().post(IntentServiceResult(Activity.RESULT_OK, "", "fleet_immobilized"))
                    }
                    mSessionManager.setswap_battery(swap_battery)

                    mSessionManager.setcompleted_rides(completed_rides)
                    mSessionManager.settoday_earnings(today_earnings)
                    mSessionManager.setmissed_rides(missed_rides)
                    Log.d("efdgf",mSessionManager.getcompleted_rides())
                    mSessionManager.setdocumentstage2(stage)
                    mSessionManager.setmstage(stage)

                    listener!!.onEarning(driver_name)
                    listener!!.onAmount(driver_phone)
                    listener!!.onEarningsAdded(driver_vehicle)

                    listener!!.driverimage(driver_image)



                    if(resultsresponse.has("duty_ride"))
                    {
                        var duty_ride = resultsresponse.getString("duty_ride")
                        if(!duty_ride.equals(""))
                        {
                            listener!!.onDutyride(duty_ride)
                        }
                    }
                    var is_otp_ride=resultsresponse.optBoolean("is_otp_ride",false)
                    mSessionManager.setis_otp_ride(is_otp_ride)
                    val is_electric = resultsresponse.getBoolean("is_electric")
                    mSessionManager.setis_electric(is_electric.toString())
                    if (is_electric.equals(true)){
                        val battery =resultsresponse.getJSONObject("battery")
                        var percentage=battery.getInt("percentage")
                        var mileage=battery.getInt("mileage")
                        mSessionManager.setpercentage(percentage.toString())
                        mSessionManager.setmileage(mileage.toString())


                    }


                    if(verify_status.equals("No"))
                    {
                        listener!!.onDocstatus("0")
                    }
                    else
                    {
                        listener!!.onDocstatus("1")
                    }


                  /*  var driver_id = resultsresponse.getString("driver_id")
                    var driver_name = resultsresponse.getString("driver_name")
                    var driver_image = resultsresponse.getString("driver_image")
                    var vehicle_model = resultsresponse.getString("vehicle_model")
                    var vehicle_number = resultsresponse.getString("vehicle_number")
                    var driver_email = resultsresponse.getString("driver_email")
                    var status_verify_window = resultsresponse.getString("status_verify_window")
                    var driver_category = resultsresponse.getString("driver_category")
                    var map_icon = ""
                    var driver_review = resultsresponse.getString("driver_review")
                    var phone_number = resultsresponse.getString("phone_number")
                    listener!!.onMobileNo(phone_number)*/

                /*    mSessionManager!!.setDriverMobileNO(phone_number)
                    mSessionManager!!.driverProfileData(driver_id,driver_name,driver_image,driver_email)
                    mSessionManager!!.setvehiclenumber(vehicle_number)
                    mSessionManager!!.setModelnamealone(vehicle_model)
                    mSessionManager!!.setReview(driver_review)
                    mSessionManager!!.setDocPending(status_verify_window)
                    listener!!.onDocstatus(status_verify_window)
                    mSessionManager!!.setCategoryNamealone(driver_category)


                    var currency = resultsresponse.getString("currency")
                    var currency_symbol: String = ""
                    var currencySymbolConventer: CurrencySymbolConventer
                    currencySymbolConventer = CurrencySymbolConventer()
                    currency_symbol = currencySymbolConventer.getCurrencySymbol(currency)
                    mSessionManager!!.setcurrencysymbol(currency_symbol)
                    listener!!.onAmount(currency_symbol)

                    var today_earnings = resultsresponse.getString("today_earnings")
                    var today_earningsres = JSONObject(today_earnings)
                    var earnings = today_earningsres.getString("earnings")
                    listener!!.onEarning(earnings)

                    //todays earning
                    var earning: String = resultsresponse.getString("today_earnings")
                    var earningresponse = JSONObject(earning)
                    var total_trips = earningresponse.getString("total_trips")
                    var toearnings = earningresponse.getString("earnings")
                    var total_hours = total_trips
                    earningarraylist.add(EarningModel(currency_symbol,toearnings,mcontext.getString(R.string.today_trips),total_hours,driver_category))

                    //LAST trip earning
                    var last_trip: String = resultsresponse.getString("last_trip")
                    var last_tripearningresponse = JSONObject(last_trip)
                    var ride_time = last_tripearningresponse.getString("ride_time")
                    var lSTearnings = last_tripearningresponse.getString("earnings")
                    var ridetime:String=""
                    if(ride_time.equals("00:00"))
                    {
                        ridetime = mcontext.getString(R.string.ridetidme)
                    }
                    else
                    {
                        ridetime = ride_time
                    }
                    earningarraylist.add(EarningModel(currency_symbol,lSTearnings,mcontext.getString(R.string.lastrip),ridetime,driver_category))


                    //Weekeley earning
                    var weekly_trip: String = resultsresponse.getString("weekly_trip")
                    var weekly_tripearningresponse = JSONObject(weekly_trip)
                    var total_tripsss = weekly_tripearningresponse.getString("total_trips")
                    var lsSTearnings = weekly_tripearningresponse.getString("earnings")
                    var total_tripss = total_tripsss
                    earningarraylist.add(EarningModel(currency_symbol,lsSTearnings,mcontext.getString(R.string.weekrip),total_tripss,driver_category))



                    vehicledetailsarraylist.add(MianPageDataModel(driver_id,driver_name,driver_image,vehicle_number,vehicle_model,driver_review,map_icon))
             */   }

                if (listener != null)
                {
                   /* listener!!.onDataReceived(vehicledetailsarraylist)
                    listener!!.onEarningsAdded(earningarraylist)*/
                }
            }
        }
        catch (e: JSONException)
        {
        }
    }

    //for getting driver details
    fun getdriveravailablitystatus(response:String,avialbilty:String,mcontext: Context)
    {
        var mSessionManager: SessionManager? = null
        try
        {
            var responseObj = JSONObject(response)
            if (responseObj.getString("status").equals("1"))
            {
                val response =responseObj.getJSONObject("response")



                mSessionManager = SessionManager(mcontext!!)
                mSessionManager.setOnlineAvailability(avialbilty)
                if (listener != null)
                {
                    listener!!.onOnlinestatusChange(avialbilty)
                }
            }
        }
        catch (e: JSONException)
        {
        }
    }




}