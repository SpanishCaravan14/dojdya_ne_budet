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
            1000 to "${R.string.clear}",
            113 to "${R.string.clear}",

            1003 to "${R.string.clear_partly}",
            116 to "${R.string.clear_partly}",

            1006 to "${R.string.partly_cloudy}",
            119 to "${R.string.partly_cloudy}",

            1009 to "${R.string.overcast}",
            122 to "${R.string.overcast}",

            1030 to "${R.string.fog}",
            143 to "${R.string.fog}",

            1135 to "${R.string.fog}",
            248 to "${R.string.fog}",

            1147 to "${R.string.rime_fog}",
            260 to "${R.string.rime_fog}",

            1150 to "${R.string.light_drizzle}",
            263 to "${R.string.light_drizzle}",

            1153 to "${R.string.light_drizzle}",
            266 to "${R.string.light_drizzle}",

            53 to "${R.string.moderate_drizzle}",
            55 to "${R.string.heavy_drizzle}",

            1072 to "${R.string.light_freezing_drizzle}",
            185 to "${R.string.light_freezing_drizzle}",
            1168 to "${R.string.light_freezing_drizzle}",
            281 to "${R.string.light_freezing_drizzle}",
            1171 to "${R.string.light_freezing_drizzle}",
            284 to "${R.string.light_freezing_drizzle}",


            1087 to "${R.string.slight_thunderstorm}",
            57 to "${R.string.heavy_freezing_drizzle}",

            1180 to "${R.string.light_rain}",
            293 to "${R.string.light_rain}",
            1183 to "${R.string.light_rain}",
            296 to "${R.string.light_rain}",

            1186 to "${R.string.moderate_rain}",
            299 to "${R.string.moderate_rain}",
            1189 to "${R.string.moderate_rain}",
            302 to "${R.string.moderate_rain}",

            1192 to "${R.string.heavy_rain}",
            305 to "${R.string.heavy_rain}",
            1195 to "${R.string.heavy_rain}",
            305 to "${R.string.heavy_rain}",

            1198 to "${R.string.light_freezing_rain}",
            311 to "${R.string.light_freezing_rain}",

            1201 to "${R.string.heavy_freezing_rain}",
            314 to "${R.string.heavy_freezing_rain}",

            1204 to "${R.string.sleet}",
            317 to "${R.string.sleet}",
            1207 to "${R.string.sleet}",
            320 to "${R.string.sleet}",

            1210 to "${R.string.light_snow}",
            323 to "${R.string.light_snow}",
            1323 to "${R.string.light_snow}",
            326 to "${R.string.light_snow}",

            1216 to "${R.string.moderate_snow}",
            329 to "${R.string.moderate_snow}",
            1219 to "${R.string.moderate_snow}",
            332 to "${R.string.moderate_snow}",

            1114 to "${R.string.heavy_snow}",
            1117 to "${R.string.heavy_snow}",
            1222 to "${R.string.heavy_snow}",
            335 to "${R.string.heavy_snow}",
            1225 to "${R.string.heavy_snow}",
            338 to "${R.string.heavy_snow}",

            1237 to "${R.string.ice_pellets}",
            350 to "${R.string.ice_pellets}",

            77 to "${R.string.snow_grains}",

            1240 to "${R.string.light_rain_showers}",
            353 to "${R.string.light_rain_showers}",

            1243 to "${R.string.moderate_rain_showers}",
            1243 to "${R.string.moderate_rain_showers}",

            1246 to "${R.string.heavy_rain_showers}",
            359 to "${R.string.heavy_rain_showers}",

           1249 to "${R.string.light_sleet_shower}",
            362 to "${R.string.light_sleet_shower}",
            1261 to "${R.string.light_sleet_shower}",
            374 to "${R.string.light_sleet_shower}",


            1252 to "${R.string.moderate_sleet_shower}",
            365 to "${R.string.moderate_sleet_shower}",
            1264 to "${R.string.moderate_sleet_shower}",
            377 to "${R.string.moderate_sleet_shower}",

            1255 to "${R.string.light_snow_showers}",
            368 to "${R.string.light_snow_showers}",

            1258 to "${R.string.heavy_snow_showers}",
            371 to "${R.string.heavy_snow_showers}",

            1273 to "${R.string.slight_thunderstorm}",
            386 to "${R.string.slight_thunderstorm}",

            1276 to "${R.string.light_show_thunder}",
            389 to "${R.string.light_show_thunder}",

            1282 to "${R.string.heavy_show_thunder}",
            395 to "${R.string.heavy_show_thunder}"
        )

        fun getWeatherDescriptionResourceId(code: Int): Int {
            return Integer.parseInt(weatherConstantsMap[code]?: "${R.string.wrong_weather_code}")
        }
    }
}