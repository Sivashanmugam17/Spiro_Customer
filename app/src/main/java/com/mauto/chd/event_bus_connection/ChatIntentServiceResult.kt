package com.mauto.chd.event_bus_connection

class ChatIntentServiceResult internal constructor(desc: String, sender_ID: String, timestamp: String, ride_id: String) {


    var desc: String
        internal set
    var sender_ID: String
        internal set
    var timestamp: String
        internal set
    var ride_id: String
        internal set

    init {
        this.desc = desc
        this.sender_ID = sender_ID
        this.timestamp = timestamp
        this.ride_id = ride_id
    }
}