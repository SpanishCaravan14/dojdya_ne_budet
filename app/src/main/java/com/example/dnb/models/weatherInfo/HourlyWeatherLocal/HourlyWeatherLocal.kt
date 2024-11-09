package com.example.dnb.models.weatherInfo.HourlyWeatherLocal

import com.example.dnb.models.weatherInfo.hourlyWeatherInfo.Hourly
import com.example.dnb.models.weatherInfo.hourlyWeatherInfo.HourlyUnits

data class HourlyWeatherLocal(
    val hourly: HourlyLocal,
    val hourly_units: HourlyUnitsLocal
)