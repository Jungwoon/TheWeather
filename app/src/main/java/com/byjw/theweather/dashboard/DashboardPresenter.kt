package com.byjw.theweather.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresPermission
import com.byjw.theweather.R
import com.byjw.theweather.retrofit.OpenWeatherMap
import com.byjw.theweather.retrofit.Weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class DashboardPresenter(
    val context: Context,
    val model: DashboardContract.Model,
    val view: DashboardContract.View
) : DashboardContract.Presenter, Serializable {


    override fun getWeatherResponseByCity(cityName: String) {
        if (!view.isContainsCityName(cityName)) {
            addWeather(model.getWeatherResponseByCity(cityName = cityName))
        } else {
            Toast.makeText(context, "이미 추가 되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getWeatherResponseByCoordinates(latitude: Double, longitude: Double) {
        addWeather(model.getWeatherResponseByCoordinates(latitude = latitude, longitude =  longitude), true)
    }

    override fun removeWeather(weather: Weather) {
        view.removeWeather(weather = weather)
        model.deleteCity(weather.name)
    }

    @SuppressLint("MissingPermission")
    override fun loadItems(hasGpsPermission: Boolean) {
        view.clear()

        if (hasGpsPermission)
            addCurrentLocationWeather()

        for (bookmark in model.getCityList()) {
            getWeatherResponseByCity(bookmark.cityName)
        }
    }

    override fun addWeather(weatherCall: Call<Weather>, gps: Boolean) {
        weatherCall.enqueue(object : Callback<Weather> {
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                if (response.isSuccessful) {
                    val weather = response.body()!!

                    if (gps) {
                        weather.gps = true
                    }

                    view.addWeather(weather)
                    model.insertCity(weather.name)
                } else {
                    Toast.makeText(context, "해당 도시를 찾을 수가 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {}
        })
    }

    override fun getAutoCompleteCityList(): ArrayList<String> {
        val cityList = ArrayList<String>()
        cityList.add("Seoul")
        cityList.add("Busan")
        cityList.add("Tokyo")
        cityList.add("Kyoto")
        cityList.add("Beijing")
        cityList.add("New York")
        cityList.add("Sydney")
        cityList.add("Paris")
        cityList.add("Hong Kong")
        cityList.add("Chicago")
        cityList.add("Singapore")
        cityList.add("Taipei")
        cityList.add("Dubai")
        cityList.add("Milano")
        cityList.add("Toronto")
        cityList.add("Moscow")
        cityList.add("San Paolo")
        cityList.add("Frankfort")
        cityList.add("Los Angeles")
        cityList.add("San Francisco")
        cityList.add("Madrid")
        cityList.add("Buenos Aires")
        cityList.add("Melbourne")
        cityList.add("Bangkok")
        cityList.add("Amsterdam")
        cityList.add("Vienna")
        cityList.add("Brussel")
        cityList.add("Mexico City")

        return cityList
    }

    override fun convertKelvinToCelsius(kelvin: Float): String {
        // formula for convert kelvin to celsius
        return "${(kelvin - 273.15).toInt()}℃"
    }

    override fun returnImageResourceId(iconName: String): Int? {
        // https://openweathermap.org/weather-conditions
        val iconHashMap = HashMap<String, Int>()

        // Day
        iconHashMap["01d"] = R.drawable.day_clear_sky
        iconHashMap["02d"] = R.drawable.day_few_clouds
        iconHashMap["03d"] = R.drawable.day_scattered_clouds
        iconHashMap["04d"] = R.drawable.day_broken_clouds
        iconHashMap["09d"] = R.drawable.day_shower_rain
        iconHashMap["10d"] = R.drawable.day_rain
        iconHashMap["11d"] = R.drawable.day_thunderstorm
        iconHashMap["13d"] = R.drawable.day_snow
        iconHashMap["50d"] = R.drawable.day_mist

        // Night
        iconHashMap["01n"] = R.drawable.night_clear_sky
        iconHashMap["02n"] = R.drawable.night_few_clouds
        iconHashMap["03n"] = R.drawable.night_scattered_clouds
        iconHashMap["04n"] = R.drawable.night_broken_clouds
        iconHashMap["09n"] = R.drawable.night_shower_rain
        iconHashMap["10n"] = R.drawable.night_rain
        iconHashMap["11n"] = R.drawable.night_thunderstorm
        iconHashMap["13n"] = R.drawable.night_snow
        iconHashMap["50n"] = R.drawable.night_mist

        return iconHashMap[iconName]
    }

    @RequiresPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
    private fun addCurrentLocationWeather() {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)?.let {
            getWeatherResponseByCoordinates(latitude = it.latitude, longitude = it.longitude)
        }
    }

}