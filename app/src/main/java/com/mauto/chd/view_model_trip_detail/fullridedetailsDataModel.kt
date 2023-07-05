package com.mauto.chd.view_model_trip_detail

class fullridedetailsDataModel
{
    var user_name: String? = null
    var user_id: String? = null
    var user_email: String? = null
    var phone_number: String? = null
    var user_image: String? = null
    var user_review: String? = null
    var pickup_location: String? = null
    var pickup_time: String? = null
    var drop_loc: String? = null
    var drop_time: String? = null
    var firstamount: String? = null
    var secondamount: String? = null
    var currency_code: String? = null
    var payment_method: String? = null
    var cab_type: String? = null
    var grand_fare: String? = null
    var ride_date: String? = null
    var distance_unit: String? = null
    var ride_distance: String? = null


    constructor(user_name: String, user_id: String, user_email: String, phone_number: String, user_image: String, user_review: String,pickup_location:String,pickup_time:String,drop_loc:String,drop_time:String,firstamount:String,secondamount:String,currency_code:String,payment_method:String,cab_type:String,grand_fare:String,ride_date:String,distance_unit:String,ride_distance:String)
    {
        this.user_id = user_id
        this.user_name = user_name
        this.user_email = user_email
        this.phone_number = phone_number
        this.user_image = user_image
        this.user_review = user_review

        this.pickup_location = pickup_location
        this.pickup_time = pickup_time
        this.drop_loc = drop_loc
        this.drop_time = drop_time
        this.firstamount = firstamount
        this.secondamount = secondamount
        this.currency_code=currency_code
        this.payment_method = payment_method
        this.cab_type = cab_type
        this.grand_fare = grand_fare
        this.ride_date = ride_date

        this.distance_unit = distance_unit
        this.ride_distance = ride_distance
    }
}