package com.mauto.chd.Modal

class tripdetailsModel
{
    var pickupdate: String? = null
    var pickuptime: String? = null
    var amount: String? = null
    var vehicletype: String? = null
    var paymentmode: String? = null
    var status: String? = null
    var invoice_src: String? = null
    var ride_status: String? = null
    var ride_id: String? = null
    var booking_date_time: String? = null
    var timestampid: String? = null



    constructor(pickupdate: String, pickuptime: String, amount: String, vehicletype: String, paymentmode: String, status: String,invoice_src:String,ride_status:String,ride_id:String,booking_date_time:String,timestampid:String)
    {
        this.pickupdate = pickupdate
        this.pickuptime = pickuptime
        this.amount = amount
        this.vehicletype = vehicletype
        this.paymentmode = paymentmode
        this.status = status
        this.invoice_src = invoice_src
        this.ride_status = ride_status
        this.ride_id = ride_id
        this.booking_date_time = booking_date_time
        this.timestampid = timestampid
    }
}