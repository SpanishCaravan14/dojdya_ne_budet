package com.example.dnb.models.weatherInfo.currentWeatherInfo
import com.example.dnb.models.weatherInfo.currentWeatherInfoLocal.CurrentUnitsLocal
import com.example.dnb.utils.DateFormatter


data class CurrentUnits(
    val interval: String,
    var pressure_msl: String,
    val relative_humidity_2m: String,
    var temperature_2m: String,
    val time: String,
    val weather_code: String,
    var wind_speed_10m: String
)
fun CurrentUnits.mapToModel() : CurrentUnitsLocal{
    val dateFormatter = object : DateFormatter{}
    return CurrentUnitsLocal(
        pressure_msl = pressure_msl,
        relative_humidity_2m = relative_humidity_2m,
        temperature_2m = temperature_2m,
        time = time,
        weather_code = weather_code,
        wind_speed_10m = wind_speed_10m
    )
}
