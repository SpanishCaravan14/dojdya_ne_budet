package com.example.dnb.models.weatherApi

import com.google.gson.annotations.SerializedName

data class ConditionRemote (

    @SerializedName("text" ) var text : String? = null,
    @SerializedName("code" ) var code : Int?    = null

)