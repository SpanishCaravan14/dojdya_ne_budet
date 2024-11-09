package com.example.dnb.models.weatherInfo.hourlyWeatherInfo

import com.example.dnb.models.weatherInfo.HourlyWeatherLocal.HourlyWeatherLocal


//Класс содержит модель, описывающую погоду в конкретный час, а также содержит список
data class HourlyWeather(
    val hourly: Hourly,
    val hourly_units: HourlyUnits
)
fun HourlyWeather.mapToModel() : HourlyWeatherLocal{
    return HourlyWeatherLocal(
        hourly = hourly.mapToModel(),
        hourly_units = hourly_units.mapToModel()
    )
}