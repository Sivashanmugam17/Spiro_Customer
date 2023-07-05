package com.dineshm.AutoCompleteWithDB.Pojo

import com.google.gson.annotations.SerializedName

data class TermsItem(

    @field:SerializedName("offset")
    val offset: Int,

    @field:SerializedName("value")
    val value: String
)