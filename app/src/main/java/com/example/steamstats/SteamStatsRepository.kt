package com.example.steamstats

import androidx.room.Room
import com.example.steamstats.models.SteamID

object SteamStatsRepository {
    private val db: AppDatabase;
    init {
        db = Room.databaseBuilder(
            SteamStatsApplication.getInstance(),
            AppDatabase::class.java,
            "steam-stats-database"
        ).build()
    }

    suspend fun createSteamId(steamID: SteamID): Long {
        return db.getSteamStatsDao().createSteamID(steamID)
    }

    suspend fun getSteamID(): SteamID {
        return db.getSteamStatsDao().getSteamID()
    }

    suspend fun updateSteamID(steamID: SteamID) {
        db.getSteamStatsDao().updateSteamID(steamID)
    }
}