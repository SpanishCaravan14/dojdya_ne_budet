package com.example.dnb.models.weatherInfo.dailyWeatherInfo

import com.example.dnb.models.weatherInfo.dailyWeatherInfoLocal.DailyWeatherLocal

data class DailyWeather(
    val daily: Daily,
    val daily_units: DailyUnits
)
fun DailyWeather.mapToModel() : DailyWeatherLocal{
    return DailyWeatherLocal(
        daily = daily.mapToModel(),
        daily_units = daily_units.mapToModel()
    )
}