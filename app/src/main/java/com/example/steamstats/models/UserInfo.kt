package com.example.steamstats.models

import androidx.databinding.ObservableArrayList
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.json.JSONArray
import org.json.JSONObject

class UserInfo (
    var steamID: String,
    var userName: String,
    var avatarURL: String,
    var gamesList: ObservableArrayList<GameInfo>,
    var backlog: ObservableArrayList<GameInfo>,
    var totalHours: Int,
    var lastLogoff: Long,
    var timeCreated: Long
)

