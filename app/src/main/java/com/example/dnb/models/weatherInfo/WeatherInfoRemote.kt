package com.example.dnb.models.weatherInfo

import com.example.dnb.models.weatherInfo.currentWeatherInfo.CurrentWeather
import com.example.dnb.models.weatherInfo.currentWeatherInfo.mapToModel
import com.example.dnb.models.weatherInfo.dailyWeatherInfo.DailyWeather
import com.example.dnb.models.weatherInfo.dailyWeatherInfo.mapToModel
import com.example.dnb.models.weatherInfo.hourlyWeatherInfo.HourlyWeather
import com.example.dnb.models.weatherInfo.hourlyWeatherInfo.mapToModel

/**
 * Класс используется для конвертации ответа от сервера с помощью функций-расширений
 */
data class WeatherInfoRemote (val city: String,
                             val country: String,
                             val lat: Double,
                             val long: Double,
                             val current_weather: CurrentWeather,
                             val daily_weather: DailyWeather,
                             val hourly_weather: HourlyWeather)

fun WeatherInfoRemote.mapToModel() : WeatherInfoLocal{
    return WeatherInfoLocal(
        city = city,
        country = country,
        current_weather = current_weather.mapToModel(),
        daily_weather = daily_weather.mapToModel(),
        hourly_weather = hourly_weather.mapToModel(),
    )
}