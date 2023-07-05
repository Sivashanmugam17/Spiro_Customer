package com.mauto.chd.Modal

import android.graphics.Bitmap

class CountryselectionModel
{
    var countrycode: String? = null
    var countryname: String? = null
    var flag: Bitmap? = null
    var countryshortname: String? = null

    constructor(code: String, name: String, flagstring: Bitmap, countryshortnamestring: String)
    {
        this.countrycode = code
        this.countryname = name
        this.flag = flagstring
        this.countryshortname = countryshortnamestring
    }
}