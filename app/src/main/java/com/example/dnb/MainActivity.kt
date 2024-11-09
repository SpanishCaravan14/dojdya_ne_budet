package com.example.dnb

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dnb.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.cancel
import com.example.dnb.R
import com.example.dnb.ui.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.net.UnknownHostException
import kotlin.getValue
import kotlin.system.exitProcess
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val weatherViewModel: WeatherViewModel by viewModels()

    //
//    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var coroutineExceptionHandler: CoroutineExceptionHandler
//    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    private var settingsUpdated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weatherViewModel.getWeather()
        val splashScreen = installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)

//        sharedPreferencesHelper = SharedPreferencesHelper(this)


        val noInternetLiveData : MutableLiveData<Boolean> =  MutableLiveData(false)

        coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
            if(throwable is UnknownHostException) {
                noInternetLiveData.postValue(true)
            }
        }

        noInternetLiveData.observe(this) {noInternet ->
            if(noInternet) {
                Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show()
                Thread.sleep(1000)
            //выход из процесса если нет интернета?
            // exitProcess(0)
            }
        }



            /*        val settingsModel = SharedPreferencesHelper(this).getSettings()
        if(settingsModel?.isMusicOn != false) {
            val startMusicIntent = Intent(this, WeatherMusicService::class.java)
            startService(startMusicIntent)
        }

        fetchData()
        splashScreen.setKeepOnScreenCondition { (weatherViewModel.weatherLiveData.value == null) }
*/

        setContentView(binding.root)
    }

    override fun onStart() {

        super.onStart()

    }
   /* private fun fetchData() {
        val weatherService = RetrofitHelper.getInstance().create(WeatherService::class.java)
        val weatherRepository = WeatherRepository(weatherService)
        weatherViewModel = ViewModelProvider(this, WeatherViewModelFactory(weatherRepository))[WeatherViewModel::class.java]

        val geocodingData = sharedPreferencesHelper.getGeocodingData()

        if(weatherViewModel.weatherLiveData.value == null) {
            if(geocodingData == null) {
                weatherViewModel.getWeather(coroutineExceptionHandler)
            } else {
                weatherViewModel.getGeoWeather(coroutineExceptionHandler, geocodingData)
            }
        }

        weatherViewModel.weatherLiveData.observe(this) { weatherData ->
            if(weatherData == null) {
                Toast.makeText(this, getString(R.string.api_fetching_error), Toast.LENGTH_SHORT).show()
                Thread.sleep(1000)
                exitProcess(0)
            } else {
                if(!settingsUpdated) {
                    createLocalDB(weatherData)
                }
            }
        }
    }
*/
   /* private fun createLocalDB(weatherData: WeatherData) {
        val currentSettings = sharedPreferencesHelper.getSettings()
        if (currentSettings != null) {
            val weatherHelper = WeatherHelper(currentSettings, weatherData)
            val newWeatherData = weatherHelper.convertWeatherData()

            if(newWeatherData != weatherData) {
                settingsUpdated = true
                weatherViewModel.updateWeatherData(newWeatherData)
            }
        }
    }
*/
    override fun onDestroy() {
        super.onDestroy()
    //    weatherViewModel.viewModelScope.cancel("ActivityDestroying")
    }
}