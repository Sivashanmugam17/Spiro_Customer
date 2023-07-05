package com.mauto.chd.Modal

//import com.google.firebase.database.Exclude

class TextMessage
{
    var rideid: String? = null
    var message: String? = null
    var senderid: String? = null
    var timestamp: String? = null
    var status: String? = null


    constructor(rideid: String,message: String,senderid: String,timestamp: String,status: String)
    {
        this.rideid = rideid
        this.message = message
        this.senderid = senderid
        this.timestamp = timestamp
        this.status = status
    }
}