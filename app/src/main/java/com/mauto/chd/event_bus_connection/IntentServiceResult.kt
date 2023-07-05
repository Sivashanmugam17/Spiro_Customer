package com.mauto.chd.event_bus_connection

class IntentServiceResult internal constructor(resultCode: Int, resultValue: String, apiName: String) {

    var result: Int = 0
        internal set
    var resultValue: String
        internal set
    var apiName: String
        internal set

    init {
        result = resultCode
        this.resultValue = resultValue
        this.apiName = apiName
    }
}