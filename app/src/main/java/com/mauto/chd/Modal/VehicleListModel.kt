package com.mauto.chd.Modal

class VehicleListModel
{
    var vehicle_number: String? = null
    var vehicle_maker: String? = null
    var vehicle_model: String? = null
    var is_default: String? = null
    var verify_status: String? = null
    var vehicle_id: String? = null


    constructor(vehicle_number: String,vehicle_maker: String,vehicle_model: String,is_default: String,verify_status: String,vehicle_id: String)
    {
        this.vehicle_number = vehicle_number
        this.vehicle_maker = vehicle_maker
        this.vehicle_model = vehicle_model
        this.is_default = is_default
        this.verify_status = verify_status
        this.vehicle_id = vehicle_id
    }
}