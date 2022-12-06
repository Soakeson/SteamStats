package com.example.steamstats.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class SteamID (
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo var steamID: String,
        )