package com.example.dnb.models.weatherInfo.dailyWeatherInfoLocal

data class DailyLocal(
    val sunrise: List<String>,
    val sunset: List<String>,
    var temperature_2m_max: List<Double>,
    var temperature_2m_min: List<Double>,
    val time: List<String>,
    val weather_code: List<Int>
)