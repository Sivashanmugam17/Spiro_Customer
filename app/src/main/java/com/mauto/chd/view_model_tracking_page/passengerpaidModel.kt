package com.mauto.chd.view_model_tracking_page

class  passengerpaidModel
{
    var title: String? = null
    var value: String? = null
    var positive: String? = null

    constructor( title: String, value: String,positive:String)
    {
        this.title = title
        this.value = value
        this.positive = positive
    }
}