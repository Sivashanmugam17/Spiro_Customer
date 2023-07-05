package com.mauto.chd.view_model_trip_detail

class TripDataModel
{
    var ride_id: String? = null
    var ride_time: String? = null
    var ride_date: String? = null
    var grand_fare: String? = null
    var driver_revenue: String? = null
    var map_image: String? = null
    var currency_code: String? = null
    var pickup: String? = null
    var group: String? = null
    var datetime: String? = null

    constructor(ride_id: String, ride_time: String, ride_date: String, grand_fare: String, driver_revenue: String, map_image: String,currency_code:String,pickup:String,group:String,datetime:String)
    {
        this.ride_id = ride_id
        this.ride_time = ride_time
        this.ride_date = ride_date
        this.grand_fare = grand_fare
        this.driver_revenue = driver_revenue
        this.map_image = map_image
        this.currency_code=currency_code
        this.pickup=pickup
        this.group=group
        this.datetime=datetime
    }
}