package com.example.dnb.data.repositories.weatherRepository

import android.util.Log
import com.example.dnb.data.Result
import com.example.dnb.data.api.WeatherService
import com.example.dnb.models.weatherInfo.WeatherInfoLocal
import java.io.IOException
import javax.inject.Inject
import com.example.dnb.R
import com.example.dnb.models.weatherInfo.mapToModel
import javax.inject.Singleton

@Singleton
class WeatherNetworkDataSource @Inject constructor(val weatherService: WeatherService){

    suspend fun getWeather() : Result<WeatherInfoLocal>{

       try{

           val result = weatherService.getWeather()
Log.d("result", result.body().toString())
           if(result.isSuccessful && result.body() != null) {
               try {
                   result.body()!!.mapToModel()
               }catch (e : Exception){
                   Log.d("eror", e.toString())
               }
                   return Result.Success<WeatherInfoLocal>(data = result.body()!!.mapToModel())
               }
           else return Result.Error(exception = IOException("${R.string.error_fetching_weather}"))

       }catch (exception : Exception){
           return Result.Error(exception = exception)
       }
    }
}