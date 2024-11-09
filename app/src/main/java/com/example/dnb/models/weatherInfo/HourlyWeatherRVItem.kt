package com.example.dnb.models.weatherInfo

/**
 * Класс описывает экземпляр данных о погоде на 1 час для использования в Recycler View
 */
data class HourlyWeatherRVItem(val time: String, val weatherIcon: Int, val weatherTemp: String, val isCurrent : Boolean)
