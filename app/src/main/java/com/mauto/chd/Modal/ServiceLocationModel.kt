package com.mauto.chd.Modal

class ServiceLocationModel
{
    var slcode: String? = null
    var slname: String? = null


    constructor(code: String, name: String)
    {
        this.slcode = code
        this.slname = name
    }
}