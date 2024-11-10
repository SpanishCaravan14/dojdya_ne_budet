package com.example.dnb.data.repositories.weatherRepository

import android.util.Log
import com.example.dnb.data.Result
import com.example.dnb.data.api.WeatherService
import com.example.dnb.models.weatherInfo.WeatherInfoLocal
import java.io.IOException
import javax.inject.Inject
import com.example.dnb.R
import com.example.dnb.models.weatherInfo.GeoWeatherModel
import com.example.dnb.models.weatherInfo.mapToModel
import javax.inject.Singleton

@Singleton
class WeatherNetworkDataSource @Inject constructor(val weatherService: WeatherService){

    /**
     * Функция получает данные без сведений местоположении
     */
    suspend fun getWeather() : Result<WeatherInfoLocal>{

       try{

           val result = weatherService.getWeather()
           if(result.isSuccessful && result.body() != null) {
               try {
                   result.body()!!.mapToModel()
               }catch (e : Exception){
                   Result.Error(exception = IOException("${R.string.error_fetching_weather}"))
               }
                return Result.Success<WeatherInfoLocal>(data = result.body()!!.mapToModel())
               }
           else return Result.Error(exception = IOException("${R.string.error_fetching_weather}"))

       }catch (exception : Exception){
           return Result.Error(exception = exception)
       }
    }

    /**
     * Получает данные с использованием сведений о местоположении
     */
    suspend fun getGeoWeather(geoWeatherModel : GeoWeatherModel) : Result<WeatherInfoLocal>{

        try{
            val result = weatherService.getGeoWeather(geoWeatherModel)
            Log.d("result", result.body().toString())
            if(result.isSuccessful && result.body() != null) {
                try {
                    result.body()!!.mapToModel()
                }catch (e : Exception){
                    Result.Error(exception = IOException("${R.string.error_fetching_weather}"))
                }
                return Result.Success<WeatherInfoLocal>(data = result.body()!!.mapToModel())
            }
            else return Result.Error(exception = IOException("${R.string.error_fetching_weather}"))

        }catch (exception : Exception){
            return Result.Error(exception = exception)
        }
    }
}