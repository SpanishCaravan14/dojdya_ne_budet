package com.example.dnb.data

import com.example.dnb.data.api.GeoService
import com.example.dnb.data.api.WeatherService
import com.example.dnb.data.api.WeatherWeekService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**Dagger Hilt модуль, доступный для всего приложения, поставляет:
 * 1) синглтон ретрофит-клиента
 * 2) синглтон класса для получения информации о погоде

 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Provides
//    fun providesBaseUrl() : BASE_URL = "https://api.weatherapi.com/v1/"

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.weatherapi.com/v1/")
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherService(retrofit : Retrofit) : WeatherService = retrofit.create(WeatherService::class.java)

    @Provides
    @Singleton
    fun provideGeoService(retrofit : Retrofit) : GeoService = retrofit.create(GeoService::class.java)

    @Provides
    @Singleton
    fun provideWeatherWeekService(retrofit : Retrofit) : WeatherWeekService = retrofit.create(WeatherWeekService::class.java)
}