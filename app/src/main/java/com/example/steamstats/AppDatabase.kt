package com.example.steamstats

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.steamstats.models.SteamID

@Database(entities = [SteamID::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getSteamStatsDao(): SteamStatsDao
}