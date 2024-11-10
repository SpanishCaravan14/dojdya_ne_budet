package com.example.dnb.models.weatherInfo


/**
 * Модель для описания запроса к API с использованием данных о местоположении
 */
data class GeoWeatherModel(
    val ll: List<Double>,
    val city: String,
    val country: String,
    val timezone: String
)
