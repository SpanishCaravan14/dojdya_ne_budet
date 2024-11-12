package com.example.dnb

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.MutableLiveData
import com.example.dnb.databinding.ActivityMainBinding
import com.example.dnb.ui.WeatherViewModel
import com.example.dnb.utils.NetworkStatusLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val weatherViewModel: WeatherViewModel by viewModels()

    //LocationClient библиотеки GoogleService
    private lateinit var fusedLocationClient: FusedLocationProviderClient

//    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    private lateinit var networkStatusLiveData: NetworkStatusLiveData
    val noInternetLiveData: MutableLiveData<Boolean> = MutableLiveData(false)


    private var settingsUpdated = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        networkStatusLiveData = NetworkStatusLiveData(this)

        binding = ActivityMainBinding.inflate(layoutInflater)

//        sharedPreferencesHelper = SharedPreferencesHelper(this)
        /**При создании активити приложение подписывается на изменения
         * LiveData с данными о статусе интернет-соединения
         * Затем, если проверяет наличие разрешения на получение данных о приблизительном местоположении устройства
         * если его нет, запрашивает, если есть, пытается получить местоположение, асинхронно запускает во ViewModel
         * установку местоположения, подписывается на его обновление, при
         */

        networkStatusLiveData.observe(this) { internetConnection ->
            if (!internetConnection) {
                Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show()
                Log.d("error i nactivity", "internet, ${internetConnection.toString()}")
            }else {
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
                                    weatherViewModel.locationInfo.observe(this) { newLocationInfo ->
                                        weatherViewModel.getWeather()
                                        //   weatherViewModel.setGeoModel(Geocoder(this), newLocationInfo)
                                    }
                                }
                                else -> {
                                    //Если разрешения не предоставлено
                                    //   weatherViewModel.getWeather()
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
                            weatherViewModel.getWeather()
                            //  weatherViewModel.setGeoModel(Geocoder(this), newLocationInfo)
                        }
                    }

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

        override fun onDestroy() {
            super.onDestroy()
            //    weatherViewModel.viewModelScope.cancel("ActivityDestroying")
        }

        fun startConnectionManager(){
             }
    }
