package com.dineshm.AutoCompleteWithDB.Pojo

import com.google.gson.annotations.SerializedName

data class AutoCompleteResponcePojo(

    @field:SerializedName("predictions")
    val predictions: List<PredictionsItem>,

    @field:SerializedName("status")
    val status: String
)