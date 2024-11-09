package com.example.dnb.models.weatherInfo.currentWeatherInfo

import com.example.dnb.models.weatherInfo.currentWeatherInfoLocal.CurrentLocal
import com.example.dnb.utils.DateFormatter

data class Current(
    val interval: Int,
    var pressure_msl: Double,
    val relative_humidity_2m: Int,
    var temperature_2m: Double,
    val time: String,
    val weather_code: Int,
    var wind_speed_10m: Double
)
fun Current.mapToModel() : CurrentLocal{
    val dateFormatter = object : DateFormatter{}
    return CurrentLocal(
        pressure_msl = pressure_msl,
        relative_humidity_2m = relative_humidity_2m,
        temperature_2m = temperature_2m,
        time = dateFormatter.convertISO8601ToCustomDateFormat(time),
        weather_code = weather_code,
        wind_speed_10m = wind_speed_10m
    )
}