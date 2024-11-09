package com.example.dnb.models.weatherInfo.dailyWeatherInfo


import com.example.dnb.models.weatherInfo.dailyWeatherInfoLocal.DailyUnitsLocal
import com.example.dnb.utils.DateFormatter

data class DailyUnits(
    val sunrise: String,
    val sunset: String,
    var temperature_2m_max: String,
    var temperature_2m_min: String,
    val time: String,
    val weather_code: String
)
fun DailyUnits.mapToModel() : DailyUnitsLocal{
    val dateFormatter = object : DateFormatter{}

    return DailyUnitsLocal(
        sunrise = sunrise,
        sunset = sunset,
        temperature_2m_max = temperature_2m_max,
        temperature_2m_min = temperature_2m_min,
        time = time,
        weather_code = weather_code
    )
}