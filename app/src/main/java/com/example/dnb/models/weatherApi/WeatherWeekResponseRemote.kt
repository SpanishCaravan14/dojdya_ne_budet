package com.example.dnb.models.weatherApi.localDataModels

import com.example.dnb.models.weatherApi.CurrentRemote
import com.example.dnb.models.weatherApi.ForecastRemote
import com.example.dnb.models.weatherApi.LocationRemote
import com.example.dnb.models.weatherApi.mapToModel
import com.example.dnb.utils.DateFormatter
import com.google.gson.annotations.SerializedName


data class WeatherWeekResponseRemote(
    @SerializedName("location" ) var location : LocationRemote? = LocationRemote(),
    @SerializedName("current"  ) var current  : CurrentRemote?  = CurrentRemote(),
    @SerializedName("forecast" ) var forecast : ForecastRemote? = ForecastRemote()
)
fun WeatherWeekResponseRemote.mapToModel() : WeatherWeekInfoLocal{
    val dateFormatter = object : DateFormatter{}
    val dayForecastList =  forecast?.forecastday!!.map{it.day!!.mapToModel(dateFormatter.convertDateStringToDayOfWeekCode(it.date ?:"2000-01-01"))}
    return WeatherWeekInfoLocal(
        tomorrowSunrise = forecast?.forecastday?.get(1)?.astro?.sunrise ?: "",
        tomorrowSunset = forecast?.forecastday?.get(1)?.astro?.sunset ?: "",
        dayForecastList = dayForecastList
    )

}