package com.byjw.theweather.dashboard.adapter

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.byjw.theweather.R
import com.byjw.theweather.dashboard.DashboardContract
import com.byjw.theweather.retrofit.Weather
import com.byjw.theweather.dashboard.dialog.DetailCardDialog

class DashboardViewHolder(
    private val context: Context,
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    var weatherIcon: ImageView = itemView.findViewById(R.id.dashboard_img_weather_icon)
    var cityName: TextView = itemView.findViewById(R.id.dashboard_txt_city_name)
    var temperature: TextView = itemView.findViewById(R.id.dashboard_txt_temp)
    var description: TextView = itemView.findViewById(R.id.dashboard_txt_description)
    var gps: TextView = itemView.findViewById(R.id.dashboard_txt_gps)

    fun onBind(
        weather: Weather,
        presenter: DashboardContract.Presenter
    ) {
        itemView.setOnClickListener {
            DetailCardDialog(context, weather, presenter).show()
        }

        weatherIcon.setImageResource(presenter.returnImageResourceId(weather.weather[0].icon)!!)
        cityName.text = weather.name
        temperature.text = presenter.convertKelvinToCelsius(weather.main.temp)
        description.text = weather.weather[0].description

        if (weather.gps) {
            gps.visibility = View.VISIBLE
        }

    }
}