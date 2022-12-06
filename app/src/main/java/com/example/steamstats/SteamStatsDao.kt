package com.example.steamstats

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.steamstats.models.SteamID

@Dao
interface SteamStatsDao {
    @Query("SELECT * FROM SteamID")
    suspend fun getSteamID(): SteamID

    @Insert
    suspend fun createSteamID(steamID: SteamID): Long

    @Update
    suspend fun updateSteamID(steamID: SteamID)

    @Query("DELETE FROM SteamID")
    fun deleteSteamID()
}