package com.mauto.chd.earningsviewmodel

class WeekModel
{
    var weeks: String? = null
    var netfare: String? = null
    var tripscount: String? = null


    constructor(weeks: String, netfare: String, tripscount: String)
    {
        this.weeks = weeks
        this.netfare = netfare
        this.tripscount = tripscount
    }
}