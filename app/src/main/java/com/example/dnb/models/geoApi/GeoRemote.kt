package com.example.dnb.models.geoApi
import com.example.dnb.models.geoApi.localDataModels.GeoInfoLocal
import com.google.gson.annotations.SerializedName

data class GeoRemote (

@SerializedName("id"      ) var id      : Int?    = null,
@SerializedName("name"    ) var name    : String? = null,
@SerializedName("region"  ) var region  : String? = null,
@SerializedName("country" ) var country : String? = null,
@SerializedName("lat"     ) var lat     : Double? = null,
@SerializedName("lon"     ) var lon     : Double? = null,
@SerializedName("url"     ) var url     : String? = null

)

fun GeoRemote.mapToModel() : GeoInfoLocal{
    return GeoInfoLocal(
        name = name?: "-",
        region = region ?: "",
        country = country ?: "-",
        lat = lat ?: 0.0,
        long = lon ?: 0.0
    )
}