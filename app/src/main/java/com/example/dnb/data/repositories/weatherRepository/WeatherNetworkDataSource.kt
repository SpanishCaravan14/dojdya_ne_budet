package com.example.dnb.data.repositories.weatherRepository

import android.util.Log
import com.example.dnb.data.Result
import com.example.dnb.data.api.WeatherService
import com.example.dnb.models.weatherApi.localDataModels.WeatherInfoLocal
import java.io.IOException
import javax.inject.Inject
import com.example.dnb.R
import com.example.dnb.data.api.GeoService
import com.example.dnb.data.api.WeatherWeekService
import com.example.dnb.models.geoApi.GeoRemote
import com.example.dnb.models.geoApi.localDataModels.GeoInfoLocal
import com.example.dnb.models.geoApi.mapToModel
import com.example.dnb.models.weatherApi.localDataModels.WeatherWeekInfoLocal
import com.example.dnb.models.weatherApi.localDataModels.WeatherWeekResponseRemote
import com.example.dnb.models.weatherApi.localDataModels.mapToModel
import com.example.dnb.models.weatherApi.mapToModel
import javax.inject.Singleton

/**
 * Вероятнее всего, следует создать отдельный класс  GeoNetworkDataSource, но для простоты они объединены
 */
@Singleton
class WeatherNetworkDataSource @Inject constructor(val weatherService: WeatherService,
                                                    val geoService : GeoService,
                                                    val weatherWeekService : WeatherWeekService){

    /**
     * Получает данные с использованием сведений о местоположении
     */

    suspend fun getWeather(llString : String) : Result<WeatherInfoLocal>{
        try{
            val result = weatherService.getWeather("b8108d9657a445b198584754241111", llString, 2)
            Log.d("result", result.toString())
                try {
                    return Result.Success<WeatherInfoLocal>(data = result.mapToModel())
                }catch (e : Exception) {
                    return Result.Error(exception = IOException("${R.string.error_fetching_weather}"))
                }
        }catch (exception : Exception){
            Log.d("exception", exception.message.toString())
            return Result.Error(exception = exception)

        }
    }
//Получает данные на неделю
    suspend fun getWeatherWeek(llString : String) : Result<WeatherWeekInfoLocal>{
        try{
            val result : WeatherWeekResponseRemote = weatherWeekService.getWeatherWeek("b8108d9657a445b198584754241111", llString, 8)
            Log.d("result", result.toString())
            try {
                return Result.Success<WeatherWeekInfoLocal>(data = result.mapToModel())
            }catch (e : Exception) {
                return Result.Error(exception = IOException("${R.string.error_fetching_weather}"))
            }
        }catch (exception : Exception){
            Log.d("exception", exception.message.toString())
            return Result.Error(exception = exception)
        }
    }

    suspend fun getGeo(q : String) : Result<List<GeoInfoLocal>>{
        try{
            val resultRemote : ArrayList<GeoRemote> = geoService.getGeo("b8108d9657a445b198584754241111", q)
            Log.d("resultRemote", resultRemote.toString())
            try {
                return Result.Success<List<GeoInfoLocal>>(data = resultRemote.map { it.mapToModel() })
            }catch (e : Exception) {
                return Result.Error(exception = IOException("${R.string.error_fetching_weather}"))
            }
        }catch (exception : Exception){
            Log.d("exception", exception.message.toString())
            return Result.Error(exception = exception)
        }
    }

}