package com.example.dnb.ui

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dnb.data.repositories.weatherRepository.WeatherRepository
import com.example.dnb.models.weatherApi.localDataModels.WeatherInfoLocal
import kotlinx.coroutines.launch
import com.example.dnb.data.Result
import com.example.dnb.models.geoApi.localDataModels.GeoInfoLocal
import com.example.dnb.models.weatherApi.localDataModels.WeatherWeekInfoLocal
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

/**
ViewModel для хранения UI-state, используемого в фрагменте HomeFragment
 При создании класса внедряется зависимость класса weatherRepository
 Для возможности внедрения зависимостей Dagger Hilt помечается аннотацией @HiltViewModel
*/
@HiltViewModel
class WeatherViewModel @Inject constructor(val weatherRepository : WeatherRepository): ViewModel() {

    private val _weatherResult = MutableLiveData<Result<WeatherInfoLocal>>()
    val weatherResult : LiveData<Result<WeatherInfoLocal>> = _weatherResult

    private val _weatherWeekResult = MutableLiveData<Result<WeatherWeekInfoLocal>>()
    val weatherWeekResult : LiveData<Result<WeatherWeekInfoLocal>> = _weatherWeekResult

    private val _locationInfo = MutableLiveData<Location>()
    val locationInfo : LiveData<Location> = _locationInfo

    private val _geoResult = MutableLiveData<Result<List<GeoInfoLocal>>>()
    val geoResult : LiveData<Result<List<GeoInfoLocal>>> = _geoResult

    /**
     * В coroutine scope viewModelScope
     * (учитывает жизненный цикл viewModel и отменит выполнение функции если viewModel перестанет существовать)
     * запускается функция по получению данных о погоде, значение присваивается MutableLiveData объекту
     */

    fun getWeather(){
        viewModelScope.launch{
            val llString = "${locationInfo.value!!.latitude} ${locationInfo.value!!.longitude}"
            Log.d("llString", llString)
            val weatherResult =  weatherRepository.getWeather(llString)
            _weatherResult.value = weatherResult

            val weatherWeekResult = getWeatherWeek(llString)
            _weatherWeekResult.value = weatherWeekResult
            Log.d("weather weekResult", weatherWeekResult.toString())

        }
    }
    //Метод используется, когда пользователь сам выбирает интересующий его город из списка
    fun getGeoWeather(llString : String){
        viewModelScope.launch{
            val weatherResult = weatherRepository.getWeather(llString)
            Log.d("weatherres", weatherResult.toString())
            _weatherResult.value = weatherResult

            val weatherWeekResult = getWeatherWeek(llString)
            _weatherWeekResult.value = weatherWeekResult
        }
    }
    suspend fun getWeatherWeek(llString: String) : Result<WeatherWeekInfoLocal>{
        return weatherRepository.getWeatherWeek(llString)
    }
    fun getGeo(q : String){
        viewModelScope.launch{
            val geoResult = weatherRepository.getGeo(q)
            _geoResult.value = geoResult
        }
    }


    /**
     * Функция для получения данных о местоположении через сервис Google
     * Игнорируем запрос на разрешения, т.к. они проверяются при вызове
     */

    @SuppressLint("MissingPermission")
    fun setCurrentLocation(fusedLocationClient: FusedLocationProviderClient) {
        viewModelScope.launch {
            //Так как используется примерное местоположение, не нужно высокого приоритета
            val priority = Priority.PRIORITY_BALANCED_POWER_ACCURACY
            val currentLocationJob=
                async(Dispatchers.IO){
                    fusedLocationClient.getCurrentLocation(
                        priority,
                        CancellationTokenSource().token,
                    )
                }
            currentLocationJob.await().addOnSuccessListener{taskResult ->
                taskResult?.let{_locationInfo.value = taskResult!!}
            }
        }
    }

    }

//
//    fun getGeoWeather(coroutineExceptionHandler: CoroutineExceptionHandler, geoWeatherModel: me.tangobee.weathernaut.models.GeoWeatherModel) {
//        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
//            me.tangobee.weathernaut.data.repository.WeatherRepository.getGeoWeather(geoWeatherModel)
//        }
//    }
//
//    fun updateWeatherData(weatherData: me.tangobee.weathernaut.models.WeatherData.WeatherData) {
//        me.tangobee.weathernaut.data.repository.WeatherRepository.updateWeatherData(weatherData)
//    }
//
//    val weatherLiveData : LiveData<me.tangobee.weathernaut.models.WeatherData.WeatherData?>
//        get() = me.tangobee.weathernaut.data.repository.WeatherRepository.weatherData

