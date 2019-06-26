package com.byjw.theweather.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark")
data class Bookmark (

    @PrimaryKey
    val cityName: String
)