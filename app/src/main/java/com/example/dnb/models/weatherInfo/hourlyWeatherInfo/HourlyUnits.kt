package com.example.dnb.models.weatherInfo.hourlyWeatherInfo

import com.example.dnb.models.weatherInfo.HourlyWeatherLocal.HourlyUnitsLocal
import com.example.dnb.utils.DateFormatter

data class HourlyUnits(
    var temperature_2m: String,
    val time: String,
    val weather_code: String
)
fun HourlyUnits.mapToModel() : HourlyUnitsLocal{
    val dateFormatter = object : DateFormatter{}

    return HourlyUnitsLocal(
        temperature_2m = temperature_2m,
        time = time,
        weather_code = weather_code
    )
}


