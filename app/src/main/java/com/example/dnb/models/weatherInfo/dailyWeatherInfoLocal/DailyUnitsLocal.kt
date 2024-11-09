package com.example.dnb.models.weatherInfo.dailyWeatherInfoLocal

data class DailyUnitsLocal(
    val sunrise: String,
    val sunset: String,
    var temperature_2m_max: String,
    var temperature_2m_min: String,
    val time: String,
    val weather_code: String
)