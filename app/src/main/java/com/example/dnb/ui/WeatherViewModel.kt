package com.example.dnb.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dnb.data.repositories.weatherRepository.WeatherRepository
import com.example.dnb.models.weatherInfo.WeatherInfoLocal
import kotlinx.coroutines.launch
import com.example.dnb.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
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

    /**
     * В coroutine scope viewModelScope
     * (учитывает жизненный цикл viewModel и отменит выполнение функции если viewModel перестанет существовать)
     * запускается функция по получению данных о погоде, значение присваивается MutableLiveData объекту
     */
    fun getWeather(){
        viewModelScope.launch{
            val weatherResult = weatherRepository.getWeather()
            _weatherResult.value = weatherResult
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

