package com.mauto.chd.Modal

class vehicleinfoedit
{
    var vehicle_id: String? = null
    var vehicle_number: String? = null
    var typeid: String? = null
    var typename: String? = null
    var makeid: String? = null
    var makename: String? = null
    var modelid: String? = null
    var modelname: String? = null
    var year: String? = null
    var vehicleimage: String? = null
    var locationid: String? = null
    var locationname: String? = null
    var categoryid: String? = null
    var categoryname: String? = null


    constructor(vehicle_id: String,vehicle_number: String,typeid: String,typename: String,makeid: String,makename: String,modelid: String,modelname: String,year: String,vehicleimage: String,locationid: String,locationname: String,categoryid: String,categoryname: String)
    {
        this.vehicle_id = vehicle_id
        this.vehicle_number = vehicle_number
        this.typeid = typeid
        this.typename = typename
        this.makeid = makeid
        this.makename = makename
        this.modelid = modelid
        this.modelname = modelname
        this.vehicleimage = vehicleimage
        this.locationid = locationid
        this.locationname = locationname
        this.categoryid = categoryid
        this.categoryname = categoryname
    }
}