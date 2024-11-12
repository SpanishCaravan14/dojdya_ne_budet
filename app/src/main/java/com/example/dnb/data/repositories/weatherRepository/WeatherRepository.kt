package com.example.dnb.data.repositories.weatherRepository

import com.example.dnb.models.weatherApi.localDataModels.WeatherInfoLocal
import javax.inject.Inject
import com.example.dnb.data.Result
import com.example.dnb.models.geoApi.localDataModels.GeoInfoLocal
import com.example.dnb.models.weatherApi.localDataModels.WeatherWeekInfoLocal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Singleton

/**
 * Класс-репозиторий с единственным source of truth - weathernetworkdatasource
 * Запускает получение данных о погоде в контексте IO потоков
 * и main-safe возвращает их
 */

@Singleton
class WeatherRepository @Inject constructor(
    val weatherNetworkDataSource: WeatherNetworkDataSource) {

    suspend fun getWeather(llString : String) : Result<WeatherInfoLocal> {
        return withContext(Dispatchers.IO){
            weatherNetworkDataSource.getWeather(llString)
        }
    }
    suspend fun getWeatherWeek(llString: String) : Result<WeatherWeekInfoLocal>{
        return withContext(Dispatchers.IO){
            weatherNetworkDataSource.getWeatherWeek(llString)
        }
    }
    suspend fun getGeo(q : String) : Result<List<GeoInfoLocal>>{
        return withContext(Dispatchers.IO){
            weatherNetworkDataSource.getGeo(q)
        }
    }
}