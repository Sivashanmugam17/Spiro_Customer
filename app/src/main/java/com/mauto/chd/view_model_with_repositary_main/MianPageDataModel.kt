package com.mauto.chd.view_model_with_repositary_main

class MianPageDataModel
{
    var driverid: String? = null
    var drivername: String? = null
    var driverimage: String? = null
    var vehicleno: String? = null
    var vehicletype: String? = null
    var driverrating: String? = null
    var map_icon: String? = null

    constructor(driverid: String, drivername: String, driverimage: String, vehicleno: String, vehicletype: String, driverrating: String,map_icon:String)
    {
        this.driverid = driverid
        this.drivername = drivername
        this.driverimage = driverimage
        this.vehicleno = vehicleno
        this.vehicletype = vehicletype
        this.driverrating = driverrating
        this.map_icon=map_icon
    }
}