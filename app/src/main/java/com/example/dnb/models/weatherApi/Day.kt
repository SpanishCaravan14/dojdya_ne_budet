package com.example.dnb.models.weatherApi

import com.example.dnb.models.weatherApi.localDataModels.DayLocal
import com.google.gson.annotations.SerializedName

data class Day (

    @SerializedName("maxtemp_c"            ) var maxtempC          : Double?    = null,
    @SerializedName("maxtemp_f"            ) var maxtempF          : Double?    = null,
    @SerializedName("mintemp_c"            ) var mintempC          : Double?    = null,
    @SerializedName("mintemp_f"            ) var mintempF          : Double?    = null,
    @SerializedName("avgtemp_c"            ) var avgtempC          : Double?    = null,
    @SerializedName("avgtemp_f"            ) var avgtempF          : Double?    = null,
    @SerializedName("daily_will_it_rain"   ) var dailyWillItRain   : Int?       = null,
    @SerializedName("daily_chance_of_rain" ) var dailyChanceOfRain : Int?       = null,
    @SerializedName("daily_will_it_snow"   ) var dailyWillItSnow   : Int?       = null,
    @SerializedName("daily_chance_of_snow" ) var dailyChanceOfSnow : Int?       = null,
    @SerializedName("condition"            ) var condition         : ConditionRemote? = ConditionRemote()

)
fun Day.mapToModel(dayOfWeek : Int) : DayLocal{
    return DayLocal(
        dayOfWeekCode = dayOfWeek,
        maxtempC = maxtempC?.toInt() ?: 0,
        mintempC = mintempC?.toInt() ?: 0,
        dailyWillItRain = dailyWillItRain ?: 0,
        dailyChanceOfRain = dailyChanceOfRain ?: 0,
        dailyWillItSnow = dailyWillItSnow ?: 0,
        dailyChanceOfSnow = dailyChanceOfSnow ?: 0,
        condition = condition ?: ConditionRemote("", 1000)
    )
}
