package com.example.shows_tomislavlovrencic.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shows_tomislavlovrencic.classes.Show

@Database(version = 1,
    entities = [Show::class])
abstract class ShowsDatabase : RoomDatabase() {
    abstract fun showsDao() : ShowsDao
}