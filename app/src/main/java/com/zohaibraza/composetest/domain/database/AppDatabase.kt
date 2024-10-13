package com.zohaibraza.composetest.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zohaibraza.composetest.model.Track
import com.zohaibraza.composetest.data.local.TrackDao

@Database(entities = [Track::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
}