package com.mauto.chd.Modal

class DriverBasicData
{
    var driverid: String? = null
    var driverimage: String? = null
    var driverfirstname: String? = null
    var driverlastname: String? = null
    var driveremailid: String? = null
    var driverdob: String? = null
    var drivergender: String? = null
    var driveraddress: String? = null
    var drivercountry: String? = null
    var driverstate: String? = null
    var drivercity: String? = null
    var driverzipcode: String? = null
    var mobilecode: String? = null
    var mobilenumber: String? = null


    constructor(driverid: String,driverimage: String,driverfirstname: String,driverlastname: String,driveremailid: String,driverdob: String,drivergender: String,driveraddress: String,drivercountry: String,driverstate: String,drivercity: String,driverzipcode: String,mobilecode:String,mobilenumber: String)
    {
        this.driverid = driverid
        this.driverimage = driverimage
        this.driverfirstname = driverfirstname
        this.driverlastname = driverlastname
        this.driveremailid = driveremailid
        this.driverdob = driverdob
        this.drivergender = drivergender
        this.driveraddress = driveraddress
        this.drivercountry = drivercountry
        this.driverstate = driverstate
        this.drivercity = drivercity
        this.driverzipcode = driverzipcode
        this.mobilecode = mobilecode
        this.mobilenumber = mobilenumber
    }
}