package com.byjw.theweather.room

import androidx.room.*

@Dao
interface BookmarkDao {

    @Query("SELECT * FROM bookmark")
    fun getBookmark(): List<Bookmark>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bookmark: Bookmark)

    @Delete
    fun delete(bookmark: Bookmark)
}