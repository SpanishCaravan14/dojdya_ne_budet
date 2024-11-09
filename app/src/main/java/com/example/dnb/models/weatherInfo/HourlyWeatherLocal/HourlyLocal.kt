package com.example.dnb.models.weatherInfo.HourlyWeatherLocal

data class HourlyLocal(
    val temperature_2m: List<Double>,
    val time: List<String>,
    val weather_code: List<Int>
)
