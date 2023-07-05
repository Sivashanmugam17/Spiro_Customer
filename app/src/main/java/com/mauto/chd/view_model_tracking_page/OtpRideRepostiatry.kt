package com.mauto.chd.view_model_tracking_page

import android.app.Activity
import android.content.Context
import android.location.Location
import android.util.Log
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.commonutils.CurrencySymbolConventer
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import com.mauto.chd.R
import org.greenrobot.eventbus.EventBus
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import javax.script.ScriptException
import kotlin.collections.ArrayList


class OtpRideRepostiatry(private val listener: OtpRidePageListener)
{

    companion object{
        var arrayList : ArrayList<String> = ArrayList()
        var arrayListvalue : ArrayList<String> = ArrayList()
        lateinit var farearray : JSONArray
    }

    var isInBoundary: ArrayList<Boolean> = ArrayList()
    var current_boundary_name = ""
    private var share_pool_single_seat_value: Double = 0.0
    var c_pool_min_value: Int = 0




    fun locationFareApiParser(mContext: Activity,response: String,pickup_latlng:LatLng,rideModeIntent:String,nearest_driver_arriving_time:String,cab_avail:String)
    {
        var currencySymbolConventer = CurrencySymbolConventer()
        var category_item_array: ArrayList<CategoryItemModel> = ArrayList()
        var category_item_updated_array: ArrayList<CategoryItemModel> = ArrayList()
        var currency_symbol = ""
        var boundary_lat_lng_array: ArrayList<LatLng> = ArrayList()
        var estimated_price_array: ArrayList<String> = ArrayList()
        var isServiceAvail = true

        var mSessionManager = SessionManager(mContext)
        mSessionManager!!.setLocationFareApiResponse(response)
        Log.d("dfhjdhjfhjdd23",response)

        val response_json_object = JSONObject(response)
        try
        {
            val status = response_json_object.get("status")
            if (status.equals("1"))
            {
                val response_object = response_json_object.getJSONObject("response")
                val location_list_array = response_object.getJSONArray("location_list")
                isInBoundary.clear()
                category_item_array.clear()
                category_item_updated_array.clear()
                for (i in 0 until location_list_array.length())
                {
                    val location_name = location_list_array.getJSONObject(i).getString("location_name")
                    val currency_code = location_list_array.getJSONObject(i).getString("currency_code")
                    currency_symbol = currencySymbolConventer.getCurrencySymbol(currency_code)
                    val category = location_list_array.getJSONObject(i).getJSONObject("category")
                    val boundaries = location_list_array.getJSONObject(i).getJSONObject("boundaries")
                    val coordinates_array = boundaries.getJSONArray("coordinates")
                    boundary_lat_lng_array.clear()
                    for (l in 0 until coordinates_array.length())
                    {
                        val lat_lng_array = coordinates_array.getJSONArray(l)
                        boundary_lat_lng_array.add(LatLng(lat_lng_array.get(1).toString().toDouble(), lat_lng_array.get(0).toString().toDouble()))
                    }
                    val inBoundary = checkBoundary(pickup_latlng, boundary_lat_lng_array, location_name)
                    val category_array = category.getJSONArray("category_list")
                    if (inBoundary)
                    {
                        var  category_seat_capacity=""
                        estimated_price_array.clear()
                        for (k in 0 until category_array.length())
                        {

                            val category_id = category_array.getJSONObject(k).getString("id")
                            val category_name = category_array.getJSONObject(k).getString("name")
                            if (category_array.getJSONObject(k).has("seating_capacity")){
                                category_seat_capacity = category_array.getJSONObject(k).getString("seating_capacity")

                            }
                            val category_image = category_array.getJSONObject(k).getString("icon_android")
                            val category_is_pool_option = category_array.getJSONObject(k).getString("is_pool_type")
                            category_item_array.add(CategoryItemModel(category_id, category_name, category_seat_capacity, category_image, "00:00:00", currency_symbol, "0.0", "", category_is_pool_option, nearest_driver_arriving_time, cab_avail,"0"))
                            category_item_updated_array.add(CategoryItemModel(category_id, category_name, category_seat_capacity, category_image, "00:00:00", currency_symbol, "0.0", "", category_is_pool_option, nearest_driver_arriving_time, cab_avail,"0"))
                        }
                    }
                }
            }
            var valuesload= LocationFareApiParserResult(isInBoundary,category_item_array,category_item_updated_array,currency_symbol,boundary_lat_lng_array,estimated_price_array,isServiceAvail,current_boundary_name)
            if (listener != null)
                listener!!.locationfareapiresult(valuesload)
        }
        catch (e: Exception)
        {

        }
    }

