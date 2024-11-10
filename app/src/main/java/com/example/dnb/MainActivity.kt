package com.example.dnb

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.MutableLiveData
import com.example.dnb.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import com.example.dnb.ui.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import java.net.UnknownHostException
import kotlin.getValue

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val weatherViewModel: WeatherViewModel by viewModels()

    //LocationClient библиотеки GoogleService и переменная для хранения данных о местоположении
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var coroutineExceptionHandler: CoroutineExceptionHandler
//    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    private var settingsUpdated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)

//        sharedPreferencesHelper = SharedPreferencesHelper(this)


        val noInternetLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

        coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            if (throwable is UnknownHostException) {
                noInternetLiveData.postValue(true)
            }
        }

        noInternetLiveData.observe(this) { noInternet ->
            if (noInternet) {
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
        /**При создании активити приложение проверяет наличие разрешения на
         * получение данных о приблизительном местоположении устройства
         * если его нет, запрашивает, если есть, пытается получить местоположение
         * затем устанавливает в viewmodel локацию и создает геомодель
         * геомодель используется для отправки запроса на сервер
         */
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val locationPermissionRequest = registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                when {
                    permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                        weatherViewModel.setCurrentLocation(fusedLocationClient)
                        weatherViewModel.locationInfo.observe(this) { locationInfoChange ->
                            weatherViewModel.setGeoModel(Geocoder(this), locationInfoChange)
                        }
                    }
                    else -> {
                        //Если разрешения не предоставлено
                        weatherViewModel.getWeather()
                    }
                }
            }
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
        //Если приложению уже предоставлено разрешение
        else {
            weatherViewModel.setCurrentLocation(fusedLocationClient)
            weatherViewModel.locationInfo.observe(this) { newLocationInfo ->
                weatherViewModel.setGeoModel(Geocoder(this), newLocationInfo)
            }
        }
        //С учетом жизненного цикла активити создается подписка на объект геомодели
        //Если она изменяется, происходит запрос на сервер с новой геомоделью
        weatherViewModel.geoWeatherModel.observe(this){
            newGeoWeatherModel ->
            weatherViewModel.getGeoWeather(newGeoWeatherModel)
        }
        setContentView(binding.root)

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
