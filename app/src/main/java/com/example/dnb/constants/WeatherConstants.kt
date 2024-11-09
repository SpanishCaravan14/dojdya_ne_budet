package com.example.dnb.constants

import com.example.dnb.R


/**
 * Класс, который хранит карту с описанием для соответствующих погодных кодов,
 * значения для упрощения локализации
 * берет из ресурсов
 * Если в карте не хранится значения для погодного года, возвращает пустую строку
 */
class WeatherConstants {
    companion object {
        val weatherConstantsMap: Map<Int, String> = mapOf(
            0 to "${R.string.clear}",
            1 to "${R.string.clear_partly}",
            2 to "${R.string.partly_cloudy}",
            3 to "${R.string.overcast}",
            45 to "${R.string.fog}",
            48 to "${R.string.rime_fog}",
            51 to "${R.string.light_drizzle}",
            53 to "${R.string.moderate_drizzle}",
            55 to "${R.string.heavy_drizzle}",
            56 to "${R.string.light_freezing_drizzle}",
            57 to "${R.string.heavy_freezing_drizzle}",
            61 to "${R.string.light_rain}",
            63 to "${R.string.moderate_rain}",
            65 to "${R.string.heavy_rain}",
            66 to "${R.string.light_freezing_rain}",
            67 to "${R.string.heavy_freezing_rain}",
            71 to "${R.string.light_snow}",
            73 to "${R.string.moderate_snow}",
            75 to "${R.string.heavy_snow}",
            77 to "${R.string.snow_grains}",
            80 to "${R.string.light_rain_showers}",
            81 to "${R.string.moderate_rain_showers}",
            82 to "${R.string.heavy_rain_showers}",
            85 to "${R.string.light_snow_showers}",
            86 to "${R.string.heavy_snow_showers}",
            95 to "${R.string.slight_thunderstorm}",
            96 to "${R.string.moderate_thunderstorm}",
            99 to "${R.string.heavy_thunderstorm}",
        )

        fun getWeatherDescriptionResourceId(code: Int): Int {
            return Integer.parseInt(weatherConstantsMap[code]?: "${R.string.wrong_weather_code}")
        }
    }
}