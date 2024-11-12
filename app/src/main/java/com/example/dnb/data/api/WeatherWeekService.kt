package com.example.dnb.data.api

import com.example.dnb.models.weatherApi.WeatherResponseRemote
import com.example.dnb.models.weatherApi.localDataModels.WeatherWeekResponseRemote
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface WeatherWeekService {
    @FormUrlEncoded
    @POST("forecast.json")
    suspend fun getWeatherWeek(
        @Field ("key") key : String,
        @Field ("q") q : String,
        @Field ("days") days : Int
    ) : WeatherWeekResponseRemote
}