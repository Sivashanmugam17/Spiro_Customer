package com.dineshm.AutoCompleteWithDB.Pojo

import com.google.gson.annotations.SerializedName

data class StructuredFormatting(

    @field:SerializedName("main_text_matched_substrings")
    val mainTextMatchedSubstrings: List<MainTextMatchedSubstringsItem>,

    @field:SerializedName("secondary_text")
    val secondaryText: String,

    @field:SerializedName("main_text")
    val mainText: String
)