package com.example.dnb.data

import com.example.dnb.data.api.WeatherService
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

    @Provides
    fun providesBaseUrl() : String = "https://weathernaut-backend.onrender.com"

    @Provides
    @Singleton
    fun provideRetrofit(BASE_URL : String) : Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherService(retrofit : Retrofit) : WeatherService = retrofit.create(WeatherService::class.java)

}