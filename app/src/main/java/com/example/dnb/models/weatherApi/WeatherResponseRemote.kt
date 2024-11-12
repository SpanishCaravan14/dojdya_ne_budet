package com.example.dnb.models.weatherApi

import com.example.dnb.models.weatherApi.localDataModels.CurrentWeatherLocal
import com.example.dnb.models.weatherApi.localDataModels.HourlyWeatherLocal
import com.example.dnb.models.weatherApi.localDataModels.WeatherInfoLocal
import com.example.dnb.utils.DateFormatter
import com.google.gson.annotations.SerializedName

data class WeatherResponseRemote (
    @SerializedName("location" ) var location : LocationRemote? = LocationRemote(),
    @SerializedName("current"  ) var current  : CurrentRemote?  = CurrentRemote(),
    @SerializedName("forecast" ) var forecast : ForecastRemote? = ForecastRemote()
)

fun WeatherResponseRemote.mapToModel() : WeatherInfoLocal {
    val dateFormatter = object : DateFormatter{}

    val hourlyTemperatureList = mutableListOf<Double>()
    val hourlyTimeList = mutableListOf<String>()
    val hourlyCodeList = mutableListOf<Int>()

    forecast?.forecastday?.forEach {
        day -> day.hour.forEach{
        hourlyTemperatureList.add(it.tempC ?: 0.0)
        hourlyTimeList.add(dateFormatter.convertDateStringToFormattedTime(it.time ?: "2000-01-01T00:01"))
        hourlyCodeList.add(it.condition?.code ?: 99)
    }}

    return WeatherInfoLocal(
        city = location?.name ?: "",
        country = location?.country ?: "",
        localDayTime = location?.localtime ?: "",
        localTime = dateFormatter.convertDateStringToHourString(location?.localtime ?: ""),
        currentWeather = CurrentWeatherLocal(
            pressure_msl = current?.pressureMb ?: 0.0,
            relative_humidity_2m = current?.humidity?.toInt() ?: 0,
            tempC = current?.tempC ?: 0.0,
            tempF = current?.tempF ?: 0.0,
            isDay = current?.isDay == 1,
            weather_code = current?.condition?.code ?: 0,
            wind_speed_10m = current?.windMph ?: 0.0
        ),
        hourlyWeather = HourlyWeatherLocal(
            temperature_2m = hourlyTemperatureList,
            time = hourlyTimeList,
            weather_code = hourlyCodeList
        )
    )
}