    fun etacalculation(context:Context, start_latlng: LatLng, end_latlng: LatLng) {
        var list: ArrayList<String>? = null
        list = ArrayList()

        val startLocation = Location("start_location")
        val endLocation = Location("end_location")

        startLocation.latitude = start_latlng.latitude
        startLocation.longitude = start_latlng.longitude
        endLocation.latitude = end_latlng.latitude
        endLocation.longitude = end_latlng.longitude

        var distanceInKm = startLocation.distanceTo(endLocation) / 1000
        val speedInKms = 20.0 / 60.0

        val est_mins = (distanceInKm / speedInKms).toInt()
        val estimatedDriveTimeInSec = (distanceInKm / speedInKms) * 60.0

        val estimated_time = estimatedTimeToReach(estimatedDriveTimeInSec)



        if (listener != null) {
            if (distanceInKm < 1) {
                distanceInKm = distanceInKm * 1000
                val roundmetters = Math.round(distanceInKm).toInt()
                listener!!.estDistance(roundmetters.toString() + " " + context.getString(R.string.meters))
            } else {
                distanceInKm = String.format("%.2f", distanceInKm).toFloat()
                listener!!.estDistance(distanceInKm.toString() + " " + context.getString(R.string.kms))
            }
            if (est_mins.toString().equals("0"))
                listener!!.estTime(context.getString(R.string.nearing))
            else
                listener!!.estTime(est_mins.toString() + " " + context.getString(R.string.minn))

            listener!!.setonhourd(estimated_time)

//
//        }
            // list.add(estimated_time)
            //list.add(est_mins.toString())


        }

    }

    fun estimatedTimeToReach(secs: Double): String {
        val sdf = SimpleDateFormat("hh:mm a")
        val currentDateandTime = sdf.format(Date())
        val date = sdf.parse(currentDateandTime)
        val calendar = Calendar.getInstance()
        calendar.setTime(date)
        calendar.add(Calendar.SECOND, secs.toInt())
        return sdf.format(calendar.time)
    }




