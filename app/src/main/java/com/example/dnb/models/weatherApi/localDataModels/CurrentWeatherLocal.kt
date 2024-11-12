package com.example.dnb.models.weatherApi.localDataModels

data class CurrentWeatherLocal (
    val pressure_msl: Double,
    val relative_humidity_2m: Int,
    val tempC: Double,
    val tempF : Double,
    val isDay : Boolean,
    val weather_code: Int,
    var wind_speed_10m: Double,

)