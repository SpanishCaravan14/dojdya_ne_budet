package com.example.dnb.data.repositories.weatherRepository

import com.example.dnb.models.weatherInfo.WeatherInfoLocal
import javax.inject.Inject
import com.example.dnb.data.Result
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
       
   suspend fun getWeather() : Result<WeatherInfoLocal>{
      return withContext(Dispatchers.IO){
           weatherNetworkDataSource.getWeather()
       }
}
}