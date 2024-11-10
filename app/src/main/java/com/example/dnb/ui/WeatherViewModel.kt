package com.example.dnb.ui

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dnb.data.repositories.weatherRepository.WeatherRepository
import com.example.dnb.models.weatherInfo.WeatherInfoLocal
import kotlinx.coroutines.launch
import com.example.dnb.data.Result
import com.example.dnb.models.weatherInfo.GeoWeatherModel
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

    private val _locationInfo = MutableLiveData<Location>()
    val locationInfo : LiveData<Location> = _locationInfo

    private val _geoWeatherModel = MutableLiveData<GeoWeatherModel>()
    val geoWeatherModel : LiveData<GeoWeatherModel> = _geoWeatherModel

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

    fun getGeoWeather(geoWeatherModel: GeoWeatherModel){
        if(locationInfo.value!=null){
            viewModelScope.launch{
                val geoWeatherResult = weatherRepository.getGeoWeather(
                    geoWeatherModel
                )
                _weatherResult.value = geoWeatherResult
            }
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
    fun setGeoModel(geocoder: Geocoder, location : Location) {
        viewModelScope.launch{
            var addressList : List<Address> = listOf ()
            val fetchingAddressJob = launch(Dispatchers.IO){
                addressList = geocoder.getFromLocation(location.latitude, location.longitude, 1)!!
            }
            fetchingAddressJob.join()
           _geoWeatherModel.value = GeoWeatherModel(
               ll = listOf<Double>(location.longitude, location.latitude),
               city= addressList[0].locality.toString(),
                   country=addressList[0].countryName.toString(),
               timezone = location.time.toString()
           )
            Log.d("currentLocation", "${location.latitude.toString()} ${location.longitude} ${location.time}")
            Log.d("addresslist", addressList.toString())
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

