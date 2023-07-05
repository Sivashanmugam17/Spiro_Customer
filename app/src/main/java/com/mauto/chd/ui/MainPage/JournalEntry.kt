package com.mauto.chd.ui.MainPage


/*@IgnoreExtraProperties*/
data class JournalEntry(
     val message: String? = null,
     val receiver_id: String? = null){
    /*@Exclude*/
    fun toMap(): Map<String, Any?> {
        return mapOf(
                "message" to message,
                "receiver_id" to receiver_id
        )
    }
}