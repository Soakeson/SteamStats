package com.example.steamstats.models

import org.json.JSONObject

class UserInfo(response: String) {
    var JSONObject = JSONObject(response)
    var json = JSONObject.getJSONObject("response")

    var gamesCount = json.getInt("game_count")
    var games = json.getJSONArray("games")
    var totalHours = 0

    init {
        parseJSON()
    }

    fun parseJSON() {
        for (i in 0 until gamesCount) {
            val game = games.getJSONObject(i)
            totalHours += game.getInt("playtime_forever")
        }
        totalHours /= 60
    }
}