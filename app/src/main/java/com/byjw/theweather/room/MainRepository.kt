package com.byjw.theweather.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(Bookmark::class)], version = 1)
abstract class MainRepository : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}