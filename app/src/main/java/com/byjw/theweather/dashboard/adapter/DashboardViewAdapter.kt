package com.byjw.theweather.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.byjw.theweather.R
import com.byjw.theweather.dashboard.DashboardContract
import com.byjw.theweather.retrofit.Weather
import com.byjw.theweather.dashboard.DashboardPresenter
import com.byjw.theweather.retrofit.OpenWeatherMap
import com.byjw.theweather.room.Bookmark
import com.byjw.theweather.room.BookmarkDao
import com.byjw.theweather.room.RepositoryHandler
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class DashboardViewAdapter(val context: Context) :
    RecyclerView.Adapter<DashboardViewHolder>(),
    DashboardContract.View,
    DashboardContract.Model
{

    private val weatherList = mutableListOf<Weather>()
    val presenter = DashboardPresenter(context, this, this)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val layoutView = LayoutInflater.from(parent.context)
            .inflate(R.layout.dashboard_card, parent, false)

        return DashboardViewHolder(context = parent.context, itemView = layoutView)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        holder.onBind(weather = weatherList[position], presenter = presenter)
    }

    override fun getItemCount() = weatherList.size

    // --------- View ---------

    override fun addWeather(weather: Weather) {
        weatherList.add(weather)
        notifyDataSetChanged()
    }

    override fun removeWeather(weather: Weather) {
        weatherList.remove(weather)
        notifyDataSetChanged()
    }

    override fun isContainsCityName(cityName: String): Boolean {
        for (weather in weatherList) {
            if (weather.name.toLowerCase() == cityName.toLowerCase()) {
                return true
            }
        }
        return false
    }

    override fun clear() {
        weatherList.clear()
        notifyDataSetChanged()
    }

    // --------- Model ---------

    override fun getWeatherResponseByCity(cityName: String)
        = OpenWeatherMap.requestByCity(cityName = cityName)

    override fun getWeatherResponseByCoordinates(latitude: Double, longitude: Double)
        = OpenWeatherMap.requestByCoordinates(latitude = latitude, longitude =  longitude)

    override fun insertCity(cityName: String) {
        Thread(Runnable {
            getBookmarkDao().insert(Bookmark(cityName = cityName))
        }).start()
    }

    override fun deleteCity(cityName: String) {
        Thread(Runnable {
            getBookmarkDao().delete(Bookmark(cityName = cityName))
        }).start()
    }

    override fun getCityList(): List<Bookmark> {
        val executor = Executors.newFixedThreadPool(4)
        return executor.submit(Callable {
            return@Callable getBookmarkDao().getBookmark()
        }).get()
    }

    private fun getBookmarkDao(): BookmarkDao {
        return RepositoryHandler(context)
            .getMainDatabase()
            .bookmarkDao()
    }

}