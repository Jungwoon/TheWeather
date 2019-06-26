package com.byjw.theweather.room

import android.content.Context
import androidx.room.Room

class RepositoryHandler(private val context: Context) {

    fun getMainDatabase()
            = Room.databaseBuilder(context, MainRepository::class.java, "weather.db").build()
}