package com.byjw.theweather.retrofit

import retrofit2.Call

object OpenWeatherMap {

    fun requestByCity(cityName: String): Call<Weather> {
        return WeatherRetrofit.getService().requestByCity(cityName = cityName)
    }

    fun requestByCoordinates(latitude: Double, longitude: Double): Call<Weather> {
        return WeatherRetrofit.getService().requestByCoordinates(latitude = latitude, longitude = longitude)
    }

}