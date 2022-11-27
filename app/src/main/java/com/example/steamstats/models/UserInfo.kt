package com.example.steamstats.models

import org.json.JSONArray
import org.json.JSONObject

class UserInfo (
    var steamID: String,
    var userName: String,
    var avatarURL: String,
    var games: JSONArray,
    var totalHours: Int
)
