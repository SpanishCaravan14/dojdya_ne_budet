package com.example.dnb.models.weatherApi

import com.google.gson.annotations.SerializedName

data class CurrentRemote (

    @SerializedName("temp_c"             ) var tempC            : Double?    = null,
    @SerializedName("temp_f"             ) var tempF            : Double?    = null,
    @SerializedName("is_day"             ) var isDay            : Int?       = null,
    @SerializedName("condition"          ) var condition        : ConditionRemote? = ConditionRemote(),
    @SerializedName("wind_mph"           ) var windMph          : Double?    = null,
    @SerializedName("wind_kph"           ) var windKph          : Double?    = null,
    @SerializedName("wind_degree"        ) var windDegree       : Double?       = null,
    @SerializedName("wind_dir"           ) var windDir          : String?    = null,
    @SerializedName("pressure_mb"        ) var pressureMb       : Double?       = null,
    @SerializedName("pressure_in"        ) var pressureIn       : Double?    = null,
    @SerializedName("humidity"           ) var humidity         : Double?       = null,
    @SerializedName("cloud"              ) var cloud            : Double?       = null,
    @SerializedName("feelslike_c"        ) var feelslikeC       : Double?    = null,
    @SerializedName("feelslike_f"        ) var feelslikeF       : Double?    = null,
)