    fun estimatedFareCalculation(mContext: Activity,response:String,pickup_latlng:LatLng,estimated_travel_time:Double,estimated_km:Double)
    {
        var boundary_lat_lng_array: ArrayList<LatLng> = ArrayList()
        var estimated_price_array: ArrayList<String> = ArrayList()
        var min_fare_array: ArrayList<Int>? = ArrayList()
        var c_pool_fare_breakup_array: ArrayList<SharePoolFareModel>? = ArrayList()
        var cPoolAvail = false
        var share_pool_double_seat_value = 0.0
        var share_pool_cate_postion: Int = 0
        val response_json_object = JSONObject(response)
        Log.d("fgcfgdg",response)
        try
        {
            val status = response_json_object.get("status")
            var formula_step_array: JSONArray
            if (status.equals("1"))
            {
                val response_object = response_json_object.getJSONObject("response")
                val location_list_array = response_object.getJSONArray("location_list")
                isInBoundary.clear()
                for (i in 0 until location_list_array.length())
                {
                    val location_name = location_list_array.getJSONObject(i).getString("location_name")
                    val category = location_list_array.getJSONObject(i).getJSONObject("category")
                    val peak_time = location_list_array.getJSONObject(i).getString("peak_time")
                    val night_charge = location_list_array.getJSONObject(i).getString("night_charge")

                    val boundaries = location_list_array.getJSONObject(i).getJSONObject("boundaries")
                    val coordinates_array = boundaries.getJSONArray("coordinates")
                    boundary_lat_lng_array.clear()
                    for (l in 0 until coordinates_array.length()) {
                        val lat_lng_array = coordinates_array.getJSONArray(l)
                        boundary_lat_lng_array.add(LatLng(lat_lng_array.get(1).toString().toDouble(), lat_lng_array.get(0).toString().toDouble()))
                    }
                    val inBoundary = checkBoundary(pickup_latlng, boundary_lat_lng_array, location_name)
                    val category_array = category.getJSONArray("category_list")
                    if (inBoundary)
                    {
                        cPoolAvail = false
                        estimated_price_array.clear()
                        min_fare_array?.clear()
                        c_pool_fare_breakup_array?.clear()
                        for (k in 0 until category_array.length()) {
                            var estimated_fare: Double = 0.0
                            var minimum_amout: Double = 0.0
                            var other_charges: Double = 0.0
                            var formula_value_min: String = ""
                            var formula_value_other_charges: String = ""
                            var min_km: Double = 0.0
                            var min_time: Double = 0.0

                            val has_pool_option = category_array.getJSONObject(k).getString("has_pool_option")
                            val is_pool_type = category_array.getJSONObject(k).getString("is_pool_type")

                            val fare_breakup_array = category_array.getJSONObject(k).getJSONArray("fare_breakup")
                            for (fb in 0 until fare_breakup_array.length()) {
                                val title = fare_breakup_array.getJSONObject(fb).getString("title")
                                val key = fare_breakup_array.getJSONObject(fb).getString("key")
                                val value = fare_breakup_array.getJSONObject(fb).getString("value")
                                val unit = fare_breakup_array.getJSONObject(fb).getString("unit")

                                if (key.equals("min_km")) {
                                    min_km = fare_breakup_array.getJSONObject(fb).getString("value").toDouble()
                                } else if (key.equals("min_time")) {
                                    min_time = fare_breakup_array.getJSONObject(fb).getString("value").toDouble()
                                } else if (key.equals("min_fare")) {
                                    if (has_pool_option.equals("1")) {
                                        min_fare_array?.add(fare_breakup_array.getJSONObject(fb).getString("value").toInt())
                                    }
                                } else if (key.equals("single_seat")) {
                                    share_pool_single_seat_value = fare_breakup_array.getJSONObject(fb).getString("value").toDouble()
                                } else if (key.equals("double_seat")) {
                                    share_pool_double_seat_value = fare_breakup_array.getJSONObject(fb).getString("value").toDouble()
                                }
                                if (is_pool_type.equals("1")) {
                                    cPoolAvail = true
                                    c_pool_fare_breakup_array?.add(SharePoolFareModel(title, key, value, unit))
                                }
                            }

                            if (is_pool_type.equals("0")) {
                                val formula_object = category_array.getJSONObject(k).getJSONObject("formula")
                                val sb = StringBuilder()
                                formula_step_array = formula_object.getJSONArray("1")

                                for (st in 0 until formula_step_array.length()) {
                                    var formula_char = formula_step_array.getString(st)
                                    val isSymbol = validateFormula(formula_char)
                                    if (isSymbol == 0) {
                                        if (formula_char.equals("travel_time")) {
                                            if (estimated_travel_time > min_time) {
                                                formula_value_min = (estimated_travel_time - min_time).toString()

                                            } else {
                                                formula_value_min = "0"
                                            }
                                        } else if (formula_char.equals("travel_km")) {
                                            if (estimated_km > min_km) {
                                                formula_value_min = (estimated_km - min_km).toString()

                                            } else {
                                                formula_value_min = "0"
                                            }
                                        } else {
                                            val fare_breakup_array = category_array.getJSONObject(k).getJSONArray("fare_breakup")
                                            for (fb in 0 until fare_breakup_array.length()) {
                                                val key = fare_breakup_array.getJSONObject(fb).getString("key")
                                                if (key.equals(formula_char)) {
                                                    formula_value_min = fare_breakup_array.getJSONObject(fb).getString("value")
                                                    break
                                                } else {
                                                    formula_value_min = "0"

                                                }
                                            }
                                        }
                                    } else {
                                        formula_value_min = formula_char
                                    }
                                    sb.append(formula_value_min)
                                }
                                val min_amout_formula_str = sb.toString()
                                minimum_amout = estimateFareFormulaOutput(min_amout_formula_str).toDouble()


                                val string_builder_other_charges = StringBuilder()
                                if (peak_time.equals("Yes") && night_charge.equals("Yes")) {
                                    formula_step_array = formula_object.getJSONArray("2")
                                    for (st in 0 until formula_step_array.length()) {
                                        var formula_char = formula_step_array.getString(st)
                                        val isSymbol = validateFormula(formula_char)
                                        if (isSymbol == 0) {
                                            if (formula_char.equals("min_amount")) {
                                                formula_value_other_charges = minimum_amout.toString()
                                            } else {
                                                val fare_breakup_array = category_array.getJSONObject(k).getJSONArray("fare_breakup")
                                                for (fb in 0 until fare_breakup_array.length()) {
                                                    val key = fare_breakup_array.getJSONObject(fb).getString("key")
                                                    if (key.equals(formula_char)) {
                                                        formula_value_other_charges = fare_breakup_array.getJSONObject(fb).getString("value")
                                                        break
                                                    } else {
                                                        formula_value_other_charges = "0"
                                                    }
                                                }
                                            }
                                        } else {
                                            formula_value_other_charges = formula_char
                                        }
                                        string_builder_other_charges.append(formula_value_other_charges)
                                    }
                                    val formula_str = string_builder_other_charges.toString()
                                    println("*************************formula_str" + formula_str)
                                    other_charges = estimateFareFormulaOutput(formula_str).toDouble()
                                } else if (peak_time.equals("Yes") || night_charge.equals("Yes")) {
                                    formula_step_array = formula_object.getJSONArray("3")
                                    for (st in 0 until formula_step_array.length()) {
                                        var formula_char = formula_step_array.getString(st)
                                        val isSymbol = validateFormula(formula_char)
                                        if (isSymbol == 0) {
                                            if (formula_char.equals("min_amount")) {
                                                formula_value_other_charges = minimum_amout.toString()
                                            } else if (formula_char.equals("peak_or_night_time_charge")) {
                                                if (peak_time.equals("yes")) {
                                                    formula_char = "peak_time_charge"
                                                } else if (peak_time.equals("yes")) {
                                                    formula_char = "night_charge"
                                                }
                                                for (fb in 0 until fare_breakup_array.length()) {
                                                    val key = fare_breakup_array.getJSONObject(fb).getString("key")
                                                    if (key.equals(formula_char)) {
                                                        formula_value_other_charges = fare_breakup_array.getJSONObject(fb).getString("value")
                                                        break
                                                    } else {
                                                        formula_value_other_charges = "0"
                                                    }
                                                }
                                            } else {
                                                val fare_breakup_array = category_array.getJSONObject(k).getJSONArray("fare_breakup")
                                                for (fb in 0 until fare_breakup_array.length()) {
                                                    val key = fare_breakup_array.getJSONObject(fb).getString("key")
                                                    if (key.equals(formula_char)) {
                                                        formula_value_other_charges = fare_breakup_array.getJSONObject(fb).getString("value")
                                                        break
                                                    } else {
                                                        formula_value_other_charges = "0"
                                                    }
                                                }
                                            }
                                        } else {
                                            formula_value_other_charges = formula_char
                                        }
                                        string_builder_other_charges.append(formula_value_other_charges)
                                    }
                                    val formula_str = string_builder_other_charges.toString()
                                    other_charges = estimateFareFormulaOutput(formula_str).toDouble()
                                } else {
                                    other_charges = 0.0
                                }
                            } else {
                                share_pool_cate_postion = k
                            }
                            estimated_fare = (minimum_amout + other_charges)
                            val df = DecimalFormat("#.##")
                            var estimated_fare_amount= Math.round(estimated_fare * 100).toFloat() / 100
                            estimated_price_array.add(""+estimated_fare_amount)
                            Log.d("defhbvf", estimated_fare.toString())

                        }
                        if (cPoolAvail)
                            estimated_price_array.set(share_pool_cate_postion, cPoolEstimatedFareCalculation(min_fare_array, c_pool_fare_breakup_array).toString())
                    }
                }
            }
            var valuesloaded= EstimateFareApiResult(isInBoundary,boundary_lat_lng_array,estimated_price_array,min_fare_array,c_pool_fare_breakup_array,cPoolAvail,share_pool_double_seat_value,share_pool_cate_postion,share_pool_single_seat_value,c_pool_min_value)

            EventBus.getDefault().post(valuesloaded)
            if (listener != null)
                listener!!.estimatefareapi(valuesloaded)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }






    fun validateFormula(formula: String): Int {
        val letters = formula.toCharArray()
        var sucess: Int = 0
        for (letter in letters) {
            if (letter == '+' || letter == '-' || letter == '(' || letter == ')' || letter == '*' || letter == '/') {
                sucess = 1;
            }
        }
        return sucess

    }

    fun estimateFareFormulaOutput(formula: String): String {
        var output: Object? = null
        try {
            val engine1: ScriptEngine = ScriptEngineManager().getEngineByName("javascript")
            output = engine1.eval(formula) as Object?;
        } catch (e: ScriptException) {
            e.printStackTrace();
        }
        System.out.println("******************************estimateFareFormulaOutput" + output);
        return output.toString()
    }

    private fun cPoolEstimatedFareCalculation(min_fare_array: ArrayList<Int>?, fare_breakup_array: ArrayList<SharePoolFareModel>?): Double {
        c_pool_min_value = getMin(min_fare_array)
        return c_pool_min_value!!.rem(share_pool_single_seat_value)
    }

    // Method for getting the minimum value
    fun getMin(inputArray: ArrayList<Int>?): Int {
        var minValue = 0
        for (i in 0 until inputArray!!.size) {
            minValue = inputArray[0]
            if (inputArray[i] < minValue) {
                minValue = inputArray[i]
            }
        }
        return minValue
    }

    private fun checkBoundary(pickup_latlng: LatLng, boundary_lat_lng_array: ArrayList<LatLng>, location_name: String): Boolean {
        val contain = PolyUtil.containsLocation(pickup_latlng, boundary_lat_lng_array, true)
        isInBoundary.add(contain)
        if (contain) {
            current_boundary_name = location_name
        }
        return contain
    }

    fun getdrivertails(response:String,context:Context)
    {
        try
        {
            var vehicledetailsarraylist = ArrayList<TrackingPageDataModel>()
            val stoplist = ArrayList<stopsModel>()
            var responseObj = JSONObject(response)
            if (responseObj.getString("status").equals("1"))
            {
                if (responseObj.has("response"))
                {

                    var user_id:String = ""
                    var user_name:String = ""
                    var user_email:String = ""
                    var phone_number:String = ""
                    var user_image:String = ""
                    var user_review:String = ""
                    var ride_id:String = ""
                    var pickup_lat:String = ""
                    var pickup_lon:String = ""
                    var pickup_time:String = ""
                    var drop_location:String = ""
                    var drop_lat:String = ""
                    var drop_lon:String = ""
                    var pickup_location:String = ""
                    var drop_loc:String = ""
                    var est_amount:String=""
                    var currency_code:String=""
                    var currency:String = "0"
                    var payment_method:String=""
                    var total_payable_amount:String = "0"
                    var receive_cash="0"

                    var getingres: JSONObject? = responseObj.getJSONObject("response")
                    Log.d("dfjdhfjhjs", getingres.toString())
                    val data = getingres!!.getJSONObject("data")

                    if (data.has("list")) {
                        val list = data.getJSONArray("list")
                        if (list.length() > 0) {
                            for (i in 0 until list.length()) {
                                var j_ride_objectss = list.getJSONObject(i)

                                user_id = j_ride_objectss!!.getString("user_id")

                                Log.d("muser_ids",user_id)
                                user_name = j_ride_objectss!!.getString("user_name")
                                user_email = ""
                                phone_number = j_ride_objectss!!.getString("user_phone")
                                user_image = j_ride_objectss!!.getString("user_image")
                                user_review = j_ride_objectss!!.getString("user_review")
                                ride_id = j_ride_objectss!!.getString("ride_id")
                                total_payable_amount = j_ride_objectss.getString("user_pay_amount")



                                if (j_ride_objectss.has("user_token")) {
                                    var fcmtoken: String = j_ride_objectss!!.getString("user_token")
                                    var mSessionManager: SessionManager? = null
                                    mSessionManager = SessionManager(context!!)
                                    mSessionManager.setUsertripfcmtoken(fcmtoken)
                                }


                                if (j_ride_objectss.has("currency")) {
                                    currency = j_ride_objectss.getString("currency")
                                    var currencySymbolConventer: CurrencySymbolConventer
                                    currencySymbolConventer = CurrencySymbolConventer()
                                    currency_code = currencySymbolConventer.getCurrencySymbol(currency)
                                }
                                if (j_ride_objectss.has("receive_cash")) {
                                    if (j_ride_objectss.getString("receive_cash").equals("1"))
                                        payment_method = context.getString(R.string.cashpay)

                                    receive_cash = j_ride_objectss.getString("receive_cash")
                                }
                                arrayListvalue.clear()
                                arrayList.clear()
                                farearray = j_ride_objectss!!.getJSONArray("fare_details")
                                if (farearray!=null){
                                    if (farearray.length() > 0){
                                        for (k in 0 until farearray.length()){
                                            var j_fare_object = farearray.getJSONObject(k)
                                            arrayList.add(j_fare_object.getString("title"))
                                            arrayListvalue.add(j_fare_object.getString("value"))
                                        }

                                    }
                                }




                                val jarray = j_ride_objectss!!.getJSONArray("locations")
                                if (jarray.length() > 0) {
                                    for (i in 0 until jarray.length()) {
                                        var j_ride_object = jarray.getJSONObject(i)
                                        if (j_ride_object.getString("ref").equals("pickup")) {
                                            pickup_location = j_ride_object.getString("location")
                                            pickup_lat = j_ride_object.getString("lat")
                                            pickup_lon = j_ride_object.getString("lon")
                                            pickup_time = j_ride_object.getString("timestamp")
                                        } else if (j_ride_object.getString("ref").equals("drop")) {
                                            drop_location = j_ride_object.getString("location")
                                            drop_loc = j_ride_object.getString("location")
                                            drop_lat = j_ride_object.getString("lat")
                                            drop_lon = j_ride_object.getString("lon")
                                        }
                                    }
                                }
                                var mSessionManager: SessionManager? = null
                                mSessionManager = SessionManager(context!!)
                                var ridestatus = mSessionManager!!.getdriverstatus()
                                mSessionManager.setpickup_location(pickup_location)
                                mSessionManager.setdrop_location(drop_location)
                                mSessionManager.setmuserid(user_id)
                                savepickupdroplatitude(pickup_lat, pickup_lon, drop_lat, drop_lon, ride_id, context)
                                stoplist.add(stopsModel(1, pickup_location, ""))
                                stoplist.add(stopsModel(2, drop_loc, ""))
                                vehicledetailsarraylist.add(TrackingPageDataModel(user_id, user_name, user_email, phone_number, user_image, user_review, ride_id, pickup_location, pickup_lat, pickup_lon, pickup_time, drop_location, drop_loc, drop_lat, drop_lon, est_amount, currency_code, payment_method, ridestatus, total_payable_amount, receive_cash))

                            }
                        }
                    }else {
                        if (data != null) {
                            user_id = data!!.getString("user_id")
                            user_name = data!!.getString("user_name")
                            user_email = ""
                            phone_number = data!!.getString("user_phone")
                            user_image = data!!.getString("user_image")
                            user_review = data!!.getString("user_review")
                            ride_id = data!!.getString("ride_id")
                            total_payable_amount = data.getString("user_pay_amount")



                            if (data.has("user_token")) {
                                var fcmtoken: String = data!!.getString("user_token")
                                var mSessionManager: SessionManager? = null
                                mSessionManager = SessionManager(context!!)
                                mSessionManager.setUsertripfcmtoken(fcmtoken)
                            }


                            if (data.has("currency")) {
                                currency = data.getString("currency")
                                var currencySymbolConventer: CurrencySymbolConventer
                                currencySymbolConventer = CurrencySymbolConventer()
                                currency_code = currencySymbolConventer.getCurrencySymbol(currency)
                            }
                            if (data.has("receive_cash")) {
                                if (data.getString("receive_cash").equals("1"))
                                    payment_method = context.getString(R.string.cashpay)

                                receive_cash = data.getString("receive_cash")
                            }

                            if(data.has("fare_details")) {
                                arrayListvalue.clear()
                                arrayList.clear()
                                farearray = data!!.getJSONArray("fare_details")
                                if (farearray != null) {
                                    if (farearray.length() > 0) {
                                        for (k in 0 until farearray.length()) {
                                            var j_fare_object = farearray.getJSONObject(k)
                                            arrayList.add(j_fare_object.getString("title"))
                                            arrayListvalue.add(j_fare_object.getString("value"))
                                        }

                                    }
                                }
                            }


                            val jarray = `data`!!.getJSONArray("locations")
                            if (jarray.length() > 0) {
                                for (i in 0 until jarray.length()) {
                                    var j_ride_object = jarray.getJSONObject(i)
                                    if (j_ride_object.getString("ref").equals("pickup")) {
                                        pickup_location = j_ride_object.getString("location")
                                        pickup_lat = j_ride_object.getString("lat")
                                        pickup_lon = j_ride_object.getString("lon")
                                        var mSessionManager: SessionManager? = null
                                        mSessionManager = SessionManager(context!!)
                                        mSessionManager!!.setonlinelongitudeotp(pickup_lon)
                                        mSessionManager!!.setonlinelatitudeotp(pickup_lat)
                                        Log.d("checking5544", pickup_lat)
                                        pickup_time = j_ride_object.getString("timestamp")
                                    } else if (j_ride_object.getString("ref").equals("drop")) {
                                        drop_location = j_ride_object.getString("location")
                                        drop_loc = j_ride_object.getString("location")
                                        drop_lat = j_ride_object.getString("lat")
                                        drop_lon = j_ride_object.getString("lon")
                                    }
                                }
                            }
                            var mSessionManager: SessionManager? = null
                            mSessionManager = SessionManager(context!!)
                            var ridestatus = mSessionManager!!.getdriverstatus()
                            savepickupdroplatitude(pickup_lat, pickup_lon, drop_lat, drop_lon, ride_id, context)
                            mSessionManager.setpickup_location(pickup_location)
                            mSessionManager.setdrop_location(drop_location)
                            stoplist.add(stopsModel(1, pickup_location, ""))
                            stoplist.add(stopsModel(2, drop_loc, ""))
                            vehicledetailsarraylist.add(TrackingPageDataModel(user_id, user_name, user_email, phone_number, user_image, user_review, ride_id, pickup_location, pickup_lat, pickup_lon, pickup_time, drop_location, drop_loc, drop_lat, drop_lon, est_amount, currency_code, payment_method, ridestatus, total_payable_amount, receive_cash))

                        }
                    }
                }
                if (listener != null)
                {
                    listener!!.onDataReceived(vehicledetailsarraylist)
//                    listener!!.onStopsAdapter(stoplist)
                }
            }
        }
        catch (e: JSONException)
        {
        }
    }


    fun getcashresponse(response:String,context:Context)
    {
        try
        {
            var responseObj = JSONObject(response)
            Log.d("cehhffss",response)
            if (responseObj.getString("status").equals("1"))
            {
                if (listener != null)
                {
                    var mSessionManager: SessionManager? = null
                    mSessionManager = SessionManager(context!!)
                    try
                    {
                        mSessionManager!!.settridestatusforcash()
                        mSessionManager!!.settridestatusforcashtwo()

                        listener!!.oncashrecived(1)
                    }
                    catch (e: Exception)
                    {
                    }
                }
            }
            else
            {
                if (listener != null)
                {
                    listener!!.oncashrecived(0)
                }
            }
        }
        catch (e: JSONException)
        {
        }
    }


    fun savepickupdroplatitude(pickup_lat:String,pickup_lon:String,drop_lat:String,drop_lon:String,ride_id:String,context:Context)
    {
        var mSessionManager: SessionManager? = null
        mSessionManager = SessionManager(context!!)
        mSessionManager.setRidepickuplatitude(pickup_lat)
        mSessionManager.setRidepickuplongitude(pickup_lon)
        mSessionManager.setRidedroplatitiude(drop_lat)
        mSessionManager.setRidedroplongitude(drop_lon)
        mSessionManager.setRideid(ride_id)
    }
}
