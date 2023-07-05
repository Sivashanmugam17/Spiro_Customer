package com.mauto.chd.viewmodelfortriplist

import android.content.Context
import android.location.Location
import android.util.Log
import com.mauto.chd.Modal.ridelistsummaryModel
import com.mauto.chd.commonutils.CurrencySymbolConventer
import com.google.android.gms.maps.model.LatLng
import io.realm.Realm
import org.json.JSONArray
import java.util.*


class ridedetailrepostiatry(private val listener: ridedetailspageListener)
{

    val realm by lazy { Realm.getDefaultInstance() }
    lateinit var currencySymbolConventer: CurrencySymbolConventer



    fun serachrideiddata(mContext: Context, rideid: String)
    {
        val results = realm.where(tripdetailsrealmm::class.java).equalTo("ride_id", rideid).findAll()
        for (recentfields in results)
        {
            Log.d("dsfdsfgcxsctdfg", recentfields.toString())


            val driver_name = recentfields.driver_name.toString()
            val driver_image = recentfields.driver_image.toString()
            val vehile_maker_model = recentfields.category.toString()
            val vehicle_number = recentfields.vehicle_number.toString()
            val avg_review =recentfields.avg_review.toString()
            val category =recentfields.category.toString()

            listener!!.drivername(driver_name)
            listener!!.driverimage(driver_image)
            listener!!.driverrating(avg_review)
            listener!!.drivervehicletype(category)
            listener!!.drivervehiclenumber(vehicle_number)

            val cancellationreason = recentfields.cancelreason.toString()

            val pickup_address_txt1 =recentfields.pickuplocation.toString()
            val drop_address_txt1 =recentfields.droplocation.toString()
            val payment_method =recentfields.payment_typeuser.toString()
            var picktime =recentfields.timestamppickup.toString()
            var droptimed =recentfields.timestampdrop.toString()
            val grand_fare =recentfields.grand_fare.toString()
            val invoice_src =recentfields.invoice_src.toString()
            val pick_time =recentfields.pickup_time.toString()
            val drop_time =recentfields.drop_time.toString()
            val ride_distance =recentfields.ride_distance.toString()


            Log.d("fdsrwe5cd4w", picktime + "---" + droptimed)
//            val i: Int = picktime.toInt()
//            val m: Int = droptimed.toInt()
            val now = Date()
//            var timestamp = i
//            var sdf = SimpleDateFormat("HH:mm")
//            var dateStr = sdf.format(timestamp)
//            Log.d("dr4w56354", dateStr)
//            var timestamps = m
//            var sdfs = SimpleDateFormat("HH:mm")
//            var dateStrs = sdfs.format(timestamps)
            if (recentfields.pick_lat.equals("")){

            }else {


                var pick_lat = recentfields.pick_lat.toString()
                var pick_long = recentfields.pick_long.toString()
                var drop_lat = recentfields.drop_lat.toString()
                var drop_long = recentfields.drop_long.toString()
                Log.d("dfsdddrftg", pick_lat + "---" + pick_long)
                var distancestring = ""

                var picklatdouble: Double = pick_lat.toDouble()
                var picklongdouble: Double = pick_long.toDouble()

                var drop_lat_double: Double = drop_lat.toDouble()
                var drop_long_double: Double = drop_long.toDouble()

                var latLngA = LatLng(picklatdouble, picklongdouble)
                var latLngB = LatLng(drop_lat_double, drop_long_double)

                var locationA = Location("point A")
                locationA.latitude = latLngA.latitude
                locationA.longitude = latLngA.longitude
                var locationB = Location("point B")
                locationB.latitude = latLngB.latitude
                locationB.longitude = latLngB.longitude
                var distance = (locationA.distanceTo(locationB).toDouble() / 1000).toDouble()

                val distanceInMeters: Float = locationA.distanceTo(locationB)
                Log.d("distanceInMeters_1", distanceInMeters.toString())
                var distcal: Int = ((distanceInMeters / 1000) + 1).toInt()
                var distankm = distanceInMeters / 1000
                var distanceg = distance

                if (distanceInMeters > 1000) {
                    var distankm = distanceInMeters / 1000
                    Log.d("distanceInMeters_55", distankm.toString())
                    distancestring = distankm.toString().substring(0, 3)
//            distancestring = distancestring.substring(0, 4)+" Km"


                } else {
                    (distanceInMeters / 1000).toInt()
                    Log.d("yhdfdgdgdydyd", distanceInMeters.toString())

                    distancestring = distanceg.toString()
                    distancestring = distancestring.substring(0, 3)


                    Log.d("distanceInMeters_201", distancestring.toString())
                    Log.d("dfcgr55sdddrftg", distance.toString())

                }
                listener!!.ridekm(ride_distance.toString())


            }
//            var distance =""




            listener!!.pickup_address_txt(pickup_address_txt1)
            listener!!.drop_address_txt(drop_address_txt1)





            if(droptimed.equals(""))
            {
                val cancelled_time =recentfields.cancelled_time.toString()
                droptimed = cancelled_time
            }

            listener!!.cancelreason(cancellationreason)
            listener!!.pickuptime(pick_time)
            listener!!.droptime(drop_time)

            val units =recentfields.units.toString()
            Log.d("dsfdsfgtdfg", units)





            val currency = units
            currencySymbolConventer = CurrencySymbolConventer()

            var currency_symbol = currencySymbolConventer.getCurrencySymbol(currency)

            val driverrevenue =recentfields.payment_method.toString()
            if(!driverrevenue.equals(""))
            {
                listener!!.fundriverrevenue(currency_symbol + driverrevenue)
            }

            listener!!.paidamount(grand_fare)

            listener!!.symbl(currency_symbol)
            listener!!.paymentmethod(payment_method)

            val fare_summary =recentfields.fare_summary.toString()
            val farelist = ArrayList<ridelistsummaryModel>()
            val fare_summaryarray = JSONArray(fare_summary)

            for (j in 0 until fare_summaryarray.length())
            {
                val job = fare_summaryarray.getJSONObject(j)
                val title = job.getString("title").toString()
                val value = job.getString("value").toString()
                farelist.add(ridelistsummaryModel(1, title, currency_symbol + value))
            }
            listener!!.farebreakup(farelist)


            if(!invoice_src.equals(""))
            {
                listener!!.mapimagesave(invoice_src)
            }
        }
    }

}