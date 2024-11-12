package com.example.dnb.constants
import com.example.dnb.R
/**
 *Вспомогательный класс для получения ресурс-кодов иконок, соответствующих погодному коду
 * English Condition list URL (XML): https://www.weatherapi.com/docs/weather_conditions.xmldd
 *TODO = Добавить обработку вечернего времени суток и присвоение иконок
 */

class WeatherImageMapper {
        companion object {
            fun getImageForWeatherCode(weatherCode: Int): Int {
                return when (weatherCode) {
                    1000, 1003 -> R.drawable.icon_weather_sun
                    1006 -> R.drawable.icon_weather_sun_cloud
                    1030, 1135, 143, 248 -> R.drawable.icon_weather_cloud_fog
                    in 1063..1087, in 176..200 -> R.drawable.icon_weather_rain_cloud
                    in 1180..1201, in 293..314 -> R.drawable.icon_weather_rain_cloud
                    in 1240..1245, in 353..358 -> R.drawable.icon_weather_rain_cloud

                    1114, 1117, 1279, 1282, 227, 230, 392, 395 -> R.drawable.icon_weather_snow_cloud
                    in 1204..1237, in 317..350 -> R.drawable.icon_weather_snow_cloud
                    in 1255..1264, in 368..377-> R.drawable.icon_weather_snow_cloud

                    80, 81, 82 -> R.drawable.icon_weather_sun_cloud_rain
                    1246, in 1273..1282, 359, in 386..395 -> R.drawable.icon_weather_thunderstorm_cloud
                    else -> R.drawable.icon_weather_cloud
                }
            }
        }

}