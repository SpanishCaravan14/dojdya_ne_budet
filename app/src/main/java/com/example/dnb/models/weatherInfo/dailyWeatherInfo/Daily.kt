package com.example.dnb.models.weatherInfo.dailyWeatherInfo

import com.example.dnb.models.weatherInfo.dailyWeatherInfoLocal.DailyLocal
import com.example.dnb.utils.DateFormatter

data class Daily(
    val sunrise: List<String>,
    val sunset: List<String>,
    var temperature_2m_max: List<Double>,
    var temperature_2m_min: List<Double>,
    val time: List<String>,
    val weather_code: List<Int>
)
fun Daily.mapToModel() : DailyLocal{
    val dateFormatter = object : DateFormatter{}

    return DailyLocal(
        sunrise = sunrise.map{dateFormatter.convertDateStringToFormattedTime(it)},
        sunset = sunset.map{dateFormatter.convertDateStringToFormattedTime(it)},
        temperature_2m_max = temperature_2m_max,
        temperature_2m_min = temperature_2m_min,
        time = time.map{dateFormatter.convertDateStringToFormattedTime(it)},
        weather_code = weather_code
    )
}