package com.example.dnb.models.weatherApi.localDataModels

data class HourlyWeatherLocal(
    val temperature_2m: List<Double>,
    val time: List<String>,
    val weather_code: List<Int>
)
