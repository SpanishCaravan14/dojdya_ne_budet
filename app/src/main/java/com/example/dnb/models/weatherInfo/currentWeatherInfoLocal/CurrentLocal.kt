package com.example.dnb.models.weatherInfo.currentWeatherInfoLocal

data class CurrentLocal (
    var pressure_msl: Double,
    val relative_humidity_2m: Int,
    var temperature_2m: Double,
    val time: String,
    val weather_code: Int,
    var wind_speed_10m: Double
)