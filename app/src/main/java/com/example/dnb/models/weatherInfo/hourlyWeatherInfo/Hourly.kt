package com.example.dnb.models.weatherInfo.hourlyWeatherInfo

import com.example.dnb.models.weatherInfo.HourlyWeatherLocal.HourlyLocal
import com.example.dnb.utils.DateFormatter


// Класс содержит списки данных температуры, погодные коды, и соответствующее им время
data class Hourly(
    var temperature_2m: List<Double>,
    val time: List<String>,
    val weather_code: List<Int>
)
fun Hourly.mapToModel() : HourlyLocal{
    val dateFormatter = object : DateFormatter{}

    return HourlyLocal(
    temperature_2m = temperature_2m,
        time = time.map { dateFormatter.convertDateStringToFormattedTime(it) },
        weather_code = weather_code
    )
}