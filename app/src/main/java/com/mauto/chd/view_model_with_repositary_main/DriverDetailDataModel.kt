package com.mauto.chd.view_model_with_repositary_main

class DriverDetailDataModel
{
    var driverid: String? = null
    var drivername: String? = null
    var driverimage: String? = null
    var vehicleno: String? = null
    var vehicletype: String? = null
    var driverrating: String? = null
    var email: String? = null
    var CategoryName: String? = null
    var currencysymboll: String? = null
    var locationmode: String? = null
    var mobileno: String? = null


    constructor(driverid: String, drivername: String, driverimage: String, vehicleno: String, vehicletype: String, driverrating: String,email:String,CategoryName:String,currencysymboll:String,locationmode:String,mobileno:String)
    {
        this.driverid = driverid
        this.drivername = drivername
        this.driverimage = driverimage
        this.vehicleno = vehicleno
        this.vehicletype = vehicletype
        this.driverrating = driverrating
        this.email=email
        this.CategoryName=CategoryName
        this.currencysymboll=currencysymboll
        this.locationmode=locationmode
        this.mobileno=mobileno

    }
}