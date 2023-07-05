package com.mauto.chd.Modal

import org.json.JSONArray

class VehicleselectionModel
{
    var id: String? = null
    var name: String? = null
    var makeid: String? = null
    var makename: String? = null
    var modelid: String? = null
    var modelname: String? = null
    var category : JSONArray? = null
    var yearslist : JSONArray? = null


    constructor(idd: String, namee: String)
    {
        this.id = idd
        this.name = namee
    }
    constructor(idd: String, namee: String, category: JSONArray){
        this.id = idd
        this.name = namee
        this.category = category
    }
    constructor( makeid: String,makename :String,modelid :String,modelname :String,yearslist :JSONArray){
        this.makeid = makeid
        this.makename = makename
        this.modelid = modelid
        this.modelname = modelname
        this.yearslist = yearslist

    }

}
class VehicleselectionModels(val makeid: String,val makename: String ){

}class VehicleselectionModeltype(val modelid :String,val modelname :String){

}class VehicleselectionModelyear(yearslist : ArrayList<String>){

}
