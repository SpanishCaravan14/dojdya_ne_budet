package com.example.dnb.models.weatherInfo.currentWeatherInfo

import com.example.dnb.models.weatherInfo.currentWeatherInfoLocal.CurrentWeatherLocal

/**
 * Класс описывает объект, приходящий с сервера, содержащий данные о текущем состоянии погоды, расширяется методом
 * mapToModel для использования в локальной модели данных
 */
data class CurrentWeather(
    val current: Current,
    val current_units: CurrentUnits
)
fun CurrentWeather.mapToModel() : CurrentWeatherLocal{
    return CurrentWeatherLocal(
        current = current.mapToModel(),
        current_units = current_units.mapToModel()
    )
}
