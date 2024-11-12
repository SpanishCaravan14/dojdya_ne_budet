package com.example.dnb.data.api

import com.example.dnb.models.geoApi.GeoRemote
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

//Интерфейс с запросом для получения данных о городах соответствующих запросу пользователя

interface GeoService {

    @FormUrlEncoded
    @POST("search.json")
    suspend fun getGeo(
        @Field ("key") key : String,
        @Field ("q") q : String,
    ) : ArrayList<GeoRemote>
}