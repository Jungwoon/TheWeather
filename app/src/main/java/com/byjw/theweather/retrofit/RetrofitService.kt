package com.byjw.theweather.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    companion object {
        const val APP_ID = "8655ad8f439511ad000ddd69beda0a50"
    }

    @GET("data/2.5/weather")
    fun requestByCity(
        @Query("q") cityName: String,
        @Query("APPID") appId: String = APP_ID
    ): Call<Weather>

    @GET("data/2.5/weather")
    fun requestByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("APPID") appId: String = APP_ID
    ): Call<Weather>

}