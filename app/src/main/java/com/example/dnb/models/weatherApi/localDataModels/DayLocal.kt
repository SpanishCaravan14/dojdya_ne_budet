package com.example.dnb.models.weatherApi.localDataModels

import com.example.dnb.models.weatherApi.ConditionRemote

data class DayLocal(
    val dayOfWeekCode : Int,
    val maxtempC : Int,
    val mintempC          : Int,
    val dailyWillItRain : Int,
    val dailyChanceOfRain : Int,
    val dailyWillItSnow : Int,
    val dailyChanceOfSnow : Int,
    val condition : ConditionRemote
)
