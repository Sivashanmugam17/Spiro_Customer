package com.mauto.chd.Modal

class TrackingPojo
{
    var ride_id: String? = null
    var currentLat: String? = null
    var currentLon: String? = null
    var bearingValue: String? = null
    var ridestatus: String? = null


    constructor(ride_id: String, currentLat: String, currentLon: String, bearingValue: String, ridestatus: String)
    {
        this.ride_id = ride_id
        this.currentLat = currentLat
        this.currentLon = currentLon
        this.bearingValue = bearingValue
        this.ridestatus = ridestatus
    }
}