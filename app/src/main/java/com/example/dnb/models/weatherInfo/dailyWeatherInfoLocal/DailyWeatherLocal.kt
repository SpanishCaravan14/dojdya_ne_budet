package com.example.dnb.models.weatherInfo.dailyWeatherInfoLocal

import com.example.dnb.models.weatherInfo.dailyWeatherInfo.Daily
import com.example.dnb.models.weatherInfo.dailyWeatherInfo.DailyUnits

data class DailyWeatherLocal(
    val daily: DailyLocal,
    val daily_units: DailyUnitsLocal
)