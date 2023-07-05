package com.mauto.chd.view_model_tracking_page

class TrackingPageDataModel
{
    var user_id: String? = null
    var user_name: String? = null
    var user_email: String? = null
    var phone_number: String? = null
    var user_image: String? = null
    var user_review: String? = null
    var ride_id: String? = null
    var pickup_location: String? = null
    var pickup_lat: String? = null
    var pickup_lon: String? = null
    var pickup_time: String? = null
    var drop_location: String? = null
    var drop_loc: String? = null
    var drop_lat: String? = null
    var drop_lon: String? = null

    var est_amount: String? = null
    var currency_code: String? = null
    var payment_method: String? = null
    var receive_cash: String? = "0"

    var ride_status: String? = null
    var tripamount: String? = null

    constructor(user_id: String, user_name: String, user_email: String, phone_number: String, user_image: String, user_review: String,ride_id:String,pickup_location:String,pickup_lat:String,pickup_lon:String,pickup_time:String,drop_location:String,drop_loc:String,drop_lat:String,drop_lon:String,est_amount:String,currency_code:String,payment_method:String,ride_status:String,tripamount:String,receive_cash:String)
    {
        this.user_id = user_id
        this.user_name = user_name
        this.user_email = user_email
        this.phone_number = phone_number
        this.user_image = user_image
        this.user_review = user_review
        this.ride_id=ride_id
        this.pickup_location=pickup_location
        this.pickup_lat=pickup_lat
        this.pickup_lon=pickup_lon
        this.pickup_time=pickup_time
        this.drop_location=drop_location
        this.drop_loc=drop_loc
        this.drop_lat=drop_lat
        this.drop_lon=drop_lon
        this.tripamount=tripamount
        this.est_amount    =est_amount
        this.currency_code =currency_code
        this.payment_method =payment_method
        this.ride_status =ride_status
        this.receive_cash = receive_cash

    }
}