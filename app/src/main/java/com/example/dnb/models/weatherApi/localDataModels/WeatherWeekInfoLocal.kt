package com.example.dnb.models.weatherApi.localDataModels

import com.example.dnb.models.weatherApi.Day

data class WeatherWeekInfoLocal(
    val tomorrowSunrise : String,
    val tomorrowSunset : String,
    val dayForecastList : List<DayLocal>
)
