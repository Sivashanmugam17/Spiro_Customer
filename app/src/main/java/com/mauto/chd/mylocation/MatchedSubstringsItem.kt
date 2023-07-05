package com.dineshm.AutoCompleteWithDB.Pojo

import com.google.gson.annotations.SerializedName

data class MatchedSubstringsItem(

    @field:SerializedName("offset")
    val offset: Int,

    @field:SerializedName("length")
    val length: Int
)