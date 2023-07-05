package com.dineshm.AutoCompleteWithDB.Pojo

import com.google.gson.annotations.SerializedName

data class PredictionsItem(

    @field:SerializedName("reference")
    val reference: String,

    @field:SerializedName("types")
    val types: List<String>,

    @field:SerializedName("matched_substrings")
    val matchedSubstrings: List<MatchedSubstringsItem>,

    @field:SerializedName("terms")
    val terms: List<TermsItem>,

    @field:SerializedName("structured_formatting")
    val structuredFormatting: StructuredFormatting,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("place_id")
    val placeId: String
)
