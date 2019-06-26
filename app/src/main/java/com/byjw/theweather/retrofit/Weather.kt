package com.byjw.theweather.retrofit

data class Weather (
    val coord: CoordParameter,
    val weather: List<WeatherParameter>,
    val main: MainParameter,
    val wind: WindParameter,
    val clouds: CloudsParameter,
    val dt: Long,
    val sys: SysParameter,
    val timezone: Long,
    val id: String,
    val name: String,
    var gps: Boolean
)

data class CoordParameter(
    val lon: Float,
    val lat: Float
)

data class WeatherParameter(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class MainParameter(
    val temp: Float,
    val pressure: Float,
    val humidity: Int,
    val temp_min: Float,
    val temp_max: Float
)

data class WindParameter(
    val speed: Float,
    val deg: Float
)

data class CloudsParameter(
    val all: Int
)

data class SysParameter(
    val type: Int,
    val id: Int,
    val message: Float,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)