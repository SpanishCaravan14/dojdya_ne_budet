package com.example.dnb.models.weatherApi

import com.google.gson.annotations.SerializedName

data class ForecastRemote (

    @SerializedName("forecastday" ) var forecastday : ArrayList<ForecastDay> = arrayListOf()

)