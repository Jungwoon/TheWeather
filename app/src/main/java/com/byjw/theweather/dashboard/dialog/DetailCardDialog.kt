package com.byjw.theweather.dashboard.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.byjw.theweather.R
import com.byjw.theweather.dashboard.DashboardContract
import com.byjw.theweather.retrofit.Weather

class DetailCardDialog(
    context: Context,
    val weather: Weather,
    private val presenter: DashboardContract.Presenter
) : Dialog(context) {

    private val cityName by lazy {
        findViewById<TextView>(R.id.detail_txt_city_name)
    }

    private val weatherIcon by lazy {
        findViewById<ImageView>(R.id.detail_img_weather_icon)
    }

    private val temperature by lazy {
        findViewById<TextView>(R.id.detail_txt_temp)
    }

    private val description by lazy {
        findViewById<TextView>(R.id.detail_txt_description)
    }

    private val lowTemperature by lazy {
        findViewById<TextView>(R.id.detail_txt_low_temp)
    }

    private val highTemperature by lazy {
        findViewById<TextView>(R.id.detail_txt_high_temp)
    }

    private val humidity by lazy {
        findViewById<TextView>(R.id.detail_txt_humidity)
    }

    private val pressure by lazy {
        findViewById<TextView>(R.id.detail_txt_pressure)
    }

    private val windSpeed by lazy {
        findViewById<TextView>(R.id.detail_txt_wind_speed)
    }

    private val btnBack by lazy {
        findViewById<Button>(R.id.detail_btn_back)
    }

    private val btnDelete by lazy {
        findViewById<Button>(R.id.detail_btn_delete)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layoutParamsWindow = WindowManager.LayoutParams()
        layoutParamsWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        layoutParamsWindow.dimAmount = 0.8f
        window!!.attributes = layoutParamsWindow

        setContentView(R.layout.dialog_detail_card)

        cityName.text = weather.name
        weatherIcon.setImageResource(presenter.returnImageResourceId(weather.weather[0].icon)!!)
        temperature.text = presenter.convertKelvinToCelsius(weather.main.temp)
        description.text = weather.weather[0].description

        lowTemperature.text = presenter.convertKelvinToCelsius(weather.main.temp_min)
        highTemperature.text = presenter.convertKelvinToCelsius(weather.main.temp_max)
        humidity.text = weather.main.humidity.toString() + "%"
        pressure.text = weather.main.pressure.toString() + "hpa"
        windSpeed.text = weather.wind.speed.toString() + "m/s"

        btnBack.setOnClickListener {
            dismiss()
        }

        btnDelete.setOnClickListener {
            presenter.removeWeather(weather)
            dismiss()
        }

    }

}