package com.mauto.chd.view_model_ride_request

class RideRequestDataModel
{
    var rideid: String? = null
    var timestamp: String? = null
    var category: String? = null
    var timer: String? = null
    var rating: String? = null
    var min: String? = null
    var miles: String? = null
    var droppoint: String? = null
    var pickuplat: String? = null
    var pickuplong: String? = null

    constructor(rideid: String, timestamp: String, category: String, timer: String, rating: String, min: String,miles:String,droppoint:String,pickuplat:String,pickuplong:String)
    {
        this.rideid = rideid
        this.timestamp = timestamp
        this.category = category
        this.timer = timer
        this.rating = rating
        this.min = min
        this.miles=miles
        this.droppoint=droppoint
        this.pickuplat=pickuplat
        this.pickuplong=pickuplong
    }
}