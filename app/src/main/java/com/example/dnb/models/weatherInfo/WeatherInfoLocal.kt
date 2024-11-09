package com.example.dnb.models.weatherInfo

import com.example.dnb.models.weatherInfo.HourlyWeatherLocal.HourlyWeatherLocal
import com.example.dnb.models.weatherInfo.currentWeatherInfoLocal.CurrentWeatherLocal
import com.example.dnb.models.weatherInfo.dailyWeatherInfo.DailyWeather
import com.example.dnb.models.weatherInfo.dailyWeatherInfoLocal.DailyWeatherLocal
import com.example.dnb.models.weatherInfo.hourlyWeatherInfo.HourlyWeather

/**
 * Класс используется для описания модели погодных данных, использующихся в дальнейшем после получения ответа от сервера
 * (данные сервреа приводятся к нужному виду в DataSource классе)
 */
data class WeatherInfoLocal (val city: String,
                             val country: String,
                            // val lat: Double,
                            // val long: Double,
                             val current_weather: CurrentWeatherLocal,
                             val daily_weather: DailyWeatherLocal,
                             val hourly_weather: HourlyWeatherLocal)