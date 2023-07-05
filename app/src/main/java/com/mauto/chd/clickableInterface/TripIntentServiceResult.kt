package com.mauto.chd.clickableInterface

class TripIntentServiceResult internal constructor(message: String, jid: String) {

    var result: Int = 0
        internal set
    var message: String
        internal set
    var jid: String
        internal set

    init {
        this.message = message
        this.jid = jid
    }
}