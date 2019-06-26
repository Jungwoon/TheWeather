package com.byjw.theweather.dashboard

import com.byjw.theweather.retrofit.Weather
import com.byjw.theweather.room.Bookmark
import retrofit2.Call

interface DashboardContract {

    interface Model {

        fun getWeatherResponseByCity(cityName: String): Call<Weather>

        fun getWeatherResponseByCoordinates(latitude: Double, longitude: Double): Call<Weather>

        fun insertCity(cityName: String)

        fun deleteCity(cityName: String)

        fun getCityList(): List<Bookmark>

    }

    interface View {

        fun addWeather(weather: Weather)

        fun removeWeather(weather: Weather)

        fun isContainsCityName(cityName: String): Boolean

        fun clear()
    }

    interface Presenter {

        fun getWeatherResponseByCity(cityName: String)

        fun getWeatherResponseByCoordinates(latitude: Double, longitude: Double)

        fun removeWeather(weather: Weather)

        fun loadItems(hasGpsPermission: Boolean)

        fun convertKelvinToCelsius(kelvin: Float): String

        fun returnImageResourceId(iconName: String): Int?

        fun getAutoCompleteCityList(): ArrayList<String>

        fun addWeather(weatherCall: Call<Weather>, gps: Boolean = false)

    }

}