package com.example.dnb.models.weatherApi.localDataModels

/**
 * Класс используется для описания модели погодных данных, использующихся в дальнейшем после получения ответа от сервера
 * (данные сервреа приводятся к нужному виду в DataSource классе)
 */
data class WeatherInfoLocal (val city: String,
                             val country: String,
                             val localDayTime : String,
                             /**
                              * Строка в формате "HH:00"
                              */
                             val localTime : String,
                             val currentWeather: CurrentWeatherLocal,
                             val hourlyWeather: HourlyWeatherLocal
                             )