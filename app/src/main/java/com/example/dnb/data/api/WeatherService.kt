package com.example.dnb.data.api;

import com.example.dnb.models.weatherInfo.GeoWeatherModel
import com.example.dnb.models.weatherInfo.WeatherInfoLocal;
import com.example.dnb.models.weatherInfo.WeatherInfoRemote

import retrofit2.Response;
import retrofit2.http.Body
import retrofit2.http.POST;

//Интерфейс с запросами для получения данных о погоде

interface WeatherService  {

    @POST("/api/weather")
    suspend fun getWeather() : Response<WeatherInfoRemote>

    @POST("/api/weather")
    suspend fun getGeoWeather(@Body geoWeatherModel: GeoWeatherModel) : Response<WeatherInfoRemote>

}