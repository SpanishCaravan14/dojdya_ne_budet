package com.example.dnb.data.api

import com.example.dnb.models.weatherApi.WeatherResponseRemote
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

//Интерфейс с запросами для получения данных о погоде

interface WeatherService  {

    @FormUrlEncoded
    @POST("forecast.json")
    suspend fun getWeather(
        @Field ("key") key : String,
        @Field ("q") q : String,
        @Field ("days") days : Int
    ) : WeatherResponseRemote
}